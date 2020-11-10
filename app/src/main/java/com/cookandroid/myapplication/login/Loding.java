package com.cookandroid.myapplication.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cookandroid.myapplication.MainActivity;
import com.cookandroid.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

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
                /* 1.선언 */
                SharedPreferences sharedPreferences= getSharedPreferences("user", MODE_PRIVATE);
                String id = sharedPreferences.getString("id","");
                String ps = sharedPreferences.getString("ps","");
                //StringRequest에 넣을 responseListner를 선언한다.
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 서버로부터 문자열 response를 받아서 처리하는 과정
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            // Register php의 불린 값을 요청
                            boolean success = jsonObject.getBoolean("success");
                            // 로그인에 성공
                            if (success){
                                //php에서 불러온 데이터를 저장
                                String userID = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPassword");

                                Toast.makeText(getApplicationContext(),userID +"님 환영합니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Loding.this, MainActivity.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("userPass",userPass);
                                startActivity(intent);
                            }else{ // 회원가입에 실패
                                Toast.makeText(getApplicationContext(),"아이디와 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                /* 2.동작 */

                //자동로그인
                if(id!="" && ps !="") {
                    //자동으로 로그인한다.
                    LoginRequest loginRequset = new LoginRequest(id,ps,responseListener);
                    //RequestQueue를 선언한다.
                    RequestQueue queue = Volley.newRequestQueue(Loding.this);
                    //서버에 값을 요청한다.
                    queue.add(loginRequset);
                }
                //수동 로그인
                Intent intent = new Intent(getBaseContext(), LoginPage.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}

