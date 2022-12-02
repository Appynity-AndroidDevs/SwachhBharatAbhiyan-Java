package com.appynitty.swachbharatabhiyanlibrary.gis;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "house_point")
public class HouseLocationEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "LatLng")
    private String mLocationPoint;

    @NonNull
    @ColumnInfo(name = "HouseId")
    private String mHouseId;

    @NonNull
    @ColumnInfo(name = "UserName")
    private String mUserName;

    @NonNull
    public String getLocationPoint() {
        return mLocationPoint;
    }

    public void setLocationPoint(@NonNull String mLocationPoint) {
        this.mLocationPoint = mLocationPoint;
    }

    @NonNull
    public String getHouseId() {
        return mHouseId;
    }

    public void setHouseId(@NonNull String mHouseId) {
        this.mHouseId = mHouseId;
    }

    @NonNull
    public String getUserName() {
        return mUserName;
    }

    public void setUserName(@NonNull String nUserName) {
        this.mUserName = nUserName;
    }
}
