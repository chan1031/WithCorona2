package com.cookandroid.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cookandroid.myapplication.Giofencing.GpsService;
import com.cookandroid.myapplication.login.LoginPage;

public class Option extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_option);


    mToolbar = (Toolbar) findViewById(R.id.OptionToolBar);
    setSupportActionBar(mToolbar);
    final ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        Button goPassChange=(Button)findViewById(R.id.GoPassChange);
        goPassChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), PasswordChange.class);
                startActivity(intent);
            }
        });

        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                SharedPreferences sp = getSharedPreferences("store", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                Toast.makeText(getApplicationContext() ,"로그아웃 하셨습니다.", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(Option.this, GpsService.class);
                stopService(intent2);
                Intent intent3 = new Intent(Option.this,LoginPage.class);
                startActivity(intent3);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    }







