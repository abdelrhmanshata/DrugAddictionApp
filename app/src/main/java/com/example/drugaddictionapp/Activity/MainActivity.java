package com.example.drugaddictionapp.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drugaddictionapp.Fragment.CalendarFragment;
import com.example.drugaddictionapp.Fragment.HomeFragment;
import com.example.drugaddictionapp.Fragment.SettingFragment;
import com.example.drugaddictionapp.R;
import com.example.drugaddictionapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    HomeFragment homeFragment = new HomeFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    SettingFragment settingFragment = new SettingFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Home();
                    return true;
                case R.id.calendar:
                    Calendar();
                    return true;
                case R.id.setting:
                    Setting();
                    return true;
            }
            return false;
        });

        binding.bottomNavigation.setSelectedItemId(R.id.home);
    }

    void Home() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
    }

    void Calendar() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, calendarFragment).commit();
    }

    void Setting() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
    }

}