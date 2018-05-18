package com.example.mehak.notes_maker.Helpers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mehak.notes_maker.R;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private Activity activity;
    private static LayoutInflater inflater;
    public MainAdapter(Activity a){
        activity = a;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MainAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public SwitchCompat switchCompat;

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }
}

