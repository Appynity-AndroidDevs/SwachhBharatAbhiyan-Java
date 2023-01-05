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

    public void addSurveyDetails(String refId, ISurveyDetailsResponse iSurveyDetailsResponse){
        SurveyDetailsRequestPojo requestPojo = new SurveyDetailsRequestPojo();
        requestPojo.setReferanceId(refId);
        requestPojo.setHouseLat(Prefs.getString(AUtils.LAT,"0"));
        requestPojo.setHouseLong(Prefs.getString(AUtils.LONG,"0"));
        requestPojo.setName(Prefs.getString(AUtils.SUR_NAME,"0"));
        requestPojo.setMobileNumber(" ");
        requestPojo.setDateOfBirth(" ");
        requestPojo.setAge(" ");
        requestPojo.setGender(" ");
        requestPojo.setBloodGroup(" ");
        requestPojo.setQualification(" ");
        requestPojo.setOccupation(" ");
        requestPojo.setMaritalStatus(" ");
        requestPojo.setMarriageDate(" ");
        requestPojo.setLivingStatus(" ");
        requestPojo.setTotalAdults(" ");
        requestPojo.setTotalChildren(" ");
        requestPojo.setTotalSrCitizen(" ");
        requestPojo.setTotalMember(" ");
        requestPojo.setWillingStart(" ");
        requestPojo.setResourcesAvailable(" ");
        requestPojo.setResourcesAvailable(" ");
        requestPojo.setMemberJobOtherCity(" ");
        requestPojo.setNoOfVehicle(" ");
        requestPojo.setTwoWheelerQty(" ");
        requestPojo.setFourWheelerQty(" ");
        requestPojo.setNoPeopleVote(" ");
        requestPojo.setSocialMedia(" ");
        requestPojo.setOnlineShopping("");
        requestPojo.setPaymentModePrefer(" ");
        requestPojo.setOnlinePayApp(" ");
        requestPojo.setInsurance(" ");
        requestPojo.setUnderInsurer(" ");
        requestPojo.setAyushmanBeneficiary(" ");
        requestPojo.setBoosterShot(" ");
        requestPojo.setMemberDivyang(" ");
        requestPojo.setCreateUserId(" ");
        requestPojo.setUpdateUserId(" ");

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
