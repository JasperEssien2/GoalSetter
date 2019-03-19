package com.example.android.goalsetter.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.goalsetter.ApiCalls;
import com.example.android.goalsetter.Interface.ApiCallsCallback;
import com.example.android.goalsetter.Models.ProfileModelData;
import com.example.android.goalsetter.Models.RegisterResponseDataModel;
import com.example.android.goalsetter.Models.User;
import com.example.android.goalsetter.R;
import com.example.android.goalsetter.databinding.ActivityHomeBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import static com.example.android.goalsetter.Constant.BundleConstants.TOKEN_BUNDLE;

public class HomeActivity extends AppCompatActivity implements ApiCallsCallback {

    private static final int SELECT_PHOTO = 44;
    private ApiCalls apiCalls = new ApiCalls(this);

    private ActivityHomeBinding binding;
    private String token;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar()
                .setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        disableAllEditText();
        if (getIntent() != null) {
            if (getIntent().hasExtra(TOKEN_BUNDLE)) {
                apiCalls.profile(getIntent().getStringExtra(TOKEN_BUNDLE));
                token = getIntent().getStringExtra(TOKEN_BUNDLE);
            }
        }
        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = binding.editButton.getText().toString();

                if (buttonText.toLowerCase().equals("Edit Profile".toLowerCase())) {
                    binding.editButton.setText("Save Changes");
                    enableAllEditText();
                } else {
                    binding.editButton.setText("Edit Profile");
                    disableAllEditText();
                    updateProfile();
                }
            }
        });
        binding.schoolOwnerSettingsImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = binding.editButton.getText().toString();

//                if (buttonText.toLowerCase().equals("Edit Profile".toLowerCase())) {
//                    binding.editButton.setText("Save Changes");
                imagePicker();
//                } else {
//                    binding.editButton.setText("Edit Profile");
//                    Toast.makeText(HomeActivity.this, "Click the edit button", Toast.LENGTH_SHORT).show();
//                }

            }
        });
        binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    if (imageUri != null) {
                        Glide
                                .with(this)
                                .load(imageUri)
                                .into(binding.userProfileImage);
                        apiCalls.uploadImage(this, imageUri, token);
                    } else
                        Toast.makeText(this, "Error getting image", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                getSharedPreferences("token", MODE_PRIVATE)
                        .edit()
                        .remove("token")
                        .apply();
                Intent intent = new Intent(HomeActivity.this, AuthenticationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void imagePicker() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }


    private void updateProfile() {
        if (user != null && token != null) {
            user.setName(binding.userName.getText().toString());
            user.setContact(binding.userContact.getText().toString());
            apiCalls.updateProfile(token, user);
        }
    }

    @Override
    public void register(User userDetail, String token) {

    }

    @Override
    public void login(RegisterResponseDataModel token) {

    }

    @Override
    public void profile(ProfileModelData.ProfileModelResponse profileModelResponse) {
        setUpViewWithData(profileModelResponse);
    }

    @Override
    public void updateProfile(ProfileModelData.ProfileModelResponse profileModelResponse) {
        if (profileModelResponse.isSuccessful()) {
            setUpViewWithData(profileModelResponse);
            Toast.makeText(this, "Profile Updated Succesfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void imageUploaded() {
        apiCalls.profile(token);
    }

    /**
     * This method uses the data goten from the api call to set u[ the views
     *
     * @param profileModelResponse response model of the api call
     */
    private void setUpViewWithData(ProfileModelData.ProfileModelResponse profileModelResponse) {
        if (profileModelResponse != null) {
            User user = profileModelResponse.getUser();
            String imageLink = profileModelResponse.getImageLink();

            if (user != null) {
                this.user = user;
                binding.userName.setText(user.getName() != null ? user.getName() : "");
                binding.userEmail.setText(user.getEmailAddress() != null ? user.getEmailAddress() : "");
                binding.userContact.setText(user.getContact() != null ? user.getContact() : "");
//                binding.userName.setText(user.getName() != null ? user.getName() : "");
                if (user.getUserImage() != null && imageLink != null) {
                    String imageUrl = imageLink + user.getUserImage();
                    if (!imageUrl.isEmpty())
                        Glide
                                .with(this)
                                .load(imageUrl)
                                .into(binding.userProfileImage);
                }
            }
        }
    }

    /**
     * To disable edit text fields.. this is called on the start of the app and when the user updates
     * the required field
     */
    private void disableAllEditText() {
        binding.userContact.setEnabled(false);
        binding.userEmail.setEnabled(false);
        binding.userName.setEnabled(false);
    }

    /**
     * To enable all edit text fields.. this is called when the edit profile button is clicked
     */
    private void enableAllEditText() {
        binding.userContact.setEnabled(true);
        binding.userEmail.setEnabled(true);
        binding.userName.setEnabled(true);
    }
}
