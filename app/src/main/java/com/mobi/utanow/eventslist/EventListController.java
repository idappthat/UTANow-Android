package com.mobi.utanow.eventslist;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

/**
 * Created by anthony on 2/6/16.
 */
public class EventListController implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener
{
    ViewPager mPager;
    TabLayout mTabLayout;

    public EventListController(ViewPager pager, TabLayout layout)
    {
        mPager = pager;
        mTabLayout = layout;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position)
    {
        mTabLayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab)
    {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}
}
