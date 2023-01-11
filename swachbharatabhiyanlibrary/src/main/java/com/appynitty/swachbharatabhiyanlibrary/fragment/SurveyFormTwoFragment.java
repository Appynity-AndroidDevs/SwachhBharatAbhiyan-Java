package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
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

import java.util.Locale;

public class SurveyFormTwoFragment extends Fragment {
    private static final String TAG = "SurveyFormTwoFragment";
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
    String ug,g,pg;
    String service,business,professional;
    String married,unmarried;
    String own,rent,lease;
    private LinearLayout marriageBox;

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
        marriageBox = view.findViewById(R.id.li_anniversary_date_box);
        marriageBox.setVisibility(View.GONE);

        txtDayMarriage = view.findViewById(R.id.auto_day_anniversary);
        txtMonthMarriage = view.findViewById(R.id.auto_month_anniversary);
        txtYearMarriage = view.findViewById(R.id.auto_year_anniversary);

        cbUg = view.findViewById(R.id.cb_un_graduate);
        ug = getResStringLanguage(R.string.str_ug,"en");
        cbG = view.findViewById(R.id.cb_graduate);
        g = getResStringLanguage(R.string.str_graduate,"en");
        cbPg = view.findViewById(R.id.cb_pst_graduate);
        pg = getResStringLanguage(R.string.str_pg,"en");

        chkArrayQualification = new CheckBox[3];
        chkArrayQualification[0] = cbUg;
        chkArrayQualification[0].setOnClickListener(mListenerQualification);
        chkArrayQualification[1] = cbG;
        chkArrayQualification[1].setOnClickListener(mListenerQualification);
        chkArrayQualification[2] = cbPg;
        chkArrayQualification[2].setOnClickListener(mListenerQualification);

        cbServiceO = view.findViewById(R.id.cb_service_occupation);
        service = getResStringLanguage(R.string.str_service,"en");
        cbBusinessO = view.findViewById(R.id.cb_business_occupation);
        business = getResStringLanguage(R.string.str_business,"en");
        cbProfessionalO = view.findViewById(R.id.cb_professional_occupation);
        professional = getResStringLanguage(R.string.str_professional,"en");

        chkArrayOccupation = new CheckBox[3];
        chkArrayOccupation[0] = cbServiceO;
        chkArrayOccupation[0].setOnClickListener(mListenerOccupation);
        chkArrayOccupation[1] = cbBusinessO;
        chkArrayOccupation[1].setOnClickListener(mListenerOccupation);
        chkArrayOccupation[2] = cbProfessionalO;
        chkArrayOccupation[2].setOnClickListener(mListenerOccupation);

        cbMarried = view.findViewById(R.id.cb_married);
        married = getResStringLanguage(R.string.str_married,"en");
        cbUnMarried = view.findViewById(R.id.cb_unmarried);
        unmarried = getResStringLanguage(R.string.str_unmarried,"en");

        chkArrayMarital = new CheckBox[2];
        chkArrayMarital[0] = cbMarried;
        chkArrayMarital[0].setOnClickListener(mListenerMarital);
        chkArrayMarital[1] = cbUnMarried;
        chkArrayMarital[1].setOnClickListener(mListenerMarital);

        cbOwnHouse = view.findViewById(R.id.cb_own_house_li_status);
        own = getResStringLanguage(R.string.str_own_house,"en");
        cbRanted = view.findViewById(R.id.cb_ranted_li_status);
        rent = getResStringLanguage(R.string.str_ranted,"en");
        cbLease = view.findViewById(R.id.cb_lease_li_status);
        lease = getResStringLanguage(R.string.str_lease,"en");

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
                            txtDayMarriage.setText("0" + strDay);
                            Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_DAY, "0" + strDay);
                            Log.i("Social", "marriage of day: "+Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DAY,""));
                            marriageDate = "0" +strDay + "-" + "0" + strMonth + "-" + strYear;
                            Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_DATE,Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_YEAR,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DAY,""));
                            Log.i("Social", "date of marriage: "+Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DATE,""));

                        } else{
                            marriageDate = strDay + "-" + strMonth + "-" + strYear;
                            Log.d("Rahul", "date_of_marriage: " + marriageDate);
                            Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_DATE,Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_YEAR,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DAY,""));
                            Log.i("Social", "date of marriage: "+Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DATE,""));
                            txtDayMarriage.setText(strDay);
                            Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_DAY, strDay);
                            Log.i("Social", "marriage day: "+Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DAY,""));
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
                            Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_MONTH, "0" + strMonth);
                            Log.i("Social", "marriage of month: "+Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DAY,""));
                            marriageDate = "0" +strDay + "-" + "0" + strMonth + "-" + strYear;
                            Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_DATE,Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_YEAR,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DAY,""));
                            Log.i("Social", "date of marriage: "+Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DATE,""));

                        } else{
                            marriageDate = strDay + "-" + strMonth + "-" + strYear;
                            Log.d("Rahul", "date_of_marriage: " + marriageDate);
                            Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_DATE,Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_YEAR,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DAY,""));
                            Log.i("Social", "date of marriage: "+Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DATE,""));
                            txtMonthMarriage.setText(strMonth);
                            Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_MONTH, strMonth);
                            Log.i("Social", "marriage month: "+Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_MONTH,""));
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

                        Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_YEAR, strYear);
                        Log.i("Social", "marriage year: "+Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_YEAR,""));
                        Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_DATE,Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_YEAR,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DAY,""));
                        Log.i("Social", "date of marriage: "+Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DATE,""));
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
                    CheckBox checkBoxQualification = view.findViewById(current.getId());
                    String cbValueQualification = checkBoxQualification.getText().toString();
                    if (checkedId == R.id.cb_un_graduate){
                        cbValueQualification = ug;
                        Log.i("Social", "onClick: "+cbValueQualification);
                        Prefs.putString(AUtils.PREFS.SUR_QUALIFICATION,cbValueQualification);
                    }else if (checkedId == R.id.cb_graduate){
                        cbValueQualification = g;
                        Log.i("Social", "onClick: "+cbValueQualification);
                        Prefs.putString(AUtils.PREFS.SUR_QUALIFICATION,cbValueQualification);
                    }else if (checkedId == R.id.cb_pst_graduate){
                        cbValueQualification = pg;
                        Log.i("Social", "onClick: "+cbValueQualification);
                        Prefs.putString(AUtils.PREFS.SUR_QUALIFICATION,cbValueQualification);
                    }
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
                    CheckBox checkBoxOccupation = view.findViewById(current.getId());
                    String cbValueOccupation = checkBoxOccupation.getText().toString();
                    if (checkedId == R.id.cb_service_occupation){
                        cbValueOccupation = service;
                        Log.i("Social", "onClick: "+cbValueOccupation);
                        Prefs.putString(AUtils.PREFS.SUR_OCCUPATION,cbValueOccupation);
                    }else if (checkedId == R.id.cb_business_occupation){
                        cbValueOccupation = business;
                        Log.i("Social", "onClick: "+cbValueOccupation);
                        Prefs.putString(AUtils.PREFS.SUR_OCCUPATION,cbValueOccupation);
                    }else if (checkedId == R.id.cb_professional_occupation){
                        cbValueOccupation = professional;
                        Log.i("Social", "onClick: "+cbValueOccupation);
                        Prefs.putString(AUtils.PREFS.SUR_OCCUPATION,cbValueOccupation);
                    }
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
                    CheckBox checkBoxMarital = view.findViewById(current.getId());
                    String cbValueMarital = checkBoxMarital.getText().toString();
                    if (checkedId == R.id.cb_married){
                        cbValueMarital = married;
                        Log.i("Social", "onClick: "+cbValueMarital);
                        Prefs.putString(AUtils.PREFS.SUR_MARITAL_STATUS,cbValueMarital);
                        marriageBox.setVisibility(View.VISIBLE);
                    }else if (checkedId == R.id.cb_unmarried){
                        cbValueMarital = unmarried;
                        Log.i("Social", "onClick: "+cbValueMarital);
                        Prefs.putString(AUtils.PREFS.SUR_MARITAL_STATUS,cbValueMarital);
                        marriageBox.setVisibility(View.GONE);
                    }

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
                    CheckBox checkBoxLiving = view.findViewById(current.getId());
                    String cbValueLiving = checkBoxLiving.getText().toString();
                    if (checkedId == R.id.cb_own_house_li_status){
                        cbValueLiving = own;
                        Log.i("Social", "onClick: "+cbValueLiving);
                        Prefs.putString(AUtils.PREFS.SUR_LIVING_STATUS,cbValueLiving);
                    }else if (checkedId == R.id.cb_ranted_li_status){
                        cbValueLiving = rent;
                        Log.i("Social", "onClick: "+cbValueLiving);
                        Prefs.putString(AUtils.PREFS.SUR_LIVING_STATUS,cbValueLiving);
                    }else if (checkedId == R.id.cb_lease_li_status){
                        cbValueLiving = lease;
                        Log.i("Social", "onClick: "+cbValueLiving);
                        Prefs.putString(AUtils.PREFS.SUR_LIVING_STATUS,cbValueLiving);
                    }
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }

            }
        }
    };

    public String getResStringLanguage(int id, String lang){
        //Get default locale to back it
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        Locale savedLocale = conf.locale;
        //Retrieve resources from desired locale
        Configuration confAr = getResources().getConfiguration();
        confAr.locale = new Locale(lang);
        DisplayMetrics metrics = new DisplayMetrics();
        Resources resources = new Resources(getResources().getAssets(), metrics, confAr);
        //Get string which you want
        String string = resources.getString(id);
        //Restore default locale
        conf.locale = savedLocale;
        res.updateConfiguration(conf, null);
        //return the string that you want
        return string;
    }

    private boolean isValid(){
        return true;
    }

    public Boolean checkFromTwoText(){
        if (isValid()){
            return true;
        }
        return false;
    }
}