package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.appynitty.swachbharatabhiyanlibrary.R;

public class SurveyFormFiveFragment extends Fragment {
    private Context context;
    private View view;
    private CheckBox cbLInsurance,cbMInsurance,cbBothInsurance;
    private CheckBox[] chkArrayInsurance;
    private CheckBox cbGov,cbPrivate;
    private CheckBox[] chkArrayIType;
    private CheckBox cbYesAyushman,cbNoAyushman;
    private CheckBox[] chkArrayAyushman;
    private CheckBox cbYesBooster, cbNoBooster;
    private CheckBox[] chkArrayBooster;
    private CheckBox cbYesDivang, cbNoDivang;
    private CheckBox[] chkArrayDivang;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            view = inflater.inflate(R.layout.fragment_survey_form_five, container, false);
            init();
        }
        return view;
    }

    private void init() {
        context = getActivity();
//insurance
        cbLInsurance = view.findViewById(R.id.cb_life_insurance);
        cbMInsurance = view.findViewById(R.id.cb_medical_insurance);
        cbBothInsurance = view.findViewById(R.id.cb_both_insurance);

        chkArrayInsurance = new CheckBox[3];
        chkArrayInsurance[0] = cbLInsurance;
        chkArrayInsurance[0].setOnClickListener(mListenerInsurance);
        chkArrayInsurance[1] = cbMInsurance;
        chkArrayInsurance[1].setOnClickListener(mListenerInsurance);
        chkArrayInsurance[2] = cbBothInsurance;
        chkArrayInsurance[2].setOnClickListener(mListenerInsurance);

// Insurance Type
        cbGov = view.findViewById(R.id.cb_gov_insurance_type);
        cbPrivate = view.findViewById(R.id.cb_private_insurance_type);

        chkArrayIType = new CheckBox[2];
        chkArrayIType[0] = cbGov;
        chkArrayIType[0].setOnClickListener(mListenerIType);
        chkArrayIType[1] = cbPrivate;
        chkArrayIType[1].setOnClickListener(mListenerIType);
// Ayushman
        cbYesAyushman = view.findViewById(R.id.cb_yes_ayushman);
        cbNoAyushman = view.findViewById(R.id.cb_no_ayushman);

        chkArrayAyushman = new CheckBox[2];
        chkArrayAyushman[0] = cbYesAyushman;
        chkArrayAyushman[0].setOnClickListener(mListenerAyushman);
        chkArrayAyushman[1] = cbNoAyushman;
        chkArrayAyushman[1].setOnClickListener(mListenerAyushman);
//Booster Dose
        cbYesBooster = view.findViewById(R.id.cb_yes_booster);
        cbNoBooster = view.findViewById(R.id.cb_no_booster);

        chkArrayBooster = new CheckBox[2];
        chkArrayBooster[0] = cbYesBooster;
        chkArrayBooster[0].setOnClickListener(mListenerBooster);
        chkArrayBooster[1] = cbNoBooster;
        chkArrayBooster[1].setOnClickListener(mListenerBooster);
//Divang
        cbYesDivang = view.findViewById(R.id.cb_yes_divyang);
        cbNoDivang = view.findViewById(R.id.cb_no_divyang);

        chkArrayDivang = new CheckBox[2];
        chkArrayDivang[0] = cbYesDivang;
        chkArrayDivang[0].setOnClickListener(mListenerDivang);
        chkArrayDivang[1] = cbNoDivang;
        chkArrayDivang[1].setOnClickListener(mListenerDivang);




        setOnClick();
    }

    private void setOnClick() {

    }

    private View.OnClickListener mListenerInsurance = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            final String checkedValue = v.toString();
            for (int i = 0; i < chkArrayInsurance.length; i++) {
                final CheckBox current = chkArrayInsurance[i];
                if (current.getId() == checkedId) {
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }
            }
        }
    };

    private View.OnClickListener mListenerIType = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            final String checkedValue = v.toString();
            for (int i = 0; i < chkArrayIType.length; i++) {
                final CheckBox current = chkArrayIType[i];
                if (current.getId() == checkedId) {
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }
            }
        }
    };

    private View.OnClickListener mListenerAyushman = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            final String checkedValue = v.toString();
            for (int i = 0; i < chkArrayAyushman.length; i++) {
                final CheckBox current = chkArrayAyushman[i];
                if (current.getId() == checkedId) {
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }
            }
        }
    };

    private View.OnClickListener mListenerBooster = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            final String checkedValue = v.toString();
            for (int i = 0; i < chkArrayBooster.length; i++) {
                final CheckBox current = chkArrayBooster[i];
                if (current.getId() == checkedId) {
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }
            }
        }
    };

    private View.OnClickListener mListenerDivang = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            final String checkedValue = v.toString();
            for (int i = 0; i < chkArrayDivang.length; i++) {
                final CheckBox current = chkArrayDivang[i];
                if (current.getId() == checkedId) {
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }
            }
        }
    };
}