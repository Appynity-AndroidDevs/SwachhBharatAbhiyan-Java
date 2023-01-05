package com.appynitty.swachbharatabhiyanlibrary.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SurveyDetailsRepo;

public class SurveyDetailsVM extends ViewModel {
    private static final String TAG = "SurveyDetailsVM";
    SurveyDetailsRepo surveyDetailsRepo = SurveyDetailsRepo.getInstance();
    private final MutableLiveData<SurveyDetailsResultPojo> surveyDetailsMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> surveyDetailsError = new MutableLiveData<>();
    private final MutableLiveData<Integer> progressStatusLiveData = new MutableLiveData<>();







    public MutableLiveData<SurveyDetailsResultPojo> getSurveyDetailsMutableLiveData() {
        return surveyDetailsMutableLiveData;
    }

    public MutableLiveData<Throwable> getSurveyDetailsError() {
        return surveyDetailsError;
    }

    public MutableLiveData<Integer> getProgressStatusLiveData() {
        return progressStatusLiveData;
    }
}
