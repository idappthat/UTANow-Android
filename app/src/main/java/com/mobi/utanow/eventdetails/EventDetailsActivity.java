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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.mobi.utanow.R;
import com.mobi.utanow.helpers.CircleTransform;
import com.mobi.utanow.helpers.CustomLinearLayoutManager;
import com.mobi.utanow.models.Comment;
import com.mobi.utanow.models.Event;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
    List<Comment> commentList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        groupImage = (ImageView)findViewById(R.id.group_image);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setVisibility(View.VISIBLE);

        intent = getIntent();

        initCollapsingToolBar();
        initData(intent);

        fakeData();
        EventCommentsAdapters adapter = new EventCommentsAdapters(this,commentList);
        RecyclerView commentList = (RecyclerView) findViewById(R.id.comments_recycler_view);
        commentList.setAdapter(adapter);


        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(this);

        commentList.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setScrollable(true);
    }

    public void fakeData(){
        commentList = new ArrayList<>();
        commentList.add(new Comment("test","test"));
        commentList.add(new Comment("test1","test1"));
        commentList.add(new Comment("test2","test2"));
        commentList.add(new Comment("test3","test3"));


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
