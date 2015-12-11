package com.cmu.smartphone.allavailable.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.entities.ReservationView;
import com.cmu.smartphone.allavailable.entities.RoomBean;
import com.cmu.smartphone.allavailable.exception.NetworkException;
import com.cmu.smartphone.allavailable.model.ScheduleListItem;
import com.cmu.smartphone.allavailable.util.DateTimeHelper;
import com.cmu.smartphone.allavailable.util.JsonHelper;
import com.cmu.smartphone.allavailable.ws.remote.DataReceiver;
import com.cmu.smartphone.allavailable.ws.remote.ServerConnectionTask;
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

/**
 * The reservation detail page
 *
 * @author Xi Wang
 * @version 1.0
 */
public class ReserveDetailActivity extends AppCompatActivity {

    private Button rescheduleButton;
    private Button cancelButton;
    private LinearLayout mapLayout;
    private TextView locationSelected;
    private TextView roomSelected;
    private TextView seatSelected;
    private TextView timeSelected;

    private ReservationView reservation;
    private ScheduleListItem item;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_detail);

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

        rescheduleButton = (Button) findViewById(R.id.detail_reschedule_button);
        cancelButton = (Button) findViewById(R.id.detail_cancel_button);

        SessionControl session = SessionControl.getInstance();
        final String user = session.getUserSession(this);

//        final String uriHost = getResources().getText(R.string.host).toString();
        final String uriHost = session.getHostIp(this);

        rescheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReserveDetailActivity.this);

                builder.setMessage(R.string.reschedule_dialog_message)
                        .setTitle(R.string.dialog_title);

                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        progress = ProgressDialog.show(ReserveDetailActivity.this, "Please Wait ...",
                                "Canceling Your Reservation....", true);
                        new CancelReservationAsyncTask(true).execute(uriHost,
                                reservation.getReservation().getReseavationId() + "", user);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReserveDetailActivity.this);

                builder.setMessage(R.string.cancel_dialog_message)
                        .setTitle(R.string.dialog_title);

                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        progress = ProgressDialog.show(ReserveDetailActivity.this, "Please Wait ...",
                                "Canceling Your Reservation....", true);
                        new CancelReservationAsyncTask(false).execute(uriHost,
                                reservation.getReservation().getReseavationId() + "", user);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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

    public class CancelReservationAsyncTask extends
            AsyncTask<String, Integer, ArrayList<ReservationView>> implements ServerConnectionTask {

        private boolean isReschedule;

        public CancelReservationAsyncTask(boolean isReschedule) {
            this.isReschedule = isReschedule;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPostExecute(ArrayList<ReservationView> result) {
            progress.dismiss();
            try {
                if (!isReschedule) {
                    if (result == null) {
                        throw new NetworkException();
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
                    ArrayList<ScheduleListItem> list = new ArrayList<>();
                    for (ReservationView reservation : result) {
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
                            list.add(item);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        Intent intent = new Intent(ReserveDetailActivity.this, MainActivity.class);
                        intent.putExtra("reservations", result);
                        intent.putExtra("schedules", list);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(ReserveDetailActivity.this, BuildingActivity.class);
                    startActivity(intent);
                }
            } catch (
                    NetworkException ne
                    )

            {
                ne.fix(ReserveDetailActivity.this);
            }
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected ArrayList<ReservationView> doInBackground(String... arg0) {
            ArrayList<ReservationView> result = null;

            try {
                URL url = new URL(arg0[0]
                        + "Reservation");
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                StringBuffer params = new StringBuffer();
                params.append("action=cancel&reId=")
                        .append(arg0[1]);
                Log.v("DEBUG", params.toString());
                byte[] bypes = params.toString().getBytes();
                connection.getOutputStream().write(bypes);
                int code = connection.getResponseCode();
                if (code == 200) {
                    Calendar cal = Calendar.getInstance();
                    Date time = cal.getTime();

                    CharSequence dateChar = DateFormat.format("MMM dd, yyyy", time);
                    CharSequence timeChar = DateFormat.format("HH:mm", time);
                    StringBuilder sb = new StringBuilder();
                    sb.append(arg0[0]);
                    sb.append("Reservation?action=reservations&user=");
                    sb.append(arg0[2]);
                    sb.append("&date=");
                    sb.append(dateChar.toString());
                    sb.append("&time=");
                    sb.append(timeChar.toString());
                    sb.append("&history=false");

                    url = new URL(sb.toString().replace(" ", "%20"));
                    Log.v("DEBUG", url.toString());
                    connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setConnectTimeout(3000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    code = connection.getResponseCode();
                    if (code == 200) {
                        String jsonString = DataReceiver.ChangeInputStream(connection
                                .getInputStream());
                        result = (ArrayList<ReservationView>) JsonHelper.getReservations(jsonString);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }


    }
}
