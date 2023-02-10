package com.appynitty.swachbharatabhiyanlibrary.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.DatabaseView;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.appynitty.swachbharatabhiyanlibrary.entity.OfflineSurvey;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public abstract class SurveyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(OfflineSurvey survey);
    @Update
    public abstract void update(OfflineSurvey survey);
    @Delete
    public abstract void delete(OfflineSurvey survey);

    @Query("DELETE FROM offline_survey_table WHERE houseId = :surveyId")
    public abstract void deleteById(String surveyId);

    @Query("DELETE FROM offline_survey_table")
    public abstract void deleteAllSurvey();

    @Query("SELECT * FROM offline_survey_table")
    public abstract LiveData<List<OfflineSurvey>> getAllSurvey();

    @Query("SELECT * FROM offline_survey_table")
    public abstract List<OfflineSurvey> getAllSurveyList();

    @Query("SELECT COUNT(houseId) FROM offline_survey_table")
    public abstract int getCount();

    @Query("SELECT * FROM OFFLINE_SURVEY_TABLE ORDER BY houseId DESC LIMIT :limit")
    public abstract LiveData<List<OfflineSurvey>> getLimitList(int limit);
}
