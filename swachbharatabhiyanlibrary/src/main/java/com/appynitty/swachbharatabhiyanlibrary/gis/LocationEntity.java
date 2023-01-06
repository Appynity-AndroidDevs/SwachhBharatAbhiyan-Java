package com.appynitty.swachbharatabhiyanlibrary.gis;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations_table")
public class LocationEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "LineElement")
    private String mLineElement;

    @ColumnInfo(name = "Lat")
    private double mLat;

    @ColumnInfo(name = "Lon")
    private double mLon;

    @ColumnInfo(name = "Accuracy")
    private double mAccuracy;

    @ColumnInfo(name = "Timestamp")
    private String mTimeStamp;


    @NonNull
    public String getLineElement() {
        return mLineElement;
    }

    public void setLineElement(@NonNull String mLineElement) {
        this.mLineElement = mLineElement;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double mLat) {
        this.mLat = mLat;
    }

    public double getLon() {
        return mLon;
    }

    public void setLon(double mLon) {
        this.mLon = mLon;
    }

    public double getAccuracy() {
        return mAccuracy;
    }

    public void setAccuracy(double mAccuracy) {
        this.mAccuracy = mAccuracy;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(String mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }
}
