package com.appynitty.swachbharatabhiyanlibrary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.SurPagerAdapter;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFiveFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFourFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormOneFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormThreeFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormTwoFragment;
import com.appynitty.swachbharatabhiyanlibrary.pojos.GetSurveyResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SurveyDetailsRepo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.viewmodels.SurveyDetailsVM;
import com.pixplicity.easyprefs.library.Prefs;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.List;
import java.util.Objects;

public class SurveyInformationActivity extends AppCompatActivity {

    private static final String TAG = "SurveyInformationActivity";
    private final static int NUM_PAGES = 5;
    private Context context;
    private FrameLayout frameLayout;
    private DotsIndicator dotsIndicator;

    private Button btnBack,btnNext, btnDone,btnUpdate;
    private ImageView imgBack;

    private ViewPager2 viewPager;
    private SurPagerAdapter pagerAdapter;
    private View view;

    private SurveyDetailsRepo surveyDetailsRepo;
    private SurveyDetailsRequestPojo requestPojo = new SurveyDetailsRequestPojo();
    private SurveyDetailsVM surveyDetailsVM;

    private SurveyFormOneFragment surveyFormOneFragment;
    private SurveyFormTwoFragment surveyFormTwoFragment;
    private SurveyFormThreeFragment surveyFormThreeFragment;
    private SurveyFormFourFragment surveyFormFourFragment;
    private SurveyFormFiveFragment surveyFormFiveFragment;
    private List<GetSurveyResponsePojo> getSurveyDetailsPojo;
    String headerReferenceId, apiReferenceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_information);
        init();
    }
    private void init(){
        context = this;
        headerReferenceId = Prefs.getString(AUtils.PREFS.SUR_REFERENCE_ID,"");
        frameLayout = findViewById(R.id.container_frame_layout);
        viewPager = findViewById(R.id.view_pager);
        dotsIndicator = findViewById(R.id.dots_indicator);
        surveyFormFiveFragment = new SurveyFormFiveFragment();
        surveyFormFourFragment = new SurveyFormFourFragment();
        surveyFormThreeFragment = new SurveyFormThreeFragment();
        surveyFormTwoFragment = new SurveyFormTwoFragment();
        surveyFormOneFragment = new SurveyFormOneFragment();

        pagerAdapter = new SurPagerAdapter(getSupportFragmentManager(),getLifecycle());
        imgBack = findViewById(R.id.img_survey_back);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);
        btnDone = findViewById(R.id.btn_done);
        btnUpdate = findViewById(R.id.btn_update);

        btnDone.setVisibility(View.GONE);
        btnUpdate.setVisibility(View.GONE);
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
                    /*for (int i=0; i<viewPager.getCurrentItem()+1; i++){
                        if (i == 0){
                            if ( surveyFormOneFragment.checkFromOneText()) {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                                return;
                            }
                            setData();
                        }else if (i == 1){
                            if ( surveyFormTwoFragment.checkFromTwoText()) {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                                return;
                            }
                            setData();
                        } else if (i == 2){
                            if ( surveyFormThreeFragment.checkFromThreeText()) {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                                return;
                            }
                            setData();
                        }else if (i == 3){
                            if ( surveyFormFourFragment.checkFromFourText()) {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                                return;
                            }
                            setData();
                        }else if (i == 4){
                            if ( surveyFormFiveFragment.checkFromFiveText()) {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() , true);
                                return;
                            }
                            setData();
                        }
                    }*/
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
                surveyDetailsVM = new ViewModelProvider((ViewModelStoreOwner) context).get(SurveyDetailsVM.class);
                surveyDetailsVM.surveyFormApi();
                surveyDetailsVM.SaveSurveyDetailsMutableLiveData().observe((LifecycleOwner) context, new Observer<List<SurveyDetailsResponsePojo>>() {
                    @Override
                    public void onChanged(List<SurveyDetailsResponsePojo> surveyDetailsResponsePojos) {
                        Log.e(TAG, "SurveyLiveData: " + surveyDetailsResponsePojos.toString());
                        if (surveyDetailsResponsePojos.get(0).getStatus().matches(AUtils.STATUS_SUCCESS)) {
                            if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI)) {
                                AUtils.success(context, surveyDetailsResponsePojos.get(0).getMessageMar());
                            } else {
                                AUtils.success(context, surveyDetailsResponsePojos.get(0).getMessage());
                            }
                            startActivity(new Intent(context, SurveyCompletActivity.class));
                        }
                        if (surveyDetailsResponsePojos.get(0).getStatus().matches(AUtils.STATUS_ERROR)) {
                            if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI)) {
                                AUtils.success(context, surveyDetailsResponsePojos.get(0).getMessageMar());
                            } else {
                                AUtils.success(context, surveyDetailsResponsePojos.get(0).getMessage());
                            }
                        }
                    }
                });
                surveyDetailsVM.getSurveyDetailsError().observe((LifecycleOwner) context, new Observer<Throwable>() {
                    @Override
                    public void onChanged(Throwable throwable) {
                        AUtils.error(context, throwable.getMessage());
                    }
                });

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context, SurveyCompletActivity.class));
                Prefs.remove(AUtils.GET_API_REFERENCE_ID);
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
                    if (headerReferenceId.equals(Prefs.getString(AUtils.GET_API_REFERENCE_ID,""))){
                        btnUpdate.setVisibility(View.VISIBLE);
                        btnDone.setVisibility(View.GONE);
                    }

                }else  {
                    btnNext.setVisibility(View.GONE);
                    btnDone.setVisibility(View.VISIBLE);
                    if (apiReferenceId == null){
                        btnUpdate.setVisibility(View.VISIBLE);
                        btnDone.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    private void modelData(){
        requestPojo.setReferanceId(Prefs.getString(AUtils.PREFS.SUR_REFERENCE_ID,""));
        requestPojo.setHouseLat(Prefs.getString(AUtils.LAT,"0"));
        requestPojo.setHouseLong(Prefs.getString(AUtils.LONG,"0"));
        requestPojo.setName(Prefs.getString(AUtils.PREFS.SUR_NAME,"0"));
        requestPojo.setMobileNumber(Prefs.getString(AUtils.PREFS.SUR_MOBILE,""));
        requestPojo.setDateOfBirth(Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,""));
        requestPojo.setAge(Prefs.getString(AUtils.PREFS.SUR_AGE,""));
        requestPojo.setGender(Prefs.getString(AUtils.PREFS.SUR_GENDER,""));
        requestPojo.setBloodGroup(Prefs.getString(AUtils.PREFS.SUR_BLOOD_GROUP,""));
        requestPojo.setQualification(Prefs.getString(AUtils.PREFS.SUR_QUALIFICATION,""));
        requestPojo.setOccupation(Prefs.getString(AUtils.PREFS.SUR_OCCUPATION,""));
        requestPojo.setMaritalStatus(Prefs.getString(AUtils.PREFS.SUR_MARITAL_STATUS,""));
        requestPojo.setMarriageDate(Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DATE,""));
        requestPojo.setLivingStatus(Prefs.getString(AUtils.PREFS.SUR_LIVING_STATUS,""));
        requestPojo.setTotalAdults(Prefs.getString(AUtils.PREFS.SUR_TOTAL_ADULT,""));
        requestPojo.setTotalChildren(Prefs.getString(AUtils.PREFS.SUR_TOTAL_CHILDREN,""));
        requestPojo.setTotalSrCitizen(Prefs.getString(AUtils.PREFS.SUR_TOTAL_CITIZEN,""));
        requestPojo.setTotalMember(Prefs.getString(AUtils.PREFS.SUR_TOTAL_MEMBER,""));
        requestPojo.setWillingStart(Prefs.getString(AUtils.PREFS.SUR_WILLING_START,""));
        requestPojo.setResourcesAvailable(Prefs.getString(AUtils.PREFS.SUR_RESOURCE_AVAILABLE,""));
        requestPojo.setMemberJobOtherCity(Prefs.getString(AUtils.PREFS.SUR_MEMBER_JOB_OTHER_CITY,""));
        requestPojo.setNoOfVehicle(Prefs.getString(AUtils.PREFS.SUR_NUM_OF_VEHICLE,""));
        requestPojo.setTwoWheelerQty(Prefs.getString(AUtils.PREFS.SUR_TWO_WHEELER_QTY,""));
        requestPojo.setFourWheelerQty(Prefs.getString(AUtils.PREFS.SUR_FOUR_WHEELER_QTY,""));
        requestPojo.setNoPeopleVote(Prefs.getString(AUtils.PREFS.SUR_NUM_OF_PEOPLE_VOTE,""));
        requestPojo.setSocialMedia(Prefs.getString(AUtils.PREFS.SUR_SOCIAL_MEDIA,""));
        requestPojo.setOnlineShopping(Prefs.getString(AUtils.PREFS.SUR_ONLINE_SHOPPING,""));
        requestPojo.setPaymentModePrefer(" ");
        requestPojo.setOnlinePayApp(Prefs.getString(AUtils.PREFS.SUR_ONLINE_PAY_APP,""));
        requestPojo.setInsurance(Prefs.getString(AUtils.PREFS.SUR_INSURANCE,""));
        requestPojo.setUnderInsurer(Prefs.getString(AUtils.PREFS.SUR_UNDER_INSURANCE,""));
        requestPojo.setAyushmanBeneficiary(Prefs.getString(AUtils.PREFS.SUR_AYUSHMAN_BENE,""));
        requestPojo.setBoosterShot(Prefs.getString(AUtils.PREFS.SUR_BOOSTER_SHOT,""));
        requestPojo.setMemberDivyang(Prefs.getString(AUtils.PREFS.SUR_MEMBER_OF_DIVYANG,""));
        requestPojo.setCreateUserId("");
        requestPojo.setUpdateUserId("");
    }

    private void getSurveyApi(){
        surveyDetailsVM = new ViewModelProvider((ViewModelStoreOwner) context).get(SurveyDetailsVM.class);
        surveyDetailsVM.getSurveyResponseApi();
        surveyDetailsVM.getSurveyMutableLiveData().observe((LifecycleOwner) context, new Observer<List<GetSurveyResponsePojo>>() {
            @Override
            public void onChanged(List<GetSurveyResponsePojo> getSurveyResponsePojos) {
                if (getSurveyResponsePojos != null){
                    Log.e(TAG, "SurveyLiveData: " + getSurveyResponsePojos.toString());
                    getSurveyDetailsPojo = getSurveyResponsePojos;
                    if (getSurveyDetailsPojo != null){
                        apiReferenceId = getSurveyDetailsPojo.get(0).getReferanceId();
                        if (apiReferenceId != null){
                            Prefs.putString(AUtils.GET_API_REFERENCE_ID, apiReferenceId);
                            if (apiReferenceId.equals(headerReferenceId)){
                                Toast.makeText(context, "Survey Already Done", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        surveyDetailsVM.getSurveyDetailsError().observe((LifecycleOwner) context, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                AUtils.error(context, throwable.getMessage());
            }
        });

        /*surveyDetailsRepo.getSurveyDetails(referenceId, new SurveyDetailsRepo.IGetSurveyResponse() {
            @Override
            public void onResponse(List<GetSurveyResponsePojo> getSurveyResponsePojo) {
                if (getSurveyResponsePojo != null){
                    Log.e(TAG, "getSurveyResponse: " + getSurveyResponsePojo.toString());

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });*/
    }
}