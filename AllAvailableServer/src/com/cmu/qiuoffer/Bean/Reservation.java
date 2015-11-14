package com.cmu.qiuoffer.Bean;

public class Reservation {
	private int reseavationId;
	private String date;
	private String time;
	private double duration;
	private String userId;
	private int seatId;

	public int getReseavationId() {
		return reseavationId;
	}

	public void setReseavationId(int reseavationId) {
		this.reseavationId = reseavationId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
}
