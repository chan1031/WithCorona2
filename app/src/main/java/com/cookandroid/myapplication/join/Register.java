package com.cookandroid.myapplication.join;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cookandroid.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private EditText et_id;
    private EditText et_pass;
    private EditText et_name;
    private EditText et_tel;
    private EditText et_passCheck;

    private static int idFlag = 0;
    private static int validateInt =0;

    private Button idCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_join);

        //id 매칭
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_passCheck = findViewById(R.id.et_passCheck);
        et_name = findViewById(R.id.et_name);
        et_tel = findViewById(R.id.et_tel);

        idCheck = findViewById(R.id.IdCheck);

        // 회원가입클릭시
        Button nextButton=(Button)findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userPassCheck = et_passCheck.getText().toString();
                String userTel = et_tel.getText().toString();
                String userName = et_name.getText().toString();

                //유효성 검사 메서드
                validateCheck(userID,userPass,userPassCheck,userTel,userName);

                //비밀번호 재입력과 아이디 중복체크 여부 확인
                if(userPass.equals(userPassCheck) && idFlag == 1 && validateInt == 1){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                // Register php의 불린 값을 요청
                                boolean success = jsonObject.getBoolean("success");
                                if (success){
                                    // 회원가입에 성공
                                    Toast.makeText(getApplicationContext(),"회원가입에 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, RegisterSuccess.class);
                                    startActivity(intent);
                                }else{ // 회원가입에 실패
                                    Toast.makeText(getApplicationContext(),"회원가입에 실패", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    };
                    RegisterRequest registerRequest = new RegisterRequest(userID,userPass,userName,userTel, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Register.this);
                    queue.add(registerRequest);
                }else{
                    if(idFlag==1){
                        Toast.makeText(getApplicationContext(),"패스워드가 같지 않습니다.", Toast.LENGTH_SHORT).show();
                        et_passCheck.requestFocus();
                    }else if(idFlag==0){
                        Toast.makeText(getApplicationContext(),"아이디중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        //idCheck
        idCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                // Register php의 불린 값을 요청
                                boolean success = jsonObject.getBoolean("success");
                                if (success){
                                    Toast.makeText(getApplicationContext(),"사용가능한 id입니다", Toast.LENGTH_SHORT).show();
                                    idFlag = 1;
                                }else{ // 회원가입에 실패
                                    Toast.makeText(getApplicationContext(),"사용불가능한 id입니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    };
                    IdCheck idCheckRequest = new IdCheck(userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Register.this);
                    queue.add(idCheckRequest);
                }

        });
    }

    public void validateCheck(String userId,String userPass,String userPassCheck,String userTel,String userName){
        if(userId.equals("")) {
            Toast.makeText(getApplicationContext(),"아이디를 입력하세요", Toast.LENGTH_SHORT).show();
            et_id.requestFocus();
        }else if(userPass.equals("")){
            Toast.makeText(getApplicationContext(),"비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            et_pass.requestFocus();
        }else if(userPassCheck.equals("")){
            Toast.makeText(getApplicationContext(),"비밀번호 확인을 입력하세요", Toast.LENGTH_SHORT).show();
            et_passCheck.requestFocus();
        }else if(userName.equals("")){
            Toast.makeText(getApplicationContext(),"이름 입력하세요", Toast.LENGTH_SHORT).show();
            et_name.requestFocus();
        }else if(userTel.equals("")){
            Toast.makeText(getApplicationContext(),"전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
            et_tel.requestFocus();
        }
        else{
            validateInt = 1;
        }

    }

}
