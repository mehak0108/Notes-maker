package com.example.mehak.notes_maker;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PhrasesActivity extends AppCompatActivity {

    String result;
    ArrayList<KeyPhrase> wordsArry;
    //String[] keyPhrases;
    TextView tv;
    TextView tvphraseid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

        if (savedInstanceState == null){
            Bundle args = new Bundle();
            if (getIntent().getParcelableExtra("Words")== null)
                Log.v("not ok", "intent is null");
            else
                Log.v("ok", "intent is not null");
            args.putParcelable(PhraseFragment.PHRASE_DETAIL,(getIntent().getParcelableExtra("Words")));
            PhraseFragment fragment = new PhraseFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.details_container, fragment).commit();
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        /*tv = (TextView)findViewById(R.id.tv_phrase);
        tvphraseid = (TextView)findViewById(R.id.tv_phraseid);

        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            result = bundle.getString("Words");
            Log.v("result",result);*/

            //FetchResult fetchResult = new FetchResult();
            //fetchResult.execute();



    }



    /*private void initToolbar() {

        Log.e("working tool", "ok");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        if (toolbar != null) {
            Log.e("working tool", "ok");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }*/
}
