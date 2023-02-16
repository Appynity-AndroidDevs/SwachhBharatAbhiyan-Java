package com.appynitty.swachbharatabhiyanlibrary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;
/***
 * Created BY Rahul Rokade
 * Date : 3 Jan 2023
 * */
public class SurveyCompletActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_complet);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                removeSurveyData();
                startActivity(new Intent(SurveyCompletActivity.this,EmpDashboardActivity.class));
                finishAffinity();
            }
        }, 300);
    }

    private void removeSurveyData(){
        Prefs.remove(AUtils.GET_API_REFERENCE_ID);
        Prefs.remove(AUtils.PREFS.SUR_REFERENCE_ID);
        Prefs.remove(AUtils.PREFS.SUR_FULL_NAME);
        Prefs.remove(AUtils.PREFS.SUR_FIRST_NAME);
        Prefs.remove(AUtils.PREFS.SUR_MIDDLE_NAME);
        Prefs.remove(AUtils.PREFS.SUR_LAST_NAME);
        Prefs.remove(AUtils.PREFS.SUR_MOBILE);
        Prefs.remove(AUtils.PREFS.SUR_GENDER);
        Prefs.remove(AUtils.PREFS.SUR_BLOOD_GROUP);
        Prefs.remove(AUtils.PREFS.SUR_QUALIFICATION);
        Prefs.remove(AUtils.PREFS.SUR_OCCUPATION);
        Prefs.remove(AUtils.PREFS.SUR_MARITAL_STATUS);
        Prefs.remove(AUtils.PREFS.SUR_AGE);
        Prefs.remove(AUtils.PREFS.SUR_BIRTHDAY_DATE);
        Prefs.remove(AUtils.PREFS.SUR_BIRTH_YEAR);
        Prefs.remove(AUtils.PREFS.SUR_BIRTH_MONTH);
        Prefs.remove(AUtils.PREFS.SUR_BIRTH_DAY);
        Prefs.remove(AUtils.PREFS.SUR_MARRIAGE_DATE);
        Prefs.remove(AUtils.PREFS.SUR_MARRIAGE_DAY);
        Prefs.remove(AUtils.PREFS.SUR_MARRIAGE_MONTH);
        Prefs.remove(AUtils.PREFS.SUR_MARRIAGE_YEAR);
        Prefs.remove(AUtils.PREFS.SUR_LIVING_STATUS);
        Prefs.remove(AUtils.PREFS.SUR_TOTAL_ADULT);
        Prefs.remove(AUtils.PREFS.SUR_TOTAL_CHILDREN);
        Prefs.remove(AUtils.PREFS.SUR_TOTAL_CITIZEN);
        Prefs.remove(AUtils.PREFS.SUR_TOTAL_MEMBER);
        Prefs.remove(AUtils.PREFS.SUR_WILLING_START);
        Prefs.remove(AUtils.PREFS.SUR_RESOURCE_AVAILABLE);
        Prefs.remove(AUtils.PREFS.SUR_MEMBER_JOB_OTHER_CITY);
        Prefs.remove(AUtils.PREFS.SUR_NUM_OF_VEHICLE);
        Prefs.remove(AUtils.PREFS.SUR_TWO_WHEELER_QTY);
        Prefs.remove(AUtils.PREFS.SUR_FOUR_WHEELER_QTY);
        Prefs.remove(AUtils.PREFS.SUR_NUM_OF_PEOPLE_VOTE);
        Prefs.remove(AUtils.PREFS.SUR_SOCIAL_MEDIA);
        Prefs.remove(AUtils.PREFS.SUR_SOCIAL_MEDIA_REMOVE_DUPLICATE);
        Prefs.remove(AUtils.PREFS.SUR_ONLINE_SHOPPING);
        Prefs.remove(AUtils.PREFS.SUR_PAYMENT_MODE);
        Prefs.remove(AUtils.PREFS.SUR_ONLINE_PAY_APP);
        Prefs.remove(AUtils.PREFS.SUR_INSURANCE);
        Prefs.remove(AUtils.PREFS.SUR_UNDER_INSURANCE);
        Prefs.remove(AUtils.PREFS.SUR_AYUSHMAN_BENE);
        Prefs.remove(AUtils.PREFS.SUR_BOOSTER_SHOT);
        Prefs.remove(AUtils.PREFS.SUR_MEMBER_OF_DIVYANG);
        Prefs.remove(AUtils.PREFS.SUR_CREATED_USER_ID);
        Prefs.remove(AUtils.PREFS.SUR_UPDATED_USER_ID);
    }
}