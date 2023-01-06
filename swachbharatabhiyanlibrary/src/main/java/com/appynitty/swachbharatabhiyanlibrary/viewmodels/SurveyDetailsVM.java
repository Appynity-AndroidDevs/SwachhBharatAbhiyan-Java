package com.appynitty.swachbharatabhiyanlibrary.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SurveyDetailsRepo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SurveyDetailsVM extends ViewModel {
    private static final String TAG = "SurveyDetailsVM";
    SurveyDetailsRepo surveyDetailsRepo = SurveyDetailsRepo.getInstance();
    public MutableLiveData<String> surveyName = new MutableLiveData<>(); //liveData with dataBinding
    public MutableLiveData<String> surveyMobile = new MutableLiveData<>(); //liveData with dataBinding
    private final MutableLiveData<List<SurveyDetailsResponsePojo>> surveyDetailsMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> surveyDetailsError = new MutableLiveData<>();
    private final MutableLiveData<Integer> progressStatusLiveData = new MutableLiveData<>();

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





    public MutableLiveData<List<SurveyDetailsResponsePojo>> getSurveyDetailsMutableLiveData() {
        return surveyDetailsMutableLiveData;
    }

    public MutableLiveData<Throwable> getSurveyDetailsError() {
        return surveyDetailsError;
    }

    public MutableLiveData<Integer> getProgressStatusLiveData() {
        return progressStatusLiveData;
    }
}
