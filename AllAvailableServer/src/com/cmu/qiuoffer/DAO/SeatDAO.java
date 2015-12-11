package com.cmu.qiuoffer.DAO;

import java.util.List;

import com.cmu.qiuoffer.Entities.SeatBean;

/**
 * Define the Seat Operation
 * 
 * @author Dudaxi Huang
 * @version 1.0
 */
public interface SeatDAO {

	/**
	 * Change the status of a seat
	 * 
	 * @param seatId
	 * @param status
	 * @return
	 */
	public boolean changeStatus(int seatId, boolean status);

	/**
	 * Get the seats of the room
	 * 
	 * @param roomId
	 * @return the seat objects
	 */
	public List<SeatBean> getSeats(int roomId);

	/**
	 * Check the seat is occupied at the given time period
	 * 
	 * @param seatId
	 * @param date
	 * @param time
	 * @param duration
	 * @return whether the seat is occupied at the given time period
	 */
	public boolean queryOccupied(int seatId, String date, String time,
			double duration);
}
