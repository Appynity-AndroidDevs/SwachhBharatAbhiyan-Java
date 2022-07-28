package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.AutocompleteContainSearch;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.AreaHouseAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.AttendanceAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.DumpYardAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaHousePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaPointPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionDumpYardPointPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.GarbageCollectionPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.GcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.ImagePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGarbageColectionPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineAttendanceRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineRepository;
import com.appynitty.swachbharatabhiyanlibrary.services.LocationMonitoringService;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.MyProgressDialog;
import com.riaylibrary.utils.LocaleHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.github.kobakei.materialfabspeeddial.FabSpeedDial;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class DumpSuperScannerActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    private final static String TAG = "DumpSuperScannerActivity";
    private final static int DUMP_YARD_DETAILS_REQUEST_CODE = 120;
    GarbageCollectionPojo garbageCollectionPojo;
    private Context mContext;
    private Toolbar toolbar;
    private ZBarScannerView scannerView;
    private FabSpeedDial fabSpeedDial;
   // private AutoCompleteTextView dumpAutoComplete;
    private TextInputLayout idIpLayout/*, dumpYardIdIpLayout*/;
    private AutoCompleteTextView idAutoComplete;
    private AreaHouseAdapterClass mHpAdapter;
    private Button submitBtn, permissionBtn;
    private View contentView;
    private boolean isActivityData;
    private ImagePojo imagePojo;
    private Boolean isScanQr;
    private static final String PREFS_NAME = "preferenceName";


    private HashMap<String, String> idHash;
    private List<CollectionAreaPointPojo> dumpHouseList;

    private DumpYardAdapterClass mDyAdapter;
    private AttendanceAdapterClass mAttendanceAdapter;


    private SyncOfflineRepository syncOfflineRepository;
    private SyncOfflineAttendanceRepository syncOfflineAttendanceRepository;
    private MyProgressDialog myProgressDialog;
    private ArrayList<Integer> mSelectedIndices;
    String selection;
    private String EmpType;
    private Spinner spinner;

    LocationMonitoringService locationMonitoringService;
    Location location;

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(newBase));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AUtils.MY_PERMISSIONS_REQUEST_CAMERA) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                checkCameraPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.CAMERA)) {

                AUtils.showPermissionDialog(mContext, "CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, AUtils.MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                });
            }
        } else if (requestCode == AUtils.MY_PERMISSIONS_REQUEST_LOCATION) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                checkLocationPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {

                AUtils.showPermissionDialog(mContext, "Location Service", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AUtils.MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    }
                });
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPreview();

    }

    @Override
    protected void onPause() {
        stopPreview();
        super.onPause();
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
    public void onBackPressed() {
        if (!isScanQr) {
            scanQR();
        } else {
            super.onBackPressed();

        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (AUtils.isInternetAvailable()) {
            AUtils.hideSnackBar();
        } else {
            AUtils.showSnackBar(findViewById(R.id.parent));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        AUtils.currentContextConstant = mContext;

        if (requestCode == DUMP_YARD_DETAILS_REQUEST_CODE && resultCode == RESULT_OK) {

            try {
                HashMap<String, String> map = (HashMap<String, String>) data.getSerializableExtra(AUtils.DUMPDATA.dumpDataMap);

                if (data.hasExtra(AUtils.REQUEST_CODE)) {
                    Type type = new TypeToken<ImagePojo>() {
                    }.getType();
                    imagePojo = new Gson().fromJson(Prefs.getString(AUtils.PREFS.IMAGE_POJO, null), type);

                    if (!AUtils.isNull(imagePojo)) {
                        isActivityData = true;
                    }
                }

                startSubmitQRAsyncTask(map);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initComponents() {
        generateId();
        registerEvents();
        initData();
//        setupFormats();
    }

    protected void generateId() {
        setContentView(R.layout.activity_dump_super_scanner);
        toolbar = findViewById(R.id.toolbar);

        mContext = DumpSuperScannerActivity.this;
        AUtils.currentContextConstant = mContext;

        EmpType = Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null); //added by Swapnil
        dumpHouseList = new ArrayList<>();
        mHpAdapter = new AreaHouseAdapterClass();

        fabSpeedDial = findViewById(R.id.flash_toggle);
        mAttendanceAdapter = new AttendanceAdapterClass();

        /*dumpYardIdIpLayout = findViewById(R.id.txt_dump_yard_id);
        dumpAutoComplete = findViewById(R.id.txt_dump_yard);
        dumpAutoComplete.setThreshold(0);
        dumpAutoComplete.setDropDownBackgroundResource(R.color.white);*/

        idAutoComplete = findViewById(R.id.txt_id_auto);
        idAutoComplete.setThreshold(0);
        idAutoComplete.setDropDownBackgroundResource(R.color.white);
        idAutoComplete.setSingleLine();

        idIpLayout = findViewById(R.id.txt_id_layout);

        idAutoComplete.setCursorVisible(false);

        /***** Rahul Rokade ****/

        submitBtn = findViewById(R.id.submit_button);
        permissionBtn = findViewById(R.id.grant_permission);
        contentView = findViewById(R.id.scanner_view);

        imagePojo = null;
        isActivityData = false;
        isScanQr = true;

        ViewGroup contentFrame = findViewById(R.id.qr_scanner);
        scannerView = new ZBarScannerView(mContext);
        scannerView.setLaserColor(getResources().getColor(R.color.colorPrimary));
        scannerView.setBorderColor(getResources().getColor(R.color.colorPrimary));
        contentFrame.addView(scannerView);

        EmpType = Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null);

        initToolbar();

//        syncServerRepository = new SyncServerRepository(AUtils.mainApplicationConstant.getApplicationContext()); //TODO
        syncOfflineRepository = new SyncOfflineRepository(AUtils.mainApplicationConstant.getApplicationContext());
        syncOfflineAttendanceRepository = new SyncOfflineAttendanceRepository(AUtils.mainApplicationConstant.getApplicationContext());

        locationMonitoringService = new LocationMonitoringService(this);

    }

    protected void initToolbar() {
        toolbar.setTitle(getResources().getString(R.string.title_activity_qrcode_scanner));
        setSupportActionBar(toolbar);
       /* dumpYardIdIpLayout.setVisibility(View.GONE);*/
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    protected void registerEvents() {
        Log.d(TAG, "registerEvents Id : " + idAutoComplete.getText().toString());


        /*submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Boolean idValid = isAutoCompleteValid(idAutoComplete, idHash);

                    if (idValid) {
                        submitQRcode(idHash.get(idAutoComplete.getText().toString().toLowerCase()));

                        finish();
                        AUtils.success(mContext,"dump yard manually entered "+idAutoComplete.getText().toString() +" successfully", Toast.LENGTH_SHORT);
                    }

            }
        });*/

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()){
                    submitQRcode(idAutoComplete.getText().toString().toUpperCase(Locale.ROOT));
                    Log.e(TAG, "manually qr code entered is: " +idAutoComplete.getText().toString());
                    finish();
                    AUtils.success(mContext,"dump yard manually entered "+idAutoComplete.getText().toString() +" successfully", Toast.LENGTH_SHORT);

                }
            }
        });


        idAutoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused)
                    AUtils.showKeyboard((Activity) mContext);

                if (isFocused && isScanQr) {

                    hideQR();
                    AUtils.showKeyboard((Activity) mContext);
                } else {
                    idAutoComplete.clearListSelection();

                }
            }
        });


        mHpAdapter.setAreaHouseListener(new AreaHouseAdapterClass.AreaHouseListener() {
            @Override
            public void onSuccessCallBack() {

                inflateHpAutoComplete(mHpAdapter.getHpPojoList());
            }

            @Override
            public void onFailureCallBack() {
                AUtils.error(mContext, getResources().getString(R.string.serverError));
            }
        });

        idAutoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                mHpAdapter.fetchHpList("", "0");
            }
        });

        idAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idAutoComplete.setText(mHpAdapter.getHpPojoList().get(i).getHouseid());
                //idAutoComplete.setText("");

                inflateAutoComplete("0");
            }
        });

        idAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    inflateAutoComplete("0");
                }

                return false;

            }
        });

        permissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
            }
        });

        fabSpeedDial.getMainFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scannerView.getFlash()) {
                    scannerView.setFlash(false);
                    fabSpeedDial.getMainFab().setImageDrawable(getResources().getDrawable(R.drawable.ic_flash_on_indicator));
                } else {
                    scannerView.setFlash(true);
                    fabSpeedDial.getMainFab().setImageDrawable(getResources().getDrawable(R.drawable.ic_flash_off));
                }
            }
        });

    }

    protected void initData() {
        checkCameraPermission();

        if (!AUtils.isConnectedFast(mContext)) {
            AUtils.warning(mContext, getResources().getString(R.string.slow_internet));
        }

        Intent intent = getIntent();
        if (intent.hasExtra(AUtils.REQUEST_CODE)) {
            Type type = new TypeToken<ImagePojo>() {
            }.getType();
            imagePojo = new Gson().fromJson(Prefs.getString(AUtils.PREFS.IMAGE_POJO, null), type);

            if (!AUtils.isNull(imagePojo)) {
                isActivityData = true;
            }
        }
    }

    private boolean isValid(){
        if (idAutoComplete.getText().toString().trim().isEmpty()){
            AUtils.warning(mContext, "Please select dump yard id");
            return false;
        }
        return true;
    }

    private void submitQRcode(String houseid) {
        if (EmpType.matches("D")) {

            if (houseid.substring(0, 2).matches("^[LlWw]+$")) {
                AUtils.showDialog(mContext, getResources().getString(R.string.alert), getResources().getString(R.string.lwc_qr_alert), null);
            } else if (houseid.substring(0, 2).matches("^[SsSs]+$")) {
                AUtils.showDialog(mContext, getResources().getString(R.string.alert), getResources().getString(R.string.ssc_qr_warning), null);
            } else if (houseid.substring(0, 2).matches("^[HhPp]+$")) {
                AUtils.showDialog(mContext, getResources().getString(R.string.alert), getResources().getString(R.string.house_qr_alert), null);
            } else if (houseid.substring(0, 2).matches("^[GgPp]+$")) {
                AUtils.showDialog(mContext, getResources().getString(R.string.alert), getResources().getString(R.string.gp_qr_alert), null);
            } else if (houseid.substring(0, 2).matches("^[DdYy]+$")) {
                  // getDumpYardDetails(houseid);
                Log.e(TAG,"Dumpyard scan Id: "+ houseid);
                AUtils.success(mContext,"Dump yard id get successfully: "+houseid,Toast.LENGTH_SHORT);
                /*Prefs.putString(AUtils.HOUSE_ID, houseid);
                Prefs.putString(AUtils.HOUSE_ID_START, houseid);*/
                //editor.commit();

                submitDumpYardSuperAttendance(houseid);
               // submitDumpyardRepo(houseid);
                /*Prefs.getString(AUtils.HOUSE_ID,"");*/
                finish();
            }
        }

    }

    private void submitDumpYardSuperAttendance(final String houseNo) {
        Log.e(TAG,"Dumyard Supervisor : "+houseNo);
        Intent intent = new Intent(mContext, DashboardActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AUtils.dumpYardSuperId, houseNo);
        intent.putExtras(bundle);
        startActivityForResult(intent, DUMP_YARD_DETAILS_REQUEST_CODE);
    }
    private void submitDumpyardRepo(final String houseNo) {
        Log.e(TAG,"Dumyard Supervisor Repo : "+houseNo);
        Intent intent = new Intent(mContext, SyncOfflineAttendanceRepository.class);
        Bundle bundle = new Bundle();
        bundle.putString(AUtils.HOUSE_ID, houseNo);
        intent.putExtras(bundle);
        startActivityForResult(intent, DUMP_YARD_DETAILS_REQUEST_CODE);
    }

    private void dumpIdDropDownList(){

    }

    private void showPopup(String id, GcResultPojo pojo) {


        Log.d(TAG, "showPopup: " + new Gson().toJson(pojo));
        Log.d(TAG, "showPopup: " + id);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        View view = View.inflate(mContext, R.layout.layout_qr_result, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        if (!dialog.isShowing()) {

            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }

            dialog.show();

        }

        final String responseStatus = pojo.getStatus();
        TextView ownerName = view.findViewById(R.id.house_owner_name);
        TextView ownerMobile = view.findViewById(R.id.house_owner_mobile);
        TextView houseId = view.findViewById(R.id.house_id);
        TextView collectionStatus = view.findViewById(R.id.collection_status);
        ImageView statusImage = view.findViewById(R.id.response_image);
        Button doneBtn = view.findViewById(R.id.done_btn);

        if (responseStatus.equals(AUtils.STATUS_ERROR)) {
            statusImage.setImageDrawable(getDrawable(R.drawable.ic_cancel_red));
            doneBtn.setText(getString(R.string.retry_txt));
            houseId.setText(null);
            if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals("2")) {
                collectionStatus.setText(pojo.getMessageMar());
            } else {
                collectionStatus.setText(pojo.getMessage());
            }
            ownerName.setText(id.toUpperCase());
            ownerMobile.setText(null);
        } else if (responseStatus.equals(AUtils.STATUS_SUCCESS)) {
            if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals("2")) {
                ownerName.setText(pojo.getNameMar());
            } else {
                ownerName.setText(pojo.getName());
            }
            if (id.substring(0, 2).matches("^[DdYy]+$")) {
                ownerMobile.setText(pojo.getMobile());
                collectionStatus.setText(getResources().getString(R.string.garbage_deposit_completed));
            }

            houseId.setText(id);

            Type type = new TypeToken<ImagePojo>() {
            }.getType();

            imagePojo = new Gson().fromJson(
                    Prefs.getString(AUtils.PREFS.IMAGE_POJO, null), type);
        }

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                restartPreview();
                if (responseStatus.equals(AUtils.STATUS_SUCCESS)) {
                    imagePojo = null;
                    Prefs.putString(AUtils.PREFS.IMAGE_POJO, null);
                    finish();
                }
            }
        });

    }

    private void checkCameraPermission() {
        if (AUtils.isCameraPermissionGiven(mContext)) {
            startPreview();
            contentView.setVisibility(View.VISIBLE);
            permissionBtn.setVisibility(View.GONE);
            checkLocationPermission();
        } else {
            contentView.setVisibility(View.GONE);
            permissionBtn.setVisibility(View.VISIBLE);
        }
    }

    private void scanQR() {
        isScanQr = true;
        startCamera();
        contentView.setVisibility(View.VISIBLE);
        submitBtn.setVisibility(View.GONE);
        /*dumpYardIdIpLayout.setVisibility(View.GONE);
        dumpAutoComplete.setVisibility(View.GONE);
        dumpAutoComplete.setText("");*/
        idAutoComplete.clearFocus();
        idAutoComplete.setText("");
        scannerView.setAutoFocus(true);

    }

    private void hideQR() {
        isScanQr = false;
        stopCamera();
        contentView.setVisibility(View.GONE);
        submitBtn.setVisibility(View.VISIBLE);
        /*dumpYardIdIpLayout.setVisibility(View.VISIBLE);
        dumpAutoComplete.setVisibility(View.VISIBLE);
        dumpAutoComplete.requestFocusFromTouch();
        dumpAutoComplete.setSelected(true);*/

    }

    @SuppressLint("MissingPermission")
    private void checkLocationPermission() {

        if (AUtils.isLocationPermissionGiven(mContext)) {
            //You already have the permission, just go ahead.
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            boolean GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!GpsStatus) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }//else{
            // AUtils.saveLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
            // }
        }
    }

    public void handleResult(Result result) {
        Log.d(TAG, "handleResult: " + new Gson().toJson(result));
        submitQRcode(result.getContents());
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

//        restartPreview();
    }

    private void startPreview() {
//        areaAutoComplete.setVisibility(View.GONE);
        scannerView.startCamera();
        scannerView.resumeCameraPreview(this);
    }

    private void stopPreview() {
        scannerView.stopCameraPreview();
        scannerView.stopCamera();
    }

    private void startCamera() {
        scannerView.startCamera();
    }

    private void stopCamera() {
        scannerView.stopCamera();
    }

    private void restartPreview() {

        stopPreview();
        startPreview();
    }


    private void startSubmitQRAsyncTask(final String houseNo, @Nullable final int garbageType, @Nullable final String gcType, @Nullable final String comment) {

        stopCamera();
        setGarbageCollectionPojo(houseNo, garbageType, gcType, comment);
//        if(AUtils.isInternetAvailable() && AUtils.isConnectedFast(mContext)) {
//            mAdapter.submitQR(garbageCollectionPojo);
//        }
//        else {
        Log.d(TAG, "startSubmitQRAsyncTask: " + new Gson().toJson(garbageCollectionPojo));
        insertToDB(garbageCollectionPojo);
//        }
    }

    private void startSubmitQRAsyncTask(HashMap<String, String> map) {

        stopCamera();
        setGarbageCollectionPojo(map);

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

//        if(AUtils.isInternetAvailable() && AUtils.isConnectedFast(mContext)) {
//            mAdapter.submitQR(garbageCollectionPojo);
//        } else {
        insertToDB(garbageCollectionPojo);
//        }
    }

    private void inflateAutoComplete(String areaId) {

            mHpAdapter.fetchHpList("", areaId);
    }


    /*private void inflateAreaAutoComplete(List<CollectionAreaPojo> pojoList) {

        areaHash = new HashMap<>();
        ArrayList<String> keyList = new ArrayList<>();
        for (CollectionAreaPojo pojo : pojoList) {
            areaHash.put(pojo.getArea().toLowerCase(), pojo.getId());
            keyList.add(pojo.getArea().trim());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, keyList);

    }*/

    private void inflateHpAutoComplete(List<CollectionAreaHousePojo> pojoList) {

        idHash = new HashMap<>();
        ArrayList<String> keyList = new ArrayList<>();
        for (CollectionAreaHousePojo pojo : pojoList) {
            idHash.put(pojo.getHouseNumber().toLowerCase(), pojo.getHouseid());
            keyList.add(pojo.getHouseNumber().trim());
        }

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, keyList);
        AutocompleteContainSearch adapter = new AutocompleteContainSearch(mContext, android.R.layout.simple_dropdown_item_1line, keyList);
        idAutoComplete.showDropDown();
        idAutoComplete.setThreshold(0);
        idAutoComplete.setAdapter(adapter);
        idAutoComplete.requestFocus();

    }

    private void inflateDyAutoComplete(List<CollectionDumpYardPointPojo> pojoList) {

        idHash = new HashMap<>();
        ArrayList<String> keyList = new ArrayList<>();
        for (CollectionDumpYardPointPojo pojo : pojoList) {
            idHash.put(pojo.getDyId().toLowerCase(), pojo.getDyId());
            keyList.add(pojo.getDyId().trim());
        }

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, keyList);
        AutocompleteContainSearch adapter = new AutocompleteContainSearch(mContext, android.R.layout.simple_dropdown_item_1line, keyList);

        idAutoComplete.setThreshold(0);
        idAutoComplete.setAdapter(adapter);
        idAutoComplete.requestFocus();
    }

    /*private Boolean isAutoCompleteValid(AutoCompleteTextView autoCompleteTextView, HashMap<String, String> hashMap) {
        try {
            return hashMap.containsKey(autoCompleteTextView.getText().toString().toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }*/

    private void setGarbageCollectionPojo(String houseNo, @Nullable final int garbageType, final String gcType, @Nullable final String comment) {
        garbageCollectionPojo = new GarbageCollectionPojo();
        garbageCollectionPojo.setId(houseNo);
        garbageCollectionPojo.setGarbageType(garbageType);
        garbageCollectionPojo.setComment(comment);
        garbageCollectionPojo.setGcType(gcType);

        /*location = locationMonitoringService.getLocation();


        if (!(location == null)) {
            double newlat = location.getLatitude();
            double newlng = location.getLongitude();
            garbageCollectionPojo.setDistance(AUtils.calculateDistance(mContext, newlat, newlng));
        }*/

        double newlat = Double.parseDouble(Prefs.getString(AUtils.LAT, "0"));
        double newlng = Double.parseDouble(Prefs.getString(AUtils.LONG, "0"));
        garbageCollectionPojo.setDistance(AUtils.calculateDistance(mContext, newlat, newlng));
        if (isActivityData) {
            garbageCollectionPojo.setAfterImage(imagePojo.getAfterImage());
            garbageCollectionPojo.setBeforeImage(imagePojo.getBeforeImage());
            garbageCollectionPojo.setComment(imagePojo.getComment());
            garbageCollectionPojo.setImage1(imagePojo.getImage1());
            garbageCollectionPojo.setImage2(imagePojo.getImage2());
            garbageCollectionPojo.setGpBeforImageTime("2022-05-04 15:32:24.038");
        }


    }

    private GarbageCollectionPojo getGarbageCollectionPojo() {
        Log.d(TAG, "getGarbageCollectionPojo: " + new Gson().toJson(garbageCollectionPojo));
        return garbageCollectionPojo;
    }

    private void setGarbageCollectionPojo(HashMap<String, String> map) {
        try {
            garbageCollectionPojo = new GarbageCollectionPojo();
            garbageCollectionPojo.setId(map.get(AUtils.DUMPDATA.dumpYardId));
            garbageCollectionPojo.setWeightTotal(Double.parseDouble(Objects.requireNonNull(map.get(AUtils.DUMPDATA.weightTotal))));
            garbageCollectionPojo.setWeightTotalDry(Double.parseDouble(Objects.requireNonNull(map.get(AUtils.DUMPDATA.weightTotalDry))));
            garbageCollectionPojo.setWeightTotalWet(Double.parseDouble(Objects.requireNonNull(map.get(AUtils.DUMPDATA.weightTotalWet))));
            garbageCollectionPojo.setGarbageType(-1);
            garbageCollectionPojo.setComment(null);
            double newlat = Double.parseDouble(Prefs.getString(AUtils.LAT, "0"));
            double newlng = Double.parseDouble(Prefs.getString(AUtils.LONG, "0"));
            garbageCollectionPojo.setDistance(AUtils.calculateDistance(mContext, newlat, newlng));
            if (isActivityData) {
                garbageCollectionPojo.setAfterImage(imagePojo.getAfterImage());
                garbageCollectionPojo.setBeforeImage(imagePojo.getBeforeImage());
                garbageCollectionPojo.setComment(imagePojo.getComment());
                garbageCollectionPojo.setImage1(imagePojo.getImage1());
                garbageCollectionPojo.setImage2(imagePojo.getImage2());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertToDB(GarbageCollectionPojo garbageCollectionPojo) {

        OfflineGarbageColectionPojo entity = new OfflineGarbageColectionPojo();

        entity.setReferenceID(garbageCollectionPojo.getId());
        if (garbageCollectionPojo.getId().substring(0, 2).matches("^[HhPp]+$")) {
            entity.setGcType("1");
        } else if (garbageCollectionPojo.getId().substring(0, 2).matches("^[GgPp]+$")) {
            entity.setGcType("2");
        } else if (garbageCollectionPojo.getId().substring(0, 2).matches("^[DdYy]+$")) {
            entity.setGcType("3");
            entity.setEmpType(Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null));   //Added by Swapnil
        } else if (garbageCollectionPojo.getId().substring(0, 2).matches("^[LlWw]+$")) {
            entity.setGcType("4");
        } else if (garbageCollectionPojo.getId().substring(0, 2).matches("^[SsSs]+$")) {
            entity.setGcType("5");
        }
        entity.setNote(garbageCollectionPojo.getComment());
        entity.setGarbageType(String.valueOf(garbageCollectionPojo.getGarbageType()));
        entity.setTotalGcWeight(String.valueOf(garbageCollectionPojo.getWeightTotal()));
        entity.setTotalDryWeight(String.valueOf(garbageCollectionPojo.getWeightTotalDry()));
        entity.setTotalWetWeight(String.valueOf(garbageCollectionPojo.getWeightTotalWet()));
        entity.setVehicleNumber(Prefs.getString(AUtils.VEHICLE_NO, ""));
        entity.setLong(Prefs.getString(AUtils.LONG, ""));
        entity.setLat(Prefs.getString(AUtils.LAT, ""));
//        entity.setGcDate(AUtils.getServerDateTime());
        entity.setGcDate(AUtils.getServerDateTimeLocal());
        entity.setDistance(String.valueOf(garbageCollectionPojo.getDistance()));

        entity.setIsOffline(AUtils.isInternetAvailable() && AUtils.isConnectedFast(mContext));

        if (isActivityData) {
//            entity.setAfterImage(imagePojo.getAfterImage());
//            entity.setBeforeImage(imagePojo.getBeforeImage());
//            entity.setComment(imagePojo.getComment());

            try {
                if (imagePojo.getImage1() != null && imagePojo.getImage2() != null) {
                    entity.setGpBeforImage(AUtils.getEncodedImage(imagePojo.getImage1(), this));
                    entity.setGpAfterImage(AUtils.getEncodedImage(imagePojo.getImage2(), this));
                    entity.setGpBeforImageTime(Prefs.getString(AUtils.BEFORE_IMAGE_TIME, null));
                    Log.e(TAG, "Images are there!");
                } else if (Prefs.contains(AUtils.BEFORE_IMAGE)) {
                    if (!Prefs.getString(AUtils.BEFORE_IMAGE, null).isEmpty() || !AUtils.isNullString(Prefs.getString(AUtils.BEFORE_IMAGE, null))) {
                        entity.setGpBeforImage(AUtils.getEncodedImage(Prefs.getString(AUtils.BEFORE_IMAGE, null), this));
                        entity.setGpAfterImage(AUtils.getEncodedImage(Prefs.getString(AUtils.AFTER_IMAGE, null), this));
                        entity.setGpBeforImageTime(Prefs.getString(AUtils.BEFORE_IMAGE_TIME, null));
                    }
                } else {
                    entity.setGpBeforImage("");
                    entity.setGpAfterImage("");
                    Log.e(TAG, "Images are null!");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        syncOfflineRepository.insertCollection(entity);
        Prefs.remove(AUtils.BEFORE_IMAGE);
        Prefs.remove(AUtils.AFTER_IMAGE);
        showOfflinePopup(garbageCollectionPojo.getId(), entity.getGcType());
    }

    private void showOfflinePopup(String pojo, String garbageType) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        View view = View.inflate(mContext, R.layout.layout_qr_result, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        if (!dialog.isShowing()) {
            dialog.show();

        }

        TextView ownerName = view.findViewById(R.id.house_owner_name);
        TextView houseTitle = view.findViewById(R.id.lbl_title);
        TextView collectionStatus = view.findViewById(R.id.collection_status);
        Button doneBtn = view.findViewById(R.id.done_btn);

        ownerName.setText(pojo);
        String value = "";

        switch (garbageType) {
            case "1":
                value = "House Id";
                break;

            case "2":
                value = "Garbage Point Id";
                break;
            case "3":
                value = "Dump yard Id  ";
                collectionStatus.setText(R.string.colectnStatus);
                break;
            case "-1":                              //added by Swapnil
                if (EmpType.matches("L"))
                    value = "Liquid waste Id  ";
                else if (EmpType.matches("S"))
                    value = "Street waste Id  ";
                break;
            /*case "4":
                value = "Liquid waste Id  ";
                break;
            case "5":
                value = "Street waste Id  ";
                break;*/
        }

        houseTitle.setText(value);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                restartPreview();
                finish();
            }
        });

    }
}