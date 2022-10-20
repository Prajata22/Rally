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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Penalty extends AppCompatActivity implements ActionBar.OnNavigationListener {
    public static final String TAG = "hackTSD";
    private static final String SUBTAG = "Penalty: ";
    SharedPreferences.Editor edit;
    int houra;
    SharedPreferences loginpref;
    int minutea;
    Float rbo;
    int seconda;
    private Button back;
    private Context context = this;
    private TextView distance;
    private TextView enterSpeedZone;
    private Button first;
    /* renamed from: go */
    private ImageButton f667go;
    private TextView hhi;
    private TextView hho;
    private Button last;
    private TextView mmi;
    private TextView mmo;
    private Button next;
    private View.OnClickListener onClickListener;
    private TextView ssi;
    private TextView sso;
    private TextView zone;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penalty);
        this.loginpref = getSharedPreferences("HACKTSD_APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        getSupportActionBar().setSubtitle((CharSequence) "Display Tc wise Penalty details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d("hackTSD", "Penalty: Launching findViews()...");
        findViews();
        Log.d("hackTSD", "Penalty: Launching setListeners()...");
        setListeners();
        this.zone.setText(String.valueOf('1'));
        last();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.i("hackTSD", "Penalty: onPause");
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Log.i("hackTSD", "Penalty: onResume");
        super.onResume();
    }

    private void findViews() {
        this.first = (Button) findViewById(R.id.first);
        this.last = (Button) findViewById(R.id.last);
        this.next = (Button) findViewById(R.id.next);
        this.back = (Button) findViewById(R.id.back);
        this.f667go = (ImageButton) findViewById(R.id.go);
        this.distance = (TextView) findViewById(R.id.distance);
        this.hhi = (TextView) findViewById(R.id.hhi);
        this.mmi = (TextView) findViewById(R.id.mmi);
        this.ssi = (TextView) findViewById(R.id.ssi);
        this.hho = (TextView) findViewById(R.id.hho);
        this.mmo = (TextView) findViewById(R.id.mmo);
        this.sso = (TextView) findViewById(R.id.sso);
        this.enterSpeedZone = (EditText) findViewById(R.id.zone_number);
        this.zone = (TextView) findViewById(R.id.zone_tv);
    }

    private void setListeners() {
        Log.d("hackTSD", "Penalty: setListeners()");
        Log.d("hackTSD", "Penalty: Setting onClickListener...");
        this.onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.go:
                        Log.d("hackTSD", "Penalty: Button Press: Booking");
                        Penalty.this.mo4617go();
                        return;
                    case R.id.first:
                        Log.d("hackTSD", "Penalty: Button Press: Booking");
                        Penalty.this.first();
                        return;
                    case R.id.last:
                        Log.d("hackTSD", "Penalty: Button Press: Booking");
                        Penalty.this.last();
                        return;
                    case R.id.back:
                        Log.d("hackTSD", "Penalty: Button Press: Need Ride");
                        Penalty.this.back();
                        return;
                    case R.id.next:
                        Log.d("hackTSD", "Penalty: Button Press: Need Ride");
                        Penalty.this.next();
                        return;
                    default:
                        return;
                }
            }
        };
        Log.d("hackTSD", "Penalty: Setting setOnClickListener for buttons");
        this.first.setOnClickListener(this.onClickListener);
        this.last.setOnClickListener(this.onClickListener);
        this.next.setOnClickListener(this.onClickListener);
        this.back.setOnClickListener(this.onClickListener);
        this.f667go.setOnClickListener(this.onClickListener);
        Log.d("hackTSD", "Penalty: Setting setOnClickListener for Buttons Completed ");
    }

    /* renamed from: go */
    public void mo4617go() {
        try {
            String j = this.enterSpeedZone.getText().toString();
            if (Integer.valueOf(j).intValue() == 1) {
            }
            this.zone.setText(String.valueOf(j));
            this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + String.valueOf(j), 0.0f)));
            this.enterSpeedZone.setText("");
            this.hhi.setText(String.valueOf(this.loginpref.getInt("penaltyh" + String.valueOf(1), 0)));
            this.mmi.setText(String.valueOf(this.loginpref.getInt("penaltym" + String.valueOf(1), 0)));
            this.ssi.setText(String.valueOf(this.loginpref.getInt("penaltys" + String.valueOf(1), 0)));
            this.hho.setText(String.valueOf(this.loginpref.getInt("penaltyh" + String.valueOf(1), 0)));
            this.mmo.setText(String.valueOf(this.loginpref.getInt("penaltym" + String.valueOf(1), 0)));
            this.sso.setText(String.valueOf(this.loginpref.getInt("penaltys" + String.valueOf(1), 0)));
            if (j.length() == 0) {
                Toast.makeText(this.context, "Enter a Real Value !", Toast.LENGTH_LONG).show();
            } else if (Integer.valueOf(j).intValue() <= 1) {
                Toast.makeText(this.context, "Enter a Real Value !", Toast.LENGTH_LONG).show();
            } else if (this.loginpref.getFloat("tcDistanceValue" + j, 0.0f) > 0.0f) {
                this.zone.setText(j);
                this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + j, 0.0f)));
                this.enterSpeedZone.setText("");
                this.hhi.setText(String.valueOf(this.loginpref.getInt("penaltyh" + String.valueOf(j), 0)));
                this.mmi.setText(String.valueOf(this.loginpref.getInt("penaltym" + String.valueOf(j), 0)));
                this.ssi.setText(String.valueOf(this.loginpref.getInt("penaltys" + String.valueOf(j), 0)));
                this.hho.setText(String.valueOf(this.loginpref.getInt("penaltyht" + String.valueOf(j), 0)));
                this.mmo.setText(String.valueOf(this.loginpref.getInt("penaltymt" + String.valueOf(j), 0)));
                this.sso.setText(String.valueOf(this.loginpref.getInt("penaltyst" + String.valueOf(j), 0)));
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
        this.zone.setText(String.valueOf(j));
        this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + String.valueOf(j), 0.0f)));
        this.enterSpeedZone.setText("");
        this.hhi.setText(String.valueOf(this.loginpref.getInt("penaltyh" + String.valueOf(j), 0)));
        this.mmi.setText(String.valueOf(this.loginpref.getInt("penaltym" + String.valueOf(j), 0)));
        this.ssi.setText(String.valueOf(this.loginpref.getInt("penaltys" + String.valueOf(j), 0)));
        this.hho.setText(String.valueOf(this.loginpref.getInt("penaltyht" + String.valueOf(j), 0)));
        this.mmo.setText(String.valueOf(this.loginpref.getInt("penaltymt" + String.valueOf(j), 0)));
        this.sso.setText(String.valueOf(this.loginpref.getInt("penaltyst" + String.valueOf(j), 0)));
    }

    public void first() {
        this.zone.setText(String.valueOf(2));
        this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + String.valueOf(2), 0.0f)));
        this.enterSpeedZone.setText("");
        this.hhi.setText(String.valueOf(this.loginpref.getInt("penaltyh" + String.valueOf(2), 0)));
        this.mmi.setText(String.valueOf(this.loginpref.getInt("penaltym" + String.valueOf(2), 0)));
        this.ssi.setText(String.valueOf(this.loginpref.getInt("penaltys" + String.valueOf(2), 0)));
        this.hho.setText(String.valueOf(this.loginpref.getInt("penaltyht" + String.valueOf(2), 0)));
        this.mmo.setText(String.valueOf(this.loginpref.getInt("penaltymt" + String.valueOf(2), 0)));
        this.sso.setText(String.valueOf(this.loginpref.getInt("penaltyst" + String.valueOf(2), 0)));
    }

    public void back() {
        int j = Integer.valueOf(this.zone.getText().toString()).intValue();
        if (j != 1) {
            if (j == 2) {
            }
            this.zone.setText(String.valueOf(j - 1));
            this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + String.valueOf(j - 1), 0.0f)));
            this.enterSpeedZone.setText("");
            this.hhi.setText(String.valueOf(this.loginpref.getInt("penaltyh" + String.valueOf(j - 1), 0)));
            this.mmi.setText(String.valueOf(this.loginpref.getInt("penaltym" + String.valueOf(j - 1), 0)));
            this.ssi.setText(String.valueOf(this.loginpref.getInt("penaltys" + String.valueOf(j - 1), 0)));
            this.hho.setText(String.valueOf(this.loginpref.getInt("penaltyht" + String.valueOf(j - 1), 0)));
            this.mmo.setText(String.valueOf(this.loginpref.getInt("penaltymt" + String.valueOf(j - 1), 0)));
            this.sso.setText(String.valueOf(this.loginpref.getInt("penaltyst" + String.valueOf(j - 1), 0)));
            if (j <= 2) {
                Toast.makeText(this.context, "Can't go further back !", Toast.LENGTH_LONG).show();
            } else if (this.loginpref.getFloat("tcDistanceValue" + String.valueOf(j - 1), 0.0f) > 0.0f) {
                this.zone.setText(String.valueOf(j - 1));
                this.distance.setText(String.valueOf(this.loginpref.getFloat("tcDistanceValue" + String.valueOf(j - 1), 0.0f)));
                this.enterSpeedZone.setText("");
                this.hhi.setText(String.valueOf(this.loginpref.getInt("penaltyh" + String.valueOf(j - 1), 0)));
                this.mmi.setText(String.valueOf(this.loginpref.getInt("penaltym" + String.valueOf(j - 1), 0)));
                this.ssi.setText(String.valueOf(this.loginpref.getInt("penaltys" + String.valueOf(j - 1), 0)));
                this.hho.setText(String.valueOf(this.loginpref.getInt("penaltyht" + String.valueOf(j - 1), 0)));
                this.mmo.setText(String.valueOf(this.loginpref.getInt("penaltymt" + String.valueOf(j - 1), 0)));
                this.sso.setText(String.valueOf(this.loginpref.getInt("penaltyst" + String.valueOf(j - 1), 0)));
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
            this.hhi.setText(String.valueOf(this.loginpref.getInt("penaltyh" + String.valueOf(i + 1), 0)));
            this.mmi.setText(String.valueOf(this.loginpref.getInt("penaltym" + String.valueOf(i + 1), 0)));
            this.ssi.setText(String.valueOf(this.loginpref.getInt("penaltys" + String.valueOf(i + 1), 0)));
            this.hho.setText(String.valueOf(this.loginpref.getInt("penaltyht" + String.valueOf(i + 1), 0)));
            this.mmo.setText(String.valueOf(this.loginpref.getInt("penaltymt" + String.valueOf(i + 1), 0)));
            this.sso.setText(String.valueOf(this.loginpref.getInt("penaltyst" + String.valueOf(i + 1), 0)));
            this.enterSpeedZone.setText("");
            return;
        }
        Toast.makeText(this.context, "No more penalties to show !", Toast.LENGTH_LONG).show();
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
