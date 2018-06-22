package com.example.letrongtin.menunavigation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.letrongtin.menunavigation.R;
import com.example.letrongtin.menunavigation.adapter.IrrigationTreeAdapter;
import com.example.letrongtin.menunavigation.model.IrrigationTree;
import com.example.letrongtin.menunavigation.utility.CheckInternet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Le Trong Tin on 11/6/2017.
 */

public class IrrigationTreesFragment extends Fragment {

    ArrayList<IrrigationTree> irrigationTrees;
    IrrigationTreeAdapter irrigationTreeAdapter;

    RecyclerView recyclerView;
    TextView notification;
    ProgressBar progressBar;

    DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_object, container, false);

        irrigationTrees = new ArrayList<>();
        irrigationTreeAdapter = new IrrigationTreeAdapter(irrigationTrees, getContext());

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(irrigationTreeAdapter);
        notification = view.findViewById(R.id.notification);

        progressBar = view.findViewById(R.id.progressbar_loading);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (CheckInternet.haveNetworkConnection(getContext()))
        {
            notification.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            GetTrees();
        } else
        {
            notification.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    private void GetTrees(){
        mDatabase.child("IrrigationTrees").addChildEventListener(new ChildEventListener() {
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
                        IrrigationTree irrigationTree = dataSnapshot.getValue(IrrigationTree.class);
                        irrigationTrees.add(irrigationTree);
                        irrigationTreeAdapter.notifyDataSetChanged();

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
