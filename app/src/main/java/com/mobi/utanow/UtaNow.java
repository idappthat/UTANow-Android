package com.mobi.utanow;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.mobi.utanow.components.AppComponent;
import com.mobi.utanow.components.DaggerAppComponent;
import com.mobi.utanow.modules.AppModule;
import com.mobi.utanow.modules.NetModule;

/**
 * Created by anthony on 2/5/16.
 */
public class UtaNow extends Application
{
    private AppComponent appComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();
    }

    public AppComponent getAppComponent()
    {
        return appComponent;
    }
}
