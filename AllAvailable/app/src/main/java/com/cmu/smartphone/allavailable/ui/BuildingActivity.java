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

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.entities.BuildingBean;
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

public class BuildingActivity extends AppCompatActivity {

    private ListView buildingListView;

    private DataArrivedHandler handler;
    private List<BuildingBean> buildingResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        String uriAPI = getResources().getText(R.string.host)
                + "SeatOperation?action=buildings";
        Log.v("DEBUG", uriAPI);
        new connectBuildings().execute(uriAPI);

        buildingListView = (ListView) findViewById(R.id.buildingListView);
        View loadingView = LayoutInflater.from(this).inflate(R.layout.listfooter,
                null);

        buildingListView.addFooterView(loadingView);
        handler = new DataArrivedHandler(buildingListView, loadingView);

        String[] buildings = {};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, buildings);

        buildingListView.setAdapter(adapter);

        buildingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BuildingActivity.this, RoomListActivity.class);
                intent.putExtra("building", buildingResults.get((int) id));
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

    public class connectBuildings extends AsyncTask<String, Integer, List<BuildingBean>> {
        @Override
        protected void onPostExecute(List<BuildingBean> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                if (result == null) {
                    throw new NetworkException();
                }
                buildingResults = result;
                List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
                for (int i = 0; i < buildingResults.size(); i++) {
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put("title", buildingResults.get(i).getBuildingName());
                    list.add(item);
                }

                SimpleAdapter sa = new SimpleAdapter(BuildingActivity.this, list, android.R.layout.simple_list_item_1,
                        new String[]{"title"}, new int[]{android.R.id.text1});

                buildingListView.setAdapter(sa);
                handler.serverDataArrived(result, true);
            } catch (NetworkException ne) {
                ne.fix(BuildingActivity.this);
            }
        }

        @Override
        protected List<BuildingBean> doInBackground(String... arg0) {
            ArrayList<BuildingBean> tmpBuildings = null;

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
                    tmpBuildings = (ArrayList<BuildingBean>) JsonHelper.getBuildings(jsonString);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tmpBuildings;
        }
    }
}
