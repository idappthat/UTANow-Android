package com.mobi.utanow.helpers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

/**
 * Created by Kevin on 2/26/2016.
 */
public class CustomLinearLayoutManager extends LinearLayoutManager {

        boolean canScroll=false;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
        public boolean canScrollVertically() {
        return canScroll;
    }
    public void setScrollable(boolean scrollable){
        canScroll = scrollable;
    }

}
