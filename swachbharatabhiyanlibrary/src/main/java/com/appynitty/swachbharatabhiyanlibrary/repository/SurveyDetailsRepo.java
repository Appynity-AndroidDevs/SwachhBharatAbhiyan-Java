package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.util.Log;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.SurveyDetailsWebService;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyDetailsRepo {
    private static final String TAG = "SurveyDetailsRepo";
    private static final SurveyDetailsRepo instance = new SurveyDetailsRepo();
    String appId = Prefs.getString(AUtils.APP_ID, null);
    Gson gson = new Gson();
    List<SurveyDetailsRequestPojo> requestPojo = new ArrayList<>();

    public static SurveyDetailsRepo getInstance() {
        return instance;
    }

    public void addSurveyDetails( ISurveyDetailsResponse iSurveyDetailsResponse){
        SurveyDetailsRequestPojo requestPojo = new SurveyDetailsRequestPojo();
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

        SurveyDetailsWebService surveyDetailsWebService = Connection.createService(SurveyDetailsWebService.class, AUtils.SURVEY_SERVER_URL);
        Call<List<SurveyDetailsResultPojo>> detailsResultPojoCall = surveyDetailsWebService.saveSurveyDetails(AUtils.CONTENT_TYPE, appId, requestPojo);

        detailsResultPojoCall.enqueue(new Callback<List<SurveyDetailsResultPojo>>() {
            @Override
            public void onResponse(Call<List<SurveyDetailsResultPojo>> call, Response<List<SurveyDetailsResultPojo>> response) {
                Log.e(TAG, "onResponse: " + response.body().toString());
                if (response.code() == 200) {
                    iSurveyDetailsResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body());
                }else if (response.code() == 500){
                    iSurveyDetailsResponse.onResponse(response.body());
                    Log.e(TAG, "onResponse: " + response.body());
                }

            }

            @Override
            public void onFailure(Call<List<SurveyDetailsResultPojo>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iSurveyDetailsResponse.onFailure(t);
            }
        });
    }



    public interface ISurveyDetailsResponse {
        void onResponse(List<SurveyDetailsResultPojo>  surveyDetailsResponse);

        void onFailure(Throwable t);
    }


}
