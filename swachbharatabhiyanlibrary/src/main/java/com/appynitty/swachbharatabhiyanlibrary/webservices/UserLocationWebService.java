package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.UserLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserLocationResultPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserLocationWebService {

    @POST("api/Save/UserLocation")
    Call<List<UserLocationResultPojo>> saveUserLocation(@Header("Authorization") String auth_token, @Header("appId") String appId,
                                                        @Header("Content-Type") String content_type,
                                                        @Header("typeId") String typeId,
                                                        @Header("EmpType") String empType,
                                                        @Header("batteryStatus") int batteryStatus,
                                                        @Body List<UserLocationPojo> userLocationPojoList);
}
