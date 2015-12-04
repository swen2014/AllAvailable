package com.cmu.smartphone.allavailable.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.adapter.HistoryItemAdapter;
import com.cmu.smartphone.allavailable.entities.BuildingBean;
import com.cmu.smartphone.allavailable.entities.ReservationBean;
import com.cmu.smartphone.allavailable.entities.ReservationView;
import com.cmu.smartphone.allavailable.entities.RoomBean;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView historyList;
    private List<ReservationView> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyList = (ListView) findViewById(R.id.historyListView);
        reservations = generateFakeData();
        HistoryItemAdapter adapter = new HistoryItemAdapter(this, reservations);

        historyList.setAdapter(adapter);
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("Click", "click");
                Intent intent = new Intent(HistoryActivity.this, ReserveDetailActivity.class);
                intent.putExtra("reservation", reservations.get(position));
                startActivity(intent);
            }
        });

    }

    /**
     * Override the onOptionsItemSelected method
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<ReservationView> generateFakeData() {
        // Remove this fake data later
        BuildingBean building = new BuildingBean();
        building.setBuildingName("Carnegie Mellon Univ, B23");

        RoomBean room1 = new RoomBean();
        room1.setName("Student Lounge");
        RoomBean room2 = new RoomBean();
        room2.setName("129A");

        ReservationBean reservation1 = new ReservationBean();
        reservation1.setDate("11/23/2015");
        reservation1.setTime("15:30");
        ReservationBean reservation2 = new ReservationBean();
        reservation2.setDate("11/25/2015");
        reservation2.setTime("09:00");

        ReservationView reservationView1 = new ReservationView();
        reservationView1.setBuilding(building);
        reservationView1.setRoom(room1);
        reservationView1.setReservation(reservation1);

        ReservationView reservationView2 = new ReservationView();
        reservationView2.setBuilding(building);
        reservationView2.setRoom(room2);
        reservationView2.setReservation(reservation2);

        List<ReservationView> list = new ArrayList<ReservationView>();
        list.add(reservationView1);
        list.add(reservationView2);

        return list;
    }
}
