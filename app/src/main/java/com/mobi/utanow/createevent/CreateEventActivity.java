package com.mobi.utanow.createevent;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentContainer;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.firebase.client.Firebase;
import com.mobi.utanow.R;
import com.mobi.utanow.UtaNow;
import com.mobi.utanow.models.Event;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

public class CreateEventActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{


    static final int PICK_IMAGE = 1;
    FloatingActionButton fab;
    ImageButton attachButton;
    EditText eventTitle, organization,description;
    Boolean eventTitleBol=false,organizationBol=false, descriptionBol=false;
    Button btnStartTime,btnEndTime,btnDatePicker;
    Calendar startTimeWDate,endTime;


    public enum TimeChanging{//enum made to pass if start time or end time is being changed.
        START,END
    }
    TimeChanging timeChanging;
    @Inject
    Firebase mFirebase;
    TextView imageLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ((UtaNow) getApplication()).getAppComponent().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent();
            }
        });
        imageLocation = (TextView) findViewById(R.id.imageNameText);
        attachButton = (ImageButton) findViewById(R.id.attachButton);
        eventTitle = (EditText) findViewById(R.id.add_event_title);
        organization = (EditText) findViewById(R.id.add_event_organization);
        description = (EditText) findViewById(R.id.add_event_description);
        btnStartTime = (Button) findViewById(R.id.btnStartTime);
        btnEndTime = (Button) findViewById(R.id.btnEndTime);
        btnDatePicker = (Button) findViewById(R.id.btnDatePicker);


        //Time/Date Picker Stuff
        btnStartTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        CreateEventActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        false//false 12 hour true 24 hour
                );
                timeChanging=TimeChanging.START;
                tpd.show(getFragmentManager(), "Pick Start Time");
            }
        });
        btnEndTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        CreateEventActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        false//false 12 hour true 24 hour
                );
                timeChanging=TimeChanging.END;
                tpd.show(getFragmentManager(), "Pick End Time");
            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreateEventActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Pick Date");
            }
        });






        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage();
            }
        });
        //Text Listeners for the action bar
        eventTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Check if it is valid if it is show the a send button
                //if not hide the send button
                if (count>0) {
                    eventTitleBol =true;
                } else {
                    eventTitleBol=false;
                    fab.hide();
                }
                if ((eventTitleBol==true)&&(organizationBol==true)&&(descriptionBol==true)) {
                    fab.show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        organization.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Check if it is valid if it is show the a send button
                //if not hide the send button
                if (count>0) {
                    organizationBol =true;
                } else {
                    organizationBol=false;
                    fab.hide();
                }
                if ((eventTitleBol==true)&&(organizationBol==true)&&(descriptionBol==true)) {
                    fab.show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Check if it is valid if it is show the a send button
                //if not hide the send button
                if (count>0) {
                    descriptionBol =true;
                } else {
                    descriptionBol=false;
                    fab.hide();
                }
                if ((eventTitleBol==true)&&(organizationBol==true)&&(descriptionBol==true)) {
                    fab.show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    private void addNewEvent(){
        Event myEvent = new Event(eventTitle.getText().toString(),
                description.getText().toString(),
                organization.getText().toString(),
                "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Smiley.svg/2000px-Smiley.svg.png");
        mFirebase.child("events").push().setValue(myEvent);
        finish();
    }




    private void addImage(){
        /* THIS CODE LETS THE USER SELECT A DIFFERENT APPLICATION TO PICK PICTURES FROM. THE CURRENT CODE USES THE DEFAULT PICKER
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);*/
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                //TODO: stuff needs to added here in order to actually save the image. Right now it just saves the path, but not the image data stream
                imageLocation.setText(data.getData().getPath());
                //File picture = new File(data.getData().getPath());

                // Do something with the contact here (bigger example below)
            }
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if(timeChanging==TimeChanging.START){//enum made to pass which button is being changed. I'm not sure how else to pass the argument
            startTimeWDate.set(Calendar.MINUTE,minute);
            startTimeWDate.set(Calendar.HOUR,hourOfDay);
        }
        if(timeChanging==TimeChanging.END){
            endTime.set(Calendar.MINUTE,minute);
            endTime.set(Calendar.HOUR,hourOfDay);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        startTimeWDate.set(year,monthOfYear,dayOfMonth);
    }
}





