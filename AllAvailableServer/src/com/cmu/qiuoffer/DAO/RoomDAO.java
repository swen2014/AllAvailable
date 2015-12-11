package com.cmu.qiuoffer.DAO;

import java.util.List;

import com.cmu.qiuoffer.Entities.RoomBean;

/**
 * Define the Room operations
 * 
 * @author Dudaxi Huang
 * @version 1.0
 */
public interface RoomDAO {
	/**
	 * Change the lock of a class
	 * 
	 * @param roomId
	 * @param lock
	 * @return whether change lock status succeeds
	 */
	public boolean changeLock(int roomId, boolean lock);

	/**
	 * Get rooms of the building
	 * 
	 * @param buildingId
	 * @return the room objects
	 */
	public List<RoomBean> getRooms(int buildingId);

	/**
	 * Create a new room
	 * 
	 * @param room
	 */
	public void createRoom(RoomBean room);
}
