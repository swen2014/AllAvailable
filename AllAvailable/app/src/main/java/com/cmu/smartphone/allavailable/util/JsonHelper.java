package com.cmu.smartphone.allavailable.util;

import android.util.Log;

import com.cmu.smartphone.allavailable.entities.BuildingBean;
import com.cmu.smartphone.allavailable.entities.CommentBean;
import com.cmu.smartphone.allavailable.entities.ReservationBean;
import com.cmu.smartphone.allavailable.entities.ReservationView;
import com.cmu.smartphone.allavailable.entities.RoomBean;
import com.cmu.smartphone.allavailable.entities.SeatBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The tool to parse the json
 *
 * @author Xi Wang
 * @version 1.0
 */
public class JsonHelper {

    /**
     * Parse the building object
     *
     * @param jsonString
     * @return the building
     */
    public static List<BuildingBean> getBuildings(
            String jsonString) {

        ArrayList<BuildingBean> buildings = new ArrayList<>();
        try {
            Log.v("DEBUG", jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject buildingJsonObject = jsonArray.getJSONObject(i);
                BuildingBean bb = new BuildingBean();
                bb.setBuildingId(buildingJsonObject.getInt("buildingId"));
                bb.setBuildingName(buildingJsonObject.getString("buildingName"));

                buildings.add(bb);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return buildings;
    }

    /**
     * Parse the room object
     *
     * @param jsonString
     * @return the room
     */
    public static List<RoomBean> getRooms(String jsonString) {
        ArrayList<RoomBean> rooms = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject roomJsonObject = jsonArray.getJSONObject(i);
                RoomBean room = new RoomBean();
                room.setRoomId(roomJsonObject.getInt("roomId"));
                room.setBuildingId(roomJsonObject.getInt("buildingId"));
                room.setCapacity(roomJsonObject.getInt("capacity"));
                room.setType(roomJsonObject.getString("type"));
                room.setName(roomJsonObject.getString("name"));
                room.setImgSrc(roomJsonObject.getString("imgSrc"));
                room.setLock_status(roomJsonObject.getBoolean("lock_status"));
                rooms.add(room);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    /**
     * Parse the seat object
     *
     * @param jsonString
     * @return the seat
     */
    public static List<SeatBean> getSeats(String jsonString) {
        ArrayList<SeatBean> seats = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject seatJsonObject = jsonArray.getJSONObject(i);
                SeatBean seat = new SeatBean();
                seat.setName(seatJsonObject.getString("name"));
                seat.setRoomId(seatJsonObject.getInt("roomId"));
                seat.setSeatId(seatJsonObject.getInt("seatId"));
                seat.setOccupied(seatJsonObject.getBoolean("occupied"));
                seats.add(seat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return seats;
    }

    /**
     * Parse the reservation object
     *
     * @param jsonString
     * @return the reservation
     */
    public static List<ReservationView> getReservations(String jsonString) {
        ArrayList<ReservationView> reservations = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject reserveJsonObject = jsonArray.getJSONObject(i);

                JSONObject buildingJSON = reserveJsonObject.getJSONObject("building");
                BuildingBean building = new BuildingBean();
                building.setBuildingName(buildingJSON.getString("buildingName"));

                JSONObject roomJSON = reserveJsonObject.getJSONObject("room");
                RoomBean room = new RoomBean();
                room.setRoomId(roomJSON.getInt("roomId"));
                room.setName(roomJSON.getString("name"));
                room.setCapacity(roomJSON.getInt("capacity"));
                room.setBuildingId(roomJSON.getInt("buildingId"));
                room.setType(roomJSON.getString("type"));
                room.setImgSrc(roomJSON.getString("imgSrc"));
                room.setLock_status(roomJSON.getBoolean("lock_status"));

                JSONObject seatJSON = reserveJsonObject.getJSONObject("seat");
                SeatBean seat = new SeatBean();
                seat.setName(seatJSON.getString("name"));
                seat.setRoomId(seatJSON.getInt("roomId"));
                seat.setSeatId(seatJSON.getInt("seatId"));
                seat.setOccupied(seatJSON.getBoolean("occupied"));

                JSONObject reserveJSON = reserveJsonObject.getJSONObject("reservation");
                ReservationBean reservation = new ReservationBean();
                reservation.setReseavationId(reserveJSON.getInt("reseavationId"));
                reservation.setDate(reserveJSON.getString("date"));
                reservation.setTime(reserveJSON.getString("time"));
                reservation.setUserId(reserveJSON.getString("userId"));
                reservation.setDuration(reserveJSON.getDouble("duration"));
                reservation.setSeatId(reserveJSON.getInt("seatId"));

                ReservationView reservationView = new ReservationView();
                reservationView.setBuilding(building);
                reservationView.setRoom(room);
                reservationView.setSeat(seat);
                reservationView.setReservation(reservation);

                reservations.add(reservationView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    /**
     * Parse the comment object
     *
     * @param jsonString
     * @return the comment
     */
    public static List<CommentBean> getComments(String jsonString) {
        List<CommentBean> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject commentJsonObject = jsonArray.getJSONObject(i);
                CommentBean commentBean = new CommentBean();
                commentBean.setUserId(commentJsonObject.getString("userId"));
                commentBean.setContent(commentJsonObject.getString("content"));
                commentBean.setImagePath(commentJsonObject.getString("imagePath"));
                commentBean.setTime(commentJsonObject.getString("time"));
                commentBean.setDate(commentJsonObject.getString("date"));
                commentBean.setCommentId(commentJsonObject.getInt("commentId"));
                commentBean.setRoomId(commentJsonObject.getInt("roomId"));

                list.add(commentBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
