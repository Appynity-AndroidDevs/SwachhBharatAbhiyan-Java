package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.widget.SpinnerAdapter;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleNumberPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleTypePojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.List;

public class VehicleNumberAdapterClass {

    private VehicleNumListener mListener;

    private List<VehicleNumberPojo> vehicleNumPojoList;

    private static final Gson gson = new Gson();

    public VehicleNumListener getVehicleNumListener() {
        return mListener;
    }

    public void setVehicleNumListener(VehicleNumListener mListener) {
        this.mListener = mListener;
    }

    public List<VehicleNumberPojo> getVehicleNumPojoList() {

        Type type = new TypeToken<List<VehicleNumberPojo>>() {
        }.getType();

        vehicleNumPojoList = gson.fromJson(
                Prefs.getString(AUtils.PREFS.VEHICLE_NUMBER_POJO_LIST, null), type);
        return vehicleNumPojoList;
    }


    public static void setVehicleNumPojoList(List<VehicleNumberPojo> vehicleNumPojoList) {
        Type type = new TypeToken<List<VehicleNumberPojo>>() {
        }.getType();
        Prefs.putString(AUtils.PREFS.VEHICLE_NUMBER_POJO_LIST, gson.toJson(vehicleNumPojoList, type));
    }

    public void getVehicleNUmber(String vType) {

        new MyAsyncTask(AUtils.currentContextConstant, false, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                /*if (vehicleTypeId.matches(Prefs.getString(AUtils.DIALOG_TYPE_VEHICLE,""))){
                    isDataPull = syncServer.pullVehicleNumberListFromServer();
                }*/
                isDataPull = syncServer.pullVehicleNumberListFromServer(vType);
            }

            @Override
            public void onFinished() {

                getVehicleNumPojoList();

                if (!AUtils.isNull(vehicleNumPojoList) && !vehicleNumPojoList.isEmpty())
                {
                    if(!AUtils.isNull(mListener))
                        mListener.onSuccessCallBack();
                }
                else
                {
                    if(!AUtils.isNull(mListener))
                        mListener.onFailureCallBack();
                }

            }

            @Override
            public void onInternetLost() {

            }
        }).execute();
    }

    public interface VehicleNumListener {
        void onSuccessCallBack();
        void onFailureCallBack();
    }
}
