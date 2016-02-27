package com.mobi.utanow.createevent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.mobi.utanow.R;
import com.mobi.utanow.UtaNow;
import com.mobi.utanow.models.Event;

import javax.inject.Inject;

import javax.inject.Inject;

public class CreateEventActivity extends AppCompatActivity {


    FloatingActionButton fab;
    EditText eventTitle, organization,description;
    Boolean eventTitleBol=false,organizationBol=false, descriptionBol=false;
    @Inject
    Firebase mFirebase;


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

        eventTitle = (EditText) findViewById(R.id.add_event_title);
        organization = (EditText) findViewById(R.id.add_event_organization);
        description = (EditText) findViewById(R.id.add_event_description);



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
}
