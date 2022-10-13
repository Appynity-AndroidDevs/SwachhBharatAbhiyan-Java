package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.InflateOfflineWorkAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.SyncOfflineAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.login.InternetWorking;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
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
        alertDialog = AUtils.getUploadingAlertDialog(mContext);
    }

    private void registerEvents() {

        btnSyncOfflineData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!alertDialog.isShowing())
                    alertDialog.show();

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (InternetWorking.isOnline()) {
                            syncOfflineAdapter.SyncOfflineData();
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        } else {
                            if (alertDialog.isShowing())
                                alertDialog.hide();

                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    AUtils.warning(mContext, getResources().getString(R.string.no_internet_error));
                                }
                            });
                        }
                    }
                });


            }
        });

        syncOfflineAdapter.setSyncOfflineListener(new SyncOfflineAdapterClass.SyncOfflineListener() {
            @Override
            public void onSuccessCallback() {

                runOnUiThread(() -> {
                    AUtils.success(mContext, getString(R.string.success_offline_sync), Toast.LENGTH_LONG);
                    if (alertDialog.isShowing())
                        alertDialog.hide();
                });

                inflateData();
            }

            @Override
            public void onFailureCallback() {


                runOnUiThread(() -> {
                    AUtils.warning(mContext, getResources().getString(R.string.try_after_sometime));
                    if (alertDialog.isShowing())
                        alertDialog.hide();
                });

            }

            @Override
            public void onErrorCallback() {

                runOnUiThread(() -> {
                    AUtils.warning(mContext, getResources().getString(R.string.serverError));
                    if (alertDialog.isShowing())
                        alertDialog.hide();
                });

            }
        });
    }

    private void initData() {
        inflateData();
    }

    private void inflateData() {
        workHistoryList.clear();
        workHistoryList = syncOfflineRepository.fetchCollectionCount();
        if (workHistoryList.size() > 0) {
            gridOfflineData.setVisibility(View.VISIBLE);
            btnSyncOfflineData.setVisibility(View.VISIBLE);
            layoutNoOfflineData.setVisibility(View.GONE);
            InflateOfflineWorkAdapter historyAdapter = new InflateOfflineWorkAdapter(mContext, workHistoryList);
            Log.e(TAG, "inflateData:- " + "OfflineWorkHistory=> " + workHistoryList);
            gridOfflineData.setAdapter(historyAdapter);
        } else {
            runOnUiThread(() -> {
                gridOfflineData.setVisibility(View.GONE);
                btnSyncOfflineData.setVisibility(View.GONE);
                layoutNoOfflineData.setVisibility(View.VISIBLE);
            });


        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (AUtils.isInternetAvailable()) {

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    if (InternetWorking.isOnline()) {

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                AUtils.hideSnackBar();
                            }
                        });
                    } else {

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                AUtils.showSnackBar(findViewById(R.id.parent));
                            }
                        });
                    }
                }
            });


        } else {
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
