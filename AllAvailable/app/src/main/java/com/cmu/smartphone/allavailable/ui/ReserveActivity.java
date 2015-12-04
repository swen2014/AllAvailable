package com.cmu.smartphone.allavailable.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmu.smartphone.allavailable.R;
import com.cmu.smartphone.allavailable.entities.BuildingBean;
import com.cmu.smartphone.allavailable.entities.RoomBean;
import com.cmu.smartphone.allavailable.entities.SeatBean;
import com.cmu.smartphone.allavailable.fragment.DatePickerFragment;
import com.cmu.smartphone.allavailable.fragment.TimePickerFragment;

import java.util.Calendar;
import java.util.Date;

public class ReserveActivity extends AppCompatActivity {

    private Button dateButton;
    private Button timeButton;
    private Spinner periodPicker;
    private Button confirm;
    private Button cancel;

    private TextView buildingLoc;
    private TextView roomLoc;
    private TextView seatLoc;

    private BuildingBean building;
    private RoomBean room;
    private SeatBean seat;

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

        CharSequence dateChar = DateFormat.format("  MMM  dd, yyyy ", time);
        dateButton.setText(dateChar);
        CharSequence timeChar = DateFormat.format("  hh:mm", time);
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
                Intent intent = new Intent(ReserveActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        ((TimePickerFragment) newFragment).setParentTimeButton(timeButton);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

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
}
