package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.util.Log;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaHousePojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

public class AreaHouseAdapterClass {
    private final static String TAG = "AreaHouseAdapterClass";
    private List<CollectionAreaHousePojo> hpPojoList;

    private AreaHouseListener mListener;
    private static String empType = Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null);

    public AreaHouseListener getAreaHouseListener() {
        return mListener;
    }

    public void setAreaHouseListener(AreaHouseListener mListener) {
        this.mListener = mListener;
    }

    public List<CollectionAreaHousePojo> getHpPojoList() {
        return hpPojoList;
    }

    private void setHpPojoList(List<CollectionAreaHousePojo> hpPojoList) {
        this.hpPojoList = hpPojoList;
    }

    public void fetchHpList(final String areaType,final String areaId) {
        new MyAsyncTask(AUtils.currentContextConstant, true, new MyAsyncTask.AsynTaskListener() {

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                setHpPojoList(syncServer.fetchCollectionAreaHouse(areaType, areaId));
            }

            @Override
            public void onFinished() {
                if(!AUtils.isNull(hpPojoList)){
                    mListener.onSuccessCallBack();
                }else{
                    mListener.onFailureCallBack();
                }
            }

            @Override
            public void onInternetLost() {

            }
        }).execute();
    }

    public interface AreaHouseListener {
        void onSuccessCallBack();
        void onFailureCallBack();
    }
}
