package com.example.docapp.model;

public class Doctor {

    int doctor_id;
    String doctorName;
    String email;
    String password;
    int contactNo;
    String designation;
    String doctorgender;
    int availability;

    public Doctor(int doctor_id, String doctorName, String email, String password, int contactNo, String designation, String doctorgender, int availability) {
        this.doctor_id = doctor_id;
        this.doctorName = doctorName;
        this.email = email;
        this.password = password;
        this.contactNo = contactNo;
        this.designation = designation;
        this.doctorgender = doctorgender;
        this.availability = availability;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContactNo(int contactNo) {
        this.contactNo = contactNo;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setDoctorgender(String doctorgender) {
        this.doctorgender = doctorgender;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getContactNo() {
        return contactNo;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDoctorgender() {
        return doctorgender;
    }

    public int getAvailability() {
        return availability;
    }


}



