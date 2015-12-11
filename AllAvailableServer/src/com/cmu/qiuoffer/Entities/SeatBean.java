package com.cmu.qiuoffer.Entities;

/**
 * SeatBean Class
 * 
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public class SeatBean {

	private int seatId;
	private boolean occupied;
	private int roomId;
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 *            the given seat id
	 */
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	/**
	 * @return the occupied
	 */
	public boolean isOccupied() {
		return occupied;
	}

	/**
	 * @param occupied
	 *            the occupied to set
	 */
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	/**
	 * Get the room id
	 * 
	 * @return the room id
	 */
	public int getRoomId() {
		return roomId;
	}

	/**
	 * Set the room id
	 * 
	 * @param roomId
	 *            the room id
	 */
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

}
