package com.eatwithme.eatwithme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;


public class CreateGroupActivity extends ActionBarActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";
    private String selectedTime = "";
    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        //for time
        final Calendar calendar = Calendar.getInstance();

        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),false);
        final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);

        findViewById(R.id.dateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        findViewById(R.id.timeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
            }
        });

        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }

            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
        }


        Spinner spinner = (Spinner)findViewById(R.id.foodGroupSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.GroupNumber, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createGroup(View view)
    {
        EditText groupNameEditText = (EditText)findViewById(R.id.group_name_edit_text);
        if (groupNameEditText.getText().toString().trim().length() == 0 )
        {
            Toast.makeText(CreateGroupActivity.this, "Please Fill in Group Name", Toast.LENGTH_SHORT).show();
        }
        else
        {

            Spinner mySpinner=(Spinner) findViewById(R.id.foodGroupSpinner);
            String groupMaxParty = mySpinner.getSelectedItem().toString();
            String groupNameString = groupNameEditText.getText().toString();
            ArrayList<String> groupMembers = new ArrayList<String>();
            Intent intent = this.getIntent();
            String theVenue = intent.getStringExtra("venue_id");
            String venueName = intent.getStringExtra("venue_name");
            String venueAddress = intent.getStringExtra("venue_address");
            Double  latitude = Double.valueOf(intent.getStringExtra("latitude"));
            Double  longitude = Double.valueOf(intent.getStringExtra("longitude"));
            String dateTime = selectedDate + selectedTime;

            Log.d("Crash HERE", "First PArt");

            ParseUser currentUser = ParseUser.getCurrentUser();
            String currentUserID = currentUser.getObjectId();

            Log.d("Crash HERE", "Second PArt");

            Log.d("DEBUG", "LATITUDE IS " + latitude + " AND LONGITUDE IS " + longitude);

            ParseObject foodGroup = new ParseObject("FoodGroup");
            foodGroup.put("GroupLocation", new ParseGeoPoint(latitude,longitude));
            foodGroup.put("GroupAdmin", ParseUser.getCurrentUser().toString());
            foodGroup.put("GroupMaxParty", groupMaxParty);
            foodGroup.put("GroupName", groupNameString);
            foodGroup.put("GroupMembers", groupMembers);
            foodGroup.put("GroupVenueID", theVenue);
            foodGroup.put("GroupVenueName", venueName);
            foodGroup.put("GroupVenueAddress", venueAddress);
            foodGroup.put("DateTime", dateTime);

            foodGroup.saveInBackground();
            finish();


//            ParseObject foodGroup = new ParseObject("test");
//            foodGroup.put("geo", new ParseGeoPoint(3.0666075,101.6116721));
//            foodGroup.put("meme", "asdasdasdasd");
//            foodGroup.put("theArray", groupMembers);
//            foodGroup.saveInBackground();
            Toast.makeText(CreateGroupActivity.this, "SAVED!", Toast.LENGTH_SHORT).show();
            Log.d("Crash HERE", "third PArt");

        }
    }


    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        selectedDate = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
        Toast.makeText(CreateGroupActivity.this, "Selected Date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        selectedTime = String.valueOf(hourOfDay) + String.valueOf(minute);
        Toast.makeText(CreateGroupActivity.this, "Selected Time:" + hourOfDay + "-" + minute, Toast.LENGTH_LONG).show();
    }
}
