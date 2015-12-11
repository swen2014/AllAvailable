package com.cmu.smartphone.allavailable.model;

import java.io.Serializable;

/**
 * The List Item of the Schedule
 *
 * @author Xi Wang
 * @version 1.0
 */
public class ScheduleListItem implements Serializable {
    private String time;
    private String place;

    /**
     * The default constructor
     */
    public ScheduleListItem() {
    }

    /**
     * The constructor with the time and place
     *
     * @param time
     * @param place
     */
    public ScheduleListItem(String time, String place) {
        this.time = time;
        this.place = place;
    }

    /**
     * Get the time of the schedule
     *
     * @return the time of the schedule
     */
    public String getTime() {
        return time;
    }

    /**
     * Set the time of the schedule
     *
     * @param time the time of the schedule
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Get the place of the schedule
     *
     * @return the place of the schedule
     */
    public String getPlace() {
        return place;
    }

    /**
     * Set the place of the schedule
     *
     * @param place the place of the schedule
     */
    public void setPlace(String place) {
        this.place = place;
    }
}
