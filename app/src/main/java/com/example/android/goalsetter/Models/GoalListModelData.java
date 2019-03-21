package com.example.android.goalsetter.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoalListModelData {


    @SerializedName("data")
    private GoalListModelResponse modelResponse;

    public GoalListModelResponse getModelResponse() {
        return modelResponse;
    }

    public void setModelResponse(GoalListModelResponse modelResponse) {
        this.modelResponse = modelResponse;
    }

    public static class GoalListModelResponse {

        @SerializedName("success")
        private boolean isSuccessful;
        @SerializedName("goals")
        private List<Goal> goals;
//        @SerializedName("image_link")
//        private String ImageLink;

        public GoalListModelResponse() {
        }

        public boolean isSuccessful() {
            return isSuccessful;
        }

        public void setSuccessful(boolean successful) {
            isSuccessful = successful;
        }

        public List<Goal> getGoals() {
            return goals;
        }

        public void setGoals(List<Goal> goals) {
            this.goals = goals;
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
