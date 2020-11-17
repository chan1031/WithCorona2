package com.cookandroid.myapplication.NFC;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cookandroid.myapplication.Giofencing.GiofencingRequest;
import com.cookandroid.myapplication.Giofencing.GpsService;
import com.cookandroid.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class VisitHistory extends AppCompatActivity {
    TextView text;
    int j;
    String userID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_visit_history);
        Handler timer = new Handler();
        text=findViewById(R.id.history);
        Intent passedIntent = getIntent();

        if(passedIntent != null)
        {
            onNewIntent(passedIntent);

        }
        timer.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(VisitHistory.this, NfcReceive.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        String phoneNumber = ""; // 핸드폰 번호 받을 변수
        Date date_now = new Date(System.currentTimeMillis());
        SimpleDateFormat nowDate = new SimpleDateFormat("yyyy년MM월dd일 HH시mm분");
        String time = nowDate.format(date_now);
        Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        userID = getIntent().getStringExtra("userID");
        if (data != null) {
            try {
                for (int i = 0; i < data.length; i++) {
                    NdefRecord[] recs = ((NdefMessage) data[i]).getRecords();
                    for (j = 0; j < recs.length; j++) {
                        if (recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(recs[j].getType(),
                                NdefRecord.RTD_TEXT)) {
                            byte[] payload = recs[j].getPayload();
                            String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                            int langCodeLen = payload[0] & 0077;
                            phoneNumber += (
                                    new String(payload, langCodeLen + 1, payload.length - langCodeLen - 1, textEncoding)  // <<====핸드폰 번호 받는 부분
                            );
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("TagDispatch", e.toString());
            }

        }
        text.setText(nowDate.format(date_now)+"방문 기록이 확인 되었습니다. \n" + phoneNumber);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        };
        System.out.println("보내려는 아이디는"+userID);
        System.out.println("현재시간은"+time);
        NfcRequest nfcRequest = new NfcRequest(userID,time, phoneNumber,responseListener);
        RequestQueue queue = Volley.newRequestQueue(VisitHistory.this);
        queue.add(nfcRequest);
    }
}
