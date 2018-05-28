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
    String[] keyPhrases;
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
            Log.v("result",result);

            FetchResult fetchResult = new FetchResult();
            fetchResult.execute();

        }

    }

    class FetchResult extends AsyncTask<String, Void, String[] >{

        String jsonString =null;
        @Override
        protected String[] doInBackground(String... strings) {

            try {
                 jsonString = GetKeyPhrases.GetJson(result);

                 Log.e("json" , jsonString);

                 keyPhrases = getWords(jsonString);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return keyPhrases;
        }

        @Override
        protected void onPostExecute(String[] words) {

            for(int i=0;i<words.length-1; i++) {
                tv.setText(words[i] + " " + words[(i + 1)]);
            }
            super.onPostExecute(words);
        }

        /*KeyPhrase kp;
        KeyPhrase keyPhrase[] = null;*/
        String[] resultList = null;
        public String[] getWords(String str) throws JSONException {
            final String ID = "id";
            final String DOCUMENTS = "documents";
            final String KEY_PHRASE = "keyPhrases";
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray(DOCUMENTS);
            JSONObject jobj = jsonArray.getJSONObject(0);
            JSONArray jarry = jobj.getJSONArray(KEY_PHRASE);

            resultList = new String[jarry.length()];
            for(int i=0;i<jarry.length();i++){
                //Log.e("words",jarry.getString(0));
                resultList[i] = jarry.getString(i);
            }

            /*for (int i=0; i<jsonArray.length() ; i++){
                JSONObject jobj = jsonArray.getJSONObject(i);
                keyPhrase = new KeyPhrase[jsonArray.length()];
                kp = new KeyPhrase();
                kp.id = jobj.getString(ID);
                Log.e("Id",kp.id);
                JSONArray jarry = jobj.getJSONArray(KEY_PHRASE);

                for(int j=0; j<jarry.length() ; j++){
                    wordsArry.add(jarry.getString(j));
                }

                kp.res = wordsArry;
                keyPhrase[i] = kp;
            }*/

            return resultList;

        }
    }
}
