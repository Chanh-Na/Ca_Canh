package com.example.letrongtin.menunavigation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.letrongtin.menunavigation.R;
import com.example.letrongtin.menunavigation.activity.SendData;
import com.example.letrongtin.menunavigation.adapter.DecorateAdapter;
import com.example.letrongtin.menunavigation.model.Decorate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Le Trong Tin on 11/30/2017.
 */

public class DecoratesAdFragment extends Fragment {

    ArrayList<Decorate> decorates;
    DecorateAdapter decorateAdapter;

    RecyclerView recyclerView;
    TextView title;
    Button btnGo;
    ProgressBar progressBar;

    DatabaseReference mDatabase;
    SendData sendData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_object_ad, container, false);

        decorates = new ArrayList<>();
        decorateAdapter = new DecorateAdapter(decorates, getContext());

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(decorateAdapter);

        title = view.findViewById(R.id.title);
        btnGo = view.findViewById(R.id.btnGo);
        progressBar = view.findViewById(R.id.progressbar_loading);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        sendData = (SendData) getActivity();

        GetDecorates();

        title.setText("Trang trí");

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData.ChangFragment(3,"decorates");
            }
        });
        return view;
    }

    private void GetDecorates(){
        mDatabase.child("Decorates").orderByKey().limitToFirst(3).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int count = 0;
                for (DataSnapshot child : children) {

                    count++;
                    if (count >= dataSnapshot.getChildrenCount()) {
                        //stop progress bar here

                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        Decorate decorate = dataSnapshot.getValue(Decorate.class);
                        decorates.add(decorate);
                        decorateAdapter.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
