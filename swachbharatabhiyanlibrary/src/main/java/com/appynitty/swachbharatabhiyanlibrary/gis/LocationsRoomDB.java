package com.appynitty.swachbharatabhiyanlibrary.gis;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LocationEntity.class, HouseLocationEntity.class}, version = 1, exportSchema = false)
abstract class LocationsRoomDB extends RoomDatabase {
    abstract LocationsDao locationsDao();
    abstract HousePointDao housePointDao();

    private static volatile LocationsRoomDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized LocationsRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocationsRoomDB.class, "gis_locations_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
