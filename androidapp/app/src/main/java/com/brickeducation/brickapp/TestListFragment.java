package com.brickeducation.brickapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin on 2015-11-21.
 */
public class TestListFragment extends Fragment {

    List<Test> testList;
    BrickAppDbHelper helper;

    public static TestListFragment newInstance() {

        Bundle args = new Bundle();

        TestListFragment fragment = new TestListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_test_list, container, false);
        Log.i("TestListFragment", "Fragment sarted");

        helper = new BrickAppDbHelper(getActivity());

        List<Test> tempList = helper.getAllTests();
        testList = new ArrayList<>();

        for (int i = 0; i<tempList.size();i++){
            List<String> content = tempList.get(i).getTestContent();


            if (BrickAppDbHelper.progressToDouble(new Question((content.get(content.size()-1))).getTestProgress())==1.0){
                testList.add(tempList.get(i));
            }


        }

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.test_recycler);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        rv.setAdapter(new TestAdapter());


        return v;
    }

    private class TestAdapter extends RecyclerView.Adapter<TestHolder>{

        @Override
        public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            //Set the view of a ViewHolder
            View v = inflater.inflate(R.layout.test_card, parent, false);
            return new TestHolder(v);
        }


        //Called to initialize a single bar on a graph
        @Override
        public void onBindViewHolder(TestHolder holder, final int position) {
            Test currentTest = testList.get(position);
            holder.testName.setText(currentTest.getTestName());
            String testAmount = currentTest.getTestContent().size()+" questions";
            holder.testAmount.setText(testAmount);
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    ft.add(android.R.id.content, QuizTimeFragment.newInstance(testList.get(position)), null);
                    ft.addToBackStack("2");
                    ft.commit();

                }
            });


        }

        //Sets the amount of ViewHolders / bars to display
        //We want to display however many bars are in the list
        @Override
        public int getItemCount() {
            return testList.size();
        }
    }


    //ViewHolder that provides access to our subtitles and views
    private class TestHolder extends RecyclerView.ViewHolder{

        TextView testName;
        TextView testAmount;
        CardView card;
        public TestHolder(View itemView) {
            super(itemView);
            testName = (TextView) itemView.findViewById(R.id.test_name);
            testAmount = (TextView) itemView.findViewById(R.id.question_amount);
            card = (CardView) itemView.findViewById(R.id.test_card);
        }
    }
}
