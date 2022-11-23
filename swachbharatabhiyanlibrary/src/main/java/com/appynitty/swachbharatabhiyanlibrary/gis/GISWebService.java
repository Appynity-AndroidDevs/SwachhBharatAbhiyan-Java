package com.appynitty.swachbharatabhiyanlibrary.gis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GISWebService {

    @Headers({"Accept: application/json"})
    @POST("/house-map-trail/add")
    Call<GISResponseDTO> sendGISData(@Body GISRequestDTO locationReqDTO);

}
