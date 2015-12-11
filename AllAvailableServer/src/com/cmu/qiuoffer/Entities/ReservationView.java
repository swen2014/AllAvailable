package com.cmu.qiuoffer.Entities;

import java.io.Serializable;

/**
 * Created by wangxi on 11/23/15.
 */
public class ReservationView implements Serializable {
	private BuildingBean building;
	private RoomBean room;
	private SeatBean seat;
	private ReservationBean reservation;

	/**
	 * Get the building of the reservation
	 * 
	 * @return the building object
	 */
	public BuildingBean getBuilding() {
		return building;
	}

	/**
	 * Set the building of the reservation
	 * 
	 * @param building
	 *            the given building object
	 */
	public void setBuilding(BuildingBean building) {
		this.building = building;
	}

	/**
	 * Get the room of the reservation
	 * 
	 * @return the room object
	 */
	public RoomBean getRoom() {
		return room;
	}

	/**
	 * Set the room of the reservation
	 * 
	 * @param room
	 *            the room
	 */
	public void setRoom(RoomBean room) {
		this.room = room;
	}

	/**
	 * Get the seat of the reservation
	 * 
	 * @return the seat of the reservation
	 */
	public SeatBean getSeat() {
		return seat;
	}

	/**
	 * Set the seat of the reservation
	 * 
	 * @param seat
	 *            the seat object
	 */
	public void setSeat(SeatBean seat) {
		this.seat = seat;
	}

	/**
	 * Get the reservation object
	 * 
	 * @return the reservation object
	 */
	public ReservationBean getReservation() {
		return reservation;
	}

	/**
	 * Set the reservation object
	 * 
	 * @param reservation
	 *            the reservation object
	 */
	public void setReservation(ReservationBean reservation) {
		this.reservation = reservation;
	}
}
