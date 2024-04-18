package com.flipkart.dto;

import java.time.LocalTime;
import java.util.UUID;

public class Appointment {

    private UUID bookingId;
    private LocalTime appointmentTime;
    private String doctorName;
    private boolean isWaitList;

    public Appointment(UUID bookingId, LocalTime appointmentTime, String doctorName) {
        this.bookingId = bookingId;
        this.appointmentTime = appointmentTime;
        this.doctorName = doctorName;
        this.isWaitList = false;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public boolean isWaitList() {
        return isWaitList;
    }

    public void setWaitList(boolean waitList) {
        isWaitList = waitList;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "bookingId=" + bookingId +
                ", appointmentTime=" + appointmentTime +
                ", doctorName='" + doctorName + '\'' +
                ", isWaitList=" + isWaitList +
                '}';
    }
}
