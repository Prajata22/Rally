package com.applex.rally;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import java.math.BigDecimal;

public class GPSTracker extends Service {
    private static final String SUBTAG = "LocationService: ";
    private static final String TAG = "RideSafe: ";
    /* access modifiers changed from: private */
    public float distance;
    /* access modifiers changed from: private */
    public float error;
    /* access modifiers changed from: private */
    public float initial;
    /* access modifiers changed from: private */
    public Location last;
    /* access modifiers changed from: private */
    public long minDistance = 0;
    /* access modifiers changed from: private */
    public long minTime = 0;
    /* access modifiers changed from: private */
    public float speed;
    SharedPreferences.Editor edit;
    SharedPreferences preferences;
    private final LocationListener listener = new LocationListener() {
        public void onLocationChanged(Location location) {
            try {
                GPSTracker.this.initial = GPSTracker.this.preferences.getFloat("distanceTravelled", 0.0f) * 1000.0f;
                GPSTracker.this.error = location.getAccuracy();
                GPSTracker.this.speed = (float) (((double) Float.valueOf(location.getSpeed()).floatValue()) * 3.6d);
                if (GPSTracker.this.speed > 3.0f) {
                    if (((double) location.getAccuracy()) < 10.0d) {
                        if (GPSTracker.this.last != null) {
                            GPSTracker.this.distance = GPSTracker.this.initial + location.distanceTo(GPSTracker.this.last);
                        }
                        GPSTracker.this.last = new Location(location);
                        GPSTracker.this.minDistance = 10;
                        GPSTracker.this.minTime = 5000;
                    }
                    if (((double) location.getAccuracy()) >= 10.0d && ((double) location.getAccuracy()) < 15.0d) {
                        if (GPSTracker.this.last != null) {
                            GPSTracker.this.distance = GPSTracker.this.initial + location.distanceTo(GPSTracker.this.last);
                        }
                        GPSTracker.this.last = new Location(location);
                        GPSTracker.this.minDistance = 15;
                        GPSTracker.this.minTime = 5000;
                    }
                    if (((double) location.getAccuracy()) >= 15.0d && ((double) location.getAccuracy()) < 20.0d) {
                        if (GPSTracker.this.last != null) {
                            GPSTracker.this.distance = GPSTracker.this.initial + location.distanceTo(GPSTracker.this.last);
                        }
                        GPSTracker.this.last = new Location(location);
                        GPSTracker.this.minDistance = 20;
                        GPSTracker.this.minTime = 5000;
                    }
                    if (((double) location.getAccuracy()) >= 20.0d) {
                        if (GPSTracker.this.last != null) {
                            GPSTracker.this.distance = GPSTracker.this.initial + location.distanceTo(GPSTracker.this.last);
                        }
                        GPSTracker.this.last = new Location(location);
                        GPSTracker.this.minDistance = 25;
                        GPSTracker.this.minTime = 5000;
                    }
                    GPSTracker gPSTracker = GPSTracker.this;
                    gPSTracker.distance = gPSTracker.distance / 1000.0f;
                    if (GPSTracker.this.preferences.getInt("distancePreference", -1) == 1) {
                        GPSTracker.this.distance = (float) (((double) GPSTracker.this.distance) / 1.609d);
                        GPSTracker.this.speed = (float) (((double) GPSTracker.this.speed) / 1.609d);
                    }
                    GPSTracker.this.edit.putFloat("currentSpeed", Float.valueOf(String.valueOf(GPSTracker.round(GPSTracker.this.speed, 2))).floatValue());
                    GPSTracker.this.edit.putFloat("distanceTravelled", GPSTracker.this.distance);
                    GPSTracker.this.edit.putFloat("gpsAccuracy", GPSTracker.this.error);
                    GPSTracker.this.edit.commit();
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };
    private LocationManager mLocationManager;

    public static BigDecimal round(float d, int decimalPlace) {
        return new BigDecimal(Float.toString(d)).setScale(decimalPlace, 4);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0060, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0061, code lost:
        r7.printStackTrace();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0060 A[ExcHandler: Exception (r7v0 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:1:0x0004] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate() {
        /*
            r8 = this;
            java.lang.String r0 = "RideSafe: "
            java.lang.String r1 = "LocationService: onCreate"
            android.util.Log.i(r0, r1)     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            java.lang.String r0 = "HACKTSD_APPLICATION_PREFERENCE"
            r1 = 0
            android.content.SharedPreferences r0 = r8.getSharedPreferences(r0, r1)     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            r8.preferences = r0     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            android.content.SharedPreferences r0 = r8.preferences     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            r8.edit = r0     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            android.content.SharedPreferences$Editor r0 = r8.edit     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            r0.commit()     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            r0 = 973279855(0x3a03126f, float:5.0E-4)
            r8.distance = r0     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            java.lang.String r0 = "RideSafe: "
            java.lang.String r1 = "LocationService: Setting Location Service to capture distance data"
            android.util.Log.i(r0, r1)     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            java.lang.String r0 = "location"
            java.lang.Object r0 = r8.getSystemService(r0)     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            android.location.LocationManager r0 = (android.location.LocationManager) r0     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            r8.mLocationManager = r0     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            android.location.LocationManager r0 = r8.mLocationManager     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            java.lang.String r1 = "network"
            long r2 = r8.minTime     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            long r4 = r8.minDistance     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            float r4 = (float) r4     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            android.location.LocationListener r5 = r8.listener     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            r0.requestLocationUpdates(r1, r2, r4, r5)     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            android.location.LocationManager r0 = r8.mLocationManager     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            java.lang.String r1 = "gps"
            long r2 = r8.minTime     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            long r4 = r8.minDistance     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            float r4 = (float) r4     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            android.location.LocationListener r5 = r8.listener     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            r0.requestLocationUpdates(r1, r2, r4, r5)     // Catch:{ NullPointerException -> 0x005b, Exception -> 0x0060 }
            android.location.Criteria r6 = new android.location.Criteria     // Catch:{ NullPointerException -> 0x0065, Exception -> 0x0060 }
            r6.<init>()     // Catch:{ NullPointerException -> 0x0065, Exception -> 0x0060 }
            android.location.LocationManager r0 = r8.mLocationManager     // Catch:{ NullPointerException -> 0x0065, Exception -> 0x0060 }
            r1 = 1
            r0.getBestProvider(r6, r1)     // Catch:{ NullPointerException -> 0x0065, Exception -> 0x0060 }
        L_0x005a:
            return
        L_0x005b:
            r7 = move-exception
            r7.printStackTrace()
            goto L_0x005a
        L_0x0060:
            r7 = move-exception
            r7.printStackTrace()
            goto L_0x005a
        L_0x0065:
            r0 = move-exception
            goto L_0x005a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.whizzdumbnetworks.hacktsdpro.GPSTracker.onCreate():void");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        this.mLocationManager.removeUpdates(this.listener);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
