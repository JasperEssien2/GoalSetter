package com.example.android.goalsetter.Models;

import com.example.android.goalsetter.ApiCalls;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GoalsViewModel extends ViewModel {

    private MutableLiveData<List<Goal>> mutableLiveData;

    public void setList(List<Goal> goals) {
        mutableLiveData.setValue(goals);
    }

    public LiveData<List<Goal>> getList(ApiCalls apiCalls, String token) {
        if (mutableLiveData == null)
            mutableLiveData = new MutableLiveData<>();

        if (apiCalls != null && token != null)
            apiCalls.getGoals(token);
        return mutableLiveData;

    }


}
