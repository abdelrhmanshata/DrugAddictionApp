package com.example.drugaddictionapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.drugaddictionapp.Activity.CommonQuestionsActivity;
import com.example.drugaddictionapp.Activity.LoginActivity;
import com.example.drugaddictionapp.Model.User;
import com.example.drugaddictionapp.R;
import com.example.drugaddictionapp.databinding.FragmentSettingBinding;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingFragment extends Fragment {

    FragmentSettingBinding binding;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refUsers = database.getReference("Users");

    User cUser = new User();

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false);

        loadPersonalInfo();

        binding.logout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(getContext(), LoginActivity.class));
            ActivityCompat.finishAffinity(requireActivity());
        });

        binding.personalInfo.setOnClickListener(v -> {
            showDialog_UpdateInfo();
        });
        binding.location.setOnClickListener(v -> {
            showDialog_UpdateLocation();
        });
        binding.questions.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), CommonQuestionsActivity.class));
        });

        binding.nightMode.setChecked(isDarkMode());
        binding.nightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setDarkMode(isChecked);
            Toast.makeText(getContext(), getResources().getString(R.string.restarted_app), Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    void loadPersonalInfo() {
        refUsers.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    cUser = user;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("MissingInflatedId")
    public void showDialog_UpdateInfo() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_personal_info_layout, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        ProgressBar progressBar = dialogView.findViewById(R.id.progressCircle);

        dialogView.findViewById(R.id.close).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        EditText userName = dialogView.findViewById(R.id.inputUserName);
        EditText userEmail = dialogView.findViewById(R.id.inputEmail);
        EditText userPassword = dialogView.findViewById(R.id.inputPassword);
        EditText userPhone = dialogView.findViewById(R.id.inputPhone);
        EditText userAge = dialogView.findViewById(R.id.inputAge);

        userName.setText(cUser.getUserName());
        userEmail.setText(cUser.getuEmail());
        userPassword.setText(cUser.getuPassword());
        userPhone.setText(cUser.getuPhone());
        userAge.setText(cUser.getuAge());

        Button saveBtn = dialogView.findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);

            String inputUserName = userName.getText().toString().trim();
            if (inputUserName.isEmpty()) {
                userName.setError(getString(R.string.nameIsRequired));
                userName.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }

            String inputPassword = userPassword.getText().toString().trim();
            if (inputPassword.isEmpty()) {
                userPassword.setError(getString(R.string.passwordIsRequired));
                userPassword.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }
            if (inputPassword.length() < 8) {
                userPassword.setError(getString(R.string.minimumLength));
                userPassword.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }

            String inputPhone = userPhone.getText().toString().trim();
            if (TextUtils.isEmpty(inputPhone)) {
                userPhone.setError(getString(R.string.phone_number_is_required));
                userPhone.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }

            String inputAge = userAge.getText().toString().trim();
            if (inputAge.isEmpty()) {
                userAge.setError(getString(R.string.ageIsRequired));
                userAge.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }

            AuthCredential credential = EmailAuthProvider.getCredential(cUser.getuEmail(), cUser.getuPassword());
            currentUser.reauthenticate(credential).addOnCompleteListener(task -> {
                currentUser.updatePassword(inputPassword).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        refUsers.child(currentUser.getUid()).child("userName").setValue(inputUserName);
                        refUsers.child(currentUser.getUid()).child("uPassword").setValue(inputPassword);
                        refUsers.child(currentUser.getUid()).child("uPhone").setValue(inputPhone);
                        refUsers.child(currentUser.getUid()).child("uAge").setValue(inputAge);
                        progressBar.setVisibility(View.GONE);
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), getString(R.string.successUpdated), Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            throw task1.getException();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });
        });
    }

    @SuppressLint("MissingInflatedId")
    public void showDialog_UpdateLocation() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_location_layout, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        ProgressBar progressBar = dialogView.findViewById(R.id.progressCircle);

        dialogView.findViewById(R.id.close).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        EditText userLocation = dialogView.findViewById(R.id.inputLocation);
        userLocation.setText(cUser.getuLocation());

        Button saveBtn = dialogView.findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);

            String inputLocation = userLocation.getText().toString().trim();
            if (inputLocation.isEmpty()) {
                userLocation.setError(getString(R.string.locationIsRequired));
                userLocation.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                return;
            }

            refUsers.child(currentUser.getUid()).child("uLocation").setValue(inputLocation).addOnSuccessListener(task -> {
                progressBar.setVisibility(View.GONE);
                alertDialog.dismiss();
                Toast.makeText(getContext(), getString(R.string.successUpdated), Toast.LENGTH_SHORT).show();
            });
        });
    }

    void setDarkMode(boolean darkMode) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDarkModeOn", darkMode);
        editor.apply();
    }

    boolean isDarkMode() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isDarkModeOn", false);
    }
}