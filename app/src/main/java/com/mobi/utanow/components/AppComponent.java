package com.mobi.utanow.components;

import com.mobi.utanow.eventslist.EventListActivity;
import com.mobi.utanow.modules.AppModule;
import com.mobi.utanow.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by anthony on 2/7/16.
 */
@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface AppComponent
{
    void inject(EventListActivity activity);
}

