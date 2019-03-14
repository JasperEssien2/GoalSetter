package com.example.android.goalsetter.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.android.goalsetter.databinding.FragmentLoginBinding;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements ApiCallsCallback {


    private AuthenticationViewPagerCallbacks callbacks;
    private FragmentLoginBinding binding;
    private ApiCalls apiCalls = new ApiCalls(this);

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        Glide
                .with(this)
                .load(R.drawable.goal_background)
                .into(binding.loginBackground);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAuthFieldErrorNull();
                if (validateFields() == 0) {
                    loginButtonClicked();
                }
            }
        });

        binding.keepMeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (callbacks != null)
                    callbacks.signUpButtonClicked(false, null, null);
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
        binding.loginEmailInputLayout.setError(null);
        binding.loginPasswordInputLayout.setError(null);
//        binding.setError(null);
    }

    /**
     * This method validates the fields
     *
     * @return the number of errors
     */
    private int validateFields() {
        int errorCount = 0;

        if (!Validation.validateEmail(getTextFromEditText(binding.emailEdditext))) {
            binding.loginEmailInputLayout.setError(Validation.EMAIL_NOT_VALID);
            errorCount++;
        }


        if (getTextFromEditText(binding.passwordEdittext).length() < 6) {
            binding.loginPasswordInputLayout.setError(Validation.PASSWORD_LESS);
            errorCount++;
        }


        return errorCount;
    }


    private void loginButtonClicked() {
        if (validateFields() == 0) {
            enableProgressbar();
            apiCalls.login(getTextFromEditText(binding.emailEdditext),
                    getTextFromEditText(binding.passwordEdittext));
        }
    }

    /**
     * This method shows progressbar and hides the button text
     */
    private void enableProgressbar() {
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.login.setVisibility(View.GONE);
    }

    /**
     * This method hides progressbar and shows the button text
     */
    private void disableProgressbar() {
        binding.progressbar.setVisibility(View.GONE);
        binding.login.setVisibility(View.VISIBLE);
    }


    @Override
    public void register(User userDetail, String token) {

    }

    @Override
    public void login(RegisterResponseDataModel token) {
        disableProgressbar();
        if (token != null && token.getRegisterResponseModel() != null) {
            getActivity().getSharedPreferences("token", Context.MODE_PRIVATE)
                    .edit()
                    .putString("token", token.getRegisterResponseModel().getToken())
                    .apply();

            Intent intent = new Intent(getActivity(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(BundleConstants.USER_BUNDLE, token.getRegisterResponseModel().getUser());
            startActivity(intent);
        }
    }

    @Override
    public void dashBoard(User user) {

    }
}
