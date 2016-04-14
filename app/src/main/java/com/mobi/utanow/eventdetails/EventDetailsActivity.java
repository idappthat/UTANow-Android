package com.mobi.utanow.eventdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.mobi.utanow.R;
import com.mobi.utanow.helpers.CircleTransform;
import com.mobi.utanow.helpers.CustomLinearLayoutManager;
import com.mobi.utanow.models.Comment;
import com.mobi.utanow.organizations.OrganizationsActivity;
import com.squareup.picasso.Picasso;

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
    final int ADD_COMMENT_CODE = 1000;


    @Inject
    Firebase mFirebase;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    ImageView groupImage, toolbarHeader;
    Intent intent;
    TextView mDescrption;
    List<Comment> commentList;
    Context context;
    AppBarLayout appBarLayout;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        groupImage = (ImageView)findViewById(R.id.group_image);
        //mDescrption = (TextView) findViewById(R.id.tv_description);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        context = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setVisibility(View.VISIBLE);

        intent = getIntent();

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        //used to check if toolbar is collapsed or not
        final int scrollRange = appBarLayout.getTotalScrollRange() ;

        initCollapsingToolBar();
        initData(intent);

        fakeData();
        EventCommentsAdapters adapter = new EventCommentsAdapters(this,commentList);
        final RecyclerView commentList = (RecyclerView) findViewById(R.id.comments_recycler_view);
        commentList.setAdapter(adapter);

        final CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(this);

        commentList.setLayoutManager(new LinearLayoutManager(this));
      //  linearLayoutManager.setScrollable(false);
        commentList.setNestedScrollingEnabled(false);

        groupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrganizationsActivity.class);
                startActivity(intent);
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // this will lock the nested recycler view's scrolling until the vertical offset
                // is equal to the parent appbar's scroll range... if it is equal then the
                // appbar has collapsed to the top
                if (verticalOffset == appBarLayout.getTotalScrollRange() *-1) {
                    commentList.setNestedScrollingEnabled(true);
                } else if (verticalOffset ==0 ){
                    commentList.setNestedScrollingEnabled(false);
                }
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = intent.getStringExtra(EVENT_NAME);
                intent = new Intent(context,CommentsActivity.class);
                intent.putExtra("eventTitle",title);
                startActivityForResult(intent,ADD_COMMENT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ADD_COMMENT_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(context,"Comment successfully added",Toast.LENGTH_SHORT).show();
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
    }

    public void fakeData(){
        commentList = new ArrayList<>();
        commentList.add(new Comment("Comment","Author"));
        commentList.add(new Comment("test1","test1"));
        commentList.add(new Comment("test2","test2"));
        commentList.add(new Comment("test3","test3"));
        commentList.add(new Comment("test3","test3"));
        commentList.add(new Comment("test3","test3"));
        commentList.add(new Comment("test3","test3"));
        commentList.add(new Comment("test3","test3"));
        for(int i = 0 ; i < 10; i++){
            commentList.add(new Comment("Comfment "+i,"Author "+i));
        }


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

       // mDescrption.setText(intent.getStringExtra(EVENT_DES));

    }
}
