package com.example.drugaddictionapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.drugaddictionapp.Model.Doctor;
import com.example.drugaddictionapp.R;

import java.util.List;

public class AdapterDoctorFilter extends ArrayAdapter<Doctor> {
    Context context;

    public AdapterDoctorFilter(@NonNull Context context, @NonNull List<Doctor> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_doctor_item, parent, false);
        }

        Doctor doctor = getItem(position);

        ImageView doctorImage = listItemView.findViewById(R.id.doctorImage);
        TextView doctorName = listItemView.findViewById(R.id.doctorName);

        doctorImage.setImageDrawable(getContext().getResources().getDrawable(doctor.getDoctorImage()));
        doctorName.setText(doctor.getDoctorName());

        return listItemView;
    }
}
