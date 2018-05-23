package com.example.mehak.notes_maker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PhrasesActivity extends AppCompatActivity {

    String result;
    ArrayList<String> wordsArry;
    KeyPhrase[] keyPhrases;
    TextView tv;
    TextView tvphraseid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

        tv = (TextView)findViewById(R.id.tv_phrase);
        tvphraseid = (TextView)findViewById(R.id.tv_phraseid);

        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            result = bundle.getString("Words");
            FetchResult fetchResult = new FetchResult();
            fetchResult.execute();
        }

    }

    class FetchResult extends AsyncTask<String, Void, KeyPhrase[] >{

        String jsonString;
        @Override
        protected KeyPhrase[] doInBackground(String... strings) {

            try {
                 jsonString = GetKeyPhrases.GetJson(result);
                 keyPhrases = getWords(jsonString);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return keyPhrases;
        }

        @Override
        protected void onPostExecute(KeyPhrase[] keyPhrases) {

            tv.setText(keyPhrases.toString());
            super.onPostExecute(keyPhrases);
        }

        KeyPhrase kp;
        KeyPhrase keyPhrase[] = null;
        public KeyPhrase[] getWords(String str) throws JSONException {
            final String ID = "id";
            final String DOCUMENTS = "documents";
            final String KEY_PHRASE = "keyPhrases";
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray(DOCUMENTS);
            keyPhrase = new KeyPhrase[jsonArray.length()];

            for (int i=0; i<jsonArray.length() ; i++){
                JSONObject jobj = jsonArray.getJSONObject(i);

                kp = new KeyPhrase();
                kp.id = jobj.getString(ID);
                Log.e("Id",kp.id);
                JSONArray jarry = jobj.getJSONArray(KEY_PHRASE);

                for(int j=0; j<jarry.length() ; j++){
                    wordsArry.add(jarry.getString(j));
                }

                kp.res = wordsArry;
                keyPhrase[i] = kp;

            }

            return keyPhrase;

        }
    }
}
