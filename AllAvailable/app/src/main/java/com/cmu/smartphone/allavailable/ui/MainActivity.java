package com.cmu.smartphone.allavailable.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.adapter.ScheduleItemAdapter;
import com.cmu.smartphone.allavailable.entities.ReservationView;
import com.cmu.smartphone.allavailable.model.ScheduleListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * The main page
 *
 * @author Xi Wang
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private ListView schedule_list;
    private TextView hintTxt;
    private TextView locationTxt;
    private TextView roomTxt;
    private TextView dateTxt;


    private ArrayList<ReservationView> reservations;
    private List<ScheduleListItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        reservations = (ArrayList<ReservationView>) intent.getSerializableExtra("reservations");
        list = (ArrayList<ScheduleListItem>) intent.getSerializableExtra("schedules");

        schedule_list = (ListView) findViewById(R.id.schedule_list);
        hintTxt = (TextView) findViewById(R.id.hintTxt);
        locationTxt = (TextView) findViewById(R.id.locationTxt);
        roomTxt = (TextView) findViewById(R.id.roomTxt);
        dateTxt = (TextView) findViewById(R.id.dateTxt);

        if (list.isEmpty()) {
            hintTxt.setText("");
            locationTxt.setText(R.string.welcome);
            roomTxt.setText(R.string.first_hint);
            dateTxt.setText("");
        } else {
            ReservationView recentReservation = reservations.get(0);
            ScheduleListItem item = list.get(0);
            String building = recentReservation.getBuilding().getBuildingName();
            int comma = building.indexOf(",");
            locationTxt.setText(building.substring(0, comma));
            String room = building.substring(comma + 2) + " "
                    + recentReservation.getRoom().getName();
            roomTxt.setText(room);

            StringBuilder sb = new StringBuilder();
            sb.append(item.getTime().substring(0, 3));
            sb.append(", ");
            sb.append(recentReservation.getReservation().getDate());
            sb.append(", ");
            sb.append(recentReservation.getReservation().getTime());
            dateTxt.setText(sb.toString());
        }

        schedule_list.setAdapter(new ScheduleItemAdapter(this, list));
        schedule_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ReserveDetailActivity.class);
                intent.putExtra("reservation", reservations.get((int) id));
                intent.putExtra("schedule", list.get((int) id));
                startActivity(intent);
            }
        });
    }

    /**
     * Override the onCreateOptionsMenu
     *
     * @param menu the given menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Override the onOptionsItemSelected
     *
     * @param item the menu item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            Intent intent = new Intent(MainActivity.this, BuildingActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_call) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "Your Phone_number"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Calling Failed!", Toast.LENGTH_SHORT).show();
            }
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
