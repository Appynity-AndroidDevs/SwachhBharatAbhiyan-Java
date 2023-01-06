package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResponsePojo;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SurveyDetailsWebService {
    @POST("api/Save/SurveyDetails")
    Call<List<SurveyDetailsResponsePojo>> saveSurveyDetails(@Header("Content-Type") String content_type,
                                                            @Header("appId") String appId,
                                                            @Body List<SurveyDetailsRequestPojo> detailsRequestPojo);
}
