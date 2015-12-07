package com.cmu.smartphone.allavailable.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.entities.ReservationView;
import com.cmu.smartphone.allavailable.entities.RoomBean;
import com.cmu.smartphone.allavailable.model.ScheduleListItem;
import com.cmu.smartphone.allavailable.util.DateTimeHelper;

public class HistoryReserveDetailActivity extends AppCompatActivity {

    private Button commentButton;
    private LinearLayout mapLayout;
    private TextView locationSelected;
    private TextView roomSelected;
    private TextView seatSelected;
    private TextView timeSelected;

    private ReservationView reservation;
    private ScheduleListItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_reserve_detail);

        Intent intent = getIntent();
        reservation = (ReservationView) intent.getSerializableExtra("reservation");
        item = (ScheduleListItem) intent.getSerializableExtra("schedule");

        mapLayout = (LinearLayout) findViewById(R.id.seat_map_linearlayout);
        if (reservation.getRoom().getType().equals(RoomBean.CONFERENCE_ROOM)) {
            mapLayout.setBackgroundResource(R.drawable.conference);
        } else {
            mapLayout.setBackgroundResource(R.drawable.seat);
        }

        locationSelected = (TextView) findViewById(R.id.location_selected);
        roomSelected = (TextView) findViewById(R.id.room_selected);
        seatSelected = (TextView) findViewById(R.id.seat_selected);
        timeSelected = (TextView) findViewById(R.id.time_selected);

        String building = reservation.getBuilding().getBuildingName();
        int comma = building.indexOf(",");

        StringBuilder sb = new StringBuilder();
        sb.append(item.getTime().substring(0, 3));
        sb.append(", ");
        sb.append(reservation.getReservation().getDate());
        sb.append(", ");
        sb.append(reservation.getReservation().getTime());
        sb.append(" - ");
        sb.append(DateTimeHelper.addTime(reservation.getReservation().getTime(),
                reservation.getReservation().getDuration()));

        locationSelected.setText(building.substring(0, comma));
        String room = building.substring(comma + 2) + " "
                + reservation.getRoom().getName();
        roomSelected.setText(room);
        seatSelected.setText(reservation.getSeat().getName());
        timeSelected.setText(sb.toString());

        commentButton = (Button) findViewById(R.id.detail_commen_button);

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryReserveDetailActivity.this, CommentActivity.class);
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
}
