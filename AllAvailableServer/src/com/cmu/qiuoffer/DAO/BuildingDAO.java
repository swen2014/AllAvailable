package com.cmu.qiuoffer.DAO;

import java.util.List;

import com.cmu.qiuoffer.Entities.BuildingBean;

public interface BuildingDAO {
	/**
	 * Get all building
	 * @param null
	 * @return All building within system
	 */
	public List<BuildingBean> getBuildings();
	
	/**
	 * Make a buidling
	 * @param buildingId 
	 * @param buildingName
	 * @return
	 */
	public boolean createBuilding(int buildingId, String buildingName) ;
}
