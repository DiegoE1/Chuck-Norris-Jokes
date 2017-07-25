package com.example.android.chucknorrisjokes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by diegoespinosa on 7/24/17.
 */

public class JokeAdapter extends  RecyclerView.Adapter<JokeAdapter.JokeAdapterViewHolder> {

    private ArrayList<String> jokeArrayList = new ArrayList<String>();

    public JokeAdapter(){

    }

    public class JokeAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView jokeListTextView;
        private final TextView jokeCountTextView;

        public JokeAdapterViewHolder(View itemView) {
            super(itemView);
            jokeListTextView = (TextView) itemView.findViewById(R.id.tv_item_content);
            jokeCountTextView = (TextView) itemView.findViewById(R.id.tv_item_count);

        }
    }

    @Override
    public JokeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.joke_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new JokeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JokeAdapterViewHolder holder, int position) {
        String jokeForX = jokeArrayList.get(position);
        Log.d("String jokeForX", jokeForX);
        holder.jokeListTextView.setText(jokeForX);
        position += 1;
        holder.jokeCountTextView.setText(String.valueOf(position) + "");

    }

    @Override
    public int getItemCount() {
        if (null == jokeArrayList) return 0;
        return jokeArrayList.size();
    }

    public void setJokeData(String jokeData){
        jokeArrayList.add(jokeData);
        notifyDataSetChanged();
    }
}
