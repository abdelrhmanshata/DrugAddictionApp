package com.example.drugaddictionapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drugaddictionapp.Adapter.AdapterReservation;
import com.example.drugaddictionapp.Model.Doctor;
import com.example.drugaddictionapp.Model.Reservation;
import com.example.drugaddictionapp.R;
import com.example.drugaddictionapp.databinding.FragmentCalendarBinding;
import com.example.drugaddictionapp.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CalendarFragment extends Fragment {

    FragmentCalendarBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refReservation = database.getReference("Reservation");

    List<Reservation> reservations;
    AdapterReservation adapterReservation;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCalendarBinding.inflate(inflater, container, false);

        reservations = new ArrayList<>();
        adapterReservation = new AdapterReservation(getContext(), reservations);
        binding.appointmentsList.setHasFixedSize(true);
        binding.appointmentsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.appointmentsList.setAdapter(adapterReservation);

        loadReservations();

        return binding.getRoot();
    }

    void loadReservations() {
        binding.progressCircle.setVisibility(View.VISIBLE);
        refReservation.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reservations.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reservation reservation = snapshot.getValue(Reservation.class);
                    if (reservation != null) {
                        reservations.add(reservation);
                    }
                }
                binding.progressCircle.setVisibility(View.GONE);
                adapterReservation.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressCircle.setVisibility(View.GONE);
            }
        });
    }
}