package com.applex.rally;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
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
import androidx.fragment.app.DialogFragment;

public class FirstTc extends AppCompatActivity implements ActionBar.OnNavigationListener {
    public static final String TAG = "hackTSD";
    private static final String SUBTAG = "First TC: ";
    /* access modifiers changed from: private */
    /* access modifiers changed from: private */
    public EditText distance;
    /* access modifiers changed from: private */
    public TextView distance_tv;
    /* access modifiers changed from: private */
    public TextView odo_label_tv;
    ImageButton distance_set;
    SharedPreferences.Editor edit;
    int houra;
    SharedPreferences loginpref;
    int minutea;
    Float rbo;
    int seconda;
    private EditText hho;
    private LocationManager mLocationManager;
    private EditText mmo;
    private View.OnClickListener onClickListener;
    private Button save;
    private EditText sso;
    private TextView zone;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.firsttc);
            this.loginpref = getSharedPreferences("HACKTSD_APPLICATION_PREFERENCE", 0);
            this.edit = this.loginpref.edit();
            this.edit.commit();
            findViews();
            setListeners();
            getSupportActionBar().setSubtitle((CharSequence) "Enter Leg Start Information");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(1, 0);
            preference();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.i("hackTSD", "First TC: onPause");
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Log.i("hackTSD", "First TC: onResume");
        super.onResume();
    }

    private void findViews() {
        this.save = (Button) findViewById(R.id.save);
        this.distance = (EditText) findViewById(R.id.distance);
        this.hho = (EditText) findViewById(R.id.hho);
        this.mmo = (EditText) findViewById(R.id.mmo);
        this.sso = (EditText) findViewById(R.id.sso);
        this.zone = (TextView) findViewById(R.id.zone_tv);
        this.distance_set = (ImageButton) findViewById(R.id.distance_set);
        this.distance_tv = (TextView) findViewById(R.id.preference);
        this.odo_label_tv = (TextView) findViewById(R.id.odo_label);
    }

    private void preference() {
        this.distance.setText("");
        this.hho.setText("");
        this.mmo.setText("");
        this.sso.setText("");
        if (this.loginpref.getInt("distancePreference", 0) == 1) {
            this.odo_label_tv.setText("Leg Start Odo Reading in Miles");
            this.distance_tv.setText("Distance in Miles");
            return;
        }
        this.distance_tv.setText("Distance in KMs");
        this.odo_label_tv.setText("Leg Start Odo Reading in KM");
    }

    private void setListeners() {
        try {
            this.onClickListener = new View.OnClickListener() {
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.save /*2131034199*/:
                            Log.d("hackTSD", "First TC: Button Press: Need Ride");
                            if (FirstTc.this.distance.getText().toString().length() != 0) {
                                FirstTc.this.save();
                                return;
                            } else {
                                Toast.makeText(FirstTc.this, "Enter Valid values !", Toast.LENGTH_LONG).show();
                                return;
                            }
                        case R.id.distance_set /*2131034206*/:
                            AlertDialog.Builder c = new AlertDialog.Builder(FirstTc.this);
                            c.setTitle("Set Distance Preference");
                            c.setItems(new String[]{"Distance in KMs", "Distance in Miles"}, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    switch (which) {
                                        case 0:
                                            FirstTc.this.edit.putInt("distancePreference", which);
                                            FirstTc.this.edit.putString("distancePreferenceText", "Distance in KMs");
                                            FirstTc.this.edit.commit();
                                            FirstTc.this.distance_tv.setText("Distance in KMs");
                                            FirstTc.this.odo_label_tv.setText("Leg Start Odo Reading in KM");
                                            return;
                                        case 1:
                                            FirstTc.this.edit.putInt("distancePreference", which);
                                            FirstTc.this.edit.putString("distancePreferenceText", "Distance in Miles");
                                            FirstTc.this.edit.commit();
                                            FirstTc.this.distance_tv.setText("Distance in Miles");
                                            FirstTc.this.odo_label_tv.setText("Leg Start Odo Reading in Miles");
                                            return;
                                        default:
                                            return;
                                    }
                                }
                            });
                            c.show();
                            return;
                        default:
                            return;
                    }
                }
            };
            Log.d("hackTSD", "First TC: Setting setOnClickListener for buttons");
            this.save.setOnClickListener(this.onClickListener);
            this.distance_set.setOnClickListener(this.onClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("hackTSD", "First TC: Setting setOnClickListener for Buttons Completed ");
    }

    public void save() {
        try {
            int i = Integer.valueOf(this.zone.getText().toString()).intValue();
            int h2 = Integer.valueOf(this.hho.getText().toString()).intValue();
            int m2 = Integer.valueOf(this.mmo.getText().toString()).intValue();
            int s2 = Integer.valueOf(this.sso.getText().toString()).intValue();
            Float valueOf = Float.valueOf(0.0f);
            Float d = Float.valueOf(this.distance.getText().toString());
            this.edit.putFloat("tcDistanceValue" + String.valueOf(i), d.floatValue());
            this.edit.putFloat("distanceTravelled", (float) (((double) d.floatValue()) + 1.0E-5d));
            this.edit.putInt("tchho1", h2);
            this.edit.putInt("tcmmo1", m2);
            this.edit.putInt("tcsso1", s2);
            this.edit.putInt("tchhi1", h2);
            this.edit.putInt("tcmmi1", m2);
            this.edit.putInt("tcssi1", s2);
            this.edit.commit();
            Intent k = new Intent(this, EnterSpeedChart.class);
            k.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(k);
        } catch (Exception e) {
        }
    }

    /* access modifiers changed from: private */
    public void enableLocationSettings() {
        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        Log.d("hackTSD", "First TC: onStart");
        this.mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!this.mLocationManager.isProviderEnabled("gps")) {
            new EnableGpsDialogFragment(this, (EnableGpsDialogFragment) null).show(getSupportFragmentManager(), "enableGpsDialog");
        }
        Log.d("hackTSD", "First TC: gps checked");
        if (!isServiceRunning()) {
            Log.d("hackTSD", "First TC: Staring Location Update service.");
            startService(new Intent(this, GPSTracker.class));
        }
        Log.d("hackTSD", "First TC: OnStart over");
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
    }

    private boolean isServiceRunning() {
        Log.i("hackTSD", "First TC: isServiceRunning()");
        for (ActivityManager.RunningServiceInfo service : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if ("com.whizzdumbnetworks.hacktsdpro.GPSTracker".equals(service.service.getClassName())) {
                Log.d("hackTSD", "First TC: Matching service found, returning true");
                return true;
            }
        }
        Log.d("hackTSD", "First TC: No matching service found, returning false");
        return false;
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.action_about /*2131034249*/:
                Intent i = new Intent(this, About.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.action_exit /*2131034250*/:
                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
        return true;
    }

    @SuppressLint({"ValidFragment"})
    private class EnableGpsDialogFragment extends DialogFragment {
        /* synthetic */ EnableGpsDialogFragment(FirstTc firstTc, EnableGpsDialogFragment enableGpsDialogFragment) {
            this();
        }

        private EnableGpsDialogFragment() {
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity()).setTitle("GPS is not enabled").setMessage("Enable GPS services fo better accuracy and precise Ideal Time estimates.").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    FirstTc.this.enableLocationSettings();
                }
            }).create();
        }
    }
}
