package com.example.mehak.notes_maker.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mehak.notes_maker.R;

import java.util.ArrayList;
import java.util.List;

public class PhraseAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public PhraseAdapter(Context context, ArrayList<String> list){
        this.context = context;
        this.list = list;
    }

    public void add(String s){
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


        Movie currentMovie;
        currentMovie= getItem(position);

        TextView nameTextView= (TextView)listItemView.findViewById(R.id.movieName);
        nameTextView.setText(currentMovie.title);

        TextView genreTextView= (TextView)listItemView.findViewById(R.id.movieRating);
        genreTextView.setText("User Rating: " + currentMovie.user_rating);

        TextView langTextView= (TextView)listItemView.findViewById(R.id.movieLanguage);
        langTextView.setText("Language: " + currentMovie.language);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        Picasso.with(context).load(list.get(position).poster_path).into(imageView);

        return listItemView;
    }
}
