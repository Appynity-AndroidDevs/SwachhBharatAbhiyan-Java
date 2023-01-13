package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.HouseOnMapPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface HouseOnMapHistoryService {


    @GET("api/Get/GetLatLongD")
    Call<List<HouseOnMapPojo>> getHouseOnMapHistory(@Header("appId") String appId,
                                                   @Header("userId") String userId,
                                                   @Header("fdate") String date);
}
