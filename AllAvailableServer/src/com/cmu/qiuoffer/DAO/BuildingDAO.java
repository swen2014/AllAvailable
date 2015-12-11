package com.cmu.qiuoffer.DAO;

import java.util.List;

import com.cmu.qiuoffer.Entities.BuildingBean;

/**
 * Define the Building Operations
 * 
 * @author Dudaxi Huang
 * @version 1.0
 */
public interface BuildingDAO {
	/**
	 * Get all building
	 * 
	 * @param null
	 * @return All building within system
	 */
	public List<BuildingBean> getBuildings();

	/**
	 * Make a building
	 * 
	 * @param buildingId
	 * @param buildingName
	 * @return
	 */
	public boolean createBuilding(int buildingId, String buildingName);
}
