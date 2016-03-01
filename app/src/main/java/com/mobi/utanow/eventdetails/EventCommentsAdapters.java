package com.mobi.utanow.eventdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.mobi.utanow.R;
import com.mobi.utanow.models.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Kevin on 2/26/2016.
 */
public class EventCommentsAdapters extends RecyclerView.Adapter<EventCommentsAdapters.CommentHolder> {

    List<Comment> commentList;
    LayoutInflater inflater;

    public EventCommentsAdapters(Context context){
        inflater = LayoutInflater.from(context);
        commentList = new ArrayList<>();
    }

    public EventCommentsAdapters(Context context, List<Comment> list){
        inflater = LayoutInflater.from(context);
        commentList = list;
        Log.d("test",list.size()+"");
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.comment_list_item, parent, false);
        CommentHolder holder = new CommentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        holder.bind(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder{
        TextView author, comment;

        public CommentHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author);
            comment = (TextView) itemView.findViewById(R.id.comment);
        }
        public void bind(Comment commentModel){
            Log.d("test7","test7");
            author.setText(commentModel.getAuthor());
            comment.setText(commentModel.getComment());
        }

    }
}
