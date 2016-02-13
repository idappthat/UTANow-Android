package com.mobi.utanow.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import com.mobi.utanow.data.Api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anthony on 2/10/16.
 */
@Module
public class NetModule
{
    @Provides
    @Singleton
    SharedPreferences sharedPreferences(Application application)
    {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Api api()
    {
        return new Api();
    }
}

