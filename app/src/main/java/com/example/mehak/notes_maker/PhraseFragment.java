package com.example.mehak.notes_maker;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mehak.notes_maker.Helpers.PhraseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhraseFragment extends Fragment {

    ArrayList<KeyPhrase> phraseList;
    PhraseAdapter adapter;
    KeyPhrase kp;
    String result;
    public static final String PHRASE_DETAIL = "phraseDetails";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("phrases", phraseList);
        Log.w(TAG, "ok");
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public PhraseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "onCreateView");
        if (savedInstanceState == null || !savedInstanceState.containsKey("phraseList")) {

            Bundle bundle = getActivity().getIntent().getExtras();
            result = bundle.getString("Words");
            Log.v("result",result);

            phraseList = new ArrayList<KeyPhrase>();
            adapter = new PhraseAdapter(getActivity(), phraseList);

            FetchResult fetchResult = new FetchResult();
            fetchResult.execute();
        } else {
            phraseList = savedInstanceState.getParcelableArrayList("phraseList");
            adapter = new PhraseAdapter(getActivity(), phraseList);

        }

        /*Bundle args = getArguments();
        if(args!=null){
            kp = args.getParcelable(PHRASE_DETAIL);
            result = kp.name;
        }*/

        View rootView = inflater.inflate(R.layout.phrase_list, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.list);
        adapter = new PhraseAdapter(getActivity(), phraseList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if (isNetworkAvailable()) {
                    ((ItemSelected) getActivity()).onItemSelected(phraseList.get(position));

                /*}
                else {
                    Toast.makeText(getContext(), "No Connection!\nCheck your Internet Connection",
                            Toast.LENGTH_LONG).show();
                }*/
            }
        });
        return rootView;
    }

    class FetchResult extends AsyncTask<String, Void, KeyPhrase[] > {

        String jsonString =null;
        KeyPhrase[] kpobj;

        @Override
        protected KeyPhrase[] doInBackground(String... strings) {

            try {
                jsonString = GetKeyPhrases.GetJson(result);

                Log.e("json" , jsonString);

                kpobj = getWords(jsonString);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return kpobj;
        }

        @Override
        protected void onPostExecute(KeyPhrase[] keys) {

            for (KeyPhrase current : keys)
                adapter.add(current);
            adapter.notifyDataSetChanged();

            //Log.v("done","done");
        }
        KeyPhrase[] resultList;

        public KeyPhrase[] getWords(String str) throws JSONException {


            final String DOCUMENTS = "documents";
            final String ENTITIES = "entities";
            final String NAME = "name";
            final String WIKIPEDIALANGUAGE = "wikipediaLanguage";
            final String WIKIPEDIAURL = "wikipediaUrl";

            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = jsonObject.getJSONArray(DOCUMENTS);
            JSONObject jobj = jsonArray.getJSONObject(0);
            JSONArray jarry = jobj.getJSONArray(ENTITIES);

            if(jarry.length()>0) {
                resultList = new KeyPhrase[jarry.length()];
                for (int i = 0; i < jarry.length(); i++) {
                    JSONObject obj = jarry.getJSONObject(i);
                    kp = new KeyPhrase();
                    kp.name = obj.getString(NAME);
                    Log.e("name",kp.name);
                    kp.url = obj.getString(WIKIPEDIAURL);
                    Log.e("url",kp.url);
                    kp.language = obj.getString(WIKIPEDIALANGUAGE);
                    Log.e("lang",kp.language);

                    resultList[i] = kp;
                    //phraseList.add(kp);
                }

                return resultList;
            }
            else
                return null;

        }
    }




}
