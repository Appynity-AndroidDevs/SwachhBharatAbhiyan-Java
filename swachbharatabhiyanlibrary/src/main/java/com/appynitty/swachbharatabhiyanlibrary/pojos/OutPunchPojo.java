package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class OutPunchPojo {

    private String endLong;

    private String daendDate;

    private String userId;

    private String endTime;

    private String endLat;

    private String vehicleNumber;

    private String vtId;

    private String EmpType;


    public String getEndLong() {
        return endLong;
    }

    public void setEndLong(String endLong) {
        this.endLong = endLong;
    }

    public String getDaendDate() {
        return daendDate;
    }

    public void setDaendDate(String daendDate) {
        this.daendDate = daendDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVtId() {
        return vtId;
    }

    public void setVtId(String vtId) {
        this.vtId = vtId;
    }

    public String getEmpType() {
        return EmpType;
    }

    public void setEmpType(String empType) {
        EmpType = empType;
    }

    @Override
    public String toString() {
        return "OutPunchPojo{" +
                "endLong='" + endLong + '\'' +
                ", daendDate='" + daendDate + '\'' +
                ", userId='" + userId + '\'' +
                ", endTime='" + endTime + '\'' +
                ", endLat='" + endLat + '\'' +
                '}';
    }
}
