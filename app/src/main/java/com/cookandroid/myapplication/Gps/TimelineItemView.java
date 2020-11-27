package com.cookandroid.myapplication.Gps;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cookandroid.myapplication.R;

public class TimelineItemView extends LinearLayout {

    ImageView iconImage;
    TextView textPosition;
    TextView textTime;
    TextView textLocation;


    public TimelineItemView(Context context) {
        super(context);
        init(context);
    }

    public TimelineItemView(Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.timeline_listview_item,this,true);

        iconImage = findViewById(R.id.iconImage);
        textPosition = findViewById(R.id.textPosition);
        textTime = findViewById(R.id.textTime);
        textLocation = findViewById(R.id.textLocation);
    }

    public void setList_image(int list_image){
        iconImage.setImageResource(list_image);
    }
    public void  setPosition(String position){
        textPosition.setText(position);
    }
    public void  setTime(String time){
        textTime.setText(time);
    }
    public void  setLocation(String location){
        textLocation.setText(location);
    }

}
