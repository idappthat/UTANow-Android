package com.mobi.utanow.favorites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mobi.utanow.Adapter.FavoritesAdapter;
import com.mobi.utanow.R;

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

        //STOPED HERE
    }
}
