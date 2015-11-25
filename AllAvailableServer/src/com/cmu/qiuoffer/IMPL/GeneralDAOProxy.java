package com.cmu.qiuoffer.IMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.cmu.qiuoffer.DAO.BuildingDAO;
import com.cmu.qiuoffer.DAO.CommentDAO;
import com.cmu.qiuoffer.DAO.ReservationDAO;
import com.cmu.qiuoffer.DAO.RoomDAO;
import com.cmu.qiuoffer.DAO.SeatDAO;
import com.cmu.qiuoffer.DAO.UserDAO;
import com.cmu.qiuoffer.DB.MySQL;
import com.cmu.qiuoffer.Entities.BuildingBean;
import com.cmu.qiuoffer.Entities.CommentBean;
import com.cmu.qiuoffer.Entities.ReservationView;
import com.cmu.qiuoffer.Entities.RoomBean;
import com.cmu.qiuoffer.Entities.SeatBean;
import com.cmu.qiuoffer.Entities.UserBean;
import com.cmu.qiuoffer.Util.DateTimeHelper;
import com.cmu.qiuoffer.Util.SQLQueryHelper;
/**
 * GeneralDAOproxy Class
 * @author Dudaxi Huang
 * @version 1.0
 * @since 11/23/2015
 */
public abstract class GeneralDAOProxy implements BuildingDAO, CommentDAO, ReservationDAO, RoomDAO, SeatDAO, UserDAO {
	private MySQL mysql = new MySQL();
	private Connection conn;
	private String sql;
	private SQLQueryHelper helper = new SQLQueryHelper();
	private PreparedStatement stmt;

	/**
	 * Get all comments of a room
	 * @param roomId Room id
	 * @return All comments of a room in a list
	 */
	public List<CommentBean> getComments(int roomId) {
		List<CommentBean> re = new LinkedList<CommentBean> ();
		CommentBean cb;
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("getComments");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, roomId);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				cb = new CommentBean();
				cb.setCommentId(rs.getInt("commentId"));
				cb.setContent(rs.getString("content"));
				cb.setDate(rs.getString("date"));
				cb.setImagePath(rs.getString("imagePath"));
				cb.setRoomId(rs.getInt(roomId));
				cb.setTime(rs.getString("time"));
				cb.setUserId(rs.getString("userId"));
				re.add(cb);
			}
			return re;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("makeComment");
			stmt = conn.prepareStatement(sql);
			String[] datetime = DateTimeHelper.getDateTime().split(" ");
			stmt.setString(1, content);
			stmt.setString(2, datetime[0]);
			stmt.setString(3, datetime[1]);
			stmt.setString(4, userId);
			stmt.setString(5, pic);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public String loginCheck(String email, String password) {
		return null;
	}
	
	/**
	 * Check if a user is admin or not
	 * @param email
	 * @return
	 */
	public boolean checkType(String email) {
		return false;
	}
	
	/**
	 * Get all building
	 * @param null
	 * @return All building within system
	 */
	public List<BuildingBean> getBuildings(){
		PreparedStatement ps = null;
		
		
		return null;
	}
	
	/**
	 * Make a building
	 * @param buildingId 
	 * @param buildingName
	 * @return
	 */
	public boolean makeBuilding(int buildingId, String buildingName){
		return false;
	}
	
	@Override
	public ReservationView getReservation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoomBean> getRooms(int buildingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createRoom(RoomBean room) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SeatBean> getSeats(int roomId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUser(UserBean user) {
		// TODO Auto-generated method stub
		
	}
}
