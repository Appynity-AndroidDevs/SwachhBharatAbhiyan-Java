package com.appynitty.swachbharatabhiyanlibrary.gis;

import com.google.gson.annotations.SerializedName;

public class GISRequestDTO {
    @SerializedName("id")
    private String trailId;

    private Integer houseId;
    private String startTs;
    private String endTs;
    private Integer createUser;
    private String createTs;
    private Integer updateUser;
    private String updateTs;
    private String geom;

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public String getStartTs() {
        return startTs;
    }

    public void setStartTs(String startTs) {
        this.startTs = startTs;
    }

    public String getEndTs() {
        return endTs;
    }

    public void setEndTs(String endTs) {
        this.endTs = endTs;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(String updateTs) {
        this.updateTs = updateTs;
    }


    public String to_String() {
        return "GISRequestDTO{" +
                "trailId='" + trailId + '\'' +
                ", houseId=" + houseId +
                ", startTs='" + startTs + '\'' +
                ", endTs='" + endTs + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTs='" + createTs + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", updateTs='" + updateTs + '\'' +
                ", geom='" + geom + '\'' +
                '}';
    }
}
