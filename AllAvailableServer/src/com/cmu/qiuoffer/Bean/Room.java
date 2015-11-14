package com.cmu.qiuoffer.Bean;

public class Room {

	private boolean roomId;
	private int capacity;
	private boolean type;
	private boolean lock;
	private String imgSrc;

	public boolean isRoomId() {
		return roomId;
	}

	public void setRoomId(boolean roomId) {
		this.roomId = roomId;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

}
