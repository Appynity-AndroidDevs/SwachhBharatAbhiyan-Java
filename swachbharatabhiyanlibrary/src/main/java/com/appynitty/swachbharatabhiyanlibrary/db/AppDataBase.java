package com.appynitty.swachbharatabhiyanlibrary.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.appynitty.swachbharatabhiyanlibrary.daos.SurveyDao;
import com.appynitty.swachbharatabhiyanlibrary.entity.OfflineSurvey;

@Database(entities = {OfflineSurvey.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance;

    public abstract SurveyDao surveyDao();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "hsSurvey_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
