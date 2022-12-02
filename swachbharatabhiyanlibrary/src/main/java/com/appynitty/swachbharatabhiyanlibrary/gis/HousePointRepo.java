package com.appynitty.swachbharatabhiyanlibrary.gis;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class HousePointRepo {
    private final HousePointDao mHouseDao;
    private final LiveData<List<HouseLocationEntity>> mAllHousePoint;

    public HousePointRepo(Application application) {
        LocationsRoomDB db = LocationsRoomDB.getDatabase(application);
        this.mHouseDao = db.housePointDao();
        this.mAllHousePoint = mHouseDao.getHousePoints();
    }

    public LiveData<List<HouseLocationEntity>> getAllHousePoint() {
        return mAllHousePoint;
    }

    public void insert(HouseLocationEntity housePoint) {
        LocationsRoomDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mHouseDao.insert(housePoint);
            }
        });
    }

    public void delete() {
        LocationsRoomDB.databaseWriteExecutor.execute(mHouseDao::deleteAll);
    }

    public void deleteHouse(String houseId) {
        LocationsRoomDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mHouseDao.deleteHouse(houseId);
            }
        });
    }
}
