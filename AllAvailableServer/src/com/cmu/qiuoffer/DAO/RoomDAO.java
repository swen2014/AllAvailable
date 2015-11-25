package com.cmu.qiuoffer.DAO;

import java.util.List;

import com.cmu.qiuoffer.Entities.RoomBean;

public interface RoomDAO {
	/**
	 * Change the lock of a class
	 * @param roomId
	 * @param lock
	 * @return
	 */
	public boolean changeLock(int roomId, boolean lock);
	
	public List<RoomBean> getRooms(int buildingId);
	
	public void createRoom(RoomBean room);
}
