package com.flipkart.service;

import com.flipkart.dto.Appointment;

import java.util.List;

public interface PatientService {

    String registerPatient(String patientName);

    String bookAppointment(String patientName, String doctorName, String bookingTime, boolean isWaitList);

    List<Appointment> showAppointmentBooked(String patientName);

    String cancelBooking(String bookingId);
}
