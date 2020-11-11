package com.cookandroid.myapplication.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cookandroid.myapplication.MainActivity;
import com.cookandroid.myapplication.R;
import com.cookandroid.myapplication.join.RegisterTerms;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity {
    private Button loginbtn;
    private EditText login_id;
    private EditText login_pass;
    private int validateFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        /* 1.선언 */
        login_id = findViewById(R.id.login_id);
        login_pass = findViewById(R.id.login_pass);
        loginbtn = (Button)findViewById(R.id.loginButton);

        //StringRequest에 넣을 responseListner를 선언한다.
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
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
                        System.out.println("로그인한 아이디:"+userID);

                        //로그인 성공 Toast
                        Toast.makeText(getApplicationContext(),"로그인에 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPage.this, MainActivity.class);
                        intent.putExtra("userID",userID);
                        //DTO에 userID의 값을 저장
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

        /* 2.method */

        //로그인 버튼 클릭시 동작
        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String userID = login_id.getText().toString();
                String userPass = login_pass.getText().toString();
                SharedPreferences sharedPreferences= getSharedPreferences("user", MODE_PRIVATE);    // test 이름의 기본모드 설정
                SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                editor.putString("id",userID); // key,value 형식으로 저장
                editor.putString("ps",userPass); // key,value 형식으로 저장
                editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.
                //유효성 검사
                validateCheck(userID,userPass);
                if(validateFlag == 1){
                    LoginRequest loginRequset = new LoginRequest(userID,userPass,responseListener);
                    //RequestQueue를 선언한다.
                    RequestQueue queue = Volley.newRequestQueue(LoginPage.this);
                    //서버에 값을 요청한다.
                    queue.add(loginRequset);
                }
            }
        });
        //회원가입페이지로 이동
        TextView nextButton=(TextView) findViewById(R.id.passSch);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), RegisterTerms.class);
                startActivity(intent);
            }
        });
    }
    public void validateCheck(String id,String pass){
        if(id.equals("")){
            Toast.makeText(getApplicationContext()," 아이디를 입력하세요", Toast.LENGTH_SHORT).show();
            login_id.requestFocus();
        }else if(pass.equals("")){
            Toast.makeText(getApplicationContext(),"비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            login_pass.requestFocus();
        }else{
            validateFlag = 1;
        }
    }

}