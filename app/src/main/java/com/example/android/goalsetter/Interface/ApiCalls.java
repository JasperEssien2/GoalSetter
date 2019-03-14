package com.example.android.goalsetter.Interface;

import android.util.Log;

import com.example.android.goalsetter.ApiClient;
import com.example.android.goalsetter.Models.RegisterResponseDataModel;
import com.example.android.goalsetter.Models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.example.android.goalsetter.Models.ACCOUNT_TYPE;

//import static com.example.android.goalsetter.Models.ACCOUNT_TYPE.company;

public class ApiCalls {
    private static final String TAG = ApiCalls.class.getSimpleName();
    ApiInterface apiInterface;
    private ApiCallsCallback callback;

    public ApiCalls(ApiCallsCallback callback) {
        this.callback = callback;
        apiInterface = ApiClient.getClient()
                .create(ApiInterface.class);
    }

    public void register(User user) {
//        String accountType = "";
//        if (user.getAccount_type() == company)
//            accountType = "company";
//        else if (user.getAccount_type() == beam)
//            accountType = "beam";
//        else
//            accountType = "personal";
        user.setAccount_type("personal");

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

    public void dashBoard(String token) {
        apiInterface.dashboard(token)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        callback.dashBoard(user);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
    }
}
