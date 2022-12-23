package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.AppGeoArea;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AppGeoAreaWebService {

    @GET("api/Get/AppAreaLatLong")
    Call<AppGeoArea> getAppGeoArea(@Header("Authorization")String auth_token,@Header("appId") String appId);
}
