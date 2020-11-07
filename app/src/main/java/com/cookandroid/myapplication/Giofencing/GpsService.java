package com.cookandroid.myapplication.Giofencing;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class GpsService extends Service {

    private ArrayList<PointD> mPointList = new ArrayList<PointD>();

    public void addPoint (double x, double y) {
        mPointList.add(new PointD(x,y));
    }

    public boolean isPointInPolygon(double x, double y){
        int size = mPointList.size();

        if (size < 3){
            return false;
        }

        int prev = size -1;
        boolean isInnerPoint = false;

        for (int cur = 0; cur < size; cur++){
            PointD curPoint = mPointList.get(cur);
            PointD prevPoint = mPointList.get(prev);

            if (curPoint.getY() < y && prevPoint.getY() >= y || prevPoint.getY() < y && curPoint.getY() >= y){
                if(curPoint.getX() + (y - curPoint.getY()) / (prevPoint.getY() - curPoint.getY()) * (prevPoint.getX() - curPoint.getX()) < x) {
                    isInnerPoint =! isInnerPoint;
                }
            }
            prev = cur;

        }
        return isInnerPoint;
    }

    final double x1 = 126.680803;
    final double x2 = 126.682557;
    final double x3 = 126.681092;
    final double x4 = 126.679418;

    final double y1 = 37.414736;
    final double y2 = 37.413644;
    final double y3 = 37.412501;
    final double y4 = 37.413728;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        // 지오펜싱 값 지정
        addPoint(x1,y1);
        addPoint(x2,y2);
        addPoint(x3,y3);
        addPoint(x4,y4);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String userID = intent.getStringExtra("userID");

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        10000,
                        1,
                        mLocationListener);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        10000,
                        1,
                        mLocationListener);
        } catch (SecurityException ex) {
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            System.out.println("listener test");
            Log.d("test", "onLocationChanged, location:" + location);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();
            float accuracy = location.getAccuracy();
            String provider = location.getProvider();

            System.out.println( "위도 : " + longitude + "\n경도 : " + latitude
                    + "\n고도 : " + altitude + "\n정확도 : " + accuracy);

            if(isPointInPolygon(longitude,latitude)){
                Toast myToast = Toast.makeText(getApplicationContext() ,"In", Toast.LENGTH_SHORT);
                myToast.show();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                };
                GiofencingRequest GiofencingRequest = new GiofencingRequest("a", responseListener);
                RequestQueue queue = Volley.newRequestQueue(GpsService.this);
                queue.add(GiofencingRequest);

            }else{
                Toast myToast = Toast.makeText(getApplicationContext(),"out", Toast.LENGTH_SHORT);
                myToast.show();
            }
        }

        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };
}
