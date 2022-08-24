package com.appynitty.swachbharatabhiyanlibrary.pojos;
// TODO: 08/08/2022
/*****
 * created by Rahul Rokade
 * vehicle number pojo
 * */

public class VehicleNumberPojo {
    private String vtId;
    private String VehicleNo;
    private String VehicleId;

    public String getVtId() {
        return vtId;
    }

    public void setVtId(String vtId) {
        this.vtId = vtId;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }


    // TODO: 08/08/2022

    @Override
    public String toString() {
        return "VehicleNumberPojo{" +
                "vtId='" + vtId + '\'' +
                ", VehicleNo='" + VehicleNo + '\'' +
                ", VehicleId='" + VehicleId + '\'' +
                '}';
    }
}
