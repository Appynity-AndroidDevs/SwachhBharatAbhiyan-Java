package com.appynitty.swachbharatabhiyanlibrary.utils;

import androidx.room.TypeConverter;

import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;

public class DataConverter implements Serializable {
    @TypeConverter
    public String fromSurveyDetailsRequestPojo(SurveyDetailsRequestPojo requestPojo) {
        if (requestPojo == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<SurveyDetailsRequestPojo>() {
        }.getType();
        String json = gson.toJson(requestPojo, type);
        return json;
    }

    @TypeConverter
    public SurveyDetailsRequestPojo toSurveyDetailsRequestPojo(String requestPojoString) {
        if (requestPojoString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<SurveyDetailsRequestPojo>() {
        }.getType();
        SurveyDetailsRequestPojo requestPojo = gson.fromJson(requestPojoString, type);
        return requestPojo;
    }
}
