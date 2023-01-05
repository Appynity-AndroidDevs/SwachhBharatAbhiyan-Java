package com.appynitty.swachbharatabhiyanlibrary.pojos;

import com.google.gson.annotations.SerializedName;

public class SurveyDetailsResultPojo {

    @SerializedName("ID")
    int ID;

    @SerializedName("status")
    String status;

    @SerializedName("message")
    String message;

    @SerializedName("messageMar")
    String messageMar;

    @SerializedName("isAttendenceOff")
    boolean isAttendenceOff;

    @SerializedName("houseId")
    String houseId;

    @SerializedName("IsExist")
    boolean IsExist;



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageMar() {
        return messageMar;
    }

    public void setMessageMar(String messageMar) {
        this.messageMar = messageMar;
    }

    public boolean isAttendenceOff() {
        return isAttendenceOff;
    }

    public void setAttendenceOff(boolean attendenceOff) {
        isAttendenceOff = attendenceOff;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public boolean isExist() {
        return IsExist;
    }

    public void setExist(boolean exist) {
        IsExist = exist;
    }

    @Override
    public String toString() {
        return "SurveyDetailsResultPojo{" +
                "ID=" + ID +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", messageMar='" + messageMar + '\'' +
                ", isAttendenceOff=" + isAttendenceOff +
                ", houseId='" + houseId + '\'' +
                ", IsExist=" + IsExist +
                '}';
    }
}
