package com.appynitty.swachbharatabhiyanlibrary.pojos;

import com.google.gson.annotations.SerializedName;

public class HouseOnMapPojo {

    private String RefferenceId;
    @SerializedName("Lat")
    private String latitude;
    @SerializedName("Long")
    private String longitude;

    public HouseOnMapPojo(String refferenceId, String latitude, String longitude) {
        RefferenceId = refferenceId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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
