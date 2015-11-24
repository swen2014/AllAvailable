package com.cmu.smartphone.allavailable.entities;

import java.io.Serializable;

/**
 * Created by wangxi on 11/23/15.
 */
public class ReservationView implements Serializable {
    private BuildingBean building;
    private RoomBean room;
    private SeatBean seat;
    private ReservationBean reservation;

    public BuildingBean getBuilding() {
        return building;
    }

    public void setBuilding(BuildingBean building) {
        this.building = building;
    }

    public RoomBean getRoom() {
        return room;
    }

    public void setRoom(RoomBean room) {
        this.room = room;
    }

    public SeatBean getSeat() {
        return seat;
    }

    public void setSeat(SeatBean seat) {
        this.seat = seat;
    }

    public ReservationBean getReservation() {
        return reservation;
    }

    public void setReservation(ReservationBean reservation) {
        this.reservation = reservation;
    }
}
