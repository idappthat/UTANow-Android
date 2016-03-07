package com.mobi.utanow.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationManagerCompat;

import com.firebase.client.Firebase;
import com.mobi.utanow.UtaNow;
import com.mobi.utanow.jobs.BaseJob;
import com.mobi.utanow.models.Event;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anthony on 2/6/16.
 */
@Module
public class AppModule {
    UtaNow mApplication;

    public AppModule(UtaNow application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    public NotificationManagerCompat notificationCompat() {
        return NotificationManagerCompat.from(mApplication);
    }
//
//    @Provides
//    @Singleton
//    public JobManager jobManager() {
//        Configuration config = new Configuration.Builder(mApplication)
//                .consumerKeepAlive(45)
//                .maxConsumerCount(3)
//                .minConsumerCount(1)
//                .injector(new DependencyInjector()
//                {
//                    @Override
//                    public void inject(Job job)
//                    {
//                        if (job instanceof BaseJob) {
//                            ((BaseJob) job).inject(mApplication.getAppComponent());
//                        }
//                    }
//                })
//                .build();
//
//        return new JobManager(mApplication, config);
//    }

    @Provides
    @Singleton
    public Firebase firebase() {
        return new Firebase("https://uta-now.firebaseio.com/");
    }

    @Provides
    @Singleton
    public Bus eventBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    public SharedPreferences sharedPrefs() {
        return mApplication.getSharedPreferences("UTANOW", Context.MODE_PRIVATE);
    }
}
