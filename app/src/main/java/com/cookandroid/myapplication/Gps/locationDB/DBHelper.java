package com.cookandroid.myapplication.Gps.locationDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static  final  int DATABACE_VERSION = 1;
    public DBHelper(Context context){
        super(context,"locationDB",null,DATABACE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String locationSQL = "create table RouteHistory("+
                "mb_id varchar(20) not null," +
                "mb_location varchar(700) not null," +
                "date varchar(70) not null," +
                "time vachar(50) not null)";
        db.execSQL(locationSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion==DATABACE_VERSION){
            db.execSQL("drop table RouteHistory");
            onCreate(db);
        }
    }
}
