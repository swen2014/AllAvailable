package com.cmu.smartphone.allavailable.model;

/**
 * Created by wangxi on 11/6/15.
 */
public class ScheduleListItem {
    private String time;
    private String place;

    public ScheduleListItem() {
    }

    public ScheduleListItem(String time, String place) {
        this.time = time;
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
