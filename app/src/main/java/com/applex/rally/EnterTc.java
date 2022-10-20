package com.applex.rally;

import android.app.ActionBar;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnterTc extends AppCompatActivity implements ActionBar.OnNavigationListener {
    public static final String TAG = "hackTSD";
    private static final String SUBTAG = "Enter TC: ";
    /* access modifiers changed from: private */
    public Context context = this;
    /* access modifiers changed from: private */
    public EditText distance;
    SharedPreferences.Editor edit;
    int houra;
    SharedPreferences loginpref;
    int minutea;
    Float rbo;
    int seconda;
    private Button back;
    private EditText enterSpeedZone;
    private Button first;
    /* renamed from: go */
    private ImageButton f666go;
    private EditText hhi;
    private EditText hho;
    private Button last;
    private EditText mmi;
    private EditText mmo;
    private Button next;
    private View.OnClickListener onClickListener;
    private Button save;
    private EditText ssi;
    private EditText sso;
    private TextView zone;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc);
        this.loginpref = getSharedPreferences("HACKTSD_APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        getSupportActionBar().setSubtitle((CharSequence) "Enter Time Control info as it comes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("hackTSD", "Enter TC: Launching findViews()...");
        findViews();
        Log.d("hackTSD", "Enter TC: Launching setListeners()...");
        setListeners();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(1, 0);
        last();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.i("hackTSD", "Enter TC: onPause");
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Log.i("hackTSD", "Enter TC: onResume");
        super.onResume();
    }

    private void findViews() {
        this.first = (Button) findViewById(R.id.first);
        this.last = (Button) findViewById(R.id.last);
        this.next = (Button) findViewById(R.id.next);
        this.back = (Button) findViewById(R.id.back);
        this.save = (Button) findViewById(R.id.save);
        this.f666go = (ImageButton) findViewById(R.id.go);
        this.distance = (EditText) findViewById(R.id.distance);
        this.hhi = (EditText) findViewById(R.id.hhi);
        this.mmi = (EditText) findViewById(R.id.mmi);
        this.ssi = (EditText) findViewById(R.id.ssi);
        this.hho = (EditText) findViewById(R.id.hho);
        this.mmo = (EditText) findViewById(R.id.mmo);
        this.sso = (EditText) findViewById(R.id.sso);
        this.enterSpeedZone = (EditText) findViewById(R.id.zone_number);
        this.zone = (TextView) findViewById(R.id.zone_tv);
    }

    private void setListeners() {
        Log.d("hackTSD", "Enter TC: setListeners()");
        Log.d("hackTSD", "Enter TC: Setting onClickListener...");
        this.onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.go:
                        Log.d("hackTSD", "Enter TC: Button Press: Booking");
                        EnterTc.this.mo4595go();
                        return;
                    case R.id.save:
                        Log.d("hackTSD", "Enter TC: Button Press: Need Ride");
                        if (EnterTc.this.distance.getText().toString().length() != 0) {
                            EnterTc.this.save();
                            EnterTc.this.distance.requestFocus();
                            return;
                        }
                        Toast.makeText(EnterTc.this.context, "Enter Valid values !", Toast.LENGTH_LONG).show();
                        return;
                    case R.id.first:
                        Log.d("hackTSD", "Enter TC: Button Press: Booking");
                        EnterTc.this.first();
                        return;
                    case R.id.last:
                        Log.d("hackTSD", "Enter TC: Button Press: Booking");
                        EnterTc.this.last();
                        return;
                    case R.id.back:
                        Log.d("hackTSD", "Enter TC: Button Press: Need Ride");
                        EnterTc.this.back();
                        return;
                    case R.id.next:
                        Log.d("hackTSD", "Enter TC: Button Press: Need Ride");
                        EnterTc.this.next();
                        return;
                    default:
                        return;
                }
            }
        };
        Log.d("hackTSD", "Enter TC: Setting setOnClickListener for buttons");
        this.first.setOnClickListener(this.onClickListener);
        this.last.setOnClickListener(this.onClickListener);
        this.next.setOnClickListener(this.onClickListener);
        this.back.setOnClickListener(this.onClickListener);
        this.f666go.setOnClickListener(this.onClickListener);
        this.save.setOnClickListener(this.onClickListener);
        Log.d("hackTSD", "Enter TC: Setting setOnClickListener for Buttons Completed ");
    }

    /* renamed from: go */
    public void mo4595go() {
        try {
            String j = this.enterSpeedZone.getText().toString();
            if (Integer.valueOf(j).intValue() == 1) {
            }
            this.zone.setText(String.valueOf(j));
            this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + String.valueOf(j), 0.0f)));
            this.enterSpeedZone.setText("");
            this.hhi.setText(String.valueOf(this.loginpref.getInt("tchhi" + String.valueOf(j), 0)));
            this.mmi.setText(String.valueOf(this.loginpref.getInt("tcmmi" + String.valueOf(j), 0)));
            this.ssi.setText(String.valueOf(this.loginpref.getInt("tcssi" + String.valueOf(j), 0)));
            this.hho.setText(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(j), 0)));
            this.mmo.setText(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(j), 0)));
            this.sso.setText(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(j), 0)));
            if (j.length() == 0) {
                Toast.makeText(this.context, "Enter a Real Value !", Toast.LENGTH_LONG).show();
            } else if (Integer.valueOf(j).intValue() <= 0) {
                Toast.makeText(this.context, "Enter a Real Value !", Toast.LENGTH_LONG).show();
            } else if (this.loginpref.getFloat("tcDistanceValue" + j, 0.0f) > 0.0f) {
                this.zone.setText(j);
                this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + j, 0.0f)));
                this.enterSpeedZone.setText("");
                this.hhi.setText(String.valueOf(this.loginpref.getInt("tchhi" + String.valueOf(j), 0)));
                this.mmi.setText(String.valueOf(this.loginpref.getInt("tcmmi" + String.valueOf(j), 0)));
                this.ssi.setText(String.valueOf(this.loginpref.getInt("tcssi" + String.valueOf(j), 0)));
                this.hho.setText(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(j), 0)));
                this.mmo.setText(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(j), 0)));
                this.sso.setText(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(j), 0)));
            } else {
                Toast.makeText(this.context, "No Value at this Tc !", Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a Real Value !", Toast.LENGTH_LONG).show();
        } catch (Exception e2) {
        }
    }

    public void last() {
        int j = 1;
        for (int i = 1; i < 1000; i++) {
            if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i), 0.0f) > 0.0f) {
                j++;
            }
        }
        if (String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(1), 0)).length() != 0) {
            this.zone.setText(String.valueOf(j + 1));
            this.distance.setText("");
            this.enterSpeedZone.setText("");
            this.hhi.setText("");
            this.mmi.setText("");
            this.ssi.setText("");
            this.hho.setText("");
            this.mmo.setText("");
            this.sso.setText("");
            return;
        }
        this.zone.setText(String.valueOf(1));
        this.distance.setText("");
        this.enterSpeedZone.setText("");
        this.hhi.setText("");
        this.mmi.setText("");
        this.ssi.setText("");
        this.hho.setText("");
        this.mmo.setText("");
        this.sso.setText("");
    }

    public void first() {
        this.zone.setText(String.valueOf(1));
        this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + String.valueOf(1), 0.0f)));
        this.enterSpeedZone.setText("");
        this.hhi.setText(String.valueOf(this.loginpref.getInt("tchhi" + String.valueOf(1), 0)));
        this.mmi.setText(String.valueOf(this.loginpref.getInt("tcmmi" + String.valueOf(1), 0)));
        this.ssi.setText(String.valueOf(this.loginpref.getInt("tcssi" + String.valueOf(1), 0)));
        this.hho.setText(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(1), 0)));
        this.mmo.setText(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(1), 0)));
        this.sso.setText(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(1), 0)));
    }

    public void back() {
        int i = Integer.valueOf(this.zone.getText().toString()).intValue();
        if (i != 1) {
            if (i == 2) {
            }
            this.zone.setText(String.valueOf(i - 1));
            this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i - 1), 0.0f)));
            this.enterSpeedZone.setText("");
            this.hhi.setText(String.valueOf(this.loginpref.getInt("tchhi" + String.valueOf(i - 1), 0)));
            this.mmi.setText(String.valueOf(this.loginpref.getInt("tcmmi" + String.valueOf(i - 1), 0)));
            this.ssi.setText(String.valueOf(this.loginpref.getInt("tcssi" + String.valueOf(i - 1), 0)));
            this.hho.setText(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(i - 1), 0)));
            this.mmo.setText(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(i - 1), 0)));
            this.sso.setText(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(i - 1), 0)));
            if (i <= 2) {
                Toast.makeText(this.context, "Can't go further back !", Toast.LENGTH_LONG).show();
            } else if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i - 1), 0.0f) > 0.0f) {
                this.zone.setText(String.valueOf(i - 1));
                this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i - 1), 0.0f)));
                this.enterSpeedZone.setText("");
                this.hhi.setText(String.valueOf(this.loginpref.getInt("tchhi" + String.valueOf(i - 1), 0)));
                this.mmi.setText(String.valueOf(this.loginpref.getInt("tcmmi" + String.valueOf(i - 1), 0)));
                this.ssi.setText(String.valueOf(this.loginpref.getInt("tcssi" + String.valueOf(i - 1), 0)));
                this.hho.setText(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(i - 1), 0)));
                this.mmo.setText(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(i - 1), 0)));
                this.sso.setText(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(i - 1), 0)));
            } else {
                Toast.makeText(this.context, "Press Last for last entered Speed Zone !", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this.context, "Can't go further back !", Toast.LENGTH_LONG).show();
        }
    }

    public void next() {
        int i = Integer.valueOf(this.zone.getText().toString()).intValue();
        if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i + 1), 0.0f) > 0.0f) {
            this.zone.setText(String.valueOf(i + 1));
            this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i + 1), 0.0f)));
            this.hhi.setText(String.valueOf(this.loginpref.getInt("tchhi" + String.valueOf(i + 1), 0)));
            this.mmi.setText(String.valueOf(this.loginpref.getInt("tcmmi" + String.valueOf(i + 1), 0)));
            this.ssi.setText(String.valueOf(this.loginpref.getInt("tcssi" + String.valueOf(i + 1), 0)));
            this.hho.setText(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(i + 1), 0)));
            this.mmo.setText(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(i + 1), 0)));
            this.sso.setText(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(i + 1), 0)));
            this.enterSpeedZone.setText("");
        } else if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i + 1), 0.0f) == 0.0f && this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i), 0.0f) == 0.0f) {
            Toast.makeText(this.context, "Can't go further !", Toast.LENGTH_LONG).show();
        } else {
            this.zone.setText(String.valueOf(i + 1));
            this.distance.setText("");
            this.enterSpeedZone.setText("");
            this.hhi.setText("");
            this.mmi.setText("");
            this.ssi.setText("");
            this.hho.setText("");
            this.mmo.setText("");
            this.sso.setText("");
        }
    }

    public void save() {
        try {
            int i = Integer.valueOf(this.zone.getText().toString()).intValue();
            int h1 = Integer.valueOf(this.hhi.getText().toString()).intValue();
            int m1 = Integer.valueOf(this.mmi.getText().toString()).intValue();
            int s1 = Integer.valueOf(this.ssi.getText().toString()).intValue();
            int h2 = Integer.valueOf(this.hho.getText().toString()).intValue();
            int m2 = Integer.valueOf(this.mmo.getText().toString()).intValue();
            int s2 = Integer.valueOf(this.sso.getText().toString()).intValue();
            Float d = Float.valueOf(this.distance.getText().toString());
            this.edit.putFloat("tcDistanceValue" + String.valueOf(i), d.floatValue());
            this.edit.putFloat("distanceTravelled", d.floatValue());
            this.edit.putInt("tchhi" + String.valueOf(i), h1);
            this.edit.putInt("tcmmi" + String.valueOf(i), m1);
            this.edit.putInt("tcssi" + String.valueOf(i), s1);
            this.edit.putInt("tchho" + String.valueOf(i), h2);
            this.edit.putInt("tcmmo" + String.valueOf(i), m2);
            this.edit.putInt("tcsso" + String.valueOf(i), s2);
            this.edit.commit();
            penalty();
            this.zone.setText(String.valueOf(i + 1));
            this.distance.setText("");
            this.enterSpeedZone.setText("");
            this.hhi.setText("");
            this.mmi.setText("");
            this.ssi.setText("");
            this.hho.setText("");
            this.mmo.setText("");
            this.sso.setText("");
        } catch (Exception e) {
        }
    }

    public void penalty() {
        this.edit.putInt("penaltyht" + String.valueOf(1), 0);
        this.edit.putInt("penaltymt" + String.valueOf(1), 0);
        this.edit.putInt("penaltyst" + String.valueOf(1), 0);
        this.edit.putInt("penaltyh" + String.valueOf(1), 0);
        this.edit.putInt("penaltym" + String.valueOf(1), 0);
        this.edit.putInt("penaltys" + String.valueOf(1), 0);
        this.edit.commit();
        int k = 1;
        for (int i = 1; i < 1000; i++) {
            if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i), 0.0f) > 0.0f) {
                k++;
            }
        }
        int i2 = Integer.valueOf(this.zone.getText().toString()).intValue();
        this.rbo = Float.valueOf(this.loginpref.getFloat("tcDistanceValue" + String.valueOf(i2), 0.0f));
        Log.d("hackTSD", "Enter TC: Penalty calculation for Tc Value " + i2 + "start");
        calculatePenalty();
        Log.d("hackTSD", "Enter TC: Penalty calculation for Tc Value " + i2 + "end");
        int hp = this.loginpref.getInt("tchhi" + String.valueOf(i2), 0) - this.houra;
        int mp = this.loginpref.getInt("tcmmi" + String.valueOf(i2), 0) - this.minutea;
        int sp = this.loginpref.getInt("tcssi" + String.valueOf(i2), 0) - this.seconda;
        Log.d("hackTSD", "Enter TC: Penalty is " + hp + " : " + mp + " : " + sp);
        if (hp == 0) {
            if (mp == 0) {
                if (sp != 0 && sp <= 0 && sp < 0 && (sp = sp * -1 * 2) >= 60) {
                    sp -= 60;
                    mp++;
                }
            } else if (mp > 0) {
                if (sp != 0 && sp <= 0 && sp < 0) {
                    sp += 60;
                    mp--;
                }
            } else if (mp < 0) {
                if (sp == 0) {
                    mp = mp * -1 * 2;
                    if (mp >= 60) {
                        mp -= 60;
                        hp++;
                    }
                } else if (sp > 0) {
                    mp = (mp + 1) * -1 * 2;
                    sp = (60 - sp) * 2;
                    if (sp >= 60) {
                        sp -= 60;
                        mp++;
                    }
                    if (mp >= 60) {
                        mp -= 60;
                        hp++;
                    }
                } else if (sp < 0) {
                    int mp2 = mp * -1 * 2;
                    int sp2 = sp * -1 * 2;
                    if (sp2 >= 60) {
                        sp2 -= 60;
                        mp2++;
                    }
                    if (mp >= 60) {
                        mp -= 60;
                        hp++;
                    }
                }
            }
        }
        if (hp > 0) {
            if (mp == 0) {
                if (sp != 0 && sp <= 0 && sp < 0) {
                    sp += 60;
                    mp = 59;
                    hp--;
                }
            } else if (mp > 0) {
                if (sp != 0 && sp <= 0 && sp < 0) {
                    sp += 60;
                    mp--;
                }
            } else if (mp < 0) {
                if (sp == 0) {
                    mp += 60;
                    hp--;
                } else if (sp > 0) {
                    mp += 60;
                    hp--;
                } else if (sp < 0) {
                    sp += 60;
                    mp = (mp + 60) - 1;
                    hp--;
                }
            }
        }
        if (hp < 0) {
            hp = 0;
            sp = 0;
            mp = 0;
        }
        this.edit.putInt("penaltyh" + String.valueOf(Integer.valueOf(this.zone.getText().toString())), hp);
        this.edit.putInt("penaltym" + String.valueOf(Integer.valueOf(this.zone.getText().toString())), mp);
        this.edit.putInt("penaltys" + String.valueOf(Integer.valueOf(this.zone.getText().toString())), sp);
        this.edit.commit();
        int hpt = this.loginpref.getInt("penaltyht" + String.valueOf(Integer.valueOf(this.zone.getText().toString()).intValue() - 1), 0) + hp;
        int mpt = this.loginpref.getInt("penaltymt" + String.valueOf(Integer.valueOf(this.zone.getText().toString()).intValue() - 1), 0) + mp;
        int spt = this.loginpref.getInt("penaltyst" + String.valueOf(Integer.valueOf(this.zone.getText().toString()).intValue() - 1), 0) + sp;
        if (spt >= 60) {
            spt -= 60;
            mpt++;
        }
        if (mpt >= 60) {
            mpt -= 60;
            hpt++;
        }
        Log.d("hackTSD", "Enter TC: Total Penalty is " + hpt + " : " + mpt + " : " + spt);
        this.edit.putInt("penaltyht" + String.valueOf(Integer.valueOf(this.zone.getText().toString())), hpt);
        this.edit.putInt("penaltymt" + String.valueOf(Integer.valueOf(this.zone.getText().toString())), mpt);
        this.edit.putInt("penaltyst" + String.valueOf(Integer.valueOf(this.zone.getText().toString())), spt);
        this.edit.commit();
    }

    public void calculatePenalty() {
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
        Log.d("hackTSD", "Enter TC: Calculate reached 3...");
        for (int a = 1; a < j + 1; a++) {
            Float distance2 = Float.valueOf(this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(a), 0.0f));
            Float previousdistance = Float.valueOf(this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(a - 1), 0.0f));
            Float speed = Float.valueOf(this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(a), 0.0f));
            for (int b = 1; b < Integer.valueOf(this.zone.getText().toString()).intValue(); b++) {
                if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(b), 0.0f) >= distance2.floatValue() || this.loginpref.getFloat("tcDistanceValue" + String.valueOf(b), 0.0f) < previousdistance.floatValue()) {
                    Float t1 = Float.valueOf(Float.valueOf(distance2.floatValue() - previousdistance.floatValue()).floatValue() / speed.floatValue());
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
                    Float t12 = Float.valueOf(Float.valueOf(distance2.floatValue() - this.loginpref.getFloat("tcDistanceValue" + String.valueOf(b), 0.0f)).floatValue() / speed.floatValue());
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
        Log.d("hackTSD", "Enter TC: Calculate reached 4...");
        for (int i3 = 1; i3 < j + 1; i3++) {
            if (this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f) < this.rbo.floatValue() && this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3), 0.0f) >= this.rbo.floatValue()) {
                this.edit.putFloat("speedUsed", this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(i3), 0.0f));
                this.edit.putFloat("nextSpeed", this.loginpref.getFloat("zoneSpeedValue" + String.valueOf(i3 + 1), 0.0f));
                this.edit.putFloat("speedChangeDistance", this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3), 0.0f));
                Float ah3 = Float.valueOf(this.loginpref.getFloat("zonehh" + String.valueOf(i3), 0.0f));
                Float am3 = Float.valueOf(this.loginpref.getFloat("zonemm" + String.valueOf(i3), 0.0f));
                Float as3 = Float.valueOf(this.loginpref.getFloat("zoness" + String.valueOf(i3), 0.0f));
                this.edit.putString("speedChangeTime", String.valueOf(String.valueOf(Math.round(ah3.floatValue()))) + " : " + String.valueOf(Math.round(am3.floatValue())) + " : " + String.valueOf(Math.round(as3.floatValue())));
                this.edit.commit();
                for (int l = 1; l < Integer.valueOf(this.zone.getText().toString()).intValue(); l++) {
                    if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f) <= this.rbo.floatValue() && this.loginpref.getFloat("zoneDistanceValue" + String.valueOf(i3 - 1), 0.0f) <= this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f)) {
                        this.edit.putFloat("hardPointtc", this.loginpref.getFloat("tcDistanceValue" + String.valueOf(l), 0.0f));
                        this.edit.putFloat("husetc", Float.valueOf(String.valueOf(this.loginpref.getInt("tchho" + String.valueOf(l), 0))).floatValue());
                        this.edit.putFloat("musetc", Float.valueOf(String.valueOf(this.loginpref.getInt("tcmmo" + String.valueOf(l), 0))).floatValue());
                        this.edit.putFloat("susetc", Float.valueOf(String.valueOf(this.loginpref.getInt("tcsso" + String.valueOf(l), 0))).floatValue());
                        this.edit.commit();
                        Log.d("hackTSD", "Enter TC: hard point h time : " + this.loginpref.getInt("tchho" + String.valueOf(l), 0));
                        Log.d("hackTSD", "Enter TC: hard point m time : " + this.loginpref.getInt("tcmmo" + String.valueOf(l), 0));
                        Log.d("hackTSD", "Enter TC: hard point s time : " + this.loginpref.getInt("tcsso" + String.valueOf(l), 0));
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
        if (Float.valueOf(this.rbo.floatValue() - this.loginpref.getFloat("hardPointz", 0.0f)).floatValue() >= Float.valueOf(this.rbo.floatValue() - this.loginpref.getFloat("hardPointtc", 0.0f)).floatValue()) {
            Float hp = Float.valueOf(this.loginpref.getFloat("hardPointtc", 0.0f));
            Float hz = Float.valueOf(this.loginpref.getFloat("husetc", 0.0f));
            Float mz = Float.valueOf(this.loginpref.getFloat("musetc", 0.0f));
            Float sz = Float.valueOf(this.loginpref.getFloat("susetc", 0.0f));
            this.edit.putFloat("hardPoint", hp.floatValue());
            this.edit.putFloat("huse", hz.floatValue());
            this.edit.putFloat("muse", mz.floatValue());
            this.edit.putFloat("suse", sz.floatValue());
            this.edit.commit();
            Log.d("hackTSD", "Enter TC: hard point : zone");
            Log.d("hackTSD", "Enter TC: hz : " + hz);
            Log.d("hackTSD", "Enter TC: mz : " + mz);
            Log.d("hackTSD", "Enter TC: sz : " + sz);
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
            Log.d("hackTSD", "Enter TC: hard point : zone");
            Log.d("hackTSD", "Enter TC: hz : " + ht);
            Log.d("hackTSD", "Enter TC: mz : " + mt);
            Log.d("hackTSD", "Enter TC: sz : " + st);
        }
        Log.d("hackTSD", SUBTAG + this.loginpref.getFloat("mindiffZDV", 0.0f) + "   mindiffZDV");
        Log.d("hackTSD", "Enter TC: Calculate reached 7...");
        Float hardpoint = Float.valueOf(this.loginpref.getFloat("hardPoint", 0.0f));
        Float speedlimit = Float.valueOf(this.loginpref.getFloat("speedUsed", 0.0f));
        Log.d("hackTSD", "Enter TC: Hard Point : " + hardpoint + "    " + "speedlimit : " + speedlimit);
        Float del = Float.valueOf(this.rbo.floatValue() - hardpoint.floatValue());
        Log.d("hackTSD", "Enter TC: del : " + del);
        Float t11 = Float.valueOf(del.floatValue() / speedlimit.floatValue());
        Float th = Float.valueOf(t11.floatValue() - (t11.floatValue() % 1.0f));
        Float th2 = Float.valueOf(th.floatValue() - (th.floatValue() % 1.0f));
        Float t222 = Float.valueOf(t11.floatValue() * 60.0f);
        Float tm = Float.valueOf((t222.floatValue() - (t222.floatValue() % 1.0f)) - (th2.floatValue() * 60.0f));
        Float tm2 = Float.valueOf(tm.floatValue() - (tm.floatValue() % 1.0f));
        Float ts = Float.valueOf(Float.valueOf(t222.floatValue() % 1.0f).floatValue() * 60.0f);
        Float ts2 = Float.valueOf(ts.floatValue() - (ts.floatValue() % 1.0f));
        Log.d("hackTSD", "Enter TC: Calculate reached 10...");
        Float bh = Float.valueOf(this.loginpref.getFloat("huse", 0.0f));
        Float bh2 = Float.valueOf(bh.floatValue() - (bh.floatValue() % 1.0f));
        Float bm = Float.valueOf(this.loginpref.getFloat("muse", 0.0f));
        Float bm2 = Float.valueOf(bm.floatValue() - (bm.floatValue() % 1.0f));
        Float bs = Float.valueOf(this.loginpref.getFloat("suse", 0.0f));
        Float bs2 = Float.valueOf(bs.floatValue() - (bs.floatValue() % 1.0f));
        Log.d("hackTSD", "Enter TC:  : " + del);
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
        Log.d("hackTSD", "Enter TC: actual time" + this.houra + " : " + this.minutea + " : " + this.seconda);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public void onBackPressed() {
        Intent i = new Intent(this, WorkBook.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        this.loginpref = getSharedPreferences("USERLOGIN_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(this, WorkBook.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.action_workbook:
                startActivity(new Intent(this, WorkBook.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(this, About.class));
                break;
            case R.id.action_exit:
                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
        return true;
    }
}
