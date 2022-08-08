package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class OutPunchPojo {

    private String endLong;

    private String daDate;

    private String userId;

    private String endTime;

    private String endLat;

    private String ReferanceId;

    private String vtId;

    private String vehicleNumber;

    private String EmpType;

    public String getReferanceId() {
        return ReferanceId;
    }

    public void setReferanceId(String referanceId) {
        ReferanceId = referanceId;
    }

    public String getEndLong() {
        return endLong;
    }

    public void setEndLong(String endLong) {
        this.endLong = endLong;
    }

    public String getDaDate() {
        return daDate;
    }

    public void setDaDate(String daDate) {
        this.daDate = daDate;
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

    public String getVtId() {
        return vtId;
    }

    public void setVtId(String vtId) {
        this.vtId = vtId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
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
                ", daDate='" + daDate + '\'' +
                ", userId='" + userId + '\'' +
                ", endTime='" + endTime + '\'' +
                ", endLat='" + endLat + '\'' +
                '}';
    }
}
