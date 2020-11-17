package com.cookandroid.myapplication.NFC;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NfcRequest extends StringRequest {

    // 서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://49.172.168.109:1228/Giofencing.php";
    private Map map;

    public NfcRequest(String userID, String time, String phoneNumber, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("boss_id",userID);
        map.put("time",time);
        map.put("phoneNumber",phoneNumber);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
