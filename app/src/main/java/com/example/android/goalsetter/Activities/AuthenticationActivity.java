package com.example.android.goalsetter.Activities;

import android.os.Bundle;

import com.example.android.goalsetter.Adapters.AuthenticationViewPagerAdapter;
import com.example.android.goalsetter.Interface.AuthenticationViewPagerCallbacks;
import com.example.android.goalsetter.Models.User;
import com.example.android.goalsetter.R;
import com.example.android.goalsetter.databinding.ActivityAuthenticationBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class AuthenticationActivity extends AppCompatActivity implements AuthenticationViewPagerCallbacks {
    private ActivityAuthenticationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);
        binding
                .authenticationViewPager
                .setAdapter(new AuthenticationViewPagerAdapter(getSupportFragmentManager(), this));
    }

    @Override
    public void signUpButtonClicked(boolean signUp, @Nullable User user, @Nullable String password) {
        if (!signUp)
            binding
                    .authenticationViewPager.setCurrentItem(2, true);
    }


    @Override
    public void loginButtonClicked(boolean login, @Nullable String email, @Nullable String password) {
        if (!login)
            binding
                    .authenticationViewPager.setCurrentItem(1, true);
    }
}
