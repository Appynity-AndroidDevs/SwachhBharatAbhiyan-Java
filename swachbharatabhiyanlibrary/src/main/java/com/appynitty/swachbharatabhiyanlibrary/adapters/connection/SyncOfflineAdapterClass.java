package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.entity.SyncOfflineEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SyncOfflinePojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.GarbageCollectionWebService;
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
public class SyncOfflineAdapterClass {

    private SyncOfflineListener syncOfflineListener;
    private AreaWarningListener areaWarningListener;
    private final Context mContext;
    private final SyncOfflineRepository syncOfflineRepository;
    private String offset = "0";
    private final List<SyncOfflinePojo> syncOfflineList;
    private final static String TAG = "SyncOfflineAdapterClass";


    public SyncOfflineAdapterClass(Context context) {
        this.mContext = context;
        syncOfflineRepository = new SyncOfflineRepository(mContext);
        syncOfflineList = new ArrayList<>();
    }

    public void setSyncOfflineListener(SyncOfflineListener syncOfflineListener) {
        this.syncOfflineListener = syncOfflineListener;
    }

    public void setAreaErrorListener(AreaWarningListener areaWarningListener) {
        this.areaWarningListener = areaWarningListener;
    }

    public void SyncOfflineData() {

        Prefs.putBoolean(AUtils.isConnectionTimeOut, false);

        if (!AUtils.isSyncOfflineScanDataRequestEnable) {

            setOfflineData();

            if (syncOfflineList.size() > 0) {

                AUtils.isSyncOfflineScanDataRequestEnable = true;
                Prefs.putBoolean(AUtils.isSyncingOn, true);

                GarbageCollectionWebService service = Connection.createService(GarbageCollectionWebService.class, AUtils.SERVER_URL);

                service.syncOfflineData(Prefs.getString(AUtils.APP_ID, ""),
                                Prefs.getString(AUtils.PREFS.USER_TYPE_ID, ""),
                                AUtils.getBatteryStatus(), AUtils.CONTENT_TYPE, syncOfflineList)
                        .enqueue(new Callback<List<OfflineGcResultPojo>>() {

                            @Override
                            public void onResponse(Call<List<OfflineGcResultPojo>> call,
                                                   Response<List<OfflineGcResultPojo>> response) {


                                if (response.code() == 200) {

                                    onResponseReceived(response.body());
                                    SyncOfflineData();

                                    Prefs.putBoolean(AUtils.isConnectionTimeOut, false);

                                } else {
                                    Log.d(TAG, "onResponse: " + response);
                                    AUtils.warning(mContext, mContext.getResources().getString(R.string.please_sync_again), Toast.LENGTH_SHORT);
                                    Log.i("SyncOfflineClass", "onFailureCallback: Response Code-" + response.code());
                                    Log.i("SyncOfflineClass", "onFailureCallback: Response Code-" + response.message());
                                    AUtils.isSyncOfflineScanDataRequestEnable = false;

                                    syncOfflineListener.onFailureCallback();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<OfflineGcResultPojo>> call, Throwable t) {
                                Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + t.getMessage());
                                AUtils.warning(mContext, mContext.getResources().getString(R.string.please_sync_again), Toast.LENGTH_LONG);
                                AUtils.isSyncOfflineScanDataRequestEnable = false;
                                Prefs.putBoolean(AUtils.isSyncingOn, false);
                                syncOfflineListener.onErrorCallback();
                                Prefs.putBoolean(AUtils.isConnectionTimeOut, true);
                                Log.i("RESPONSE_CODE", "onResponse: " + t.getMessage());

                            }

                        });
            } else {

                if (syncOfflineListener != null) {
                    syncOfflineListener.onSuccessCallback();
                }

            }
        } else {
            if (syncOfflineListener != null)
                syncOfflineListener.onFailureCallback();
        }

    }

    private void onResponseReceived(List<OfflineGcResultPojo> results) {

        Log.d(TAG, "onResponseReceived: " + results);
        Log.e(SyncOfflineAdapterClass.class.getSimpleName(), results.get(0).getMessage());


        if (!AUtils.isNull(results) && results.size() > 0) {

            for (OfflineGcResultPojo result : results) {

                if (result.getStatus() != null) {
                    if (result.getStatus().equals(AUtils.STATUS_SUCCESS) || result.getStatus().equals(AUtils.STATUS_ERROR)) {

                        if (results.size() == 1) {
                            if (result.getStatus().equals(AUtils.STATUS_SUCCESS)) {

                                if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI))
                                    AUtils.success(mContext, result.getMessageMar());
                                else
                                    AUtils.success(mContext, result.getMessage());
                            }
                        }

                        if (result.getStatus().equals(AUtils.STATUS_ERROR)) {

                            if (result.getMessage().contains("outside")) {
                                if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI))
                                    areaWarningListener.onError(result.getMessageMar());
                                else
                                    areaWarningListener.onError(result.getMessage());

                            } else {

                                if (!result.getMessage().contains("wrong")) {

                                    if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI))
                                        AUtils.error(mContext, result.getMessageMar());
                                    else
                                        AUtils.error(mContext, result.getMessage());

                                }

                            }
                        }


                        if (Integer.parseInt(result.getID()) != 0) {

                            if (!result.getMessage().contains("wrong")) {
                                //    AUtils.warning(mContext , mContext.getResources().getString(R.string.please_wait));

                                int deleteCount = syncOfflineRepository.deleteSyncTableData(result.getID());
                                if (deleteCount == 0) {
                                    offset = String.valueOf(Integer.parseInt(offset) + 1);
                                }
                                for (int i = 0; i < syncOfflineList.size(); i++) {
                                    if (syncOfflineList.get(i).getOfflineID().equals(result.getID())) {
                                        syncOfflineList.remove(i);
                                        break;
                                    }
                                }
                            }
                        }

                    } else
                        offset = String.valueOf(Integer.parseInt(offset) + 1);
                } else {

                    //if nearby scan condition
                    if (results.size() == 1) {

                        if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI))
                            AUtils.error(mContext, result.getMessageMar());
                        else
                            AUtils.error(mContext, result.getMessage());

                    }
                    if (Integer.parseInt(result.getID()) != 0) {
                        if (!result.getMessage().contains("wrong")) {
                            //    AUtils.warning(mContext , mContext.getResources().getString(R.string.please_wait));
                            int deleteCount = syncOfflineRepository.deleteSyncTableData(result.getID());
                            if (deleteCount == 0) {
                                offset = String.valueOf(Integer.parseInt(offset) + 1);
                            }
                            for (int i = 0; i < syncOfflineList.size(); i++) {
                                if (syncOfflineList.get(i).getOfflineID().equals(result.getID())) {
                                    syncOfflineList.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if (results.size() > 1) {
                boolean isSuccess = false;
                for (OfflineGcResultPojo result : results) {
                    if (result.getStatus() != null) {
                        if (result.getStatus().equals(AUtils.STATUS_SUCCESS)) {
                            isSuccess = true;
                        }
                    }
                }
                if (isSuccess) {
                    if (syncOfflineRepository.fetchCollectionCount().size() == 0) {
                        AUtils.success(mContext, mContext.getResources().getString(R.string.success_offline_sync));
                        Prefs.putBoolean(AUtils.isSyncingOn, false);
                    }
                }
            } else if (results.size() == 1) {
                if (syncOfflineRepository.fetchCollectionCount().size() == 0) {
                    Prefs.putBoolean(AUtils.isSyncingOn, false);
                }
            }
        }

        AUtils.isSyncOfflineScanDataRequestEnable = false;
    }

    private void setOfflineData() {
        syncOfflineList.clear();
        List<SyncOfflineEntity> offlineEntities = syncOfflineRepository.fetchOfflineData(offset);
        for (SyncOfflineEntity entity : offlineEntities) {
            SyncOfflinePojo offlinePojo;
            Type typeToken = new TypeToken<SyncOfflinePojo>() {
            }.getType();
            offlinePojo = new Gson().fromJson(entity.getOfflineData(), typeToken);
            offlinePojo.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, ""));
            offlinePojo.setOfflineID(String.valueOf(entity.getOfflineId()));
            offlinePojo.setIsLocation(entity.getOfflineIsLocation());
            offlinePojo.setEmpType(Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null));

            Log.d(TAG, "setOfflineData: " + new Gson().toJson(offlinePojo));
            if (!AUtils.isNull(offlinePojo.getEmpType()) || !offlinePojo.getEmpType().isEmpty()) {
                if (!AUtils.isNull(offlinePojo.getUserId()) || !offlinePojo.getUserId().isEmpty()) {
                    syncOfflineList.add(offlinePojo);
                }
            }

        }
        Log.d(TAG, "setOfflineData: " + syncOfflineList);
    }

    public interface SyncOfflineListener {
        void onSuccessCallback();

        void onFailureCallback();

        void onErrorCallback();
    }

    public interface AreaWarningListener {
        void onError(String s);
    }
}