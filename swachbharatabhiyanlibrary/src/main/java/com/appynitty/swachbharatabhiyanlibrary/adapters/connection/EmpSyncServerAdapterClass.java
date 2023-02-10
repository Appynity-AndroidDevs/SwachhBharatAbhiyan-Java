package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.entity.EmpSyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.OfflineSurvey;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpOfflineCollectionCount;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.QrLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.EmpSyncServerRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.OfflineSurveyRepo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SurveyDetailsRepo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.viewmodels.OfflineSurveyVM;
import com.appynitty.swachbharatabhiyanlibrary.viewmodels.SurveyDetailsVM;
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
    //survey
    private int surveyCount;
    private OfflineSurveyVM offlineSurveyVM;
    private List<OfflineSurvey> surveyList;
    private SurveyDetailsRepo surveyDetailsRepo;
    private OfflineSurveyRepo offlineSurveyRepo;
    private List<SurveyDetailsRequestPojo> surveyDetailsRequestPojoList;
    List<EmpOfflineCollectionCount> countList;
    String surveyHouseId;
    String syncSurveyCount = "0";
    String finalSyncSurveyCount = "0";
    private SurveyDetailsVM surveyDetailsVM;


    public EmpSyncServerAdapterClass(Context mContext) {
        empSyncServerRepository = new EmpSyncServerRepository(AUtils.mainApplicationConstant.getApplicationContext());
        locationPojoList = new ArrayList<>();
        surveyDetailsRequestPojoList = new ArrayList<>();
        surveyDetailsVM = new ViewModelProvider((ViewModelStoreOwner) mContext).get(SurveyDetailsVM.class);
        gson = new Gson();
        this.mContext = mContext;
    }

    public void setSyncOfflineListener(EmpSyncServerAdapterClass.EmpSyncOfflineListener empSyncOfflineListener) {
        this.empSyncOfflineListener = empSyncOfflineListener;
    }

    public void
    syncServer() {

        offlineCount = empSyncServerRepository.getOfflineCount();

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
                                AUtils.warning(mContext, mContext.getResources().getString(R.string.connection_timeout), Toast.LENGTH_SHORT);

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

    }

    public  void  syncSurveyServer(){
        surveyCount = offlineSurveyRepo.getOfflineCount();
        for (int i = 0; i < surveyCount; i++) {
            if (!AUtils.isEmpSyncServerRequestEnable) {
                getDatabaseSurveyByLimit();
                Log.e(TAG, "syncServer: Sending survey limit by 10 " + surveyDetailsRequestPojoList.get(i).getReferanceId() + " data packets!");
                if (surveyDetailsRequestPojoList.size() > 0) {
                    Log.e(TAG, "syncServer: offlineData10Count: " + offlineSurveyRepo.getOfflineCount());
                    AUtils.isEmpSyncServerRequestEnable = true;
                    Prefs.putBoolean(AUtils.isSyncingOn, true);

                    Collections.reverse(surveyDetailsRequestPojoList);
                }
            }
        }
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

    private void getDatabaseSurveyByLimit(){
        offlineSurveyVM = ViewModelProvider.AndroidViewModelFactory.getInstance((Application) mContext.getApplicationContext()).create(OfflineSurveyVM.class);
        offlineSurveyVM.getSurveyByLimit10LiveData().observe((LifecycleOwner) mContext, new Observer<List<OfflineSurvey>>() {
            @Override
            public void onChanged(List<OfflineSurvey> offlineSurveys) {
                surveyDetailsRequestPojoList.clear();
                if (offlineSurveys != null && !offlineSurveys.isEmpty()){
                    surveyDetailsRequestPojoList.add(offlineSurveys.get(0).getSurveyRequestObj());
                    Log.e(TAG, "offline survey list by limit: "+offlineSurveys.get(0).getSurveyRequestObj());
                    for (int i=0; i<offlineSurveys.size(); i++){
                        countList.clear();
                        surveyHouseId = offlineSurveys.get(i).getHouseId();
                    }
                }
            }
        });


    }
    private void sendSurveyVmOffline(){
        Log.i("Rahul_test", "offlineAddSurveyDetails : "+surveyDetailsRequestPojoList);
        surveyDetailsVM.surveyOfflineFormApi(surveyDetailsRequestPojoList);
        surveyDetailsVM.OfflineSurveyDetailsMutableLiveData().observe((LifecycleOwner) mContext, new Observer<List<SurveyDetailsResponsePojo>>() {
            @Override
            public void onChanged(List<SurveyDetailsResponsePojo> surveyDetailsResponsePojos) {
                Log.e(TAG, "SurveyLiveData: " + surveyDetailsResponsePojos.toString());
                String houseId;
                if (surveyDetailsResponsePojos.get(0).getStatus().matches(AUtils.STATUS_SUCCESS)) {
                    houseId = surveyDetailsResponsePojos.get(0).getHouseId();
                    offlineSurveyRepo.deleteSurveyById(houseId);
                    syncSurveyServer();
                    Prefs.remove(AUtils.OFFLINE_SURVEY_COUNT);
                    Log.e(TAG, "sendOfflineSurvey: successfully deleted surveyHouseId: " + houseId);
                    if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI)) {
                        AUtils.success(mContext, surveyDetailsResponsePojos.get(0).getMessageMar());
                    } else {
                        AUtils.success(mContext, surveyDetailsResponsePojos.get(0).getMessage());
                    }
                }else if (surveyDetailsResponsePojos.get(0).getStatus().matches(AUtils.STATUS_ERROR)) {
                    houseId = surveyDetailsResponsePojos.get(0).getHouseId();
                    Log.e(TAG, "sendOfflineSurvey: This surveyHouseId is error, please check date: " + houseId);
                    if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI)) {
                        AUtils.error(mContext, surveyDetailsResponsePojos.get(0).getMessageMar());
                    } else {
                        AUtils.error(mContext, surveyDetailsResponsePojos.get(0).getMessage());
                    }
                }

            }
        });

        surveyDetailsVM.getSurveyDetailsError().observe((LifecycleOwner) mContext, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                AUtils.error(mContext, throwable.getMessage());
            }
        });

        /*surveyDetailsVM.getProgressStatusLiveData().observe((LifecycleOwner) mContext, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                loader.setVisibility(visibility);
            }
        });*/

    }

    private void onResponseReceived(List<OfflineGcResultPojo> results) {

        if (!AUtils.isNull(results) && results.size() > 0) {

            for (OfflineGcResultPojo result : results) {

                if (result.getStatus().equals(AUtils.STATUS_SUCCESS)) {


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

        offlineCount = empSyncServerRepository.getOfflineCount();

//            if (isSuccess) {
        if (offlineCount == 0) {
            Log.i("EmpSyncCountTest", "onResponseReceived size 0: " + offlineCount);
            Prefs.putBoolean(AUtils.isSyncingOn, false);
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
