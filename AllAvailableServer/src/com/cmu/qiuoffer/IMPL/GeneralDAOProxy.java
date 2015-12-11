package com.cmu.qiuoffer.IMPL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.cmu.qiuoffer.DAO.BuildingDAO;
import com.cmu.qiuoffer.DAO.CommentDAO;
import com.cmu.qiuoffer.DAO.ReservationDAO;
import com.cmu.qiuoffer.DAO.RoomDAO;
import com.cmu.qiuoffer.DAO.SeatDAO;
import com.cmu.qiuoffer.DAO.UserDAO;
import com.cmu.qiuoffer.DB.MySQL;
import com.cmu.qiuoffer.DB.SQLHelper;
import com.cmu.qiuoffer.Entities.BuildingBean;
import com.cmu.qiuoffer.Entities.CommentBean;
import com.cmu.qiuoffer.Entities.ReservationBean;
import com.cmu.qiuoffer.Entities.ReservationView;
import com.cmu.qiuoffer.Entities.RoomBean;
import com.cmu.qiuoffer.Entities.SeatBean;
import com.cmu.qiuoffer.Entities.UserBean;
import com.cmu.qiuoffer.Util.DateTimeHelper;
import com.cmu.qiuoffer.Util.SQLQueryHelper;

/**
 * GeneralDAOproxy Class
 * 
 * @author Dudaxi Huang
 * @version 1.0
 * @since 11/23/2015
 */
public abstract class GeneralDAOProxy implements BuildingDAO, CommentDAO,
		ReservationDAO, RoomDAO, SeatDAO, UserDAO {
	private MySQL mysql = new MySQL();
	private Connection conn;
	private String sql;
	private SQLQueryHelper helper = new SQLQueryHelper();
	private PreparedStatement stmt;

	/**
	 * Get all comments of a room
	 * 
	 * @param roomId
	 *            Room id
	 * @return All comments of a room in a list
	 */
	@Override
	public List<CommentBean> getComments(int roomId) {
		List<CommentBean> re = new LinkedList<CommentBean>();
		CommentBean cb;
		ResultSet rs = null;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("getComments");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, roomId);

			rs = stmt.executeQuery();
			while (rs.next()) {
				cb = new CommentBean();
				cb.setCommentId(rs.getInt("commentId"));
				cb.setContent(rs.getString("content"));
				cb.setDate(rs.getString("date"));
				cb.setImagePath(rs.getString("image_url"));
				cb.setRoomId(rs.getInt("roomId"));
				cb.setTime(rs.getString("time"));
				cb.setUserId(rs.getString("user_email"));
				re.add(cb);
			}
			return re;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					mysql.close(rs, stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * Make a comment on a room
	 * 
	 * @param userId
	 * @param roomId
	 * @param content
	 * @param pic
	 * @return
	 */
	@Override
	public boolean makeComment(String userId, int roomId, String content,
			String pic) {
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("makeComment");
			stmt = conn.prepareStatement(sql);
			String[] datetime = DateTimeHelper.getDateTime().split(" ");
			stmt.setString(1, content);
			stmt.setString(2, datetime[1]);
			stmt.setString(3, datetime[0]);
			stmt.setString(4, userId);
			stmt.setInt(5, roomId);
			stmt.setString(6, pic);
			stmt.execute();
			conn.commit();
			// conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				try {
					mysql.close(stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

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
	@Override
	public boolean makeReservation(String userId, int seatId, String time,
			String date, double duration) {

		boolean success = false;
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("addReservation");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, time);
			stmt.setString(2, date);
			stmt.setDouble(3, duration);
			stmt.setString(4, userId);
			stmt.setInt(5, seatId);
			stmt.execute();
			conn.commit();
			success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null) {
				try {
					mysql.close(stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return success;
	}

	/**
	 * Cancel the reservation
	 */
	@Override
	public boolean cancelReservation(int reservationId) {
		boolean success = false;
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("cancelReservation");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, reservationId);
			stmt.execute();
			conn.commit();
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null) {
				try {
					mysql.close(stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	/**
	 * Change the lock of a class
	 * 
	 * @param roomId
	 * @param lock
	 * @return
	 */
	@Override
	public boolean changeLock(int roomId, boolean lock) {
		boolean success = false;
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("changeLock");
			stmt = conn.prepareStatement(sql);
			stmt.setBoolean(1, lock);
			stmt.setInt(2, roomId);
			stmt.execute();
			conn.commit();
			success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				mysql.close(stmt, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return success;
	}

	/**
	 * Get the building id of the roomId
	 * 
	 * @param roomId
	 * @return
	 */
	public int getBuildingId(int roomId) {
		int id = -1;
		ResultSet rs = null;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("getBuildingId");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, roomId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				id = rs.getInt("roomId");
				break;
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null) {
				try {
					mysql.close(rs, stmt, conn);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return id;
	}

	/**
	 * Check if a seat is available or not
	 * 
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
			while (rs.next()) {
				status = rs.getBoolean("occupied");
				break;
			}
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null) {
				try {
					mysql.close(stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return status;
	}

	/**
	 * Change the status of a seat
	 * 
	 * @param seatId
	 * @param status
	 * @return
	 */
	@Override
	public boolean changeStatus(int seatId, boolean status) {
		boolean success = false;
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("changeStatus");
			stmt = conn.prepareStatement(sql);
			stmt.setBoolean(1, status);
			stmt.setInt(2, seatId);
			stmt.execute();
			conn.commit();
			success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null) {
				try {
					mysql.close(stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	/**
	 * Return the room id of a given seat
	 * 
	 * @param seatId
	 * @return
	 */
	public int getRoomId(int seatId) {
		int id = -1;
		ResultSet rs = null;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("changeStatus");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, seatId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				id = rs.getInt("roomId");
				break;
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null && rs != null) {
				try {
					mysql.close(rs, stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return id;
	}

	/**
	 * Check if user name and password matches
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	@Override
	public String loginCheck(String email, String password) {
		String result = null;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("loginCheck");
			if (conn == null) {
				System.out.println("connection is null");
			}
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("email") != null) {
					if (rs.getString("password").equals(password)) {
						result = email;
					} else {
						result = SQLHelper.AUTHENTICATION_FAILED;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (sql != null && stmt != null) {
				try {
					mysql.close(stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * Check if a user is admin or not
	 * 
	 * @param email
	 * @return
	 */
	@Override
	public boolean checkType(String email) {
		return false;
	}

	/**
	 * Get all building
	 * 
	 * @param null
	 * @return All building within system
	 */
	@Override
	public List<BuildingBean> getBuildings() {
		List<BuildingBean> re = new LinkedList<BuildingBean>();
		BuildingBean bb;
		ResultSet rs = null;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("getBuilding");
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				bb = new BuildingBean();
				bb.setBuildingId(rs.getInt("buildingId"));
				bb.setBuildingName(rs.getString("name"));
				re.add(bb);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null && rs != null) {
				try {
					mysql.close(rs, stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return re;
	}

	/**
	 * Make a building
	 * 
	 * @param buildingId
	 * @param buildingName
	 * @return
	 */
	@Override
	public boolean createBuilding(int buildingId, String buildingName) {
		boolean success = false;
		try {
			conn = mysql.getConnection();
			conn.setAutoCommit(false);
			sql = helper.getSQLTemplate("createBuilding");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, buildingName);
			stmt.execute();
			conn.commit();
			success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null) {
				try {
					mysql.close(stmt, conn);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return success;
	}

	/**
	 * Create a user
	 */
	@Override
	public void createUser(UserBean user) {
		// TODO Auto-generated method stub
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("createUser");
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getPassword());
			stmt.setString(3,
					user.getEmail().substring(0, user.getEmail().indexOf('@')));
			stmt.execute();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null) {
				try {
					mysql.close(stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Get the seat list
	 */
	@Override
	public List<SeatBean> getSeats(int roomId) {
		// TODO Auto-generated method stub
		List<SeatBean> re = new LinkedList<SeatBean>();
		SeatBean sb;
		ResultSet rs = null;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("getSeats");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, roomId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				sb = new SeatBean();
				sb.setName(rs.getString("name"));
				sb.setOccupied(rs.getBoolean("occupied"));
				sb.setRoomId(rs.getInt("roomId"));
				sb.setSeatId(rs.getInt("seatId"));
				re.add(sb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null) {
				try {
					mysql.close(rs, stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return re;
	}

	/**
	 * Get the room list
	 */
	@Override
	public List<RoomBean> getRooms(int buildingId) {
		List<RoomBean> re = new LinkedList<RoomBean>();
		RoomBean rb;
		ResultSet rs = null;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("getRooms");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, buildingId);
			rs = stmt.executeQuery();
			while (rs.next()) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null) {
				try {
					mysql.close(rs, stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return re;
	}

	/**
	 * Create a room
	 */
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null) {
				try {
					mysql.close(stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Get the reservation list
	 */
	@Override
	public List<ReservationView> getReservations(String userEmail, String date,
			String time, boolean history) {
		List<ReservationView> reservations = new ArrayList<ReservationView>();
		ResultSet rs = null;
		try {
			conn = mysql.getConnection();
			if (history) {
				sql = helper.getSQLTemplate("getHistoryReservations");
			} else {
				sql = helper.getSQLTemplate("getRecentReservations");
			}
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userEmail);
			stmt.setString(2, date);
			stmt.setString(3, userEmail);
			stmt.setString(4, date);
			stmt.setString(5, time);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ReservationView view = new ReservationView();
				BuildingBean building = new BuildingBean();
				building.setBuildingName(rs.getString("building_name"));

				RoomBean rb = new RoomBean();
				int roomId = rs.getInt("room_id");
				rb.setRoomId(roomId);
				rb.setCapacity(rs.getInt("capacity"));
				rb.setImgSrc(rs.getString("image_src"));
				rb.setLock_status(rs.getBoolean("lock_status"));
				rb.setName(rs.getString("room_name"));
				rb.setType(rs.getString("type"));

				SeatBean sb = new SeatBean();
				sb.setName(rs.getString("seat_name"));
				sb.setSeatId(rs.getInt("seatId"));
				sb.setRoomId(roomId);

				ReservationBean reservation = new ReservationBean();
				reservation.setReseavationId(rs.getInt("id"));
				reservation.setDate(rs.getString("date"));
				reservation.setTime(rs.getString("time"));
				reservation.setDuration(rs.getDouble("duration"));
				reservation.setUserId(userEmail);
				reservation.setSeatId(sb.getSeatId());

				view.setBuilding(building);
				view.setRoom(rb);
				view.setSeat(sb);
				view.setReservation(reservation);
				reservations.add(view);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null && rs != null) {
				try {
					mysql.close(rs, stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return reservations;
	}

	/**
	 * Query if the room is occupied at the given time period
	 */
	@Override
	public boolean queryOccupied(int seatId, String date, String time,
			double duration) {
		boolean occupied = false;
		ResultSet rs = null;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("queryOccupied");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, seatId);
			stmt.setString(2, date);
			stmt.setString(3, time);
			rs = stmt.executeQuery();
			if (rs.next()) {
				String lastTime = rs.getString("time");
				double lastDuration = rs.getDouble("duration");

				occupied = DateTimeHelper.checkTimeOverlap(time, lastTime,
						lastDuration);
			}

			if (!occupied) {
				sql = helper.getSQLTemplate("queryOccupied2");
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, seatId);
				stmt.setString(2, date);
				stmt.setString(3, time);
				stmt.setString(4, DateTimeHelper.addTime(time, duration));
				rs = stmt.executeQuery();
				if (rs.next()) {
					occupied = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null && rs != null) {
				try {
					mysql.close(rs, stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return occupied;
	}

	/**
	 * Check if the time is avaialable for the user
	 */
	public boolean isAvailableAtTime(String email, String date, String time,
			double duration) {
		boolean occupied = false;
		ResultSet rs = null;
		try {
			conn = mysql.getConnection();
			sql = helper.getSQLTemplate("queryUserOccupied");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, date);
			stmt.setString(3, time);
			System.out.println(stmt.toString());
			rs = stmt.executeQuery();
			if (rs.next()) {
				String lastTime = rs.getString("time");
				double lastDuration = rs.getDouble("duration");

				occupied = DateTimeHelper.checkTimeOverlap(time, lastTime,
						lastDuration);
			}

			if (!occupied) {
				sql = helper.getSQLTemplate("queryUserOccupied2");
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, email);
				stmt.setString(2, date);
				stmt.setString(3, time);
				stmt.setString(4, DateTimeHelper.addTime(time, duration));
				rs = stmt.executeQuery();
				if (rs.next()) {
					occupied = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null && stmt != null && rs != null) {
				try {
					mysql.close(rs, stmt, conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return occupied;
	}
}
