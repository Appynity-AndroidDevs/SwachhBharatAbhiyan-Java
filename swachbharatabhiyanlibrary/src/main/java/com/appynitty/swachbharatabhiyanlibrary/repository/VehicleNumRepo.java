package com.appynitty.swachbharatabhiyanlibrary.repository;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleNumberPojo;

// TODO: 08/08/2022

public class VehicleNumRepo {
    private static final String TAG = "VehicleNumRepo";
    private static final VehicleNumRepo instance = new VehicleNumRepo();

    public static VehicleNumRepo getInstance() {
        return instance;
    }

    public interface IVehicleNumRepoResponse {
        void onResponse(VehicleNumberPojo vehicleNumberPojo);

        void onFailure(Throwable throwable);
    }
}
