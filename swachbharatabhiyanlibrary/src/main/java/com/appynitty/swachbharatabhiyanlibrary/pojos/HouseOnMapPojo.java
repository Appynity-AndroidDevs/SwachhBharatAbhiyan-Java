package com.appynitty.swachbharatabhiyanlibrary.pojos;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "house_on_map_table")
public class HouseOnMapPojo {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String RefferenceId;
    @SerializedName("Lat")
    private String latitude;
    @SerializedName("Long")
    private String longitude;

    public HouseOnMapPojo(@NonNull String RefferenceId, String latitude, String longitude) {
        this.RefferenceId = RefferenceId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public String getRefferenceId() {
        return RefferenceId;
    }

    public void setRefferenceId(String refferenceId) {
        RefferenceId = refferenceId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
