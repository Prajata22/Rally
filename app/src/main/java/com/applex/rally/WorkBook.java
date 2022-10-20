package com.applex.rally;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class WorkBook extends AppCompatActivity implements ActionBar.OnNavigationListener {
    public static final String TAG = "hackTSD";
    private static final String SUBTAG = "WorkBook ";
    /* access modifiers changed from: private */
    public Context context = this;
    /* access modifiers changed from: private */
    public Button first;
    /* access modifiers changed from: private */
    public Button last;
    /* access modifiers changed from: private */
    public TextView rbodo;
    Float cSpeed;
    float changedistance;
    Float diff;
    Float difftc;
    TimerTask doAsynchronousTask;
    /* renamed from: e */
    float f678e;
    SharedPreferences.Editor edit;
    String gotovalue;
    int houra;
    int hourb;
    /* renamed from: l */
    int f681l;
    SharedPreferences loginpref;
    /* renamed from: m */
    int f682m;
    int minutea;
    int minuteb;
    float nspeed;
    float rbo;
    int seconda;
    int secondb;
    float speedlimit;
    Timer timer;
    /* renamed from: x */
    Float f685x;
    /* renamed from: x1 */
    Float f686x1;
    /* renamed from: y */
    Float f687y;
    /* renamed from: y1 */
    Float f688y1;
    /* renamed from: z */
    int f689z;
    private TextView actualspeed;
    private Button back;
    private Button backrb;
    private TextView change;
    private TextView changerb;
    private TextView changetime;
    private TextView changetimerb;
    private TextView currentspeed;
    private TextView currentspeedrb;
    private TextView error;
    private Button firstrb;
    /* renamed from: go */
    private Button f679go;
    /* renamed from: hh */
    private TextView f680hh;
    private TextView hhrb;
    private Button lastrb;
    private TextView lasttcodo;
    private TextView lasttctime;
    private LocationManager mLocationManager;
    /* renamed from: mm */
    private TextView f683mm;
    private TextView mmrb;
    private Button next;
    private Button nextrb;
    private TextView nextspeed;
    private TextView nextspeedrb;
    private View.OnClickListener onClickListener;
    private TextView rbodorb;
    private Button save;
    private Button saverb;
    /* renamed from: ss */
    private TextView f684ss;
    private TextView ssrb;
    private TextView status;
    private TextView totalpenalty;
    private TextView zone;
    private TextView zonelabel;
    private TextView zonerb;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.workbook);
            this.loginpref = getSharedPreferences("HACKTSD_APPLICATION_PREFERENCE", 0);
            this.edit = this.loginpref.edit();
            this.edit.commit();
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            getWindow().addFlags(128);
            findViews();
            setListeners();
            calculaterb();
            callAsynchronousTask();
            int j = 1;
            for (int i = 1; i < 1000; i++) {
                if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i), 0.0f) > 0.0f) {
                    j++;
                }
            }
            this.first.setText(String.valueOf(this.loginpref.getFloat("increment", 0.01f)));
            this.last.setText(String.valueOf(this.loginpref.getFloat("increment", 0.01f)));
            this.lasttcodo.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + j, 0.0f)));
            this.lasttctime.setText(String.valueOf(this.loginpref.getInt("tchhi" + j, 0)) + " : " + this.loginpref.getInt("tcmmi" + j, 0) + " : " + this.loginpref.getInt("tcssi" + j, 0));
            this.totalpenalty.setText(String.valueOf(this.loginpref.getInt("penaltyht" + j, 0)) + " : " + this.loginpref.getInt("penaltymt" + j, 0) + " : " + this.loginpref.getInt("penaltyst" + j, 0));
            if (this.loginpref.getInt("distancePreference", 0) == 1) {
                this.status.setText("Odo in Miles");
            } else {
                this.status.setText("Odo in KMs");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        this.timer = new Timer();
        this.doAsynchronousTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            WorkBook.this.checkSpeedChange();
                            WorkBook.this.calculate();
                            WorkBook.this.parseData();
                            if (!WorkBook.this.isServiceRunning()) {
                                Log.d("hackTSD", "WorkBook Staring Location Update service.");
                                WorkBook.this.startService(new Intent(WorkBook.this.context, GPSTracker.class));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        this.timer.schedule(this.doAsynchronousTask, 0, 1000);
    }

    public void checkSpeedChange() {
        float speed = this.loginpref.getFloat("zoneSpeedValue" + 1, 0.0f);
        float currentSpeed = this.loginpref.getFloat("speedUsed", speed);
        if (currentSpeed - this.loginpref.getFloat("reference", speed) != 0.0f) {
            speedChanged();
            this.edit.putFloat("reference", currentSpeed);
            this.edit.commit();
        }
    }

    public void speedChanged() {
        try {
            MediaPlayer.create(this, R.raw.sound).start();
        } catch (Exception e) {
        }
    }

    /* access modifiers changed from: private */
    public boolean isServiceRunning() {
        Log.i("hackTSD", "WorkBook isServiceRunning()");
        for (ActivityManager.RunningServiceInfo service : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if ("com.whizzdumbnetworks.hacktsdpro.GPSTracker".equals(service.service.getClassName())) {
                Log.d("hackTSD", "WorkBook Matching service found, returning true");
                return true;
            }
        }
        Log.d("hackTSD", "WorkBook No matching service found, returning false");
        return false;
    }

    public void calculate() {
        try {
            this.rbo = this.loginpref.getFloat("distanceTravelled", 5.0E-4f);
            this.f678e = this.loginpref.getFloat("gpsAccuracy", 0.0f);
            this.cSpeed = Float.valueOf(this.loginpref.getFloat("currentSpeed", 0.0f));
            int j = 0;
            for (int i = 1; i < 1000; i++) {
                if (this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i), 0.0f) > 0.0f) {
                    j++;
                }
            }
            int k = 1;
            for (int i2 = 1; i2 < 1000; i2++) {
                if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i2), 0.0f) > 0.0f) {
                    k++;
                }
            }
            Log.d("hackTSD", "WorkBook Calculate reached 3...");
            for (int a = 1; a < j + 1; a++) {
                Float distance = Float.valueOf(this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(a), 0.0f));
                Float previousdistance = Float.valueOf(this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(a - 1), 0.0f));
                Float speed = Float.valueOf(this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(a), 0.0f));
                for (int b = 1; b < k + 1; b++) {
                    if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(b), 0.0f) >= distance.floatValue() || this.loginpref.getFloat("tcDistanceValue" + String.valueOf(b), 0.0f) < previousdistance.floatValue()) {
                        Float t1 = Float.valueOf(Float.valueOf(distance.floatValue() - previousdistance.floatValue()).floatValue() / speed.floatValue());
                        Float thh = Float.valueOf(t1.floatValue() - (t1.floatValue() % 1.0f));
                        Float t2 = Float.valueOf(t1.floatValue() * 60.0f);
                        Float tmm = Float.valueOf((t2.floatValue() - (t2.floatValue() % 1.0f)) - (thh.floatValue() * 60.0f));
                        Float tss = Float.valueOf(Float.valueOf(t2.floatValue() % 1.0f).floatValue() * 60.0f);
                        Float ah = Float.valueOf(this.loginpref.getFloat("zonehh" + String.valueOf(a - 1), 0.0f));
                        Float am = Float.valueOf(this.loginpref.getFloat("zonemm" + String.valueOf(a - 1), 0.0f));
                        Float as = Float.valueOf(this.loginpref.getFloat("zoness" + String.valueOf(a - 1), 0.0f));
                        Float hour = Float.valueOf(thh.floatValue() + ah.floatValue());
                        Float minute = Float.valueOf(tmm.floatValue() + am.floatValue());
                        Float second = Float.valueOf(tss.floatValue() + as.floatValue());
                        if (second.floatValue() > 60.0f) {
                            minute = Float.valueOf(minute.floatValue() + 1.0f);
                            second = Float.valueOf(second.floatValue() - 60.0f);
                        }
                        if (minute.floatValue() > 60.0f) {
                            hour = Float.valueOf(hour.floatValue() + 1.0f);
                            minute = Float.valueOf(minute.floatValue() - 60.0f);
                        }
                        if (hour.floatValue() > 24.0f) {
                            hour = Float.valueOf(hour.floatValue() - 24.0f);
                        }
                        this.edit.putFloat("zonehh" + String.valueOf(a), hour.floatValue());
                        this.edit.putFloat("zonemm" + String.valueOf(a), minute.floatValue());
                        this.edit.putFloat("zoness" + String.valueOf(a), second.floatValue());
                        Log.d("hackTSD", SUBTAG + hour + " : " + minute + " : " + second);
                        this.edit.commit();
                    } else {
                        Float t12 = Float.valueOf(Float.valueOf(distance.floatValue() - this.loginpref.getFloat("tcDistanceValue" + String.valueOf(b), 0.0f)).floatValue() / speed.floatValue());
                        Float thh2 = Float.valueOf(t12.floatValue() - (t12.floatValue() % 1.0f));
                        Float t22 = Float.valueOf(t12.floatValue() * 60.0f);
                        Float tmm2 = Float.valueOf((t22.floatValue() - (t22.floatValue() % 1.0f)) - (thh2.floatValue() * 60.0f));
                        Float tss2 = Float.valueOf(Float.valueOf(t22.floatValue() % 1.0f).floatValue() * 60.0f);
                        Float ah2 = Float.valueOf(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(b), 0)));
                        Float am2 = Float.valueOf(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(b), 0)));
                        Float as2 = Float.valueOf(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(b), 0)));
                        Float hour2 = Float.valueOf(thh2.floatValue() + ah2.floatValue());
                        Float minute2 = Float.valueOf(tmm2.floatValue() + am2.floatValue());
                        Float second2 = Float.valueOf(tss2.floatValue() + as2.floatValue());
                        if (second2.floatValue() >= 60.0f) {
                            minute2 = Float.valueOf(minute2.floatValue() + 1.0f);
                            second2 = Float.valueOf(second2.floatValue() - 60.0f);
                        }
                        if (minute2.floatValue() >= 60.0f) {
                            hour2 = Float.valueOf(hour2.floatValue() + 1.0f);
                            minute2 = Float.valueOf(minute2.floatValue() - 60.0f);
                        }
                        if (hour2.floatValue() >= 24.0f) {
                            hour2 = Float.valueOf(hour2.floatValue() - 24.0f);
                        }
                        this.edit.putFloat("zonehh" + String.valueOf(a), hour2.floatValue());
                        this.edit.putFloat("zonemm" + String.valueOf(a), minute2.floatValue());
                        this.edit.putFloat("zoness" + String.valueOf(a), second2.floatValue());
                        Log.d("hackTSD", SUBTAG + hour2 + " : " + minute2 + " : " + second2);
                        this.edit.commit();
                    }
                }
            }
            Log.d("hackTSD", "WorkBook Calculate reached 4...");
            for (int i3 = 1; i3 < j + 1; i3++) {
                if (this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f) < this.rbo && this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3), 0.0f) >= this.rbo) {
                    this.edit.putFloat("speedUsed", this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(i3), 0.0f));
                    this.edit.putFloat("nextSpeed", this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(i3 + 1), 0.0f));
                    this.edit.putFloat("speedChangeDistance", this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3), 0.0f));
                    Float ah3 = Float.valueOf(this.loginpref.getFloat("zonehh" + String.valueOf(i3), 0.0f));
                    Float am3 = Float.valueOf(this.loginpref.getFloat("zonemm" + String.valueOf(i3), 0.0f));
                    Float as3 = Float.valueOf(this.loginpref.getFloat("zoness" + String.valueOf(i3), 0.0f));
                    this.edit.putString("speedChangeTime", String.valueOf(String.valueOf(Math.round(ah3.floatValue()))) + " : " + String.valueOf(Math.round(am3.floatValue())) + " : " + String.valueOf(Math.round(as3.floatValue())));
                    this.edit.commit();
                    for (int l = 1; l < k + 1; l++) {
                        if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f) <= this.rbo && this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f) <= this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f)) {
                            this.edit.putFloat("hardPointtc", this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f));
                            this.edit.putFloat("husetc", Float.valueOf(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(l), 0))).floatValue());
                            this.edit.putFloat("musetc", Float.valueOf(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(l), 0))).floatValue());
                            this.edit.putFloat("susetc", Float.valueOf(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(l), 0))).floatValue());
                            this.edit.commit();
                        }
                    }
                    Float q2 = Float.valueOf(this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f));
                    Float qh = Float.valueOf(this.loginpref.getFloat("zonehh" + String.valueOf(i3 - 1), 0.0f));
                    Float qm = Float.valueOf(this.loginpref.getFloat("zonemm" + String.valueOf(i3 - 1), 0.0f));
                    Float qs = Float.valueOf(this.loginpref.getFloat("zoness" + String.valueOf(i3 - 1), 0.0f));
                    this.edit.putFloat("hardPointz", q2.floatValue());
                    this.edit.putFloat("husez", qh.floatValue());
                    this.edit.putFloat("musez", qm.floatValue());
                    this.edit.putFloat("susez", qs.floatValue());
                    this.edit.commit();
                }
            }
            if (Float.valueOf(this.rbo - this.loginpref.getFloat("hardPointz", 0.0f)).floatValue() >= Float.valueOf(this.rbo - this.loginpref.getFloat("hardPointtc", 0.0f)).floatValue()) {
                Float hp = Float.valueOf(this.loginpref.getFloat("hardPointtc", 0.0f));
                Float hz = Float.valueOf(this.loginpref.getFloat("husetc", 0.0f));
                Float mz = Float.valueOf(this.loginpref.getFloat("musetc", 0.0f));
                Float sz = Float.valueOf(this.loginpref.getFloat("susetc", 0.0f));
                this.edit.putFloat("hardPoint", hp.floatValue());
                this.edit.putFloat("huse", hz.floatValue());
                this.edit.putFloat("muse", mz.floatValue());
                this.edit.putFloat("suse", sz.floatValue());
                this.edit.commit();
            } else {
                Float hp2 = Float.valueOf(this.loginpref.getFloat("hardPointz", 0.0f));
                Float ht = Float.valueOf(this.loginpref.getFloat("husez", 0.0f));
                Float mt = Float.valueOf(this.loginpref.getFloat("musez", 0.0f));
                Float st = Float.valueOf(this.loginpref.getFloat("susez", 0.0f));
                this.edit.putFloat("hardPoint", hp2.floatValue());
                this.edit.putFloat("huse", ht.floatValue());
                this.edit.putFloat("muse", mt.floatValue());
                this.edit.putFloat("suse", st.floatValue());
                this.edit.commit();
            }
            Float hardpoint = Float.valueOf(this.loginpref.getFloat("hardPoint", 0.0f));
            this.speedlimit = this.loginpref.getFloat("speedUsed", 0.0f);
            this.nspeed = this.loginpref.getFloat("nextSpeed", 0.0f);
            this.changedistance = this.loginpref.getFloat("speedChangeDistance", 0.0f);
            Log.d("hackTSD", "WorkBook Hard Point : " + hardpoint + "    " + "speedlimit : " + this.speedlimit);
            Float del = Float.valueOf(this.rbo - hardpoint.floatValue());
            Log.d("hackTSD", "WorkBook del : " + del);
            Float t11 = Float.valueOf(del.floatValue() / this.speedlimit);
            Float th = Float.valueOf(t11.floatValue() - (t11.floatValue() % 1.0f));
            Float th2 = Float.valueOf(th.floatValue() - (th.floatValue() % 1.0f));
            Float t222 = Float.valueOf(t11.floatValue() * 60.0f);
            Float tm = Float.valueOf((t222.floatValue() - (t222.floatValue() % 1.0f)) - (th2.floatValue() * 60.0f));
            Float tm2 = Float.valueOf(tm.floatValue() - (tm.floatValue() % 1.0f));
            Float ts = Float.valueOf(Float.valueOf(t222.floatValue() % 1.0f).floatValue() * 60.0f);
            Float ts2 = Float.valueOf(ts.floatValue() - (ts.floatValue() % 1.0f));
            Log.d("hackTSD", "WorkBook Calculate reached 10...");
            Float bh = Float.valueOf(this.loginpref.getFloat("huse", 0.0f));
            Float bh2 = Float.valueOf(bh.floatValue() - (bh.floatValue() % 1.0f));
            Float bm = Float.valueOf(this.loginpref.getFloat("muse", 0.0f));
            Float bm2 = Float.valueOf(bm.floatValue() - (bm.floatValue() % 1.0f));
            Float bs = Float.valueOf(this.loginpref.getFloat("suse", 0.0f));
            Float bs2 = Float.valueOf(bs.floatValue() - (bs.floatValue() % 1.0f));
            Log.d("hackTSD", "WorkBook  : " + del);
            this.houra = Math.round(th2.floatValue() + bh2.floatValue());
            this.minutea = Math.round(tm2.floatValue() + bm2.floatValue());
            this.seconda = Math.round(ts2.floatValue() + bs2.floatValue());
            if (this.seconda >= 60) {
                this.minutea++;
                this.seconda -= 60;
            }
            if (this.minutea >= 60) {
                this.houra++;
                this.minutea -= 60;
            }
            if (this.houra >= 24) {
                this.houra -= 24;
            }
            Calendar cal = Calendar.getInstance();
            int second3 = cal.get(13);
            int minute3 = cal.get(12);
            this.edit.putInt("delta", ((cal.get(11) - this.houra) * 3600) + ((minute3 - this.minutea) * 60) + (second3 - this.seconda));
            this.edit.putInt("hh", this.houra);
            this.edit.putInt("mm", this.minutea);
            this.edit.putInt("ss", this.seconda);
            this.edit.commit();
            Log.d("hackTSD", "WorkBook actual time" + this.houra + " : " + this.minutea + " : " + this.seconda);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void parseData() {
        this.error.setText(String.valueOf(this.f678e));
        this.actualspeed.setText(String.valueOf(this.cSpeed));
        this.currentspeed.setText(String.valueOf(this.speedlimit));
        this.change.setText(String.valueOf(this.changedistance));
        this.nextspeed.setText(String.valueOf(this.nspeed));
        this.changetime.setText(this.loginpref.getString("speedChangeTime", ""));
        this.rbodo.setText(String.valueOf(round(this.rbo, 3)));
        int delta1 = this.loginpref.getInt("delta", 0);
        this.f680hh.setText(String.valueOf(this.houra));
        this.f683mm.setText(String.valueOf(this.minutea));
        this.f684ss.setText(String.valueOf(this.seconda));
        if (delta1 < 0) {
            this.zone.setText(String.valueOf(delta1 * -1));
            this.zonelabel.setText(" Seconds Early");
            this.zone.setTextColor(-65536);
            this.zonelabel.setTextColor(-65536);
        } else if (delta1 == 0) {
            this.zone.setText("Perfect Timing");
            this.zonelabel.setText("");
            this.zone.setTextColor(-1);
            this.zonelabel.setTextColor(-1);
        } else {
            this.zone.setText(String.valueOf(delta1));
            this.zonelabel.setText(" Seconds Lateness");
            this.zone.setTextColor(-16711936);
            this.zonelabel.setTextColor(-16711936);
        }
    }

    public static BigDecimal round(float d, int decimalPlace) {
        return new BigDecimal(Float.toString(d)).setScale(decimalPlace, 4);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.i("hackTSD", "WorkBook onPause");
        super.onPause();
        showNotification();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Log.i("hackTSD", "WorkBook onResume");
        super.onResume();
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher).setContentTitle("hackTSD Pro").setContentText("Return to rally computer");
        builder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, WorkBook.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), PendingIntent.FLAG_UPDATE_CURRENT));
        builder.setAutoCancel(true);
        builder.setLights(-16776961, 500, 500);
        builder.setStyle(new NotificationCompat.InboxStyle());
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, builder.build());
    }

    private void findViews() {
        this.first = (Button) findViewById(R.id.first);
        this.last = (Button) findViewById(R.id.last);
        this.next = (Button) findViewById(R.id.next);
        this.back = (Button) findViewById(R.id.back);
        this.save = (Button) findViewById(R.id.save);
        this.f680hh = (TextView) findViewById(R.id.hh_tv);
        this.f683mm = (TextView) findViewById(R.id.mm_tv);
        this.f684ss = (TextView) findViewById(R.id.ss_tv);
        this.rbodo = (TextView) findViewById(R.id.rbodo_tv);
        this.rbodo.setText("0.0");
        this.rbodorb.setText("0.0");
        this.currentspeed = (TextView) findViewById(R.id.currentspeed_tv);
        this.nextspeed = (TextView) findViewById(R.id.nextspeed_tv);
        this.change = (TextView) findViewById(R.id.change_tv);
        this.error = (TextView) findViewById(R.id.error_tv);
        this.changetime = (TextView) findViewById(R.id.changetime_tv);
        this.status = (TextView) findViewById(R.id.status_tv);
        this.zone = (TextView) findViewById(R.id.zone_tv);
        this.zonelabel = (TextView) findViewById(R.id.zone_label_tv);
        this.actualspeed = (TextView) findViewById(R.id.actualspeed_tv);
        this.lasttcodo = (TextView) findViewById(R.id.last_tc);
        this.lasttctime = (TextView) findViewById(R.id.last_tc_time);
        this.totalpenalty = (TextView) findViewById(R.id.total_penalty);
    }

    private void setListeners() {
        try {
            Log.d("hackTSD", "WorkBook setListeners()");
            Log.d("hackTSD", "WorkBook Setting onClickListener...");
            this.onClickListener = new View.OnClickListener() {
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.save:
                            Log.d("hackTSD", "WorkBook Button Press: save");
                            View promptsView = LayoutInflater.from(WorkBook.this.context).inflate(R.layout.prompts, (ViewGroup) null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WorkBook.this.context);
                            alertDialogBuilder.setView(promptsView);
                            alertDialogBuilder.setTitle("Correct Odometer");
                            alertDialogBuilder.setMessage("Enter Odometer with respect to Roadbook");
                            EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                            userInput.setRawInputType(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                            final EditText editText = userInput;
                            alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (String.valueOf(editText.getText().toString()).length() != 0) {
                                        WorkBook.this.rbodo.setText(String.valueOf(editText.getText().toString()));
                                        WorkBook.this.edit.putFloat("distanceTravelled", Float.valueOf(editText.getText().toString()).floatValue());
                                        WorkBook.this.edit.commit();
                                        new CheckUserRequestStatus().execute(new String[0]);
                                        return;
                                    }
                                    Toast.makeText(WorkBook.this.context, "Enter Correct Value", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                            alertDialogBuilder.create().show();
                            userInput.requestFocus();
                            return;
                        case R.id.first:
                            WorkBook.this.dothis();
                            return;
                        case R.id.last:
                            Log.d("hackTSD", "WorkBook Button Press: last");
                            WorkBook.this.dothis();
                            return;
                        case R.id.back:
                            Log.d("hackTSD", "WorkBook Button Press: back");
                            Float result1 = Float.valueOf(Float.valueOf(WorkBook.this.loginpref.getFloat("distanceTravelled", 0.0f)).floatValue() - Float.valueOf(WorkBook.this.last.getText().toString()).floatValue());
                            if (result1.floatValue() < 0.0f) {
                                Toast.makeText(WorkBook.this.context, "Can't decrement further !", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            WorkBook.this.edit.putFloat("distanceTravelled", result1.floatValue());
                            WorkBook.this.edit.commit();
                            new CheckUserRequestStatus().execute(new String[0]);
                            return;
                        case R.id.next:
                            WorkBook.this.edit.putFloat("distanceTravelled", Float.valueOf(Float.valueOf(WorkBook.this.loginpref.getFloat("distanceTravelled", 0.0f)).floatValue() + Float.valueOf(WorkBook.this.first.getText().toString()).floatValue()).floatValue());
                            WorkBook.this.edit.commit();
                            new CheckUserRequestStatus().execute(new String[0]);
                            return;
                        default:
                            return;
                    }
                }
            };
            Log.d("hackTSD", "WorkBook Setting setOnClickListener for buttons");
            this.save.setOnClickListener(this.onClickListener);
            this.last.setOnClickListener(this.onClickListener);
            this.first.setOnClickListener(this.onClickListener);
            this.back.setOnClickListener(this.onClickListener);
            this.next.setOnClickListener(this.onClickListener);
            this.saverb.setOnClickListener(this.onClickListener);
            this.lastrb.setOnClickListener(this.onClickListener);
            this.firstrb.setOnClickListener(this.onClickListener);
            this.backrb.setOnClickListener(this.onClickListener);
            this.nextrb.setOnClickListener(this.onClickListener);
            this.f679go.setOnClickListener(this.onClickListener);
            Log.d("hackTSD", "WorkBook Setting setOnClickListener for Buttons Completed ");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void dothis() {
        View promptsView1 = LayoutInflater.from(this.context).inflate(R.layout.prompts, (ViewGroup) null);
        AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this.context);
        alertDialogBuilder1.setView(promptsView1);
        alertDialogBuilder1.setTitle("Increment Odometer");
        alertDialogBuilder1.setMessage("Enter Odometer Increment Value");
        final EditText userInput1 = (EditText) promptsView1.findViewById(R.id.editTextDialogUserInput);
        userInput1.setRawInputType(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        alertDialogBuilder1.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (String.valueOf(userInput1.getText().toString()).length() != 0) {
                    WorkBook.this.last.setText(String.valueOf(userInput1.getText().toString()));
                    WorkBook.this.first.setText(String.valueOf(userInput1.getText().toString()));
                    WorkBook.this.edit.putFloat("increment", Float.valueOf(userInput1.getText().toString()).floatValue());
                    WorkBook.this.edit.commit();
                    return;
                }
                Toast.makeText(WorkBook.this.context, "Enter Correct Value", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertDialogBuilder1.create().show();
        userInput1.requestFocus();
    }

    public void calculaterb() {
        try {
            float rborb = this.loginpref.getFloat("rbDistanceValue" + String.valueOf(Integer.valueOf(this.zonerb.getText().toString()).intValue()), 0.0f);
            Log.d("hackTSD", "WorkBook Calculate reached 1...");
            int j = 0;
            for (int i = 1; i < 1000; i++) {
                if (this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i), 0.0f) > 0.0f) {
                    j++;
                }
            }
            Log.d("hackTSD", "WorkBook Calculate reached 2...");
            int k = 1;
            for (int i2 = 1; i2 < 1000; i2++) {
                if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i2), 0.0f) > 0.0f) {
                    k++;
                }
            }
            Log.d("hackTSD", "WorkBook Calculate reached 3...");
            for (int a = 1; a < j + 1; a++) {
                Float distance = Float.valueOf(this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(a), 0.0f));
                Float previousdistance = Float.valueOf(this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(a - 1), 0.0f));
                Float speed = Float.valueOf(this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(a), 0.0f));
                for (int b = 1; b < k + 1; b++) {
                    if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(b), 0.0f) >= distance.floatValue() || this.loginpref.getFloat("tcDistanceValue" + String.valueOf(b), 0.0f) < previousdistance.floatValue()) {
                        Float t1 = Float.valueOf(Float.valueOf(distance.floatValue() - previousdistance.floatValue()).floatValue() / speed.floatValue());
                        Float thh = Float.valueOf(t1.floatValue() - (t1.floatValue() % 1.0f));
                        Float t2 = Float.valueOf(t1.floatValue() * 60.0f);
                        Float tmm = Float.valueOf((t2.floatValue() - (t2.floatValue() % 1.0f)) - (thh.floatValue() * 60.0f));
                        Float tss = Float.valueOf(Float.valueOf(t2.floatValue() % 1.0f).floatValue() * 60.0f);
                        Float ah = Float.valueOf(this.loginpref.getFloat("zonehhrb" + String.valueOf(a - 1), 0.0f));
                        Float am = Float.valueOf(this.loginpref.getFloat("zonemmrb" + String.valueOf(a - 1), 0.0f));
                        Float as = Float.valueOf(this.loginpref.getFloat("zonessrb" + String.valueOf(a - 1), 0.0f));
                        Float hour = Float.valueOf(thh.floatValue() + ah.floatValue());
                        Float minute = Float.valueOf(tmm.floatValue() + am.floatValue());
                        Float second = Float.valueOf(tss.floatValue() + as.floatValue());
                        if (second.floatValue() > 60.0f) {
                            minute = Float.valueOf(minute.floatValue() + 1.0f);
                            second = Float.valueOf(second.floatValue() - 60.0f);
                        }
                        if (minute.floatValue() > 60.0f) {
                            hour = Float.valueOf(hour.floatValue() + 1.0f);
                            minute = Float.valueOf(minute.floatValue() - 60.0f);
                        }
                        if (hour.floatValue() > 24.0f) {
                            hour = Float.valueOf(hour.floatValue() - 24.0f);
                        }
                        this.edit.putFloat("zonehhrb" + String.valueOf(a), hour.floatValue());
                        this.edit.putFloat("zonemmrb" + String.valueOf(a), minute.floatValue());
                        this.edit.putFloat("zonessrb" + String.valueOf(a), second.floatValue());
                        Log.d("hackTSD", SUBTAG + hour + " : " + minute + " : " + second);
                        this.edit.commit();
                    } else {
                        Float t12 = Float.valueOf(Float.valueOf(distance.floatValue() - this.loginpref.getFloat("tcDistanceValue" + String.valueOf(b), 0.0f)).floatValue() / speed.floatValue());
                        Float thh2 = Float.valueOf(t12.floatValue() - (t12.floatValue() % 1.0f));
                        Float t22 = Float.valueOf(t12.floatValue() * 60.0f);
                        Float tmm2 = Float.valueOf((t22.floatValue() - (t22.floatValue() % 1.0f)) - (thh2.floatValue() * 60.0f));
                        Float tss2 = Float.valueOf(Float.valueOf(t22.floatValue() % 1.0f).floatValue() * 60.0f);
                        Float ah2 = Float.valueOf(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(b), 0)));
                        Float am2 = Float.valueOf(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(b), 0)));
                        Float as2 = Float.valueOf(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(b), 0)));
                        Float hour2 = Float.valueOf(thh2.floatValue() + ah2.floatValue());
                        Float minute2 = Float.valueOf(tmm2.floatValue() + am2.floatValue());
                        Float second2 = Float.valueOf(tss2.floatValue() + as2.floatValue());
                        if (second2.floatValue() > 60.0f) {
                            minute2 = Float.valueOf(minute2.floatValue() + 1.0f);
                            second2 = Float.valueOf(second2.floatValue() - 60.0f);
                        }
                        if (minute2.floatValue() > 60.0f) {
                            hour2 = Float.valueOf(hour2.floatValue() + 1.0f);
                            minute2 = Float.valueOf(minute2.floatValue() - 60.0f);
                        }
                        if (hour2.floatValue() > 24.0f) {
                            hour2 = Float.valueOf(hour2.floatValue() - 24.0f);
                        }
                        this.edit.putFloat("zonehhrb" + String.valueOf(a), hour2.floatValue());
                        this.edit.putFloat("zonemmrb" + String.valueOf(a), minute2.floatValue());
                        this.edit.putFloat("zonessrb" + String.valueOf(a), second2.floatValue());
                        Log.d("hackTSD", SUBTAG + hour2 + " : " + minute2 + " : " + second2);
                        this.edit.commit();
                    }
                }
            }
            Log.d("hackTSD", "WorkBook Calculate reached 4...");
            for (int i3 = 1; i3 < j + 1; i3++) {
                if (this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f) < rborb && this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3), 0.0f) >= rborb) {
                    this.edit.putFloat("speedUsedrb", this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(i3), 0.0f));
                    this.edit.putFloat("nextSpeedrb", this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(i3 + 1), 0.0f));
                    this.edit.putFloat("speedChangeDistancerb", this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3), 0.0f));
                    Float ah3 = Float.valueOf(this.loginpref.getFloat("zonehhrb" + String.valueOf(i3), 0.0f));
                    Float am3 = Float.valueOf(this.loginpref.getFloat("zonemmrb" + String.valueOf(i3), 0.0f));
                    Float as3 = Float.valueOf(this.loginpref.getFloat("zonessrb" + String.valueOf(i3), 0.0f));
                    this.edit.putString("speedChangeTimerb", String.valueOf(String.valueOf(Math.round(ah3.floatValue()))) + " : " + String.valueOf(Math.round(am3.floatValue())) + " : " + String.valueOf(Math.round(as3.floatValue())));
                    this.edit.commit();
                    if (rborb == 0.0f) {
                        this.edit.putFloat("hardPointrb", this.loginpref.getFloat("tcDistanceValue" + String.valueOf(1), 0.0f));
                        this.edit.putFloat("huserb", Float.valueOf(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(1), 0))).floatValue());
                        this.edit.putFloat("muserb", Float.valueOf(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(1), 0))).floatValue());
                        this.edit.putFloat("suserb", Float.valueOf(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(1), 0))).floatValue());
                        this.edit.commit();
                    }
                    for (int l = 1; l < k + 1; l++) {
                        if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f) <= rborb && this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f) <= this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f)) {
                            this.edit.putFloat("hardPointtcrb", this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f));
                            this.edit.putFloat("husetcrb", Float.valueOf(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(l), 0))).floatValue());
                            this.edit.putFloat("musetcrb", Float.valueOf(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(l), 0))).floatValue());
                            this.edit.putFloat("susetcrb", Float.valueOf(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(l), 0))).floatValue());
                            this.edit.commit();
                        }
                    }
                    Float q2 = Float.valueOf(this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f));
                    Float qh = Float.valueOf(this.loginpref.getFloat("zonehhrb" + String.valueOf(i3 - 1), 0.0f));
                    Float qm = Float.valueOf(this.loginpref.getFloat("zonemmrb" + String.valueOf(i3 - 1), 0.0f));
                    Float qs = Float.valueOf(this.loginpref.getFloat("zonessrb" + String.valueOf(i3 - 1), 0.0f));
                    this.edit.putFloat("hardPointzrb", q2.floatValue());
                    this.edit.putFloat("husezrb", qh.floatValue());
                    this.edit.putFloat("musezrb", qm.floatValue());
                    this.edit.putFloat("susezrb", qs.floatValue());
                    this.edit.commit();
                }
            }
            if (Float.valueOf(rborb - this.loginpref.getFloat("hardPointzrb", 0.0f)).floatValue() >= Float.valueOf(rborb - this.loginpref.getFloat("hardPointtcrb", 0.0f)).floatValue()) {
                Float hp = Float.valueOf(this.loginpref.getFloat("hardPointtcrb", 0.0f));
                Float hz = Float.valueOf(this.loginpref.getFloat("husetcrb", 0.0f));
                Float mz = Float.valueOf(this.loginpref.getFloat("musetcrb", 0.0f));
                Float sz = Float.valueOf(this.loginpref.getFloat("susetcrb", 0.0f));
                this.edit.putFloat("hardPointrb", hp.floatValue());
                this.edit.putFloat("huserb", hz.floatValue());
                this.edit.putFloat("muserb", mz.floatValue());
                this.edit.putFloat("suserb", sz.floatValue());
                this.edit.commit();
            } else {
                Float hp2 = Float.valueOf(this.loginpref.getFloat("hardPointzrb", 0.0f));
                Float hz2 = Float.valueOf(this.loginpref.getFloat("husezrb", 0.0f));
                Float mz2 = Float.valueOf(this.loginpref.getFloat("musezrb", 0.0f));
                Float sz2 = Float.valueOf(this.loginpref.getFloat("susezrb", 0.0f));
                this.edit.putFloat("hardPointrb", hp2.floatValue());
                this.edit.putFloat("huserb", hz2.floatValue());
                this.edit.putFloat("muserb", mz2.floatValue());
                this.edit.putFloat("suserb", sz2.floatValue());
                this.edit.commit();
            }
            Log.d("hackTSD", SUBTAG + this.loginpref.getFloat("mindiffZDV", 0.0f) + "   mindiffZDV");
            Log.d("hackTSD", "WorkBook Calculate reached 7...");
            Float hardpoint = Float.valueOf(this.loginpref.getFloat("hardPointrb", 0.0f));
            Float speedlimit2 = Float.valueOf(this.loginpref.getFloat("speedUsedrb", 0.0f));
            Float nspeed2 = Float.valueOf(this.loginpref.getFloat("nextSpeedrb", 0.0f));
            Float changedistance2 = Float.valueOf(this.loginpref.getFloat("speedChangeDistancerb", 0.0f));
            this.currentspeedrb.setText(String.valueOf(speedlimit2));
            this.changerb.setText(String.valueOf(changedistance2));
            this.nextspeedrb.setText(String.valueOf(nspeed2));
            Log.d("hackTSD", "WorkBook Hard Point : " + hardpoint + "    " + "speedlimit : " + speedlimit2);
            Float del = Float.valueOf(rborb - hardpoint.floatValue());
            Log.d("hackTSD", "WorkBook del : " + del);
            Float t11 = Float.valueOf(del.floatValue() / speedlimit2.floatValue());
            Float th = Float.valueOf(t11.floatValue() - (t11.floatValue() % 1.0f));
            Float th2 = Float.valueOf(th.floatValue() - (th.floatValue() % 1.0f));
            Float t222 = Float.valueOf(t11.floatValue() * 60.0f);
            Float tm = Float.valueOf((t222.floatValue() - (t222.floatValue() % 1.0f)) - (th2.floatValue() * 60.0f));
            Float tm2 = Float.valueOf(tm.floatValue() - (tm.floatValue() % 1.0f));
            Float ts = Float.valueOf(Float.valueOf(t222.floatValue() % 1.0f).floatValue() * 60.0f);
            Float ts2 = Float.valueOf(ts.floatValue() - (ts.floatValue() % 1.0f));
            Log.d("hackTSD", "WorkBook Calculate reached 10...");
            Float bh = Float.valueOf(this.loginpref.getFloat("huserb", 0.0f));
            Float bh2 = Float.valueOf(bh.floatValue() - (bh.floatValue() % 1.0f));
            Float bm = Float.valueOf(this.loginpref.getFloat("muserb", 0.0f));
            Float bm2 = Float.valueOf(bm.floatValue() - (bm.floatValue() % 1.0f));
            Float bs = Float.valueOf(this.loginpref.getFloat("suserb", 0.0f));
            Float bs2 = Float.valueOf(bs.floatValue() - (bs.floatValue() % 1.0f));
            int houra2 = Math.round(th2.floatValue() + bh2.floatValue());
            int minutea2 = Math.round(tm2.floatValue() + bm2.floatValue());
            int seconda2 = Math.round(ts2.floatValue() + bs2.floatValue());
            if (seconda2 > 60) {
                minutea2++;
                seconda2 -= 60;
            }
            if (minutea2 > 60) {
                houra2++;
                minutea2 -= 60;
            }
            if (houra2 > 24) {
                houra2 -= 24;
            }
            Log.d("hackTSD", "WorkBook actual time" + houra2 + " : " + minutea2 + " : " + seconda2);
            this.hhrb.setText(String.valueOf(houra2));
            this.mmrb.setText(String.valueOf(minutea2));
            this.ssrb.setText(String.valueOf(seconda2));
            this.changetimerb.setText(this.loginpref.getString("speedChangeTimerb", ""));
            this.rbodorb.setText(String.valueOf(rborb));
        } catch (Exception e) {
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        try {
            super.onStart();
            if (!this.mLocationManager.isProviderEnabled("gps")) {
                Toast.makeText(this.context, "GPS Not Running !", Toast.LENGTH_LONG).show();
            }
            if (!isServiceRunning()) {
                Log.d("hackTSD", "WorkBook Staring Location Update service.");
                startService(new Intent(this.context, GPSTracker.class));
            }
            calculate();
            parseData();
            callAsynchronousTask();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return true;
    }

    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.transition:
            case R.id.action_speed:
                this.doAsynchronousTask.cancel();
                this.timer.cancel();
                this.timer.purge();
                Intent j = new Intent(this, EnterSpeedChart.class);
                j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(j);
                break;
            case R.id.edit:
            case R.id.action_tc:
                this.doAsynchronousTask.cancel();
                this.timer.cancel();
                this.timer.purge();
                Intent i = new Intent(this, EnterTc.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.action_about:
                this.doAsynchronousTask.cancel();
                this.timer.cancel();
                this.timer.purge();
                startActivity(new Intent(this, About.class));
                break;
            case R.id.action_exit:
                this.doAsynchronousTask.cancel();
                this.timer.cancel();
                this.timer.purge();
                stopService(new Intent(this.context, GPSTracker.class));
                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.action_calculation:
                this.doAsynchronousTask.cancel();
                this.timer.cancel();
                this.timer.purge();
                Intent i4 = new Intent(this, Calculation.class);
                i4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i4);
                break;
            case R.id.action_penalty:
                this.doAsynchronousTask.cancel();
                this.timer.cancel();
                this.timer.purge();
                Intent i5 = new Intent(this, Penalty.class);
                i5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i5);
                break;
        }
        return true;
    }

    /* renamed from: go */
    public void mo4628go() {
        try {
            String j = this.gotovalue;
            if (Integer.valueOf(j).intValue() == 1) {
            }
            this.zonerb.setText(String.valueOf(j));
            calculaterb();
            if (j.length() == 0) {
                Toast.makeText(this.context, "Enter a Real Value !", Toast.LENGTH_LONG).show();
            } else if (Integer.valueOf(j).intValue() <= 0) {
                Toast.makeText(this.context, "Enter a Real Value !", Toast.LENGTH_LONG).show();
            } else if (this.loginpref.getFloat("rbDistanceValue" + j, 0.0f) > 0.0f) {
                this.zonerb.setText(j);
                calculaterb();
            } else {
                Toast.makeText(this.context, "No Value at this Tulip !", Toast.LENGTH_LONG).show();
            }
            this.edit.putString("gotovalue", "1");
            this.edit.commit();
        } catch (Exception e) {
        }
    }

    public void last() {
        int j = 0;
        for (int i = 1; i < 1000; i++) {
            if (this.loginpref.getFloat("rbDistanceValue" + String.valueOf(i), 0.0f) > 0.0f) {
                j++;
            }
        }
        try {
            this.zonerb.setText(String.valueOf(j));
            calculaterb();
        } catch (Exception e) {
        }
    }

    public void first() {
        try {
            this.zonerb.setText(String.valueOf(1));
            calculaterb();
        } catch (Exception e) {
        }
    }

    public void back() {
        try {
            int i = Integer.valueOf(this.zonerb.getText().toString()).intValue();
            if (i != 1) {
                if (i == 2) {
                }
                this.zonerb.setText(String.valueOf(i - 1));
                calculaterb();
                if (i > 2) {
                    this.zonerb.setText(String.valueOf(i - 1));
                    calculaterb();
                    return;
                }
                Toast.makeText(this.context, "Can't go further back !", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(this.context, "Can't go further back !", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

    public void next() {
        try {
            int i = Integer.valueOf(this.zonerb.getText().toString()).intValue();
            if (this.loginpref.getFloat("rbDistanceValue" + String.valueOf(i + 1), 0.0f) > 0.0f) {
                this.zonerb.setText(String.valueOf(i + 1));
                calculaterb();
                return;
            }
            Toast.makeText(this.context, "Can't go further !", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

    class CheckUserRequestStatus extends AsyncTask<String, String, String> {
        CheckUserRequestStatus() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(String... args) {
            try {
                WorkBook.this.calculate();
                return null;
            } catch (NullPointerException e) {
                e.printStackTrace();
                return null;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String file_url) {
            WorkBook.this.parseData();
        }
    }
}
