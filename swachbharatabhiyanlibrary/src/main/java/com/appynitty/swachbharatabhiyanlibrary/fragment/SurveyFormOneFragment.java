package com.appynitty.swachbharatabhiyanlibrary.fragment;

import static java.util.Calendar.*;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.DaysPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.MonthPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.MonthYearPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.Calendar;


public class SurveyFormOneFragment extends Fragment {
    private Context context;
    private View view;
    private LinearLayout liMonthBox, liDayBox, liYearBox;
    private AutoCompleteTextView atxtMonth, atxtDay, atxtYear;
    private MonthYearPickerDialog monthYearPickerDialog;
    private MonthPickerDialog monthPickerDialog;
    private DaysPickerDialog daysPickerDialog;
    private TextView txtAge;
    private String birthDayDate;
    private String birthDay, birthMonth, birthYear;
   // private int bYear =0,bMonth = 0,bDay = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_survey_form_one, container, false);
            init();
        }
        return view;
    }

    private void init() {
        context = getActivity();
        liMonthBox = view.findViewById(R.id.li_month_box);
        liDayBox = view.findViewById(R.id.li_day_box);
        liYearBox = view.findViewById(R.id.li_year_box);
        LayoutInflater inflater = LayoutInflater.from(context);
        atxtMonth = view.findViewById(R.id.auto_month);
        atxtDay = view.findViewById(R.id.auto_day);
        atxtYear = view.findViewById(R.id.auto_year);
        txtAge = view.findViewById(R.id.txt_age);

        setOnClick();

    }

    private void setOnClick() {
        liMonthBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPickerDialog = new MonthPickerDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        birthMonth = String.valueOf(i1);
                        //

                        if (birthMonth.length() == 1) {
                            birthDayDate = birthDay + "-" + "0" + birthMonth + "-" + birthYear;
                            atxtMonth.setText("0" + birthMonth);
                        } else{
                            birthDayDate = birthDay + "-" + birthMonth + "-" + birthYear;
                            Log.d("Rahul", "date_of_birth: " + birthDayDate);
                            atxtMonth.setText(birthMonth);
                        }
                    }
                }, birthMonth);
                monthPickerDialog.setCancelable(false);
                monthPickerDialog.show(getChildFragmentManager().beginTransaction(), monthPickerDialog.getTag());
                atxtMonth.setText(birthMonth);
            }
        });

        liDayBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysPickerDialog = new DaysPickerDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        birthDay = String.valueOf(i2);

                        if (birthDay.length() == 1) {
                            birthDayDate = "0" +birthDay + "-" + "0" + birthMonth + "-" + birthYear;
                            atxtDay.setText("0" + birthDay);
                        } else{
                            birthDayDate = birthDay + "-" + birthMonth + "-" + birthYear;
                            Log.d("Rahul", "date_of_birth: " + birthDayDate);
                            atxtDay.setText(birthDay);
                        }
                    }
                }, birthDay);
                daysPickerDialog.setCancelable(false);
                daysPickerDialog.show(getChildFragmentManager().beginTransaction(), daysPickerDialog.getTag());

                atxtDay.setText(birthDay);
            }
        });

        liYearBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthYearPickerDialog = new MonthYearPickerDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int i1, int i2) {
                        birthYear = String.valueOf(year);
                        atxtYear.setText(birthYear);
                        birthDayDate = "0" +birthDay + "-" + "0" + birthMonth + "-" + birthYear;
                        Log.d("Rahul", "date_of_birth: " + birthDayDate);
                        myAge(birthYear);
                    }
                }, birthYear);
                monthYearPickerDialog.setCancelable(false);
                monthYearPickerDialog.show(getChildFragmentManager().beginTransaction(), monthYearPickerDialog.getTag());

                atxtYear.setText(birthYear);

            }
        });

        birthDayDate = "0" +birthDay + "-" + "0" + birthMonth + "-" + birthYear;
        Log.d("Rahul", "date_of_birth: " + birthDayDate);
    }


    private boolean isValidPhone(String pass) {
        if (pass != null && pass.length() == 10) {
            AUtils.warning(context, "please enter valid mobile number");
            return false;
        }
        return true;
    }

    private void myAge(String birthYear){
        Calendar today = getInstance();
        int currentYear = today.get(YEAR);
        int age = currentYear - Integer.parseInt(birthYear);
        Log.d("TAG", "My Age is: "+age);
        txtAge.setText("Age: " +age);
    }

}