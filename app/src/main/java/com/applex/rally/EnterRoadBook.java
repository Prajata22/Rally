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

public class EnterRoadBook extends AppCompatActivity implements ActionBar.OnNavigationListener {
    public static final String TAG = "hackTSD";
    private static final String SUBTAG = "Enter Speed Chart: ";
    /* access modifiers changed from: private */
    public Context context = this;
    /* access modifiers changed from: private */
    public EditText distance;
    SharedPreferences.Editor edit;
    SharedPreferences loginpref;
    private Button back;
    private EditText enterSpeedZone;
    private Button first;
    /* renamed from: go */
    private ImageButton f664go;
    private Button last;
    private Button next;
    private View.OnClickListener onClickListener;
    private Button save;
    private TextView zone;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roadbooklite);
        Log.d("hackTSD", "Enter Speed Chart: Launching findViews()...");
        findViews();
        Log.d("hackTSD", "Enter Speed Chart: Launching setListeners()...");
        setListeners();
        getSupportActionBar().setSubtitle((CharSequence) "Enter RoadBook Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.zone.setText(String.valueOf('1'));
        this.loginpref = getSharedPreferences("HACKTSD_APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        if (this.loginpref.getFloat("rbDistanceValue" + String.valueOf(2), 0.0f) > 0.0f) {
            last();
        }
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
        this.f664go = (ImageButton) findViewById(R.id.go);
        this.distance = (EditText) findViewById(R.id.distance);
        this.enterSpeedZone = (EditText) findViewById(R.id.zone_number);
        this.zone = (TextView) findViewById(R.id.zone_tv);
    }

    private void setListeners() {
        Log.d("hackTSD", "Enter Speed Chart: setListeners()");
        Log.d("hackTSD", "Enter Speed Chart: Setting onClickListener...");
        this.onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.go /*2131034183*/:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Booking");
                        EnterRoadBook.this.mo4580go();
                        return;
                    case R.id.save /*2131034199*/:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Need Ride");
                        if (EnterRoadBook.this.distance.getText().toString().length() != 0) {
                            EnterRoadBook.this.save();
                            EnterRoadBook.this.distance.requestFocus();
                            return;
                        }
                        Toast.makeText(EnterRoadBook.this.context, "Enter Valid values !", Toast.LENGTH_LONG).show();
                        return;
                    case R.id.first /*2131034208*/:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Booking");
                        EnterRoadBook.this.first();
                        return;
                    case R.id.last /*2131034209*/:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Booking");
                        EnterRoadBook.this.last();
                        return;
                    case R.id.back /*2131034210*/:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Need Ride");
                        EnterRoadBook.this.back();
                        return;
                    case R.id.next /*2131034211*/:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Need Ride");
                        EnterRoadBook.this.next();
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
        this.f664go.setOnClickListener(this.onClickListener);
        this.save.setOnClickListener(this.onClickListener);
        Log.d("hackTSD", "Enter Speed Chart: Setting setOnClickListener for Buttons Completed ");
    }

    /* renamed from: go */
    public void mo4580go() {
        String j = this.enterSpeedZone.getText().toString();
        if (j.length() == 0) {
            Toast.makeText(this.context, "Enter a Real Value !", Toast.LENGTH_LONG).show();
        } else if (Integer.valueOf(j).intValue() <= 0) {
            Toast.makeText(this.context, "Enter a Real Value !", Toast.LENGTH_LONG).show();
        } else if (this.loginpref.getFloat("rbDistanceValue" + j, 0.0f) > 0.0f) {
            this.zone.setText(j);
            this.distance.setText(String.valueOf(this.loginpref.getFloat("rbDistanceValue" + j, 0.0f)));
            this.enterSpeedZone.setText("");
        } else {
            Toast.makeText(this.context, "No Value at this Speed Zone !", Toast.LENGTH_LONG).show();
        }
    }

    public void last() {
        int j = 0;
        if (this.loginpref.getFloat("rbDistanceValue" + String.valueOf(1), 0.0f) == 0.0f) {
            j = 1;
        }
        for (int i = 1; i < 1000; i++) {
            if (this.loginpref.getFloat("rbDistanceValue" + String.valueOf(i), 0.0f) > 0.0f) {
                j++;
            }
        }
        if (j == 0) {
            this.zone.setText(String.valueOf(1));
            this.distance.setText("");
            this.enterSpeedZone.setText("");
            return;
        }
        this.zone.setText(String.valueOf(j + 1));
        this.distance.setText("");
        this.enterSpeedZone.setText("");
    }

    public void first() {
        this.zone.setText(String.valueOf(1));
        this.distance.setText(String.valueOf(this.loginpref.getFloat("rbDistanceValue" + String.valueOf(1), 0.0f)));
        this.enterSpeedZone.setText("");
    }

    public void back() {
        int i = Integer.valueOf(this.zone.getText().toString()).intValue();
        if (i <= 1) {
            Toast.makeText(this.context, "Can't go further back !", Toast.LENGTH_LONG).show();
        } else if (this.loginpref.getFloat("rbDistanceValue" + String.valueOf(i - 1), 0.0f) > 0.0f) {
            this.zone.setText(String.valueOf(i - 1));
            this.distance.setText(String.valueOf(this.loginpref.getFloat("rbDistanceValue" + String.valueOf(i - 1), 0.0f)));
            this.enterSpeedZone.setText("");
        } else {
            Toast.makeText(this.context, "Press Last for last entered Speed Zone !", Toast.LENGTH_LONG).show();
        }
    }

    public void next() {
        int i = Integer.valueOf(this.zone.getText().toString()).intValue();
        if (this.loginpref.getFloat("rbDistanceValue" + String.valueOf(i + 1), 0.0f) > 0.0f) {
            this.zone.setText(String.valueOf(i + 1));
            this.distance.setText(String.valueOf(this.loginpref.getFloat("rbDistanceValue" + String.valueOf(i + 1), 0.0f)));
            this.enterSpeedZone.setText("");
            return;
        }
        this.zone.setText(String.valueOf(i + 1));
        this.distance.setText("");
        this.enterSpeedZone.setText("");
    }

    public void save() {
        int i = Integer.valueOf(this.zone.getText().toString()).intValue();
        this.edit.putFloat("rbDistanceValue" + String.valueOf(i), Float.valueOf(this.distance.getText().toString()).floatValue());
        this.edit.commit();
        this.zone.setText(String.valueOf(i + 1));
        this.distance.setText("");
        this.enterSpeedZone.setText("");
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
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
            case R.id.edit /*2131034137*/:
                Intent i = new Intent(this, WorkBook.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.action_workbook /*2131034248*/:
                startActivity(new Intent(this, WorkBook.class));
                break;
            case R.id.action_about /*2131034249*/:
                Intent i2 = new Intent(this.context, About.class);
                i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i2);
                break;
            case R.id.action_exit /*2131034250*/:
                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
        return true;
    }

    public void onBackPressed() {
    }
}
