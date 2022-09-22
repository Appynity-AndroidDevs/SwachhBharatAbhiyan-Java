package com.appynitty.swachbharatabhiyanlibrary.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.CtptEmpAttendanceRepo;


/***
 * crated by Rahul Rokade   09/21/2022
 *
 * */
public class CtptEmpAttendanceVM extends ViewModel {

    private static final String TAG = "CtptEmpAttendanceVM";
    CtptEmpAttendanceRepo ctptEmpAttendanceRepo = CtptEmpAttendanceRepo.getInstance();
    private final MutableLiveData<ResultPojo> ctptEmpCheckInMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ResultPojo> ctptEmpCheckOutMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> ctptEmpAttendanceError = new MutableLiveData<>();
    private final MutableLiveData<Integer> progressStatusLiveData = new MutableLiveData<>();

    public void setDumpEmpAttendanceIn(/*String refId*/) {
        progressStatusLiveData.setValue(View.VISIBLE);
        ctptEmpAttendanceRepo.setCtptEmpAttendanceIn(/*refId,*/ new CtptEmpAttendanceRepo.ICtptEmpAttendanceResponse() {
            @Override
            public void onResponse(ResultPojo attendanceResponse) {
                progressStatusLiveData.setValue(View.GONE);
                Log.e(TAG, "onResponse: " + attendanceResponse.getMessage());
                ctptEmpCheckInMutableLiveData.setValue(attendanceResponse);
            }

            @Override
            public void onFailure(Throwable throwable) {
                progressStatusLiveData.setValue(View.GONE);
                Log.e(TAG, "onFailure: " + throwable.getMessage());
                ctptEmpAttendanceError.setValue(throwable);
            }
        });
    }

    public void setDumpEmpAttendanceOut(/*String refId*/) {
        progressStatusLiveData.setValue(View.VISIBLE);
        ctptEmpAttendanceRepo.setCtptEmpAttendanceOut(/*refId,*/ new CtptEmpAttendanceRepo.ICtptEmpAttendanceResponse() {
            @Override
            public void onResponse(ResultPojo attendanceResponse) {
                progressStatusLiveData.setValue(View.GONE);
                Log.e(TAG, "onResponse: " + attendanceResponse.getMessage());
                ctptEmpCheckOutMutableLiveData.setValue(attendanceResponse);
            }

            @Override
            public void onFailure(Throwable throwable) {
                progressStatusLiveData.setValue(View.GONE);
                Log.e(TAG, "onFailure: " + throwable.getMessage());
                ctptEmpAttendanceError.setValue(throwable);
            }
        });
    }

    public MutableLiveData<ResultPojo> getCtptEmpCheckInLiveData() {
        return ctptEmpCheckInMutableLiveData;
    }

    public MutableLiveData<ResultPojo> getCtptEmpCheckOutLiveData() {
        return ctptEmpCheckOutMutableLiveData;
    }

    public MutableLiveData<Throwable> getCtptEmpAttendanceError() {
        return ctptEmpAttendanceError;
    }

    public MutableLiveData<Integer> getProgressStatusLiveData() {
        return progressStatusLiveData;
    }
}
