package com.mobi.utanow.createevent;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.firebase.client.Firebase;
import com.mobi.utanow.R;
import com.mobi.utanow.UtaNow;
import com.mobi.utanow.models.Event;

import java.io.File;
import java.util.Calendar;

import javax.inject.Inject;

public class CreateEventActivity extends AppCompatActivity {

    static final int PICK_IMAGE = 1;
    FloatingActionButton fab;
    ImageButton attachButton;
    EditText eventTitle, organization,description;
    Boolean eventTitleBol=false,organizationBol=false, descriptionBol=false;
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


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }
}





