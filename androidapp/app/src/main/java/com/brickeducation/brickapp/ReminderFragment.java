package com.brickeducation.brickapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Colin on 2015-11-22.
 */
public class ReminderFragment extends Fragment {

    public static ReminderFragment newInstance() {

        Bundle args = new Bundle();

        ReminderFragment fragment = new ReminderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_reminder, container, false);

        return v;
    }
}
