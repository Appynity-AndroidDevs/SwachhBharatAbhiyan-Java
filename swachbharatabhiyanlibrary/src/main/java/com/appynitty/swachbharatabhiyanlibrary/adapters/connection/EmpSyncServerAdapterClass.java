package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.content.Context;
import android.util.Log;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.entity.EmpSyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.QrLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.EmpSyncServerRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.QrLocationWebService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ayan Dey on 28/5/19.
 */
public class EmpSyncServerAdapterClass {

    private static final String TAG = "EmpSyncServerAdapterClass";
    EmpSyncServerAdapterClass.EmpSyncOfflineListener empSyncOfflineListener;
    int offlineCount = 0;
    private final List<QrLocationPojo> locationPojoList;
    private final Gson gson;
    private final EmpSyncServerRepository empSyncServerRepository;
    private Context mContext;


    public EmpSyncServerAdapterClass(Context mContext) {
        empSyncServerRepository = new EmpSyncServerRepository(AUtils.mainApplicationConstant.getApplicationContext());
        locationPojoList = new ArrayList<>();
        gson = new Gson();
        this.mContext = mContext;
    }

    public void setSyncOfflineListener(EmpSyncServerAdapterClass.EmpSyncOfflineListener empSyncOfflineListener) {
        this.empSyncOfflineListener = empSyncOfflineListener;
    }

    public void syncServer() {

        offlineCount = empSyncServerRepository.getOfflineCount();


//        do {

        for (int i = 0; i < offlineCount; i++) {


            if (!AUtils.isEmpSyncServerRequestEnable) {


                getDatabaseList();

                Log.e(TAG, "syncServer: Sending " + locationPojoList.size() + " data packets!");
                if (locationPojoList.size() > 0) {

                    Log.e(TAG, "syncServer: offlineDataCount: " + empSyncServerRepository.getOfflineCount());

                    AUtils.isEmpSyncServerRequestEnable = true;
                    Prefs.putBoolean(AUtils.isSyncingOn, true);

                    Collections.reverse(locationPojoList);

                    QrLocationWebService service = Connection.createService(QrLocationWebService.class, AUtils.SERVER_URL);

                    service.saveQrLocationDetailsOffline(Prefs.getString(AUtils.APP_ID, ""), AUtils.CONTENT_TYPE, locationPojoList).enqueue(new Callback<List<OfflineGcResultPojo>>() {
                        @Override
                        public void onResponse(Call<List<OfflineGcResultPojo>> call, Response<List<OfflineGcResultPojo>> response) {

                            if (response.code() == 200) {
                                Log.i(TAG, "onResponse: " + response);

                                onResponseReceived(response.body());
                                syncServer();

                                if (locationPojoList.size() > 0) {

                                } else {
                                    empSyncOfflineListener.onSuccessCallback();
                                }

                            } else {
                                Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + response.code());
                                AUtils.isEmpSyncServerRequestEnable = false;
                             //   Prefs.putBoolean(AUtils.isSyncingOn, false);
                                empSyncOfflineListener.onFailureCallback();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<OfflineGcResultPojo>> call, Throwable t) {
                            Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + t.getMessage());
                            AUtils.isEmpSyncServerRequestEnable = false;
                            Prefs.putBoolean(AUtils.isSyncingOn, false);
                            empSyncOfflineListener.onErrorCallback();
                        }
                    });
                } else {
                    if (empSyncOfflineListener != null)
                        empSyncOfflineListener.onSuccessCallback();
                }
            } else {
                if (empSyncOfflineListener != null)
                    empSyncOfflineListener.onFailureCallback();
            }
        }
//        } while (offlineCount > 0);
    }

    private void getDatabaseList() {

        List<EmpSyncServerEntity> entityList = empSyncServerRepository.get10EmpSyncServerEntity();
        locationPojoList.clear();
        for (EmpSyncServerEntity entity : entityList) {
            Type type = new TypeToken<QrLocationPojo>() {
            }.getType();
            QrLocationPojo pojo = gson.fromJson(entity.getPojo(), type);

            pojo.setOfflineID(String.valueOf(entity.getIndex_id()));
            locationPojoList.add(pojo);
        }
    }

    private void onResponseReceived(List<OfflineGcResultPojo> results) {

        if (!AUtils.isNull(results) && results.size() > 0) {

            for (OfflineGcResultPojo result : results) {

                if (result.getStatus().equals(AUtils.STATUS_SUCCESS)) {

//                    if (results.size() == 1 && result.getStatus().equals(AUtils.STATUS_SUCCESS)) {
//                        AUtils.success(mContext, mContext.getResources().getString(R.string.success_offline_sync));
//                    }


                    if (Integer.parseInt(result.getID()) != 0) {
                        empSyncServerRepository.deleteEmpSyncServerEntity(Integer.parseInt(result.getID()));
                    }

                    for (int i = 0; i < locationPojoList.size(); i++) {
                        if (locationPojoList.get(i).getOfflineID().equals(result.getID())) {
                            locationPojoList.remove(i);
                            break;
                        }
                    }

                } else {

                    if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI))
                        AUtils.error(mContext, result.getMessageMar());
                    else
                        AUtils.error(mContext, result.getMessage());

                    if (!result.getMessage().contains("wrong")) {
                        if (Integer.parseInt(result.getID()) != 0) {
                            empSyncServerRepository.deleteEmpSyncServerEntity(Integer.parseInt(result.getID()));
                        }

                        for (int i = 0; i < locationPojoList.size(); i++) {
                            if (locationPojoList.get(i).getOfflineID().equals(result.getID())) {
                                locationPojoList.remove(i);
                                break;
                            }
                        }
                    }

                }
            }
        }

//        if (results.size() > 1) {
//            boolean isSuccess = false;
//            for (OfflineGcResultPojo result : results) {
//                if (result.getStatus().equals(AUtils.STATUS_SUCCESS)) {
//                    isSuccess = true;
//                }
//            }

        AUtils.isEmpSyncServerRequestEnable = false;
        Prefs.putBoolean(AUtils.isSyncingOn, false);
        offlineCount = empSyncServerRepository.getOfflineCount();

//            if (isSuccess) {
        if (offlineCount == 0) {
            Log.i("EmpSyncCountTest", "onResponseReceived size 0: " + offlineCount);
            AUtils.success(mContext, mContext.getResources().getString(R.string.success_offline_sync));
        }

//            }
//
//        }

        Log.i("EmpSyncCountTest", "onResponseReceived: " + offlineCount);

    }

    public interface EmpSyncOfflineListener {
        void onSuccessCallback();

        void onFailureCallback();

        void onErrorCallback();
    }

    public interface TestEvents {
        void onSuccess();

        void onFailed();
    }


}
