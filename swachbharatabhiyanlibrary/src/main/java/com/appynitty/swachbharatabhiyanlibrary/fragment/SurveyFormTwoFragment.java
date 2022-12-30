package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

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
}