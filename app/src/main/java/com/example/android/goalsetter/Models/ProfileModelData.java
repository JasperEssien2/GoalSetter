package com.example.android.goalsetter.Models;

import com.google.gson.annotations.SerializedName;

public class ProfileModelData {


    @SerializedName("data")
    private ProfileModelResponse modelResponse;

    public ProfileModelResponse getModelResponse() {
        return modelResponse;
    }

    public void setModelResponse(ProfileModelResponse modelResponse) {
        this.modelResponse = modelResponse;
    }

    public static class ProfileModelResponse {

        @SerializedName("success")
        private boolean isSuccessful;
        @SerializedName("user")
        private User user;
        @SerializedName("image_link")
        private String ImageLink;

        public ProfileModelResponse() {
        }

        public boolean isSuccessful() {
            return isSuccessful;
        }

        public void setSuccessful(boolean successful) {
            isSuccessful = successful;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getImageLink() {
            return ImageLink;
        }

        public void setImageLink(String imageLink) {
            ImageLink = imageLink;
        }
    }
}
