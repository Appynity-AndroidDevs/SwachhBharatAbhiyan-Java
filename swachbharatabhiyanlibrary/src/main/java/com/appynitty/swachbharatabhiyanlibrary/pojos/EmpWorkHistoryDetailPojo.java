package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class EmpWorkHistoryDetailPojo {

    private String time;

    private String Date;

    private String type;

    private String HouseNo;

    private String DumpYardNo;

    private String LiquidNo;

    private String StreetNo;

    private String PointNo;
    private String SurveyNo;

    public String getLiquidNo() {
        return LiquidNo;
    }

    public void setLiquidNo(String liquidNo) {
        LiquidNo = liquidNo;
    }

    public String getStreetNo() {
        return StreetNo;
    }

    public void setStreetNo(String streetNo) {
        StreetNo = streetNo;
    }

    public String getSurveyNo() {
        return SurveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        SurveyNo = surveyNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLiquidWasteNo() {
        return LiquidNo;
    }

    public void setLiquidWasteNo(String liquidWasteNo) {
        LiquidNo = liquidWasteNo;
    }

    public String getStreetWasteNo() {
        return StreetNo;
    }

    public void setStreetWasteNo(String streetWasteNo) {
        StreetNo = streetWasteNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
    }

    public String getDumpYardNo() {
        return DumpYardNo;
    }

    public void setDumpYardNo(String dumpYardNo) {
        DumpYardNo = dumpYardNo;
    }

    public String getPointNo() {
        return PointNo;
    }

    public void setPointNo(String pointNo) {
        PointNo = pointNo;
    }

    @Override
    public String toString() {
        return "EmpWorkHistoryDetailPojo{" +
                "time='" + time + '\'' +
                ", Date='" + Date + '\'' +
                ", type='" + type + '\'' +
                ", HouseNo='" + HouseNo + '\'' +
                ", SurveyNo='" + SurveyNo + '\'' +
                ", DumpYardNo='" + DumpYardNo + '\'' +
                ", PointNo='" + PointNo + '\'' +
                '}';
    }
}
