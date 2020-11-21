package com.cookandroid.myapplication.News;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cookandroid.myapplication.News.Model.Articles;
import com.cookandroid.myapplication.News.Model.Headlines;
import com.cookandroid.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    final String API_KEY = "163d45ac546043a7933b1a44e3c0013b";
    Adapter adapter;
    List<Articles> articles = new ArrayList<>();
    Date date_now = new Date(System.currentTimeMillis());
    SimpleDateFormat nowDate = new SimpleDateFormat("yyyy-MM-dd");
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);

        final Calendar c = Calendar.getInstance();
        c.setTime(date_now);
        c.add(Calendar.DATE, -7);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String country = getCountry();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("코로나",nowDate.format(c.getTime()),nowDate.format(date_now),API_KEY);
            }
        });
        retrieveJson("코로나",nowDate.format(c.getTime()),nowDate.format(date_now),API_KEY);
    }

    public void retrieveJson(String country,String startDate,String endDate, String apiKey) {
        Call<Headlines> call = ApiClient.getInstance().getApi().getSpecificData(country,startDate,endDate,apiKey);
        swipeRefreshLayout.setRefreshing(true);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(NewsActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(NewsActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
