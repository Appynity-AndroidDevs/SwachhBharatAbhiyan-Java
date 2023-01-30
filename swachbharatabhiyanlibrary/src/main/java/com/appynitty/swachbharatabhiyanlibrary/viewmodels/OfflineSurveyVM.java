package com.appynitty.swachbharatabhiyanlibrary.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.swachbharatabhiyanlibrary.entity.OfflineSurvey;
import com.appynitty.swachbharatabhiyanlibrary.repository.OfflineSurveyRepo;

import java.util.List;

public class OfflineSurveyVM extends AndroidViewModel {
    private OfflineSurveyRepo offlineSurveyRepo;
    private LiveData<List<OfflineSurvey>> allSurveyLiveData;
    private MutableLiveData<Integer> offlineSurveyCount = new MutableLiveData<>();

    public OfflineSurveyVM(@NonNull Application application) {
        super(application);
        offlineSurveyRepo = new OfflineSurveyRepo(application);
        allSurveyLiveData = offlineSurveyRepo.getAllSurvey();
    }


    public void insert(OfflineSurvey offlineSurvey) {
        offlineSurveyRepo.insertSurvey(offlineSurvey);
    }

    public void update(OfflineSurvey offlineSurvey) {
        offlineSurveyRepo.updateSurvey(offlineSurvey);
    }

    public void delete(OfflineSurvey offlineSurvey) {
        offlineSurveyRepo.deleteSurvey(offlineSurvey);
    }

    public void deleteAllSurvey() {
        offlineSurveyRepo.deleteAllSurvey();
    }

    public LiveData<List<OfflineSurvey>> getAllSurveyLiveData() {
        return allSurveyLiveData;
    }

    public MutableLiveData<Integer> getOfflineSurveyCount() {
        return offlineSurveyCount;
    }
}
