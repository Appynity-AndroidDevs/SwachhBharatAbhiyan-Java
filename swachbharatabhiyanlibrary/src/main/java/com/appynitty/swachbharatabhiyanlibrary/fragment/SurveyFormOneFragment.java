package com.appynitty.swachbharatabhiyanlibrary.fragment;

import static java.util.Calendar.*;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.DaysPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.MonthPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.MonthYearPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Calendar;
import java.util.Locale;


public class SurveyFormOneFragment extends Fragment {

    private static String TAG = "SurveyFormOneFragment";
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

    private EditText edtName, edtMobile;
    private CheckBox cbMale, cbFemale,cbTransG;
    private boolean isCheckGender = false;
    private CheckBox[] chkArrayGender;
    private CheckBox[] chkArrayBloodGroup;

    private CheckBox cbBloodPosA,cbBloodPosB,cbBloodPosAB,cbBloodPosO;
    private CheckBox cbBloodNegA,cbBloodNegB,cbBloodNegAB,cbBloodNegO;


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
        edtName = view.findViewById(R.id.edt_your_name);
        String name = edtName.getText().toString().trim();
        Prefs.putString(AUtils.PREFS.SUR_NAME,name);
        Log.i("Social", "name is: "+Prefs.getString(AUtils.PREFS.SUR_NAME,""));
        edtMobile = view.findViewById(R.id.edt_phone_call);
        String mobile = edtMobile.getText().toString().trim();
        Prefs.putString(AUtils.PREFS.SUR_MOBILE, mobile);
        Log.i("Social", "mobile is: "+Prefs.getString(AUtils.PREFS.SUR_MOBILE,""));
        cbMale = view.findViewById(R.id.cb_male);
        cbFemale = view.findViewById(R.id.cb_female);
        cbTransG = view.findViewById(R.id.cb_transgender);

        chkArrayGender = new CheckBox[3];
        chkArrayGender[0] = cbMale;
        chkArrayGender[0].setOnClickListener(mListenerGender);

        chkArrayGender[1] = cbFemale;
        chkArrayGender[1].setOnClickListener(mListenerGender);

        chkArrayGender[2] = cbTransG;
        chkArrayGender[2].setOnClickListener(mListenerGender);

        cbBloodPosA = view.findViewById(R.id.cb_positive_a);
        cbBloodPosO = view.findViewById(R.id.cb_positive_o);
        cbBloodPosB = view.findViewById(R.id.cb_positive_b);
        cbBloodPosAB = view.findViewById(R.id.cb_positive_ab);

        cbBloodNegA = view.findViewById(R.id.cb_negative_a);
        cbBloodNegO = view.findViewById(R.id.cb_negative_o);
        cbBloodNegB = view.findViewById(R.id.cb_negative_b);
        cbBloodNegAB = view.findViewById(R.id.cb_negative_ab);

        chkArrayBloodGroup = new CheckBox[8];
        chkArrayBloodGroup[0] = cbBloodPosA;
        chkArrayBloodGroup[0].setOnClickListener(mListenerBloodGroup);

        chkArrayBloodGroup[1] = cbBloodPosO;
        chkArrayBloodGroup[1].setOnClickListener(mListenerBloodGroup);

        chkArrayBloodGroup[2] = cbBloodPosB;
        chkArrayBloodGroup[2].setOnClickListener(mListenerBloodGroup);

        chkArrayBloodGroup[3] = cbBloodPosAB;
        chkArrayBloodGroup[3].setOnClickListener(mListenerBloodGroup);

        chkArrayBloodGroup[4] = cbBloodNegA;
        chkArrayBloodGroup[4].setOnClickListener(mListenerBloodGroup);

        chkArrayBloodGroup[5] = cbBloodNegO;
        chkArrayBloodGroup[5].setOnClickListener(mListenerBloodGroup);

        chkArrayBloodGroup[6] = cbBloodNegB;
        chkArrayBloodGroup[6].setOnClickListener(mListenerBloodGroup);

        chkArrayBloodGroup[7] = cbBloodNegAB;
        chkArrayBloodGroup[7].setOnClickListener(mListenerBloodGroup);


        setOnClick();
        setData();
    }

    private void setData() {
        Log.d(TAG, "name : "+Prefs.getString(AUtils.PREFS.SUR_NAME,""));
        Log.d(TAG, "mobile : "+Prefs.getString(AUtils.PREFS.SUR_MOBILE,""));


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
                            Prefs.putString(AUtils.PREFS.SUR_BIRTHDAY_DATE,Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,""));
                            Log.i("Social", "date of birth: "+Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,""));
                            atxtMonth.setText("0" + birthMonth);
                            Prefs.putString(AUtils.PREFS.SUR_BIRTH_MONTH, "0" + birthMonth);
                            Log.i("Social", "birth month: "+Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,""));
                        } else{
                            birthDayDate = birthDay + "-" + birthMonth + "-" + birthYear;
                            Log.d("Rahul", "date_of_birth: " + birthDayDate);
                            Prefs.putString(AUtils.PREFS.SUR_BIRTHDAY_DATE,Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,""));
                            Log.i("Social", "date of birth: "+Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,""));
                            atxtMonth.setText(birthMonth);
                            Prefs.putString(AUtils.PREFS.SUR_BIRTH_MONTH, birthMonth);
                            Log.i("Social", "birth month: "+Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,""));
                        }
                    }
                }, birthMonth);
                monthPickerDialog.setCancelable(false);
                monthPickerDialog.show(getChildFragmentManager().beginTransaction(), monthPickerDialog.getTag());
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
                            Prefs.putString(AUtils.PREFS.SUR_BIRTHDAY_DATE,Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,""));
                            Log.i("Social", "date of birth: "+Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,""));
                            atxtDay.setText("0" + birthDay);
                            Prefs.putString(AUtils.PREFS.SUR_BIRTH_DAY, "0" + birthDay);
                            Log.i("Social", "birth day: "+Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,""));
                        } else{
                            birthDayDate = birthDay + "-" + birthMonth + "-" + birthYear;
                            Log.d("Rahul", "date_of_birth: " + birthDayDate);
                            Prefs.putString(AUtils.PREFS.SUR_BIRTHDAY_DATE,Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,""));
                            Log.i("Social", "date of birth: "+Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,""));
                            atxtDay.setText(birthDay);
                            Prefs.putString(AUtils.PREFS.SUR_BIRTH_DAY, birthDay);
                            Log.i("Social", "birth day: "+Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,""));
                        }
                    }
                }, birthDay);
                daysPickerDialog.setCancelable(false);
                daysPickerDialog.show(getChildFragmentManager().beginTransaction(), daysPickerDialog.getTag());
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
                        Prefs.putString(AUtils.PREFS.SUR_BIRTH_YEAR, birthYear);
                        Log.i("Social", "birth year: "+Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,""));
                        Prefs.putString(AUtils.PREFS.SUR_BIRTHDAY_DATE,Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,""));
                        Log.i("Social", "date of birth: "+Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,""));
                    }
                }, birthYear);
                monthYearPickerDialog.setCancelable(false);
                monthYearPickerDialog.show(getChildFragmentManager().beginTransaction(), monthYearPickerDialog.getTag());

            }
        });

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
        Prefs.putString(AUtils.PREFS.SUR_AGE, String.valueOf(age));
        Log.d("TAG", "My Age is: "+age);
        Log.i("Social", "My Age is: "+Prefs.getString(AUtils.PREFS.SUR_AGE,""));
        //txtAge.setText(age);
        txtAge.setText(Prefs.getString(AUtils.PREFS.SUR_AGE,""));
    }

    private View.OnClickListener mListenerGender = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            for (int i = 0; i < chkArrayGender.length; i++) {
                final CheckBox current = chkArrayGender[i];
                if (current.getId() == checkedId) {
                    CheckBox checkBoxGender = view.findViewById(current.getId());
                    String cbValueGender = checkBoxGender.getText().toString();
                    Log.i("Social", "onClick: "+cbValueGender);
                    Prefs.putString(AUtils.PREFS.SUR_GENDER,cbValueGender);
                    current.setChecked(true);
                } else {
                    current.setChecked(false);
                }

            }
        }
    };

    private View.OnClickListener mListenerBloodGroup = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            for (int i = 0; i < chkArrayBloodGroup.length; i++) {
                final CheckBox current = chkArrayBloodGroup[i];
                if (current.getId() == checkedId) {
                    CheckBox checkBoxBloodGroup = view.findViewById(current.getId());
                    String cbValueBloodGroup = checkBoxBloodGroup.getText().toString();
                    Log.i("Social", "onClick: "+cbValueBloodGroup);
                    Prefs.putString(AUtils.PREFS.SUR_BLOOD_GROUP,cbValueBloodGroup);
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
        if (edtName.getText().toString().trim().isEmpty()){
            AUtils.warning(context,"Please enter your nome");
            return false;
        }
        return true;
    }

}