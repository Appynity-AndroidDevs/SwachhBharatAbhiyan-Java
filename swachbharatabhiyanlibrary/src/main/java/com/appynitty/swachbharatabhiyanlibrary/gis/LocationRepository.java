package com.appynitty.swachbharatabhiyanlibrary.gis;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class LocationRepository {
    private final LocationsDao mLocationsDao;
    private final LiveData<List<LocationEntity>> mAllLocations;
    private LatLng latLng;
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

    public LatLng getLastLatLng() {
        LocationsRoomDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                latLng = new LatLng(mLocationsDao.getLastLat(), mLocationsDao.getLastLon());
            }
        });
        return latLng;
    }

    public int getCount() {
        LocationsRoomDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                count = mLocationsDao.getCount();
            }
        });
        return count;
    }
}
