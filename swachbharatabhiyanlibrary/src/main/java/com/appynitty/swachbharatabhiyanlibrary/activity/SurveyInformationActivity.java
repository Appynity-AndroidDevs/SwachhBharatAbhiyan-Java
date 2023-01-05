package com.appynitty.swachbharatabhiyanlibrary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.SurPagerAdapter;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFiveFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFourFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormOneFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormThreeFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormTwoFragment;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SurveyDetailsRepo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SurveyInformationActivity extends AppCompatActivity {

    private static final String TAG = "SurveyInformationActivity";
    private final static int NUM_PAGES = 5;
    private Context context;
    private FrameLayout frameLayout;
    private DotsIndicator dotsIndicator;

    private Button btnBack,btnNext, btnDone;
    private ImageView imgBack;

    private ViewPager2 viewPager;
    private SurPagerAdapter pagerAdapter;
    private View view;

    private SurveyDetailsRepo surveyDetailsRepo;
    private List<SurveyDetailsRequestPojo> requestPojo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_information);
        init();
    }
    private void init(){
        context = this;
        frameLayout = findViewById(R.id.container_frame_layout);
        viewPager = findViewById(R.id.view_pager);
        dotsIndicator = findViewById(R.id.dots_indicator);
        pagerAdapter = new SurPagerAdapter(getSupportFragmentManager(),getLifecycle());
        imgBack = findViewById(R.id.img_survey_back);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);
        btnDone = findViewById(R.id.btn_done);

        btnDone.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);

        viewPager.setAdapter(pagerAdapter);
        dotsIndicator.attachTo(viewPager);

        setOnClick();

    }

    private void setData() {
        String surName = Prefs.getString(AUtils.PREFS.SUR_NAME,"");
        Log.d(TAG, "surName: "+surName);
        String surMobile = Prefs.getString(AUtils.PREFS.SUR_MOBILE,"");
        Log.d(TAG, "surMobile: "+surMobile);
        String surBirthdayDate = Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,"");
        Log.d(TAG, "surBirthdayDate: "+surBirthdayDate);
        String surAge = Prefs.getString(AUtils.PREFS.SUR_AGE,"");
        Log.d(TAG, "surAge: "+surAge);
        String surGender = Prefs.getString(AUtils.PREFS.SUR_GENDER,"");
        Log.d(TAG, "surGender: "+surGender);
        String surBloodGroup = Prefs.getString(AUtils.PREFS.SUR_BLOOD_GROUP,"");
        Log.d(TAG, "surBloodGroup: "+surBloodGroup);
        String surQualification = Prefs.getString(AUtils.PREFS.SUR_QUALIFICATION,"");
        Log.d(TAG, "surQualification: "+surQualification);
        String surOccupation = Prefs.getString(AUtils.PREFS.SUR_OCCUPATION,"");
        Log.d(TAG, "surOccupation: "+surOccupation);
        String surMaritalStatus = Prefs.getString(AUtils.PREFS.SUR_MARITAL_STATUS,"");
        Log.d(TAG, "surMaritalStatus: "+surMaritalStatus);
        String surLivingStatus = Prefs.getString(AUtils.PREFS.SUR_LIVING_STATUS,"");
        Log.d(TAG, "surLivingStatus: "+surLivingStatus);
        String surMarriageDate = Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DATE,"");
        Log.d(TAG, "surMarriageDate: "+surMarriageDate);
        String surTotalMember = Prefs.getString(AUtils.PREFS.SUR_TOTAL_MEMBER,"");
        Log.d(TAG, "surTotalMember: "+surTotalMember);
        String surAdult = Prefs.getString(AUtils.PREFS.SUR_TOTAL_ADULT,"");
        Log.d(TAG, "surAdult: "+surAdult);
        String surChildren = Prefs.getString(AUtils.PREFS.SUR_TOTAL_CHILDREN,"");
        Log.d(TAG, "surChildren: "+surChildren);
        String surSeniorCitizen = Prefs.getString(AUtils.PREFS.SUR_TOTAL_CITIZEN,"");
        Log.d(TAG, "surSeniorCitizen: "+surSeniorCitizen);
        String surBusinessWillingStart = Prefs.getString(AUtils.PREFS.SUR_WILLING_START,"");
        Log.d(TAG, "surBusinessWillingStart: "+surBusinessWillingStart);
        String surResourceAvailable = Prefs.getString(AUtils.PREFS.SUR_RESOURCE_AVAILABLE,"");
        Log.d(TAG, "surResourceAvailable: "+surResourceAvailable);
        String surJobOtherCity = Prefs.getString(AUtils.PREFS.SUR_MEMBER_JOB_OTHER_CITY,"");
        Log.d(TAG, "surJobOtherCity: "+surJobOtherCity);
        String surTotalVehicle = Prefs.getString(AUtils.PREFS.SUR_NUM_OF_VEHICLE,"");
        Log.d(TAG, "surTotalVehicle: "+surTotalVehicle);
        String surTwoWheeler = Prefs.getString(AUtils.PREFS.SUR_TWO_WHEELER_QTY,"");
        Log.d(TAG, "surTwoWheeler: "+surTwoWheeler);
        String surFourWheeler = Prefs.getString(AUtils.PREFS.SUR_FOUR_WHEELER_QTY,"");
        Log.d(TAG, "surFourWheeler: "+surFourWheeler);
        String surTotalVote = Prefs.getString(AUtils.PREFS.SUR_NUM_OF_PEOPLE_VOTE,"");
        Log.d(TAG, "surTotalVote: "+surTotalVote);
        String surSocialMedia = Prefs.getString(AUtils.PREFS.SUR_SOCIAL_MEDIA,"");
        Log.d(TAG, "surSocialMedia: "+surSocialMedia);
        String surShopping = Prefs.getString(AUtils.PREFS.SUR_ONLINE_SHOPPING,"");
        Log.d(TAG, "surShopping: "+surShopping);
        String surOnlinePayment = Prefs.getString(AUtils.PREFS.SUR_ONLINE_PAY_APP,"");
        Log.d(TAG, "surOnlinePayment: "+surOnlinePayment);
        String surInsurance = Prefs.getString(AUtils.PREFS.SUR_INSURANCE,"");
        Log.d(TAG, "surInsurance: "+surInsurance);
        String surUnderInsurance = Prefs.getString(AUtils.PREFS.SUR_UNDER_INSURANCE,"");
        Log.d(TAG, "surUnderInsurance: "+surUnderInsurance);
        String surAyushmanBene = Prefs.getString(AUtils.PREFS.SUR_AYUSHMAN_BENE,"");
        Log.d(TAG, "surAyushmanBene: "+surAyushmanBene);
        String surBoosterDose = Prefs.getString(AUtils.PREFS.SUR_BOOSTER_SHOT,"");
        Log.d(TAG, "surBoosterDose: "+surBoosterDose);
        String surDivyangMember = Prefs.getString(AUtils.PREFS.SUR_MEMBER_OF_DIVYANG,"");
        Log.d(TAG, "surDivyangMember: "+surDivyangMember);
    }


    private void setOnClick(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() < Objects.requireNonNull(viewPager.getAdapter()).getItemCount()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                    setData();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() != 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                startActivity(new Intent(context, SurveyCompletActivity.class));
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("Rahul", "PageChange: "+position);

                if(position==0) {
                    btnBack.setVisibility(View.GONE);
                }else  {
                    btnBack.setVisibility(View.VISIBLE);
                }
                if(position < Objects.requireNonNull(viewPager.getAdapter()).getItemCount() -1 ) {
                    btnNext.setVisibility(View.VISIBLE);
                    btnDone.setVisibility(View.GONE);

                }else  {
                    btnNext.setVisibility(View.GONE);
                    btnDone.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }
}