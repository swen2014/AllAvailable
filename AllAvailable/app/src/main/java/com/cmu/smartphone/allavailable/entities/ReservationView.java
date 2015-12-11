package com.cmu.smartphone.allavailable.entities;

import java.io.Serializable;

/**
 * The reservation view mapped from database
 *
 * @author Xi Wang
 * @version 1.0
 */
public class ReservationView implements Serializable {
    private BuildingBean building;
    private RoomBean room;
    private SeatBean seat;
    private ReservationBean reservation;

    /**
     * Get the building of the reservation
     *
     * @return the building object of the reservation
     */
    public BuildingBean getBuilding() {
        return building;
    }

    /**
     * Set the building of the reservation
     *
     * @param building the given building object of the reservation
     */
    public void setBuilding(BuildingBean building) {
        this.building = building;
    }

    /**
     * Get the room of the reservation
     *
     * @return the room object of the reservation
     */
    public RoomBean getRoom() {
        return room;
    }

    /**
     * Set the room of the reservation
     *
     * @param room the given room object
     */
    public void setRoom(RoomBean room) {
        this.room = room;
    }

    /**
     * Get the seat of the reservation
     *
     * @return the seat object
     */
    public SeatBean getSeat() {
        return seat;
    }

    /**
     * Set the seat of the reservation
     *
     * @param seat the given seat object
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
     * @param reservation the given reservation object
     */
    public void setReservation(ReservationBean reservation) {
        this.reservation = reservation;
    }
}
