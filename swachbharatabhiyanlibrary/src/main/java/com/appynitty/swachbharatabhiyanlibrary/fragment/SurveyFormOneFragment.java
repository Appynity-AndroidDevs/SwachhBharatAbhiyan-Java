package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.zip.Inflater;


public class SurveyFormOneFragment extends Fragment {
        private Context context;
        private View view;
        private LinearLayout liMonthBox, liDayBox,liYearBox;
        private AutoCompleteTextView atxtMonth,atxtDay,atxtYear;
        private  ArrayAdapter<String> adapterMonth;
        private MonthYearPickerDialog monthYearPickerDialog;
        private MonthPickerDialog monthPickerDialog;
        private DaysPickerDialog daysPickerDialog;
        private TextView txtAge;
        private String birthDayDate;
        private String birthDay,birthMonth,birthYear;


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
        liDayBox = view.findViewById(R.id.li_day_box);
        liYearBox = view.findViewById(R.id.li_year_box);
        LayoutInflater inflater = LayoutInflater.from(context);
        atxtMonth = view.findViewById(R.id.auto_month);
        atxtDay = view.findViewById(R.id.auto_day);
        atxtYear = view.findViewById(R.id.auto_year);
        txtAge = view.findViewById(R.id.txt_age);

        String[] month = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
       adapterMonth = new ArrayAdapter<>(context, R.layout.drop_down_item, month);
        setOnClick();
    }

    private void setOnClick() {
        liMonthBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPickerDialog = new MonthPickerDialog();
                monthPickerDialog.setCancelable(false);
                monthPickerDialog.show(getChildFragmentManager().beginTransaction(), monthPickerDialog.getTag());

                atxtMonth.setText(birthMonth);
            }
        });

        liDayBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysPickerDialog = new DaysPickerDialog();
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
                        atxtYear.setText(year);
                    }
                });
                monthYearPickerDialog.setCancelable(false);
                monthYearPickerDialog.show(getChildFragmentManager().beginTransaction(), monthYearPickerDialog.getTag());

                atxtYear.setText(birthYear);
            }
        });


    }


    private boolean isValidPhone(String pass) {
        if (pass != null && pass.length() == 10){
            AUtils.warning(context,"please enter valid mobile number");
            return false;
        }
        return true;
    }

    private int getAge(String dobString){

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        return age;
    }

}