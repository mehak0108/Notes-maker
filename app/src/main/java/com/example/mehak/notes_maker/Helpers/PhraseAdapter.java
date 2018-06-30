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

public class PhraseAdapter extends BaseAdapter {

    private Context context;
    private List<KeyPhrase> list;

    public PhraseAdapter(Context context, ArrayList<KeyPhrase> list){
        this.context = context;
        this.list = list;
    }

    public void add(KeyPhrase s){
        this.list.add(s);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        }


        KeyPhrase currentPhrase;
        currentPhrase = list.get(position);

        TextView nameTextView= (TextView)listItemView.findViewById(R.id.wordName);
        nameTextView.setText("Word:"+ currentPhrase.name);

        TextView urlTextView= (TextView)listItemView.findViewById(R.id.wordUrl);
        urlTextView.setText("URL: " + currentPhrase.url);

        TextView langTextView= (TextView)listItemView.findViewById(R.id.wordLanguage);
        langTextView.setText("Language: " + currentPhrase.language);

        return listItemView;
    }
}
