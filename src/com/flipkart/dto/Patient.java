package com.flipkart.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient {

    private String patientName;
    private List<Appointment> appointmentList;
    Map<LocalTime, String> appointmentMap;

    public Patient(String patientName) {
        this.patientName = patientName;
        this.appointmentList = new ArrayList<>();
        this.appointmentMap = new HashMap<>();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public Map<LocalTime, String> getAppointmentMap() {
        return appointmentMap;
    }

    public void setAppointmentMap(Map<LocalTime, String> appointmentMap) {
        this.appointmentMap = appointmentMap;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientName='" + patientName + '\'' +
                ", appointmentList=" + appointmentList +
                ", appointmentMap=" + appointmentMap +
                '}';
    }
}
