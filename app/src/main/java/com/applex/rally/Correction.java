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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Correction extends AppCompatActivity implements ActionBar.OnNavigationListener {
    public static final String TAG = "hackTSD";
    private static final String SUBTAG = "Enter Speed Chart: ";
    /* access modifiers changed from: private */
    public Context context = this;
    /* access modifiers changed from: private */
    public EditText distance;
    /* access modifiers changed from: private */
    public EditText speed;
    SharedPreferences.Editor edit;
    SharedPreferences loginpref;
    private View.OnClickListener onClickListener;
    private TextView rbo;
    private Button save;
    private TextView zone;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correction);
        Log.d("hackTSD", "Enter Speed Chart: Launching findViews()...");
        findViews();
        Log.d("hackTSD", "Enter Speed Chart: Launching setListeners()...");
        setListeners();
        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 3...");
        String x1 = this.loginpref.getString("correctionAtTulip", "");
        Log.d("hackTSD", "Enter Speed Chart: 1");
        String x2 = this.loginpref.getString("correctionAtRBOdo", "");
        Log.d("hackTSD", "Enter Speed Chart: 2");
        this.zone.setText(x1);
        Log.d("hackTSD", "Enter Speed Chart: 3");
        this.rbo.setText(x2);
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 4...");
        if (this.loginpref.getFloat("correctGpsOdo", 0.0f) > 0.0f) {
            this.distance.setText(String.valueOf(String.valueOf(x2) + this.loginpref.getFloat("correctGpsOdo", 0.0f)));
        } else {
            this.distance.setText(x2);
        }
        if (this.loginpref.getFloat("correctCarOdo", 0.0f) > 0.0f) {
            this.distance.setText(String.valueOf(String.valueOf(x2) + this.loginpref.getFloat("correctCarOdo", 0.0f)));
        } else {
            this.speed.setText(x2);
        }
        Log.d("hackTSD", "Enter Speed Chart: Calculate reached 5...");
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
        this.save = (Button) findViewById(R.id.save);
        this.speed = (EditText) findViewById(R.id.speed);
        this.distance = (EditText) findViewById(R.id.distance);
        this.rbo = (TextView) findViewById(R.id.distance_tv);
        this.zone = (TextView) findViewById(R.id.zone_tv);
    }

    private void setListeners() {
        Log.d("hackTSD", "Enter Speed Chart: setListeners()");
        Log.d("hackTSD", "Enter Speed Chart: Setting onClickListener...");
        this.onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Correction.this.loginpref = Correction.this.getSharedPreferences("APPLICATION_PREFERENCE", 0);
                Correction.this.edit = Correction.this.loginpref.edit();
                switch (v.getId()) {
                    case R.id.save /*2131034199*/:
                        Log.d("hackTSD", "Enter Speed Chart: Button Press: Need Ride");
                        if (Correction.this.speed.getText().toString().length() == 0) {
                            Toast.makeText(Correction.this.context, "Enter Valid values !", Toast.LENGTH_LONG).show();
                            return;
                        } else if (Correction.this.distance.getText().toString().length() != 0) {
                            Correction.this.save();
                            Correction.this.startActivity(new Intent(Correction.this.context, WorkRoadBook.class));
                            return;
                        } else {
                            Toast.makeText(Correction.this.context, "Enter Valid values !", Toast.LENGTH_LONG).show();
                            return;
                        }
                    default:
                        return;
                }
            }
        };
        this.save.setOnClickListener(this.onClickListener);
        Log.d("hackTSD", "Enter Speed Chart: Setting setOnClickListener for Buttons Completed ");
    }

    public void save() {
        this.loginpref = getSharedPreferences("APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.putFloat("correctCarOdo", Float.valueOf(this.speed.getText().toString()).floatValue() - Float.valueOf(this.rbo.getText().toString()).floatValue());
        this.edit.putFloat("correctGpsOdo", Float.valueOf(this.distance.getText().toString()).floatValue() - Float.valueOf(this.rbo.getText().toString()).floatValue());
        this.edit.commit();
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

    public void onBackPressed() {
        Intent i = new Intent(this, WorkRoadBook.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        menu.removeItem(R.id.edit);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.getItemId();
        return true;
    }
}
