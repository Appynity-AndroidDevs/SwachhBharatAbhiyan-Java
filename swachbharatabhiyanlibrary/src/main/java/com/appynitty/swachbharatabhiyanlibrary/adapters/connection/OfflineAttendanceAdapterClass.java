package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.entity.UserDailyAttendanceEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.AttendancePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.AttendanceResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineAttendanceRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.PunchWebService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ayan Dey on 9/10/19.
 */
public class OfflineAttendanceAdapterClass {

    private OfflineAttendanceListener offlineAttendanceListener;
    private final Context mContext;
    private final SyncOfflineAttendanceRepository syncOfflineAttendanceRepository;
    private String offset = "0";
    private final List<AttendancePojo> syncOfflineList;
    String auth_token = "Bearer " + Prefs.getString(AUtils.BEARER_TOKEN, null);

    public OfflineAttendanceAdapterClass(Context context) {
        this.mContext = context;
        syncOfflineAttendanceRepository = new SyncOfflineAttendanceRepository(mContext);
        syncOfflineList = new ArrayList<>();
    }

    public void setSyncOfflineListener(OfflineAttendanceListener syncOfflineListener) {
        this.offlineAttendanceListener = syncOfflineListener;
    }

    /**
     * Add CurrentTime
     */
    public void SyncOfflineData() {
        if (!AUtils.isSyncOfflineAttendanceDataRequestEnable) {

            setOfflineData();

            if (syncOfflineList.size() > 0) {

                AUtils.isSyncOfflineAttendanceDataRequestEnable = true;

                PunchWebService service = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);

                service.saveOfflineAttendanceDetails(auth_token, Prefs.getString(AUtils.APP_ID, ""),
                                AUtils.CONTENT_TYPE, AUtils.getServerDateTimeWithMilliesSecond(), Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null), syncOfflineList)
                        .enqueue(new Callback<List<AttendanceResponsePojo>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<AttendanceResponsePojo>> call,
                                                   @NonNull Response<List<AttendanceResponsePojo>> response) {

                                if (response.code() == 200) {
                                    onResponseReceived(response.body());
                                    SyncOfflineData();
                                } else {
                                    Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + response.code());
                                    AUtils.isSyncOfflineAttendanceDataRequestEnable = false;
                                    if (!AUtils.isNull(offlineAttendanceListener))
                                        offlineAttendanceListener.onFailureCallback();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<AttendanceResponsePojo>> call, @NonNull Throwable t) {
                                Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + t.getMessage());
                                AUtils.isSyncOfflineAttendanceDataRequestEnable = false;
                                if (!AUtils.isNull(offlineAttendanceListener))
                                    offlineAttendanceListener.onErrorCallback();
                            }
                        });
            } else {
                if (!AUtils.isNull(offlineAttendanceListener))
                    offlineAttendanceListener.onSuccessCallback();
            }
        } else {
            if (!AUtils.isNull(offlineAttendanceListener))
                offlineAttendanceListener.onFailureCallback();
        }

    }

    private void onResponseReceived(List<AttendanceResponsePojo> results) {

        if (!AUtils.isNull(results) && results.size() > 0) {

            for (AttendanceResponsePojo result : results) {

                if (result.getStatus().equals(AUtils.STATUS_SUCCESS)) {

                    if (Integer.parseInt(result.getID()) != 0) {
                        int deleteCount = 0;
                        if (result.getIsInSync().equals("true")
                                && result.getIsOutSync().equals("true")) {
                            deleteCount = syncOfflineAttendanceRepository.deleteAttendanceSyncTableData(result.getID());

                            if (deleteCount == 0) {
                                offset = String.valueOf(Integer.parseInt(offset) + 1);
                            }
                        } else if (result.getIsInSync().equals("true")) {
                            syncOfflineAttendanceRepository.updateIsInSync(result.getID());
                        }
                    }
                    for (int i = 0; i < syncOfflineList.size(); i++) {
                        if (syncOfflineList.get(i).getOfflineID().equals(result.getID())) {
                            syncOfflineList.remove(i);
                            break;
                        }
                    }
                } else
                    offset = String.valueOf(Integer.parseInt(offset) + 1);
            }
        }

        AUtils.isSyncOfflineAttendanceDataRequestEnable = false;
    }

    private void setOfflineData() {
        syncOfflineList.clear();
        List<UserDailyAttendanceEntity> offlineEntities = syncOfflineAttendanceRepository.fetchOfflineAttendanceData(offset);
        for (UserDailyAttendanceEntity entity : offlineEntities) {
            if ((entity.getAttendanceInSync().equals("1") &&
                    !AUtils.isNullString(entity.getAttendanceOutDate())) ||
                    (entity.getAttendanceInSync().equals("0"))) {
                AttendancePojo offlinePojo;
                Type typeToken = new TypeToken<AttendancePojo>() {
                }.getType();
                offlinePojo = new Gson().fromJson(entity.getAttendanceData(), typeToken);
                offlinePojo.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, ""));
                offlinePojo.setOfflineID(String.valueOf(entity.getAttendanceId()));

                syncOfflineList.add(offlinePojo);
            }
        }
    }

    public interface OfflineAttendanceListener {
        void onSuccessCallback();

        void onFailureCallback();

        void onErrorCallback();
    }
}
