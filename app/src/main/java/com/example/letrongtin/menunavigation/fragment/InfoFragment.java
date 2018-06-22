package com.example.letrongtin.menunavigation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.letrongtin.menunavigation.R;
import com.example.letrongtin.menunavigation.activity.InfoActivity;

/**
 * Created by Le Trong Tin on 11/30/2017.
 */

public class InfoFragment extends Fragment {

    Button btnMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        btnMap = view.findViewById(R.id.btnMap);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InfoActivity.class));
            }
        });
        return view;

    }
}
