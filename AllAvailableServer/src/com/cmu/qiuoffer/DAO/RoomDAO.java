package com.cmu.qiuoffer.DAO;

public interface RoomDAO {
	/**
	 * Change the lock of a class
	 * @param roomId
	 * @param lock
	 * @return
	 */
	public boolean changeLock(int roomId, boolean lock);
	
	/**
	 * Get the building id of the roomId
	 * @param roomId
	 * @return
	 */
	public int getBuildingId(int roomId);
}
