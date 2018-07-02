package com.example.mehak.notes_maker;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity {

    ArrayList<String> result;
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    DatabaseReference myRef;
    public static String mLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    myRef = FirebaseDatabase.getInstance().getReference();
                    myRef.child("Notes").push().setValue(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_phrase){

            if(result == null){
                Toast.makeText(this,"Please enter a text!",Toast.LENGTH_SHORT).show();

            }else{
                Intent intent  = new Intent(NotesActivity.this, PhrasesActivity.class);
                intent.putExtra("Words", result.get(0));
                startActivity(intent);
            }
        }
        if(id == R.id.action_language){
            Intent intent = new Intent(NotesActivity.this,SettingsActivity.class);
            //intent.putExtra("Words",result.get(0));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String sort_by = prefs.getString(getString(R.string.pref_general_key), getString(R.string.italian));
        if (mLanguage!=null && !sort_by.equals(mLanguage)){
            LanguageFragment mf=(LanguageFragment) getSupportFragmentManager().
                    findFragmentById(R.id.lang_notes);
            Log.e("done","gggg");

            /*Bundle args = new Bundle();
            if (getIntent().getParcelableExtra("Words")== null)
                Log.v("not ok", "intent is null");
            else
                Log.v("ok", "intent is not null");
            args.putParcelable(LanguageFragment.LANG_DETAIL,(getIntent().getParcelableExtra("Words")));
            LanguageFragment fragment = new LanguageFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.lang_container, fragment).commit();*/
            //mf = new LanguageFragment();
            //mf.updateMovie();
            if(mf!=null)
                mf.onPreferenceChanged(sort_by);

        }
        mLanguage= sort_by;

    }


}
