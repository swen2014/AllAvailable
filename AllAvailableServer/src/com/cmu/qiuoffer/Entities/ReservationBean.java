package com.cmu.qiuoffer.Entities;

/**
 * ReservationBean Class
 * 
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public class ReservationBean {
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
	 * @param reseavationId
	 *            the reservation id
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
	 *            the given date
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
	 *            the given time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Get the duration of the reservation
	 * 
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * Set the duration of the reservation
	 * 
	 * @param duration
	 *            the given duration
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/**
	 * Get the user id
	 * 
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Set the user id
	 * 
	 * @param userId
	 *            the user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Get the seat id
	 * 
	 * @return the seat id
	 */
	public int getSeatId() {
		return seatId;
	}

	/**
	 * Set the seat id
	 * 
	 * @param seatId
	 *            the seat id
	 */
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
}
