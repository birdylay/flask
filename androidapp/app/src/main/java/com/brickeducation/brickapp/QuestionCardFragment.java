package com.brickeducation.brickapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Colin on 2015-11-22.
 */
public class QuestionCardFragment extends Fragment {

    public static QuestionCardFragment newInstance() {

        Bundle args = new Bundle();

        QuestionCardFragment fragment = new QuestionCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_question_card, container, false);

        Button send = (Button) v.findViewById(R.id.question_send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Question sent to teacher!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}