package com.cookandroid.myapplication.login;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cookandroid.myapplication.Gps.SubActivity;
import com.cookandroid.myapplication.R;
import com.cookandroid.myapplication.join.Join;
import com.google.android.material.navigation.NavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


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


        getKorea = (TextView) findViewById(R.id.getKorea);
        getDate = (TextView) findViewById(R.id.getDate);
        getDateTime = (TextView) findViewById(R.id.getDateTime);
        getForeign = (TextView) findViewById(R.id.getForeign);

        getDate.setText(year + "." + month + "." + day + " 현재 코로나 위기 경보 ");
        getDateTime.setText(year + "." + month + "." + day + " 00:00 기준");


        //Option 버튼
        Button OptionButton=(Button)findViewById(R.id.Option_Button);
        OptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Ointent=new Intent(getApplicationContext(),Option.class);
                startActivity(Ointent);
            }
        });





        TextView DECIDE_cnt= (TextView) findViewById(R.id.DECIDE_CNT);
        TextView DEATH_cnt= (TextView) findViewById(R.id.DEATH_CNT);
        TextView EXAM_cnt= (TextView) findViewById(R.id.EXAM_CNT);
        TextView CLEAR_cnt= (TextView) findViewById(R.id.CLEAR_CNT);
        TextView ACC_EXAM_cnt= (TextView) findViewById(R.id.ACC_EXAM_CNT);
        //파싱된 결과확인!
        ArrayList<String>DEATH_Count=new ArrayList<String>();
        boolean initem = false, STATE_DT1 = false, STATE_TIME1 = false, DECIDE_CNT1 = false, CLEAR_CNT1 = false, EXAM_CNT1 = false;
        boolean DEATH_CNT1 = false, CARE_CNT1 = false, RESUTL_NEG_CNT1 = false, ACC_EXAM_CNT1 = false, ACC_EXAM_COMP_CNT1 = false;

        String STATE_DT = null, STATE_TIME = null, DECIDE_CNT = null, CLEAR_CNT = null, EXAM_CNT = null, DEATH_CNT = null, CARE_CNT = null, RESUTL_NEG_CNT = null;
        String ACC_EXAM_CNT = null, ACC_EXAM_COMP_CNT = null;

        Date date_now = new Date(System.currentTimeMillis());
        java.text.SimpleDateFormat nowDate = new java.text.SimpleDateFormat("yyyyMMdd");
        StrictMode.enableDefaults();
        try {
            String urlstr = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey=%2F3rq7E7ZGEQNvXiyQaKe6e2CAeuptfcprA0W4pB0mA9hQxN8f1g6jesOAnh9h%2F%2FttPD0glBdwD8QwA4TPzW3IQ%3D%3D&pageNo=1&numOfRows=10&startCreateDt=&endCreateDt=&endCreateDt=";
             //검색 URL부분

            URL url=new URL(urlstr);

            URLConnection connection=url.openConnection();
            connection.setDoOutput(true);

            BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));


            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(in);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("stateDt")) {
                            STATE_DT1 = true;
                        }
                        if (parser.getName().equals("stateTime")) {
                            STATE_TIME1 = true;
                        }
                        if (parser.getName().equals("decideCnt")) {
                            DECIDE_CNT1 = true;
                        }
                        if (parser.getName().equals("clearCnt")) {
                            CLEAR_CNT1 = true;
                        }
                        if (parser.getName().equals("examCnt")) {
                            EXAM_CNT1 = true;
                        }
                        if (parser.getName().equals("deathCnt")) {
                            DEATH_CNT1 = true;
                        }
                        if (parser.getName().equals("careCnt")) {
                            CARE_CNT1 = true;
                        }
                        if (parser.getName().equals("resutlNegCnt")) {
                            RESUTL_NEG_CNT1 = true;
                        }
                        if (parser.getName().equals("accExamCnt")) {
                            ACC_EXAM_CNT1 = true;
                        }
                        if (parser.getName().equals("accExamCompCnt")) {
                            ACC_EXAM_COMP_CNT1 = true;
                        }

                        break;

                    case XmlPullParser.TEXT:
                        if (STATE_DT1) {
                            STATE_DT = parser.getText();
                            STATE_DT1 = false;

                        }
                        if (STATE_TIME1) {
                            STATE_TIME = parser.getText();
                            STATE_TIME1 = false;

                        }
                        if (DECIDE_CNT1) {
                            DECIDE_CNT = parser.getText();
                            DECIDE_CNT1 = false;

                        }
                        if (CLEAR_CNT1) {
                            CLEAR_CNT = parser.getText();
                            CLEAR_CNT1 = false;
                        }
                        if (EXAM_CNT1) {
                            EXAM_CNT = parser.getText();
                            EXAM_CNT1 = false;
                        }
                        if (DEATH_CNT1) {
                            DEATH_CNT = parser.getText();
                            DEATH_CNT1 = false;
                        }
                        if (CARE_CNT1) {
                            CARE_CNT = parser.getText();
                            CARE_CNT1 = false;
                        }
                        if (RESUTL_NEG_CNT1) {
                            RESUTL_NEG_CNT = parser.getText();
                            RESUTL_NEG_CNT1 = false;
                        }
                        if (ACC_EXAM_CNT1) {
                            ACC_EXAM_CNT = parser.getText();
                            ACC_EXAM_CNT1 = false;
                        }
                        if (ACC_EXAM_COMP_CNT1) {
                            ACC_EXAM_COMP_CNT = parser.getText();
                            ACC_EXAM_COMP_CNT1 = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {

                            DECIDE_cnt.setText(DECIDE_cnt.getText()+DECIDE_CNT + " 명");
                            DEATH_cnt.setText(DEATH_cnt.getText()+DEATH_CNT+"명");
                            CLEAR_cnt.setText(DEATH_cnt.getText()+CLEAR_CNT+"명");
                            EXAM_cnt.setText(EXAM_cnt.getText()+EXAM_CNT+"명");
                            ACC_EXAM_cnt.setText(ACC_EXAM_cnt.getText()+ACC_EXAM_CNT+"건");







                        }
                        initem = false;
                        break;
                }
                parserEvent = parser.next();

            }

        } catch (Exception e) {

        }


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
                intent=new Intent(getApplicationContext(),Join.class);
                startActivity(intent);
                break;
            case R.id.navigation_item_timeline:
                intent=new Intent(getApplicationContext(),SubActivity.class);
                startActivity(intent);
                break;
            case R.id.navigation_item_nfc:
                intent=new Intent(getApplicationContext(),Option.class);
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



        }


