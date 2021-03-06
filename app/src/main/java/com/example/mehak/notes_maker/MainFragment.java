package com.example.mehak.notes_maker;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mehak.notes_maker.Helpers.GridAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private DatabaseReference mDatabase;
    ArrayList<String> notes;
    GridAdapter gridAdapter;
    public static final String MAIN_DETAIL = "mainDetails";
    FloatingActionButton mfab;

    public void onCreate(Bundle savedInstanceState) {
        Log.v("on create", "onCreate");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v("ok", "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v("ok", "onDetach");
    }

    public void onStart()
    {
        super.onStart();
        Log.v("ok", "onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v("ok", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("ok", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("ok", "onDestroy");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putParcelableArrayList("notes", notes);
        super.onSaveInstanceState(outState);
        Log.v("ok", "onSaveInstanceState");

    }

    public void onResume(){
        Log.v("ok","onResume");
        super.onResume();
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("created","created");

        if(savedInstanceState==null || !savedInstanceState.containsKey("notes")){
            notes = new ArrayList<String>();
            gridAdapter = new GridAdapter(getContext(),notes);
            if(isNetworkAvailable()){
                getNotes();
            }
            else{
                Toast.makeText(getActivity(),"Please check the internet connection!",Toast.LENGTH_SHORT).show();
            }

        }
        else{
            notes = new ArrayList<String>();
            gridAdapter = new GridAdapter(getContext(),notes);
        }

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView notes_gridview = (GridView) rootView.findViewById(R.id.notes_gridview);
        gridAdapter = new GridAdapter(getContext(),notes);
        notes_gridview.setAdapter(gridAdapter);

        /*notes_gridview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/

        notes_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ok","ok");
                ((ItemSelectedNotes)getActivity()).onItemSelect(notes.get(position));
                Log.e("ok","ok");
            }
        });

        mfab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Notes are being recorded", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(getActivity(), NotesActivity.class);
                startActivity(intent);
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

    public void getNotes(){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.v("Review", "2");
                notes.clear();

                for (DataSnapshot q : dataSnapshot.getChildren()) {
                     Log.v("ok", q.getKey());
                    String s;
                     s = q.getValue(String.class);
                     Log.v("hmm1111", q.getValue(String.class));
                    notes.add(s);
                }

                if (notes.size() > 0) {

                    gridAdapter.notifyDataSetChanged();

                }else {

                    Toast.makeText(getActivity(), "No notes till now!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}




