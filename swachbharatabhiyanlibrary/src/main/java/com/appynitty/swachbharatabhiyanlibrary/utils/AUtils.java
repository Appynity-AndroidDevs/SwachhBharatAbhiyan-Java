package com.appynitty.swachbharatabhiyanlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.activity.DashboardActivity;
import com.appynitty.swachbharatabhiyanlibrary.activity.EmpDashboardActivity;
import com.appynitty.swachbharatabhiyanlibrary.activity.WasteDashboardActivity;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.AppGeoAreaAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpSyncServerAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.ShareLocationAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.SyncServerAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.entity.LastLocationEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.AppGeoArea;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LanguagePojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.LastLocationRepository;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.PolyUtil;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.CommonUtils;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AUtils extends CommonUtils {

    //    Local URL
//    public static final String SERVER_URL = "http://192.168.200.4:6077/";
//    public static final String SERVER_URL = "http://192.168.200.3:6560/";

    //  Advanced Ghanta Gadi Live URL
    // public static final String SERVER_URL = "http://202.65.157.253:6560";
//    public static final String SERVER_URL = "http://202.65.157.254:6560";


    /**
     * http://202.65.157.254:6590/
     * https://akot.ictsbm.com/
     * akot special
     * app Id : 3127
     ***/
    // public static final String SERVER_URL = "http://202.65.157.254:6590"; //akot

    /*//Nagpur staging server url
    public static final String SERVER_URL = "http://183.177.126.33:6561";*/

    /*//Nagpur live server url
    public static final String SERVER_URL = "http://202.65.157.253:6561";*/
//    public static final String SERVER_URL = "http://202.65.157.254:6561";

    //   public static final String SERVER_URL = "http://202.65.157.254:6590";

    public static final String SERVER_URL = "https://ictsbm.com:30443";
    //   public static final String SERVER_URL = "http://202.65.157.254:6590";

    /***
     * Staging portap
     * */


    //    Staging URL
//    public static final String SERVER_URL = "http://115.115.153.117:4044/";
//    public static final String SERVER_URL = "http://202.65.157.254:6560";
    //public static final String SERVER_URL = "http://202.65.157.254:5049";   //Important note: Ballarpur port = 5047, Wanadongri = 5048, Shrirampur = 5049

//    public static final String SERVER_URL = "http://183.177.126.33:6560/";

    //Relese URL
//    public static final String SERVER_URL = "https://ghantagadi.in:444/";

    //    Relese Backup URL
//    public static final String SERVER_URL = "http://202.65.157.253:4044/";

    //Testingserver
//    public static final String SERVER_URL = "http://202.65.157.254:4055/";


    //    General Constant
    public static final String STATUS_SUCCESS = "success";

    public static final String STATUS_ERROR = "error";

    public static final String CONTENT_TYPE = "application/json";

    public static final String APP_ID = "AppId";
    public static final String VERSION_CODE = "AppVersion";

    public static final String DIALOG_TYPE_VEHICLE = "DialogTypeVehicle";
    public static final String DIALOG_NUMBER_VEHICLE = "DialogNumberVehicle";
    public static final String DIALOG_TYPE_LANGUAGE = "Dialog_Type_Language";

    public static final String DEFAULT_LANGUAGE_ID = LanguageConstants.ENGLISH;

    public static final int SPLASH_SCREEN_TIME = 3000;

    public static final int MY_RESULT_REQUEST_QR = 55;

    public static final String REQUEST_CODE = "RequestCode";

    public static final String VEHICLE_ID = "VehicleId";

    public static final String LAT = "Lat";
    public static final String LONG = "Long";
    public static final String HOUSE_ID = "ReferanceId";
    public static final String HOUSE_ID_START = "ReferanceId";

    public static final int LOCATION_INTERVAL = 5000;
    //    public static final int LOCATION_INTERVAL = 1000 * 60 * 10; //10 Minute
    public static final int FASTEST_LOCATION_INTERVAL = 5000;

    public static final String HISTORY_DETAILS_DATE = "HistoryDetailsDate";
    public static final String RADIO_SELECTED_HP = "house_point";
    public static final String RADIO_SELECTED_LW = "liquid_point";
    public static final String RADIO_SELECTED_SW = "street_point";

    public static final String RADIO_SELECTED_GP = "garbage_point";
    public static final String RADIO_SELECTED_DY = "garbage_dump_yard";

    public static final String isFromLogin = "isFromLogin";
    public static final String dumpYardId = "dumpYardId";
    public static final String dumpYardSuperId = "dumpYardSuperId";

    public static final String SERVER_DATE_FORMATE = "MM-dd-yyyy";
    public static final String SERVER_DATE_FORMATE_LOCAL = "yyyy-MM-dd";
    // public static final String EMP_TYPE = "EmpType";
    private static final String EMP_SERVER_DATE_FORMATE = "dd-MM-yyyy";

    private static final String TITLE_DATE_FORMATE = "dd MMM yyyy";

    private static final String SEMI_MONTH_FORMATE = "MMM";
    private static final String DATE_VALUE_FORMATE = "dd";

    private static final String SERVER_TIME_FORMATE = "hh:mm a";
    private static final String SERVER_TIME_24HR_FORMATE = "HH:mm";

    public static final String SERVER_DATE_TIME_FORMATE = "MM-dd-yyyy HH:mm:ss";
    public static final String SERVER_DATE_TIME_FORMATE_LOCAL = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final long LOCATION_INTERVAL_MINUTES = 10 * 60 * 1000;

    public static final String VEHICLE_NO = "VehicleNumber";

    private static final String TAG = "AUtils";

    public static final String HP_AREA_TYPE_ID = "1";

    public static final String GP_AREA_TYPE_ID = "2";

    public static final String DY_AREA_TYPE_ID = "3";
    public static final String DY_SUPERVISOR_TYPE_ID = "6";

    public final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    public static final String TAG_HTTP_RESPONSE = "HTTPResponse_Error";
    public static final String ADD_DETAILS_TYPE_KEY = "ADD_DETAILS_TYPE_KEY";
    public static final int ADD_DETAILS_REQUEST_KEY = 8986;

    public static final String DATABASE_NAME = "db_sba_offline";
    public static final String LOCATION_TABLE_NAME = "table_location";
    public static final String COLLECTION_TABLE_NAME = "table_gcollection";
    public static final String QR_TABLE_NAME = "table_qr_emp";

    public static final String BEFORE_IMAGE = "imageB";
    public static final String BEFORE_IMAGE_TIME = "b4ImageTime";
    public static final String AFTER_IMAGE = "imageA";
    public static final String QR_IMAGE_PATH = "qr_image_path";

    private static SyncServerAdapterClass syncServer;
    private static ShareLocationAdapterClass shareLocationAdapterClass;
    private static EmpSyncServerAdapterClass empSyncServer;

    public static boolean isSyncServerRequestEnable = false;
    public static boolean isLocationRequestEnable = false;
    public static boolean isEmpSyncServerRequestEnable = false;
    public static boolean isSyncOfflineDataRequestEnable = false;
    public static boolean isSyncOfflineWasteManagementDataRequestEnable = false;

    private static ArrayList<LanguagePojo> languagePojoList;

    public static boolean DutyOffFromService = false;

    public static boolean isIsOnduty() {       // important function to get the status of the employee's duty
        return Prefs.getBoolean(PREFS.IS_ON_DUTY, false);
    }

    public static void setIsOnduty(boolean isOnduty) {   // important function to set the status of the employee's duty
        Prefs.putBoolean(PREFS.IS_ON_DUTY, isOnduty);
    }

    public static void setEmpType(String empType) {
        Prefs.putString(PREFS.EMPLOYEE_TYPE, empType);
    }

    public interface PREFS {

        //    Save Data Constant
        String IS_USER_LOGIN = "UserLoginStatus";
        String USER_ID = "UserId";
        String USER_TYPE = "UserType";
        String USER_TYPE_ID = "UserTypeId";
        String IS_GT_FEATURE = "isGtFeature";
        String IS_ON_DUTY = "isOnDuty";
        String IS_AREA_ACTIVE = "IsAreaActive";
        String IS_SAME_LOCALITY = "isSameLocality";
        String IN_PUNCH_DATE = "IN_PUNCH_DATE";

        String IMAGE_POJO = "ImagePojo";

        String IN_PUNCH_POJO = "InPunchPull";
        String VEHICLE_TYPE_POJO_LIST = "VehicleTypePullList";
        String VEHICLE_NUMBER_POJO_LIST = "VehicleNo";
        String USER_DETAIL_POJO = "UserDetailPull";
        String WORK_HISTORY_POJO_LIST = "WorkHistoryPullList";
        String WORK_HISTORY_DETAIL_POJO_LIST = "WorkHistoryDetailPullList";
        String LANGUAGE_POJO_LIST = "LanguagePullList";
        String AREA_VERTICES = "areaVertices";
        String VEHICLE_NUMBER_LIST = "VehicleNumberList";

        String EMPLOYEE_TYPE = "EmpType";
        /*String DUMP_YARD_SUPERVISOR = "ReferanceId";
        String HOUSE_ID_START = "ReferanceId";
        String HOUSE_ID = "ReferanceId";*/
    }

    public interface USER_TYPE {
        String USER_TYPE_GHANTA_GADI = "0";
        String USER_TYPE_EMP_SCANNIFY = "1";
        String USER_TYPE_WASTE_MANAGER = "2";
    }

    public interface NondaniLocation {
        String OPEN_FORM_TYPE = "OPEN_FORM_TYPE";
        String SUBMIT_TYPE = "SUBMIT_TYPE";
        String REFERENCE_ID = "REFERENCE_ID";
    }

    public interface DUMPDATA {
        String dumpDataMap = "dump_data_map";
        String dumpYardId = "dump_yard_id";
        String weightTotal = "total_weight";
        String weightTotalDry = "total_weight_dry";
        String weightTotalWet = "total_weight_wet";
    }

//    public static void saveLocation(Location location) {
//        if (!AUtils.isNull(location)) {
//            double latti = location.getLatitude();
//            double longi = location.getLongitude();
//
//            Prefs.putString(AUtils.LONG, Double.toString(longi));
//            Prefs.putString(AUtils.LAT, Double.toString(latti));
//        }
//    }

    public static String getServerDate() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getLocalDate() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateLocal(String date) {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        try {
            return format.format(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format.format(new Date());
    }

    public static String serverDateFromLocal(String date) {

        SimpleDateFormat local = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        SimpleDateFormat server = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE, Locale.ENGLISH);
        try {
            return server.format(local.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return server.format(new Date());
    }

    public static String getServerTime() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_TIME_FORMATE, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateTime() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateTimeWithMilliesSecond() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateTimeLocal() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static Date parse(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String getTitleDateFormat(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(TITLE_DATE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractDate(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(DATE_VALUE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDumpSuperId(String dumpId) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        /* bundle = getIntent().getExtras();*/

        if (bundle != null) {
            dumpId = bundle.getString(AUtils.dumpYardSuperId);
            System.out.println("Dumpster Scan Id: " + dumpId);

        }
        return "";
    }

    public static String extractMonth(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(SEMI_MONTH_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatDate(String date, String fromFormat, String toFormat) {

        SimpleDateFormat providedFormat = new SimpleDateFormat(fromFormat, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(toFormat, Locale.ENGLISH);

        try {
            return requiredFormat.format(providedFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getEmpTimeLineFormat(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_TIME_24HR_FORMATE, Locale.ENGLISH);
        SimpleDateFormat timelineFormat = new SimpleDateFormat(SERVER_TIME_FORMATE, Locale.ENGLISH);

        try {
            return timelineFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTitleDateFormatEmp(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(EMP_SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(TITLE_DATE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getHistoryDetailsDateFormat(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(EMP_SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractDateEmp(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(EMP_SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(DATE_VALUE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractMonthEmp(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(EMP_SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(SEMI_MONTH_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void showDialog(Context context, @Nullable String Title, @Nullable String Message, DialogInterface.OnClickListener
            positiveListener, DialogInterface.OnClickListener negativeLisner) {

        String positiveText = context.getResources().getString(R.string.yes_txt);
        String negativeText = context.getResources().getString(R.string.no_txt);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false).setPositiveButton(positiveText, positiveListener).setNegativeButton(negativeText, negativeLisner);

        if (!AUtils.isNull(Title)) {
            builder.setTitle(Title);
        }

        if (!AUtils.isNull(Message)) {
            builder.setMessage(Message);
        }

        builder.create().show();
    }

    public static void showDialog(Context context, @Nullable String Title, @Nullable String Message, DialogInterface.OnClickListener positiveListener) {

        String positiveText = context.getResources().getString(R.string.ok_txt);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (!AUtils.isNull(Title)) {
            builder.setTitle(Title);
        }

        if (!AUtils.isNull(Message)) {
            builder.setMessage(Message);
        }

        builder.setCancelable(false).setPositiveButton(positiveText, positiveListener).create().show();
    }

    public static void hideSnackBar() {
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
    }

    public static SQLiteDatabase sqlDBInstance(Context mContext) {

        SbaDatabase databaseHelper = new SbaDatabase(mContext);

        return databaseHelper.getWritableDatabase();
    }

    public static Calendar getDutyEndTime() {
        Calendar cl = Calendar.getInstance();

        cl.set(Calendar.HOUR_OF_DAY, 23);
        cl.set(Calendar.MINUTE, 50);
        cl.set(Calendar.SECOND, 0);

        return cl;
    }

    public static Calendar getCurrentTime() {
        Calendar now = Calendar.getInstance();

        return now;
    }

    public static void setInPunchDate(Calendar calendar) {

        Prefs.putString(PREFS.IN_PUNCH_DATE, getServerDate(calendar));
    }

    public static String getInPunchDate() {

        return Prefs.getString(PREFS.IN_PUNCH_DATE, getServerDate(Calendar.getInstance()));
    }

    public static void removeInPunchDate() {

        Prefs.remove(PREFS.IN_PUNCH_DATE);
    }

    public static void removeInDumpHouseId() {

        Prefs.remove(AUtils.HOUSE_ID_START);
        Prefs.remove(AUtils.HOUSE_ID);
    }

    public static String getServerDate(Calendar calendar) {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE, Locale.ENGLISH);
        return format.format(calendar.getTime());
    }

    public static AlertDialog getUploadingAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setView(R.layout.layout_progress_bar).setCancelable(true).create();
    }

    public static double calculateDistance(Context context, double newlat, double newLng) {
        LastLocationRepository lastLocationRepository = new LastLocationRepository(context);
        double startlat = 0d;
        double startLng = 0d;
        HashMap<String, Double> map = lastLocationRepository.getLastLocationCoordinates(AUtils.getLocalDate());
        if (!AUtils.isNull(map) && map.containsKey(LastLocationRepository.LatitudeKey) && map.containsKey(LastLocationRepository.LongitudeKey)) {
            startlat = map.get(LastLocationRepository.LatitudeKey);
            startLng = map.get(LastLocationRepository.LongitudeKey);
        }

        LastLocationEntity entity = new LastLocationEntity();
        entity.setColumnDate(AUtils.getServerDateTimeLocal());
        entity.setColumnLattitude(String.valueOf(newlat));
        entity.setColumnLongitude(String.valueOf(newLng));
        lastLocationRepository.insertUpdateLastLocation(entity);
        if (startlat != 0d && startLng != 0d)
            return calculateLatLngDistance(startlat, startLng, newlat, newLng, UNITS.KiloMeter);

        return 0d;
    }

    public static String setLanguage(String language) {
        switch (language) {
            case AUtils.LanguageNameConstants.ENGLISH:
                return AUtils.LanguageConstants.ENGLISH;
            case AUtils.LanguageNameConstants.MARATHI:
                return AUtils.LanguageConstants.MARATHI;
            case AUtils.LanguageNameConstants.HINDI:
                return AUtils.LanguageConstants.HINDI;
        }

        return Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID);
    }

    public static ArrayList<LanguagePojo> getLanguagePojoList() {

        if (AUtils.isNull(languagePojoList)) {

            languagePojoList = new ArrayList<>();

            LanguagePojo eng = new LanguagePojo();
            eng.setLanguage(AUtils.LanguageNameConstants.ENGLISH);
            eng.setLanguageId(AUtils.LanguageIDConstants.ENGLISH);
            languagePojoList.add(eng);

            LanguagePojo mar = new LanguagePojo();
            mar.setLanguageId(AUtils.LanguageIDConstants.MARATHI);
            mar.setLanguage(AUtils.LanguageNameConstants.MARATHI);
            languagePojoList.add(mar);

            LanguagePojo hi = new LanguagePojo();
            hi.setLanguageId(AUtils.LanguageIDConstants.HINDI);
            hi.setLanguage(AUtils.LanguageNameConstants.HINDI);
            languagePojoList.add(hi);

        }

        return languagePojoList;
    }

    public static void setLanguagePojoList(ArrayList<LanguagePojo> languagePojoList) {
        AUtils.languagePojoList = languagePojoList;
    }

    public static String getPreviousDateDutyOffTime() {

        DateFormat dateFormat = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 50);
        cal.set(Calendar.SECOND, 0);

        return dateFormat.format(cal.getTime());
    }

    public static String getCurrentDateDutyOffTime() {

        DateFormat dateFormat = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 50);
        cal.set(Calendar.SECOND, 0);

        return dateFormat.format(cal.getTime());
    }

    public static String serverTimeFromLocal(String date) {

        SimpleDateFormat local = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);
        SimpleDateFormat server = new SimpleDateFormat(AUtils.SERVER_TIME_FORMATE, Locale.ENGLISH);
        try {
            return server.format(local.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return server.format(new Date());
    }

    public static Class<?> getDashboardClass(String userType) {
        switch (userType) {
            case AUtils.USER_TYPE.USER_TYPE_WASTE_MANAGER:
                return WasteDashboardActivity.class;
            case AUtils.USER_TYPE.USER_TYPE_EMP_SCANNIFY:
                return EmpDashboardActivity.class;
            case AUtils.USER_TYPE.USER_TYPE_GHANTA_GADI:
            default:
                return DashboardActivity.class;
        }
    }


    /**
     * Encoded Image
     */


    @Nullable
    public static Bitmap getImageBitmap(String _sourcePath, Context _context) throws Resources.NotFoundException {
        Bitmap bitmap = null;
        if (_sourcePath != null) {

            File file = new File(_sourcePath);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            }

            Uri uri = Uri.fromFile(file);
            Log.d(TAG, "getImageBitmap: " + uri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(_context.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            bitmap= BitmapFactory.decodeFile(_sourcePath);
        } else {
            throw new Resources.NotFoundException("Invalid Source Path");
        }
        return bitmap;
    }

    @Nullable
    public Bitmap getImageSourcePath(String _sourcePath, Context _context) throws Resources.NotFoundException, IOException {
        File file = null;
        Bitmap bitmap = null;

        if (_sourcePath != null) {
            file = new File(_sourcePath);
//             if(file.exists()){
//
//                 BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//                      bitmap = BitmapFactory.decodeFile(file.getPath(), bmOptions);
//                     bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
//
//             }


            Uri uri = Uri.fromFile(file);

            Log.d(TAG, "getImageSourcePath: " + _sourcePath);
//            Log.d(TAG, "getImageSourcePath: "+uri);
//            content://com.appynitty.basepta.fileProvider/external_files/Android/data/com.appynitty.basepta/files/Pictures/image_20201218_1830485390469829670621400.jpg
//            content://com.appynitty.basepta.fileProvider/external_files/Android/data/com.appynitty.basepta/files/Pictures/image_20201218_1830485390469829670621400.jpg


//            if (file.exists()) {
//                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            }

//            /storage/emulated/0/Android/data/com.appynitty.basepta/files/Pictures/image_20201218_1814219021659496099062004.jpg
//             /storage/emulated/0/Android/data/com.appynitty.basepta/files/Pictures/image_20201218_1814219021659496099062004.jpg
//              /storage/emulated/0/Android/data/com.appynitty.basepta/files/Pictures/image_20201218_1814219021659496099062004.jpg

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                try {
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(_context.getContentResolver(), uri));

                } catch (IOException e) {
                    e.printStackTrace();
                    MDToast.makeText(_context, e.getMessage());
                }
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(_context.getContentResolver(), uri);
            }
        } else if (_sourcePath == null) {
            throw new Resources.NotFoundException("Invalid Source Path");
        }
        Log.d(TAG, "getImageSourcePath:  Bitmap " + file);
        return bitmap;
    }


    @Nullable
    public static String getEncodedImage(String _sourcePath, Context _context) throws IOException, Resources.NotFoundException {
        String encoded = null;
        if (TextUtils.isEmpty(_sourcePath)) {
            return "";
        }
        Bitmap bitmap = getImageBitmap(_sourcePath, _context);
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] array = byteArrayOutputStream.toByteArray();
            String encode = Base64.encodeToString(array, Base64.DEFAULT);
            encoded = String.format("data:image/jpg;Base64,%s", encode);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        }

        Log.d(TAG, "getEncodedImage: " + encoded);
        return encoded;

    }

    public static Bitmap writeOnImage(Context ctx, String mDate, String mId, String mPath) {

        Uri uri = Uri.fromFile(new File(mPath));
        final String lat = Prefs.getString(AUtils.LAT, "");
        final String lon = Prefs.getString(AUtils.LONG, "");

        Bitmap bm = loadFromUri(uri, ctx);
        Bitmap mutableBitmap = bm.copy(Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
//        paint.setStrokeWidth(2.0f);
        Typeface plain = Typeface.createFromAsset(mainApplicationConstant.getAssets(), "bebasneuebold.ttf");
        Typeface bold = Typeface.create(plain, Typeface.NORMAL);
        paint.setTypeface(bold);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);


        Paint stkPaint = new Paint();
        paint.setAntiAlias(true);
        stkPaint.setStyle(Paint.Style.STROKE);
        stkPaint.setStrokeWidth(4);
        stkPaint.setColor(Color.BLACK);
        stkPaint.setTypeface(bold);
        stkPaint.setTextSize(50);


        Canvas canvas = new Canvas(mutableBitmap);

        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));

        /*canvas.drawText("ID: " + mId, 25, yPos + 230, stkPaint);    //difference of 25
        canvas.drawText("ID: " + mId, 25, yPos + 230, paint);
        canvas.drawText("Lat: " + lat, 25, yPos + 255, stkPaint);
        canvas.drawText("Lat: " + lat, 25, yPos + 255, paint);
        canvas.drawText("Long: " + lon, 25, yPos + 280, stkPaint);
        canvas.drawText("Long: " + lon, 25, yPos + 280, paint);
        canvas.drawText(mDate, 25, yPos + 305, stkPaint);
        canvas.drawText(mDate, 25, yPos + 305, paint);*/

        canvas.drawText("ID: " + mId, 26, yPos + 340, stkPaint);    //difference of 40
        canvas.drawText("ID: " + mId, 25, yPos + 340, paint);
        canvas.drawText("Lat: " + lat, 28, yPos + 380, stkPaint);
        canvas.drawText("Lat: " + lat, 27, yPos + 380, paint);
        canvas.drawText("Long: " + lon, 30, yPos + 420, stkPaint);
        canvas.drawText("Long: " + lon, 29, yPos + 420, paint);
        canvas.drawText(mDate, 32, yPos + 460, stkPaint);
        canvas.drawText(mDate, 31, yPos + 460, paint);


        return mutableBitmap;
    }


    public static String getDateAndTime() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;
    }

    /**
     * Created by Swapnil
     */
    public static void gpsStatusCheck(Context ctx) {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10);
        mLocationRequest.setSmallestDisplacement(10);
        mLocationRequest.setFastestInterval(10);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new
                LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(ctx).checkLocationSettings(builder.build());


        task.addOnCompleteListener(task1 -> {
            try {
                LocationSettingsResponse response = task1.getResult(ApiException.class);
                // All location settings are satisfied. The client can initialize location
                // requests here.

            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        try {
                            // Cast to a resolvable exception.
                            ResolvableApiException resolvable = (ResolvableApiException) exception;
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                    (Activity) ctx,
                                    101);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        } catch (ClassCastException e) {
                            // Ignore, should be an impossible error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });

    }

    public static Bitmap loadFromUri(Uri photoUri, Context ctx) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if (Build.VERSION.SDK_INT > 27) {
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(ctx.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static File getCompressed(Context context, String path) throws IOException {

        if (context == null)
            throw new NullPointerException("Context must not be null.");
        //getting device external cache directory, might not be available on some devices,
        // so our code fall back to internal storage cache directory, which is always available but in smaller quantity
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null)
            //fall back
            cacheDir = context.getCacheDir();

        String rootDir = cacheDir.getAbsolutePath() + "/ImageCompressor";
        File root = new File(rootDir);

        //Create ImageCompressor folder if it doesnt already exists.
        if (!root.exists())
            root.mkdirs();

        //decode and resize the original bitmap from @param path.
        Bitmap bitmap = decodeImageFromFiles(path, /* your desired width*/300, /*your desired height*/ 300);

        //create placeholder for the compressed image file
        File compressed = new File(root, "IMG_" + System.currentTimeMillis() + ".jpg" /*Your desired format*/);

        //convert the decoded bitmap to stream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        /*compress bitmap into byteArrayOutputStream
            Bitmap.compress(Format, Quality, OutputStream)
            Where Quality ranges from 1 - 100.
         */
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);

        /*
        Right now, we have our bitmap inside byteArrayOutputStream Object, all we need next is to write it to the compressed file we created earlier,
        java.io.FileOutputStream can help us do just That!
         */
        FileOutputStream fileOutputStream = new FileOutputStream(compressed);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();

        fileOutputStream.close();

        //File written, return to the caller. Done!
        return compressed;
    }

    public static Bitmap decodeImageFromFiles(String path, int width, int height) {
        BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
        scaleOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, scaleOptions);
        int scale = 1;
        while (scaleOptions.outWidth / scale / 2 >= width
                && scaleOptions.outHeight / scale / 2 >= height) {
            scale *= 2;
        }
        // decode with the sample size
        BitmapFactory.Options outOptions = new BitmapFactory.Options();
        outOptions.inSampleSize = scale;
        return BitmapFactory.decodeFile(path, outOptions);
    }

    // added by rahul

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float convertDpToPixel(float dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / displayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / displayMetrics.DENSITY_DEFAULT);
    }

    public interface UlbConstants {
        String ETAPALLI = "Etapalli";
        String BHAMRAGAD = "Bhamragad";
        String GADCHIROLI = "Gadchiroli";
        String CHAMORSHI = "Chamorshi";
        String KHAPA = "Savner";
        String MULCHERA = "Mulchera";
        String DESAIGANJ = "Desaiganj (Vadasa)";
        String AHERI = "Aheri";
        String SIRONCHA = "Sironcha";
        String ARMORI = "Armori";
        String ALANDI = "Haveli";
        String KALMESHWAR = "Kalameshwar";
        String KUHI = "Kuhi";
        String MAHADULA = "Kamptee Taluka";
        String HINGNA = "Hingna";
        String PARSEONI = "Parseoni";
        String MOHPA = "Kalameshwar";
        String AKLUJ = "Malshiras";
        String NAGPUR = "101";
        String WARDHA = "Wardha taluka";
        String THANE = "Thane Municipal Corporation";
        String KURKHEDA = "Kurkheda";
        String PULGAON = "Deoli";
        String MAUDA = "Mauda";
        String ARVI = "Samudrapur";
        String HINGANGHAT = "Hinganghat";
        String MANGALWEDHA = "Mangalvedhe";
        String APPYNITTY = "Nagpur";
        String DAHANU = "Dahanu";
        String MOHADI = "Mohadi";
        String KHAMGAON = "Khamgaon";
        String PALGHAR = "Palghar";
        String DHANORA = "Dhanora";
        String KORCHI = "Korchi";
        String CHANDUR = "Chandur, Maharashtra";
        String JINTUR = "Jintur";
        String MANWATH = "Manwath";
        String INDAPUR = "Indapur";

    }

    public static String getLocality(int AppId) {
        switch (AppId) {
            case 3047:
                return UlbConstants.ETAPALLI;
            case 3070:
                return UlbConstants.BHAMRAGAD;
            case 3071:
                return UlbConstants.GADCHIROLI;
            case 3073:
                return UlbConstants.CHAMORSHI;
            case 3078:
                return UlbConstants.KHAPA;
            case 3069:
                return UlbConstants.MULCHERA;
            case 3072:
                return UlbConstants.DESAIGANJ;
            case 3074:
                return UlbConstants.AHERI;
            case 3075:
                return UlbConstants.SIRONCHA;
            case 3076:
                return UlbConstants.ARMORI;
            case 3077:
                return UlbConstants.ALANDI;
            case 3079:
                return UlbConstants.KALMESHWAR;
            case 3080:
                return UlbConstants.KUHI;
            case 3081:
                return UlbConstants.MAHADULA;
            case 3082:
                return UlbConstants.HINGNA;
            case 3083:
                return UlbConstants.PARSEONI;
            case 3084:
                return UlbConstants.MOHPA;
            case 3085:
                return UlbConstants.AKLUJ;
            case 3068:
                return UlbConstants.NAGPUR;
            case 3087:
                return UlbConstants.WARDHA;
            case 3089:
                return UlbConstants.KURKHEDA;
            case 3090:
                return UlbConstants.PULGAON;
            case 3091:
                return UlbConstants.MAUDA;
            case 3092:
                return UlbConstants.ARVI;
            case 3093:
                return UlbConstants.HINGANGHAT;
            case 3095:
                return UlbConstants.MANGALWEDHA;
            case 3098:
                return UlbConstants.APPYNITTY;
            case 3086:
                return UlbConstants.DAHANU;
            case 3096:
                return UlbConstants.MOHADI;
            case 3097:
                return UlbConstants.KHAMGAON;
            case 3100:
                return UlbConstants.DHANORA;
            case 3101:
                return UlbConstants.KORCHI;
            case 3102:
                return UlbConstants.CHANDUR;
            case 3103:
                return UlbConstants.JINTUR;
            case 3104:
                return UlbConstants.MANWATH;
            case 3099:
                return UlbConstants.PALGHAR;
            case 3106:
                return UlbConstants.INDAPUR;
            default:
                return "none";
        }
    }

    public static void getAppGeoArea(geoAreaRequestListener geoAreaRequestListener) {

        AppGeoAreaAdapter.getInstance().getAppGeoArea(new AppGeoAreaAdapter.AppGeoListener() {
            @Override
            public void onResponse(AppGeoArea appGeoArea) {
                Log.e(TAG, "onResponse: IsAreaActive: " + appGeoArea.getIsAreaActive() + ", List of vertices: " + appGeoArea.getAreaGeoVertices());

                Prefs.putBoolean(AUtils.PREFS.IS_AREA_ACTIVE, appGeoArea.getIsAreaActive());
                /*if (geoAreaRequestListener != null)
                    geoAreaRequestListener.onResponse();*/

                String s = appGeoArea.getAreaGeoVertices();
                if (s != null) {
                    String[] splitString = s.split(";");
                    List<LatLng> prefList = new ArrayList<>();

                    for (String value : splitString) {
                        String[] splitSubString = value.split(",");
                        double lat = Double.parseDouble(splitSubString[0]);
                        double lon = Double.parseDouble(splitSubString[1]);
                        LatLng asdf = new LatLng(lat, lon);
                        prefList.add(asdf);
                    }

                    Gson gson = new Gson();

                    String json = gson.toJson(prefList);

                    Prefs.putString(AUtils.PREFS.AREA_VERTICES, json);

                    if (geoAreaRequestListener != null)
                        geoAreaRequestListener.onResponse();
//                checkLocationValidity();
                } else {
                    if (geoAreaRequestListener != null)
                        geoAreaRequestListener.onResponse();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (geoAreaRequestListener != null)
                    geoAreaRequestListener.onFailure(throwable);
                Log.e(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

    public interface geoAreaRequestListener {
        void onResponse();

        void onFailure(Throwable throwable);
    }

    public static boolean isValidArea() {
        Log.e(TAG, "isValidArea: lat: " + Prefs.getString(AUtils.LAT, null));
        double lat = Double.parseDouble(Prefs.getString(AUtils.LAT, null));
        double lon = Double.parseDouble(Prefs.getString(AUtils.LONG, null));
        LatLng asdf = new LatLng(lat, lon);

        List<LatLng> prefList = new ArrayList<>();
        String json = Prefs.getString(AUtils.PREFS.AREA_VERTICES, null);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<LatLng>>() {
        }.getType();

        prefList = gson.fromJson(json, type);

        Log.d(TAG, "isValidArea: " + prefList);
        if (prefList != null) {
            boolean isPointInPolygon = PolyUtil.containsLocation(asdf, prefList, false);

            Log.e(TAG, "current latlng= " + asdf
                    + ", isValidArea: " + isPointInPolygon);

            return isPointInPolygon;
        } else {

            return false;
        }
    }
}

