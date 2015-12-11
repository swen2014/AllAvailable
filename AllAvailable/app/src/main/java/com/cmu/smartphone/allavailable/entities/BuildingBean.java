package com.cmu.smartphone.allavailable.entities;

import java.io.Serializable;

/**
 * BuildingBean Class
 *
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public class BuildingBean implements Serializable {
    private int buildingId;
    private String buildingName;

    /**
     * Get the Building's Id
     *
     * @return
     */
    public int getBuildingId() {
        return buildingId;
    }

    /**
     * Set the Building's Id
     *
     * @param buildingId the id of the building
     */
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    /**
     * Get the building's name
     *
     * @return the building's name
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * Set the building's name
     *
     * @param buildingName the name of the building
     */
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

}
