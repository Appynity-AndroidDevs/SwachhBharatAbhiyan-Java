package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.EmpInflateOfflineHistoryAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpSyncServerAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.ShareLocationAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.entity.EmpSyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpOfflineCollectionCount;
import com.appynitty.swachbharatabhiyanlibrary.pojos.QrLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.EmpSyncServerRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Swapnil Lanjewar on 08/01/2022.
 */

public class EmpSyncOfflineActivity extends AppCompatActivity {

    private Executor executor = Executors.newSingleThreadExecutor();
    private static final String TAG = "EmpSyncOfflineActivity";

    private Context mContext;
    private LinearLayout layoutNoOfflineData;
    private Button btnSyncOfflineData;
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

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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

        layoutNoOfflineData = findViewById(R.id.show_error_offline_data);
        btnSyncOfflineData = findViewById(R.id.btn_sync_data);
        //   uploadDialog = findViewById(R.id.upload_progressBar);
        gridOfflineData = findViewById(R.id.grid_offline_data);

        // ALERT DIALOG CREATION TO SHOW SYNCING STATUS
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        alertDialog = builder.create();
        View view = AUtils.getUploadingAlertDialog(mContext);
        alertDialog.setView(view);
        remainingCountTv = view.findViewById(R.id.remaining_count_tv);


        empSyncServerRepository = new EmpSyncServerRepository(AUtils.mainApplicationConstant.getApplicationContext());
        locationPojoList = new ArrayList<>();
        gson = new Gson();
        countList = new ArrayList<>();

        houseCount = 0;
        dyCount = 0;
        ssCount = 0;
        lwcCount = 0;

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
                    + ", lwcCount: " + lwcCount);

            countList.add(new EmpOfflineCollectionCount(
                    String.valueOf(houseCount),
                    String.valueOf(dyCount),
                    String.valueOf(ssCount),
                    String.valueOf(lwcCount), locationPojoList.get(0).getDate())
            );
        }
        return countList;

    }


    private void registerEvents() {

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

        if (Prefs.getBoolean(AUtils.isSyncingOn, false)) {
            showDialogWithCount();
            btnSyncOfflineData.setVisibility(View.GONE);
        } else {
            inflateData();
        }
    }

    private void inflateData() {

        countList.clear();
        countList = getDatabaseList();
        if (locationPojoList.size() > 0) {
            if (countList.size() > 0) {
                if (Integer.parseInt(countList.get(0).getHouseCount()) > 0) {
                    if (empSyncServerRepository.getOfflineCount() > 0) {

                        gridOfflineData.setVisibility(View.VISIBLE);
                        if (!Prefs.getBoolean(AUtils.isSyncingOn, false))
                            btnSyncOfflineData.setVisibility(View.VISIBLE);
                        layoutNoOfflineData.setVisibility(View.GONE);

                        historyAdapter = new EmpInflateOfflineHistoryAdapter(mContext, R.layout.layout_history_card, countList);
//            historyAdapter.setNotifyOnChange(true);
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

}
