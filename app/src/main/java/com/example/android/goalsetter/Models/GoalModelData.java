package com.example.android.goalsetter.Models;

import com.google.gson.annotations.SerializedName;

public class GoalModelData {


    @SerializedName("data")
    private GoalModelResponse modelResponse;

    public GoalModelResponse getModelResponse() {
        return modelResponse;
    }

    public void setModelResponse(GoalModelResponse modelResponse) {
        this.modelResponse = modelResponse;
    }

    public static class GoalModelResponse {

        @SerializedName("success")
        private boolean isSuccessful;
        @SerializedName("goal")
        private Goal goal;
        @SerializedName("error")
        private boolean error;
        @SerializedName("message")
        private String message;
//        @SerializedName("image_link")
//        private String ImageLink;

        public GoalModelResponse() {
        }

        public boolean isSuccessful() {
            return isSuccessful;
        }

        public void setSuccessful(boolean successful) {
            isSuccessful = successful;
        }

        public Goal getGoal() {
            return goal;
        }

        public void setGoal(Goal goal) {
            this.goal = goal;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

//        public String getImageLink() {
//            return ImageLink;
//        }
//
//        public void setImageLink(String imageLink) {
//            ImageLink = imageLink;
//        }
    }
}
