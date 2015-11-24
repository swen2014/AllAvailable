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
import android.widget.Toast;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.adapter.ScheduleItemAdapter;
import com.cmu.smartphone.allavailable.model.ScheduleListItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView schedule_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        schedule_list = (ListView) findViewById(R.id.schedule_list);
        List<ScheduleListItem> list = generateList();

        schedule_list.setAdapter(new ScheduleItemAdapter(this, list));
        schedule_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ReserveDetailActivity.class);
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

    private List<ScheduleListItem> generateList() {
        List<ScheduleListItem> list = new ArrayList<ScheduleListItem>();

        ScheduleListItem item = new ScheduleListItem();
        item.setPlace("B19 1022");
        item.setTime("Fri, 10:00 - 12:00");
        list.add(item);

        item = new ScheduleListItem("Fri, 16:00 - 17:00", "B19 1020");
        list.add(item);

        item = new ScheduleListItem("Sat, 9:30 - 14:30", "B23 Student Lounge");
        list.add(item);
        return list;
    }
}
