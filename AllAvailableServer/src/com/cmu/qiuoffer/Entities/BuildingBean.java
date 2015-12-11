package com.cmu.qiuoffer.Entities;

/**
 * BuildingBean Class
 * 
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public class BuildingBean {
	private int buildingId;
	private String buildingName;

	/**
	 * Get the building id
	 * 
	 * @return the building id
	 */
	public int getBuildingId() {
		return buildingId;
	}

	/**
	 * Set the building id
	 * 
	 * @param buildingId
	 *            the given building id
	 */
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	/**
	 * Get the building name
	 * 
	 * @return the name of the building
	 */
	public String getBuildingName() {
		return buildingName;
	}

	/**
	 * Set the building name
	 * 
	 * @param buildingName
	 *            the given building's name
	 */
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

}
