package com.example.drugaddictionapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import com.example.drugaddictionapp.databinding.ActivityIntroBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntroActivity extends AppCompatActivity {

    private final static int SPLASH_DISPLAY_LENGTH = 1000; //change time
    ActivityIntroBinding binding;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        new Handler().postDelayed(() -> {
            if (firebaseUser != null) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
            ActivityCompat.finishAffinity(this);
        }, SPLASH_DISPLAY_LENGTH);

    }

    boolean isDarkMode() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isDarkModeOn", false);
    }

}