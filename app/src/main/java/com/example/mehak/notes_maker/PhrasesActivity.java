package com.example.mehak.notes_maker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class PhrasesActivity extends AppCompatActivity implements ItemSelected{

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

    }

    @Override
    public void onItemSelected(KeyPhrase keyPhrase) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(keyPhrase.url));
        startActivity(browserIntent);

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
