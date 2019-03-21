package com.example.android.goalsetter.Interface;

import com.example.android.goalsetter.Models.GoalListModelData;
import com.example.android.goalsetter.Models.GoalModelData;

public interface GoalsApiCallback {


    void goalAdded(GoalModelData.GoalModelResponse modelResponse);

    void goalListGotten(GoalListModelData.GoalListModelResponse goalListModelResponse);

    void goalListDeleted(boolean isSuccessful);

    void goalListEdited(boolean isSuccessful);
}
