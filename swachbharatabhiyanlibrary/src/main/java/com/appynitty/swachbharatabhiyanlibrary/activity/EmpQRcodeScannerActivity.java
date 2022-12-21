package com.appynitty.swachbharatabhiyanlibrary.activity;

import static android.os.Environment.DIRECTORY_PICTURES;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpQrLocationAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.ChooseActionPopUp;
import com.appynitty.swachbharatabhiyanlibrary.gis.HouseLocationEntity;
import com.appynitty.swachbharatabhiyanlibrary.gis.HousePointRepo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.QrLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.EmpSyncServerRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyApplication;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.MyProgressDialog;
import com.riaylibrary.utils.LocaleHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.github.kobakei.materialfabspeeddial.FabSpeedDial;

public class EmpQRcodeScannerActivity extends AppCompatActivity {

    private final static String TAG = "EmpQRcodeScannerActivity";
    final Executor executor = Executors.newSingleThreadExecutor();
    /**
     * permission code
     */
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1013;
    private Context mContext;
    private Toolbar toolbar;
    //    private ZBarScannerView scannerView;
    private DecoratedBarcodeView scannerView;
    private FabSpeedDial fabSpeedDial;
    private TextInputLayout idIpLayout;
    private AutoCompleteTextView idAutoComplete;
    private RadioGroup collectionRadioGroup;
    private String radioSelection, mHouse_id, mImagePath, encodedImage, mLat, mLon;
    private Button submitBtn, permissionBtn;
    private View contentView;
    private Boolean isScanQr;
    private ChooseActionPopUp chooseActionPopUp;
    private EmpQrLocationAdapterClass empQrLocationAdapter;
    private QrLocationPojo qrLocationPojo;
    private static final int REQUEST_CAMERA = 22;
    private static int CAMERA_FACING_BACK;
    private EmpSyncServerRepository empSyncServerRepository;
    private HousePointRepo housePointRepo;
    private Gson gson;
    Camera mCamera;
    private String lastText;
    private BeepManager beepManager;
    boolean isChecked = true;
    private MyProgressDialog myProgressDialog;
    private ArrayList<Integer> mSelectedIndices;
    private boolean isFlashOn, isNewConstruction;
    private UserDetailPojo userDetailPojo;

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
                    @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
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

        } else if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFile();
            } else {
                // Permission Denied
                Toast.makeText(EmpQRcodeScannerActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.resume();
//        startPreview();
    }

    @Override
    protected void onPause() {
//        stopPreview();
        scannerView.pause();
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
            if (isChecked) {
                //not assign
            } else {

                AUtils.showSnackBar(findViewById(R.id.parent));
            }

        }
    }

    private void initComponents() {
        generateId();
        registerEvents();
        initData();
//        setupFormats();
    }

    protected void generateId() {
        setContentView(R.layout.emp_activity_qrcode_scanner);
        toolbar = findViewById(R.id.toolbar);
        mHouse_id = "";
        mLat = "";
        mLon = "";
        mContext = EmpQRcodeScannerActivity.this;
        AUtils.currentContextConstant = mContext;
        myProgressDialog = new MyProgressDialog(mContext, R.drawable.progress_bar, false);

        empSyncServerRepository = new EmpSyncServerRepository(mContext);
        housePointRepo = new HousePointRepo(getApplication());

        Type type = new TypeToken<UserDetailPojo>() {
        }.getType();
        userDetailPojo = new Gson().fromJson(Prefs.getString(AUtils.PREFS.USER_DETAIL_POJO, null), type);
        gson = new Gson();

        fabSpeedDial = findViewById(R.id.flash_toggle);
        idAutoComplete = findViewById(R.id.txt_id_auto);
        idAutoComplete.setThreshold(0);
        idAutoComplete.setDropDownBackgroundResource(R.color.white);
        idAutoComplete.setSingleLine();

        idIpLayout = findViewById(R.id.txt_id_layout);
        collectionRadioGroup = findViewById(R.id.collection_radio_group);
        radioSelection = AUtils.RADIO_SELECTED_HP;

        submitBtn = findViewById(R.id.submit_button);
        permissionBtn = findViewById(R.id.grant_permission);
        contentView = findViewById(R.id.scanner_view);

        isScanQr = true;

        /*ViewGroup contentFrame = (ViewGroup) findViewById(R.id.qr_scanner);
        scannerView = new ZBarScannerView(mContext);
        scannerView.setAutoFocus(true);
        scannerView.setLaserColor(getResources().getColor(R.color.colorPrimary));
        scannerView.setBorderColor(getResources().getColor(R.color.colorPrimary));
        contentFrame.addView(scannerView);*/
        scannerView = findViewById(R.id.barcode_scanner);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        scannerView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        scannerView.initializeFromIntent(getIntent());
        scannerView.decodeContinuous(callback);

        beepManager = new BeepManager(EmpQRcodeScannerActivity.this);

        initToolbar();

        chooseActionPopUp = new ChooseActionPopUp(mContext);
        empQrLocationAdapter = new EmpQrLocationAdapterClass();
        qrLocationPojo = new QrLocationPojo();
    }

    protected void initToolbar() {
        toolbar.setTitle(getResources().getString(R.string.title_activity_qrcode_scanner));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }

            Log.e(TAG, "barcodeResult: " + result.getText() + ", Bitmap: " + result.getBitmap());
            lastText = result.getText();
            scannerView.setStatusText(result.getText());
            beepManager.playBeepSoundAndVibrate();
            openMapsActivityForResult();
//            handleResult(result);
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    public void openMapsActivityForResult() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("lat", Prefs.getString(AUtils.LAT, null));
        intent.putExtra("lon", Prefs.getString(AUtils.LONG, null));
        mapsActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> mapsActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                scannerView.pause();
                mLat = result.getData().getStringExtra("newLat");
                mLon = result.getData().getStringExtra("newLong");
                isNewConstruction = result.getData().getBooleanExtra("isNewConstruction", false);
                Log.e(TAG, "onActivityResult: newLat: " + mLat + ", newLong: " + mLon + ", isNewConstruction: " + isNewConstruction);

                handleResult(scannerView.getStatusView().getText().toString());
//                        doSomeOperations(str);
            }
        }
    });


    ActivityResultLauncher<Intent> cameraActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                scannerView.pause();
                Log.e(TAG, "onActivityResult: " + result.getData().getStringExtra("image_path"));
                try {
                    onCaptureImageResult(result.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    protected void registerEvents() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (!idAutoComplete.getText().toString().matches("")) {
                        submitQRcode(idAutoComplete.getText().toString());
                    } else {
                        if (getGCType().equals(AUtils.HP_AREA_TYPE_ID))
                            AUtils.error(mContext, mContext.getResources().getString(R.string.hp_area_validation_emp));
                        else if (getGCType().equals(AUtils.GP_AREA_TYPE_ID))
                            AUtils.error(mContext, mContext.getResources().getString(R.string.gp_area_validation_emp));
                        else
                            AUtils.error(mContext, mContext.getResources().getString(R.string.dy_area_validation_emp));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        collectionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioGroupId = radioGroup.getCheckedRadioButtonId();

                idAutoComplete.setText("");
                AUtils.showKeyboard((Activity) mContext);

                idAutoComplete.setText("");

                if (radioGroupId == R.id.house_collection_radio) {
                    idIpLayout.setHint(getResources().getString(R.string.house_number_hint));
                    radioSelection = AUtils.RADIO_SELECTED_HP;
                    idAutoComplete.setText("HPSBA");
                    idAutoComplete.setSelection(5);
                }

                /*if (radioGroupId == R.id.point_collection_radio) {
                    idIpLayout.setHint(getResources().getString(R.string.gp_id_hint));
                    radioSelection = AUtils.RADIO_SELECTED_GP;
                    idAutoComplete.setText("GPSBA");
                    idAutoComplete.setSelection(5);
                }*/

                if (radioGroupId == R.id.dump_yard_radio) {
                    idIpLayout.setHint(getResources().getString(R.string.dy_id_hint));
                    radioSelection = AUtils.RADIO_SELECTED_DY;
                    idAutoComplete.setText("DYSBA");
                    idAutoComplete.setSelection(5);
                }
            }
        });

        idAutoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) AUtils.showKeyboard((Activity) mContext);

                if (isFocused && isScanQr) {
                    hideQR();
                    idAutoComplete.setText("HPSBA");
                    idAutoComplete.setSelection(4);
                    AUtils.showKeyboard((Activity) mContext);
                } else {
                    idAutoComplete.clearListSelection();
                }
            }
        });

        idAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AUtils.hideKeyboard((Activity) mContext);
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
                if (hasFlash()) {
                    if (isFlashOn) {
                        isFlashOn = false;
                        scannerView.setTorchOff();
                        fabSpeedDial.getMainFab().setImageDrawable(getResources().getDrawable(R.drawable.ic_flash_off));
                    } else {
                        isFlashOn = true;
                        scannerView.setTorchOn();
                        fabSpeedDial.getMainFab().setImageDrawable(getResources().getDrawable(R.drawable.ic_flash_on_indicator));
                    }

                } else {
                    fabSpeedDial.setVisibility(View.GONE);
                }
            }
        });

        chooseActionPopUp.setChooseActionPopUpDialogListener(new ChooseActionPopUp.ChooseActionPopUpDialogListener() {
            @Override
            public void onChooseActionPopUpDismissed(String data, int actionType) {
                String mId = data;
                switch (actionType) {
                    case ChooseActionPopUp.ADD_DETAILS_BUTTON_CLICKED:
                        if (AUtils.isInternetAvailable() && AUtils.isConnectedFast(mContext)) {
                            submitOnDetails(mId, getGCType(mId.trim()));
                        } else {
                            if (!AUtils.isConnectedFast(mContext)) {
                                AUtils.warning(mContext, getResources().getString(R.string.feature_unavailable_error), Toast.LENGTH_LONG);
                            } else {
                                AUtils.info(mContext, getResources().getString(R.string.no_internet_error), Toast.LENGTH_LONG);
                            }

                            restartPreview();
                        }
                        break;
                    case ChooseActionPopUp.SKIP_BUTTON_CLICKED:
                        submitOnSkip(mHouse_id.trim().toString());
                        Log.e(TAG, "onChooseActionPopUpDismissed: " + mId);
                        break;
                }
            }
        });

        empQrLocationAdapter.setEmpQrLocationListner(new EmpQrLocationAdapterClass.EmpQrLocationListner() {
            @Override
            public void onSuccessCallback(ResultPojo resultPojo) {
                myProgressDialog.dismiss();
                String message = "";
                if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals("2")) {
                    message = resultPojo.getMessageMar();
                } else {
                    message = resultPojo.getMessage();
                }

                if (/*resultPojo.getStatus().equals(AUtils.STATUS_SUCCESS) ||*/ AUtils.isNullString(resultPojo.getStatus())) {
//                    AUtils.success(mContext, message, Toast.LENGTH_LONG);
                    AUtils.error(mContext, "No response!", Toast.LENGTH_LONG);
                    finish();
                } else {
//                    AUtils.error(mContext, message, Toast.LENGTH_LONG);
                    AUtils.success(mContext, message, Toast.LENGTH_LONG);
                    finish();
                }
            }

            @Override
            public void onFailureCallback() {
                myProgressDialog.dismiss();
                AUtils.error(mContext, getResources().getString(R.string.something_error), Toast.LENGTH_LONG);
            }

            @Override
            public void onErrorCallback() {
                myProgressDialog.dismiss();
                AUtils.error(mContext, getResources().getString(R.string.serverError), Toast.LENGTH_LONG);
            }
        });

    }

    protected void initData() {

        checkCameraPermission();

    }

    private void submitQRcode(String houseid) {
        Log.e(TAG, "submitQRcode: " + houseid);
        mHouse_id = houseid;
        if (validSubmitId(houseid.toLowerCase())) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                takePhotoImageViewOnClick();
//            }
//            showActionPopUp(houseid);

        } else {
            AUtils.warning(EmpQRcodeScannerActivity.this, mContext.getResources().getString(R.string.qr_error));
            restartPreview();
        }
    }

    private Boolean validSubmitId(String id) {

        Log.e(TAG, "validSubmitId: " + id);
        /* if (Prefs.getBoolean(AUtils.PREFS.IS_SAME_LOCALITY, false)) {*/
        return id.substring(0, 2).matches("^[HhPp]+$") || id.matches("gpsba[0-9]+$") || id.matches("lwsba[0-9]+$") || id.matches("sssba[0-9]+$") || id.matches("dysba[0-9]+$");
       /* } else {
            return false;
        }*/

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


    private void isStoragePermissionGiven() {
        if (AUtils.isStoragePermissionGiven(mContext)) {
            //You already have the permission, just go ahead.
//            isLocationPermissionGiven();
        }
    }

    //
    private void scanQR() {
        isScanQr = true;
        startCamera();
        contentView.setVisibility(View.VISIBLE);
        submitBtn.setVisibility(View.GONE);
        collectionRadioGroup.setVisibility(View.GONE);
        idAutoComplete.clearFocus();
        idAutoComplete.setText("");
        idIpLayout.setHint(getResources().getString(R.string.hp_gp_id_hint));

    }

    private void hideQR() {
        isScanQr = false;
        stopCamera();
        contentView.setVisibility(View.GONE);
        submitBtn.setVisibility(View.VISIBLE);
        collectionRadioGroup.setVisibility(View.VISIBLE);

        if (radioSelection.equals(AUtils.RADIO_SELECTED_HP)) {
            idIpLayout.setHint(getResources().getString(R.string.house_number_hint));
        } else {
            idIpLayout.setHint(getResources().getString(R.string.gp_id_hint));
        }
    }

    @SuppressLint("MissingPermission")
    private void checkLocationPermission() {

        if (AUtils.isLocationPermissionGiven(mContext)) {
            //You already have the permission, just go ahead.
            isStoragePermissionGiven();
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            boolean GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!GpsStatus) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            } //else {
            //AUtils.saveLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
            //}
        }
    }


    private void takePhotoImageViewOnClick() {
//        setContentView(R.layout.layout_blank);
//        stopPreview();
//        scannerView.stopCamera();
//        scannerView.stopCameraPreview();
        Intent i = new Intent(EmpQRcodeScannerActivity.this, CameraActivity.class);
//        startActivityForResult(i, REQUEST_CAMERA);
        cameraActivityLauncher.launch(i);
    }

    public void handleResult(String result) {
        Log.e(TAG, "handleResult: " + result);
        mHouse_id = result;
//        takePhotoImageViewOnClick();
        Intent i = new Intent(EmpQRcodeScannerActivity.this, CameraActivity.class);
        cameraActivityLauncher.launch(i);
    }

    private void startPreview() {
        scannerView.resume();
    }

    private void stopPreview() {
        scannerView.pause();
    }

    private void startCamera() {
        scannerView.resume();

    }

    private void stopCamera() {
        scannerView.pause();
    }

    private void restartPreview() {
        stopPreview();
        startPreview();
    }

    private void showActionPopUp(String id) {

        if (validSubmitId(id.toLowerCase())) {
            chooseActionPopUp.setData(id, mImagePath);
            chooseActionPopUp.setCanceledOnTouchOutside(false);
            chooseActionPopUp.show();


        } else AUtils.error(mContext, getResources().getString(R.string.invalid_qr_error));
    }

    private void submitOnSkip(String id) {
        try {
            qrLocationPojo.setReferanceId(id);
            qrLocationPojo.setLat(mLat);
            qrLocationPojo.setLong(mLon);

            qrLocationPojo.setName("");
            qrLocationPojo.setNameMar("");
            qrLocationPojo.setAddress("");
            qrLocationPojo.setZoneId("");
            qrLocationPojo.setWardId("");
            qrLocationPojo.setAreaId("");
            qrLocationPojo.setHouseNumber("");
            qrLocationPojo.setMobileno("");

            qrLocationPojo.setGcType(getGCType(id));
            qrLocationPojo.setDate(AUtils.getServerDateTime());
            qrLocationPojo.setQRCodeImage("data:image/jpeg;base64," + encodedImage);

            //SANATH : Checking lat before saving ( if lat is null or empty string )
            // Prefs.putString(AUtils.LAT, null);
            if (!AUtils.isNull(Prefs.getString(AUtils.LAT, null)) && !Prefs.getString(AUtils.LAT, null).equals("")) {
                startSubmitQRAsyncTask(qrLocationPojo);
            } else {
                ((MyApplication) AUtils.mainApplicationConstant).startLocationTracking();
//         //   AUtils.warning(mContext, mContext.getString(R.string.no_location_found_cant_save));
                ProgressDialog mProgressDialog = new ProgressDialog(mContext);
                mProgressDialog.setMessage(getResources().getString(R.string.fetching_location));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();


                executor.execute(new Runnable() {
                    @Override
                    public void run() {

                        boolean isLocationFound = false;
                        while (!isLocationFound) {
                            if (!AUtils.isNull(Prefs.getString(AUtils.LAT, null)) && !Prefs.getString(AUtils.LAT, null).equals("")) {
                                isLocationFound = true;
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {

                                        startSubmitQRAsyncTask(qrLocationPojo);
                                        mProgressDialog.dismiss();

                                    }
                                });

                            }
                        }
                    }
                });

                //  AUtils.warning(mContext, getResources().getString(R.string.no_location_found_cant_save));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitOnDetails(String refId, String gctype) {
        Intent intent = new Intent(mContext, EmpAddLocationDetailsActivity.class);
        intent.putExtra(AUtils.ADD_DETAILS_TYPE_KEY, AUtils.NondaniLocation.OPEN_FORM_TYPE);
        intent.putExtra(AUtils.NondaniLocation.REFERENCE_ID, refId);
        intent.putExtra(AUtils.NondaniLocation.SUBMIT_TYPE, gctype);
        startActivityForResult(intent, AUtils.ADD_DETAILS_REQUEST_KEY);
    }

    private void startSubmitQRAsyncTask(QrLocationPojo pojo) {
        myProgressDialog.show();
        pojo.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, ""));
        insertToDB(pojo);
        stopCamera();
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        AUtils.currentContextConstant = mContext;

        if (requestCode == AUtils.ADD_DETAILS_REQUEST_KEY && resultCode == RESULT_OK) {
            finish();
        } else if (requestCode == REQUEST_CAMERA) {
            if (data != null) {
                String message = data.getStringExtra("image_path");
                Log.e(TAG, "onActivityResult: " + message);
                try {
                    onCaptureImageResult(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
//        }

        super.onActivityResult(requestCode, resultCode, data);

    }*/

    private String getGCType() {
        String areaType = AUtils.GP_AREA_TYPE_ID;
        if (radioSelection.equals(AUtils.RADIO_SELECTED_HP)) {
            areaType = AUtils.HP_AREA_TYPE_ID;
        } else if (radioSelection.equals(AUtils.RADIO_SELECTED_DY)) {
            areaType = AUtils.DY_AREA_TYPE_ID;
        }
        return areaType;
    }

    private String getGCType(String refId) {
        if (refId.substring(0, 2).toLowerCase().matches("^[hp]+$")) return "1";
        else if (refId.substring(0, 2).toLowerCase().matches("^[gp]+$")) return "2";
        else if (refId.substring(0, 2).toLowerCase().matches("^[dy]+$")) return "3";
        else if (refId.substring(0, 2).toLowerCase().matches("^[lw]+$")) return "4";
        else if (refId.substring(0, 2).toLowerCase().matches("^[ss]+$")) return "5";
        else return "0";
    }

    private void insertToDB(QrLocationPojo pojo) {

        Type type = new TypeToken<QrLocationPojo>() {
        }.getType();
        empSyncServerRepository.insertEmpSyncServerEntity(gson.toJson(pojo, type));


        String str = pojo.getReferanceId();
        String[] part = str.split("(?<=\\D)(?=\\d)");
        System.out.println(part[0]);
        System.out.println(part[1]);

        HouseLocationEntity houseLocationEntity = new HouseLocationEntity();
        houseLocationEntity.setHouseId(part[1]);
        houseLocationEntity.setLocationPoint(pojo.getLat() + " " + pojo.getLong());
        houseLocationEntity.setUserName(userDetailPojo.getName());
        housePointRepo.insert(houseLocationEntity);
        myProgressDialog.dismiss();
        if (isChecked) {
            if (!AUtils.isInternetAvailable()) {
                Toast.makeText(mContext, "Submitted successfully", Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }

    private void onCaptureImageResult(Intent data) throws IOException {

        String imagePath = data.getStringExtra("image_path");
        String compressedImagePath = compressImage(imagePath); // important code for compressing an image

        Log.e(TAG, "onCaptureImageResult: compressedImagePath:- " + compressedImagePath);
        mImagePath = compressedImagePath;
        showActionPopUp(mHouse_id);
        Bitmap newBitmap = AUtils.writeOnImage(mContext, AUtils.getDateAndTime(), mHouse_id, mImagePath, mLat, mLon);


        Uri uri = getImageUri(mContext, newBitmap);
        String str = getRealPathFromURI(String.valueOf(uri));
        compressedImagePath = compressImage(str);

        Log.e(TAG, "onCaptureImageResult: realPath: " + str);
        newBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(compressedImagePath)));


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos); // bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();

        // dpi set by rahul
        setDpi(byteArrayImage, 160);

        long lengthbmp = byteArrayImage.length;
        Log.e(TAG, "onCaptureImageResult: Final-size: " + lengthbmp);

        encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        Log.d(TAG, "onCaptureImageResult: Base64:- " + encodedImage);

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) throws IOException {

        File tempDir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + "/.temp/");
        tempDir.mkdir();
        File tempFile = File.createTempFile("tmp_" + System.currentTimeMillis(), ".jpg", tempDir);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        byte[] bitmapData = bytes.toByteArray();

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(bitmapData);
        fos.flush();
        fos.close();
        return Uri.fromFile(tempFile);
    }


    public String compressImage(String imageUri) {

        String filePath = imageUri;
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float maxHeight = 1280.0f;
        float maxWidth = 1707.0f;

        int bounding = dpToPx(250);
        Log.i("Test", "bounding = " + bounding);
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = imageUri;
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }


    private void readFile() {

        Log.e(TAG, "readFile: reading the photo file");
    }

    public int setDpi(byte[] imageData, int dpi) {
        imageData[13] = 1;
        imageData[14] = (byte) (dpi >> 8);
        imageData[15] = (byte) (dpi & 0xff);
        imageData[16] = (byte) (dpi >> 8);
        imageData[17] = (byte) (dpi & 0xff);

        /*DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int dpiClassification = dm.densityDpi;
        float xDpi = dm.xdpi;
        float yDpi = dm.ydpi;*/
        return dpi;
    }

    private boolean hasFlash() {
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}