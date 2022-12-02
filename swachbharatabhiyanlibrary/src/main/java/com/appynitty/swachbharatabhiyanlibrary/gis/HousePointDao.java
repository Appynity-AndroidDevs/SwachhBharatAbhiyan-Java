package com.appynitty.swachbharatabhiyanlibrary.gis;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface HousePointDao {

    @Query("SELECT * FROM house_point")
    LiveData<List<HouseLocationEntity>> getHousePoints();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(HouseLocationEntity housePoint);

    @Query("DELETE FROM house_point")
    void deleteAll();

    @Query("DELETE FROM house_point WHERE HouseId = :houseId")
    void deleteHouse(String houseId);
}
