package com.appynitty.swachbharatabhiyanlibrary.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.appynitty.swachbharatabhiyanlibrary.entity.OfflineSurvey;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public abstract class SurveyDao {
    @Insert
    public abstract void insert(OfflineSurvey survey);
    @Update
    public abstract void update(OfflineSurvey survey);
    @Delete
    public abstract void delete(OfflineSurvey survey);

    @Query("DELETE FROM offline_survey_table WHERE id = :surveyId")
    public abstract void deleteById(int surveyId);

    @Query("DELETE FROM offline_survey_table")
    public abstract void deleteAllSurvey();

    @Query("SELECT * FROM offline_survey_table")
    public abstract LiveData<List<OfflineSurvey>> getAllSurvey();

    @Query("SELECT * FROM offline_survey_table")
    public abstract List<OfflineSurvey> getAllSurveyList();

    @Query("SELECT COUNT(id) FROM offline_survey_table")
    public abstract int getCount();
}
