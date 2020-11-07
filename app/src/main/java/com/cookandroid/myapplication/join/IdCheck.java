package com.cookandroid.myapplication.join;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class IdCheck extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://49.172.168.109:1228/Idcheck.php";
    private Map<String, String> map;

    public IdCheck(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID",userID);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
