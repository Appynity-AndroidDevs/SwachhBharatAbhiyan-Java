package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class DumpEmpPunchPojo {
    String userId;
    String startLat;
    String endLat;
    String endLong;
    String endTime;
    String daendDate;
    String startLong;
    String startTime;
    String daDate;
    String vehicleNumber;
    String vtId;
    String EmpType;
    String ReferanceId;
    String status;
    String message;
    String messageMar;
    String isAttendenceOff;
    String emptype;

    public DumpEmpPunchPojo(String userId, String startLat, String startLong, String startTime, String daDate,
                            String vehicleNumber, String vtId, String empType, String referanceId) {
        this.userId = userId;
        this.startLat = startLat;
        this.startLong = startLong;
        this.startTime = startTime;
        this.daDate = daDate;
        this.vehicleNumber = vehicleNumber;
        this.vtId = vtId;
        EmpType = empType;
        ReferanceId = referanceId;
    }

    public DumpEmpPunchPojo(String userId, String endLat, String endLong, String endTime, String daendDate,
                            String vehicleNumber, String vtId, String empType, String referanceId, String endDumpDuty) {
        this.userId = userId;
        this.endLat = endLat;
        this.endLong = endLong;
        this.endTime = endTime;
        this.daendDate = daendDate;
        this.vehicleNumber = vehicleNumber;
        this.vtId = vtId;
        EmpType = empType;
        ReferanceId = referanceId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLong() {
        return startLong;
    }

    public void setStartLong(String startLong) {
        this.startLong = startLong;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDaDate() {
        return daDate;
    }

    public void setDaDate(String daDate) {
        this.daDate = daDate;
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

    public String getReferanceId() {
        return ReferanceId;
    }

    public void setReferanceId(String referanceId) {
        ReferanceId = referanceId;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getEndLong() {
        return endLong;
    }

    public void setEndLong(String endLong) {
        this.endLong = endLong;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDaendDate() {
        return daendDate;
    }

    public void setDaendDate(String daendDate) {
        this.daendDate = daendDate;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageMar() {
        return messageMar;
    }

    public String getIsAttendenceOff() {
        return isAttendenceOff;
    }

    public String getEmptype() {
        return emptype;
    }
}
