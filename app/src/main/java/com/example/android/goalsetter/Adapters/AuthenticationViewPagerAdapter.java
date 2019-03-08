package com.example.android.goalsetter.Adapters;

import com.example.android.goalsetter.Fragments.LoginFragment;
import com.example.android.goalsetter.Fragments.SignUpFragment;
import com.example.android.goalsetter.Fragments.WelcomeFragment;
import com.example.android.goalsetter.Interface.AuthenticationViewPagerCallbacks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AuthenticationViewPagerAdapter extends FragmentPagerAdapter {

    private final int NUM = 3;
    private final AuthenticationViewPagerCallbacks callbacks;

    public AuthenticationViewPagerAdapter(@NonNull FragmentManager fm, AuthenticationViewPagerCallbacks callbacks) {
        super(fm);
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                WelcomeFragment welcomeFragment = new WelcomeFragment();
                welcomeFragment.initAuthenticationCallbacks(callbacks);
                return welcomeFragment;
            case 1:
                LoginFragment loginFragment = new LoginFragment();
                loginFragment.initAuthenticationCallbacks(callbacks);
                return loginFragment;
            case 2:
                SignUpFragment signupFragment = new SignUpFragment();
                signupFragment.initAuthenticationCallbacks(callbacks);
                return signupFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM;
    }
}
