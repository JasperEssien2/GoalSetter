package com.example.android.goalsetter.Interface;

import com.example.android.goalsetter.Models.RegisterResponseDataModel;
import com.example.android.goalsetter.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    String BASE_URL = "https://goaltickapp.herokuapp.com/";

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

    @GET("api/dashboard")
    Call<User> dashboard(@Header("token") String token);

    @POST("api/logout")
    Call<Boolean> logout();
}
