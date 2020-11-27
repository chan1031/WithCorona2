package com.cookandroid.myapplication.Gps;

public class TImeline_Iist_Item {

    private int list_image;
    private String position;
    private String time;
    private String location;

    public TImeline_Iist_Item(int list_image, String position, String time, String location) {
        this.list_image = list_image;
        this.position = position;
        this.time = time;
        this.location = location;
    }

    public int getList_image() {
        return list_image;
    }

    public void setList_image(int list_image) {
        this.list_image = list_image;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
