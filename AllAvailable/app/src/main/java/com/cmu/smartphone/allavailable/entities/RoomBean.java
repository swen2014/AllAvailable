package com.cmu.smartphone.allavailable.entities;

import java.io.Serializable;

/**
 * RoomBean Class
 *
 * @author Xi Wang
 * @version 1.0
 * @since 11/23/2015
 */
public class RoomBean implements Serializable {

    private static final String CONFERENCE_ROOM = "conference";
    private static final String STUDY_ROOM = "study";

    private int roomId;
    private String name;
    private int capacity;
    private String type;
    private boolean lock_status;
    private String imgSrc;
    private int buildingId;

    /**
     * @return the roomId
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * @param roomId the roomId to set
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the lock_status
     */
    public boolean isLock_status() {
        return lock_status;
    }

    /**
     * @param lock_status the lock_status to set
     */
    public void setLock_status(boolean lock_status) {
        this.lock_status = lock_status;
    }

    /**
     * @return the imgSrc
     */
    public String getImgSrc() {
        return imgSrc;
    }

    /**
     * @param imgSrc the imgSrc to set
     */
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    /**
     * @return the buildingId
     */
    public int getBuildingId() {
        return buildingId;
    }

    /**
     * @param buildingId the buildingId to set
     */
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

}
