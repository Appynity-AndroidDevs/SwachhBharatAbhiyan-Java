package com.appynitty.swachbharatabhiyanlibrary.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.appynitty.swachbharatabhiyanlibrary.utils.AUtils.stringToObjectS;
import static com.pixplicity.easyprefs.library.Prefs.getPreferences;
import static java.util.Calendar.*;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.DaysPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.MonthPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.MonthYearPickerDialog;
import com.appynitty.swachbharatabhiyanlibrary.pojos.GetApiResponseModel;
import com.appynitty.swachbharatabhiyanlibrary.pojos.GetSurveyResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
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
    private TextView txtAge, txtBtnCalAge;
    private String birthDayDate;
    private String birthDay, birthMonth, birthYear;

    private EditText edtName,edtMName,edtLName, edtMobile;
    private CheckBox cbMale, cbFemale,cbTransG;
    private boolean isCheckGender = false;
    private CheckBox[] chkArrayGender;
    public static ArrayList<String> genderArray = new ArrayList<String>();
    private CheckBox[] chkArrayBloodGroup;

    private CheckBox cbBloodPosA,cbBloodPosB,cbBloodPosAB,cbBloodPosO;
    private CheckBox cbBloodNegA,cbBloodNegB,cbBloodNegAB,cbBloodNegO;

    String male,female,other;
    String posA,posB,posAB,posO,negA,negB,negAB,negO;
    String fullName;

    String getApiName,getApiMobile,getApiAge,getApiGender,getApiBloodGroup;
    private GetApiResponseModel apiResponseModel;

    public SurveyFormOneFragment(){

    }
    public SurveyFormOneFragment(GetApiResponseModel apiResponseModel){
        this.apiResponseModel = apiResponseModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_survey_form_one, container, false);

            init();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (apiResponseModel != null){

            Log.d(TAG, "onViewCreated: "+apiResponseModel);
            edtName.setText(apiResponseModel.getFirstName());
            edtMName.setText(apiResponseModel.getMiddleName());
            edtLName.setText(apiResponseModel.getLastName());
            edtMobile.setText(apiResponseModel.getMobileNumber());
            atxtDay.setText(apiResponseModel.getBirtDay());
            atxtMonth.setText(apiResponseModel.getBirthMonth());
            atxtYear.setText(apiResponseModel.getBirthYear());
            txtAge.setText(apiResponseModel.getAge());
            if (apiResponseModel.getGender().equals(male)){
                cbMale.setChecked(true);
            }else if (apiResponseModel.getGender().equals(female)){
                cbFemale.setChecked(true);
            }else if (apiResponseModel.getGender().equals(other)){
                cbTransG.setChecked(true);
            }
            if (apiResponseModel.getBloodGroup().equals(posA)){
                cbBloodPosA.setChecked(true);
            }else if (apiResponseModel.getBloodGroup().equals(posO)){
                cbBloodPosO.setChecked(true);
            }else if (apiResponseModel.getBloodGroup().equals(posB)){
                cbBloodPosB.setChecked(true);
            }else if (apiResponseModel.getBloodGroup().equals(posAB)){
                cbBloodPosAB.setChecked(true);
            }else if (apiResponseModel.getBloodGroup().equals(negA)){
                cbBloodNegA.setChecked(true);
            }else if (apiResponseModel.getBloodGroup().equals(negO)){
                cbBloodNegO.setChecked(true);
            }else if (apiResponseModel.getBloodGroup().equals(negB)){
                cbBloodNegB.setChecked(true);
            }else if (apiResponseModel.getBloodGroup().equals(negAB)){
                cbBloodNegAB.setChecked(true);
            }
        }else {
            Log.d(TAG, "onViewCreated: ");
            init();
        }
    }

    private void init() {
        context = getActivity();
        liMonthBox = view.findViewById(R.id.li_month_box);
        liDayBox = view.findViewById(R.id.li_day_box);
        liYearBox = view.findViewById(R.id.li_year_box);
        LayoutInflater inflater = LayoutInflater.from(context);
        edtName = view.findViewById(R.id.edt_your_name);
        edtMName = view.findViewById(R.id.edt_middle_name);
        edtLName = view.findViewById(R.id.edt_last_name);
        edtMobile = view.findViewById(R.id.edt_phone_call);
        txtBtnCalAge = view.findViewById(R.id.txt_cal_age);

        atxtMonth = view.findViewById(R.id.auto_month);
        atxtDay = view.findViewById(R.id.auto_day);
        atxtYear = view.findViewById(R.id.auto_year);
        txtAge = view.findViewById(R.id.txt_age);


        cbMale = view.findViewById(R.id.cb_male);
        male = getResStringLanguage(R.string.str_male,"en");
        cbFemale = view.findViewById(R.id.cb_female);
        female = getResStringLanguage(R.string.str_female,"en");
        cbTransG = view.findViewById(R.id.cb_transgender);
        other = getResStringLanguage(R.string.str_transgender,"en");

        chkArrayGender = new CheckBox[3];
        chkArrayGender[0] = cbMale;
        chkArrayGender[0].setOnClickListener(mListenerGender);

        chkArrayGender[1] = cbFemale;
        chkArrayGender[1].setOnClickListener(mListenerGender);

        chkArrayGender[2] = cbTransG;
        chkArrayGender[2].setOnClickListener(mListenerGender);

        cbBloodPosA = view.findViewById(R.id.cb_positive_a);
        posA = getResStringLanguage(R.string.str_pos_a,"en");
        cbBloodPosO = view.findViewById(R.id.cb_positive_o);
        posO = getResStringLanguage(R.string.str_pos_o,"en");
        cbBloodPosB = view.findViewById(R.id.cb_positive_b);
        posB = getResStringLanguage(R.string.str_pos_b,"en");
        cbBloodPosAB = view.findViewById(R.id.cb_positive_ab);
        posAB = getResStringLanguage(R.string.str_pos_ab,"en");

        cbBloodNegA = view.findViewById(R.id.cb_negative_a);
        negA = getResStringLanguage(R.string.str_neg_a,"en");
        cbBloodNegO = view.findViewById(R.id.cb_negative_o);
        negO = getResStringLanguage(R.string.str_neg_o,"en");
        cbBloodNegB = view.findViewById(R.id.cb_negative_b);
        negB = getResStringLanguage(R.string.str_neg_b,"en");
        cbBloodNegAB = view.findViewById(R.id.cb_negative_ab);
        negAB = getResStringLanguage(R.string.str_neg_ab,"en");

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

        /*SharedPreferences  mPrefs = getPreferences();
        String value= mPrefs.getString("MyObject", "");
        apiResponseModel = stringToObjectS(value);
        Log.d(TAG, "init: "+apiResponseModel);*/

        setOnClick();
        setFillData();
        getApiResponse();
    }

    private void getApiResponse() {
       // edtName.setText(apiResponseModel.getName());


        if (getArguments() != null){

            getApiName = getArguments().getString("fragmentOneName");
            Log.i("social", "getApiResponse: "+getApiName);
            edtName.setText(getApiName);
            getApiMobile = getArguments().getString("fragmentOneMobile");
            Log.i("social", "getApiResponse: "+getApiMobile);
            edtMobile.setText(getApiMobile);
        }
    }

    private void setFillData() {
        String surFirstName = Prefs.getString(AUtils.PREFS.SUR_FIRST_NAME,"");
        edtName.setText(surFirstName/*.trim().replace(" ","")*/);
        String surMiddleName = Prefs.getString(AUtils.PREFS.SUR_MIDDLE_NAME,"");
        edtMName.setText(surMiddleName/*.trim().replace(" ","")*/);
        String surLastName = Prefs.getString(AUtils.PREFS.SUR_LAST_NAME,"");
        edtLName.setText(surLastName);
        String surFullName = surFirstName+" "+surMiddleName+" "+surLastName;
        Log.i(TAG, "surFullName: "+surFullName);
        Prefs.putString(AUtils.PREFS.SUR_FULL_NAME,surFullName);
        String surMobile = Prefs.getString(AUtils.PREFS.SUR_MOBILE,"");
        edtMobile.setText(surMobile);
        String bDay = Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,"");
        atxtDay.setText(bDay);
        String bMonth = Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"");
        atxtMonth.setText(bMonth);
        String bYear = Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,"");
        atxtYear.setText(bYear);

        String age = Prefs.getString(AUtils.PREFS.SUR_AGE,"");
        txtAge.setText(age);
        String mGender = Prefs.getString(AUtils.PREFS.SUR_GENDER,"");
        if (mGender.equals(male)){
            cbMale.setChecked(true);
        }else if (mGender.equals(female)){
            cbFemale.setChecked(true);
        }else if (mGender.equals(other)){
            cbTransG.setChecked(true);
        }
        String bloodGroup = Prefs.getString(AUtils.PREFS.SUR_BLOOD_GROUP,"");
        if (bloodGroup.equals(posA)){
            cbBloodPosA.setChecked(true);
        }else if (bloodGroup.equals(posO)){
            cbBloodPosO.setChecked(true);
        }else if (bloodGroup.equals(posB)){
            cbBloodPosB.setChecked(true);
        }else if (bloodGroup.equals(posAB)){
            cbBloodPosAB.setChecked(true);
        }else if (bloodGroup.equals(negA)){
            cbBloodNegA.setChecked(true);
        }else if (bloodGroup.equals(negO)){
            cbBloodNegO.setChecked(true);
        }else if (bloodGroup.equals(negB)){
            cbBloodNegB.setChecked(true);
        }else if (bloodGroup.equals(negAB)){
            cbBloodNegAB.setChecked(true);
        }


    }


    private void setOnClick() {
        txtBtnCalAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAge();
            }
        });
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = editable.toString();
                Prefs.putString(AUtils.PREFS.SUR_FIRST_NAME,name);
                Log.i("Social", "first name is: "+Prefs.getString(AUtils.PREFS.SUR_FIRST_NAME,""));
            }
        });

        edtMName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = editable.toString();
                Prefs.putString(AUtils.PREFS.SUR_MIDDLE_NAME,name);
                Log.i("Social", "middle name is: "+Prefs.getString(AUtils.PREFS.SUR_MIDDLE_NAME,""));
            }
        });
        edtLName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = editable.toString();
                Prefs.putString(AUtils.PREFS.SUR_LAST_NAME,name);
                Log.i("Social", "last name is: "+Prefs.getString(AUtils.PREFS.SUR_LAST_NAME,""));
            }
        });

        edtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String mobile = editable.toString();
                Prefs.putString(AUtils.PREFS.SUR_MOBILE,mobile);
                Log.i("Social", "mobile is: "+Prefs.getString(AUtils.PREFS.SUR_MOBILE,""));
            }
        });

        liMonthBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthPickerDialog = new MonthPickerDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        birthMonth = String.valueOf(i1);
                        //

                        if (birthMonth.length() == 1) {
                            birthDayDate = birthYear + "-" + "0" + birthMonth + "-" +"0" +birthDay ;
                            Prefs.putString(AUtils.PREFS.SUR_BIRTHDAY_DATE, Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,""));
                            Log.i("Social", "date of birth: "+Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,""));
                            atxtMonth.setText("0" + birthMonth);
                            Prefs.putString(AUtils.PREFS.SUR_BIRTH_MONTH, "0" + birthMonth);
                            Log.i("Social", "birth month: "+Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,""));
                        } else{
                            birthDayDate = birthDay + "-" + birthMonth + "-" + birthYear;
                            Log.d("Rahul", "date_of_birth: " + birthDayDate);
                            Prefs.putString(AUtils.PREFS.SUR_BIRTHDAY_DATE,Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,""));
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
                            Prefs.putString(AUtils.PREFS.SUR_BIRTHDAY_DATE,Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,""));
                            Log.i("Social", "date of birth: "+Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,""));
                            atxtDay.setText("0" + birthDay);
                            Prefs.putString(AUtils.PREFS.SUR_BIRTH_DAY, "0" + birthDay);
                            Log.i("Social", "birth day: "+Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,""));
                        } else{
                            birthDayDate = birthDay + "-" + birthMonth + "-" + birthYear;
                            Log.d("Rahul", "date_of_birth: " + birthDayDate);
                            Prefs.putString(AUtils.PREFS.SUR_BIRTHDAY_DATE,Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,""));
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
                        Prefs.putString(AUtils.PREFS.SUR_BIRTH_YEAR, birthYear);
                        myAge(birthYear);
                        Log.i("Social", "birth year: "+Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,""));
                        Prefs.putString(AUtils.PREFS.SUR_BIRTHDAY_DATE,Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"")+ "-" +Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,""));
                        Log.i("Social", "date of birth: "+Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,""));
                    }
                }, birthYear);
                monthYearPickerDialog.setCancelable(false);
                monthYearPickerDialog.show(getChildFragmentManager().beginTransaction(), monthYearPickerDialog.getTag());

            }
        });

    }



    private void myAge(String birthYear){
        Calendar today = getInstance();
        int currentYear = today.get(YEAR);
        int age = currentYear - Integer.parseInt(birthYear);
        Log.e(TAG, "only year wise age: "+age);

    }

    private void rightAge(){
        int bitDay = Integer.parseInt(Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,"0"));
        int bitMonth = Integer.parseInt(Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"0"));
        int bitYear = Integer.parseInt(Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,"0"));
        if (bitDay!=0 && bitMonth !=0 && bitYear !=0){
            int niceAge = AUtils.getPerfectAge(bitYear,bitMonth,bitDay);
            Log.e(TAG, "perfect Age Cal: "+niceAge);
            Prefs.putString(AUtils.PREFS.SUR_AGE, String.valueOf(niceAge));
            txtAge.setText(niceAge+"");
        }else {
            Toast.makeText(context, "Please select day, month, year and then calculate", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener mListenerGender = new View.OnClickListener() {

        @SuppressLint("ResourceType")
        @Override
        public void onClick(View v) {
            final int checkedId = v.getId();
            for (int i = 0; i < chkArrayGender.length; i++) {
                final CheckBox current = chkArrayGender[i];
                if (current.getId() == checkedId) {
                    CheckBox checkBoxGender = view.findViewById(current.getId());
                    String cbValueGender = checkBoxGender.getText().toString();
                    if (checkedId == R.id.cb_male){
                        cbValueGender = male;
                        Log.i("Social", "onClick: "+cbValueGender);
                        Prefs.putString(AUtils.PREFS.SUR_GENDER,cbValueGender);
                    }else if (checkedId == R.id.cb_female){
                        cbValueGender = female;
                        Log.i("Social", "onClick: "+cbValueGender);
                        Prefs.putString(AUtils.PREFS.SUR_GENDER,cbValueGender);

                    }else if (checkedId == R.id.cb_transgender){
                        cbValueGender = other;
                        Log.i("Social", "onClick: "+cbValueGender);
                        Prefs.putString(AUtils.PREFS.SUR_GENDER,cbValueGender);
                    }
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
                    if (checkedId == R.id.cb_positive_a){
                        cbValueBloodGroup = posA;
                        Log.i("Social", "onClick: "+cbValueBloodGroup);
                        Prefs.putString(AUtils.PREFS.SUR_BLOOD_GROUP,cbValueBloodGroup);
                    }else if (checkedId == R.id.cb_positive_o){
                        cbValueBloodGroup = posO;
                        Log.i("Social", "onClick: "+cbValueBloodGroup);
                        Prefs.putString(AUtils.PREFS.SUR_BLOOD_GROUP,cbValueBloodGroup);
                    }else if (checkedId == R.id.cb_positive_b){
                        cbValueBloodGroup = posB;
                        Log.i("Social", "onClick: "+cbValueBloodGroup);
                        Prefs.putString(AUtils.PREFS.SUR_BLOOD_GROUP,cbValueBloodGroup);
                    }else if (checkedId == R.id.cb_positive_ab){
                        cbValueBloodGroup = posAB;
                        Log.i("Social", "onClick: "+cbValueBloodGroup);
                        Prefs.putString(AUtils.PREFS.SUR_BLOOD_GROUP,cbValueBloodGroup);
                    }else if (checkedId == R.id.cb_negative_a){
                        cbValueBloodGroup = negA;
                        Log.i("Social", "onClick: "+cbValueBloodGroup);
                        Prefs.putString(AUtils.PREFS.SUR_BLOOD_GROUP,cbValueBloodGroup);
                    }else if (checkedId == R.id.cb_negative_o){
                        cbValueBloodGroup = negO;
                        Log.i("Social", "onClick: "+cbValueBloodGroup);
                        Prefs.putString(AUtils.PREFS.SUR_BLOOD_GROUP,cbValueBloodGroup);
                    }else if (checkedId == R.id.cb_negative_b){
                        cbValueBloodGroup = negB;
                        Log.i("Social", "onClick: "+cbValueBloodGroup);
                        Prefs.putString(AUtils.PREFS.SUR_BLOOD_GROUP,cbValueBloodGroup);
                    }else if (checkedId == R.id.cb_negative_ab){
                        cbValueBloodGroup = negAB;
                        Log.i("Social", "onClick: "+cbValueBloodGroup);
                        Prefs.putString(AUtils.PREFS.SUR_BLOOD_GROUP,cbValueBloodGroup);
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
        if (edtName.getText().toString().trim().isEmpty()){
            AUtils.warning(context,"Please enter your nome");
            return false;
        }else if (edtMobile.getText().toString().trim().isEmpty()){
            AUtils.warning(context,"Please enter your nome");
            return false;
        }else if (edtMobile.getText().toString().length() <10){
            AUtils.warning(context,"Please enter ");
            return false;
        }else if (cbFemale.isChecked() || cbMale.isChecked() || cbTransG.isChecked()){
            AUtils.warning(context,"Please checked any one checkbox ");
            return false;
        }
        return true;
    }

    public Boolean checkFromOneText(){
        if (isValid()){
            return true;
        }
        return false;
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

}