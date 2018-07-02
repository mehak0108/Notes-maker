package com.example.mehak.notes_maker;


import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
            //if(result!=null)
            Log.v("result",result);

            phraseList = new ArrayList<KeyPhrase>();
            adapter = new PhraseAdapter(getActivity(), phraseList);

            if(isNetworkAvailable()){
                FetchResult fetchResult = new FetchResult();
                fetchResult.execute();
            }
            else{
                Toast.makeText(getActivity(),"Please check the internet connection!",Toast.LENGTH_SHORT).show();

            }

        } else {
            phraseList = savedInstanceState.getParcelableArrayList("phraseList");
            adapter = new PhraseAdapter(getActivity(), phraseList);

        }

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

            if(keys.length>0){
                for (KeyPhrase current : keys)
                    adapter.add(current);
                adapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(getActivity(),"No specific keyword available!",Toast.LENGTH_SHORT).show();
            }
        }
        KeyPhrase[] resultList = null;

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
            resultList = new KeyPhrase[jarry.length()];
            if(jarry.length()>0) {

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
            }
            return resultList;
        }
    }

}
