package com.cmu.smartphone.allavailable.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.entities.BuildingBean;
import com.cmu.smartphone.allavailable.entities.RoomBean;
import com.cmu.smartphone.allavailable.exception.NetworkException;
import com.cmu.smartphone.allavailable.util.JsonHelper;
import com.cmu.smartphone.allavailable.ws.remote.DataArrivedHandler;
import com.cmu.smartphone.allavailable.ws.remote.DataReceiver;
import com.cmu.smartphone.allavailable.ws.remote.ServerConnectionTask;
import com.cmu.smartphone.allavailable.ws.remote.SessionControl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Room List page
 *
 * @author Xi Wang
 * @version 1.0
 */
public class RoomListActivity extends AppCompatActivity {

    private ListView roomListView;
    private TextView buildingLabel;

    private DataArrivedHandler handler;
    private List<RoomBean> roomResults;
    private BuildingBean building;

    /**
     * The override onCreate method
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        Intent intent = getIntent();
        building = (BuildingBean) intent.getSerializableExtra("building");

        SessionControl session = SessionControl.getInstance();

//        String uriAPI = getResources().getText(R.string.host)
        String uriAPI = session.getHostIp(this)
                + "SeatOperation?action=rooms&bId=" + building.getBuildingId();
        Log.v("DEBUG", uriAPI);
        new connectRooms().execute(uriAPI);

        roomListView = (ListView) findViewById(R.id.roomListView);
        buildingLabel = (TextView) findViewById(R.id.listName);
        buildingLabel.setText(building.getBuildingName());
        View loadingView = LayoutInflater.from(this).inflate(R.layout.listfooter,
                null);

        roomListView.addFooterView(loadingView);
        handler = new DataArrivedHandler(roomListView, loadingView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, new String[]{});


        roomListView.setAdapter(adapter);
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoomListActivity.this, SeatInfoActivity.class);
                intent.putExtra("room", roomResults.get((int) id));
                intent.putExtra("building", building);
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

    /**
     * The task to request the room list
     */
    public class connectRooms extends AsyncTask<String, Integer, List<RoomBean>>
            implements ServerConnectionTask {
        /**
         * Override the onPostExecute method
         *
         * @param result
         */
        @Override
        protected void onPostExecute(List<RoomBean> result) {
            super.onPostExecute(result);
            try {
                if (result == null) {
                    throw new NetworkException();
                }
                roomResults = result;
                List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                for (int i = 0; i < roomResults.size(); i++) {
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put("title", roomResults.get(i).getName());
                    list.add(item);
                }

                SimpleAdapter sa = new SimpleAdapter(RoomListActivity.this, list, android.R.layout.simple_list_item_1,
                        new String[]{"title"}, new int[]{android.R.id.text1});

                roomListView.setAdapter(sa);
                handler.serverDataArrived(result, true);
            } catch (NetworkException ne) {
                ne.fix(RoomListActivity.this);
            }
        }

        /**
         * Override the doInBackground method
         *
         * @param arg0
         * @return
         */
        @Override
        protected List<RoomBean> doInBackground(String... arg0) {
            ArrayList<RoomBean> tmpRooms = null;

            try {
                Log.v("DEBUG", arg0[0]);
                URL url = new URL(arg0[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                int code = connection.getResponseCode();
                if (code == 200) {
                    String jsonString = DataReceiver.ChangeInputStream(connection
                            .getInputStream());
                    tmpRooms = (ArrayList<RoomBean>) JsonHelper.getRooms(jsonString);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tmpRooms;
        }
    }
}
