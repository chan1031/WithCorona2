package com.cookandroid.myapplication.join;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cookandroid.myapplication.R;
import com.cookandroid.myapplication.join.BossRegister.BossRegister;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterSelect extends AppCompatActivity {

    private Button bossBtn;
    private Button customerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_select);

        bossBtn= (Button) findViewById(R.id.bossBtn);
        bossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), BossRegister.class);
                startActivity(intent);
            }
        });

        customerBtn= (Button) findViewById(R.id.customerBtn);
        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

    }
}
