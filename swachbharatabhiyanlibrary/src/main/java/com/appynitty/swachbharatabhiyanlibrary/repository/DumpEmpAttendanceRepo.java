package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.util.Log;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.pojos.DumpEmpPunchPojo;
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

        DumpEmpPunchPojo dumpEmpPunchInBody = new DumpEmpPunchPojo(Prefs.getString(AUtils.PREFS.USER_ID, null), Prefs.getString(AUtils.LAT, null),
                Prefs.getString(AUtils.LONG, null), AUtils.getServerTime(), AUtils.getLocalDate(), "", "", Prefs.getString(AUtils.EMP_TYPE, null), refId);

        PunchWebService punchWebService = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);
        Call<DumpEmpPunchPojo> punchCall = punchWebService.saveDumpEmpAttendanceIn(Prefs.getString(AUtils.APP_ID, null),
                String.valueOf(AUtils.getBatteryStatus()),
                AUtils.CONTENT_TYPE, dumpEmpPunchInBody);

        punchCall.enqueue(new Callback<DumpEmpPunchPojo>() {
            @Override
            public void onResponse(Call<DumpEmpPunchPojo> call, Response<DumpEmpPunchPojo> response) {
                Log.e(TAG, "onResponse: " + response.body().getMessage());
                iDumpEmpAttendanceResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<DumpEmpPunchPojo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iDumpEmpAttendanceResponse.onFailure(t);
            }
        });
    }

    public void setDumpEmpAttendanceOut(String refId, IDumpEmpAttendanceResponse iDumpEmpAttendanceResponse) {

        DumpEmpPunchPojo dumpEmpPunchOutBody = new DumpEmpPunchPojo(
                Prefs.getString(AUtils.PREFS.USER_ID, null),
                Prefs.getString(AUtils.LAT, null),
                Prefs.getString(AUtils.LONG, null),
                AUtils.getServerTime(),
                AUtils.getLocalDate(),
                "", "",
                Prefs.getString(AUtils.EMP_TYPE, null),
                refId, "Perform duty off!");

        PunchWebService punchWebService = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);
        Call<DumpEmpPunchPojo> punchCall = punchWebService.saveDumpEmpAttendanceOut(Prefs.getString(AUtils.APP_ID, null),
                String.valueOf(AUtils.getBatteryStatus()),
                AUtils.CONTENT_TYPE, dumpEmpPunchOutBody);

        punchCall.enqueue(new Callback<DumpEmpPunchPojo>() {
            @Override
            public void onResponse(Call<DumpEmpPunchPojo> call, Response<DumpEmpPunchPojo> response) {
                Log.e(TAG, "onResponse: " + response.body().getMessage());
                iDumpEmpAttendanceResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<DumpEmpPunchPojo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iDumpEmpAttendanceResponse.onFailure(t);
            }
        });
    }

    public interface IDumpEmpAttendanceResponse {
        void onResponse(DumpEmpPunchPojo attendanceResponse);

        void onFailure(Throwable throwable);
    }
}
