package com.example.android.goalsetter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.goalsetter.Adapters.AuthenticationViewPagerAdapter;
import com.example.android.goalsetter.Constant.BundleConstants;
import com.example.android.goalsetter.Interface.ApiCalls;
import com.example.android.goalsetter.Interface.ApiCallsCallback;
import com.example.android.goalsetter.Interface.AuthenticationViewPagerCallbacks;
import com.example.android.goalsetter.Models.RegisterResponseDataModel;
import com.example.android.goalsetter.Models.User;
import com.example.android.goalsetter.R;
import com.example.android.goalsetter.databinding.ActivityAuthenticationBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class AuthenticationActivity extends AppCompatActivity implements AuthenticationViewPagerCallbacks,
        ApiCallsCallback {
    ApiCalls apiCalls = new ApiCalls(this);
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
        else apiCalls.register(user);
    }


    @Override
    public void loginButtonClicked(boolean login, @Nullable String email, @Nullable String password) {
        if (!login)
            binding
                    .authenticationViewPager.setCurrentItem(1, true);
        else apiCalls.login(email, password);
    }

    @Override
    public void register(User userDetail, String token) {
        if (userDetail != null) {

        } else Toast.makeText(this, "Sign up not succesful, try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login(RegisterResponseDataModel token) {
        if (token != null)
            apiCalls.dashBoard(token.getRegisterResponseModel().getToken());
    }

    @Override
    public void dashBoard(User user) {
        Intent intent = new Intent(AuthenticationActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BundleConstants.USER_BUNDLE, user);
        startActivity(intent);
    }
}
