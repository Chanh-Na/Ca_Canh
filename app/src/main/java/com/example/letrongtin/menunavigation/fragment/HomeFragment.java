package com.example.letrongtin.menunavigation.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.letrongtin.menunavigation.R;
import com.example.letrongtin.menunavigation.model.Advertisement;
import com.example.letrongtin.menunavigation.utility.CheckInternet;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by Le Trong Tin on 11/6/2017.
 */

public class HomeFragment extends Fragment {

    LinearLayout layoutHome;

    TextView notification;

    DatabaseReference mDatabase;

    ViewFlipper viewFlipper;

    private AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        layoutHome = view.findViewById(R.id.layout_home);

        notification = view.findViewById(R.id.notification);

        viewFlipper = view.findViewById(R.id.viewFlipper);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        MobileAds.initialize(getContext(), "ca-app-pub-2498644322314522~8457004985");
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (CheckInternet.haveNetworkConnection(getContext()))
        {
            notification.setVisibility(View.INVISIBLE);
            layoutHome.setVisibility(View.VISIBLE);
            ActionViewFipper();
        } else
        {
            notification.setVisibility(View.VISIBLE);
            layoutHome.setVisibility(View.GONE);
        }

        return view;

    }

    private void ActionViewFipper() {
        mDatabase.child("Advertisements").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot!= null){

                }

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                int count = 0;
                for (DataSnapshot child : children) {

                    count++;
                    if (count >= dataSnapshot.getChildrenCount()) {
                        //stop progress bar here

                        Advertisement advertisement = dataSnapshot.getValue(Advertisement.class);
                        ImageView img = new ImageView(getContext());
                        Picasso.with(getContext()).load(advertisement.getUrlImage())
                                .fit()
                                .into(img);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                        viewFlipper.addView(img);

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

        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        Animation rightIn = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_in);
        Animation rightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_out);

        viewFlipper.setInAnimation(rightIn);
        viewFlipper.setOutAnimation(rightOut);

    }

}
