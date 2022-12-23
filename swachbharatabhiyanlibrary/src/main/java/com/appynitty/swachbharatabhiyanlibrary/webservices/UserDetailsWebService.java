package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.UserDetailPojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserDetailsWebService {

    @GET("api/Get/User")
    Call<UserDetailPojo> pullUserDetails(@Header("Authorization")String auth_token, @Header("appId") String appId,
                                         @Header("Content-Type") String content_type,
                                         @Header("userId") String userId,
                                         @Header("typeId") String typeId);

}
