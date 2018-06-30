package com.example.mehak.notes_maker.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mehak.notes_maker.KeyPhrase;
import com.example.mehak.notes_maker.R;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter{

    private Context context;
    private List<String> names;

    public GridAdapter(Context context, ArrayList<String> note_list) {
        this.context = context;
        this.names = note_list;
    }
    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_notes,parent,false);
        }

        String s = names.get(position);

        TextView nameTextView= (TextView)listItemView.findViewById(R.id.wordName);
        nameTextView.setText(s);

        return listItemView;
    }
}
