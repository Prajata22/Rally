package com.applex.rally;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WorkRoadBook extends AppCompatActivity implements ActionBar.OnNavigationListener {
    public static final String TAG = "hackTSD";
    private static final String SUBTAG = "Enter Speed Chart: ";
    /* access modifiers changed from: private */
    public Context context = this;
    /* access modifiers changed from: private */
    public TextView rbodo;
    /* access modifiers changed from: private */
    public TextView zone;
    Float diff;
    Float difftc;
    SharedPreferences.Editor edit;
    int hourb;
    /* renamed from: l */
    int f643l;
    SharedPreferences loginpref;
    /* renamed from: m */
    int f644m;
    int minuteb;
    float rbo;
    int secondb;
    /* renamed from: x */
    Float f648x;
    /* renamed from: x1 */
    Float f649x1;
    /* renamed from: y */
    Float f650y;
    /* renamed from: y1 */
    Float f651y1;
    /* renamed from: z */
    int f652z;
    private Button back;
    private TextView carodo;
    private TextView change;
    private TextView changetime;
    private TextView currentspeed;
    private EditText enterSpeedZone;
    private Button first;
    /* renamed from: go */
    private ImageButton f641go;
    private TextView gpsodo;
    /* renamed from: hh */
    private TextView f642hh;
    private Button last;
    /* renamed from: mm */
    private TextView f645mm;
    private Button next;
    private TextView nextspeed;
    private View.OnClickListener onClickListener;
    private Button save;
    /* renamed from: ss */
    private TextView f646ss;
    /* renamed from: tc */
    private Button f647tc;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workbooklite);
        Log.d("hackTSD", "Enter Speed Chart: Launching findViews()...");
        findViews();
        Log.d("hackTSD", "Enter Speed Chart: Launching setListeners()...");
        setListeners();
        Log.d("hackTSD", "Enter Speed Chart: Listener Over...");
        this.zone.setText("1");
        Log.d("hackTSD", "Enter Speed Chart: Launching calculate()...");
        calculate();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.i("hackTSD", "Enter Speed Chart: onPause");
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Log.i("hackTSD", "Enter Speed Chart: onResume");
        super.onResume();
    }

    private void findViews() {
        this.first = (Button) findViewById(R.id.first);
        this.last = (Button) findViewById(R.id.last);
        this.next = (Button) findViewById(R.id.next);
        this.back = (Button) findViewById(R.id.back);
        this.save = (Button) findViewById(R.id.save);
        this.f647tc = (Button) findViewById(R.id.tc);
        this.f641go = (ImageButton) findViewById(R.id.go);
        this.f642hh = (TextView) findViewById(R.id.hh_tv);
        this.f645mm = (TextView) findViewById(R.id.mm_tv);
        this.f646ss = (TextView) findViewById(R.id.ss_tv);
        this.rbodo = (TextView) findViewById(R.id.rbodo_tv);
        this.gpsodo = (TextView) findViewById(R.id.gpsodo_tv);
        this.carodo = (TextView) findViewById(R.id.carodo_tv);
        this.currentspeed = (TextView) findViewById(R.id.currentspeed_tv);
        this.nextspeed = (TextView) findViewById(R.id.nextspeed_tv);
        this.change = (TextView) findViewById(R.id.change_tv);
        this.changetime = (TextView) findViewById(R.id.changetime_tv);
        this.enterSpeedZone = (EditText) findViewById(R.id.zone_number);
        this.zone = (TextView) findViewById(R.id.zone_tv);
    }

    private void setListeners() {
        Log.d("hackTSD", "Enter Speed Chart: setListeners()");
        Log.d("hackTSD", "Enter Speed Chart: Setting onClickListener...");
        this.onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                WorkRoadBook.this.loginpref = WorkRoadBook.this.getSharedPreferences("APPLICATION_PREFERENCE", 0);
                WorkRoadBook.this.edit = WorkRoadBook.this.loginpref.edit();
                switch (v.getId()) {
                    case R.id.go:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Booking");
                        WorkRoadBook.this.mo4571go();
                        return;
                    case R.id.save:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Need Ride");
                        WorkRoadBook.this.loginpref = WorkRoadBook.this.getSharedPreferences("APPLICATION_PREFERENCE", 0);
                        WorkRoadBook.this.edit = WorkRoadBook.this.loginpref.edit();
                        WorkRoadBook.this.edit.putString("correctionAtTulip", WorkRoadBook.this.zone.getText().toString());
                        WorkRoadBook.this.edit.putString("correctionAtRBOdo", WorkRoadBook.this.rbodo.getText().toString());
                        WorkRoadBook.this.edit.commit();
                        WorkRoadBook.this.startActivity(new Intent(WorkRoadBook.this.context, Correction.class));
                        return;
                    case R.id.first:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Booking");
                        WorkRoadBook.this.first();
                        return;
                    case R.id.last:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Booking");
                        WorkRoadBook.this.last();
                        return;
                    case R.id.back:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Need Ride");
                        WorkRoadBook.this.back();
                        return;
                    case R.id.next:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Need Ride");
                        WorkRoadBook.this.next();
                        return;
                    case R.id.tc:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Booking");
                        WorkRoadBook.this.startActivity(new Intent(WorkRoadBook.this.context, EnterTc.class));
                        return;
                    default:
                        return;
                }
            }
        };
        Log.d("hackTSD", "Enter Speed Chart: Setting setOnClickListener for buttons");
        this.first.setOnClickListener(this.onClickListener);
        this.last.setOnClickListener(this.onClickListener);
        this.next.setOnClickListener(this.onClickListener);
        this.back.setOnClickListener(this.onClickListener);
        this.f641go.setOnClickListener(this.onClickListener);
        this.save.setOnClickListener(this.onClickListener);
        this.f647tc.setOnClickListener(this.onClickListener);
        Log.d("hackTSD", "Enter Speed Chart: Setting setOnClickListener for Buttons Completed ");
    }

    /* renamed from: go */
    public void mo4571go() {
        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        String j = this.enterSpeedZone.getText().toString();
        if (Integer.valueOf(j).intValue() == 1) {
        }
        this.zone.setText(String.valueOf(j));
        this.enterSpeedZone.setText("");
        calculate();
        if (j.length() == 0) {
            Toast.makeText(this.context, "Enter a Real Value !", Toast.LENGTH_LONG).show();
        } else if (Integer.valueOf(j).intValue() <= 0) {
            Toast.makeText(this.context, "Enter a Real Value !", Toast.LENGTH_LONG).show();
        } else if (this.loginpref.getFloat("rbDistanceValue" + j, 0.0f) > 0.0f) {
            this.zone.setText(j);
            calculate();
            this.enterSpeedZone.setText("");
        } else {
            Toast.makeText(this.context, "No Value at this Tulip !", Toast.LENGTH_LONG).show();
        }
    }

    public void calculate() {
        Log.d("hackTSD", "Enter Speed Chart: Calculate Start...");
        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        this.f652z = Integer.valueOf(this.zone.getText().toString()).intValue();
        this.rbo = this.loginpref.getFloat("rbDistanceValue" + String.valueOf(this.f652z), 0.0f);
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 1...");
        int j = 0;
        for (int i = 1; i < 1000; i++) {
            if (this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i), 0.0f) > 0.0f) {
                j++;
            }
        }
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 2...");
        int k = 1;
        for (int i2 = 1; i2 < 1000; i2++) {
            if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i2), 0.0f) > 0.0f) {
                k++;
            }
        }
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 3...");
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
                    this.edit.putFloat("zonehh" + String.valueOf(a), hour2.floatValue());
                    this.edit.putFloat("zonemm" + String.valueOf(a), minute2.floatValue());
                    this.edit.putFloat("zoness" + String.valueOf(a), second2.floatValue());
                    Log.d("hackTSD", SUBTAG + hour2 + " : " + minute2 + " : " + second2);
                    this.edit.commit();
                }
            }
        }
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 4...");
        for (int i3 = 1; i3 < j + 1; i3++) {
            if (this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f) < this.rbo && this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3), 0.0f) >= this.rbo) {
                this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
                this.edit = this.loginpref.edit();
                this.edit.putFloat("speedUsed", this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(i3), 0.0f));
                this.edit.putFloat("nextSpeed", this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(i3 + 1), 0.0f));
                this.edit.putFloat("speedChangeDistance", this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3), 0.0f));
                Float ah3 = Float.valueOf(this.loginpref.getFloat("zonehh" + String.valueOf(i3), 0.0f));
                Float am3 = Float.valueOf(this.loginpref.getFloat("zonemm" + String.valueOf(i3), 0.0f));
                Float as3 = Float.valueOf(this.loginpref.getFloat("zoness" + String.valueOf(i3), 0.0f));
                this.edit.putString("speedChangeTime", String.valueOf(String.valueOf(Math.round(ah3.floatValue()))) + " : " + String.valueOf(Math.round(am3.floatValue())) + " : " + String.valueOf(Math.round(as3.floatValue())));
                this.edit.commit();
                if (this.rbo == 0.0f) {
                    this.edit.putFloat("hardPoint", this.loginpref.getFloat("tcDistanceValue" + String.valueOf(1), 0.0f));
                    this.edit.putFloat("huse", Float.valueOf(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(1), 0))).floatValue());
                    this.edit.putFloat("muse", Float.valueOf(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(1), 0))).floatValue());
                    this.edit.putFloat("suse", Float.valueOf(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(1), 0))).floatValue());
                    this.edit.commit();
                }
                for (int l = 1; l < k + 1; l++) {
                    if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f) <= this.rbo && this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f) <= this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f)) {
                        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
                        this.edit = this.loginpref.edit();
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
        Float diffz = Float.valueOf(this.rbo - this.loginpref.getFloat("hardPointz", 0.0f));
        Float difftc2 = Float.valueOf(this.rbo - this.loginpref.getFloat("hardPointtc", 0.0f));
        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        if (diffz.floatValue() >= difftc2.floatValue()) {
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
            Float hz2 = Float.valueOf(this.loginpref.getFloat("husez", 0.0f));
            Float mz2 = Float.valueOf(this.loginpref.getFloat("musez", 0.0f));
            Float sz2 = Float.valueOf(this.loginpref.getFloat("susez", 0.0f));
            this.edit.putFloat("hardPoint", hp2.floatValue());
            this.edit.putFloat("huse", hz2.floatValue());
            this.edit.putFloat("muse", mz2.floatValue());
            this.edit.putFloat("suse", sz2.floatValue());
            this.edit.commit();
        }
        Log.d("hackTSD", SUBTAG + this.loginpref.getFloat("mindiffZDV", 0.0f) + "   mindiffZDV");
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 7...");
        Float hardpoint = Float.valueOf(this.loginpref.getFloat("hardPoint", 0.0f));
        Float speedlimit = Float.valueOf(this.loginpref.getFloat("speedUsed", 0.0f));
        Float nspeed = Float.valueOf(this.loginpref.getFloat("nextSpeed", 0.0f));
        Float changedistance = Float.valueOf(this.loginpref.getFloat("speedChangeDistance", 0.0f));
        this.currentspeed.setText(String.valueOf(speedlimit));
        this.change.setText(String.valueOf(changedistance));
        this.nextspeed.setText(String.valueOf(nspeed));
        Log.d("hackTSD", "Enter Speed Chart: Hard Point : " + hardpoint + "    " + "speedlimit : " + speedlimit);
        Float del = Float.valueOf(this.rbo - hardpoint.floatValue());
        Log.d("hackTSD", "Enter Speed Chart: del : " + del);
        Float t11 = Float.valueOf(del.floatValue() / speedlimit.floatValue());
        Float th = Float.valueOf(t11.floatValue() - (t11.floatValue() % 1.0f));
        Float th2 = Float.valueOf(th.floatValue() - (th.floatValue() % 1.0f));
        Float t222 = Float.valueOf(t11.floatValue() * 60.0f);
        Float tm = Float.valueOf((t222.floatValue() - (t222.floatValue() % 1.0f)) - (th2.floatValue() * 60.0f));
        Float tm2 = Float.valueOf(tm.floatValue() - (tm.floatValue() % 1.0f));
        Float ts = Float.valueOf(Float.valueOf(t222.floatValue() % 1.0f).floatValue() * 60.0f);
        Float ts2 = Float.valueOf(ts.floatValue() - (ts.floatValue() % 1.0f));
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 10...");
        Float bh = Float.valueOf(this.loginpref.getFloat("huse", 0.0f));
        Float bh2 = Float.valueOf(bh.floatValue() - (bh.floatValue() % 1.0f));
        Float bm = Float.valueOf(this.loginpref.getFloat("muse", 0.0f));
        Float bm2 = Float.valueOf(bm.floatValue() - (bm.floatValue() % 1.0f));
        Float bs = Float.valueOf(this.loginpref.getFloat("suse", 0.0f));
        Float bs2 = Float.valueOf(bs.floatValue() - (bs.floatValue() % 1.0f));
        int houra = Math.round(th2.floatValue() + bh2.floatValue());
        int minutea = Math.round(tm2.floatValue() + bm2.floatValue());
        int seconda = Math.round(ts2.floatValue() + bs2.floatValue());
        if (seconda > 60) {
            minutea++;
            seconda -= 60;
        }
        if (minutea > 60) {
            houra++;
            minutea -= 60;
        }
        if (houra > 24) {
            houra -= 24;
        }
        Log.d("hackTSD", "Enter Speed Chart: actual time" + houra + " : " + minutea + " : " + seconda);
        this.f642hh.setText(String.valueOf(houra));
        this.f645mm.setText(String.valueOf(minutea));
        this.f646ss.setText(String.valueOf(seconda));
        Float gpsCorrection = Float.valueOf(this.loginpref.getFloat("correctGpsOdo", 0.0f));
        Float carCorrection = Float.valueOf(this.loginpref.getFloat("correctCarOdo", 0.0f));
        this.changetime.setText(this.loginpref.getString("speedChangeTime", ""));
        this.rbodo.setText(String.valueOf(this.rbo));
        if (String.valueOf(this.loginpref.getFloat("correctCarOdo", 0.0f)).length() != 0) {
            this.carodo.setText(String.valueOf(this.rbo + carCorrection.floatValue()));
        } else {
            this.carodo.setText(String.valueOf(this.rbo));
        }
        if (String.valueOf(this.loginpref.getFloat("correctGpsOdo", 0.0f)).length() != 0) {
            this.gpsodo.setText(String.valueOf(this.rbo + gpsCorrection.floatValue()));
        } else {
            this.gpsodo.setText(String.valueOf(this.rbo));
        }
    }

    public void last() {
        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        int j = 0;
        for (int i = 1; i < 1000; i++) {
            if (this.loginpref.getFloat("rbDistanceValue" + String.valueOf(i), 0.0f) > 0.0f) {
                j++;
            }
        }
        this.zone.setText(String.valueOf(j));
        calculate();
        this.enterSpeedZone.setText("");
    }

    public void first() {
        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        this.zone.setText(String.valueOf(1));
        calculate();
        this.enterSpeedZone.setText("");
    }

    public void back() {
        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        int i = Integer.valueOf(this.zone.getText().toString()).intValue();
        if (i != 1) {
            if (i == 2) {
            }
            this.zone.setText(String.valueOf(i - 1));
            calculate();
            this.enterSpeedZone.setText("");
            if (i > 2) {
                this.zone.setText(String.valueOf(i - 1));
                calculate();
                this.enterSpeedZone.setText("");
                return;
            }
            Toast.makeText(this.context, "Can't go further back !", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this.context, "Can't go further back !", Toast.LENGTH_LONG).show();
    }

    public void next() {
        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        int i = Integer.valueOf(this.zone.getText().toString()).intValue();
        if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i + 1), 0.0f) > 0.0f) {
            this.zone.setText(String.valueOf(i + 1));
            calculate();
            this.enterSpeedZone.setText("");
            return;
        }
        this.zone.setText(String.valueOf(i + 1));
        calculate();
        this.enterSpeedZone.setText("");
    }

    public void onBackPressed() {
        Log.i("hackTSD", "Enter Speed Chart: onPause");
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public void calculatePenalty() {
    }

    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions1, menu);
        menu.removeItem(R.id.edit);
        menu.removeItem(R.id.transition);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.getItemId();
        return true;
    }

    public void calculateP() {
        Log.d("hackTSD", "Enter Speed Chart: Calculate Start...");
        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 1...");
        int j = 0;
        for (int i = 1; i < 1000; i++) {
            if (this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i), 0.0f) > 0.0f) {
                j++;
            }
        }
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 2...");
        int k = 1;
        for (int i2 = 1; i2 < 1000; i2++) {
            if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i2), 0.0f) > 0.0f) {
                k++;
            }
        }
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 3...");
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
                    if (second.floatValue() >= 60.0f) {
                        minute = Float.valueOf(minute.floatValue() + 1.0f);
                        second = Float.valueOf(second.floatValue() - 60.0f);
                    }
                    if (minute.floatValue() >= 60.0f) {
                        hour = Float.valueOf(hour.floatValue() + 1.0f);
                        minute = Float.valueOf(minute.floatValue() - 60.0f);
                    }
                    if (hour.floatValue() >= 24.0f) {
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
                    this.edit.putFloat("zonehh" + String.valueOf(a), hour2.floatValue());
                    this.edit.putFloat("zonemm" + String.valueOf(a), minute2.floatValue());
                    this.edit.putFloat("zoness" + String.valueOf(a), second2.floatValue());
                    Log.d("hackTSD", SUBTAG + hour2 + " : " + minute2 + " : " + second2);
                    this.edit.commit();
                }
            }
        }
        for (int i3 = 1; i3 < j + 1; i3++) {
            if (this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f) < this.rbo && this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3), 0.0f) >= this.rbo) {
                this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
                this.edit = this.loginpref.edit();
                this.edit.putFloat("speedUsed", this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(i3), 0.0f));
                this.edit.commit();
                if (this.rbo == 0.0f) {
                    this.edit.putFloat("hardPoint", this.loginpref.getFloat("tcDistanceValue" + String.valueOf(1), 0.0f));
                    this.edit.putFloat("huse", Float.valueOf(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(1), 0))).floatValue());
                    this.edit.putFloat("muse", Float.valueOf(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(1), 0))).floatValue());
                    this.edit.putFloat("suse", Float.valueOf(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(1), 0))).floatValue());
                    this.edit.commit();
                }
                for (int l = 1; l < k + 1; l++) {
                    if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f) <= this.rbo && this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f) <= this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f)) {
                        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
                        this.edit = this.loginpref.edit();
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
        Float diffz = Float.valueOf(this.rbo - this.loginpref.getFloat("hardPointz", 0.0f));
        Float difftc2 = Float.valueOf(this.rbo - this.loginpref.getFloat("hardPointtc", 0.0f));
        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        if (diffz.floatValue() >= difftc2.floatValue()) {
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
            Float hz2 = Float.valueOf(this.loginpref.getFloat("husez", 0.0f));
            Float mz2 = Float.valueOf(this.loginpref.getFloat("musez", 0.0f));
            Float sz2 = Float.valueOf(this.loginpref.getFloat("susez", 0.0f));
            this.edit.putFloat("hardPoint", hp2.floatValue());
            this.edit.putFloat("huse", hz2.floatValue());
            this.edit.putFloat("muse", mz2.floatValue());
            this.edit.putFloat("suse", sz2.floatValue());
            this.edit.commit();
        }
        Float hardpoint = Float.valueOf(this.loginpref.getFloat("hardPoint", 0.0f));
        Float speedlimit = Float.valueOf(this.loginpref.getFloat("speedUsed", 0.0f));
        Log.d("hackTSD", "Enter Speed Chart: Hard Point : " + hardpoint + "    " + "speedlimit : " + speedlimit);
        Float del = Float.valueOf(this.rbo - hardpoint.floatValue());
        Log.d("hackTSD", "Enter Speed Chart: del : " + del);
        Float t11 = Float.valueOf(del.floatValue() / speedlimit.floatValue());
        Float th = Float.valueOf(t11.floatValue() - (t11.floatValue() % 1.0f));
        Float th2 = Float.valueOf(th.floatValue() - (th.floatValue() % 1.0f));
        Float t222 = Float.valueOf(t11.floatValue() * 60.0f);
        Float tm = Float.valueOf((t222.floatValue() - (t222.floatValue() % 1.0f)) - (th2.floatValue() * 60.0f));
        Float tm2 = Float.valueOf(tm.floatValue() - (tm.floatValue() % 1.0f));
        Float ts = Float.valueOf(Float.valueOf(t222.floatValue() % 1.0f).floatValue() * 60.0f);
        Float ts2 = Float.valueOf(ts.floatValue() - (ts.floatValue() % 1.0f));
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 10...");
        Float bh = Float.valueOf(this.loginpref.getFloat("huse", 0.0f));
        Float bh2 = Float.valueOf(bh.floatValue() - (bh.floatValue() % 1.0f));
        Float bm = Float.valueOf(this.loginpref.getFloat("muse", 0.0f));
        Float bm2 = Float.valueOf(bm.floatValue() - (bm.floatValue() % 1.0f));
        Float bs = Float.valueOf(this.loginpref.getFloat("suse", 0.0f));
        Float bs2 = Float.valueOf(bs.floatValue() - (bs.floatValue() % 1.0f));
        this.hourb = Math.round(th2.floatValue() + bh2.floatValue());
        this.minuteb = Math.round(tm2.floatValue() + bm2.floatValue());
        this.secondb = Math.round(ts2.floatValue() + bs2.floatValue());
        if (this.secondb >= 60) {
            this.minuteb++;
            this.secondb -= 60;
        }
        if (this.minuteb >= 60) {
            this.hourb++;
            this.minuteb -= 60;
        }
        if (this.hourb >= 24) {
            this.hourb -= 24;
        }
        Log.d("hackTSD", "Enter Speed Chart: actual time" + this.hourb + " : " + this.minuteb + " : " + this.secondb);
    }
}
