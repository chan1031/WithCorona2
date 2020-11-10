package com.cookandroid.myapplication.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://49.172.168.109:1228/Login.php";
    private Map<String, String> map;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        //getParams에 넣을 Map을 정의한다.
        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userPassword",userPassword);

    }

    //getParams는 map의 형태로 서버에 데이터를 전송한다.
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
