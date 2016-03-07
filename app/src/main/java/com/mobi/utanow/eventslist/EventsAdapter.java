package com.mobi.utanow.eventslist;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mobi.utanow.R;
import com.mobi.utanow.UtaNow;
import com.mobi.utanow.eventdetails.EventDetailsActivity;
import com.mobi.utanow.models.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by anthony on 2/6/16.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventHolder> {

    @Inject
    Firebase mFirebase;
    @Inject
    SharedPreferences mPrefs;

    List<Event> eventList;
    String uuid;
    RecyclerView mView;

    public EventsAdapter(Application application, RecyclerView view) {
        eventList = new ArrayList<>();
        ((UtaNow) application).getAppComponent().inject(this);
        uuid = mPrefs.getString("uuid", "");
        mView = view;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        holder.bind(eventList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void addEvent(Event e, int index) {
        eventList.add(index, e);
        notifyItemInserted(index);
    }

    public void removeEvent(Event event) {
        eventList.remove(event);
        notifyItemRemoved(eventList.indexOf(event));
    }

    public void saveBookmark(Event event) {
        mFirebase.child("userInfo").child(uuid).child("bookmarks").child(event.getKey()).setValue(true);
    }

    public void removeBookmark(Event event) {
        mFirebase.child("userInfo").child(uuid).child("bookmarks").child(event.getKey()).removeValue();
    }

    public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImage;
        TextView mTitle, mOrg;
        Event mEvent;
        ImageButton mMapButton, mBookmarkButton;
        boolean isBookmarked;

        public EventHolder(View itemView) {
            super(itemView);

            mImage = (ImageView) itemView.findViewById(R.id.im_event);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mOrg = (TextView) itemView.findViewById(R.id.tv_org);
            mMapButton = (ImageButton) itemView.findViewById(R.id.ib_map);
            mBookmarkButton = (ImageButton) itemView.findViewById(R.id.ib_bookmark);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EventDetailsActivity.class);
                    intent.putExtra(EventDetailsActivity.ORG_NAME, mEvent.getOrganization());
                    intent.putExtra(EventDetailsActivity.EVENT_NAME, mEvent.getTitle());
                    intent.putExtra(EventDetailsActivity.EVENT_DES, mEvent.getDescription());
                    intent.putExtra(EventDetailsActivity.IMAGE_URL, mEvent.getImgUrl());
                    intent.putExtra(EventDetailsActivity.ORG_IMG, mEvent.getOrganizationImg());
                    v.getContext().startActivity(intent);
                }
            });

            mMapButton.setOnClickListener(this);
            mBookmarkButton.setOnClickListener(this);
        }

        public void bind(Event model) {
            mEvent = model;

            mFirebase.child("userInfo").child(uuid).child("bookmarks").child(mEvent.getKey())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    isBookmarked = dataSnapshot.getValue() != null;
                    setIcon(isBookmarked);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {}
            });

            Picasso.with(itemView.getContext())
                    .load(model.getImgUrl())
                    .into(mImage);

            mTitle.setText(model.getTitle());
            mOrg.setText(model.getOrganization());
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_map:
                    mapEvent();
                    break;

                case R.id.ib_bookmark:
                   bookmark();
            }
        }

        private void mapEvent() {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + mEvent.getCoords());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
                itemView.getContext().startActivity(mapIntent);
            }
        }

       private void bookmark() {
           if(uuid.length() > 0) {
               if(isBookmarked) {
                   removeBookmark(mEvent);
                   Snackbar.make(mView, "Event removed", Snackbar.LENGTH_SHORT);
                   isBookmarked = false;
                   setIcon(isBookmarked);
               }
               else {
                   Snackbar.make(mView, "Event added", Snackbar.LENGTH_SHORT);
                   saveBookmark(mEvent);
                   isBookmarked = true;
                   setIcon(isBookmarked);
               }
           }
           else {
               Log.d("events", "uuid null");
               Snackbar.make(mView, "You must login to use this feature", Snackbar.LENGTH_SHORT);
           }
       }

        private void setIcon(boolean marked) {
            Drawable outline = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_bookmark_border_24dp);
            Drawable filled = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_bookmark_white);

            Drawable icon = marked ? filled : outline;
            mBookmarkButton.setImageDrawable(icon);
        }
    }

}
