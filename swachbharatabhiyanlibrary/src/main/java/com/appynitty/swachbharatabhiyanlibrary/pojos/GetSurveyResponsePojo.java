package com.appynitty.swachbharatabhiyanlibrary.pojos;

import com.google.gson.annotations.SerializedName;

public class GetSurveyResponsePojo {
    @SerializedName("svId")
    int svId;

    @SerializedName("houseId")
    int houseId;

    @SerializedName("ReferanceId")
    String ReferanceId;

    @SerializedName("houseLat")
    String houseLat;

    @SerializedName("houseLong")
    String houseLong;

    @SerializedName("name")
    String name;

    @SerializedName("mobileNumber")
    String mobileNumber;

    @SerializedName("age")
    int age;

    @SerializedName("dateOfBirth")
    String dateOfBirth;

    @SerializedName("gender")
    String gender;

    @SerializedName("bloodGroup")
    String bloodGroup;

    @SerializedName("qualification")
    String qualification;

    @SerializedName("occupation")
    String occupation;

    @SerializedName("maritalStatus")
    String maritalStatus;

    @SerializedName("marriageDate")
    String marriageDate;

    @SerializedName("livingStatus")
    String livingStatus;

    @SerializedName("totalMember")
    int totalMember;

    @SerializedName("willingStart")
    boolean willingStart;

    @SerializedName("resourcesAvailable")
    String resourcesAvailable;

    @SerializedName("memberJobOtherCity")
    boolean memberJobOtherCity;

    @SerializedName("noOfVehicle")
    int noOfVehicle;

    @SerializedName("vehicleType")
    String vehicleType;

    @SerializedName("twoWheelerQty")
    int twoWheelerQty;

    @SerializedName("threeWheelerQty")
    String threeWheelerQty;

    @SerializedName("fourWheelerQty")
    int fourWheelerQty;

    @SerializedName("noPeopleVote")
    int noPeopleVote;

    @SerializedName("socialMedia")
    String socialMedia;

    @SerializedName("onlineShopping")
    String onlineShopping;

    @SerializedName("paymentModePrefer")
    String paymentModePrefer;

    @SerializedName("onlinePayApp")
    String onlinePayApp;

    @SerializedName("insurance")
    String insurance;

    @SerializedName("underInsurer")
    boolean underInsurer;

    @SerializedName("ayushmanBeneficiary")
    boolean ayushmanBeneficiary;

    @SerializedName("boosterShot")
    boolean boosterShot;

    @SerializedName("memberDivyang")
    boolean memberDivyang;

    @SerializedName("createUserId")
    String createUserId;

    @SerializedName("createDate")
    String createDate;

    @SerializedName("updateUserId")
    int updateUserId;

    @SerializedName("updateDate")
    String updateDate;

    @SerializedName("totalAdults")
    int totalAdults;

    @SerializedName("totalChildren")
    int totalChildren;

    @SerializedName("totalSrCitizen")
    int totalSrCitizen;


    public GetSurveyResponsePojo(String referanceId, String name, String mobileNumber, int age, String gender, String bloodGroup, String qualification, String occupation, String maritalStatus, String marriageDate, String livingStatus, int totalMember, boolean willingStart, String resourcesAvailable, boolean memberJobOtherCity, int noOfVehicle, String vehicleType, int twoWheelerQty, int fourWheelerQty, int noPeopleVote, String socialMedia, String onlineShopping, String onlinePayApp, String insurance, boolean underInsurer, boolean ayushmanBeneficiary, boolean boosterShot, boolean memberDivyang, int updateUserId, String updateDate, int totalAdults, int totalChildren, int totalSrCitizen) {
        ReferanceId = referanceId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.qualification = qualification;
        this.occupation = occupation;
        this.maritalStatus = maritalStatus;
        this.marriageDate = marriageDate;
        this.livingStatus = livingStatus;
        this.totalMember = totalMember;
        this.willingStart = willingStart;
        this.resourcesAvailable = resourcesAvailable;
        this.memberJobOtherCity = memberJobOtherCity;
        this.noOfVehicle = noOfVehicle;
        this.vehicleType = vehicleType;
        this.twoWheelerQty = twoWheelerQty;
        this.fourWheelerQty = fourWheelerQty;
        this.noPeopleVote = noPeopleVote;
        this.socialMedia = socialMedia;
        this.onlineShopping = onlineShopping;
        this.onlinePayApp = onlinePayApp;
        this.insurance = insurance;
        this.underInsurer = underInsurer;
        this.ayushmanBeneficiary = ayushmanBeneficiary;
        this.boosterShot = boosterShot;
        this.memberDivyang = memberDivyang;
        this.updateUserId = updateUserId;
        this.updateDate = updateDate;
        this.totalAdults = totalAdults;
        this.totalChildren = totalChildren;
        this.totalSrCitizen = totalSrCitizen;
    }

    public int getSvId() {
        return svId;
    }

    public void setSvId(int svId) {
        this.svId = svId;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getReferanceId() {
        return ReferanceId;
    }

    public void setReferanceId(String referanceId) {
        ReferanceId = referanceId;
    }

    public String getHouseLat() {
        return houseLat;
    }

    public void setHouseLat(String houseLat) {
        this.houseLat = houseLat;
    }

    public String getHouseLong() {
        return houseLong;
    }

    public void setHouseLong(String houseLong) {
        this.houseLong = houseLong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(String marriageDate) {
        this.marriageDate = marriageDate;
    }

    public String getLivingStatus() {
        return livingStatus;
    }

    public void setLivingStatus(String livingStatus) {
        this.livingStatus = livingStatus;
    }

    public int getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(int totalMember) {
        this.totalMember = totalMember;
    }

    public boolean isWillingStart() {
        return willingStart;
    }

    public void setWillingStart(boolean willingStart) {
        this.willingStart = willingStart;
    }

    public String getResourcesAvailable() {
        return resourcesAvailable;
    }

    public void setResourcesAvailable(String resourcesAvailable) {
        this.resourcesAvailable = resourcesAvailable;
    }

    public boolean isMemberJobOtherCity() {
        return memberJobOtherCity;
    }

    public void setMemberJobOtherCity(boolean memberJobOtherCity) {
        this.memberJobOtherCity = memberJobOtherCity;
    }

    public int getNoOfVehicle() {
        return noOfVehicle;
    }

    public void setNoOfVehicle(int noOfVehicle) {
        this.noOfVehicle = noOfVehicle;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getTwoWheelerQty() {
        return twoWheelerQty;
    }

    public void setTwoWheelerQty(int twoWheelerQty) {
        this.twoWheelerQty = twoWheelerQty;
    }

    public String getThreeWheelerQty() {
        return threeWheelerQty;
    }

    public void setThreeWheelerQty(String threeWheelerQty) {
        this.threeWheelerQty = threeWheelerQty;
    }

    public int getFourWheelerQty() {
        return fourWheelerQty;
    }

    public void setFourWheelerQty(int fourWheelerQty) {
        this.fourWheelerQty = fourWheelerQty;
    }

    public int getNoPeopleVote() {
        return noPeopleVote;
    }

    public void setNoPeopleVote(int noPeopleVote) {
        this.noPeopleVote = noPeopleVote;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getOnlineShopping() {
        return onlineShopping;
    }

    public void setOnlineShopping(String onlineShopping) {
        this.onlineShopping = onlineShopping;
    }

    public String getPaymentModePrefer() {
        return paymentModePrefer;
    }

    public void setPaymentModePrefer(String paymentModePrefer) {
        this.paymentModePrefer = paymentModePrefer;
    }

    public String getOnlinePayApp() {
        return onlinePayApp;
    }

    public void setOnlinePayApp(String onlinePayApp) {
        this.onlinePayApp = onlinePayApp;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public boolean isUnderInsurer() {
        return underInsurer;
    }

    public void setUnderInsurer(boolean underInsurer) {
        this.underInsurer = underInsurer;
    }

    public boolean isAyushmanBeneficiary() {
        return ayushmanBeneficiary;
    }

    public void setAyushmanBeneficiary(boolean ayushmanBeneficiary) {
        this.ayushmanBeneficiary = ayushmanBeneficiary;
    }

    public boolean isBoosterShot() {
        return boosterShot;
    }

    public void setBoosterShot(boolean boosterShot) {
        this.boosterShot = boosterShot;
    }

    public boolean isMemberDivyang() {
        return memberDivyang;
    }

    public void setMemberDivyang(boolean memberDivyang) {
        this.memberDivyang = memberDivyang;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getTotalAdults() {
        return totalAdults;
    }

    public void setTotalAdults(int totalAdults) {
        this.totalAdults = totalAdults;
    }

    public int getTotalChildren() {
        return totalChildren;
    }

    public void setTotalChildren(int totalChildren) {
        this.totalChildren = totalChildren;
    }

    public int getTotalSrCitizen() {
        return totalSrCitizen;
    }

    public void setTotalSrCitizen(int totalSrCitizen) {
        this.totalSrCitizen = totalSrCitizen;
    }

    @Override
    public String toString() {
        return "GetSurveyResponsePojo{" +
                "svId=" + svId +
                ", houseId=" + houseId +
                ", ReferanceId='" + ReferanceId + '\'' +
                ", houseLat='" + houseLat + '\'' +
                ", houseLong='" + houseLong + '\'' +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", age=" + age +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", qualification='" + qualification + '\'' +
                ", occupation='" + occupation + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", marriageDate='" + marriageDate + '\'' +
                ", livingStatus='" + livingStatus + '\'' +
                ", totalMember=" + totalMember +
                ", willingStart=" + willingStart +
                ", resourcesAvailable='" + resourcesAvailable + '\'' +
                ", memberJobOtherCity=" + memberJobOtherCity +
                ", noOfVehicle=" + noOfVehicle +
                ", vehicleType='" + vehicleType + '\'' +
                ", twoWheelerQty=" + twoWheelerQty +
                ", threeWheelerQty='" + threeWheelerQty + '\'' +
                ", fourWheelerQty=" + fourWheelerQty +
                ", noPeopleVote=" + noPeopleVote +
                ", socialMedia='" + socialMedia + '\'' +
                ", onlineShopping='" + onlineShopping + '\'' +
                ", paymentModePrefer='" + paymentModePrefer + '\'' +
                ", onlinePayApp='" + onlinePayApp + '\'' +
                ", insurance='" + insurance + '\'' +
                ", underInsurer=" + underInsurer +
                ", ayushmanBeneficiary=" + ayushmanBeneficiary +
                ", boosterShot=" + boosterShot +
                ", memberDivyang=" + memberDivyang +
                ", createUserId='" + createUserId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateUserId=" + updateUserId +
                ", updateDate='" + updateDate + '\'' +
                ", totalAdults=" + totalAdults +
                ", totalChildren=" + totalChildren +
                ", totalSrCitizen=" + totalSrCitizen +
                '}';
    }
}
