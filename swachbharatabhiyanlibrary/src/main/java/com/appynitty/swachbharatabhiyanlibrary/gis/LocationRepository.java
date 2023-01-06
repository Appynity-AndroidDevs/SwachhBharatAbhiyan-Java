package com.appynitty.swachbharatabhiyanlibrary.gis;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LocationRepository {
    private final LocationsDao mLocationsDao;
    private final LiveData<List<LocationEntity>> mAllLocations;
    private int count;

    public LocationRepository(Application application) {
        LocationsRoomDB db = LocationsRoomDB.getDatabase(application);
        mLocationsDao = db.locationsDao();
        mAllLocations = mLocationsDao.getLocations();
    }

    public LiveData<List<LocationEntity>> getAllLocations() {
        return mAllLocations;
    }

    public void insert(LocationEntity location) {
        LocationsRoomDB.databaseWriteExecutor.execute(() -> mLocationsDao.insert(location));
    }

    public void delete() {
        LocationsRoomDB.databaseWriteExecutor.execute(mLocationsDao::deleteAll);
    }

    public int getCount() {
        LocationsRoomDB.databaseWriteExecutor.execute(() -> count = mLocationsDao.getCount());
        return count;
    }
}
