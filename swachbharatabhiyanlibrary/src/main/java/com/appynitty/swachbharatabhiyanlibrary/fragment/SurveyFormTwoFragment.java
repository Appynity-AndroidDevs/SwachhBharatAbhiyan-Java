package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.DaysPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.MonthPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.MonthYearPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

public class SurveyFormTwoFragment extends Fragment {
    private Context context;
    private View view;
    private LinearLayout liDayMarriage,liMonthMarriage,liYearMarriage;
    private TextView txtDayMarriage,txtMonthMarriage, txtYearMarriage;
    private MonthPickerDialog monthPickerDialog;
    private MonthYearPickerDialog yearPickerDialog;
    private DaysPickerDialog daysPickerDialog;
    private String strDay,strMonth,strYear;
    private String marriageDate;

    private CheckBox cbUg,cbG,cbPg;
    private CheckBox[] chkArrayQualification;
    private CheckBox cbServiceO,cbBusinessO,cbProfessionalO;
    private CheckBox[] chkArrayOccupation;
    private CheckBox cbMarried,cbUnMarried;
    private CheckBox[] chkArrayMarital;
    private CheckBox cbOwnHouse,cbRanted,cbLease;
    private CheckBox[] chkArrayLiving;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            view = inflater.inflate(R.layout.fragment_survey_form_two, container, false);
            init();
        }
        return view;
    }

    private void init() {
        context = getActivity();
        liDayMarriage = view.findViewById(R.id.li_day_marriage_box);
        liMonthMarriage = view.findViewById(R.id.li_month_marriage_box);
        liYearMarriage = view.findViewById(R.id.li_year_marriage_box);
        
        txtDayMarriage = view.findViewById(R.id.auto_day_anniversary);
        txtMonthMarriage = view.findViewById(R.id.auto_month_anniversary);
        txtYearMarriage = view.findViewById(R.id.auto_year_anniversary);

        cbUg = view.findViewById(R.id.cb_un_graduate);
        cbG = view.findViewById(R.id.cb_graduate);
        cbPg = view.findViewById(R.id.cb_pst_graduate);

        chkArrayQualification = new CheckBox[3];
        chkArrayQualification[0] = cbUg;
        chkArrayQualification[0].setOnClickListener(mListenerQualification);
        chkArrayQualification[1] = cbG;
        chkArrayQualification[1].setOnClickListener(mListenerQualification);
        chkArrayQualification[2] = cbPg;
        chkArrayQualification[2].setOnClickListener(mListenerQualification);

        cbServiceO = view.findViewById(R.id.cb_service_occupation);
        cbBusinessO = view.findViewById(R.id.cb_business_occupation);
        cbProfessionalO = view.findViewById(R.id.cb_professional_occupation);

        chkArrayOccupation = new CheckBox[3];
        chkArrayOccupation[0] = cbServiceO;
        chkArrayOccupation[0].setOnClickListener(mListenerOccupation);
        chkArrayOccupation[1] = cbBusinessO;
        chkArrayOccupation[1].setOnClickListener(mListenerOccupation);
        chkArrayOccupation[2] = cbProfessionalO;
        chkArrayOccupation[2].setOnClickListener(mListenerOccupation);

        cbMarried = view.findViewById(R.id.cb_married);
        cbUnMarried = view.findViewById(R.id.cb_unmarried);

        chkArrayMarital = new CheckBox[2];
        chkArrayMarital[0] = cbMarried;
        chkArrayMarital[0].setOnClickListener(mListenerMarital);
        chkArrayMarital[1] = cbUnMarried;
        chkArrayMarital[1].setOnClickListener(mListenerMarital);

        cbOwnHouse = view.findViewById(R.id.cb_own_house_li_status);
        cbRanted = view.findViewById(R.id.cb_ranted_li_status);
        cbLease = view.findViewById(R.id.cb_lease_li_status);

        chkArrayLiving = new CheckBox[3];
        chkArrayLiving[0] = cbOwnHouse;
        chkArrayLiving[0].setOnClickListener(mListenerLiving);
        chkArrayLiving[1] = cbRanted;
        chkArrayLiving[1].setOnClickListener(mListenerLiving);
        chkArrayLiving[2] = cbLease;
        chkArrayLiving[2].setOnClickListener(mListenerLiving);

        setOnClick();
    }

    private void setOnClick() {
        liDayMarriage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysPickerDialog = new DaysPickerDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        strDay = String.valueOf(i2);

                        if (strDay.length() == 1) {
                            marriageDate = "0" +strDay + "-" + "0" + strMonth + "-" + strYear;
                            txtDayMarriage.setText("0" + strDay);
                        } else{
                            marriageDate = "0" +strDay + "-" + "0" + strMonth + "-" + strYear;
                            Log.d("Rahul", "date_of_marriage: " + marriageDate);
                            txtDayMarriage.setText(strDay);
                        }
                    }
                }, strDay);
                daysPickerDialog.setCancelable(false);
                daysPickerDialog.show(getChildFragmentManager().beginTransaction(), daysPickerDialog.getTag());

            }
        });

        liMonthMarriage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPickerDialog = new MonthPickerDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        strMonth = String.valueOf(i1);
                        //

                        if (strMonth.length() == 1) {
                            marriageDate = "0" +strDay + "-" + "0" + strMonth + "-" + strYear;
                            txtMonthMarriage.setText("0" + strMonth);
                        } else{
                            marriageDate = "0" +strDay + "-" + "0" + strMonth + "-" + strYear;
                            Log.d("Rahul", "date_of_marriage: " + marriageDate);
                            txtMonthMarriage.setText(strMonth);
                        }
                    }
                }, strMonth);
                monthPickerDialog.setCancelable(false);
                monthPickerDialog.show(getChildFragmentManager().beginTransaction(), monthPickerDialog.getTag());
            }
        });

        liYearMarriage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yearPickerDialog = new MonthYearPickerDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int i1, int i2) {
                        strYear = String.valueOf(year);
                        txtYearMarriage.setText(strYear);
                        marriageDate = "0" +strDay + "-" + "0" + strMonth + "-" + strYear;
                        txtYearMarriage.setText(strYear);
                        Log.d("Rahul", "date_of_marriage: " + marriageDate);
                        Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_DATE, marriageDate);
                    }
                }, strYear);
                yearPickerDialog.setCancelable(false);
                yearPickerDialog.show(getChildFragmentManager().beginTransaction(), yearPickerDialog.getTag());
            }
        });
    }

    private View.OnClickListener mListenerQualification = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            final String checkedValue = v.toString();
            for (int i = 0; i < chkArrayQualification.length; i++) {
                final CheckBox current = chkArrayQualification[i];
                if (current.getId() == checkedId) {
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }

            }
        }
    };

    private View.OnClickListener mListenerOccupation = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            for (int i = 0; i < chkArrayOccupation.length; i++) {
                final CheckBox current = chkArrayOccupation[i];
                if (current.getId() == checkedId) {
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }

            }
        }
    };


    private View.OnClickListener mListenerMarital = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            for (int i = 0; i < chkArrayMarital.length; i++) {
                final CheckBox current = chkArrayMarital[i];
                if (current.getId() == checkedId) {
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }

            }
        }
    };

    private View.OnClickListener mListenerLiving = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            for (int i = 0; i < chkArrayLiving.length; i++) {
                final CheckBox current = chkArrayLiving[i];
                if (current.getId() == checkedId) {
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }

            }
        }
    };
}