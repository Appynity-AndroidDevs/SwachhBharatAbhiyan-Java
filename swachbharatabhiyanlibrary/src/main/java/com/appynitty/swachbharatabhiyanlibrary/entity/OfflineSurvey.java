package com.appynitty.swachbharatabhiyanlibrary.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.DataConverter;

import java.io.Serializable;


@Entity(tableName = "offline_survey_table")
@TypeConverters(DataConverter.class)
public class OfflineSurvey implements Serializable {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    //private long id = 0;
    private String houseId;
    @ColumnInfo(name = "surveyRequestObj")
    private SurveyDetailsRequestPojo surveyRequestObj;
    @ColumnInfo(name = "date")
    private String date;

    public OfflineSurvey(String houseId, SurveyDetailsRequestPojo surveyRequestObj) {
        this.houseId = houseId;
        this.surveyRequestObj = surveyRequestObj;
    }

    @Ignore
    public OfflineSurvey(String houseId) {
        this.houseId = houseId;
    }

    /*public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }*/

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public SurveyDetailsRequestPojo getSurveyRequestObj() {
        return surveyRequestObj;
    }

    public void setSurveyRequestObj(SurveyDetailsRequestPojo surveyRequestObj) {
        this.surveyRequestObj = surveyRequestObj;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
