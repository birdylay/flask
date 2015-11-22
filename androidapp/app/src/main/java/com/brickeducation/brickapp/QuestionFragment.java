package com.brickeducation.brickapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Colin on 2015-11-21.
 */
public class QuestionFragment extends Fragment {

    BrickAppDbHelper helper;

    private static final String AKEY = "aKey";
    private static final String BKEY = "bKey";
    private static final String CKEY = "cKey";
    private static final String DKEY = "dKey";
    private static final String QUESTION_KEY = "qKey";
    private static final String POSKEY = "pKey";
    private static final String TOTKEY = "tKey";
    private static final String TESTKEY = "testKey";

    public static QuestionFragment newInstance(Question question, int position, int total, String testName) {

        Bundle args = new Bundle();
        args.putString(AKEY, question.getA() );
        args.putString(BKEY, question.getB() );
        args.putString(CKEY, question.getC() );
        args.putString(DKEY, question.getD() );
        args.putString(QUESTION_KEY, question.getQuestion() );
        args.putInt(POSKEY, position);
        args.putInt(TOTKEY, total);
        args.putString(TESTKEY, testName);
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_question, container, false);

        String rawMessage = getArguments().getString(QuestionListenerService.MESSAGE_KEY);

        TextView questionText = (TextView) v.findViewById(R.id.question);
        TextView aAns = (TextView) v.findViewById(R.id.a_ans);
        TextView bAns = (TextView) v.findViewById(R.id.b_ans);
        TextView cAns = (TextView) v.findViewById(R.id.c_ans);
        TextView dAns = (TextView) v.findViewById(R.id.d_ans);
        TextView progress = (TextView) v.findViewById(R.id.progress);

        helper = new BrickAppDbHelper(getActivity());

        Bundle args = getArguments();

        aAns.setText(args.getString(AKEY));
        bAns.setText(args.getString(BKEY));
        cAns.setText(args.getString(CKEY));
        dAns.setText(args.getString(DKEY));
        questionText.setText(args.getString(QUESTION_KEY));
        progress.setText("Question "+args.getInt(POSKEY)+"/"+args.getInt(TOTKEY));

        CardView aCard = (CardView) v.findViewById(R.id.a_button);
        CardView bCard = (CardView) v.findViewById(R.id.b_button);
        CardView cCard = (CardView) v.findViewById(R.id.c_button);
        CardView dCard = (CardView) v.findViewById(R.id.d_button);

        final CustomViewPager mPager = (CustomViewPager) getActivity().findViewById(R.id.quiz_pager);

        aCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movePage(mPager, 1);
            }
        });
        bCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movePage(mPager, 2);
            }
        });
        cCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movePage(mPager, 3);
            }
        });
        dCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movePage(mPager, 4);
            }
        });



        return v;
    }

    private void movePage(CustomViewPager pager, int letter){
        Log.i("questionFrag", "count is "+pager.getAdapter().getCount()+" and current is "+pager.getCurrentItem());

        if (pager.getCurrentItem()==pager.getAdapter().getCount()-1){
            QuizTimeFragment.addAnswer(letter);
            Toast.makeText(getActivity(),"Test Answers Sent!", Toast.LENGTH_SHORT).show();
            helper.deleteTest(helper.getTestFromName(getArguments().getString(TESTKEY)));
            getActivity().onBackPressed();
            getActivity().onBackPressed();
            ((BrickActivity) getActivity()).resetCount();
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("+2897881489", null, QuizTimeFragment.getAnswer().toString(), null, null);
            QuizTimeFragment.clearAnswers();
        }else{
            QuizTimeFragment.addAnswer(letter);
            pager.setCurrentItem(pager.getCurrentItem() + 1);
        }
    }



}
