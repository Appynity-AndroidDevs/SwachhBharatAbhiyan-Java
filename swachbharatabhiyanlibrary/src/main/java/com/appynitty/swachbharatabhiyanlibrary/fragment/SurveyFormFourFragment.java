package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class SurveyFormFourFragment extends Fragment {

    private Context context;
    private View view;
    private ImageView imgMinusVote,imgPlusVote;
    private TextView txtNumVote;
    private String vote;
    private Integer o = 1;

    private CheckBox cbFbS,cbTwitSocial,cbInstagram,cbWhatsapp,cbLinkedIn,cbSnapChat,cbOtherS;

    private HashMap<String, String> ckList = new HashMap<>();
    ArrayList<String> list = new ArrayList<String>();

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

        cbTwitSocial = view.findViewById(R.id.cb_twitter_social);
        cbInstagram = view.findViewById(R.id.cb_instagram_social);
        cbFbS = view.findViewById(R.id.cb_fb_social);
        cbWhatsapp = view.findViewById(R.id.cb_whatsapp_social);
        cbLinkedIn = view.findViewById(R.id.cb_linkedin_social);
        cbSnapChat = view.findViewById(R.id.cb_snapchat_social);
        cbOtherS = view.findViewById(R.id.cb_other_social);

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

        cbFbS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ckList.put(cbFbS.getText().toString(),cbFbS.getText().toString());
                    Log.d("SocialMediaArrayAdd", "onCheckedChanged: "+ckList);
                }
                else{
                    try{
                        ckList.remove(cbFbS.getText().toString());
                        Log.d("SocialMediaArrayRemove", "onCheckedChanged: "+ckList);
                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        cbInstagram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ckList.put(cbInstagram.getText().toString(),cbInstagram.getText().toString());
                    Log.d("SocialMediaArrayAdd", "onCheckedChanged: "+ckList);
                }
                else{
                    try{
                        ckList.remove(cbInstagram.getText().toString());
                        Log.d("SocialMediaArrayRemove", "onCheckedChanged: "+ckList);
                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        cbWhatsapp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ckList.put(cbWhatsapp.getText().toString(),cbWhatsapp.getText().toString());
                    Log.d("SocialMediaArrayAdd", "onCheckedChanged: "+ckList);
                }
                else{
                    try{
                        ckList.remove(cbWhatsapp.getText().toString());
                        Log.d("SocialMediaArrayRemove", "onCheckedChanged: "+ckList);
                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        cbTwitSocial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ckList.put(cbTwitSocial.getText().toString(),cbTwitSocial.getText().toString());
                    Log.d("SocialMediaArrayAdd", "onCheckedChanged: "+ckList);
                }
                else{
                    try{
                        ckList.remove(cbWhatsapp.getText().toString());
                        Log.d("SocialMediaArrayRemove", "onCheckedChanged: "+ckList);
                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

   /* CheckBox[] chkArray = new CheckBox[8];
    chkArray[0] = (CheckBox) findViewById(R.id.cb1R1);
    chkArray[0].setOnClickListener(mListener);
    chkArray[1] = (CheckBox) findViewById(R.id.cb2R1); // what id do you have?
    chkArray[1].setOnClickListener(mListener);
// so on for the rest of the 8 CheckBoxes

    private OnClickListener mListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            for (int i = 0; i < chkArray.length; i++) {
                final CheckBox current = chkArray[i];
                if (current.getId() == checkedId) {
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }
            }
        }
    };*/

    /*ArrayList<String> unselectList = new ArrayList<String>();
    StringBuilder sb = new StringBuilder();
    String wo = TextUtils.join(",", Collections.singleton(sb.append(ulbId)));
                    unselectList.add(wo.trim());
                    Log.e(TAG, "unselect ulb list is :- " +unselectList);

                    for(int i=0;i<list.size();i++){
        if(list.get(i).equals(wo.trim()))
        {
            list.remove(i);
            break;
        }
    }
                    Log.i(TAG, "unselect ulb list is :- " +list);
    String rahulListUnSelectSave = Arrays.toString(list.toArray()).replace("[","").replace("]","");
                    Log.i("check", "stringCheck: "+rahulListUnSelectSave.trim());*/

    /*StringBuilder sb = new StringBuilder();
    String ww = TextUtils.join(",",Collections.singleton(sb.append(ulbId)));
                    list.add(ww.trim());
                    Log.e(TAG, String.valueOf(list));*/
}