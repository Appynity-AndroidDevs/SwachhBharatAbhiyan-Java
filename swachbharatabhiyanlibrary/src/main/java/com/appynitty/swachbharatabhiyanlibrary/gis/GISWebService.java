package com.appynitty.swachbharatabhiyanlibrary.gis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GISWebService {

    //for sending locations from House_Scanify employee
    @Headers({"Accept: application/json"})
    @POST("/api/Save/HouseMapTrail")
    Call<List<GISResponseDTO>> sendHouseMapTrail(@Header("Authorization") String auth_token, @Header("AppId") String appId, @Body /*List<*/GISRequestDTO/*>*/ locationReqDTOList);

    //for sending locations from GhantaGadi employee
    @Headers({"Accept: application/json"})
    @POST("/garbage-trail/add")
    Call<GISResponseDTO> sendGarbageTrail(@Body GISRequestDTO locationReqDTO);

    //for sending house point from House_Scanify employee
    @Headers({"Accept: application/json"})
    @POST("/house/add")
    Call<GISResponseDTO> sendHousePoint(@Body GISRequestDTO locationReqDTO);

}
