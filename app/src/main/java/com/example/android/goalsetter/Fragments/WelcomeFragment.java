package com.example.android.goalsetter.Fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.android.goalsetter.Interface.AuthenticationViewPagerCallbacks;
import com.example.android.goalsetter.R;
import com.example.android.goalsetter.databinding.FragmentWelcomeBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding binding;
    private AuthenticationViewPagerCallbacks callbacks;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false);
        Glide
                .with(this)
                .load(R.drawable.goal_background)
                .into(binding.welcomeBackground);
       binding.goToLoginPage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(callbacks != null)
               callbacks.loginButtonClicked(false, null, null);
           }
       });

        binding.goToSignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callbacks != null)
                callbacks.signUpButtonClicked(false, null, null);
            }
        });
       return binding.getRoot();
    }

    public void initAuthenticationCallbacks(AuthenticationViewPagerCallbacks callbacks) {

        this.callbacks = callbacks;
    }
}
