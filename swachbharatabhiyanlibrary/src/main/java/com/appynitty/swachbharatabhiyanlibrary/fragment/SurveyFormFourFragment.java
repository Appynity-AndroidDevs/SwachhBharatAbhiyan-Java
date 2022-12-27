package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;

public class SurveyFormFourFragment extends Fragment {

    private Context context;
    private View view;
    private ImageView imgMinusVote,imgPlusVote;
    private TextView txtNumVote;
    private String vote;
    private Integer o = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            view = inflater.inflate(R.layout.fragment_survey_form_four, container, false);
            init();
        }
        return view;
    }

    private void init(){
        context = getActivity();

        //Vote
        imgMinusVote = view.findViewById(R.id.img_minus_vote);
        txtNumVote = view.findViewById(R.id.txt_num_vote);
        imgPlusVote = view.findViewById(R.id.img_plus_vote);

        o = Integer.parseInt(txtNumVote.getText().toString());
        setOnClick();
    }

    private void setOnClick() {
        //Vote
        imgMinusVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Decreasing value...");

                if (o > 0) {
                    o = o - 1;
                    vote = String.valueOf(o);
                    txtNumVote.setText(vote);
                } else {
                    Log.d("src", "Value can't be less than 0");
                }
            }
        });

        imgPlusVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("src", "Increasing value...");
                o = o + 1;
                vote = String.valueOf(o);
                txtNumVote.setText(vote);
            }
        });
    }
}