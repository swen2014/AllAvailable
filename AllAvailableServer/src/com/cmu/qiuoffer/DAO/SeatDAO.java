package com.cmu.qiuoffer.DAO;

public interface SeatDAO {
	/**
	 * Check if a seat is available or not
	 * @param seatId
	 * @return
	 */
	public boolean checkStatus(int seatId);
	
	/**
	 * Change the status of a seat
	 * @param seatId
	 * @param status
	 * @return
	 */
	public boolean changeStatus(int seatId, boolean status);
	
	/**
	 * Return the room id of a given seat
	 * @param seatId
	 * @return
	 */
	public int getRoomId(int seatId);
}
