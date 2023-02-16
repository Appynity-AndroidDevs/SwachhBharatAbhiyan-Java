package com.appynitty.swachbharatabhiyanlibrary.db;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.appynitty.swachbharatabhiyanlibrary.daos.SurveyDao;
import com.appynitty.swachbharatabhiyanlibrary.entity.OfflineSurvey;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/***
 * Created BY Rahul Rokade
 * Date : 3 Jan 2023
 * */
@Database(entities = {OfflineSurvey.class}, version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase appRoomDataBase;

    public abstract SurveyDao surveyDao();
    private static final int NUMBER_OF_THREADS  = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDataBase getAppRoomDataBase(final Context context){
        if (context != null && appRoomDataBase == null){
            synchronized (AppDataBase.class){
                if(appRoomDataBase == null){
                    appRoomDataBase = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "Employee_database").allowMainThreadQueries().build();
                }
            }
        }
        return appRoomDataBase;
    }

}
