package com.cookandroid.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cookandroid.myapplication.Giofencing.GpsService;
import com.cookandroid.myapplication.Gps.SubActivity;
import com.cookandroid.myapplication.join.Register;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};

    DrawerLayout drawerLayout;
    private Toolbar mToolbar;

    private TextView getDate;
    private TextView getDateTime;
    private TextView getKorea;
    private TextView getForeign;

    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

    String year = yearFormat.format(currentTime);
    String month = monthFormat.format(currentTime);
    String day = dayFormat.format(currentTime);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //GioFencing Part
        if (checkLocationServicesStatus()) {
            checkRunTimePermission();
        }

        String userID = getIntent().getStringExtra("userID");

        Intent GpsServiceIntent = new Intent(this, GpsService.class);
        GpsServiceIntent.putExtra("userID",userID);
        startService(GpsServiceIntent);
        // ------------------



        // 이거 뭐임?
       /* getKorea = (TextView) findViewById(R.id.getKorea);
        getDate = (TextView) findViewById(R.id.getDate);
        getDateTime = (TextView) findViewById(R.id.getDateTime);
        getForeign = (TextView) findViewById(R.id.getForeign);

        getDate.setText(year + "." + month + "." + day + " 현재 코로나 위기 경보 ");
        getDateTime.setText(year + "." + month + "." + day + " 00:00 기준");


        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson");
        try {
            urlBuilder.append("?" + URLEncoder.encode("SeviceKey", "UTF-8") + "=N7%2FsYqKdRUE7yDEZLUn4sOX8s2P7Ole7gMahYwt6bSx1vIbK3QW2IRDKOzX7pdgOWyjrAX0XAmNMPqdVrrJIww%3D%3D");
            urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode("N7%2FsYqKdRUE7yDEZLUn4sOX8s2P7Ole7gMahYwt6bSx1vIbK3QW2IRDKOzX7pdgOWyjrAX0XAmNMPqdVrrJIww%3D%3D", "UTF-8")); *//*공공데이터포털에서 받은 인증키*//*
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); *//*페이지번호*//*
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); *//*한 페이지 결과 수*//*
            urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "=" + URLEncoder.encode("20200310", "UTF-8")); *//*검색할 생성일 범위의 시작*//*
            urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "=" + URLEncoder.encode("20200315", "UTF-8")); *//*검색할 생성일 범위의 종료*//*
//    필수


            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            getKorea.setText(sb);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/
        mToolbar = (Toolbar) findViewById(R.id.mToolBar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.homberger_icon);
        actionBar.setDisplayShowTitleEnabled(false);


        drawerLayout = findViewById(R.id.drawer_layout);
NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
navigationView.setNavigationItemSelectedListener(this);



}
@Override
public boolean onNavigationItemSelected(MenuItem item){
        int id=item.getItemId();

        switch (id){
            case R.id.navigation_item_home:
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
            case R.id.navigation_item_news:
                intent=new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                break;
            case R.id.navigation_item_timeline:
                intent=new Intent(getApplicationContext(),SubActivity.class);
                startActivity(intent);
                break;
            case R.id.navigation_item_nfc:
                intent=new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
}


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
                return super.onOptionsItemSelected(item); }
    // 퍼미션 체크 part
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            System.out.println("testtestest"+grandResults.length);
            boolean check_result = true;
            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                System.out.println(result);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }
            if (check_result) {
                //위치 값을 가져올 수 있음
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }

        }
    }
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    void checkRunTimePermission() {
        System.out.println("checkRunTimePermission");
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasBackgroundLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
        hasBackgroundLocationPermission == PackageManager.PERMISSION_GRANTED) { System.out.println("전부 권한을 가짐");
        } else {
            System.out.println("전부 권한을 가지지 못함");
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else{
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }
    }


    // -------------------


        }
