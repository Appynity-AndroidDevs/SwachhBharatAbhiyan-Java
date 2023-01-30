package com.appynitty.swachbharatabhiyanlibrary.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EmpViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public EmpViewModelFactory( Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        return (T) new OfflineSurveyVM(application);
    }
}
