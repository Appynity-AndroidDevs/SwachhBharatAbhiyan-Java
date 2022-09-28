package com.appynitty.swachbharatabhiyanlibrary.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppGeoArea {
    String AppId;

    @Expose
    @SerializedName("AppAreaLatLong")
    String AreaGeoVertices;

    Boolean IsAreaActive;

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getAreaGeoVertices() {
        return AreaGeoVertices;
    }

    public void setAreaGeoVertices(String areaGeoVertices) {
        AreaGeoVertices = areaGeoVertices;
    }

    public Boolean getIsAreaActive() {
        return IsAreaActive;
    }

    public void setIsAreaActive(Boolean isAreaActive) {
        IsAreaActive = isAreaActive;
    }
}
