package com.appynitty.swachbharatabhiyanlibrary.houseOnMap.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.appynitty.swachbharatabhiyanlibrary.houseOnMap.dao.EmpHouseOnMapDao;
import com.appynitty.swachbharatabhiyanlibrary.pojos.HouseOnMapPojo;

@Database(entities = {HouseOnMapPojo.class}, version = 1)
public abstract class HouseScanifyDb extends RoomDatabase {

    private static HouseScanifyDb mInstance;

    public abstract EmpHouseOnMapDao empHouseOnMapDao();

    public static synchronized HouseScanifyDb getmInstance(Context context){
        if (mInstance == null){
            mInstance = Room.databaseBuilder(context.getApplicationContext() ,
                            HouseScanifyDb.class
                            ,"house_scanify_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

}
