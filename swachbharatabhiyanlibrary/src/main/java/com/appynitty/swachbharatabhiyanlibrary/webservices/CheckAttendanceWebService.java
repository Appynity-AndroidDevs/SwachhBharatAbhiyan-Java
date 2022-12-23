package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.CheckAttendancePojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CheckAttendanceWebService {

    @GET("api/Get/IsAttendence")
    Call<CheckAttendancePojo> CheckAttendance(@Header("Authorization") String auth_token,
                                              @Header("appId") String appId,
                                              @Header("UserId") String userId,
                                              @Header("typeId") String typeId);
}
/*this service is used for checking the employee's attendance*/