package com.cmu.qiuoffer.Dao;

import java.util.List;

import com.cmu.qiuoffer.Entities.CommentBean;


/**
 * AADao Class
 * @author Dudaxi Huang
 * @version 1.0
 * @since 11/23/2015
 */

public class AADAO {
	

	/**
	 * Get all comments of a room
	 * @param roomId Room id
	 * @return All comments of a room in a list
	 */
	public List<CommentBean> getComments(int roomId) {
		return null;
	}
	
	/**
	 * Make a comment on a room
	 * @param userId 
	 * @param roomId
	 * @param content
	 * @param pic
	 * @return
	 */
	public boolean makeComment(String userId, int roomId, String content, String pic) {
		return false;
	}
	
	/**
	 * Make a reservation
	 * @param userId
	 * @param seatId
	 * @param time
	 * @param date
	 * @param duration
	 * @return
	 */
	public boolean makeReservation(String userId, int seatId, String time, String date, double duration) {
		return false;
	}
	
	public boolean cancelReservation(int reservationId) {
		return false;
	}
	
	/**
	 * Change the lock of a class
	 * @param roomId
	 * @param lock
	 * @return
	 */
	public boolean changeLock(int roomId, boolean lock) {
		return false;
	}
	
	/**
	 * Get the building id of the roomId
	 * @param roomId
	 * @return
	 */
	public int getBuildingId(int roomId) {
		return 0;
	}
	
	/**
	 * Check if a seat is available or not
	 * @param seatId
	 * @return
	 */
	public boolean checkStatus(int seatId) {
		return false;
	}
	
	/**
	 * Change the status of a seat
	 * @param seatId
	 * @param status
	 * @return
	 */
	public boolean changeStatus(int seatId, boolean status) {
		return false;
	}
	
	/**
	 * Return the room id of a given seat
	 * @param seatId
	 * @return
	 */
	public int getRoomId(int seatId) {
		return 0;
	}
	
	/**
	 * Check if user name and password matches
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean loginCheck(String email, String password) {
		return false;
	}
	
	/**
	 * Check if a user is admin or not
	 * @param email
	 * @return
	 */
	public boolean checkType(String email) {
		return false;
	}
}
