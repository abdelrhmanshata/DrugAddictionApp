package com.example.drugaddictionapp.Model;

public class Reservation {

    String id,date,doctorName,time;

    public Reservation() {
    }

    public Reservation(String id, String date, String doctorName, String time) {
        this.id = id;
        this.date = date;
        this.doctorName = doctorName;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
