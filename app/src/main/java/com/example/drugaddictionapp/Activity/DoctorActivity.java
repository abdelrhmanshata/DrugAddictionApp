package com.example.drugaddictionapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.drugaddictionapp.Model.Doctor;
import com.example.drugaddictionapp.Model.Reservation;
import com.example.drugaddictionapp.R;
import com.example.drugaddictionapp.databinding.ActivityCommonQuestionsBinding;
import com.example.drugaddictionapp.databinding.ActivityDoctorBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DoctorActivity extends AppCompatActivity {

    ActivityDoctorBinding binding;
    Doctor doctor;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refReservation = database.getReference("Reservation");

    String reservationTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        doctor = (Doctor) getIntent().getSerializableExtra("Doctor");
        binding.doctorName.setText(doctor.getDoctorName());
        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnConfirm.setOnClickListener(v -> {

            if (reservationTime.isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.please_select_time), Toast.LENGTH_SHORT).show();
            } else {

                Reservation reservation = new Reservation();
                reservation.setId(refReservation.push().getKey());
                reservation.setDoctorName(doctor.getDoctorName());
                reservation.setDate(getDateTime());
                reservation.setTime(reservationTime);

                refReservation.child(currentUser.getUid()).child(reservation.getId()).setValue(reservation).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showDialog_ConfirmReservation();
                    }
                });
            }
        });
    }


    public void selectTime(View view) {
        reservationTime = view.getTag().toString();
        binding.timeSelect.setText(getResources().getString(R.string.select_you)+" "+ view.getTag());
    }

    String getDateTime() {
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd EEEE", Locale.getDefault()).format(date);
    }

    @SuppressLint("MissingInflatedId")
    public void showDialog_ConfirmReservation() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.confirm_reservation_layout, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogView.findViewById(R.id.close).setOnClickListener(v -> {
            alertDialog.dismiss();
            onBackPressed();
        });
    }
}