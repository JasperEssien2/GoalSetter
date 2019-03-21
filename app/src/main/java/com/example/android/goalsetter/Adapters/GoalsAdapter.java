package com.example.android.goalsetter.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.goalsetter.Models.Goal;
import com.example.android.goalsetter.R;
import com.example.android.goalsetter.databinding.HomeItemTaskBinding;
import com.example.android.goalsetter.databinding.ItemGoalBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.GoalsViewHolder> {

    private boolean isHome;
    private Activity activity;
    private List<Goal> goals = new ArrayList<>();
    private HomeItemTaskBinding itemHomeTaskBinding;
    private ItemGoalBinding itemGoalBinding;

    public GoalsAdapter(boolean isHome, Activity activity, List<Goal> goals) {
        super();
        this.isHome = isHome;
        this.activity = activity;
        this.goals = goals;
    }


    @NonNull
    @Override
    public GoalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (isHome) {

            itemHomeTaskBinding = DataBindingUtil.inflate(layoutInflater, R.layout.home_item_task, parent, false);
            return new GoalsViewHolder(itemHomeTaskBinding.getRoot());
        } else {
            itemGoalBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_goal, parent, false);
            return new GoalsViewHolder(itemGoalBinding.getRoot());
        }
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull GoalsViewHolder holder, int position) {
        Goal goal = goals.get(holder.getAdapterPosition());
        holder.title.setText(goal.getTitle() != null ? goal.getTitle() : "");
        holder.description.setText(goal.getDescription() != null ? goal.getDescription() : "");
        if (goal.getLevel().toLowerCase().equals("easy".toLowerCase()))
            holder.level.setBorderColor(ContextCompat.getColor(activity, android.R.color.holo_blue_bright));
        else if (goal.getLevel().toLowerCase().equals("normal".toLowerCase()))
            holder.level.setBorderColor(ContextCompat.getColor(activity, android.R.color.holo_green_dark));
        else
            holder.level.setBorderColor(ContextCompat.getColor(activity, android.R.color.holo_red_light));
        if (!isHome) {
            holder.startTime.append(goal.getStartTime() != null ? goal.getStartTime() : "");
            holder.dueDate.append(goal.getDueTime() != null ? goal.getDueTime() : "");
        }
    }

    @Override
    public int getItemCount() {
        return goals == null ? 0 : goals.size();
    }

    class GoalsViewHolder extends RecyclerView.ViewHolder {
        private TextView title, description, startTime, dueDate;
        private CircleImageView level;

        public GoalsViewHolder(@NonNull View itemView) {
            super(itemView);
            if (isHome) {
                title = itemHomeTaskBinding.taskTitle;
                description = itemHomeTaskBinding.taskDescription;
//                startTime = itemHomeTaskBinding.startTime;
//                dueDate = itemHomeTaskBinding.dueTime;
            } else {
                title = itemGoalBinding.taskTitle;
                description = itemGoalBinding.taskDescription;
                startTime = itemGoalBinding.startTime;
                dueDate = itemGoalBinding.dueTime;
                level = itemGoalBinding.level;
            }
        }
    }
}
