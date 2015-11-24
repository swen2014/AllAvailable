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

    private String roomId;
    private int capacity;
    private String type;
    private boolean lock;
    private String imgSrc;
    private String buildingId;

    public static String getConferenceRoom() {
        return CONFERENCE_ROOM;
    }

    public static String getStudyRoom() {
        return STUDY_ROOM;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
