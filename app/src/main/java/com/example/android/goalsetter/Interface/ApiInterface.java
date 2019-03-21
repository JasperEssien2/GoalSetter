package com.example.android.goalsetter.Interface;

import com.example.android.goalsetter.Models.Goal;
import com.example.android.goalsetter.Models.GoalListModelData;
import com.example.android.goalsetter.Models.GoalModelData;
import com.example.android.goalsetter.Models.ProfileModelData;
import com.example.android.goalsetter.Models.RegisterResponseDataModel;
import com.example.android.goalsetter.Models.User;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

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


    @Multipart
    @POST("api/profile/upload")
    Call<ResponseBody> updateProfileImage(@Header("Authorization") String token, @Part MultipartBody.Part file);


    @POST("api/goals/create")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<GoalModelData> createGoal(@Header("Authorization") String token,
                                   @Field("title") String title, @Field("description") String description,
                                   @Field("begin_date") String begin_date, @Field("due_date") String due_date,
                                   @Field("level") String level);

    @POST("api/goals/create")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<GoalModelData> createGoal(@Header("Authorization") String token,
                                   @Body Goal goal);

    @GET("api/goals")
    Call<GoalListModelData> getAllGoals(@Header("Authorization") String token);


    @POST("api/logout")
    Call<Boolean> logout();
}
