package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.util.Log;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.InPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OutPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.PunchWebService;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DumpEmpAttendanceRepo {

    private static final String TAG = "DumpEmpAttendanceRepo";
    private static final DumpEmpAttendanceRepo instance = new DumpEmpAttendanceRepo();

    public static DumpEmpAttendanceRepo getInstance() {
        return instance;
    }

    public void setDumpEmpAttendanceIn(String refId, IDumpEmpAttendanceResponse iDumpEmpAttendanceResponse) {

        InPunchPojo inPunchBody = new InPunchPojo();
        inPunchBody.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, null));
        inPunchBody.setStartLat(Prefs.getString(AUtils.LAT, null));
        inPunchBody.setStartLong(Prefs.getString(AUtils.LONG, null));
        inPunchBody.setDaDate(AUtils.getLocalDate());
        inPunchBody.setStartTime(AUtils.getServerTime());
        inPunchBody.setVehicleNumber("");
        inPunchBody.setVtId("");
        inPunchBody.setReferanceId(refId);
        inPunchBody.setEmpType(Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null));

        PunchWebService punchWebService = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);
        Call<ResultPojo> punchInCall = punchWebService.saveInPunchDetails(Prefs.getString(AUtils.APP_ID, null), AUtils.CONTENT_TYPE,
                AUtils.getBatteryStatus(),
                inPunchBody);

        punchInCall.enqueue(new Callback<ResultPojo>() {
            @Override
            public void onResponse(Call<ResultPojo> call, Response<ResultPojo> response) {
                Log.e(TAG, "onResponse: " + response.body().getMessage());
                iDumpEmpAttendanceResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ResultPojo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iDumpEmpAttendanceResponse.onFailure(t);
            }
        });
    }

    public void setDumpEmpAttendanceOut(String refId, IDumpEmpAttendanceResponse iDumpEmpAttendanceResponse) {

        OutPunchPojo outPunchBody = new OutPunchPojo();
        outPunchBody.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, null));
        outPunchBody.setEndLat(Prefs.getString(AUtils.LAT, null));
        outPunchBody.setEndLong(Prefs.getString(AUtils.LONG, null));
        outPunchBody.setEndTime(AUtils.getServerTime());
        outPunchBody.setDaDate(AUtils.getLocalDate());
        outPunchBody.setVehicleNumber("");
        outPunchBody.setVtId("");
        outPunchBody.setEmpType(Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null));
        outPunchBody.setReferanceId(refId);

        PunchWebService punchWebService = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);
        Call<ResultPojo> punchOutCall = punchWebService.saveOutPunchDetails(Prefs.getString(AUtils.APP_ID, null),
                AUtils.CONTENT_TYPE, AUtils.getBatteryStatus(),
                outPunchBody);

        punchOutCall.enqueue(new Callback<ResultPojo>() {
            @Override
            public void onResponse(Call<ResultPojo> call, Response<ResultPojo> response) {
                Log.e(TAG, "onResponse: " + response.body().getMessage());
                iDumpEmpAttendanceResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ResultPojo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iDumpEmpAttendanceResponse.onFailure(t);
            }
        });
    }

    public interface IDumpEmpAttendanceResponse {
        void onResponse(ResultPojo resultPojo);

        void onFailure(Throwable throwable);
    }
}
