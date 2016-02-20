package com.mobi.utanow.eventslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobi.utanow.R;
import com.mobi.utanow.models.Event;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthony on 2/6/16.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventHolder>
{
   List<Event> eventList = new ArrayList<>();

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
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

    public void addEvent(Event e, int index)
    {
        eventList.add(index, e);
        notifyItemInserted(index);
    }

    public static class EventHolder extends RecyclerView.ViewHolder
    {
        ImageView mImage;
        TextView mTitle;
        TextView mOrg;

        public EventHolder(View itemView)
        {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.im_event);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mOrg = (TextView) itemView.findViewById(R.id.tv_org);
        }

        public void bind(Event model)
        {
            Picasso.with(itemView.getContext())
                    .load(model.getImgUrl())
                    .into(mImage);

            mTitle.setText(model.getTitle());
            mOrg.setText(model.getOrganization());
        }
    }
}
