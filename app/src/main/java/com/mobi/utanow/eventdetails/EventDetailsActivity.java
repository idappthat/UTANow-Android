package com.mobi.utanow.eventdetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.mobi.utanow.R;
import com.mobi.utanow.helpers.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.IOException;


/**
 * Created by Anthony on 11/13/15.
 */
public class EventDetailsActivity extends AppCompatActivity{

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    Bitmap image;
    ImageView groupImage, toolbarHeader;
    CircleTransform transformer;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        groupImage = (ImageView)findViewById(R.id.group_image);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();

        initCollapsingToolBar();
        initData(intent);

    }
    public void initCollapsingToolBar(){
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(intent.getStringExtra("eventName"));

        toolbarHeader = (ImageView)findViewById(R.id.header);

        Picasso.with(this)
                .load(intent.getStringExtra("imgURL"))
                .into(toolbarHeader);
    }

    public void initData(Intent intent){
        Picasso.with(this)
                .load(intent.getStringExtra("imgURL"))
                .transform(new CircleTransform())
                .into(groupImage);

    }
}
