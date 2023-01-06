package com.appynitty.swachbharatabhiyanlibrary.gis;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationsDao {
    @Query("SELECT * FROM locations_table")
    LiveData<List<LocationEntity>> getLocations();

    @Query("SELECT * FROM locations_table")
    List<LocationEntity> getLocationList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(LocationEntity location);

    @Query("DELETE FROM locations_table")
    void deleteAll();

    @Query("SELECT * FROM locations_table ORDER BY Lat DESC LIMIT 1")
    double getLastLat();

    @Query("SELECT * FROM locations_table ORDER BY Lon DESC LIMIT 1")
    double getLastLon();

    @Query("SELECT COUNT (LineElement) FROM locations_table")
    int getCount();
}
