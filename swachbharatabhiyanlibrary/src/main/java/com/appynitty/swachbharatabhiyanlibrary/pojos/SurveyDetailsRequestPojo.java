package com.appynitty.swachbharatabhiyanlibrary.pojos;

import com.google.gson.annotations.SerializedName;

public class SurveyDetailsRequestPojo {
    @SerializedName("ReferanceId")
    String ReferanceId;

    @SerializedName("name")
    String name;

    @SerializedName("houseLat")
    String houseLat;

    @SerializedName("houseLong")
    String houseLong;

    @SerializedName("mobileNumber")
    String mobileNumber;

    /*@SerializedName("dateOfBirth")
    Date dateOfBirth;*/

    @SerializedName("dateOfBirth")
    String dateOfBirth;

    @SerializedName("age")
    String age;

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

   /* @SerializedName("marriageDate")
    Date marriageDate;*/

    @SerializedName("marriageDate")
    String marriageDate;

    @SerializedName("livingStatus")
    String livingStatus;

    @SerializedName("totalAdults")
    String totalAdults;

    @SerializedName("totalChildren")
    String totalChildren;

    @SerializedName("totalSrCitizen")
    String totalSrCitizen;

    @SerializedName("totalMember")
    String totalMember;

    @SerializedName("willingStart")
    String willingStart;

    @SerializedName("resourcesAvailable")
    String resourcesAvailable;

    @SerializedName("memberJobOtherCity")
    String memberJobOtherCity;

    @SerializedName("noOfVehicle")
    String noOfVehicle;

    @SerializedName("twoWheelerQty")
    String twoWheelerQty;

    @SerializedName("fourWheelerQty")
    String fourWheelerQty;

    @SerializedName("noPeopleVote")
    String noPeopleVote;

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
    String underInsurer;

    @SerializedName("ayushmanBeneficiary")
    String ayushmanBeneficiary;

    @SerializedName("boosterShot")
    String boosterShot;

    @SerializedName("memberDivyang")
    String memberDivyang;

    @SerializedName("createUserId")
    String createUserId;

    @SerializedName("updateUserId")
    String updateUserId;

    public SurveyDetailsRequestPojo(String referanceId, String name, String houseLat, String houseLong, String mobileNumber, String dateOfBirth, String age, String gender, String bloodGroup, String qualification, String occupation, String maritalStatus, String marriageDate, String livingStatus, String totalAdults, String totalChildren, String totalSrCitizen, String totalMember, String willingStart, String resourcesAvailable, String memberJobOtherCity, String noOfVehicle, String twoWheelerQty, String fourWheelerQty, String noPeopleVote, String socialMedia, String onlineShopping, String paymentModePrefer, String onlinePayApp, String insurance, String underInsurer, String ayushmanBeneficiary, String boosterShot, String memberDivyang, String createUserId, String updateUserId) {
        ReferanceId = referanceId;
        this.name = name;
        this.houseLat = houseLat;
        this.houseLong = houseLong;
        this.mobileNumber = mobileNumber;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.qualification = qualification;
        this.occupation = occupation;
        this.maritalStatus = maritalStatus;
        this.marriageDate = marriageDate;
        this.livingStatus = livingStatus;
        this.totalAdults = totalAdults;
        this.totalChildren = totalChildren;
        this.totalSrCitizen = totalSrCitizen;
        this.totalMember = totalMember;
        this.willingStart = willingStart;
        this.resourcesAvailable = resourcesAvailable;
        this.memberJobOtherCity = memberJobOtherCity;
        this.noOfVehicle = noOfVehicle;
        this.twoWheelerQty = twoWheelerQty;
        this.fourWheelerQty = fourWheelerQty;
        this.noPeopleVote = noPeopleVote;
        this.socialMedia = socialMedia;
        this.onlineShopping = onlineShopping;
        this.paymentModePrefer = paymentModePrefer;
        this.onlinePayApp = onlinePayApp;
        this.insurance = insurance;
        this.underInsurer = underInsurer;
        this.ayushmanBeneficiary = ayushmanBeneficiary;
        this.boosterShot = boosterShot;
        this.memberDivyang = memberDivyang;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
    }

    public SurveyDetailsRequestPojo() {

    }

    public String getReferanceId() {
        return ReferanceId;
    }

    public void setReferanceId(String referanceId) {
        ReferanceId = referanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getTotalAdults() {
        return totalAdults;
    }

    public void setTotalAdults(String totalAdults) {
        this.totalAdults = totalAdults;
    }

    public String getTotalChildren() {
        return totalChildren;
    }

    public void setTotalChildren(String totalChildren) {
        this.totalChildren = totalChildren;
    }

    public String getTotalSrCitizen() {
        return totalSrCitizen;
    }

    public void setTotalSrCitizen(String totalSrCitizen) {
        this.totalSrCitizen = totalSrCitizen;
    }

    public String getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(String totalMember) {
        this.totalMember = totalMember;
    }

    public String getWillingStart() {
        return willingStart;
    }

    public void setWillingStart(String willingStart) {
        this.willingStart = willingStart;
    }

    public String getResourcesAvailable() {
        return resourcesAvailable;
    }

    public void setResourcesAvailable(String resourcesAvailable) {
        this.resourcesAvailable = resourcesAvailable;
    }

    public String getMemberJobOtherCity() {
        return memberJobOtherCity;
    }

    public void setMemberJobOtherCity(String memberJobOtherCity) {
        this.memberJobOtherCity = memberJobOtherCity;
    }

    public String getNoOfVehicle() {
        return noOfVehicle;
    }

    public void setNoOfVehicle(String noOfVehicle) {
        this.noOfVehicle = noOfVehicle;
    }

    public String getTwoWheelerQty() {
        return twoWheelerQty;
    }

    public void setTwoWheelerQty(String twoWheelerQty) {
        this.twoWheelerQty = twoWheelerQty;
    }

    public String getFourWheelerQty() {
        return fourWheelerQty;
    }

    public void setFourWheelerQty(String fourWheelerQty) {
        this.fourWheelerQty = fourWheelerQty;
    }

    public String getNoPeopleVote() {
        return noPeopleVote;
    }

    public void setNoPeopleVote(String noPeopleVote) {
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

    public String getUnderInsurer() {
        return underInsurer;
    }

    public void setUnderInsurer(String underInsurer) {
        this.underInsurer = underInsurer;
    }

    public String getAyushmanBeneficiary() {
        return ayushmanBeneficiary;
    }

    public void setAyushmanBeneficiary(String ayushmanBeneficiary) {
        this.ayushmanBeneficiary = ayushmanBeneficiary;
    }

    public String getBoosterShot() {
        return boosterShot;
    }

    public void setBoosterShot(String boosterShot) {
        this.boosterShot = boosterShot;
    }

    public String getMemberDivyang() {
        return memberDivyang;
    }

    public void setMemberDivyang(String memberDivyang) {
        this.memberDivyang = memberDivyang;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }



    @Override
    public String toString() {
        return "SurveyDetailsResponsePojo{" +
                "ReferanceId='" + ReferanceId + '\'' +
                ", name='" + name + '\'' +
                ", houseLat='" + houseLat + '\'' +
                ", houseLong='" + houseLong + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", qualification='" + qualification + '\'' +
                ", occupation='" + occupation + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", marriageDate='" + marriageDate + '\'' +
                ", livingStatus='" + livingStatus + '\'' +
                ", totalAdults='" + totalAdults + '\'' +
                ", totalChildren='" + totalChildren + '\'' +
                ", totalSrCitizen='" + totalSrCitizen + '\'' +
                ", totalMember='" + totalMember + '\'' +
                ", willingStart='" + willingStart + '\'' +
                ", resourcesAvailable='" + resourcesAvailable + '\'' +
                ", memberJobOtherCity='" + memberJobOtherCity + '\'' +
                ", noOfVehicle='" + noOfVehicle + '\'' +
                ", twoWheelerQty='" + twoWheelerQty + '\'' +
                ", fourWheelerQty='" + fourWheelerQty + '\'' +
                ", noPeopleVote='" + noPeopleVote + '\'' +
                ", socialMedia='" + socialMedia + '\'' +
                ", onlineShopping='" + onlineShopping + '\'' +
                ", paymentModePrefer='" + paymentModePrefer + '\'' +
                ", onlinePayApp='" + onlinePayApp + '\'' +
                ", insurance='" + insurance + '\'' +
                ", underInsurer='" + underInsurer + '\'' +
                ", ayushmanBeneficiary='" + ayushmanBeneficiary + '\'' +
                ", boosterShot='" + boosterShot + '\'' +
                ", memberDivyang='" + memberDivyang + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", updateUserId='" + updateUserId + '\'' +
                '}';
    }
}
