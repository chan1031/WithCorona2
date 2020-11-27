package com.cookandroid.myapplication.Gps;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.myapplication.R;

import java.util.ArrayList;


public class Timeline extends AppCompatActivity {

    String getLocation;
    String getTime;
    String getBeforeLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_gps);

        Spinner yearSpinner = (Spinner) findViewById(R.id.spinner_year);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        Spinner monthSpinner = (Spinner) findViewById(R.id.spinner_month);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        Spinner daySpinner = (Spinner) findViewById(R.id.spinner_day);
        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        Intent intent = getIntent();
        getLocation = intent.getStringExtra("getLocation");
        getTime = intent.getStringExtra("getTime");
        getBeforeLocation = intent.getStringExtra("getBeforeLocation");
        ArrayList<String> locationList = (ArrayList<String>) intent.getSerializableExtra("getLocationList");
        ArrayList<String> timeList = (ArrayList<String>) intent.getSerializableExtra("getTImeList");

        ListView listView = (ListView) findViewById(R.id.listView);
        ListAdapter adapter = new ListAdapter();
        if (getLocation != null || getTime != null) {
            for (int i = 0; i < locationList.size(); i++) {
                adapter.addItem(new TImeline_Iist_Item(R.drawable.w, "이동", timeList.get(i), locationList.get(i)));
                System.out.println(getLocation);
                System.out.println(locationList.get(i));
            }
            listView.setAdapter(adapter);
        }
        //}


    }

    class ListAdapter extends BaseAdapter {
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
        startActivity(intent);
    }

}