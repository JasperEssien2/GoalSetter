package com.example.android.goalsetter.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.goalsetter.Activities.HomeActivity;
import com.example.android.goalsetter.Constant.BundleConstants;
import com.example.android.goalsetter.Interface.ApiCalls;
import com.example.android.goalsetter.Interface.ApiCallsCallback;
import com.example.android.goalsetter.Interface.AuthenticationViewPagerCallbacks;
import com.example.android.goalsetter.Models.RegisterResponseDataModel;
import com.example.android.goalsetter.Models.User;
import com.example.android.goalsetter.R;
import com.example.android.goalsetter.Validation;
import com.example.android.goalsetter.databinding.FragmentSignUpBinding;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements ApiCallsCallback {

    private FragmentSignUpBinding binding;
    private AuthenticationViewPagerCallbacks callbacks;
    private ApiCalls apiCalls = new ApiCalls(this);

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        Glide
                .with(this)
                .load(R.drawable.goal_background)
                .into(binding.signupBackground);
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(callbacks != null)
//                    callbacks.signUpButtonClicked(true, new User(), "");
                setAuthFieldErrorNull();
                signUpButtonClicked();
            }
        });
        return binding.getRoot();
    }

    public void initAuthenticationCallbacks(AuthenticationViewPagerCallbacks callbacks) {

        this.callbacks = callbacks;
    }

    /**
     * Gets the text from the edittext
     *
     * @param editText required
     * @return String of text
     */
    private String getTextFromEditText(AppCompatEditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * Sets fields error null
     */
    private void setAuthFieldErrorNull() {
        binding.signUpEmailInputLayout.setError(null);
        binding.signUpContactInputLayout.setError(null);
        binding.signUpUsernameInputLayout.setError(null);
        binding.signUpPasswordInputLayout.setError(null);
        binding.signUpConfirmPasswordInputLayout.setError(null);
//        binding.setError(null);
    }

    /**
     * This method validates the fields
     *
     * @return the number of errors
     */
    private int validateFields() {
        int errorCount = 0;
        if (!Validation.validateFields(getTextFromEditText(binding.usernameEdditext))) {
            binding.signUpUsernameInputLayout.setError(Validation.EMPTY_FIELD);
            errorCount++;
        }

        if (!Validation.validateFields(getTextFromEditText(binding.contactEdditext))) {
            binding.signUpContactInputLayout.setError(Validation.EMPTY_FIELD);
            errorCount++;
        }

        if (!Validation.validateEmail(getTextFromEditText(binding.emailEdditext))) {
            binding.signUpEmailInputLayout.setError(Validation.EMAIL_NOT_VALID);
            errorCount++;
        }


        if (getTextFromEditText(binding.passwordEdittext).length() < 6) {
            binding.signUpPasswordInputLayout.setError(Validation.PASSWORD_LESS);
            errorCount++;
        }

        if (!getTextFromEditText(binding.passwordConfirmEdittext).equals(getTextFromEditText(binding.passwordEdittext))) {
            binding.signUpConfirmPasswordInputLayout.setError(Validation.PASSWORD_NOT_EQUAL);
            errorCount++;
        }

        return errorCount;
    }


    private void signUpButtonClicked() {
        if (validateFields() == 0) {
            enableProgressbar();
            apiCalls.register(getUser());
        }
    }

    /**
     * This method shows progressbar and hides the button text
     */
    private void enableProgressbar() {
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.signUp.setVisibility(View.GONE);
    }

    /**
     * This method hides progressbar and shows the button text
     */
    private void disableProgressbar() {
        binding.progressbar.setVisibility(View.GONE);
        binding.signUp.setVisibility(View.VISIBLE);
    }

    /**
     * Initializes a user object
     *
     * @return user
     */
    private User getUser() {
        User users = new User();
        users.setName(getTextFromEditText(binding.usernameEdditext));
        users.setContact(getTextFromEditText(binding.contactEdditext));
        users.setEmailAddress(getTextFromEditText(binding.emailEdditext));
        users.setPassword(getTextFromEditText(binding.passwordEdittext));
        users.setConfirmPassword(getTextFromEditText(binding.passwordConfirmEdittext));

        return users;
    }

    @Override
    public void register(User userDetail, String token) {
        disableProgressbar();
        if (userDetail != null) {

            getActivity().getSharedPreferences("token", Context.MODE_PRIVATE)
                    .edit()
                    .putString("token", token)
                    .apply();

            Intent intent = new Intent(getActivity(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(BundleConstants.USER_BUNDLE, userDetail);
            startActivity(intent);
        } else
            Toast.makeText(getActivity(), "Sign up not succesful, try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login(RegisterResponseDataModel token) {
        if (token != null)
            apiCalls.dashBoard(token.getRegisterResponseModel().getToken());
    }

    @Override
    public void dashBoard(User user) {

    }
}
