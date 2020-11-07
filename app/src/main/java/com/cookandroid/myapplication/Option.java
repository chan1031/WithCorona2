package com.cookandroid.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.cookandroid.myapplication.R;

public class Option extends Fragment {
    protected Activity mActivity;
    private View view;
    private TextView password;
    private TextView locationSet;
    private int flagNum;
    Toolbar toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frame_option, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.OptionToolBar);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        if (activity != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        ab.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return null;
    }
    }







