package com.example.android.goalsetter.Interface;

import com.example.android.goalsetter.Models.ProfileModelData;
import com.example.android.goalsetter.Models.RegisterResponseDataModel;
import com.example.android.goalsetter.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    String BASE_URL = "https://goalsetterapi.herokuapp.com/";

    @POST("api/register")
    @FormUrlEncoded
    Call<RegisterResponseDataModel> register(@Field("name") String name, @Field("email") String email,
                                             @Field("phone_number") String contact, @Field("account_type") String accountType,
                                             @Field("password") String password, @Field("password_confirmation") String confirmPassword);

    @POST("api/register")
    Call<RegisterResponseDataModel> register(@Body User user);

    @POST("api/login")
    @FormUrlEncoded
    Call<RegisterResponseDataModel> login(@Field("email") String email, @Field("password") String password);

    @GET("api/profile")
    Call<ProfileModelData> profile(@Header("Authorization") String token);

    @PUT("api/profile/edit")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<ProfileModelData> updateProfile(@Header("Authorization") String token,
                                         @Field("user") User user);

    @POST("api/logout")
    Call<Boolean> logout();
}
