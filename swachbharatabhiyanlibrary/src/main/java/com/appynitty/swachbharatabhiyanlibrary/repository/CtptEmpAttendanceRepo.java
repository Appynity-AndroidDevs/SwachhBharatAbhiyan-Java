package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.util.Log;
import android.widget.Toast;

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

/***
 * crated by Rahul Rokade   09/21/2022
 *
 * */
public class CtptEmpAttendanceRepo {

    private static final String TAG = "CtptEmpAttendanceRepo";
    private static final CtptEmpAttendanceRepo instance = new CtptEmpAttendanceRepo();

    public static CtptEmpAttendanceRepo getInstance() {
        return instance;
    }

    public void setCtptEmpAttendanceIn(/*String refId,*/ ICtptEmpAttendanceResponse iCtptEmpAttendanceResponse) {

        InPunchPojo inPunchBody = new InPunchPojo();
        inPunchBody.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, null));
        inPunchBody.setStartLat(Prefs.getString(AUtils.LAT, null));
        inPunchBody.setStartLong(Prefs.getString(AUtils.LONG, null));
        inPunchBody.setDaDate(AUtils.getLocalDate());
        inPunchBody.setStartTime(AUtils.getServerTime());
        inPunchBody.setVehicleNumber("1");
        inPunchBody.setVtId("1");
        /*inPunchBody.setReferanceId(refId);*/
        inPunchBody.setEmpType(Prefs.getString(AUtils.EMP_TYPE, null));

        if (inPunchBody.getEmpType() != null &&
                inPunchBody.getUserId() != null &&
                inPunchBody.getStartLat() != null &&
                inPunchBody.getStartLong() != null &&
                inPunchBody.getDaDate() != null &&
                inPunchBody.getStartTime() != null &&
                inPunchBody.getVehicleNumber() != null &&
                inPunchBody.getVtId() != null)   {

            PunchWebService punchWebService = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);
            Call<ResultPojo> punchInCall = punchWebService.saveInPunchDetails(Prefs.getString(AUtils.APP_ID, null), AUtils.CONTENT_TYPE,
                    AUtils.getBatteryStatus(),
                    inPunchBody);

            punchInCall.enqueue(new Callback<ResultPojo>() {
                @Override
                public void onResponse(Call<ResultPojo> call, Response<ResultPojo> response) {

                    if (response.body() != null){
                        Log.e(TAG, "onResponse: " + response.body().getMessage());
                        iCtptEmpAttendanceResponse.onResponse(response.body());
                    }

                }

                @Override
                public void onFailure(Call<ResultPojo> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    iCtptEmpAttendanceResponse.onFailure(t);
                }
            });
        }else {

        }

        /*PunchWebService punchWebService = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);
        Call<ResultPojo> punchInCall = punchWebService.saveInPunchDetails(Prefs.getString(AUtils.APP_ID, null), AUtils.CONTENT_TYPE,
                AUtils.getBatteryStatus(),
                inPunchBody);

        punchInCall.enqueue(new Callback<ResultPojo>() {
            @Override
            public void onResponse(Call<ResultPojo> call, Response<ResultPojo> response) {

                if (response != null){
                    Log.e(TAG, "onResponse: " + response.body().getMessage());
                    iCtptEmpAttendanceResponse.onResponse(response.body());
                }

            }

            @Override
            public void onFailure(Call<ResultPojo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iCtptEmpAttendanceResponse.onFailure(t);
            }
        });*/
    }

    public void setCtptEmpAttendanceOut(/*String refId, */ICtptEmpAttendanceResponse iCtptEmpAttendanceResponse) {

        OutPunchPojo outPunchBody = new OutPunchPojo();
        outPunchBody.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, null));
        outPunchBody.setEndLat(Prefs.getString(AUtils.LAT, null));
        outPunchBody.setEndLong(Prefs.getString(AUtils.LONG, null));
        outPunchBody.setEndTime(AUtils.getServerTime());
        outPunchBody.setDaDate(AUtils.getLocalDate());
        outPunchBody.setVehicleNumber("1");
        outPunchBody.setVtId("1");
        outPunchBody.setEmpType(Prefs.getString(AUtils.EMP_TYPE, null));
        /*outPunchBody.setReferanceId(refId);*/

        if (outPunchBody.getEmpType() != null &&
                outPunchBody.getUserId() != null &&
                outPunchBody.getEndLat() != null &&
                outPunchBody.getEndLong() != null &&
                outPunchBody.getDaDate() != null &&
                outPunchBody.getEndTime() != null &&
                outPunchBody.getVehicleNumber() != null &&
                outPunchBody.getVtId() != null)   {

            PunchWebService punchWebService = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);
            Call<ResultPojo> punchOutCall = punchWebService.saveOutPunchDetails(Prefs.getString(AUtils.APP_ID, null),
                    AUtils.CONTENT_TYPE, AUtils.getBatteryStatus(),
                    outPunchBody);

            punchOutCall.enqueue(new Callback<ResultPojo>() {
                @Override
                public void onResponse(Call<ResultPojo> call, Response<ResultPojo> response) {
                    Log.e(TAG, "onResponse: " + response.body().getMessage());
                    iCtptEmpAttendanceResponse.onResponse(response.body());
                }

                @Override
                public void onFailure(Call<ResultPojo> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    iCtptEmpAttendanceResponse.onFailure(t);
                }
            });
        }

       /* PunchWebService punchWebService = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);
        Call<ResultPojo> punchOutCall = punchWebService.saveOutPunchDetails(Prefs.getString(AUtils.APP_ID, null),
                AUtils.CONTENT_TYPE, AUtils.getBatteryStatus(),
                outPunchBody);

        punchOutCall.enqueue(new Callback<ResultPojo>() {
            @Override
            public void onResponse(Call<ResultPojo> call, Response<ResultPojo> response) {
                Log.e(TAG, "onResponse: " + response.body().getMessage());
                iCtptEmpAttendanceResponse.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ResultPojo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                iCtptEmpAttendanceResponse.onFailure(t);
            }
        });*/
    }

    public interface ICtptEmpAttendanceResponse {
        void onResponse(ResultPojo resultPojo);

        void onFailure(Throwable throwable);
    }
}
