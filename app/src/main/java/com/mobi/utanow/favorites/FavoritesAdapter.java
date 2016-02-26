package com.mobi.utanow.favorites;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobi.utanow.R;
import com.mobi.utanow.models.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robert on 2/18/2016.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteHolder> {
    List<Event> events;

    public FavoritesAdapter(){
        events = new ArrayList<>();
    }

    public void addFavorite(Event event){
        events.add(event);
        notifyItemInserted(events.size()-1);
    }
    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favorite,parent,false);
        return new FavoriteHolder(itemView);
    }
    @Override
    public void onBindViewHolder(FavoriteHolder holder, int position){
        Event event = events.get(position);
        holder.bind(event);
    }
    @Override
    public int getItemCount(){return events.size();}

    public class FavoriteHolder extends RecyclerView.ViewHolder{
        TextView tvEvent, tvClub, tvDescription;

        public FavoriteHolder(View itemView){
            super(itemView);
            tvEvent = (TextView) itemView.findViewById(R.id.event_Name);
            tvClub = (TextView) itemView.findViewById(R.id.club_Name);
            tvDescription = (TextView) itemView.findViewById(R.id.event_Description);
        }
        public void bind(Event event){
            tvEvent.setText(event.getTitle());
            tvClub.setText(event.getOrganization());
            tvDescription.setText(event.getDescription());
        }
    }
}