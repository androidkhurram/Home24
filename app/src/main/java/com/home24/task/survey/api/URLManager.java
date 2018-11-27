package com.home24.task.survey.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Muhammad on 21/03/2015.
 */
public interface URLManager
{
    @GET
    Call<ResponseBody> getSurveys(@Url String url);

    /****
     *
     * @param name
     * @param email
     * @param deviceType
     * @param deviceToken
     */
    @FormUrlEncoded
    @POST("main/register")
    Call<ResponseBody> registerUser(
            @Field("latitude") double latitude,
            @Field("longitude") double longitude,
            @Field("firstname") String name,
            @Field("lastname") String lastname,
            @Field("email") String email,
            @Field("zip") String zip,
            @Field("gender") String gender,
            @Field("storeId") String storeId,
            @Field("deviceToken") String deviceToken,
            @Field("deviceType") String deviceType,
            @Field("phone") String phone,
            @Field("countryCode") String countryCode,
            @Field("code") String code
    );


    @FormUrlEncoded
    @POST("main/savePhone")
    Call<ResponseBody> savePhone(
            @Field("userId") String userId,
            @Field("phone") String phone,
            @Field("countryCode") String countryCode,
            @Field("code") String code
    );

    /**
     *
     */

    @GET("main/getRings")
    Call<ResponseBody> getRingDetails(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("storeId") String storeId
    );

    /**
     *
     * @param userId
     * @param storeId
     * @return
     */
    @GET("main/alertStore")
    Call<ResponseBody> alertStore(
            @Query("userId") String userId,
            @Query("storeId") String storeId)
            ;

    @GET("main/callStore")
    Call<ResponseBody> callStore(
            @Query("userId") String userId,
            @Query("storeId") String storeId
    );


    @GET("main/requestQuote")
    Call<ResponseBody> requestQuote(
            @Query("userId") String userId,
            @Query("storeId") String storeId,
            @Query("ringId") String ringId,
            @Query("ringNum") String ringNum,
            @Query("phone") String phone,
            @Query("deviceType") String deviceType
    );

    /**
     *
     */
    @FormUrlEncoded
    @POST("main/searchDiamonds")
    Call<ResponseBody> searchDiamonds(
            @Field("shape") String shape,
            @Field("color") String color,
            @Field("clarity") String clarity,
            @Field("cut") String cut,
            @Field("lowPrice") int lowPrice,
            @Field("highPrice") int highPrice,
            @Field("lowCarat") float lowCarat,
            @Field("highCarat") float highCarat,
            @Field("storeKey") String storeKey
    );


    /**
     *
     */
    @FormUrlEncoded
    @POST("main/requestAppointment")
    Call<ResponseBody> makeAppointment(
            @Field("message") String message,
            @Field("subject") String subject,
            @Field("storeId") String storeId,
            @Field("email") String email
    );
}

