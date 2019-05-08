package com.example.noisibel;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ComparisonRecyclerAdapter extends RecyclerView.Adapter<ComparisonRecyclerAdapter.ViewHolder>{
    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    private int active = -1;



    public ComparisonRecyclerAdapter(Context context, List<String> data ){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        active = -1;
    }

    //change the active decibel comparison item colour
    public void changeItem(int position){
        active = position;
        notifyItemChanged(active);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.comparison_row, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = mData.get(position);
        holder.myTextView.setText(title);
        if(position >= 0 && position < 14 && active >= 0 && active < 14){
            if (position == active ) {
                holder.myTextView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            } else  {
                holder.myTextView.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.comparisonRow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
