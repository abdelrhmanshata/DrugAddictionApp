package com.example.drugaddictionapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drugaddictionapp.Model.Reservation;
import com.example.drugaddictionapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterReservation extends RecyclerView.Adapter<AdapterReservation.ImageViewHolder> {

    Context mContext;
    private List<Reservation> mReservations;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refReservation = database.getReference("Reservation");

    public AdapterReservation(Context context, List<Reservation> reservations) {
        this.mContext = context;
        this.mReservations = reservations;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_appointments_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        Reservation reservation = mReservations.get(position);

        holder.dateReservation.setText(reservation.getDate());
        holder.doctorReservation.setText(reservation.getDoctorName());
        holder.timeReservation.setText(reservation.getTime());

        holder.cancelAppointments.setOnClickListener(v -> {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.cancel_appointments), Toast.LENGTH_SHORT).show();
            refReservation.child(user.getUid()).child(reservation.getId()).removeValue();
        });
    }

    @Override
    public int getItemCount() {
        return mReservations.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView dateReservation, doctorReservation, timeReservation, cancelAppointments;

        public ImageViewHolder(View itemView) {
            super(itemView);

            dateReservation = itemView.findViewById(R.id.dateReservation);
            doctorReservation = itemView.findViewById(R.id.doctorReservation);
            timeReservation = itemView.findViewById(R.id.timeReservation);
            cancelAppointments = itemView.findViewById(R.id.cancelAppointments);
        }
    }
}
