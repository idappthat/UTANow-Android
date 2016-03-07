package com.mobi.utanow.bookmarks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobi.utanow.R;
import com.mobi.utanow.helpers.CircleTransform;
import com.mobi.utanow.models.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert on 2/18/2016
 * .
 */
public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.FavoriteHolder> {

    List<Event> events;

    public BookmarksAdapter() {
        events = new ArrayList<>();
    }

    public void addBookmark(Event event) {
        events.add(event);
        notifyItemInserted(events.size()-1);
    }

    public void removeBookmark(Event event) {
        events.remove(event);
        notifyItemRemoved(events.indexOf(event));
    }

    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list_item,parent,false);
        return new FavoriteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavoriteHolder holder, int position){
        Event event = events.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {return events.size();}

    public static class FavoriteHolder extends RecyclerView.ViewHolder {
        TextView tvEvent, tvClub, tvDescription;
        ImageView imOrg;

        public FavoriteHolder(View itemView) {
            super(itemView);

            tvEvent = (TextView) itemView.findViewById(R.id.event_name);
            tvClub = (TextView) itemView.findViewById(R.id.club_name);
            tvDescription = (TextView) itemView.findViewById(R.id.event_description);
            imOrg = (ImageView) itemView.findViewById(R.id.club_profile_picture);
        }

        public void bind(Event event){
            tvEvent.setText(event.getTitle());
            tvClub.setText(event.getOrganization());
            tvDescription.setText(event.getDescription());

            Picasso.with(itemView.getContext())
                    .load(event.getOrganizationImg())
                    .transform(new CircleTransform())
                    .into(imOrg);
        }
    }
}