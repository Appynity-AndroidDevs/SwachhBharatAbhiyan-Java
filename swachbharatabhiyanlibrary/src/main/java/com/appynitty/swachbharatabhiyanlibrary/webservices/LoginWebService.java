package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginDetailsPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginPojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginWebService {

    @POST("api/Account/Login")
    Call<LoginDetailsPojo> saveLoginDetails(@Header("Authorization") String token, @Header("appId") String appId,
                                            @Header("Content-Type") String content_type,
                                            @Header("EmpType") String emp_type,
                                            @Body LoginPojo loginPojo);
}
