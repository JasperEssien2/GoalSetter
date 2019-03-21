package com.example.android.goalsetter;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.android.goalsetter.Interface.ApiCallsCallback;
import com.example.android.goalsetter.Interface.ApiInterface;
import com.example.android.goalsetter.Interface.GoalsApiCallback;
import com.example.android.goalsetter.Models.Goal;
import com.example.android.goalsetter.Models.GoalListModelData;
import com.example.android.goalsetter.Models.GoalModelData;
import com.example.android.goalsetter.Models.ProfileModelData;
import com.example.android.goalsetter.Models.RegisterResponseDataModel;
import com.example.android.goalsetter.Models.User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.example.android.goalsetter.Models.ACCOUNT_TYPE;

//import static com.example.android.goalsetter.Models.ACCOUNT_TYPE.company;

public class ApiCalls {
    private static final String TAG = ApiCalls.class.getSimpleName();
    ApiInterface apiInterface;
    private ApiCallsCallback callback;
    private GoalsApiCallback goalsApiCallback;

    public ApiCalls(ApiCallsCallback callback) {
        this.callback = callback;
        apiInterface = ApiClient.getClient()
                .create(ApiInterface.class);
    }

    public ApiCalls(GoalsApiCallback callback) {
        this.goalsApiCallback = callback;
        apiInterface = ApiClient.getClient()
                .create(ApiInterface.class);
    }

    /**
     * This method calls the api that registers a user
     *
     * @param user type User containing user details of the user
     */
    public void register(User user) {
//        String accountType = "";
//        if (user.getAccount_type() == company)
//            accountType = "company";
//        else if (user.getAccount_type() == beam)
//            accountType = "beam";
//        else
//            accountType = "personal";
//        user.setAccount_type("personal");

        apiInterface.register(user.getName(), user.getEmailAddress(), user.getContact(), user.getAccount_type(), user.getPassword(), user.getConfirmPassword())
                .enqueue(new Callback<RegisterResponseDataModel>() {
                    @Override
                    public void onResponse(Call<RegisterResponseDataModel> call, Response<RegisterResponseDataModel> response) {
                        RegisterResponseDataModel registerResponseModel = response.body();
                        Log.e(TAG, "Url ---------- " + call.request().url());
                        Log.e(TAG, "notification url ------- " + response.raw().toString());

                        if (registerResponseModel != null) {
//                            Log.e(TAG, "token ------- " + registerResponseModel.getToken());
                            callback.register(registerResponseModel.getRegisterResponseModel().getUser(),
                                    registerResponseModel.getRegisterResponseModel().getToken());
                        } else callback.register(null, null);

                    }

                    @Override
                    public void onFailure(Call<RegisterResponseDataModel> call, Throwable t) {
                        Log.e(TAG, "Url ---------- " + call.request().url());
                        Log.e(TAG, "onFailure ---------- " + t.getMessage());
                        callback.register(null, null);
                    }
                });
    }

    /**
     * This method is called to call the api that login users
     *
     * @param email    of the user (type string)
     * @param password of the user (type string)
     */
    public void login(String email, String password) {
        apiInterface.login(email, password)
                .enqueue(new Callback<RegisterResponseDataModel>() {
                    @Override
                    public void onResponse(Call<RegisterResponseDataModel> call, Response<RegisterResponseDataModel> response) {
                        Log.e(TAG, "Url ---------- " + call.request().url());
                        Log.e(TAG, "notification url ------- " + response.raw().toString());
                        RegisterResponseDataModel token = response.body();
//                        if(token == null) return;
                        callback.login(token);
                    }

                    @Override
                    public void onFailure(Call<RegisterResponseDataModel> call, Throwable t) {
                        Log.e(TAG, "Url ---------- " + call.request().url());
                        Log.e(TAG, "onFailure ---------- " + t.getMessage());
                        callback.login(null);
                    }
                });
    }

    /**
     * Methood to call the api that gets the user's profile details
     *
     * @param token token of the user to be passed to header of the api
     */
    public void profile(final String token) {
        apiInterface.profile(token)
                .enqueue(new Callback<ProfileModelData>() {
                    @Override
                    public void onResponse(Call<ProfileModelData> call, Response<ProfileModelData> response) {
//                        User user = response.body();
                        Log.e(TAG, "onResponse() --------------- " + response.raw().toString());
                        Log.e(TAG, "token --------------- " + token);

                        if (response.body() != null)
                            callback.profile(response.body().getModelResponse());
                    }

                    @Override
                    public void onFailure(Call<ProfileModelData> call, Throwable t) {
                        Log.e(TAG, "onFailure() --------------- " + t.getMessage());
                        ApiCalls.this.profile(token);
                    }
                });
    }

    /**
     * Method call to update profile of user
     *
     * @param token a string of the user's token
     * @param user  user details
     */
    public void updateProfile(final String token, final User user) {
        apiInterface.updateProfile(token, user)
                .enqueue(new Callback<ProfileModelData>() {
                    @Override
                    public void onResponse(Call<ProfileModelData> call, Response<ProfileModelData> response) {
//                        User user = response.body();
                        Log.e(TAG, "onResponse() --------------- " + response.raw().toString());
                        Log.e(TAG, "token --------------- " + token);
                        if (response.body() != null)
                            callback.updateProfile(response.body().getModelResponse());
                        else callback.updateProfile(null);
                    }

                    @Override
                    public void onFailure(Call<ProfileModelData> call, Throwable t) {
                        Log.e(TAG, "onFailure() --------------- " + t.getMessage());
                    }
                });
    }


    /**
     * This method task is to add goals to the api database
     *
     * @param token a string of the user's token
     * @param goal  the goal object to add
     */
    public void addGoal(final String token, final Goal goal) {
        apiInterface.createGoal(token, goal.getTitle(), goal.getDescription(), goal.getStartTime(), goal.getDueTime(),
                goal.getLevel())
                .enqueue(new Callback<GoalModelData>() {
                    @Override
                    public void onResponse(Call<GoalModelData> call, Response<GoalModelData> response) {
                        Log.e(TAG, "onResponse() addGoal --------------- " + response.raw().toString());
                        Log.e(TAG, "token --------------- " + token);
                        if (response.body() == null)
                            goalsApiCallback.goalAdded(null);
                        else goalsApiCallback.goalAdded(response.body().getModelResponse());
                    }

                    @Override
                    public void onFailure(Call<GoalModelData> call, Throwable t) {
                        //This will help it to keep trying when it fails
                        ApiCalls.this.addGoal(token, goal);
                        goalsApiCallback.goalAdded(null);
                        Log.e(TAG, "onFaliure() adding goals  " + t.getMessage());
                    }
                });
    }

    /**
     * This method task is to get all the user's goal in the database
     *
     * @param token the string token of the user
     */
    public void getGoals(final String token) {
        apiInterface.getAllGoals(token)
                .enqueue(new Callback<GoalListModelData>() {
                    @Override
                    public void onResponse(Call<GoalListModelData> call, Response<GoalListModelData> response) {
                        Log.e(TAG, "onResponse() getGoals --------------- " + response.raw().toString());
                        Log.e(TAG, "token --------------- " + token);
                        goalsApiCallback.goalListGotten(response.body().getModelResponse());
                    }

                    @Override
                    public void onFailure(Call<GoalListModelData> call, Throwable t) {
                        goalsApiCallback.goalListGotten(null);
                        Log.e(TAG, "onFaliure() get goals  " + t.getMessage());
                        //This will help it to keep trying when it fails
                        ApiCalls.this.getGoals(token);
                    }
                });
    }

    public void uploadImage(final Activity activity, final Uri mediaPath, final String token) {
        String selectedImagePath = null;
        Uri selectedImageUri = mediaPath;
        Cursor cursor = activity.getContentResolver().query(
                selectedImageUri, null, null, null, null);
        if (cursor == null) {
            selectedImagePath = selectedImageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            selectedImagePath = cursor.getString(idx);
        }

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(selectedImagePath);

        // Parsing any Media type file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("user_image", file.getName(), requestFile);

        apiInterface.updateProfileImage(token, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG, "onResponse() --------------- " + response.raw().toString());
                if (response != null) {
                    callback.imageUploaded();
//                    if (requestBody.getSuccess()) {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
                } else {
//                    assert serverResponse != null;
//                    Log.v("Response", serverResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure() --------------- " + t.getMessage());
                ApiCalls.this.uploadImage(activity, mediaPath, token);
            }
        });
    }
}
