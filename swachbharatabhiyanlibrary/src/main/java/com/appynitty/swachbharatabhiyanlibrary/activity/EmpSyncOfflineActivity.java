package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.EmpInflateOfflineHistoryAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpSyncServerAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.ShareLocationAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.entity.EmpSyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.OfflineSurvey;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpOfflineCollectionCount;
import com.appynitty.swachbharatabhiyanlibrary.pojos.QrLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.EmpSyncServerRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.OfflineSurveyRepo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SurveyDetailsRepo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.viewmodels.OfflineSurveyVM;
import com.appynitty.swachbharatabhiyanlibrary.viewmodels.SurveyDetailsVM;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Swapnil Lanjewar on 08/01/2022.
 */

public class EmpSyncOfflineActivity extends AppCompatActivity {

    private Executor executor = Executors.newSingleThreadExecutor();
    private static final String TAG = "EmpSyncOfflineActivity";

    private Context mContext;
    private LinearLayout layoutNoOfflineData;
    private Button btnSyncOfflineData,btnSurveyOffline;
    //  CardView uploadDialog;
    private GridView gridOfflineData;

    private EmpSyncServerRepository empSyncServerRepository;
    private List<QrLocationPojo> locationPojoList;
    private Gson gson;
    private AlertDialog alertDialog;
    private int houseCount, dyCount, ssCount, lwcCount;
    EmpInflateOfflineHistoryAdapter historyAdapter;
    List<EmpOfflineCollectionCount> countList;
    private TextView remainingCountTv;
    //survey
    private int surveyCount;
    private OfflineSurveyVM offlineSurveyVM;
    private List<OfflineSurvey> surveyList;
    private SurveyDetailsRepo surveyDetailsRepo;
    private OfflineSurveyRepo offlineSurveyRepo;
    private List<SurveyDetailsRequestPojo> surveyDetailsRequestPojoList;
    String surveyHouseId;
    String syncSurveyCount = "0";
    String finalSyncSurveyCount = "0";
    private SurveyDetailsVM surveyDetailsVM;

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            super.attachBaseContext(LocaleHelper.onAttach(newBase));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
    }

    private void initComponents() {

        generateId();
        registerEvents();
        initData();

    }

    private void generateId() {
        setContentView(R.layout.activity_sync_offline);
        mContext = AUtils.currentContextConstant = EmpSyncOfflineActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_sync_offline);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        surveyDetailsVM = new ViewModelProvider((ViewModelStoreOwner) mContext).get(SurveyDetailsVM.class);
        offlineSurveyVM = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(OfflineSurveyVM.class);
        layoutNoOfflineData = findViewById(R.id.show_error_offline_data);
        btnSyncOfflineData = findViewById(R.id.btn_sync_data);
        btnSurveyOffline = findViewById(R.id.btn_sync_data_survey);
        btnSurveyOffline.setVisibility(View.GONE);
        //   uploadDialog = findViewById(R.id.upload_progressBar);
        gridOfflineData = findViewById(R.id.grid_offline_data);

        // ALERT DIALOG CREATION TO SHOW SYNCING STATUS
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        alertDialog = builder.create();
        View view = AUtils.getUploadingAlertDialog(mContext);
        alertDialog.setView(view);
        remainingCountTv = view.findViewById(R.id.remaining_count_tv);
        //survey
        surveyDetailsRepo = new SurveyDetailsRepo();
        offlineSurveyRepo = new OfflineSurveyRepo(getApplication());
        surveyDetailsRequestPojoList = new ArrayList<>();
        surveyList = new ArrayList<>();

        empSyncServerRepository = new EmpSyncServerRepository(AUtils.mainApplicationConstant.getApplicationContext());
        locationPojoList = new ArrayList<>();
        gson = new Gson();
        countList = new ArrayList<>();


        houseCount = 0;
        dyCount = 0;
        ssCount = 0;
        lwcCount = 0;
        surveyCount =0;

        offlineSurvey();

    }

    private List<EmpOfflineCollectionCount> getDatabaseList() {

        List<EmpSyncServerEntity> entityList = empSyncServerRepository.getAllEmpSyncServerEntity();
        locationPojoList.clear();
        clearCount();
        for (EmpSyncServerEntity entity : entityList) {
            Type type = new TypeToken<QrLocationPojo>() {
            }.getType();
            QrLocationPojo pojo = gson.fromJson(entity.getPojo(), type);

            pojo.setOfflineID(String.valueOf(entity.getIndex_id()));
            locationPojoList.add(pojo);

        }

        if (locationPojoList.size() > 0) {

            String refId;

            for (int i = 0; i < locationPojoList.size(); i++) {
                refId = locationPojoList.get(i).getReferanceId();
                if (refId.substring(0, 2).matches("^[HhPp]+$")) {
                    houseCount++;
                } else if (refId.substring(0, 2).matches("^[LlWw]+$")) {
                    lwcCount++;
                } else if (refId.substring(0, 2).matches("^[DdYy]+$")) {
                    dyCount++;
                } else if (refId.substring(0, 2).matches("^[SsSs]+$")) {
                    ssCount++;
                }
            }

            Log.e(TAG, "House count: " + houseCount
                    + ", dyCount: " + dyCount
                    + ", ssCount: " + ssCount
                    + ", surveyCount: " + surveyCount
                    + ", lwcCount: " + lwcCount);

            countList.add(new EmpOfflineCollectionCount(
                    String.valueOf(houseCount),
                    String.valueOf(dyCount),
                    String.valueOf(ssCount),
                    String.valueOf(""),
                    String.valueOf(lwcCount), locationPojoList.get(0).getDate())
            );
        }
        return countList;

    }


    private void registerEvents() {

        btnSurveyOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AUtils.isInternetAvailable()){
                    sendSurveyVmOffline();
                    setOfflineSurveyIds();
                    finish();
                }else {
                    AUtils.warning(mContext,getResources().getString(R.string.no_internet_error));
                }
            }
        });

        btnSyncOfflineData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                    uploadToServer();
                    showDialogWithCount();
                }
            }
        });
    }


    private void showDialogWithCount() {

        if (!alertDialog.isShowing())
            alertDialog.show();
        else
            alertDialog.hide();

        if (Prefs.getBoolean(AUtils.isSyncingOn, false)) {
            //   if (syncOfflineRepository.fetchCollectionCount().size() > 0) {

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    final boolean[] isSyncing = {true};

                    String syncCount = "0";
                    String finalSyncCount = "0";

                    while (isSyncing[0]) {

//                        if (Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                        syncCount = String.valueOf(empSyncServerRepository.getOfflineCount());
                        if (Integer.parseInt(syncCount) == 0) {
                            syncCount = "0";
                            finalSyncCount = "0";
                        }

//                        } else {
//                            syncCount = "0";
//                            finalSyncCount = "0";
//                        }
                        Log.i(TAG, "run: " + syncCount);


                        if (!Objects.equals(finalSyncCount, syncCount)) {

                            String finalSyncCount1 = syncCount;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    StringBuffer stringBuffer = new StringBuffer();
                                    stringBuffer.append(finalSyncCount1);
                                    stringBuffer.append(" ");
                                    stringBuffer.append(getResources().getString(R.string.remaining));
                                    remainingCountTv.setText(stringBuffer);
                                    if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                                        isSyncing[0] = false;

                                    }
                                    if (empSyncServerRepository.getOfflineCount() == 0) {
                                        isSyncing[0] = false;
                                    }
                                    inflateData();
                                }
                            });
                        } else if (Integer.parseInt(finalSyncCount) == 0 && Integer.parseInt(syncCount) == 0) {
//
//                            if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
//                                Log.i(TAG, "run: if " + finalSyncCount + "  " + syncCount);
//                                isSyncing[0] = false;
//
//                            }
                            if (empSyncServerRepository.getOfflineCount() == 0) {
                                Log.i(TAG, "run: this if" + "  " + syncCount);
                                isSyncing[0] = false;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.i(TAG, "run: " + "count");
                                        inflateData();

                                    }
                                });
                            }


                        }
                        finalSyncCount = syncCount;

                    }

                }
            });
//            } else {
//                inflateData();
//            }
        } else {
            inflateData();
        }

    }

    private void uploadToServer() {
        final EmpSyncServerAdapterClass empSyncAdapter = new EmpSyncServerAdapterClass(this);
        // uploadDialog.setVisibility(View.VISIBLE);
        empSyncAdapter.syncServer();

        empSyncAdapter.setSyncOfflineListener(new EmpSyncServerAdapterClass.EmpSyncOfflineListener() {
            @Override
            public void onSuccessCallback() {
                //   uploadDialog.setVisibility(View.GONE);
//                AUtils.success(mContext, getString(R.string.success_offline_sync), Toast.LENGTH_LONG);
//                locationPojoList.clear();
//                countList.clear();
//                getDatabaseList();
//                historyAdapter.setNotifyOnChange(true);
//                inflateData();
            }

            @Override
            public void onFailureCallback() {
                //  uploadDialog.setVisibility(View.GONE);
                //   AUtils.warning(mContext, getResources().getString(R.string.try_after_sometime));
            }

            @Override
            public void onErrorCallback() {
                //   uploadDialog.setVisibility(View.GONE);
                //  AUtils.warning(mContext, getResources().getString(R.string.serverError));
            }
        });

        ShareLocationAdapterClass shareLocationAdapterClass = new ShareLocationAdapterClass();
        shareLocationAdapterClass.shareLocation();
    }

    private void initData() {
        if (empSyncServerRepository.getOfflineCount() > 0) {
            if (Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                showDialogWithCount();
                btnSyncOfflineData.setVisibility(View.GONE);
                Log.i("SANATH_SYNC", "initData: " + "isSyncingOn");
            } else {
                inflateData();
                Log.i("SANATH_SYNC", "initData: " + "isSyncingOff");
            }
        } else {
            inflateData();
            Log.i("SANATH_SYNC", "initData: " + "isSyncingOff");
        }
    }

    private void inflateData() {

        countList.clear();
        countList = getDatabaseList();
        if (locationPojoList.size() > 0) {
            if (countList.size() > 0) {
                if (Integer.parseInt(countList.get(0).getHouseCount()) > 0) {
                    gridOfflineData.setVisibility(View.VISIBLE);
                    if (Integer.parseInt(countList.get(0).getHouseCount()) == 0) {
                        btnSyncOfflineData.setVisibility(View.VISIBLE);
                    }

                    layoutNoOfflineData.setVisibility(View.GONE);
                    historyAdapter = new EmpInflateOfflineHistoryAdapter(mContext, R.layout.layout_history_card, countList);
                    gridOfflineData.setAdapter(historyAdapter);

                } else if (Integer.parseInt(countList.get(0).getStreetSweepCount()) > 0) {

                    gridOfflineData.setVisibility(View.VISIBLE);
                    if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                        btnSyncOfflineData.setVisibility(View.VISIBLE);
                    }

                    layoutNoOfflineData.setVisibility(View.GONE);
                    historyAdapter = new EmpInflateOfflineHistoryAdapter(mContext, R.layout.layout_history_card, countList);
                    gridOfflineData.setAdapter(historyAdapter);

                }else if (Integer.parseInt(countList.get(0).getLiquidWasteCount()) > 0) {

                    gridOfflineData.setVisibility(View.VISIBLE);
                    if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                        btnSyncOfflineData.setVisibility(View.VISIBLE);
                    }

                    layoutNoOfflineData.setVisibility(View.GONE);
                    historyAdapter = new EmpInflateOfflineHistoryAdapter(mContext, R.layout.layout_history_card, countList);
                    gridOfflineData.setAdapter(historyAdapter);

                } else if (Integer.parseInt(countList.get(0).getDumpYardCount()) > 0) {
                    gridOfflineData.setVisibility(View.VISIBLE);
                    if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                        btnSyncOfflineData.setVisibility(View.VISIBLE);
                    }

                    layoutNoOfflineData.setVisibility(View.GONE);
                    historyAdapter = new EmpInflateOfflineHistoryAdapter(mContext, R.layout.layout_history_card, countList);
                    gridOfflineData.setAdapter(historyAdapter);
                } else {

                    gridOfflineData.setVisibility(View.GONE);
                    btnSyncOfflineData.setVisibility(View.GONE);
                    layoutNoOfflineData.setVisibility(View.VISIBLE);
                    if (alertDialog.isShowing())
                        alertDialog.dismiss();
                }


            } else {
                gridOfflineData.setVisibility(View.GONE);
                btnSyncOfflineData.setVisibility(View.GONE);
                layoutNoOfflineData.setVisibility(View.VISIBLE);
                if (alertDialog.isShowing())
                    alertDialog.dismiss();
            }
        } else {

            gridOfflineData.setVisibility(View.GONE);
            btnSyncOfflineData.setVisibility(View.GONE);
            layoutNoOfflineData.setVisibility(View.VISIBLE);
            if (alertDialog.isShowing())
                alertDialog.dismiss();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (!AUtils.isInternetAvailable()) {
            btnSyncOfflineData.setVisibility(View.GONE);
            AUtils.showSnackBar(findViewById(R.id.parent));
        }
//        if (AUtils.isInternetAvailable() && AUtils.isConnectedFast(getApplicationContext())) {
//
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    if (InternetWorking.isOnline()) {
//
//                        Handler handler = new Handler(Looper.getMainLooper());
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                AUtils.hideSnackBar();
//                                if (empSyncServerRepository.getOfflineCount() > 0) {
//                                    uploadToServer();
//                                }
//                            }
//                        });
//                    } else {
//
//                        Handler handler = new Handler(Looper.getMainLooper());
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                AUtils.showSnackBar(findViewById(R.id.parent));
//                            }
//                        });
//                    }
//                }
//            });
//
//        } else {
//            AUtils.showSnackBar(findViewById(R.id.parent));
//        }
    }

    public void clearCount() {
        houseCount = 0;
        dyCount = 0;
        ssCount = 0;
        lwcCount = 0;
        surveyCount =0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void offlineSurvey(){
        Log.i("checkList", "offlineSurveyRahul: ");
        clearCount();

        offlineSurveyVM.getAllSurveyLiveData().observe(this, offlineSurveys -> {
            if (offlineSurveys != null && !offlineSurveys.isEmpty()){
                surveyDetailsRequestPojoList.add(offlineSurveys.get(0).getSurveyRequestObj());
                Log.e(TAG, "offline survey list: "+offlineSurveys.get(0).getSurveyRequestObj());
                Log.i("TAG", "offline survey list check: "+offlineSurveys.get(0).getSurveyRequestObj());
                for (int i=0; i<offlineSurveys.size(); i++){
                        countList.clear();

                        surveyHouseId = offlineSurveys.get(i).getHouseId();
                        if (!surveyHouseId.isEmpty()){
                            btnSyncOfflineData.setVisibility(View.GONE);
                            btnSurveyOffline.setVisibility(View.VISIBLE);
                        }else {
                            layoutNoOfflineData.setVisibility(View.VISIBLE);
                        }
                        //surveyCount = Integer.parseInt(surveyHouseId);
                        if (surveyHouseId.substring(0, 2).matches("^[HhPp]+$")) {
                            surveyCount++;
                            Log.i("rahul", "offlineSurvey count: "+surveyCount);
                          //  syncSurveyCount = String.valueOf(surveyCount);
                            syncSurveyCount = String.valueOf(offlineSurveyRepo.getOfflineCount());
                            Prefs.putString(AUtils.OFFLINE_SURVEY_COUNT, String.valueOf(surveyCount));
                        }

                        countList.add(new EmpOfflineCollectionCount(String.valueOf(""),String.valueOf(""),String.valueOf(""),
                                String.valueOf(""),String.valueOf(syncSurveyCount),String.valueOf(AUtils.getServerDateTime())));

                        TableDataCountPojo.WorkHistory entity = new TableDataCountPojo().new WorkHistory();
                        entity.setSurveyCollection(String.valueOf(syncSurveyCount));
                        Log.i(TAG, "Rahul offlineSurvey: "+entity);

                    if (Prefs.getBoolean(AUtils.isSyncingOn, false)) {

                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                final boolean[] isSyncing = {true};

                                while (isSyncing[0]) {

                                    syncSurveyCount = String.valueOf(empSyncServerRepository.getOfflineCount());
                                    if (Integer.parseInt(syncSurveyCount) == 0) {
                                        syncSurveyCount = "0";
                                        finalSyncSurveyCount = "0";
                                    }
                                    Log.i(TAG, "run: " + syncSurveyCount);


                                    if (!Objects.equals(finalSyncSurveyCount, syncSurveyCount)) {

                                        String finalSyncCount1 = syncSurveyCount;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                StringBuffer stringBuffer = new StringBuffer();
                                                stringBuffer.append(finalSyncCount1);
                                                stringBuffer.append(" ");
                                                stringBuffer.append(getResources().getString(R.string.remaining));
                                                remainingCountTv.setText(stringBuffer);
                                                if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                                                    isSyncing[0] = false;

                                                }
                                                if (empSyncServerRepository.getOfflineCount() == 0) {
                                                    isSyncing[0] = false;
                                                }
                                                inflateData();
                                            }
                                        });
                                    } else if (Integer.parseInt(finalSyncSurveyCount) == 0 && Integer.parseInt(syncSurveyCount) == 0) {

                                        if (empSyncServerRepository.getOfflineCount() == 0) {
                                            Log.i(TAG, "run: this if" + "  " + syncSurveyCount);
                                            isSyncing[0] = false;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Log.i(TAG, "run: " + "count");
                                                    inflateData();

                                                }
                                            });
                                        }
                                    }
                                    finalSyncSurveyCount = syncSurveyCount;
                                }

                            }
                        });
                    } else {
                        if (offlineSurveys.size() > 0) {
                            if (countList.size() > 0) {
                                Log.i(TAG, "countList is: "+countList.size());
                                if (Integer.parseInt(countList.get(0).getSurveyCount()) > 0) {
                                    gridOfflineData.setVisibility(View.VISIBLE);
                                    if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                                        btnSyncOfflineData.setVisibility(View.GONE);
                                        btnSurveyOffline.setVisibility(View.VISIBLE);
                                    }

                                    layoutNoOfflineData.setVisibility(View.GONE);
                                    historyAdapter = new EmpInflateOfflineHistoryAdapter(mContext, R.layout.layout_history_card, countList);
                                    gridOfflineData.setAdapter(historyAdapter);

                                }
                            }
                        }else {
                            gridOfflineData.setVisibility(View.GONE);
                            btnSyncOfflineData.setVisibility(View.GONE);
                            btnSurveyOffline.setVisibility(View.GONE);
                            layoutNoOfflineData.setVisibility(View.VISIBLE);
                            if (alertDialog.isShowing())
                                alertDialog.dismiss();
                        }

                    }
                }
                    if (AUtils.isInternetAvailable()){
                        checkNetwork(offlineSurveys);
                    }
            }
        });
    }

    private void checkNetwork(List<OfflineSurvey> offlineSurveys){
       // sendOfflineSurvey();
       // sendSurveyVmOffline();

        if (offlineSurveys.size() > 0) {
            for (OfflineSurvey offlineSurvey : offlineSurveys) {
                offlineSurvey.getSurveyRequestObj().setReferanceId(offlineSurvey.getHouseId());
                offlineSurveyVM.update(offlineSurvey);
            }
        }else {
            clearCount();
        }
    }

    private void sendSurveyVmOffline(){
        Log.i("Rahul_test", "offlineAddSurveyDetails : "+surveyDetailsRequestPojoList);
        surveyDetailsVM.surveyOfflineFormApi(surveyDetailsRequestPojoList);
        surveyDetailsVM.OfflineSurveyDetailsMutableLiveData().observe((LifecycleOwner) mContext, new Observer<List<SurveyDetailsResponsePojo>>() {
            @Override
            public void onChanged(List<SurveyDetailsResponsePojo> surveyDetailsResponsePojos) {
                Log.e(TAG, "SurveyLiveData: " + surveyDetailsResponsePojos.toString());
                String houseId;

                for (int i=0; i<surveyDetailsResponsePojos.size(); i++){
                    if (surveyDetailsResponsePojos.get(i).getStatus().matches(AUtils.STATUS_SUCCESS)) {
                        houseId = surveyDetailsResponsePojos.get(i).getHouseId();
                        offlineSurveyRepo.deleteSurveyById(houseId);
                        Prefs.remove(AUtils.OFFLINE_SURVEY_COUNT);
                        Log.e(TAG, "sendOfflineSurvey: successfully deleted surveyHouseId: " + houseId);
                        if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI)) {
                            AUtils.success(mContext, surveyDetailsResponsePojos.get(i).getMessageMar());
                        } else {
                            AUtils.success(mContext, surveyDetailsResponsePojos.get(i).getMessage());
                        }
                    }else if (surveyDetailsResponsePojos.get(i).getStatus().matches(AUtils.STATUS_ERROR)) {
                        houseId = surveyDetailsResponsePojos.get(i).getHouseId();
                        Log.e(TAG, "sendOfflineSurvey: This surveyHouseId is error, please check date: " + houseId);
                        if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI)) {
                            AUtils.error(mContext, surveyDetailsResponsePojos.get(i).getMessageMar());
                        } else {
                            AUtils.error(mContext, surveyDetailsResponsePojos.get(i).getMessage());
                        }
                    }
                }

            }
        });

    }

    /*public void sendOfflineSurvey() {
        Log.i("Rahul_test", "offlineAddSurveyDetails : "+surveyDetailsRequestPojoList);
        surveyDetailsRepo.offlineAddSurveyDetails(surveyDetailsRequestPojoList, new SurveyDetailsRepo.IOfflineSurveyDetailsResponse() {
            @Override
            public void onResponse(List<SurveyDetailsResponsePojo> offlineSurveyDetailsResponse) {
                Log.e(TAG, "offlineAddSurveyDetails offline data send: " + offlineSurveyDetailsResponse);
                String houseId;

                if (offlineSurveyDetailsResponse.get(0).getStatus().matches(AUtils.STATUS_SUCCESS)) {
                    houseId = offlineSurveyDetailsResponse.get(0).getHouseId();
                    offlineSurveyRepo.deleteSurveyById(houseId);
                    Prefs.remove(AUtils.OFFLINE_SURVEY_COUNT);
                    Log.e(TAG, "sendOfflineSurvey: successfully deleted surveyHouseId: " + houseId);
                }else if (offlineSurveyDetailsResponse.get(0).getStatus().matches(AUtils.STATUS_ERROR)) {
                    houseId = offlineSurveyDetailsResponse.get(0).getHouseId();
                    Log.e(TAG, "sendOfflineSurvey: This surveyHouseId is error, please check date: " + houseId);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }*/

    private void setOfflineSurveyIds() {
        surveyList = offlineSurveyRepo.getOfflineTotalSurveyList();
        if (surveyList.size() > 0) {
            for (OfflineSurvey offlineSurvey : surveyList) {
                offlineSurvey.getSurveyRequestObj().getReferanceId();
                offlineSurveyVM.update(offlineSurvey);
            }
            for (int i=0; i<surveyList.size(); i++){
                surveyDetailsRequestPojoList.add(surveyList.get(i).getSurveyRequestObj());
            }
            sendSurveyVmOffline();
        }

    }

    private void showDialogWithSurveyCount() {

        if (!alertDialog.isShowing())
            alertDialog.show();
        else
            alertDialog.hide();

        if (Prefs.getBoolean(AUtils.isSyncingOn, false)) {

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    final boolean[] isSyncing = {true};

                    while (isSyncing[0]) {

                        syncSurveyCount = String.valueOf(empSyncServerRepository.getOfflineCount());
                        if (Integer.parseInt(syncSurveyCount) == 0) {
                            syncSurveyCount = "0";
                            finalSyncSurveyCount = "0";
                        }
                        Log.i(TAG, "run: " + syncSurveyCount);


                        if (!Objects.equals(finalSyncSurveyCount, syncSurveyCount)) {

                            String finalSyncCount1 = syncSurveyCount;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    StringBuffer stringBuffer = new StringBuffer();
                                    stringBuffer.append(finalSyncCount1);
                                    stringBuffer.append(" ");
                                    stringBuffer.append(getResources().getString(R.string.remaining));
                                    remainingCountTv.setText(stringBuffer);
                                    if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                                        isSyncing[0] = false;

                                    }
                                    if (empSyncServerRepository.getOfflineCount() == 0) {
                                        isSyncing[0] = false;
                                    }
                                    inflateData();
                                }
                            });
                        } else if (Integer.parseInt(finalSyncSurveyCount) == 0 && Integer.parseInt(syncSurveyCount) == 0) {
//
//                            if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
//                                Log.i(TAG, "run: if " + finalSyncCount + "  " + syncCount);
//                                isSyncing[0] = false;
//
//                            }
                            if (empSyncServerRepository.getOfflineCount() == 0) {
                                Log.i(TAG, "run: this if" + "  " + syncSurveyCount);
                                isSyncing[0] = false;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.i(TAG, "run: " + "count");
                                        inflateData();

                                    }
                                });
                            }


                        }
                        finalSyncSurveyCount = syncSurveyCount;
                    }

                }
            });
        } else {

        }

    }

}
