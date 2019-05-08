package com.example.noisibel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordingListAdapter extends RecyclerView.Adapter<RecordingListAdapter.ViewHolder>{
    private List<Recording> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    private Button viewBtn;
    private Button deleteBtn;

    public RecordingListAdapter(Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recording_row, parent, false);
        context = view.getContext();
        viewBtn = view.findViewById(R.id.view);
        deleteBtn = view.findViewById(R.id.delete);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String title = ListOfRecordings.getRecording(position).getFile().getName();
        holder.myTextView.setText(title);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,RecordingView.class);
                i.putExtra("recording",position);
                context.startActivity(i);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListOfRecordings.delete(position);
                notifyDataSetChanged();
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return ListOfRecordings.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.recordingName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }




    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
