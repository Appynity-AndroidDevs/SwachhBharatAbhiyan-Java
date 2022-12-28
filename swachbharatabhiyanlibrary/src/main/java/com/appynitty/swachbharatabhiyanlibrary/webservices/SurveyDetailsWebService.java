package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsRequestPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SurveyDetailsResultPojo;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SurveyDetailsWebService {
    @POST("api/Save/SurveyDetails")
    Call<List<SurveyDetailsResultPojo>> saveSurveyDetails(@Header("Content-Type") String content_type,
                                                    @Header("appId") String appId,
                                                     @Body SurveyDetailsRequestPojo detailsRequestPojo);
}
