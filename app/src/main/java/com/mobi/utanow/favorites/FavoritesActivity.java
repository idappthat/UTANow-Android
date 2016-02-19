package com.mobi.utanow.favorites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mobi.utanow.Adapter.FavoritesAdapter;
import com.mobi.utanow.R;
import com.mobi.utanow.models.EventModel;

public class FavoritesActivity extends AppCompatActivity {
    FavoritesAdapter adapter;
    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        //ToDo add toolbar?
        LinearLayoutManager lm = new LinearLayoutManager(this);
        adapter = new FavoritesAdapter();

        list = (RecyclerView) findViewById(R.id.list);
        list.setHasFixedSize(true);
        list.setLayoutManager(lm);
        list.setAdapter(adapter);
        addFakeEvents();
    }


    //ToDo actually recieve real information
    public void addFakeEvents(){
        adapter.addFavorite(new EventModel("BaseBall Game","Team Horses with Bats", "1....2.....3 strikes you're out!"));
        adapter.addFavorite(new EventModel("Elite Four","Watch as Red fights rival", "Dude has legendary pokemon. Wont even be fair"));
        adapter.addFavorite(new EventModel("UTA Big Band Concert","UTA MUSI Department", "Why is it a big band if it's smaller than a symphony?"));
        adapter.addFavorite(new EventModel("Dope Party","Dope Party Club", "Actually it's a school sponsored so it's def not dope."));
    }
}
