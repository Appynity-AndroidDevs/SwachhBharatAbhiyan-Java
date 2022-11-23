package com.appynitty.swachbharatabhiyanlibrary.gis;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations_table")
public class LocationEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "LatLng")
    private String mLatLng;

    @NonNull
    public String getLatLng() {
        return mLatLng;
    }

    public void setLatLng(@NonNull String mLatLng) {
        this.mLatLng = mLatLng;
    }
}
