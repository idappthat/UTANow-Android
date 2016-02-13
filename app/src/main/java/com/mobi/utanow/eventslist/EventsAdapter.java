package com.mobi.utanow.eventslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobi.utanow.models.EventModel;
import com.mobi.utanow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthony on 2/6/16.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventHolder>
{
   List<EventModel> eventList = new ArrayList<>();

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position)
    {
        holder.bind(eventList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return eventList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder
    {
        public EventHolder(View itemView)
        {
         super(itemView);
        }

        public void bind(EventModel model)
        {
        }
    }
}
