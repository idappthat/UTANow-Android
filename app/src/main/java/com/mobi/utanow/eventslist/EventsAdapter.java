package com.mobi.utanow.eventslist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobi.utanow.R;
import com.mobi.utanow.eventdetails.EventDetailsActivity;
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
    LayoutInflater inflater;

    public EventsAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.event_list_item, parent, false);
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

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView mImage;
        TextView mTitle;
        TextView mOrg;
        String url;

        public EventHolder(View itemView)
        {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.im_event);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mOrg = (TextView) itemView.findViewById(R.id.tv_org);
            itemView.setOnClickListener(this);
        }

        public void bind(Event model)
        {
            Picasso.with(itemView.getContext())
                    .load(model.getImgUrl())
                    .into(mImage);
            url = model.getImgUrl();
            mTitle.setText(model.getTitle());
            mOrg.setText(model.getOrganization());
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(),EventDetailsActivity.class);
            intent.putExtra("orgName",mOrg.getText().toString());
            intent.putExtra("eventName",mTitle.getText().toString());
            intent.putExtra("imgURL",url);
            v.getContext().startActivity(intent);
        }
    }
}
