package com.cmu.smartphone.allavailable.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.cmu.smartphone.allavailable.exception.NetworkException;
import com.cmu.smartphone.allavailable.model.ScheduleListItem;
import com.cmu.smartphone.allavailable.util.DateTimeHelper;
import com.cmu.smartphone.allavailable.util.JsonHelper;
import com.cmu.smartphone.allavailable.ws.remote.DataArrivedHandler;
import com.cmu.smartphone.allavailable.ws.remote.DataReceiver;
import com.cmu.smartphone.allavailable.ws.remote.SessionControl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView historyList;
    private List<ReservationView> reservations;

    private DataArrivedHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SessionControl session = SessionControl.getInstance();
        String user = session.getUserSession(this);

        String hostAPI = getResources().getText(R.string.host).toString();

        new connectHistories().execute(hostAPI, user);

        historyList = (ListView) findViewById(R.id.historyListView);

        View loadingView = LayoutInflater.from(this).inflate(R.layout.listfooter,
                null);

        historyList.addFooterView(loadingView);
        handler = new DataArrivedHandler(historyList, loadingView);

        HistoryItemAdapter adapter = new HistoryItemAdapter(this, new ArrayList<ReservationView>());

        historyList.setAdapter(adapter);
        historyList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.v("Click", "click");

                        ReservationView reservation = reservations.get((int) id);
                        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
                        String dateString = reservation.getReservation().getDate();
                        String timeString = reservation.getReservation().getTime();
                        double duration = reservation.getReservation().getDuration();
                        try {
                            Date date = formatter.parse(dateString);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);

                            ScheduleListItem item = new ScheduleListItem();
                            StringBuilder sb = new StringBuilder();
                            sb.append(DateTimeHelper.getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK) - 1));
                            sb.append(", ");
                            sb.append(timeString);
                            sb.append(" - ");
                            sb.append(DateTimeHelper.addTime(timeString, duration));
                            item.setTime(sb.toString());

                            String building = reservation.getBuilding().getBuildingName().
                                    substring(reservation.getBuilding().getBuildingName().indexOf(",") + 2);
                            item.setPlace(building + ", " + reservation.getRoom().getName());

                            Intent intent = new Intent(HistoryActivity.this, HistoryReserveDetailActivity.class);
                            intent.putExtra("reservation", reservation);
                            intent.putExtra("schedule", item);
                            startActivity(intent);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
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

    public class connectHistories extends AsyncTask<String, Integer, List<ReservationView>> {
        @Override
        protected void onPostExecute(List<ReservationView> result) {
            super.onPostExecute(result);
            try {
                if (result == null) {
                    throw new NetworkException();
                }
                reservations = result;
                HistoryItemAdapter adapter = new HistoryItemAdapter(HistoryActivity.this, reservations);

                historyList.setAdapter(adapter);
                handler.serverDataArrived(result, true);
            } catch (NetworkException ne) {
                ne.fix(HistoryActivity.this);
            }
        }

        @Override
        protected List<ReservationView> doInBackground(String... arg0) {
            ArrayList<ReservationView> tmpReservations = null;

            try {
                Calendar cal = Calendar.getInstance();
                Date time = cal.getTime();

                CharSequence dateChar = DateFormat.format("MMM dd, yyyy", time);
                CharSequence timeChar = DateFormat.format("HH:mm", time);
                StringBuilder sb = new StringBuilder();
                sb.append(arg0[0]);
                sb.append("Reservation?action=reservations&user=");
                sb.append(arg0[1]);
                sb.append("&date=");
                sb.append(dateChar.toString());
                sb.append("&time=");
                sb.append(timeChar.toString());
                sb.append("&history=true");
                Log.v("DEBUG", arg0[0]);
                URL url = new URL(sb.toString().replace(" ", "%20"));
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                int code = connection.getResponseCode();
                if (code == 200) {
                    String jsonString = DataReceiver.ChangeInputStream(connection
                            .getInputStream());
                    tmpReservations = (ArrayList<ReservationView>) JsonHelper.getReservations(jsonString);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tmpReservations;
        }
    }
}
