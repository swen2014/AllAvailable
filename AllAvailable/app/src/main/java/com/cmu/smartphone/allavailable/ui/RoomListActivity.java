package com.cmu.smartphone.allavailable.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmu.smartphone.allavailable.R;


public class RoomListActivity extends AppCompatActivity {

    private ListView roomListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        roomListView = (ListView) findViewById(R.id.roomListView);
        String[] rooms = {"Room 1022", "Room 1024", "Room 1026", "Room 1028"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, rooms);

        roomListView.setAdapter(adapter);
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoomListActivity.this, SeatInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
