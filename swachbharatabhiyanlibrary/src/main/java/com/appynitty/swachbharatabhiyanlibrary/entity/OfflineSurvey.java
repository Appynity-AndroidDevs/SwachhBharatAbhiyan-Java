package com.appynitty.swachbharatabhiyanlibrary.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.DataConverter;

import java.io.Serializable;


@Entity(tableName = "offline_survey_table")
@TypeConverters(DataConverter.class)
public class OfflineSurvey implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private SurveyDetailsRequestPojo surveyRequestObj;
    private String date;

    public void offSurveyRequest(SurveyDetailsRequestPojo surveyRequestObj, String date){
        this.surveyRequestObj = surveyRequestObj;
        this.date = date;
    }

    public void offSurveyRequest(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
