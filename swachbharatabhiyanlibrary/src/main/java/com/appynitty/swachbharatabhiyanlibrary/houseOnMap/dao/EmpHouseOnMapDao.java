package com.appynitty.swachbharatabhiyanlibrary.houseOnMap.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.appynitty.swachbharatabhiyanlibrary.pojos.HouseOnMapPojo;

import java.util.List;

@Dao
public interface EmpHouseOnMapDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHouseOnMap(HouseOnMapPojo houseOnMapPojo);

    @Query("DELETE FROM house_on_map_table")
    void deleteAllHouseOnMap();

    @Query("SELECT * FROM house_on_map_table")
    LiveData<List<HouseOnMapPojo>> getAllHouseOnMapData();


}
