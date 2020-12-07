package com.cookandroid.myapplication.Gps;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.myapplication.Gps.locationDB.DBHelper;
import com.cookandroid.myapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Timeline extends AppCompatActivity {

    String getLocation;
    String getTime;
    String getBeforeLocation;
    String dbname = "locationDB";
    String tablename = "RouteHistory";
    String sql;
    SQLiteDatabase db;   // db를 다루기 위한 SQLiteDatabase 객체 생성
    ListView listView;   // ListView 객체 생성
    String[] result;

    String userID;

    String formatDate;
    String datePickM;
    String datePick;
    String sqlSelect;

    public static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_gps);
        mContext = this;


        userID = getIntent().getStringExtra("userID");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //db = openOrCreateDatabase(dbname,MODE_PRIVATE,null);

        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd");
        // nowDate 변수에 값을 저장한다.
        formatDate = sdfNow.format(date);

        ListView listView = (ListView) findViewById(R.id.listView);
        ListAdapter adapter = new ListAdapter();
        DBHelper DBHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String sqlSelect = "select * from RouteHistory where not mb_location is null and mb_id='"+userID+"' and date LIKE '%"+ formatDate +"%' order by date";
        // select 문 출력위해 사용하는 Cursor 형태 객체 생성
        Cursor cursor = db.rawQuery(sqlSelect, null);
        int count = cursor.getCount();   // db에 저장된 행 개수를 읽어온다
        result = new String[count];
        System.out.println("-------------Select  행개수--------: "+count);
        if (cursor.getCount()==0 ){
            adapter.addItem(new TImeline_Iist_Item(R.drawable.w, "경로가 없습니다.", "" , ""));
            listView.setAdapter(adapter);
        }else if(cursor != null && cursor.getCount()>0){
            if (count==1) {
                String location = cursor.getString(cursor.getColumnIndex("mb_location"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                adapter.addItem(new TImeline_Iist_Item(R.drawable.w, "이동", time , location));
                listView.setAdapter(adapter);
            }else if (count>1) {
                while(cursor.moveToNext()) {
                    // 첫번째에서 다음 레코드가 없을때까지 읽음
                    String location = cursor.getString(cursor.getColumnIndex("mb_location"));   // 두번째 속성
                    String time = cursor.getString(cursor.getColumnIndex("time"));
                    adapter.addItem(new TImeline_Iist_Item(R.drawable.w, "이동", time, location));
                }
                listView.setAdapter(adapter);
            }
            System.out.println("select ok");
        }
        cursor.close();
        db.close();


    }

    public void listv(String userID){
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
        Date date = new Date(now);
        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd");
        // nowDate 변수에 값을 저장한다.
        formatDate = sdfNow.format(date);

        ListView listView = (ListView) findViewById(R.id.listView);
        ListAdapter adapter = new ListAdapter();
        DBHelper DBHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String sqlSelect = "select * from RouteHistory where not mb_location is null and mb_id='"+userID+"' and date LIKE '%"+ formatDate +"%' order by date";
        // select 문 출력위해 사용하는 Cursor 형태 객체 생성
        Cursor cursor = db.rawQuery(sqlSelect, null);
        int count = cursor.getCount();   // db에 저장된 행 개수를 읽어온다
        result = new String[count];
        System.out.println("-------------Select  행개수--------: "+count);
        if (cursor.getCount()==0 ){
            adapter.addItem(new TImeline_Iist_Item(R.drawable.w, "경로가 없습니다.", "" , ""));
            listView.setAdapter(adapter);
        }else if(cursor != null && cursor.getCount()>0){
            if (count==1) {
                String location = cursor.getString(cursor.getColumnIndex("mb_location"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                adapter.addItem(new TImeline_Iist_Item(R.drawable.w, "이동", time , location));
                listView.setAdapter(adapter);
            }else if (count>1) {
                while(cursor.moveToNext()) {
                    // 첫번째에서 다음 레코드가 없을때까지 읽음
                    String location = cursor.getString(cursor.getColumnIndex("mb_location"));   // 두번째 속성
                    String time = cursor.getString(cursor.getColumnIndex("time"));
                    adapter.addItem(new TImeline_Iist_Item(R.drawable.w, "이동", time, location));
                }
                listView.setAdapter(adapter);
            }
            System.out.println("select ok");
        }
        cursor.close();
        db.close();
    }


    public  class ListAdapter extends BaseAdapter {
        ArrayList<TImeline_Iist_Item> items = new ArrayList<TImeline_Iist_Item>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(TImeline_Iist_Item item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TimelineItemView timelineItemView = null;

            if (convertView == null) {
                timelineItemView = new TimelineItemView(getApplicationContext());
            } else {
                timelineItemView = (TimelineItemView) convertView;
            }
            TImeline_Iist_Item item = items.get(position);
            timelineItemView.setList_image(item.getList_image());
            timelineItemView.setPosition(item.getPosition());
            timelineItemView.setTime(item.getTime());
            timelineItemView.setLocation(item.getLocation());

            return timelineItemView;
        }
    }

    //지도보기
    public void onClickMap(View view) {
        Intent intent = new Intent(this, TimelineMap.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    public void OnClickHandler(View view) {
        Calendar pickedDate = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {//날짜 선택시 이벤트
                        Toast.makeText(getApplicationContext(),"select date : "+ year + "-"+(month+1)+"-"+dayOfMonth,Toast.LENGTH_LONG).show();
                        ListView listView = (ListView) findViewById(R.id.listView);
                        ListAdapter adapter = new ListAdapter();
                        DBHelper DBHelper = new DBHelper(getApplicationContext());
                        SQLiteDatabase db = DBHelper.getReadableDatabase();
                        if (dayOfMonth<10) {
                            datePick = year + "-" + (month + 1) + "-0" + dayOfMonth;
                            sqlSelect = "select * from RouteHistory where not mb_location is null and mb_id='"+userID+"' and date LIKE '%"+datePick+"%' order by date";
                        }
                        else if (dayOfMonth >9){
                            datePickM = year+"-"+(month+1)+"-"+dayOfMonth;
                            sqlSelect = "select * from RouteHistory where not mb_location is null and mb_id='"+userID+"' and date LIKE '%"+datePickM+"%' order by date";
                        }
                        // select 문 출력위해 사용하는 Cursor 형태 객체 생성
                        Cursor cursor = db.rawQuery(sqlSelect, null);

                        if (cursor.getCount()==0){
                            adapter.addItem(new TImeline_Iist_Item(R.drawable.w, "경로가 없습니다.","" , ""));
                            listView.setAdapter(adapter);
                        }
                        else if(cursor.getCount()>0){
                            cursor.moveToFirst();
                            if (cursor.getCount()==1) {
                                String location = cursor.getString(cursor.getColumnIndex("mb_location"));
                                String time = cursor.getString(cursor.getColumnIndex("time"));
                                adapter.addItem(new TImeline_Iist_Item(R.drawable.w, "이동", time , location));
                                listView.setAdapter(adapter);
                            }
                            while(cursor.moveToNext()){
                                String location = cursor.getString(cursor.getColumnIndex("mb_location"));
                                String time = cursor.getString(cursor.getColumnIndex("time"));
                                adapter.addItem(new TImeline_Iist_Item(R.drawable.w, "이동",time , location));

                            }
                        }
                        listView.setAdapter(adapter);
                        cursor.close();
                        db.close();

                    }
                },
                //오늘날짜
                pickedDate.get(Calendar.YEAR),
                pickedDate.get(Calendar.MONTH),
                pickedDate.get(Calendar.DATE)
        );

        //2주간의 날짜만 표시s
        minDate.set(pickedDate.get(Calendar.YEAR),pickedDate.get(Calendar.MONTH),pickedDate.get(Calendar.DATE)-14);
        datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());

        maxDate.set(pickedDate.get(Calendar.YEAR),pickedDate.get(Calendar.MONTH),pickedDate.get(Calendar.DATE));
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

        //datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.setTitle("2주간의 경로만 선택할 수 있습니다.");
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        datePickerDialog.show();
    }

}