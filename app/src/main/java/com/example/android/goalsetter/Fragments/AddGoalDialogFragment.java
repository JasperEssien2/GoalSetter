package com.example.android.goalsetter.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.android.goalsetter.ApiCalls;
import com.example.android.goalsetter.Models.Goal;
import com.example.android.goalsetter.R;
import com.example.android.goalsetter.databinding.ItemAddGoalBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

public class AddGoalDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private String token;
    private ApiCalls apiCalls;
    private boolean isEdit;
    @Nullable
    private Goal goal;
    private ItemAddGoalBinding binding;
    private String levels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.item_add_goal, container, false);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.levels_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAllErrorNull();
        // Apply the adapter to the spinner
        binding.levelSpinner.setAdapter(adapter);
        binding.levelSpinner.setOnItemSelectedListener(this);
        if (isEdit && goal != null)
            bindViewWithData(goal);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.curve_background);
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.addButton.setEnabled(false);
                binding.progress.setVisibility(View.VISIBLE);
                setAllErrorNull();
                if (validateField() == 0)
                    if (!isEdit)
                        addGoal();
                    else if (goal != null) apiCalls.editGoal(token, goal);
//                else setAllErrorNull();
            }
        });
        return binding.getRoot();
    }

    /**
     * Initializes a new goal object and then calls the api to add goal to the database
     */
    private void addGoal() {
        Goal goal = new Goal();
        goal.setTitle(binding.title.getText().toString());
        goal.setDescription(binding.decription.getText().toString());
        goal.setDueTime(binding.dueTime.getText().toString());
        goal.setStartTime(binding.startTime.getText().toString());
        goal.setLevel(levels);
        if (apiCalls != null && token != null)
            apiCalls.addGoal(token, goal);
    }

    private void bindViewWithData(Goal goal) {
        binding.title.setText(goal.getTitle());
        binding.decription.setText(goal.getDescription());
        binding.startTime.setText(goal.getStartTime());
        binding.dueTime.setText(goal.getDueTime());
        binding.levelSpinner.setPrompt(goal.getLevel());
    }


    private void setAllErrorNull() {
        binding.title.setError(null);
        binding.decription.setError(null);
        binding.startTime.setError(null);
        binding.dueTime.setError(null);
    }

    /**
     * This method checks aall edittext field to confirm if they re empty or not
     *
     * @return an int indicating the error count
     */
    private int validateField() {
        int errorCount = 0;

        if (getStringFromEdittext(binding.title).isEmpty()) {
            binding.title.setError("Empty Field");
            errorCount++;
        } else if (getStringFromEdittext(binding.decription).isEmpty()) {
            binding.title.setError("Empty Field");
            errorCount++;
        } else if (getStringFromEdittext(binding.dueTime).isEmpty()) {
            binding.title.setError("Empty Field");
            errorCount++;
        } else if (getStringFromEdittext(binding.startTime).isEmpty()) {
            binding.title.setError("Empty Field");
            errorCount++;
        }
        return errorCount;
    }

    private String getStringFromEdittext(EditText editText) {
        return editText.getText().toString();
    }

    /**
     * This method hides the progressbar and enables the add button when adding a goal fails
     */
    public void addingGoalFailed() {
        binding.progress.setVisibility(View.GONE);
        binding.addButton.setEnabled(true);
    }

    /**
     * Initializes the api call instance and the token
     *
     * @param token
     * @param apiCalls
     * @param isEdit
     */
    public void initApiCalls(String token, ApiCalls apiCalls, boolean isEdit, @Nullable Goal goal) {
        this.token = token;
        this.apiCalls = apiCalls;
        this.isEdit = isEdit;
        this.goal = goal;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        levels = getResources().getStringArray(R.array.levels_array)[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
