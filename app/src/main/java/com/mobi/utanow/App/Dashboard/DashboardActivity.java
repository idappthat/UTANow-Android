package com.mobi.utanow.App.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.mobi.utanow.myapplication.R;

/**
 * Created by Anthony on 11/13/15.
 */
public class DashboardActivity extends AppCompatActivity{

    Intent intent;
    Context context;
    Button logoutButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        context = this;


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Test");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setVisibility(View.VISIBLE);

        // Getting viewpage
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new DashboardFragmentPageAdapter(getSupportFragmentManager(), DashboardActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }
}
