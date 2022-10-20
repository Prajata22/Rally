package com.applex.rally;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity implements ActionBar.OnNavigationListener {
    public static final String TAG = "hackTSD";
    private static final String SUBTAG = "Splash: ";
    public static boolean connectivity;
    /* access modifiers changed from: private */
    public Context context = this;
    protected Intent intent;
    SharedPreferences.Editor edit;
    SharedPreferences loginpref;
    private Button giveRideButton;
    private LocationManager mLocationManager;
    private Button needRideButton;
    private View.OnClickListener onClickListener;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        this.loginpref = getSharedPreferences("HACKTSD_APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        findViews();
        setAdapters();
        setListeners();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        Log.i("hackTSD", "Splash: onPause");
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Log.i("hackTSD", "Splash: onResume");
        super.onResume();
    }

    private void findViews() {
        this.giveRideButton = (Button) findViewById(R.id.button1);
        this.needRideButton = (Button) findViewById(R.id.button2);
    }

    private void setAdapters() {
    }

    private void setListeners() {
        Log.d("hackTSD", "Splash: setListeners()");
        Log.d("hackTSD", "Splash: Setting onClickListener...");
        this.onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button1 /*2131034221*/:
                        MainActivity.this.edit.clear();
                        MainActivity.this.edit.commit();
                        Intent i = new Intent(MainActivity.this.context, FirstTc.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        MainActivity.this.startActivity(i);
                        return;
                    case R.id.button2 /*2131034222*/:
                        Log.d("hackTSD", "Splash: Button Press: Booking");
                        if (MainActivity.this.loginpref.getFloat("zoneSpeedValue1", 0.0f) > 0.0f) {
                            Intent i2 = new Intent(MainActivity.this.context, WorkBook.class);
                            i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            MainActivity.this.startActivity(i2);
                            return;
                        }
                        Toast.makeText(MainActivity.this.context, "Data Unavailable For Previous Leg. Start a new Leg !", Toast.LENGTH_LONG).show();
                        return;
                    default:
                        return;
                }
            }
        };
        this.giveRideButton.setOnClickListener(this.onClickListener);
        this.needRideButton.setOnClickListener(this.onClickListener);
    }

    /* access modifiers changed from: private */
    public void enableLocationSettings() {
        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        Log.d("hackTSD", "Splash: onStart");
        this.mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!this.mLocationManager.isProviderEnabled("gps")) {
            new EnableGpsDialogFragment(this, null).show(getSupportFragmentManager(), "abandonUserDialog");
        }
        Log.d("hackTSD", "Splash: gps checked");
        if (!isServiceRunning()) {
            Log.d("hackTSD", "Splash: Staring Location Update service.");
            startService(new Intent(this.context, GPSTracker.class));
        }
        Log.d("hackTSD", "Splash: OnStart over");
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
    }

    private boolean isServiceRunning() {
        for (ActivityManager.RunningServiceInfo service : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if ("com.whizzdumbnetworks.hacktsdpro.GPSTracker".equals(service.service.getClassName())) {
                Log.d("hackTSD", "Splash: Matching service found, returning true");
                return true;
            }
        }
        Log.d("hackTSD", "Splash: No matching service found, returning false");
        return false;
    }

    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        if (itemPosition != 0) {
        }
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
            case R.id.action_about /*2131034249*/:
                Intent i = new Intent(this.context, About.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.action_exit /*2131034250*/:
                stopService(new Intent(this.context, GPSTracker.class));
                System.exit(0);
                finish();
                break;
        }
        return true;
    }

    public void onBackPressed() {
        System.exit(0);
        finish();
    }

    @SuppressLint({"ValidFragment"})
    private class EnableGpsDialogFragment extends DialogFragment {
        /* synthetic */ EnableGpsDialogFragment(MainActivity mainActivity, EnableGpsDialogFragment enableGpsDialogFragment) {
            this();
        }

        private EnableGpsDialogFragment() {
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(context).setTitle("GPS is not enabled").setMessage("Enable GPS services fo better accuracy and precise Ideal Time estimates.").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.enableLocationSettings();
                }
            }).create();
        }
    }
}
