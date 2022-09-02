package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.util.Log;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.InPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OutPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.PunchWebService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceOnlineAdapter {
    private static final String TAG = "AttendanceOnlineAdapter";
    private static final AttendanceOnlineAdapter instance = new AttendanceOnlineAdapter();
    private AttendanceResponseListener listener;
    private static Gson gson = new Gson();

    public static AttendanceOnlineAdapter getInstance() {
        return instance;
    }

    public void setAttendanceIn(AttendanceResponseListener listener) {

        InPunchPojo inPunchPojo = new InPunchPojo();

        inPunchPojo.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, null));
        inPunchPojo.setEmpType(Prefs.getString(AUtils.EMP_TYPE, null));
        inPunchPojo.setVtId(Prefs.getString(AUtils.VEHICLE_ID, "0"));
        inPunchPojo.setVehicleNumber(Prefs.getString(AUtils.VEHICLE_NO, null));
        inPunchPojo.setStartLat(Prefs.getString(AUtils.LAT, null));
        inPunchPojo.setStartLong(Prefs.getString(AUtils.LONG, null));
        inPunchPojo.setStartTime(AUtils.getServerTime());
        inPunchPojo.setDaDate(AUtils.getServerDate());

        Type type = new TypeToken<InPunchPojo>() {
        }.getType();
        Prefs.putString(AUtils.PREFS.IN_PUNCH_POJO, gson.toJson(inPunchPojo, type));

        PunchWebService service = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);
        service.saveInPunchDetails(Prefs.getString(AUtils.APP_ID, ""),
                AUtils.CONTENT_TYPE, AUtils.getBatteryStatus(), inPunchPojo
        ).enqueue(new Callback<ResultPojo>() {
            @Override
            public void onResponse(Call<ResultPojo> call, Response<ResultPojo> response) {
                listener.onResponse(response.body());
                Log.e(TAG, "onResponse: " + response.body().getMessageMar());
            }

            @Override
            public void onFailure(Call<ResultPojo> call, Throwable t) {
                listener.onFailure(t);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void setAttendanceOut(AttendanceResponseListener listener) {

        OutPunchPojo outPunchPojo = new OutPunchPojo();
        outPunchPojo.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, null));
        outPunchPojo.setEmpType(Prefs.getString(AUtils.EMP_TYPE, null));
        outPunchPojo.setVtId(Prefs.getString(AUtils.VEHICLE_ID, "0"));
        outPunchPojo.setVehicleNumber(Prefs.getString(AUtils.VEHICLE_NO, null));
        outPunchPojo.setEndLat(Prefs.getString(AUtils.LAT, null));
        outPunchPojo.setEndLong(Prefs.getString(AUtils.LONG, null));
        outPunchPojo.setEndTime(AUtils.getServerTime());
        outPunchPojo.setDaendDate(AUtils.getServerDate());

        PunchWebService service = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);
        service.saveOutPunchDetails(Prefs.getString(AUtils.APP_ID, ""),
                        AUtils.CONTENT_TYPE, AUtils.getBatteryStatus(), outPunchPojo)
                .enqueue(new Callback<ResultPojo>() {
                    @Override
                    public void onResponse(Call<ResultPojo> call, Response<ResultPojo> response) {
                        listener.onResponse(response.body());
                        Log.e(TAG, "onResponse: " + response.body());
                    }

                    @Override
                    public void onFailure(Call<ResultPojo> call, Throwable t) {
                        listener.onFailure(t);
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    public interface AttendanceResponseListener {
        void onResponse(ResultPojo resultPojo);

        void onFailure(Throwable throwable);
    }
}
