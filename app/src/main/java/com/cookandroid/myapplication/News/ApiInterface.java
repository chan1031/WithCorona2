package com.cookandroid.myapplication.News;

import com.cookandroid.myapplication.News.Model.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<Headlines> getSpecificData(
            @Query("q") String query,
            @Query("from") String Startdate,
            @Query("to") String Enddate,
            @Query("apiKey") String apiKey
    );
}
