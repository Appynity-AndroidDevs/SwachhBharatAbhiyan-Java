package com.appynitty.swachbharatabhiyanlibrary.webservices;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TokenWebService {

    @POST("/api/Account/getToken")
    Call<String> getToken(@Header("appid") String enAppId);
}
