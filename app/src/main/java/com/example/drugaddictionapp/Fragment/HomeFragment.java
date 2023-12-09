package com.example.drugaddictionapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.drugaddictionapp.Activity.DoctorActivity;
import com.example.drugaddictionapp.Adapter.AdapterDoctorFilter;
import com.example.drugaddictionapp.Model.Doctor;
import com.example.drugaddictionapp.R;
import com.example.drugaddictionapp.databinding.FragmentHomeBinding;
import com.example.drugaddictionapp.databinding.FragmentSettingBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    List<Doctor> doctors = new ArrayList<>();

    AdapterDoctorFilter doctorAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refDoctors = database.getReference("Doctors");

    public HomeFragment() {
        // Required empty public constructor
    }


    TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            loadingData(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        loadingData("");

        doctorAdapter = new AdapterDoctorFilter(getContext(), doctors);
        binding.doctorsGridView.setAdapter(doctorAdapter);

        binding.doctorsGridView.setOnItemClickListener((parent, view, position, id) -> {
            Doctor doctor = doctors.get(position);
            startActivity(new Intent(getContext(), DoctorActivity.class).putExtra("Doctor", doctor));
        });

        binding.searchDoctor.addTextChangedListener(searchTextWatcher);

        return binding.getRoot();
    }

    void loadingData(String name) {
        binding.progressCircle.setVisibility(View.VISIBLE);
        refDoctors.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctors.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doctor doctor = snapshot.getValue(Doctor.class);
                    if (doctor != null) {
                        if (name.isEmpty()) {
                            doctors.add(doctor);
                        } else {
                            if (doctor.getDoctorName().toLowerCase().contains(name.toLowerCase())) {
                                doctors.add(doctor);
                            }
                        }
                    }
                }
                binding.progressCircle.setVisibility(View.GONE);
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressCircle.setVisibility(View.GONE);
            }
        });
    }

    void initList() {
        refDoctors.child("1").setValue(new Doctor("1", "Ahmed", R.drawable.m_doctor));
        refDoctors.child("2").setValue(new Doctor("2", "Mohammed", R.drawable.m_doctor));
        refDoctors.child("3").setValue(new Doctor("3", "Fatima", R.drawable.fe_doctor));
        refDoctors.child("4").setValue(new Doctor("4", "Karim", R.drawable.m_doctor));
        refDoctors.child("5").setValue(new Doctor("5", "Amina", R.drawable.fe_doctor));
        refDoctors.child("6").setValue(new Doctor("6", "Nasser", R.drawable.m_doctor));
        refDoctors.child("7").setValue(new Doctor("7", "Layla", R.drawable.fe_doctor));
        refDoctors.child("8").setValue(new Doctor("8", "Sara", R.drawable.fe_doctor));
        refDoctors.child("9").setValue(new Doctor("9", "Rana", R.drawable.fe_doctor));
        refDoctors.child("10").setValue(new Doctor("10", "Youssef", R.drawable.m_doctor));
        refDoctors.child("11").setValue(new Doctor("11", "Hana", R.drawable.fe_doctor));
        refDoctors.child("12").setValue(new Doctor("12", "Abdullah", R.drawable.m_doctor));
    }
}