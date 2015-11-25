package com.cmu.qiuoffer.DAO;

public interface ReservationDAO {
	/**
	 * Make a reservation
	 * @param userId
	 * @param seatId
	 * @param time
	 * @param date
	 * @param duration
	 * @return
	 */
	public boolean makeReservation(String userId, int seatId, String time, String date, double duration);
	
	public boolean cancelReservation(int reservationId);
}
