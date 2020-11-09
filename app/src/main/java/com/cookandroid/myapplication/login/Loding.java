package com.cookandroid.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.myapplication.R;

public class Loding extends AppCompatActivity {
    private Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_loding);
        startLoading();

    }
    private void startLoading(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), LoginPage.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}

