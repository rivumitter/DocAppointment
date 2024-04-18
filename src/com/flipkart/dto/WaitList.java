package com.flipkart.dto;

import java.util.UUID;


public class WaitList {

    private UUID bookingId;
    private String patientName;

    public WaitList(UUID bookingId, String patientName) {
        this.bookingId = bookingId;
        this.patientName = patientName;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Override
    public String toString() {
        return "WaitList{" +
                "bookingId=" + bookingId +
                ", patientName='" + patientName + '\'' +
                '}';
    }
}
