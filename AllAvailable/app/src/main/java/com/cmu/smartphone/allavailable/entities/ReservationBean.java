package com.cmu.smartphone.allavailable.entities;

import java.io.Serializable;

/**
 * ReservationBean Class
 *
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public class ReservationBean implements Serializable {
    private int reseavationId;
    private String date;
    private String time;
    private double duration;
    private String userId;
    private int seatId;

    /**
     * Get the reservation id
     *
     * @return the reservation id
     */
    public int getReseavationId() {
        return reseavationId;
    }

    /**
     * Set the reservation id
     *
     * @param reseavationId the reservation id
     */
    public void setReseavationId(int reseavationId) {
        this.reseavationId = reseavationId;
    }

    /**
     * Get the date of the reservation
     *
     * @return the date of the reservation
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date of the reservation
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get the time of the reservation
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Set the time of the reservation
     *
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Get the duration of the reservation
     *
     * @return the duration of the reservation
     */
    public double getDuration() {
        return duration;
    }

    /**
     * Set the duration of the reservation
     *
     * @param duration the duration of the reservation
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * Get the user of the reservation
     *
     * @return the user id of the reservation
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Set the user of the reservation
     *
     * @param userId the given user id of the reservation
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get the seat id of the reservation
     *
     * @return the seat id of the reservation
     */
    public int getSeatId() {
        return seatId;
    }

    /**
     * Set the seat id of the reservation
     *
     * @param seatId the seat id of the reservation
     */
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }
}
