package com.cmu.smartphone.allavailable.local.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmu.smartphone.allavailable.R;

public class BuildingActivity extends AppCompatActivity {

    private ListView buildingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        buildingListView = (ListView) findViewById(R.id.buildingListView);

        String[] buildings = {"Carnegie Mellon University, Building 23", "Carnegie Mellon University, Building 19"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, buildings);

        buildingListView.setAdapter(adapter);

        buildingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BuildingActivity.this, RoomListActivity.class);
                startActivity(intent);
            }
        });
    }
}
