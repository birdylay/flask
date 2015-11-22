package com.brickeducation.brickapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.brickeducation.brickapp.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin on 2015-11-21.
 */
public class QuizTimeFragment extends Fragment {

    public static void addAnswer(int ans){
        answers.add(ans);
    }

    public static void clearAnswers(){
        answers.clear();
    }

    public static List<Integer> getAnswer(){
        return answers;
    }

    public static QuizTimeFragment newInstance(Test test) {

        Bundle args = new Bundle();
        args.putString("TEST_NAME", test.getTestName());
        QuizTimeFragment fragment = new QuizTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private CustomViewPager mPager;
    private Test mTest;
    private BrickAppDbHelper helper;
    List<Question> questions;
    private static List<Integer> answers;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
        View v = inflater.inflate(R.layout.quiz_time, container, false);
        helper = new BrickAppDbHelper(getActivity());
        mPager = (CustomViewPager) v.findViewById(R.id.quiz_pager);
        mTest = helper.getTestFromName(getArguments().getString("TEST_NAME"));
        questions = new ArrayList<>();
        answers = new ArrayList<>();

        for (int i = 0; i<mTest.getTestContent().size(); i++){
            questions.add(new Question(mTest.getTestContent().get(i)));
        }

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getChildFragmentManager());

        mPager.setAdapter(pagerAdapter);
        mPager.setOffscreenPageLimit(2);
        mPager.setPagingEnabled(false);

        return v;
    }

    private class MainPagerAdapter extends FragmentStatePagerAdapter {

        public MainPagerAdapter(FragmentManager fm){
            super(fm);
        }


        //A different Fragment will be loaded depending on page number
        @Override
        public Fragment getItem(int position) {
            return QuestionFragment.newInstance(questions.get(position), position+1, questions.size(), getArguments().getString("TEST_NAME"));
        }

        //The number of pages to display
        @Override
        public int getCount() {
            return questions.size();
        }
    }

}
