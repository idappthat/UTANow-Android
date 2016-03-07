package com.mobi.utanow.bookmarks;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.mobi.utanow.R;
import com.mobi.utanow.UtaNow;
import com.mobi.utanow.models.Event;

import javax.inject.Inject;

public class BookMarksActivity extends AppCompatActivity {

    @Inject
    Firebase mFirebase;
    @Inject
    SharedPreferences mPrefs;

    String uuid;
    BookmarksAdapter adapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_marks);

        ((UtaNow) getApplication()).getAppComponent().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uuid = mPrefs.getString("uuid", "");
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        adapter = new BookmarksAdapter();

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(adapter);

        if(uuid.length() > 0) {
            mFirebase.child("userInfo").child(uuid).child("bookmarks").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    mFirebase.child("events").child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Event event = dataSnapshot.getValue(Event.class);
                            adapter.addBookmark(event);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {}
                    });
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    mFirebase.child("events").child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Event event = dataSnapshot.getValue(Event.class);
                            adapter.removeBookmark(event);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {}
                    });
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(FirebaseError firebaseError) {}
            });
        }
    }
}
