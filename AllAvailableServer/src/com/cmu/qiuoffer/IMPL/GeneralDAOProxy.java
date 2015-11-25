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
	@Override
	public List<CommentBean> getComments(int roomId) {
		List<CommentBean> re = new LinkedList<CommentBean> ();
		CommentBean cb;
		try {
			conn = mysql.getConnection();
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
			conn.close();
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
	@Override
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
			stmt.setInt(5, roomId);
			stmt.setString(6, pic);
			stmt.execute();
			conn.commit();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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
	@Override
	public boolean makeReservation(String userId, int seatId, String time, String date, double duration) {
		return false;
	}
	
	@Override
	public boolean cancelReservation(int reservationId) {
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("cancelReservation");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, reservationId);
			stmt.execute();
			conn.commit();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Change the lock of a class
	 * @param roomId
	 * @param lock
	 * @return
	 */
	@Override
	public boolean changeLock(int roomId, boolean lock) {
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("changeLock");
			stmt = conn.prepareStatement(sql);
			stmt.setBoolean(1, lock);
			stmt.setInt(2, roomId);
			stmt.execute();
			conn.commit();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Get the building id of the roomId
	 * @param roomId
	 * @return
	 */
	public int getBuildingId(int roomId) {
		int id = -1;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("getBuildingId");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, roomId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				id = rs.getInt("roomId");
				break;
			}
			conn.close();
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Check if a seat is available or not
	 * @param seatId
	 * @return
	 */
	public boolean checkStatus(int seatId) {
		boolean status = false;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("checkStatus");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, seatId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				status = rs.getBoolean("occupied");
				break;
			}
			conn.close();
			return status;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Change the status of a seat
	 * @param seatId
	 * @param status
	 * @return
	 */
	@Override
	public boolean changeStatus(int seatId, boolean status) {
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("changeStatus");
			stmt = conn.prepareStatement(sql);
			stmt.setBoolean(1, status);
			stmt.setInt(2, seatId);
			stmt.execute();
			conn.commit();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Return the room id of a given seat
	 * @param seatId
	 * @return
	 */
	public int getRoomId(int seatId) {
		try {
			int id = -1;
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("changeStatus");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, seatId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				id = rs.getInt("roomId");
				break;
			}
			conn.close();
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Check if user name and password matches
	 * @param email
	 * @param password
	 * @return
	 */
	@Override
	public String loginCheck(String email, String password) {
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("changeStatus");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				if(rs.getInt(1) != 0)
					return email;
			}
			rs.close();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Check if a user is admin or not
	 * @param email
	 * @return
	 */
	@Override
	public boolean checkType(String email) {
		return false;
	}
	
	/**
	 * Get all building
	 * @param null
	 * @return All building within system
	 */
	@Override
	public List<BuildingBean> getBuildings(){
		List<BuildingBean> re = new LinkedList<BuildingBean> ();
		BuildingBean bb;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("getBuilding");
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				bb = new BuildingBean();
				bb.setBuildingId(rs.getInt("buildingId"));
				bb.setBuildingName(rs.getString("name"));
				re.add(bb);
			}
			conn.close();
			return re;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Make a building
	 * @param buildingId 
	 * @param buildingName
	 * @return
	 */
	@Override
	public boolean createBuilding(int buildingId, String buildingName){
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("createBuilding");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, buildingName);
			stmt.execute();
			conn.commit();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void createUser(UserBean user) {
		// TODO Auto-generated method stub
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("createUser");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getName());
			stmt.execute();
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<SeatBean> getSeats(int roomId) {
		// TODO Auto-generated method stub
		List<SeatBean> re = new LinkedList<SeatBean> ();
		SeatBean sb;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("getSeats");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, roomId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				sb = new SeatBean();
				sb.setName(rs.getString("name"));
				sb.setOccupied(rs.getBoolean("occupied"));
				sb.setRoomId(rs.getInt("roomId"));
				sb.setSeatId(rs.getInt("seatId"));
				re.add(sb);
			}
			conn.close();
			return re;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<RoomBean> getRooms(int buildingId) {
		// TODO Auto-generated method stub
		List<RoomBean> re = new LinkedList<RoomBean> ();
		RoomBean rb;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("getRooms");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, buildingId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				rb = new RoomBean();
				rb.setBuildingId(buildingId);
				rb.setCapacity(rs.getInt("capacity"));
				rb.setImgSrc(rs.getString("image_src"));
				rb.setLock_status(rs.getBoolean("lock_status"));
				rb.setName(rs.getString("name"));
				rb.setRoomId(rs.getInt("roomId"));
				rb.setType(rs.getString("type"));
				re.add(rb);
			}
			conn.close();
			return re;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void createRoom(RoomBean room) {
		// TODO Auto-generated method stub
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("createRoom");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, room.getName());
			stmt.setInt(2, room.getCapacity());
			stmt.setBoolean(3, false);
			stmt.setString(4, room.getType());
			stmt.setString(5, room.getImgSrc());
			stmt.setInt(6, room.getBuildingId());
			stmt.execute();
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<ReservationView> getReservations() {
		// TODO Auto-generated method stub
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("getRooms");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, buildingId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				rb = new RoomBean();
				rb.setBuildingId(buildingId);
				rb.setCapacity(rs.getInt("capacity"));
				rb.setImgSrc(rs.getString("image_src"));
				rb.setLock_status(rs.getBoolean("lock_status"));
				rb.setName(rs.getString("name"));
				rb.setRoomId(rs.getInt("roomId"));
				rb.setType(rs.getString("type"));
				re.add(rb);
			}
			conn.close();
			return re;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

}
