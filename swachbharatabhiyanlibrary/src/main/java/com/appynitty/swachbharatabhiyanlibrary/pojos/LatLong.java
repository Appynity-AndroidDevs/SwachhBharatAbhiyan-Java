package com.appynitty.swachbharatabhiyanlibrary.pojos;


import android.os.Parcel;
import android.os.Parcelable;

public class LatLong implements Parcelable {

    private String latitude , longitude;

    public LatLong(String latitude , String longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected LatLong(Parcel in) {
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<LatLong> CREATOR = new Creator<LatLong>() {
        @Override
        public LatLong createFromParcel(Parcel in) {
            return new LatLong(in);
        }

        @Override
        public LatLong[] newArray(int size) {
            return new LatLong[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(latitude);
        parcel.writeString(longitude);
    }
}
