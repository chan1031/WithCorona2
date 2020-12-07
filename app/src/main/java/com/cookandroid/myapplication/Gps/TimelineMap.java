package com.cookandroid.myapplication.Gps;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cookandroid.myapplication.Gps.locationDB.DBHelper;
import com.cookandroid.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TimelineMap extends AppCompatActivity
        implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback{

    Toolbar toolbar;

    private GoogleMap mMap;
    private Marker currentMarker;
    private Polyline currentPolyline;
    String textLocation;
    String formatDate;
    String timeHistory;
    String textBeforeLocation;
    ArrayList<String> locationList;
    ArrayList<String> timeList;
    ArrayList<String> timeHistoryList;
    ArrayList<Polyline> polylines;
    PolylineOptions polylineOptions;
    ArrayList<String> list;

    String userID;

    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000 * 60 * 1;  // 1분단위로 갱신
    private static final int FASTEST_UPDATE_INTERVAL_MS = 1000 * 30; // 30초


    // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용됩니다.
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;


    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소


    Location mCurrentLocatiion;
    LatLng currentPosition;


    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;
    private Location beforeLocation;


    private View mLayout;  // Snackbar 사용하기 위해서는 View가 필요합니다.
    // (참고로 Toast에서는 Context가 필요했습니다.)

    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userID = getIntent().getStringExtra("userID");
            //액티비티 시작후 저장된 데이터 불러오기(경로)
            list = getStringArrayPref(this, SETTINGS_PLAYER_JSON);
            if (list != null) {
                for (String value : list) {
                    Log.d(TAG, "Get json : " + value);
                }
            }


        //툴바 설정-오류로 주석처리함
        toolbar = (Toolbar) findViewById(R.id.GPSToolBar);
        //setSupportActionBar(toolbar);
        //ActionBar ab = getSupportActionBar();

        //지도 켜져있을때는 휴대폰화면이 잠기지않게함
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.frame_gps_map);//레이아웃-자바파일 맞춤

        mLayout = findViewById(R.id.Map_layout);

        //업데이트 주기 설정
        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);


        //카메라 설정
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //지도 프레그먼트(레이아웃) 설정
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    //툴바 뒤로가기 클릭시
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //동작안함
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickList(View view){
        //경로를 텍스트로 뿌려줄때 데이터 다른 레이아웃으로 이동
        Intent intent = new Intent(this, Timeline.class);
//        intent.putExtra("getLocation",textLocation);
//        intent.putExtra("getTime",formatDate);
//        intent.putExtra("getBeforeLocation",textBeforeLocation);
//        intent.putExtra("getLocationList",locationList);
//        intent.putExtra("getTImeList",timeList);
//        intent.putExtra("gettimeHistoryList",timeHistoryList);
        ((Timeline)Timeline.mContext).listv(userID);
        startActivity(intent);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.d(TAG, "onMapReady :");

        mMap = googleMap;

        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        setDefaultLocation();



        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            startLocationUpdates(); // 3. 위치 업데이트 시작


        }else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                        ActivityCompat.requestPermissions( TimelineMap.this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }).show();


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            //위치가져오기
            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);

                currentPosition
                        = new LatLng(location.getLatitude(), location.getLongitude());

                //경로 그리기 위한 장치-이전위치와 현위치로 경로를 그림
                if (beforeLocation==null){
                    beforeLocation=location;
                }

                String markerTitle = getCurrentAddress(currentPosition,userID);
                String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
                        + " 경도:" + String.valueOf(location.getLongitude());

                Log.d(TAG, "onLocationResult : " + markerSnippet);

                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location, beforeLocation, markerTitle, markerSnippet);

                //경로를 그리기 위한 장치-이전위치 업데이트
                if (!beforeLocation.equals(location)) {
                    beforeLocation = location;
                    Log.d("이전 위치 업데이트 결과-------:", beforeLocation.toString());
                }
                mCurrentLocatiion = location;
            }
            DBDelete();


        }

    };


    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        }else {

            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);



            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED) {

                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }


            Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates");

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            if (checkPermission())
                mMap.setMyLocationEnabled(true);

        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        if (checkPermission()) {

            Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates");
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            if (mMap!=null)
                // 카메라 현재위치로 옮김
                mMap.setMyLocationEnabled(true);

        }


    }
    private void setStringArrayPref(String key, ArrayList polylines) {
        // Activity가 종료되기 전에 저장한다.
        //SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = prefs.edit();
        //SharedPreferencess에서 arraylist는 json으로 바꿔서 저장후 로드할때 다시 바꿔줌
        JSONArray a = new JSONArray();
        for (int i = 0; i < polylines.size(); i++) {
            a.put(polylines.get(i));
        }
        if (!polylines.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }

        //최종 커밋
        editor.commit();
    }

    private ArrayList<String> getStringArrayPref(Context context, String key) {
        //액티비티 생성후 다시 데이터 가져오기
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //json으로 바꾼 데이터를 다시 원상복구
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    @Override
    protected void onStop() {

        setStringArrayPref(SETTINGS_PLAYER_JSON, polylines);

        super.onStop();

        if (mFusedLocationClient != null) {

            Log.d(TAG, "onStop : call stopLocationUpdates");
        }
    }




    public String getCurrentAddress(LatLng latlng,String userID) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            long now = System.currentTimeMillis();
            // 현재시간을 date 변수에 저장한다.
            Date date = new Date(now);
            // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
            SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm aa");
            SimpleDateFormat Time = new SimpleDateFormat("yyyy-MM-dd HH:mm aa");
            timeHistory = Time.format(date);
            // nowDate 변수에 값을 저장한다.
            formatDate = sdfNow.format(date);

            Log.d(TAG, "현재시간 : " + formatDate);

            Log.d("변환주소",addresses.get(0).getAddressLine(0).toString());
            textLocation = addresses.get(0).getAddressLine(0).toString();
            //위치 중복 저장 방지 장치
            if (textBeforeLocation==null) {
                textBeforeLocation = addresses.get(0).getAddressLine(0).toString();
            }Log.d("이전 위치",textBeforeLocation);
        }
        //위치 중복 저장 방지 장치
        DBHelper DBHelper = new DBHelper(this);

        //테이블에 데이터가 없을때
        SQLiteDatabase dbSelect = DBHelper.getReadableDatabase();
        String sqlSelect = "select * from RouteHistory where mb_id='"+userID+"' order by date";
        Cursor cursor = dbSelect.rawQuery(sqlSelect, null);
        System.out.println("insert(select) 행 개수 ------: "+cursor.getCount());
        if (cursor.getCount()==0){
            SQLiteDatabase db = DBHelper.getWritableDatabase();
            String sqlInsert = "insert into RouteHistory(mb_id,mb_location,date,time) values('"+userID+"','"+textLocation+"','"+timeHistory+"','"+formatDate+"')";
            db.execSQL(sqlInsert);
            db.close();
            SQLiteDatabase dbSelectInsert = DBHelper.getReadableDatabase();
            String sqlSelectInsert = "select * from RouteHistory where mb_id='"+userID+"' order by date";
            Cursor cursorS = dbSelectInsert.rawQuery(sqlSelectInsert, null);
            System.out.println("insert후 (select) 행 개수 ------: "+cursorS.getCount());
            cursorS.close();
            dbSelectInsert.close();
        }
        else if(cursor.getCount()!=0){
            cursor.moveToLast();
            String location = cursor.getString(cursor.getColumnIndex("mb_location"));
            Log.d("db 마지막위치 확인-------:", location);
            if (!textBeforeLocation.equals(location) && !textBeforeLocation.equals(textLocation)){
                SQLiteDatabase db = DBHelper.getWritableDatabase();
                String sqlInsert = "insert into RouteHistory(mb_id,mb_location,date,time) values('"+userID+"','"+textLocation+"','"+timeHistory+"','"+formatDate+"')";
                db.execSQL(sqlInsert);
                db.close();
                if (!textBeforeLocation.equals(textLocation)) {
                    textBeforeLocation = textLocation;
                    Log.d("db 넣고 이전 위치 업데이트", textBeforeLocation);

                }
            }
        }cursor.close();
        dbSelect.close();
        SQLiteDatabase dbSelectRow = DBHelper.getReadableDatabase();
        String sqlSelectRow = "select * from RouteHistory where mb_id='"+userID+"' order by date";
        Cursor cursorR = dbSelectRow.rawQuery(sqlSelectRow, null);
        while (cursorR.moveToNext()) {
            // 첫번째에서 다음 레코드가 없을때까지 읽음
            String id = cursorR.getString(cursorR.getColumnIndex("mb_id"));
            String location = cursorR.getString(cursorR.getColumnIndex("mb_location"));   // 두번째 속성
            String datetime = cursorR.getString(cursorR.getColumnIndex("date"));
            String time = cursorR.getString(cursorR.getColumnIndex("time"));
            System.out.println("sqlDB 테이블 출력" + id + "," + location + "," + datetime + "," + time);
        }
        Log.d("테이블 행 개수", String.valueOf(cursorR.getCount()));
        cursorR.close();
        dbSelectRow.close();
        return addresses.get(0).getAddressLine(0).toString();
    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void setCurrentLocation(Location location,Location beforeLocation, String markerTitle, String markerSnippet) {

        //핑,경로 그리기
        if (currentMarker != null) currentMarker.remove();

        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng beforeCurrentLatLng = new LatLng(beforeLocation.getLatitude(), beforeLocation.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        currentMarker = mMap.addMarker(markerOptions);

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        polylines = new ArrayList<Polyline>();
        if (!beforeCurrentLatLng.equals(currentLatLng)) {
            Log.d("이전위치 확인-------:", beforeCurrentLatLng.toString());
            polylineOptions.add(currentLatLng, beforeCurrentLatLng);
            mMap.addPolyline(polylineOptions);
            polylines.add(this.mMap.addPolyline(polylineOptions));
        }


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mMap.moveCamera(cameraUpdate);
    }


    public void setDefaultLocation() {


        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";


        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);

    }


    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);



        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            return true;
        }

        return false;

    }



    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
                startLocationUpdates();
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {


                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();

                }else {


                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();
                }
            }

        }
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(TimelineMap.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d(TAG, "onActivityResult : GPS 활성화 되있음");


                        needRequest = true;

                        return;
                    }
                }

                break;
        }
    }

    public void DBDelete(){
        Calendar week = Calendar.getInstance();
        week.add(Calendar.DATE , -14);
        String beforeWeek = new SimpleDateFormat("yyyy-MM-dd").format(week.getTime());
        System.out.println(beforeWeek);

        DBHelper DBHelper = new DBHelper(this);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String sqlSelectData= "select exists ( select * FROM RouteHistory WHERE mb_id='"+userID+"' and date LIKE '%"+beforeWeek+"%') as success";
        Cursor cursor = db.rawQuery(sqlSelectData, null);
        if(cursor != null && cursor.getCount() !=0) {
            cursor.moveToFirst();
            int success = cursor.getInt(cursor.getColumnIndex("success"));
            if (success > 0) {
                String sqlDelete = "DELETE FROM RouteHistory WHERE mb_id='test' and date LIKE '%"+ beforeWeek +"%'";
                db.execSQL(sqlDelete);
            }cursor.close();
            db.close();
        }
        db.close();
    }


}
