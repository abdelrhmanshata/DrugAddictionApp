package com.example.drugaddictionapp.Model;

import java.io.Serializable;

public class Doctor implements Serializable {

    String doctorID, doctorName;
    int doctorImage;

    public Doctor() {
    }

    public String getDoctorID() {
        return doctorID;
    }

    public Doctor(String doctorID, String doctorName, int doctorImage) {
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.doctorImage = doctorImage;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(int doctorImage) {
        this.doctorImage = doctorImage;
    }
}
