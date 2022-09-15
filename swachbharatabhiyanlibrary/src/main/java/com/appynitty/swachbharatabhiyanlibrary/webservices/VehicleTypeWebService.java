package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaHousePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.InPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleNumberPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleTypePojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface VehicleTypeWebService {

    @GET("api/Get/VehicleType")
    Call<List<VehicleTypePojo>> pullVehicleTypeList(@Header("appId") String appId,
                                                    @Header("Content-Type") String content_type);

    @GET("api/Get/Vehicles") // added by Rahul
    Call<List<VehicleNumberPojo>> pullVehicleNumberList(@Header("Content-Type") String content_type,
                                                        @Header("appId") String appId,
                                                        @Header("vehicleTypeId") String vehicleTypeId);

    @GET("api/Get/AreaHouse") // added by Rahul
    Call<List<CollectionAreaHousePojo>> pullVehicleQRIdList(@Header("appId") String appId,
                                                            @Header("Content-Type") String content_type,
                                                            @Header("type") String collectionType,
                                                            @Header("areaId") String areaId,
                                                            @Header("EmpType") String EmpType);
}
