package com.example.android.goalsetter;

import android.app.Application;
import android.content.Intent;

import com.example.android.goalsetter.Activities.HomeActivity;

public class App extends Application {
    public App() {
        super();
        String string = getSharedPreferences("token", MODE_PRIVATE)
                .getString("token", null);
//        getSharedPreferences("token", MODE_PRIVATE)
//                .get
        if (string != null && !string.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra(BundleConstants.USER_BUNDLE, userDetail);
            startActivity(intent);
        }
    }
}
