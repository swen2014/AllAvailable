package com.cmu.qiuoffer.DAO;

import java.util.List;

import com.cmu.qiuoffer.Entities.SeatBean;

public interface SeatDAO {

	/**
	 * Change the status of a seat
	 * 
	 * @param seatId
	 * @param status
	 * @return
	 */
	public boolean changeStatus(int seatId, boolean status);

	public List<SeatBean> getSeats(int roomId);

	public boolean queryOccupied(int seatId, String date, String time,
			double duration);
}
