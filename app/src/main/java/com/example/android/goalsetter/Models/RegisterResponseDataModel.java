package com.example.android.goalsetter.Models;

import com.google.gson.annotations.SerializedName;

public class RegisterResponseDataModel {

    @SerializedName("data")
    private RegisterResponseModel registerResponseModel;

    public RegisterResponseDataModel(RegisterResponseModel registerResponseModel) {
        this.registerResponseModel = registerResponseModel;
    }

    public RegisterResponseModel getRegisterResponseModel() {
        return registerResponseModel;
    }

    public void setRegisterResponseModel(RegisterResponseModel registerResponseModel) {
        this.registerResponseModel = registerResponseModel;
    }

    public static class RegisterResponseModel {
        @SerializedName("token")
        private String token;
        @SerializedName("user")
        private User user;
        @SerializedName("success")
        private boolean isSuccessful;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public boolean isSuccessful() {
            return isSuccessful;
        }

        public void setSuccessful(boolean successful) {
            isSuccessful = successful;
        }
    }
}
