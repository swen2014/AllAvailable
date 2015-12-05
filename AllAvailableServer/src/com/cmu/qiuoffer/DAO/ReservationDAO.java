package com.cmu.qiuoffer.DAO;

import java.util.List;

import com.cmu.qiuoffer.Entities.ReservationView;

public interface ReservationDAO {
	/**
	 * Make a reservation
	 * 
	 * @param userId
	 * @param seatId
	 * @param time
	 * @param date
	 * @param duration
	 * @return
	 */
	public boolean makeReservation(String userId, int seatId, String time,
			String date, double duration);

	public boolean cancelReservation(int reservationId);

	public List<ReservationView> getReservations(String userEmail, String date,
			String time, boolean history);

}
