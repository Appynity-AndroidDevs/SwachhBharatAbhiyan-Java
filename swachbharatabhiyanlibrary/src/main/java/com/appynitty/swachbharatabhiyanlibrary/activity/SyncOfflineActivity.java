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
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.InflateOfflineWorkAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.SyncOfflineAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SyncOfflineActivity extends AppCompatActivity {

    private Executor executor = Executors.newSingleThreadExecutor();
    private static final String TAG = "SyncOfflineActivity";
    private Context mContext;
    private LinearLayout layoutNoOfflineData;
    private Button btnSyncOfflineData;
    private GridView gridOfflineData;
    private SyncOfflineRepository syncOfflineRepository;
    private List<TableDataCountPojo.WorkHistory> workHistoryList;
    private SyncOfflineAdapterClass syncOfflineAdapter;
    private AlertDialog alertDialog;
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

        mContext = AUtils.currentContextConstant = SyncOfflineActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_sync_offline);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        layoutNoOfflineData = findViewById(R.id.show_error_offline_data);
        btnSyncOfflineData = findViewById(R.id.btn_sync_data);
        gridOfflineData = findViewById(R.id.grid_offline_data);

        workHistoryList = new ArrayList<>();
        syncOfflineRepository = new SyncOfflineRepository(mContext);
        syncOfflineAdapter = new SyncOfflineAdapterClass(mContext);

        // ALERT DIALOG CREATION TO SHOW SYNCING STATUS
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        alertDialog = builder.create();
        View view = AUtils.getUploadingAlertDialog(mContext);
        alertDialog.setView(view);
        remainingCountTv = view.findViewById(R.id.remaining_count_tv);

    }

    private void registerEvents() {

        btnSyncOfflineData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                    syncOfflineAdapter.SyncOfflineData();
                    showDialogWithCount();
                }

//
//                executor.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (InternetWorking.isOnline()) {
//
//                            Handler handler = new Handler(Looper.getMainLooper());
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    syncOfflineAdapter.SyncOfflineData();
//                                    if (!alertDialog.isShowing())
//                                        alertDialog.show();
//                                }
//                            });
//                        } else {
//
//                            Handler handler = new Handler(Looper.getMainLooper());
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    if (alertDialog.isShowing())
//                                        alertDialog.hide();
//                                    AUtils.warning(mContext, getResources().getString(R.string.no_internet_error));
//                                }
//                            });
//                        }
//                    }
//                });


            }
        });

        syncOfflineAdapter.setSyncOfflineListener(new SyncOfflineAdapterClass.SyncOfflineListener() {
            @Override
            public void onSuccessCallback() {
//                if (alertDialog.isShowing())
//                    alertDialog.dismiss();

                //         inflateData();
            }

            @Override
            public void onFailureCallback() {
//                if (alertDialog.isShowing())
//                    alertDialog.hide();
//                AUtils.warning(mContext, getResources().getString(R.string.try_after_sometime));
            }

            @Override
            public void onErrorCallback() {
//                if (alertDialog.isShowing())
//                    alertDialog.hide();
                //   AUtils.warning(mContext, getResources().getString(R.string.serverError));
            }
        });
    }

    private void initData() {


        if (syncOfflineRepository.fetchCollectionCount().size() > 0) {
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

                        List<TableDataCountPojo.WorkHistory> mList = syncOfflineRepository.fetchCollectionCount();
                        if (mList.size() > 0) {

                            if (Integer.parseInt(mList.get(0).getStreetCollection()) > 0)
                                syncCount = mList.get(0).getStreetCollection();
                            else if (Integer.parseInt(mList.get(0).getLiquidCollection()) > 0)
                                syncCount = mList.get(0).getLiquidCollection();
                            else if (Integer.parseInt(mList.get(0).getLiquidCollection()) > 0)
                                syncCount = mList.get(0).getLiquidCollection();
                            else if (Integer.parseInt(mList.get(0).getHouseCollection()) > 0)
                                syncCount = mList.get(0).getHouseCollection();
                            else if (Integer.parseInt(mList.get(0).getDumpYardCollection()) > 0)
                                syncCount = mList.get(0).getDumpYardCollection();

                        } else {
                            Log.i("SyncCountTest", "run: " + syncCount + "  " + finalSyncCount);
                            syncCount = "0";
                            finalSyncCount = "0";

                        }

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

                                    if (syncOfflineRepository.fetchCollectionCount().size() == 0) {
                                        isSyncing[0] = false;
                                    }
                                    inflateData();
                                }
                            });
                        } else if (Integer.parseInt(finalSyncCount) == 0 && Integer.parseInt(syncCount) == 0) {

                            if (syncOfflineRepository.fetchCollectionCount().size() == 0) {
                                Log.i("SyncCountTest", "run: this if" + "  " + syncCount);
                                isSyncing[0] = false;

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.i("SyncCountTest", "run: " + "count");
                                        inflateData();

                                    }
                                });
                            }

                        }
                        finalSyncCount = syncCount;

                    }
                }
            });
        } else {
            inflateData();
        }

    }

    private void inflateData() {

        workHistoryList.clear();
        workHistoryList = syncOfflineRepository.fetchCollectionCount();
        int size = workHistoryList.size();
        Log.i("WorkHistorySize", "inflateData: " + workHistoryList);
        if (workHistoryList.size() > 0) {
            if (Integer.parseInt(workHistoryList.get(0).getHouseCollection()) > 0) {
                gridOfflineData.setVisibility(View.VISIBLE);
                if (Integer.parseInt(workHistoryList.get(0).getHouseCollection()) == 0) {
                    btnSyncOfflineData.setVisibility(View.VISIBLE);
                }

                layoutNoOfflineData.setVisibility(View.GONE);
                InflateOfflineWorkAdapter historyAdapter = new InflateOfflineWorkAdapter(mContext, workHistoryList);
                Log.e(TAG, "inflateData:- " + "OfflineWorkHistory=> " + workHistoryList);
                gridOfflineData.setAdapter(historyAdapter);
            } else if (Integer.parseInt(workHistoryList.get(0).getStreetCollection()) > 0) {

                gridOfflineData.setVisibility(View.VISIBLE);
                if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                    btnSyncOfflineData.setVisibility(View.VISIBLE);
                }

                layoutNoOfflineData.setVisibility(View.GONE);
                InflateOfflineWorkAdapter historyAdapter = new InflateOfflineWorkAdapter(mContext, workHistoryList);
                Log.e(TAG, "inflateData:- " + "OfflineWorkHistory=> " + workHistoryList);
                gridOfflineData.setAdapter(historyAdapter);

            } else if (Integer.parseInt(workHistoryList.get(0).getLiquidCollection()) > 0) {

                gridOfflineData.setVisibility(View.VISIBLE);
                if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                    btnSyncOfflineData.setVisibility(View.VISIBLE);
                }

                layoutNoOfflineData.setVisibility(View.GONE);
                InflateOfflineWorkAdapter historyAdapter = new InflateOfflineWorkAdapter(mContext, workHistoryList);
                Log.e(TAG, "inflateData:- " + "OfflineWorkHistory=> " + workHistoryList);
                gridOfflineData.setAdapter(historyAdapter);
            } else if (Integer.parseInt(workHistoryList.get(0).getDumpYardCollection()) > 0) {
                gridOfflineData.setVisibility(View.VISIBLE);
                if (!Prefs.getBoolean(AUtils.isSyncingOn, false)) {
                    btnSyncOfflineData.setVisibility(View.VISIBLE);
                }

                layoutNoOfflineData.setVisibility(View.GONE);
                InflateOfflineWorkAdapter historyAdapter = new InflateOfflineWorkAdapter(mContext, workHistoryList);
                Log.e(TAG, "inflateData:- " + "OfflineWorkHistory=> " + workHistoryList);
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
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (AUtils.isInternetAvailable()) {

        } else {
            btnSyncOfflineData.setVisibility(View.GONE);
            AUtils.showSnackBar(findViewById(R.id.parent));

        }
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

    @Override
    protected void onPause() {
        super.onPause();
        alertDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alertDialog.dismiss();
    }
}