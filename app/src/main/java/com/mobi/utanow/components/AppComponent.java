package com.mobi.utanow.components;

import com.mobi.utanow.SplashActivity;
import com.mobi.utanow.bookmarks.BookMarksActivity;
import com.mobi.utanow.bookmarks.BookmarksAdapter;
import com.mobi.utanow.createevent.CreateEventActivity;
import com.mobi.utanow.eventslist.EventListActivity;
import com.mobi.utanow.eventslist.EventsAdapter;
import com.mobi.utanow.login.LoginActivity;
import com.mobi.utanow.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by anthony on 2/7/16.
 */
@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {
    void inject(EventListActivity activity);
    void inject(SplashActivity activity);
    void inject(LoginActivity activity);
    void inject(CreateEventActivity activity);
    void inject(BookMarksActivity activity);
    void inject(EventsAdapter adapter);
    void inject(BookmarksAdapter adapter);
}

