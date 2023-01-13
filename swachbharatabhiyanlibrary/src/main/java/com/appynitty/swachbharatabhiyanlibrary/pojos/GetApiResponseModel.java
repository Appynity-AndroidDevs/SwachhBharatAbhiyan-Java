package com.appynitty.swachbharatabhiyanlibrary.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetApiResponseModel extends Throwable implements Parcelable {

    @SerializedName("houseId")
    @Expose
    String houseId;
    @SerializedName("ReferanceId")
    @Expose
    String ReferanceId;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("mobileNumber")
    @Expose
    String mobileNumber;
    @SerializedName("age")
    @Expose
    String age;
    @SerializedName("dateOfBirth")
    @Expose
    String dateOfBirth;
    @SerializedName("birtDay")
    @Expose
    String birtDay;
    @SerializedName("birthMonth")
    @Expose
    String birthMonth;
    @SerializedName("birthYear")
    @Expose
    String birthYear;
    @SerializedName("gender")
    @Expose
    String gender;
    @SerializedName("bloodGroup")
    @Expose
    String bloodGroup;
    @SerializedName("qualification")
    @Expose
    String qualification;
    @SerializedName("occupation")
    @Expose
    String occupation;
    @SerializedName("maritalStatus")
    @Expose
    String maritalStatus;
    @SerializedName("marriageDate")
    @Expose
    String marriageDate;
    @SerializedName("marriageDay")
    @Expose
    String marriageDay;
    @SerializedName("marriageMonth")
    @Expose
    String marriageMonth;
    @SerializedName("marriageYear")
    @Expose
    String marriageYear;
    @SerializedName("livingStatus")
    @Expose
    String livingStatus;
    @SerializedName("totalMember")
    @Expose
    String totalMember;
    @SerializedName("adults")
    @Expose
    String adults;
    @SerializedName("children")
    @Expose
    String children;
    @SerializedName("srCitizen")
    @Expose
    String srCitizen;
    @SerializedName("willingStart")
    @Expose
    String willingStart;
    @SerializedName("resourcesAvailable")
    @Expose
    String resourcesAvailable;
    @SerializedName("memberJobOtherCity")
    @Expose
    String memberJobOtherCity;
    @SerializedName("totalVehicle")
    @Expose
    String totalVehicle;
    @SerializedName("twoWheelerQty")
    @Expose
    String twoWheelerQty;
    @SerializedName("fourWheelerQty")
    @Expose
    String fourWheelerQty;
    @SerializedName("noPeopleVote")
    @Expose
    String noPeopleVote;
    @SerializedName("socialMedia")
    @Expose
    String socialMedia;
    @SerializedName("onlineShopping")
    @Expose
    String onlineShopping;
    @SerializedName("onlinePayApp")
    @Expose
    String onlinePayApp;
    @SerializedName("insurance")
    @Expose
    String insurance;
    @SerializedName("underInsurer")
    @Expose
    String underInsurer;
    @SerializedName("ayushmanBeneficiary")
    @Expose
    String ayushmanBeneficiary;
    @SerializedName("boosterShot")
    @Expose
    String boosterShot;
    @SerializedName("memberDivyang")
    @Expose
    String memberDivyang;


    public GetApiResponseModel(String houseId, String referanceId, String name, String mobileNumber, String age, String dateOfBirth, String birtDay, String birthMonth, String birthYear, String gender, String bloodGroup, String qualification, String occupation, String maritalStatus, String marriageDate, String marriageDay, String marriageMonth, String marriageYear, String livingStatus, String totalMember, String adults, String children, String srCitizen, String willingStart, String resourcesAvailable, String memberJobOtherCity, String totalVehicle, String twoWheelerQty, String fourWheelerQty, String noPeopleVote, String socialMedia, String onlineShopping, String onlinePayApp, String insurance, String underInsurer, String ayushmanBeneficiary, String boosterShot, String memberDivyang) {
        this.houseId = houseId;
        ReferanceId = referanceId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.birtDay = birtDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.qualification = qualification;
        this.occupation = occupation;
        this.maritalStatus = maritalStatus;
        this.marriageDate = marriageDate;
        this.marriageDay = marriageDay;
        this.marriageMonth = marriageMonth;
        this.marriageYear = marriageYear;
        this.livingStatus = livingStatus;
        this.totalMember = totalMember;
        this.adults = adults;
        this.children = children;
        this.srCitizen = srCitizen;
        this.willingStart = willingStart;
        this.resourcesAvailable = resourcesAvailable;
        this.memberJobOtherCity = memberJobOtherCity;
        this.totalVehicle = totalVehicle;
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
    }

    public GetApiResponseModel() {
    }

    protected GetApiResponseModel(Parcel in) {
        houseId = in.readString();
        ReferanceId = in.readString();
        name = in.readString();
        mobileNumber = in.readString();
        age = in.readString();
        dateOfBirth = in.readString();
        birtDay = in.readString();
        birthMonth = in.readString();
        birthYear = in.readString();
        gender = in.readString();
        bloodGroup = in.readString();
        qualification = in.readString();
        occupation = in.readString();
        maritalStatus = in.readString();
        marriageDate = in.readString();
        marriageDay = in.readString();
        marriageMonth = in.readString();
        marriageYear = in.readString();
        livingStatus = in.readString();
        totalMember = in.readString();
        adults = in.readString();
        children = in.readString();
        srCitizen = in.readString();
        willingStart = in.readString();
        resourcesAvailable = in.readString();
        memberJobOtherCity = in.readString();
        totalVehicle = in.readString();
        twoWheelerQty = in.readString();
        fourWheelerQty = in.readString();
        noPeopleVote = in.readString();
        socialMedia = in.readString();
        onlineShopping = in.readString();
        onlinePayApp = in.readString();
        insurance = in.readString();
        underInsurer = in.readString();
        ayushmanBeneficiary = in.readString();
        boosterShot = in.readString();
        memberDivyang = in.readString();
    }

    public static final Creator<GetApiResponseModel> CREATOR = new Creator<GetApiResponseModel>() {
        @Override
        public GetApiResponseModel createFromParcel(Parcel in) {
            return new GetApiResponseModel(in);
        }

        @Override
        public GetApiResponseModel[] newArray(int size) {
            return new GetApiResponseModel[size];
        }
    };

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBirtDay() {
        return birtDay;
    }

    public void setBirtDay(String birtDay) {
        this.birtDay = birtDay;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
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

    public String getMarriageDay() {
        return marriageDay;
    }

    public void setMarriageDay(String marriageDay) {
        this.marriageDay = marriageDay;
    }

    public String getMarriageMonth() {
        return marriageMonth;
    }

    public void setMarriageMonth(String marriageMonth) {
        this.marriageMonth = marriageMonth;
    }

    public String getMarriageYear() {
        return marriageYear;
    }

    public void setMarriageYear(String marriageYear) {
        this.marriageYear = marriageYear;
    }

    public String getLivingStatus() {
        return livingStatus;
    }

    public void setLivingStatus(String livingStatus) {
        this.livingStatus = livingStatus;
    }

    public String getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(String totalMember) {
        this.totalMember = totalMember;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getSrCitizen() {
        return srCitizen;
    }

    public void setSrCitizen(String srCitizen) {
        this.srCitizen = srCitizen;
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

    public String getTotalVehicle() {
        return totalVehicle;
    }

    public void setTotalVehicle(String totalVehicle) {
        this.totalVehicle = totalVehicle;
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

    @Override
    public String toString() {
        return "GetApiResponseModel{" +
                "houseId='" + houseId + '\'' +
                ", ReferanceId='" + ReferanceId + '\'' +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", age='" + age + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", birtDay='" + birtDay + '\'' +
                ", birthMonth='" + birthMonth + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", qualification='" + qualification + '\'' +
                ", occupation='" + occupation + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", marriageDate='" + marriageDate + '\'' +
                ", marriageDay='" + marriageDay + '\'' +
                ", marriageMonth='" + marriageMonth + '\'' +
                ", marriageYear='" + marriageYear + '\'' +
                ", livingStatus='" + livingStatus + '\'' +
                ", totalMember='" + totalMember + '\'' +
                ", adults='" + adults + '\'' +
                ", children='" + children + '\'' +
                ", srCitizen='" + srCitizen + '\'' +
                ", willingStart='" + willingStart + '\'' +
                ", resourcesAvailable='" + resourcesAvailable + '\'' +
                ", memberJobOtherCity='" + memberJobOtherCity + '\'' +
                ", totalVehicle='" + totalVehicle + '\'' +
                ", twoWheelerQty='" + twoWheelerQty + '\'' +
                ", fourWheelerQty='" + fourWheelerQty + '\'' +
                ", noPeopleVote='" + noPeopleVote + '\'' +
                ", socialMedia='" + socialMedia + '\'' +
                ", onlineShopping='" + onlineShopping + '\'' +
                ", onlinePayApp='" + onlinePayApp + '\'' +
                ", insurance='" + insurance + '\'' +
                ", underInsurer='" + underInsurer + '\'' +
                ", ayushmanBeneficiary='" + ayushmanBeneficiary + '\'' +
                ", boosterShot='" + boosterShot + '\'' +
                ", memberDivyang='" + memberDivyang + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(houseId);
        parcel.writeString(ReferanceId);
        parcel.writeString(name);
        parcel.writeString(mobileNumber);
        parcel.writeString(age);
        parcel.writeString(dateOfBirth);
        parcel.writeString(birtDay);
        parcel.writeString(birthMonth);
        parcel.writeString(birthYear);
        parcel.writeString(gender);
        parcel.writeString(bloodGroup);
        parcel.writeString(qualification);
        parcel.writeString(occupation);
        parcel.writeString(maritalStatus);
        parcel.writeString(marriageDate);
        parcel.writeString(marriageDay);
        parcel.writeString(marriageMonth);
        parcel.writeString(marriageYear);
        parcel.writeString(livingStatus);
        parcel.writeString(totalMember);
        parcel.writeString(adults);
        parcel.writeString(children);
        parcel.writeString(srCitizen);
        parcel.writeString(willingStart);
        parcel.writeString(resourcesAvailable);
        parcel.writeString(memberJobOtherCity);
        parcel.writeString(totalVehicle);
        parcel.writeString(twoWheelerQty);
        parcel.writeString(fourWheelerQty);
        parcel.writeString(noPeopleVote);
        parcel.writeString(socialMedia);
        parcel.writeString(onlineShopping);
        parcel.writeString(onlinePayApp);
        parcel.writeString(insurance);
        parcel.writeString(underInsurer);
        parcel.writeString(ayushmanBeneficiary);
        parcel.writeString(boosterShot);
        parcel.writeString(memberDivyang);
    }
}
