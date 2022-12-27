package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.appynitty.swachbharatabhiyanlibrary.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.zip.Inflater;


public class SurveyFormOneFragment extends Fragment {
        private Context context;
        private View view;
        private LinearLayout liMonthBox;
        private AutoCompleteTextView atxtMonth;
        private  ArrayAdapter<String> adapterMonth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            view = inflater.inflate(R.layout.fragment_survey_form_one, container, false);
            init();
        }
        return view;
    }

    private void init() {
        context = getActivity();
        liMonthBox = view.findViewById(R.id.li_month_box);
        LayoutInflater inflater = LayoutInflater.from(context);
        atxtMonth = view.findViewById(R.id.auto_month);

        String[] month = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
       adapterMonth = new ArrayAdapter<>(context, R.layout.drop_down_item, month);
        setOnClick();
    }

    private void setOnClick() {
        liMonthBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}