package com.appynitty.swachbharatabhiyanlibrary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.SurPagerAdapter;
import com.appynitty.swachbharatabhiyanlibrary.entity.OfflineSurvey;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFiveFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormFourFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormOneFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormThreeFragment;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SurveyFormTwoFragment;
import com.appynitty.swachbharatabhiyanlibrary.pojos.GetApiResponseModel;
import com.appynitty.swachbharatabhiyanlibrary.pojos.GetSurveyResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.OfflineSurveyRepo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SurveyDetailsRepo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.viewmodels.OfflineSurveyVM;
import com.appynitty.swachbharatabhiyanlibrary.viewmodels.SurveyDetailsVM;
import com.pixplicity.easyprefs.library.Prefs;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SurveyInformationActivity extends AppCompatActivity {

    private static final String TAG = "SurveyInformationActivity";
    private final static int NUM_PAGES = 5;
    private Context context;
    private FrameLayout frameLayout;
    private DotsIndicator dotsIndicator;

    private Button btnBack,btnNext, btnDone,btnUpdate;
    private ImageView imgBack;
    private TextView txtHouseId;

    private ViewPager2 viewPager;
    private SurPagerAdapter pagerAdapter;
    private View view;
    private ProgressBar loader;

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
    String getApiHouseId,getApiName,getApiMobile,getApiAge, getApiDob,getApiGender,getApiBloodGroup;
    String getApiQualification,getApiOccupation,getApiMaritalStatus,getApiMarriageDate,getApiLivingStatus;
    String getApiTotalMember,getApiWillingStart,getApiResAvailable,getApiJobOtherCity,getApiTotalVehicle;
    String getApiTwoWheeler,getApiFourWheeler,getApiTotalVote,getApiSocialMedia,getApiShopping,getApiPaymentApp;
    String getApiInsurance,getApiUnderI,getApiAyushman,getApiBoosterDose,getApiDivyang,getApiAdult,getApiChildren,getApiSeniorCitizen;
    private List<String> selectedSocialMediaList = new ArrayList<>();
    private List<String> selectedShoppingList = new ArrayList<>();
    private List<String> selectedPaymentList = new ArrayList<>();
    private int fragPosition = 0;
    private Bundle bundle;
    private GetApiResponseModel apiResponseModel;
    private OfflineSurveyVM offlineSurveyVM;
    List<OfflineSurvey> offlineSurveyList;
    List<SurveyDetailsRequestPojo> surveyDetailsRequestPojoList;
    private OfflineSurveyRepo offlineSurveyRepo;
    private TextView txtNoConnection;
    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";
    private Matcher matcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_information);
        init();
    }
    private void init(){
        context = this;
        offlineSurveyList = new ArrayList<>();
        surveyDetailsRequestPojoList = new ArrayList<>();
        headerReferenceId = Prefs.getString(AUtils.PREFS.SUR_REFERENCE_ID,"");
        frameLayout = findViewById(R.id.container_frame_layout);
        viewPager = findViewById(R.id.view_pager);
        txtNoConnection = findViewById(R.id.txt_no_connection);
        txtNoConnection.setVisibility(View.GONE);
        viewPager.setUserInputEnabled(false);
        txtHouseId = findViewById(R.id.txt_house_id_num);
        txtHouseId.setText(String.format("( %s )", Prefs.getString(AUtils.PREFS.SUR_REFERENCE_ID, "")));
        loader = findViewById(R.id.progress_bar);
        dotsIndicator = findViewById(R.id.dots_indicator);
        surveyFormFiveFragment = new SurveyFormFiveFragment();
        //surveyFormFiveFragment.setArguments(bundle);
        surveyFormFourFragment = new SurveyFormFourFragment();
        //surveyFormFourFragment.setArguments(bundle);
        surveyFormThreeFragment = new SurveyFormThreeFragment();
        //surveyFormThreeFragment.setArguments(bundle);
        surveyFormTwoFragment = new SurveyFormTwoFragment();
       // surveyFormTwoFragment.setArguments(bundle);
        surveyFormOneFragment = new SurveyFormOneFragment();
      //  surveyFormOneFragment.setArguments(bundle);


        bundle = new Bundle();

        imgBack = findViewById(R.id.img_survey_back);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);
        btnDone = findViewById(R.id.btn_done);
       /* btnUpdate = findViewById(R.id.btn_update);*/

        btnDone.setVisibility(View.GONE);
        /*btnUpdate.setVisibility(View.GONE);*/
        loader.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);

        /*offlineSurveyVM = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(OfflineSurveyVM.class);
        offlineSurveyVM.getAllSurveyLiveData().observe(this, offlineSurveys -> {
            if (offlineSurveys != null && !offlineSurveys.isEmpty()){

                for (int i=0; i<offlineSurveys.size(); i++){
                    Log.e(TAG, "offline survey list: "+offlineSurveys.get(i).getSurveyRequestObj());
                   // offlineSurveyVM.insert(offlineSurveys.get(i));
                }

                *//*if (offlineSurveys.size() > 0) {
                    for (OfflineSurvey offlineSurvey : offlineSurveys) {
                        offlineSurvey.getSurveyRequestObj().setReferanceId(offlineSurvey.getHouseId());
                        offlineSurveyVM.update(offlineSurvey);
                    }
                    for (int i = 0; i < offlineSurveys.size(); i++) {
                        surveyDetailsRequestPojoList.add(offlineSurveys.get(i).getSurveyRequestObj());
                    }
                    sendOfflineSurvey(surveyDetailsRequestPojoList);
                }*//*
            }
        });*/

        if (AUtils.isInternetAvailable(AUtils.mainApplicationConstant)) {

            if (AUtils.isConnectedFast(getApplicationContext())) {
                txtNoConnection.setVisibility(View.GONE);
                getSurveyApi();
            }else {
                AUtils.warning(SurveyInformationActivity.this, getResources().getString(R.string.slow_internet));
            }
        }else {

            AUtils.warning(SurveyInformationActivity.this, getResources().getString(R.string.no_internet_error));
            txtNoConnection.setVisibility(View.VISIBLE);
            pagerAdapter = new SurPagerAdapter(getSupportFragmentManager(),getLifecycle(),null);
            viewPager.setAdapter(pagerAdapter);
            dotsIndicator.attachTo(viewPager);
        }
        setOnClick();
    }
    private void setData() {
        String surFName = Prefs.getString(AUtils.PREFS.SUR_FIRST_NAME,"");
        String surMName = Prefs.getString(AUtils.PREFS.SUR_MIDDLE_NAME,"");
        String surLName = Prefs.getString(AUtils.PREFS.SUR_LAST_NAME,"");
        String surFullName =surFName+" "+surMName+" "+surLName;
        Prefs.putString(AUtils.PREFS.SUR_FULL_NAME,surFullName);
        Log.d(TAG, "surName: "+surFullName);
        String surMobile = Prefs.getString(AUtils.PREFS.SUR_MOBILE,"");
        Log.d(TAG, "surMobile: "+surMobile);
        String surBirthdayDate = Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,"");
        Log.d(TAG, "surBirthdayDate: "+surBirthdayDate);
        matcher = Pattern.compile(DATE_PATTERN).matcher(surBirthdayDate);
        int myAge = getAge(surBirthdayDate);
        String meriAge = String.valueOf(myAge);
        Log.d(TAG, "myAge is: "+meriAge);
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

                    if (fragPosition == 0){
                        if ( isValidFragOne()) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                            setData();
                        }
                    }else if (fragPosition == 1){
                        if ( isValidFragTwo()) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                            setData();
                        }
                    }else if (fragPosition == 2){
                        if ( isValidFragThree()) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                            setData();
                        }
                    }else if (fragPosition == 3){
                        if ( isValidFragFour()) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                            setData();
                        }
                    } else if (fragPosition == 4){
                        if ( isValidFragFive()) {
                            viewPager.setUserInputEnabled(true);
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                            setData();
                        }
                    }
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

        if (AUtils.isInternetAvailable()) {
            txtNoConnection.setVisibility(View.GONE);
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (fragPosition == 4){

                        if ( isValidFragFive()) {
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
                                        Prefs.remove(AUtils.GET_API_REFERENCE_ID);
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

                            surveyDetailsVM.getProgressStatusLiveData().observe((LifecycleOwner) context, new Observer<Integer>() {
                                @Override
                                public void onChanged(Integer visibility) {
                                    loader.setVisibility(visibility);
                                }
                            });

                        }

                    }
                }
            });
        }else {
            txtNoConnection.setVisibility(View.VISIBLE);
            offlineSurveyVM = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(OfflineSurveyVM.class);
            offlineSurveyVM.getAllSurveyLiveData().observe(this, offlineSurveys -> {
                if (offlineSurveys != null && !offlineSurveys.isEmpty()){
                    Log.e(TAG, "offline survey list: "+offlineSurveys.get(0).getSurveyRequestObj());
                    offlineSurveyVM.insert(offlineSurveys.get(0));
                   /* for (int i=0; i<offlineSurveys.size(); i++){
                        Log.e(TAG, "offline survey list: "+offlineSurveys.get(i).getSurveyRequestObj());
                        offlineSurveyVM.insert(offlineSurveys.get(i));
                    }*/
                }
            });
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fragPosition == 4){
                        if ( isValidFragFive()) {
                            setData();
                            saveOfflineData();
                            if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI)) {
                                AUtils.success(context,"Data saved offline" );
                            }
                            startActivity(new Intent(context, SurveyCompletActivity.class));
                            Prefs.remove(AUtils.GET_API_REFERENCE_ID);
                        }
                    }
                }
            });
        }

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                Log.i("Rahul", "PageChange: "+position);
                fragPosition = position;
                if(position==0) {
                   // loadFragment(surveyFormOneFragment);
                    btnBack.setVisibility(View.GONE);
                }else  {
                    btnBack.setVisibility(View.VISIBLE);
                }
                if(position < Objects.requireNonNull(viewPager.getAdapter()).getItemCount() -1 ) {
                    btnNext.setVisibility(View.VISIBLE);
                    btnDone.setVisibility(View.GONE);
                    /*if (headerReferenceId.equals(Prefs.getString(AUtils.GET_API_REFERENCE_ID,""))){
                        btnUpdate.setVisibility(View.VISIBLE);
                        btnDone.setVisibility(View.GONE);
                    }*/
                }else  {
                    btnNext.setVisibility(View.GONE);
                    btnDone.setVisibility(View.VISIBLE);
                    viewPager.setUserInputEnabled(false);
                    /*if (apiReferenceId == null){
                        btnUpdate.setVisibility(View.GONE);
                        btnDone.setVisibility(View.VISIBLE);
                    }else {
                        btnDone.setVisibility(View.GONE);
                        btnUpdate.setVisibility(View.VISIBLE);
                    }*/
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
    private void saveOfflineData(){
        SurveyDetailsRequestPojo requestPojo = new SurveyDetailsRequestPojo();
        requestPojo.setReferanceId(Prefs.getString(AUtils.PREFS.SUR_REFERENCE_ID,""));
        requestPojo.setHouseLat(Prefs.getString(AUtils.LAT,"0"));
        requestPojo.setHouseLong(Prefs.getString(AUtils.LONG,"0"));
        requestPojo.setName(Prefs.getString(AUtils.PREFS.SUR_FULL_NAME,"0"));
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
        requestPojo.setCreateUserId(Prefs.getString(AUtils.PREFS.USER_ID,""));
        requestPojo.setUpdateUserId(Prefs.getString(AUtils.PREFS.USER_ID,""));
        String houseId = Prefs.getString(AUtils.PREFS.SUR_REFERENCE_ID,"");

        OfflineSurvey offlineSurvey = new OfflineSurvey(houseId,requestPojo);
        offlineSurveyVM.insert(offlineSurvey);

       // sendOfflineSurvey((List<SurveyDetailsRequestPojo>) offlineSurvey);
       // sendOfflineSurvey((Collections.singleton(offlineSurvey));

    }

    private void getSurveyApi(){
        surveyDetailsVM = new ViewModelProvider((ViewModelStoreOwner) context).get(SurveyDetailsVM.class);
        surveyDetailsVM.getSurveyResponseApi();
        surveyDetailsVM.getSurveyMutableLiveData().observe((LifecycleOwner) context, new Observer<List<GetSurveyResponsePojo>>() {
            @Override
            public void onChanged(List<GetSurveyResponsePojo> getSurveyResponsePojos) {
                if (getSurveyResponsePojos.isEmpty()){

                    pagerAdapter = new SurPagerAdapter(getSupportFragmentManager(),getLifecycle(),null);
                    viewPager.setAdapter(pagerAdapter);
                    dotsIndicator.attachTo(viewPager);
                    Log.e(TAG, "SurveyLiveData: " + getSurveyResponsePojos.toString());

                    btnDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (fragPosition == 4){

                                if ( isValidFragFive()) {
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

                                    surveyDetailsVM.getProgressStatusLiveData().observe((LifecycleOwner) context, new Observer<Integer>() {
                                        @Override
                                        public void onChanged(Integer visibility) {
                                            loader.setVisibility(visibility);
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
                else {
                    Log.e(TAG, "SurveyLiveData: " + getSurveyResponsePojos.toString());
                    getSurveyDetailsPojo = getSurveyResponsePojos;
                    for (int i=0; i< getSurveyDetailsPojo.size(); i++){
                        if (getSurveyDetailsPojo.get(i) != null){
                            apiReferenceId = getSurveyDetailsPojo.get(i).getReferanceId();
                            apiResponseModel = new GetApiResponseModel();
                            /*loadFragment(surveyFormOneFragment);*/
                            Log.i("social", "getApiSurvey: " + apiReferenceId);
                            Prefs.putString(AUtils.PREFS.SUR_REFERENCE_ID,apiReferenceId);
                            apiResponseModel.setReferanceId(apiReferenceId);
                            getApiHouseId = String.valueOf(getSurveyDetailsPojo.get(i).getHouseId());
                            Log.i("social", "getApiSurvey: " + getApiHouseId);
                            apiResponseModel.setHouseId(getApiHouseId);
                            getApiName = getSurveyDetailsPojo.get(i).getName();
                            Log.i("social", "getApiSurvey: " + getApiName);

                            String[] separatedFullName = getApiName.split("\\s+");
                            String firstName = separatedFullName[0];
                            String middleName = separatedFullName[1];
                            String  lastName = separatedFullName[2];
                            Log.i("social", "firstName: " +firstName.toString());
                            Log.i("social", "middleName: " +middleName.toString());
                            Log.i("social", "lastName: " +lastName.toString());
                            apiResponseModel.setFullName(getApiName);
                            apiResponseModel.setFirstName(firstName);
                            apiResponseModel.setMiddleName(middleName);
                            apiResponseModel.setLastName(lastName);
                            Prefs.putString(AUtils.PREFS.SUR_FULL_NAME,getApiName);
                            Prefs.putString(AUtils.PREFS.SUR_FIRST_NAME,firstName.toString());
                            Prefs.putString(AUtils.PREFS.SUR_MIDDLE_NAME,middleName.toString());
                            Prefs.putString(AUtils.PREFS.SUR_LAST_NAME,lastName.toString());
                            bundle.putString("fragmentOneName", getApiName);


                            getApiMobile = getSurveyDetailsPojo.get(i).getMobileNumber();
                            Log.i("social", "getApiSurvey: " + getApiMobile);
                            apiResponseModel.setMobileNumber(getApiMobile);
                            Prefs.putString(AUtils.PREFS.SUR_MOBILE,getApiMobile);
                            bundle.putString("fragmentOneMobile", getApiMobile);
                            getApiAge = String.valueOf(getSurveyDetailsPojo.get(i).getAge());
                            Log.i("social", "getApiSurvey: " + getApiAge);
                            apiResponseModel.setAge(getApiAge);
                            Prefs.putString(AUtils.PREFS.SUR_AGE,getApiAge);
                            bundle.putString("fragmentOneAge", getApiAge);
                            getApiDob = getSurveyDetailsPojo.get(i).getDateOfBirth();
                            Log.i("social", "getApiSurvey: " + getApiDob);
                            String normalBirthDate = AUtils.getApiSurveyResponseDateConvertToLocal(getApiDob);
                            Log.i("social", "normalBirthDate: " + normalBirthDate);
                            String[] separated = normalBirthDate.split("-");
                            String yyyy = separated[0];
                            String mm = separated[1];
                            String  dd = separated[2];
                            Log.i("social", "birthDay: " + dd.toString());
                            Log.i("social", "birthMonth: " + mm.toString());
                            Log.i("social", "birthYear: " + yyyy.toString());
                            apiResponseModel.setDateOfBirth(normalBirthDate);
                            Prefs.putString(AUtils.PREFS.SUR_BIRTHDAY_DATE,normalBirthDate);
                            apiResponseModel.setBirtDay(dd.toString());
                            Prefs.putString(AUtils.PREFS.SUR_BIRTH_DAY,dd.toString());
                            apiResponseModel.setBirthMonth(mm.toString());
                            Prefs.putString(AUtils.PREFS.SUR_BIRTH_MONTH,mm.toString());
                            apiResponseModel.setBirthYear(yyyy.toString());
                            Prefs.putString(AUtils.PREFS.SUR_BIRTH_YEAR,yyyy.toString());

                            /*if (getApiDob != null){
                                String normalBirthDate = AUtils.getApiSurveyResponseDateConvertToLocal(getApiDob);
                                Log.i("social", "normalBirthDate: " + normalBirthDate);
                                apiResponseModel.setDateOfBirth(normalBirthDate);
                            }else {
                                apiResponseModel.setBirtDay(" ");
                                apiResponseModel.setBirthMonth(" ");
                                apiResponseModel.setBirthYear(" ");
                            }*/

                            getApiGender = getSurveyDetailsPojo.get(i).getGender();
                            Log.i("social", "getApiSurvey: " + getApiGender);
                            apiResponseModel.setGender(getApiGender);
                            Prefs.putString(AUtils.PREFS.SUR_GENDER,getApiGender);
                            bundle.putString("fragmentOneGender", getApiGender);
                            getApiBloodGroup = getSurveyDetailsPojo.get(i).getBloodGroup();
                            Log.i("social", "getApiSurvey: " + getApiBloodGroup);
                            apiResponseModel.setBloodGroup(getApiBloodGroup);
                            Prefs.putString(AUtils.PREFS.SUR_BLOOD_GROUP,getApiBloodGroup);

                            //loadFragment(surveyFormTwoFragment);
                            getApiQualification = getSurveyDetailsPojo.get(i).getQualification();
                            Log.i("social", "getApiSurvey: " + getApiQualification);
                            apiResponseModel.setQualification(getApiQualification);
                            Prefs.putString(AUtils.PREFS.SUR_QUALIFICATION,getApiQualification);
                            getApiOccupation = getSurveyDetailsPojo.get(i).getOccupation();
                            Log.i("social", "getApiSurvey: " + getApiOccupation);
                            apiResponseModel.setOccupation(getApiOccupation);
                            Prefs.putString(AUtils.PREFS.SUR_OCCUPATION,getApiOccupation);
                            getApiMaritalStatus = getSurveyDetailsPojo.get(i).getMaritalStatus();
                            Log.i("social", "getApiSurvey: " + getApiMaritalStatus);
                            apiResponseModel.setMaritalStatus(getApiMaritalStatus);
                            Prefs.putString(AUtils.PREFS.SUR_MARITAL_STATUS,getApiMaritalStatus);
                            getApiMarriageDate = getSurveyDetailsPojo.get(i).getMarriageDate();
                            Log.i("social", "getApiSurvey: " + getApiMarriageDate);

                            if (getApiMarriageDate != null){
                                String normalMarriageDate = AUtils.getApiSurveyResponseDateConvertToLocal(getApiMarriageDate);
                                Log.i("social", "normalMarriageDate: " + normalMarriageDate);
                                apiResponseModel.setMarriageDate(normalMarriageDate);
                                Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_DATE,normalMarriageDate);
                                String[] separatedMarriage = normalMarriageDate.split("-");
                                String mYear = separatedMarriage[0];
                                String mMonth = separatedMarriage[1];
                                String mDay = separatedMarriage[2];
                                Log.i("social", "marriageDay: " + mDay.toString());
                                Log.i("social", "marriageMonth: " + mMonth.toString());
                                Log.i("social", "marriageYear: " +mYear.toString());
                                apiResponseModel.setMarriageDay(mDay.toString());
                                Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_DAY,mDay.toString());
                                apiResponseModel.setMarriageMonth(mMonth.toString());
                                Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_MONTH,mMonth.toString());
                                apiResponseModel.setMarriageYear(mYear.toString());
                                Prefs.putString(AUtils.PREFS.SUR_MARRIAGE_YEAR,mYear.toString());

                            }else {
                                apiResponseModel.setMarriageDay(" ");
                                apiResponseModel.setMarriageMonth(" ");
                                apiResponseModel.setMarriageYear(" ");
                            }
                            getApiLivingStatus = getSurveyDetailsPojo.get(i).getLivingStatus();
                            Log.i("social", "getApiSurvey: " + getApiLivingStatus);
                            apiResponseModel.setLivingStatus(getApiLivingStatus);
                            Prefs.putString(AUtils.PREFS.SUR_LIVING_STATUS,getApiLivingStatus);

                            //loadFragment(surveyFormThreeFragment);
                            getApiTotalMember = String.valueOf(getSurveyDetailsPojo.get(i).getTotalMember());
                            Log.i("social", "getApiSurvey: " + getApiTotalMember);
                            apiResponseModel.setTotalMember(getApiTotalMember);
                            Prefs.putString(AUtils.PREFS.SUR_TOTAL_MEMBER,getApiTotalMember);
                            getApiAdult = String.valueOf(getSurveyDetailsPojo.get(i).getTotalAdults());
                            Log.i("social", "getApiSurvey: " + getApiAdult);
                            apiResponseModel.setAdults(getApiAdult);
                            Prefs.putString(AUtils.PREFS.SUR_TOTAL_ADULT,getApiAdult);
                            getApiChildren = String.valueOf(getSurveyDetailsPojo.get(i).getTotalChildren());
                            Log.i("social", "getApiSurvey: " + getApiChildren);
                            apiResponseModel.setChildren(getApiChildren);
                            Prefs.putString(AUtils.PREFS.SUR_TOTAL_CHILDREN,getApiChildren);
                            getApiSeniorCitizen = String.valueOf(getSurveyDetailsPojo.get(i).getTotalSrCitizen());
                            Log.i("social", "getApiSurvey: " + getApiSeniorCitizen);
                            apiResponseModel.setSrCitizen(getApiSeniorCitizen);
                            Prefs.putString(AUtils.PREFS.SUR_TOTAL_CITIZEN,getApiSeniorCitizen);
                            getApiWillingStart = String.valueOf(getSurveyDetailsPojo.get(i).isWillingStart());
                            Log.i("social", "getApiSurvey: " + getApiWillingStart);
                            apiResponseModel.setWillingStart(getApiWillingStart);
                            Prefs.putString(AUtils.PREFS.SUR_WILLING_START,getApiWillingStart);
                            getApiResAvailable = getSurveyDetailsPojo.get(i).getResourcesAvailable();
                            Log.i("social", "getApiSurvey: " + getApiResAvailable);
                            apiResponseModel.setResourcesAvailable(getApiResAvailable);
                            Prefs.putString(AUtils.PREFS.SUR_RESOURCE_AVAILABLE,getApiResAvailable);
                            getApiJobOtherCity = String.valueOf(getSurveyDetailsPojo.get(i).isMemberJobOtherCity());
                            Log.i("social", "getApiSurvey: " + getApiJobOtherCity);
                            apiResponseModel.setMemberJobOtherCity(getApiJobOtherCity);
                            Prefs.putString(AUtils.PREFS.SUR_MEMBER_JOB_OTHER_CITY,getApiJobOtherCity);
                            getApiTotalVehicle = String.valueOf(getSurveyDetailsPojo.get(i).getNoOfVehicle());
                            Log.i("social", "getApiSurvey: " + getApiTotalVehicle);
                            apiResponseModel.setTotalVehicle(getApiTotalVehicle);
                            Prefs.putString(AUtils.PREFS.SUR_NUM_OF_VEHICLE,getApiTotalVehicle);
                            getApiTwoWheeler = String.valueOf(getSurveyDetailsPojo.get(i).getTwoWheelerQty());
                            Log.i("social", "getApiSurvey: " + getApiTwoWheeler);
                            apiResponseModel.setTwoWheelerQty(getApiTwoWheeler);
                            Prefs.putString(AUtils.PREFS.SUR_TWO_WHEELER_QTY,getApiTwoWheeler);
                            getApiFourWheeler = String.valueOf(getSurveyDetailsPojo.get(i).getFourWheelerQty());
                            Log.i("social", "getApiSurvey: " + getApiFourWheeler);
                            apiResponseModel.setFourWheelerQty(getApiFourWheeler);
                            Prefs.putString(AUtils.PREFS.SUR_FOUR_WHEELER_QTY,getApiFourWheeler);

                           // loadFragment(surveyFormFourFragment);
                            getApiTotalVote = String.valueOf(getSurveyDetailsPojo.get(i).getNoPeopleVote());
                            Log.i("social", "getApiSurvey: " + getApiTotalVote);
                            apiResponseModel.setNoPeopleVote(getApiTotalVote);
                            Prefs.putString(AUtils.PREFS.SUR_NUM_OF_PEOPLE_VOTE,getApiTotalVote);
                            getApiSocialMedia = getSurveyDetailsPojo.get(i).getSocialMedia();
                            Log.i("social", "getApiSurvey: " + getApiSocialMedia);
                            if (getApiSocialMedia != null && !getApiSocialMedia.equals("")){
                                apiResponseModel.setSocialMedia(getApiSocialMedia);
                                Prefs.putString(AUtils.PREFS.SUR_SOCIAL_MEDIA,getApiSocialMedia);
                            }

                            getApiShopping = getSurveyDetailsPojo.get(i).getOnlineShopping();
                            Log.i("social", "getApiSurvey: " + getApiShopping);
                            if (getApiShopping != null && !getApiShopping.equals("")){
                                apiResponseModel.setOnlineShopping(getApiShopping);
                                Prefs.putString(AUtils.PREFS.SUR_ONLINE_SHOPPING,getApiShopping);
                            }
                            getApiPaymentApp = getSurveyDetailsPojo.get(i).getOnlinePayApp();
                            Log.i("social", "getApiSurvey: " + getApiPaymentApp);
                            if (getApiPaymentApp != null && !getApiPaymentApp.equals("")){
                                apiResponseModel.setOnlinePayApp(getApiPaymentApp);
                                Prefs.putString(AUtils.PREFS.SUR_ONLINE_PAY_APP,getApiPaymentApp);
                            }

                           // loadFragment(surveyFormFiveFragment);
                            getApiInsurance = getSurveyDetailsPojo.get(i).getInsurance();
                            Log.i("social", "getApiSurvey: " + getApiInsurance);
                            apiResponseModel.setInsurance(getApiInsurance);
                            Prefs.putString(AUtils.PREFS.SUR_INSURANCE,getApiInsurance);
                            getApiUnderI = String.valueOf(getSurveyDetailsPojo.get(i).isUnderInsurer());
                            Log.i("social", "getApiSurvey: " + getApiUnderI);
                            apiResponseModel.setUnderInsurer(getApiUnderI);
                            Prefs.putString(AUtils.PREFS.SUR_UNDER_INSURANCE,getApiUnderI);
                            getApiAyushman = String.valueOf(getSurveyDetailsPojo.get(i).isAyushmanBeneficiary());
                            Log.i("social", "getApiSurvey: " + getApiAyushman);
                            apiResponseModel.setAyushmanBeneficiary(getApiAyushman);
                            Prefs.putString(AUtils.PREFS.SUR_AYUSHMAN_BENE,getApiAyushman);
                            getApiBoosterDose = String.valueOf(getSurveyDetailsPojo.get(i).isBoosterShot());
                            Log.i("social", "getApiSurvey: " + getApiBoosterDose);
                            apiResponseModel.setBoosterShot(getApiBoosterDose);
                            Prefs.putString(AUtils.PREFS.SUR_BOOSTER_SHOT,getApiBoosterDose);
                            getApiDivyang = String.valueOf(getSurveyDetailsPojo.get(i).isMemberDivyang());
                            Log.i("social", "getApiSurvey: " + getApiDivyang);
                            apiResponseModel.setMemberDivyang(getApiDivyang);
                            Prefs.putString(AUtils.PREFS.SUR_MEMBER_OF_DIVYANG,getApiDivyang);



                            if (apiReferenceId != null){
                                Prefs.putString(AUtils.GET_API_REFERENCE_ID, apiReferenceId);
                                if (headerReferenceId.equals(apiReferenceId)){
                                    Toast.makeText(context, "Survey Already Done", Toast.LENGTH_SHORT).show();
                                    Log.i("social", "getApiSurvey: " + "property "+apiReferenceId+" survey form already filled");
                                    Log.d(TAG, "apiResponseModel: ", apiResponseModel);
                                    pagerAdapter = new SurPagerAdapter(getSupportFragmentManager(),getLifecycle(),apiResponseModel);
                                    viewPager.setAdapter(pagerAdapter);
                                    dotsIndicator.attachTo(viewPager);

                                    SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
                                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                    prefsEditor.putString("MyObject", AUtils.objectToString(apiResponseModel));
                                    prefsEditor.commit();

                                    btnDone.setText(R.string.btn_update);
                                    btnDone.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (fragPosition == 4){

                                                if ( isValidFragFive()) {
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

                                                    surveyDetailsVM.getProgressStatusLiveData().observe((LifecycleOwner) context, new Observer<Integer>() {
                                                        @Override
                                                        public void onChanged(Integer visibility) {
                                                            loader.setVisibility(visibility);
                                                        }
                                                    });

                                                }

                                            }

                                        }
                                    });
                                }
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
        surveyDetailsVM.getProgressStatusLiveData().observe((LifecycleOwner) context, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                loader.setVisibility(visibility);
            }
        });
    }
    private boolean isValidFragOne(){

        String surFName = Prefs.getString(AUtils.PREFS.SUR_FIRST_NAME,"");
        String surMName = Prefs.getString(AUtils.PREFS.SUR_MIDDLE_NAME,"");
        String surLName = Prefs.getString(AUtils.PREFS.SUR_LAST_NAME,"");
        String surFullName = Prefs.getString(AUtils.PREFS.SUR_FULL_NAME,"");

        String surMobile = Prefs.getString(AUtils.PREFS.SUR_MOBILE,"");
        String bDay = Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,"");
        String bMonth = Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,"");
        String bYear = Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,"");
        String surBirthdayDate = Prefs.getString(AUtils.PREFS.SUR_BIRTHDAY_DATE,"");

        String age = Prefs.getString(AUtils.PREFS.SUR_AGE,"");
        String mGender = Prefs.getString(AUtils.PREFS.SUR_GENDER,"");
        String bloodGroup = Prefs.getString(AUtils.PREFS.SUR_BLOOD_GROUP,"");
        if (surFName.trim().isEmpty()){
            AUtils.warning(context,"Please enter your first name");
            return false;
        }else if (surMName.trim().isEmpty()){
            AUtils.warning(context,"Please enter your middle name");
            return false;
        }else if (surLName.trim().isEmpty()){
            AUtils.warning(context,"Please enter your last name");
            return false;
        } else if (surMobile.trim().isEmpty()){
            AUtils.warning(context,"Please enter your mobile number");
            return false;
        }else if (surMobile.length()<10){
            AUtils.warning(context,"Please enter your valid mobile number");
            return false;
        }else if (bDay.trim().isEmpty()){
            AUtils.warning(context,"Please select your birthday date");
            return false;
        }else if (bMonth.trim().isEmpty()){
            AUtils.warning(context,"Please select your birthday Month");
            return false;
        }else if (bYear.trim().isEmpty()){
            AUtils.warning(context,"Please select your birthday Year");
            return false;
        }else if (age.trim().isEmpty()){
            AUtils.warning(context,"Your age calculate successfully");
            return true;
        }else if (bYear.equalsIgnoreCase("2023")){
            AUtils.warning(context,"Please select your valid birthday Year");
            return false;
        }else if (bYear.equalsIgnoreCase("2024")){
            AUtils.warning(context,"Please select your valid birthday Year");
            return false;
        }else if (bYear.equalsIgnoreCase("2022")){
            AUtils.warning(context,"Please select your valid birthday Year");
            return false;
        }else if (bYear.equalsIgnoreCase("2021")){
            AUtils.warning(context,"Please select your valid birthday Year");
            return false;
        }else if (!isValidMonthOfDays(bMonth)){
            return false;
        } else if (mGender.isEmpty()){
            AUtils.warning(context,"Please check your gender");
            return false;
        }else if (bloodGroup.isEmpty()){
            AUtils.warning(context,"Please select your blood group");
            return false;
        }
        return true;
    }
    public boolean isValidMonthOfDays( String month) {
        int bDay = Integer.parseInt(Prefs.getString(AUtils.PREFS.SUR_BIRTH_DAY,""));
        int bMonth = Integer.parseInt(Prefs.getString(AUtils.PREFS.SUR_BIRTH_MONTH,""));
        int bYear = Integer.parseInt(Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,""));
        String  monthValue ;
        int number_Of_DaysInMonth = 0;

        switch (month) {
            case "01":
                monthValue = "01";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of January");
                }else {
                    AUtils.warning(context, "Invalid date of January");
                    return false;
                }
                break;
            case "02":
                monthValue = "02";
                if (((bYear % 4 == 0) && (bYear % 100 != 0)) || (bYear % 400 == 0)) {
                    number_Of_DaysInMonth = 29;
                } else {
                    number_Of_DaysInMonth = 28;
                }
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of February");
                }else {
                    AUtils.warning(context, "Invalid date of February");
                    return false;
                }
                break;
            case "03":
                monthValue = "03";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of March");
                }else {
                    AUtils.warning(context, "Invalid date of March");
                    return false;
                }
                break;
            case "04":
                monthValue = "04";
                number_Of_DaysInMonth = 30;
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of April");
                }else {
                    AUtils.warning(context, "Invalid date of April");
                    return false;
                }
                break;
            case "05":
                monthValue = "05";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of May");
                }else {
                    AUtils.warning(context, "Invalid date of May");
                    return false;
                }
                break;
            case "06":
                monthValue = "06";
                number_Of_DaysInMonth = 30;
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of June");
                }else {
                    AUtils.warning(context, "Invalid date of June");
                    return false;
                }
                break;
            case "07":
                monthValue = "07";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of July");
                }else {
                    AUtils.warning(context, "Invalid date of July");
                    return false;
                }
                break;
            case "08":
                monthValue = "08";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of August");
                }else {
                    AUtils.warning(context, "Invalid date of August");
                    return false;
                }
                break;
            case "09":
                monthValue = "09";
                number_Of_DaysInMonth = 30;
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of September");
                }else {
                    AUtils.warning(context, "Invalid date of September");
                    return false;
                }
                break;
            case "10":
                monthValue = "10";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of October");
                }else {
                    AUtils.warning(context, "Invalid date of October");
                    return false;
                }
                break;
            case "11":
                monthValue = "11";
                number_Of_DaysInMonth = 30;
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of November");
                }else {
                    AUtils.warning(context, "Invalid date of November");
                    return false;
                }
                break;
            case "12":
                monthValue = "12";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= bDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of December");
                }else {
                    AUtils.warning(context, "Invalid date of December");
                    return false;
                }
                break;
        }

        return true;
    }
    private boolean isValidFragTwo(){
        String qualification = Prefs.getString(AUtils.PREFS.SUR_QUALIFICATION,"");
        String occupation = Prefs.getString(AUtils.PREFS.SUR_OCCUPATION,"");
        String maritalStatus = Prefs.getString(AUtils.PREFS.SUR_MARITAL_STATUS,"");
        String mMonth = Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_MONTH,"");
        String living = Prefs.getString(AUtils.PREFS.SUR_LIVING_STATUS,"");
        if (qualification.trim().isEmpty()){
            AUtils.warning(context,"Please select your qualification");
            return false;
        }else if (occupation.trim().isEmpty()){
            AUtils.warning(context,"Please select your occupation");
            return false;
        }else if (maritalStatus.trim().isEmpty()){
            AUtils.warning(context,"Please select your marital status");
            return false;
        }else if (maritalStatus.equals("Married") && !isMarriageValidation()){
            return false;
        }else if (maritalStatus.equals("Married") && !isValidMonthOfDaysMarriage(mMonth)){
            return false;
        }else if (living.trim().isEmpty()){
            AUtils.warning(context,"Please select living status");
            return false;
        }
        return true;
    }
    private boolean isMarriageValidation(){
        String mDay = Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DAY,"");
        String mMonth = Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_MONTH,"");
        String mYear = Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_YEAR,"");
        int marriageAge = 0;
        try {
            String bYear = Prefs.getString(AUtils.PREFS.SUR_BIRTH_YEAR,"");
            String maYear = Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_YEAR,"");
            marriageAge = Integer.parseInt(maYear) - Integer.parseInt(bYear);
        } catch (NumberFormatException e) {
            // The format was incorrect
        }

         if (mDay.trim().isEmpty()){
            AUtils.warning(context,"Please select your marriage date");
            return false;
        }else if (mMonth.trim().isEmpty()){
            AUtils.warning(context,"Please select your marriage Month");
            return false;
        }else if (mYear.trim().isEmpty()){
            AUtils.warning(context,"Please select your marriage Year");
            return false;
        }else if (!mYear.isEmpty()){
            if (marriageAge < 18){
                AUtils.warning(context,"Your age below 18, please select valid marriage date");
                return false;
            }
        }
         return true;
    }

    public boolean isValidMonthOfDaysMarriage( String month) {
        int mDay = Integer.parseInt(Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_DAY,""));
        int mYear = Integer.parseInt(Prefs.getString(AUtils.PREFS.SUR_MARRIAGE_YEAR,""));
        String  monthValue ;
        int number_Of_DaysInMonth = 0;

        switch (month) {
            case "01":
                monthValue = "01";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of January");
                }else {
                    AUtils.warning(context, "Invalid date of January");
                    return false;
                }
                break;
            case "02":
                monthValue = "02";
                if (((mYear % 4 == 0) && (mYear % 100 != 0)) || (mYear % 400 == 0)) {
                    number_Of_DaysInMonth = 29;
                } else {
                    number_Of_DaysInMonth = 28;
                }
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of February");
                }else {
                    AUtils.warning(context, "Invalid date of February");
                    return false;
                }
                break;
            case "03":
                monthValue = "03";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of March");
                }else {
                    AUtils.warning(context, "Invalid date of March");
                    return false;
                }
                break;
            case "04":
                monthValue = "04";
                number_Of_DaysInMonth = 30;
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of April");
                }else {
                    AUtils.warning(context, "Invalid date of April");
                    return false;
                }
                break;
            case "05":
                monthValue = "05";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of May");
                }else {
                    AUtils.warning(context, "Invalid date of May");
                    return false;
                }
                break;
            case "06":
                monthValue = "06";
                number_Of_DaysInMonth = 30;
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of June");
                }else {
                    AUtils.warning(context, "Invalid date of June");
                    return false;
                }
                break;
            case "07":
                monthValue = "07";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of July");
                }else {
                    AUtils.warning(context, "Invalid date of July");
                    return false;
                }
                break;
            case "08":
                monthValue = "08";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of August");
                }else {
                    AUtils.warning(context, "Invalid date of August");
                    return false;
                }
                break;
            case "09":
                monthValue = "09";
                number_Of_DaysInMonth = 30;
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of September");
                }else {
                    AUtils.warning(context, "Invalid date of September");
                    return false;
                }
                break;
            case "10":
                monthValue = "10";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of October");
                }else {
                    AUtils.warning(context, "Invalid date of October");
                    return false;
                }
                break;
            case "11":
                monthValue = "11";
                number_Of_DaysInMonth = 30;
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of November");
                }else {
                    AUtils.warning(context, "Invalid date of November");
                    return false;
                }
                break;
            case "12":
                monthValue = "12";
                number_Of_DaysInMonth = 31;
                if (number_Of_DaysInMonth >= mDay && month.equals(monthValue)){
                    AUtils.info(context, "Valid date of December");
                }else {
                    AUtils.warning(context, "Invalid date of December");
                    return false;
                }
                break;
        }

        return true;
    }
    private boolean isValidFragThree(){
        String totalMember = Prefs.getString(AUtils.PREFS.SUR_TOTAL_MEMBER,"");
        String adult = Prefs.getString(AUtils.PREFS.SUR_TOTAL_ADULT,"");
        String children = Prefs.getString(AUtils.PREFS.SUR_TOTAL_CHILDREN,"");
        String citizen = Prefs.getString(AUtils.PREFS.SUR_TOTAL_CITIZEN,"");
        String willingStart = Prefs.getString(AUtils.PREFS.SUR_WILLING_START,"");
        String resourceA = Prefs.getString(AUtils.PREFS.SUR_RESOURCE_AVAILABLE,"");
        String otherCity = Prefs.getString(AUtils.PREFS.SUR_MEMBER_JOB_OTHER_CITY,"");
        String totalVehicle = Prefs.getString(AUtils.PREFS.SUR_NUM_OF_VEHICLE,"");
        String twoWheeler = Prefs.getString(AUtils.PREFS.SUR_TWO_WHEELER_QTY,"");
        String fourWheeler = Prefs.getString(AUtils.PREFS.SUR_FOUR_WHEELER_QTY,"");

        if (adult.trim().isEmpty()){
            AUtils.warning(context,"Please add total adults");
            return false;
        }else if (children.trim().isEmpty()){
            AUtils.warning(context,"Please add total children");
            return false;
        }else if (citizen.trim().isEmpty()){
            AUtils.warning(context,"Please add total Senior Citizen");
            return false;
        }else if (willingStart.trim().isEmpty()){
            AUtils.warning(context,"Please select yes/no type question");
            return false;
        }else if (resourceA.trim().isEmpty()){
            AUtils.warning(context,"Please select your resources");
            return false;
        }else if (otherCity.trim().isEmpty()){
            AUtils.warning(context,"Please select yes/no type question");
            return false;
        }else if (twoWheeler.trim().isEmpty()){
            AUtils.warning(context,"Please add total two wheeler");
            return false;
        }/*else if (fourWheeler.trim().isEmpty()){
            AUtils.warning(context,"Please add total four wheeler");
            return false;
        }*/
        return true;
    }
    private boolean isValidFragFour(){
        String totalVote = Prefs.getString(AUtils.PREFS.SUR_NUM_OF_PEOPLE_VOTE,"");
        String socialMedia = Prefs.getString(AUtils.PREFS.SUR_SOCIAL_MEDIA,"");
        String shopping = Prefs.getString(AUtils.PREFS.SUR_ONLINE_SHOPPING,"");
        String paymentApp = Prefs.getString(AUtils.PREFS.SUR_ONLINE_PAY_APP,"");

        if (totalVote.trim().isEmpty()){
            AUtils.warning(context,"Please add total vote member");
            return false;
        }else if (socialMedia.trim().isEmpty()){
            AUtils.warning(context,"Please select your used social media app");
            return false;
        }else if (shopping.trim().isEmpty()){
            AUtils.warning(context,"Please select your used shopping app");
            return false;
        }else if (paymentApp.trim().isEmpty()){
            AUtils.warning(context,"Please select your used payment mode app");
            return false;
        }
        return true;
    }
    private boolean isValidFragFive(){
        String insurance = Prefs.getString(AUtils.PREFS.SUR_INSURANCE,"");
        String insuredType = Prefs.getString(AUtils.PREFS.SUR_UNDER_INSURANCE,"");
        String ayushman = Prefs.getString(AUtils.PREFS.SUR_AYUSHMAN_BENE,"");
        String booster = Prefs.getString(AUtils.PREFS.SUR_BOOSTER_SHOT,"");
        String divyang = Prefs.getString(AUtils.PREFS.SUR_MEMBER_OF_DIVYANG,"");

        if (insurance.trim().isEmpty()){
            AUtils.warning(context,"Please select your insurance");
            return false;
        }else if (insuredType.trim().isEmpty()){
            AUtils.warning(context,"Please select your insurance type");
            return false;
        }else if (ayushman.trim().isEmpty()){
            AUtils.warning(context,"Please select yes/no type question");
            return false;
        }else if (booster.trim().isEmpty()){
            AUtils.warning(context,"Please select yes/no type question");
            return false;
        }else if (divyang.trim().isEmpty()){
            AUtils.warning(context,"Please select yes/no type question");
            return false;
        }

        return true;
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            fragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_frame_layout, fragment);
            ft.commit();
            return true;
        }
        return false;
    }

    /*private boolean dataSendFragment(int position){
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = surveyFormOneFragment;
                break;
            case 1:
                fragment = surveyFormTwoFragment;
                break;
            case 2:
                fragment = surveyFormThreeFragment;
                break;
            case 3:
                fragment = surveyFormFourFragment;
                break;
            case 4:
                fragment = surveyFormFiveFragment;
                break;
        }
        return loadFragment(fragment);
    }*/
    private int getAge(String dobString){

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
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

    /*private void setOfflineSurveyIds() {
        offlineSurveyList = (List<OfflineSurvey>) offlineSurveyRepo.getAllOfflineSurvey();
        if (offlineSurveyList.size() > 0) {
            for (OfflineSurvey offlineSurvey : offlineSurveyList) {
                offlineSurvey.getSurveyRequestObj().setReferanceId(offlineSurvey.getHouseId());
                offlineSurveyVM.update(offlineSurvey);
            }
            for (int i = 0; i < offlineSurveyList.size(); i++) {
                surveyDetailsRequestPojoList.add(offlineSurveyList.get(i).getSurveyRequestObj());
            }
            sendOfflineSurvey(surveyDetailsRequestPojoList);
        }

    }*/


    public void sendOfflineSurvey(List<SurveyDetailsRequestPojo> listOffSurvey) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                surveyDetailsRepo.offlineAddSurveyDetails(listOffSurvey, new SurveyDetailsRepo.IOfflineSurveyDetailsResponse() {
                    @Override
                    public void onResponse(List<SurveyDetailsResponsePojo> offlineSurveyDetailsResponse) {
                        Log.e(TAG, "offline data send: " + offlineSurveyDetailsResponse);
                        String houseId;
                        for (int i = 0; i < offlineSurveyDetailsResponse.size(); i++) {
                            if (offlineSurveyDetailsResponse.get(i).getStatus().matches(AUtils.STATUS_SUCCESS)) {
                                houseId = offlineSurveyDetailsResponse.get(i).getHouseId();
                                offlineSurveyRepo.deleteSurveyById(houseId);
                                Log.e(TAG, "sendOfflineLocations: successfully deleted locationId: " + houseId);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (AUtils.isInternetAvailable()) {
            txtNoConnection.setVisibility(View.GONE);
        } else {
            txtNoConnection.setVisibility(View.VISIBLE);
            pagerAdapter = new SurPagerAdapter(getSupportFragmentManager(),getLifecycle(),null);
            viewPager.setAdapter(pagerAdapter);
            dotsIndicator.attachTo(viewPager);

        }
    }

}