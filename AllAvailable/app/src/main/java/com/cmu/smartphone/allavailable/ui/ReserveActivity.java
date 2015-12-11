package com.cmu.smartphone.allavailable.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.entities.BuildingBean;
import com.cmu.smartphone.allavailable.entities.ReservationView;
import com.cmu.smartphone.allavailable.entities.RoomBean;
import com.cmu.smartphone.allavailable.entities.SeatBean;
import com.cmu.smartphone.allavailable.exception.NetworkException;
import com.cmu.smartphone.allavailable.exception.ReservationValidateException;
import com.cmu.smartphone.allavailable.fragment.DatePickerFragment;
import com.cmu.smartphone.allavailable.fragment.TimePickerFragment;
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
 * The reserve page
 *
 * @author Xi Wang
 * @version 1.0
 */
public class ReserveActivity extends AppCompatActivity {

    private Button dateButton;
    private Button timeButton;
    private Spinner periodPicker;
    private Button confirm;
    private Button cancel;

    private ProgressDialog progress;

    private TextView buildingLoc;
    private TextView roomLoc;
    private TextView seatLoc;

    private BuildingBean building;
    private RoomBean room;
    private SeatBean seat;

    private CharSequence dateChar;
    private CharSequence timeChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        Intent intent = getIntent();
        building = (BuildingBean) intent.getSerializableExtra("building");
        room = (RoomBean) intent.getSerializableExtra("room");
        seat = (SeatBean) intent.getSerializableExtra("seat");

        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);

        buildingLoc = (TextView) findViewById(R.id.buildingLoc);
        roomLoc = (TextView) findViewById(R.id.roomLoc);
        seatLoc = (TextView) findViewById(R.id.seatLoc);

        buildingLoc.setText(building.getBuildingName());
        roomLoc.setText(room.getName());
        seatLoc.setText(seat.getName());

        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();

        dateChar = DateFormat.format("  MMM dd, yyyy ", time);
        dateButton.setText(dateChar);
        timeChar = DateFormat.format("  HH:mm", time);
        timeButton.setText(timeChar);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        periodPicker = (Spinner) findViewById(R.id.period_spinner);
        final ArrayAdapter<CharSequence> periodAdapter = ArrayAdapter.createFromResource(this,
                R.array.period, android.R.layout.simple_spinner_item);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodPicker.setAdapter(periodAdapter);
        periodPicker.setSelection(2);

        confirm = (Button) findViewById(R.id.confirm_button);
        cancel = (Button) findViewById(R.id.cancel_button);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String date = dateButton.getText().toString().trim();
                    String time = timeButton.getText().toString().trim();
                    double duration = Double.parseDouble(periodPicker.getSelectedItem().
                            toString().replace(" hour", ""));

                    if (!validateRequest(date, time, duration)) {
                        throw new ReservationValidateException("The reservation period should be " +
                                "in the following hours at the same day!");
                    }
                    progress = ProgressDialog.show(ReserveActivity.this, "Please Wait ...",
                            "Creating Your Reservation....", true);
                    SessionControl session = SessionControl.getInstance();
                    String user = session.getUserSession(ReserveActivity.this);

//                    String uriHost = getResources().getText(R.string.host).toString();
                    String uriHost = session.getHostIp(ReserveActivity.this);

                    new CreateReservationAsyncTask().execute(uriHost, user, seat.getSeatId() + "",
                            date, time, duration + "");
                } catch (ReservationValidateException e) {
                    e.fix(ReserveActivity.this);
                }
            }
        });
    }

    /**
     * Show the dialog to choose the time
     *
     * @param v
     */
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        ((TimePickerFragment) newFragment).setParentTimeButton(timeButton);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * Show the dialog to choose the date
     *
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        ((DatePickerFragment) newFragment).setParentDateButton(dateButton);
        newFragment.show(getSupportFragmentManager(), "datePicker");
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

    public void setTimeText(String selectedTime) {
        timeButton.setText(selectedTime);
    }

    /**
     * Validate the request parameters
     *
     * @param date
     * @param time
     * @param duration
     * @return whether the request parameters are valid
     */
    private boolean validateRequest(String date, String time, double duration) {

        String endTime = DateTimeHelper.addTime(time, duration);

        if (date.equals(dateChar.toString().trim()) && time.compareTo(timeChar.toString().trim()) < 0) {
            return false;
        }

        if (time.compareTo("18:00") > 0 && endTime.startsWith("0") && !endTime.equals("00:00")) {
            return false;
        }


        return true;
    }

    /**
     * The task to create the reservation
     */
    public class CreateReservationAsyncTask extends
            AsyncTask<String, Integer, String> implements ServerConnectionTask {

        private static final String OCCUPIED_STATUS = "Occupied\n";
        private static final String OK_STATUS = "OK\n";
        private static final String USER_NOT_AVAILABLE = "NotAvailable\n";
        private ArrayList<ReservationView> dataResults;

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override

        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Override the onPostExecute method
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            progress.dismiss();
            try {
                if (result == null) {
                    throw new NetworkException();
                }
                if (result.equals(USER_NOT_AVAILABLE)) {
                    throw new ReservationValidateException("You have another reservation at this period!");
                } else if (result.equals(OCCUPIED_STATUS)) {
                    throw new ReservationValidateException("The space has been occupied during this period!");
                } else if (result.equals(OK_STATUS)) {
                    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
                    ArrayList<ScheduleListItem> list = new ArrayList<>();
                    for (ReservationView reservation : dataResults) {
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
                    }
                    Intent intent = new Intent(ReserveActivity.this, MainActivity.class);
                    intent.putExtra("reservations", dataResults);
                    intent.putExtra("schedules", list);
                    startActivity(intent);
                }
            } catch (ReservationValidateException re) {
                re.fix(ReserveActivity.this);
            } catch (NetworkException ne) {
                ne.fix(ReserveActivity.this);
            }
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected String doInBackground(String... arg0) {

            String result = null;
            System.out.println("In AsncTask!!");
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
                params.append("action=new&user=")
                        .append(arg0[1]).append("&seat=")
                        .append(arg0[2]).append("&date=")
                        .append(arg0[3]).append("&time=")
                        .append(arg0[4]).append("&duration=")
                        .append(arg0[5]);
                Log.v("DEBUG", params.toString());
                byte[] bypes = params.toString().getBytes();
                connection.getOutputStream().write(bypes);
                int code = connection.getResponseCode();
                if (code == 200) {
                    result = DataReceiver.ChangeInputStream(connection
                            .getInputStream());
                }

                if (result.equals(OK_STATUS)) {
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
                        dataResults = (ArrayList<ReservationView>) JsonHelper.getReservations(jsonString);
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
