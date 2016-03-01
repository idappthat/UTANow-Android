package com.mobi.utanow.eventslist;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.mobi.utanow.R;
import com.mobi.utanow.UtaNow;
import com.mobi.utanow.bookmarks.BookMarksActivity;
import com.mobi.utanow.createevent.CreateEventActivity;
import com.mobi.utanow.login.LoginActivity;
import com.mobi.utanow.models.Event;
import com.mobi.utanow.organizations.OrganizationsActivity;
import com.mobi.utanow.settings.SettingsActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;


/**
 * Created by Anthony on 11/13/15.
 *
 */
public class EventListActivity extends AppCompatActivity
{
    @Inject
    Bus mEventBus;

    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle mToggle;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefresh;
    EventsAdapter mAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        ((UtaNow) getApplication()).getAppComponent().inject(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDrawer();
        initSwipeRefresh();
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mEventBus.register(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mEventBus.unregister(this);
    }

    private void initSwipeRefresh()
    {
    mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
    mSwipeRefresh.setProgressViewEndTarget(false, 300);
    mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fakeData();
                    mSwipeRefresh.setRefreshing(false);
                }
            }, 2000);
        }
    });
}

    private void initRecyclerView()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.eventList);
        context = mRecyclerView.getContext();
        mAdapter = new EventsAdapter(context);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fakeData()
    {
        Event e1 = new Event("Fun", "Doing cool stuff", "The club", "http://i.imgur.com/K32XT35.png");
        Event e2 = new Event("Fun", "Doing cool stuff", "The club", "http://i.imgur.com/aZOgXw7.jpg");
        Event e3 = new Event("Fun", "Doing cool stuff", "The club", "http://i.imgur.com/7xYpSBK.jpg");
        Event e4 = new Event("Fun", "Doing cool stuff", "The club", "http://i.imgur.com/7xYpSBK.jpg");



        mAdapter.addEvent(e1, 0);
        mAdapter.addEvent(e2, 0);
        mAdapter.addEvent(e3, 0);
    }

    private void initDrawer()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)

            {
                selectDrawerItem(menuItem);
                return true;
            }
        });

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mToggle);
    }

    private void selectDrawerItem(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.bookmarks:
                Intent b = new Intent(this, BookMarksActivity.class);
                startActivity(b);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.createEvent:
                Intent c = new Intent(this, CreateEventActivity.class);
                startActivity(c);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.organizations:
                Intent o = new Intent(this, OrganizationsActivity.class);
                startActivity(o);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.logOut:
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }
}
