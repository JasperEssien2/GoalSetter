package com.example.android.goalsetter.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.goalsetter.Adapters.GoalsAdapter;
import com.example.android.goalsetter.ApiCalls;
import com.example.android.goalsetter.Constant.BundleConstants;
import com.example.android.goalsetter.Fragments.AddGoalDialogFragment;
import com.example.android.goalsetter.Interface.GoalsApiCallback;
import com.example.android.goalsetter.Models.Goal;
import com.example.android.goalsetter.Models.GoalListModelData;
import com.example.android.goalsetter.Models.GoalModelData;
import com.example.android.goalsetter.Models.GoalsViewModel;
import com.example.android.goalsetter.R;
import com.example.android.goalsetter.databinding.ActivityGoalBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GoalActivity extends AppCompatActivity implements GoalsApiCallback {

    private ActivityGoalBinding binding;
    private ApiCalls apiCalls = new ApiCalls(this);
    private String token;
    private AddGoalDialogFragment dialogFragment = new AddGoalDialogFragment();
    private GoalsViewModel model;
    private GoalsAdapter adapter = new GoalsAdapter(false, this, new ArrayList<Goal>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_goal);
        model = new ViewModelProvider.NewInstanceFactory().create(GoalsViewModel.class);
        setUpRecyclerView();
        if (getIntent() != null && getIntent().hasExtra(BundleConstants.TOKEN_BUNDLE)) {
            token = getIntent().getStringExtra(BundleConstants.TOKEN_BUNDLE);
        }
        adapter.setEditDialogFragment(dialogFragment, token, apiCalls);
        model.getList(apiCalls, token)
                .observe(this, new Observer<List<Goal>>() {
                    @Override
                    public void onChanged(List<Goal> goals) {
                        if (goals != null && !goals.isEmpty()) {
                            adapter.setGoals(goals);
                            hideEmptyView();
                        } else {
                            showEmptyView();
                        }
                    }
                });
        binding.addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.initApiCalls(token, apiCalls, false, null);
                dialogFragment.show(getSupportFragmentManager(), null);
            }
        });
    }

    /**
     * If the list is empty this method shows the empty view and hides the revyvlerview
     */
    private void showEmptyView() {
        binding.taskRecylerView.setVisibility(View.GONE);
        binding.emptyList.setVisibility(View.VISIBLE);
    }

    /**
     * If this list is not empty this method hides the empty view and shows the recyclerview
     */
    private void hideEmptyView() {
        binding.taskRecylerView.setVisibility(View.VISIBLE);
        binding.emptyList.setVisibility(View.GONE);
    }

    /**
     * This method sets up the home page recent goal recylerview
     */
    private void setUpRecyclerView() {
        binding.taskRecylerView.setAdapter(adapter);
        binding.taskRecylerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        adapter.setGoals(getDummyGoalList());
    }

    @Override
    public void goalAdded(GoalModelData.GoalModelResponse modelResponse) {
        if (modelResponse != null) {
            dialogFragment.dismiss();
            Toast.makeText(this, "Goal Added", Toast.LENGTH_SHORT).show();
            apiCalls.getGoals(token);
        } else dialogFragment.addingGoalFailed();

    }

    @Override
    public void goalListGotten(GoalListModelData.GoalListModelResponse goalListModelResponse) {
        if (goalListModelResponse != null)
            if (goalListModelResponse.getGoals() != null)
                model.setList(goalListModelResponse.getGoals());
    }

    @Override
    public void goalListDeleted(boolean isSuccessful) {
        if (isSuccessful) {
            apiCalls.getGoals(token);
            Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
//            dialogFragment.dismiss();
        } else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
//            dialogFragment.addingGoalFailed();
        }
    }

    @Override
    public void goalListEdited(boolean isSuccessful) {
        if (isSuccessful) {
            apiCalls.getGoals(token);
            dialogFragment.dismiss();
        } else {
            Toast.makeText(this, "Edit Failed", Toast.LENGTH_SHORT).show();
            dialogFragment.addingGoalFailed();
        }
    }
}
