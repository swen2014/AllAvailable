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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.entities.BuildingBean;
import com.cmu.smartphone.allavailable.entities.RoomBean;
import com.cmu.smartphone.allavailable.entities.SeatBean;
import com.cmu.smartphone.allavailable.exception.NetworkException;
import com.cmu.smartphone.allavailable.util.JsonHelper;
import com.cmu.smartphone.allavailable.ws.remote.DataArrivedHandler;
import com.cmu.smartphone.allavailable.ws.remote.DataReceiver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeatInfoActivity extends AppCompatActivity {

    private ListView seatListView;
    private Button commentButton;
    private ImageView background;

    private DataArrivedHandler handler;
    private List<SeatBean> seatResults;
    private BuildingBean currentBuilding;
    private RoomBean currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_info);

        Intent intent = getIntent();
        currentRoom = (RoomBean) intent.getSerializableExtra("room");
        currentBuilding = (BuildingBean) intent.getSerializableExtra("building");

        String uriAPI = getResources().getText(R.string.host)
                + "SeatOperation?action=seats&rId=" + currentRoom.getRoomId();
        Log.v("DEBUG", uriAPI);
        new connectSeats().execute(uriAPI);

        seatListView = (ListView) findViewById(R.id.seatListView);
        commentButton = (Button) findViewById(R.id.comment_button);
        background = (ImageView) findViewById(R.id.seatImage);

        View loadingView = LayoutInflater.from(this).inflate(R.layout.listfooter,
                null);

        seatListView.addFooterView(loadingView);
        handler = new DataArrivedHandler(seatListView, loadingView);

//        String[] seats = {"Seat 1", "Seat 2", "Seat 3", "Seat 4", "Seat 5", "Seat 6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, new String[]{});
        seatListView.setAdapter(adapter);
        seatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SeatInfoActivity.this, ReserveActivity.class);
                intent.putExtra("seat", seatResults.get((int) id));
                intent.putExtra("room", currentRoom);
                intent.putExtra("building", currentBuilding);
                startActivity(intent);
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeatInfoActivity.this, CommentActivity.class);
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

    public class connectSeats extends AsyncTask<String, Integer, List<SeatBean>> {
        @Override
        protected void onPostExecute(List<SeatBean> result) {
            super.onPostExecute(result);
            try {
                if (result == null) {
                    throw new NetworkException();
                }
                seatResults = result;
                List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                for (int i = 0; i < seatResults.size(); i++) {
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put("title", seatResults.get(i).getName());
                    list.add(item);
                }

                SimpleAdapter sa = new SimpleAdapter(SeatInfoActivity.this, list, android.R.layout.simple_list_item_1,
                        new String[]{"title"}, new int[]{android.R.id.text1});

                seatListView.setAdapter(sa);
                handler.serverDataArrived(result, true);
                if (currentRoom.getType().equals(RoomBean.CONFERENCE_ROOM)) {
                    background.setImageResource(R.drawable.conference);
                } else {
                    background.setImageResource(R.drawable.seat);
                }
            } catch (NetworkException ne) {
                ne.fix(SeatInfoActivity.this);
            }
        }

        @Override
        protected List<SeatBean> doInBackground(String... arg0) {
            ArrayList<SeatBean> tmpSeats = null;

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
                    tmpSeats = (ArrayList<SeatBean>) JsonHelper.getSeats(jsonString);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tmpSeats;
        }
    }
}
