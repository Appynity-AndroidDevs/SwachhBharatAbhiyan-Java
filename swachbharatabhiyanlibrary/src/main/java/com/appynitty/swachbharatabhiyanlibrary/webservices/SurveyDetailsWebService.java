package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.GetSurveyResponsePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResponsePojo;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
/***
 * Created BY Rahul Rokade
 * Date : 3 Jan 2023
 * */
public interface SurveyDetailsWebService {
    @POST("api/Save/SurveyDetails")
    Call<List<SurveyDetailsResponsePojo>> saveSurveyDetails(@Header("Content-Type") String content_type,
                                                            @Header("appId") String appId,
                                                            @Body List<SurveyDetailsRequestPojo> detailsRequestPojo);

    @GET("api/Get/SurveyDetails")
    Call<List<GetSurveyResponsePojo>> getSurveyDetails(@Header("Content-Type") String content_type,
                                                       @Header("appId") String appId,
                                                       @Header("referanceId") String referenceId);
}
