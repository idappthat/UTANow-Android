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
import android.widget.TextView;


import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.mobi.utanow.R;
import com.mobi.utanow.helpers.CircleTransform;
import com.mobi.utanow.helpers.CustomLinearLayoutManager;
import com.mobi.utanow.models.Comment;
import com.mobi.utanow.models.Event;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by Anthony on 11/13/15.
 */
public class EventDetailsActivity extends AppCompatActivity {

    public static String IMAGE_URL = "image";
    public static String ORG_IMG ="orgimg";
    public static String ORG_NAME = "orgname";
    public static String EVENT_NAME = "eventname";
    public static String EVENT_DES = "eventdes";


    @Inject
    Firebase mFirebase;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    ImageView groupImage, toolbarHeader;
    Intent intent;
    TextView mDescrption;
    List<Comment> commentList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        groupImage = (ImageView)findViewById(R.id.group_image);
        mDescrption = (TextView) findViewById(R.id.tv_description);
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
        commentList.setNestedScrollingEnabled(false);


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
    public void initCollapsingToolBar() {
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(intent.getStringExtra(EVENT_NAME));

        toolbarHeader = (ImageView)findViewById(R.id.header);

        Picasso.with(this)
                .load(intent.getStringExtra(IMAGE_URL))
                .into(toolbarHeader);
    }

    public void initData(Intent intent) {

        Picasso.with(this)
                .load(intent.getStringExtra(ORG_IMG))
                .transform(new CircleTransform())
                .into(groupImage);

        mDescrption.setText(intent.getStringExtra(EVENT_DES));

    }
}
