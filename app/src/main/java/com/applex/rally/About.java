package com.applex.rally;

import android.app.ActionBar;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity implements ActionBar.OnNavigationListener {
    SharedPreferences.Editor edit;
    SharedPreferences loginpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        this.loginpref = getSharedPreferences("HACKTSD_APPLICATION_PREFERENCE", 0);
        this.edit = this.loginpref.edit();
        this.edit.commit();
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
    }

    /* access modifiers changed from: protected */

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit /*2131034250*/:
                Intent i = new Intent(this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                break;
            case R.id.action_newleg /*2131034255*/:
                this.edit.clear();
                this.edit.putFloat("correctGpsOdo", 0.0f);
                this.edit.putFloat("correctCarOdo", 0.0f);
                this.edit.putFloat("distanceTravelled", 0.0f);
                this.edit.commit();
                Intent i2 = new Intent(this, EnterSpeedChart.class);
                i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i2);
                finish();
                break;
            case R.id.action_previousleg /*2131034256*/:
                if (this.loginpref.getFloat("zoneSpeedValue1", 0.0f) <= 0.0f) {
                    Toast.makeText(this, "Data Unavailable For Previous Rides !", Toast.LENGTH_LONG).show();
                    break;
                } else {
                    Intent i3 = new Intent(this, WorkBook.class);
                    i3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i3);
                    finish();
                    break;
                }
        }
        return true;
    }

    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return true;
    }
}
