package com.example.mehak.notes_maker;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class LanguageFragment extends Fragment {

    private static final String API_KEY = "AIzaSyAeR77CF6NjaO1YdTITlanN41DS4Hfg4Rg";
    Handler textViewHandler;
    TextView textView;
    public static final String LANG_DETAIL = "mainDetails";

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
        Log.w(TAG, "ok");
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public LanguageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.e("working","working");
        updateMovie();

        View rootView = inflater.inflate(R.layout.activity_notes, container, false);
        textView = (TextView) rootView.findViewById(R.id.txtSpeechInput);
        textViewHandler = new Handler();
        /*final TextView textView = (TextView) findViewById(R.id.text_view);
        final Handler textViewHandler = new Handler();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                TranslateOptions options = TranslateOptions.newBuilder()
                        .setApiKey(API_KEY)
                        .build();
                Translate translate = options.getService();
                final Translation translation =
                        translate.translate("Hello World",
                                Translate.TranslateOption.targetLanguage("de"));
                textViewHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (textView != null) {
                            textView.setText(translation.getTranslatedText());
                        }
                    }
                });
                return null;
            }
        }.execute();
        //return null;
    }*/
        return rootView;
}
    class FetchResult extends AsyncTask<String, Void, Void > {

        @Override
        protected Void doInBackground(String... params) {

            TranslateOptions options = TranslateOptions.newBuilder()
                    .setApiKey(API_KEY)
                    .build();
            Translate translate = options.getService();
            final Translation translation =
                    translate.translate("Hello World",
                            Translate.TranslateOption.targetLanguage("de"));
            textViewHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (textView != null) {
                        textView.setText(translation.getTranslatedText());
                    }
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("ok","ok");
            super.onPostExecute(aVoid);
        }
    }

    public void updateMovie() {
        //if (isNetworkAvailable()) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String select_language = prefs.getString(getString(R.string.pref_general_key), getString(R.string.english));
            //moviesList.clear();

            /*if(sort_by.equals(getString(R.string.favorites))){
                fetchFavorites();
            }
            else{*/
            FetchResult fr = new FetchResult();
            fr.execute(select_language);
            //}
        /*}
        else {
            Toast.makeText(getContext(), "No Connection!\nCheck your Internet Connection", Toast.LENGTH_LONG).show();
        }*/
    }


    public void onPreferenceChanged(String select_language) {
        Log.v(TAG, "PREFERENCE CHANGED");
        //moviesList.clear();

        FetchResult fr = new FetchResult();
        fr.execute(select_language);

        /*if (sort_by.equals(getString(R.string.favorites))) {

            fetchFavorites();
        } else {
            if (isNetworkAvailable()) {

                FetchMovieTask movieTask = new FetchMovieTask();
                movieTask.execute(sort_by);
            } else
                Toast.makeText(getContext(), "No Connection!\nCheck your Internet Connection",
                        Toast.LENGTH_LONG).show();

        }*/
    }

}
