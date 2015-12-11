package com.cmu.qiuoffer.DAO;

import java.util.List;

import com.cmu.qiuoffer.Entities.ReservationView;

/**
 * Define the reservation operations
 * 
 * @author Dudaxi Huang
 * @version 1.0
 */
public interface ReservationDAO {
	/**
	 * Make a reservation
	 * 
	 * @param userId
	 * @param seatId
	 * @param time
	 * @param date
	 * @param duration
	 * @return whether the creation succeeds
	 */
	public boolean makeReservation(String userId, int seatId, String time,
			String date, double duration);

	/**
	 * Cancel the reservation
	 * 
	 * @param reservationId
	 * @return whether the reservation is canceled
	 */
	public boolean cancelReservation(int reservationId);

	/**
	 * Get the reservations
	 * 
	 * @param userEmail
	 * @param date
	 * @param time
	 * @param history
	 * @return the list of the reservations
	 */
	public List<ReservationView> getReservations(String userEmail, String date,
			String time, boolean history);

}
