package com.appynitty.swachbharatabhiyanlibrary.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.GetSurveyResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SurveyDetailsRepo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SurveyDetailsVM extends ViewModel {
    private static final String TAG = "SurveyDetailsVM";
    SurveyDetailsRepo surveyDetailsRepo = SurveyDetailsRepo.getInstance();
    private final MutableLiveData<List<SurveyDetailsResponsePojo>> surveyDetailsMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<GetSurveyResponsePojo>> getSurveyMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> surveyDetailsError = new MutableLiveData<>();
    private final MutableLiveData<Integer> progressStatusLiveData = new MutableLiveData<>();

    //getDetails
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> mobileNumber = new MutableLiveData<>();
    public MutableLiveData<Integer> svId = new MutableLiveData<>();
    public MutableLiveData<String> ReferanceId = new MutableLiveData<>();
    public MutableLiveData<Integer> age = new MutableLiveData<>();
    public MutableLiveData<String> dateOfBirth = new MutableLiveData<>();
    public MutableLiveData<String> gender = new MutableLiveData<>();
    public MutableLiveData<String> bloodGroup = new MutableLiveData<>();
    public MutableLiveData<String> qualification = new MutableLiveData<>();
    public MutableLiveData<String> occupation = new MutableLiveData<>();
    public MutableLiveData<String> maritalStatus = new MutableLiveData<>();
    public MutableLiveData<String> marriageDate = new MutableLiveData<>();
    public MutableLiveData<String> livingStatus = new MutableLiveData<>();
    public MutableLiveData<Integer> totalMember = new MutableLiveData<>();
    public MutableLiveData<Boolean> willingStart = new MutableLiveData<>();
    public MutableLiveData<String> resourcesAvailable = new MutableLiveData<>();
    public MutableLiveData<Boolean> memberJobOtherCity = new MutableLiveData<>();
    public MutableLiveData<Integer> noOfVehicle = new MutableLiveData<>();
    public MutableLiveData<String> vehicleType = new MutableLiveData<>();

    public void surveyFormApi(){
        progressStatusLiveData.setValue(View.VISIBLE);
        surveyDetailsRepo.addSurveyDetails(new SurveyDetailsRepo.ISurveyDetailsResponse() {
            @Override
            public void onResponse(List<SurveyDetailsResponsePojo> surveyDetailsResponse) {
                progressStatusLiveData.setValue(View.GONE);
                Log.e(TAG, "onResponse: " + surveyDetailsResponse.get(0).getMessage());
                surveyDetailsMutableLiveData.setValue(surveyDetailsResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                progressStatusLiveData.setValue(View.GONE);
                Log.e(TAG, "onFailure: " + t.getMessage());
                surveyDetailsError.setValue(t);

            }
        });
    }


    public void getSurveyResponseApi(){
        progressStatusLiveData.setValue(View.VISIBLE);
        surveyDetailsRepo.getSurveyDetails(Prefs.getString(AUtils.PREFS.SUR_REFERENCE_ID, ""), new SurveyDetailsRepo.IGetSurveyResponse() {
            @Override
            public void onResponse(List<GetSurveyResponsePojo> getSurveyResponsePojo) {
                progressStatusLiveData.setValue(View.GONE);
                if (getSurveyResponsePojo != null){
                    Log.e(TAG, "onResponse: " + getSurveyResponsePojo);
                    getSurveyMutableLiveData.setValue(getSurveyResponsePojo);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressStatusLiveData.setValue(View.GONE);
                Log.e(TAG, "onFailure: " + t.getMessage());
                surveyDetailsError.setValue(t);
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

        }
    }


    public MutableLiveData<List<SurveyDetailsResponsePojo>> SaveSurveyDetailsMutableLiveData() {
        return surveyDetailsMutableLiveData;
    }

    public MutableLiveData<List<GetSurveyResponsePojo>> getSurveyMutableLiveData() {
        return getSurveyMutableLiveData;
    }

    public MutableLiveData<Throwable> getSurveyDetailsError() {
        return surveyDetailsError;
    }

    public MutableLiveData<Integer> getProgressStatusLiveData() {
        return progressStatusLiveData;
    }
}
