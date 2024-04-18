package com.flipkart.util;

import com.flipkart.dto.Doctor;
import com.flipkart.dto.Patient;
import com.flipkart.repo.PatientRepo;

import java.util.UUID;

import static com.flipkart.repo.DoctorRepo.getDoctorRepo;
import static com.flipkart.repo.PatientRepo.getPatientList;

public class DocAppointmentUtility {

    public static Doctor getDoctorByDoctorName(String docName) {

        return getDoctorRepo()
                .stream()
                .filter(doctor -> doctor.getDoctorName().equalsIgnoreCase(docName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No doc found with given name:"+docName));

    }

    public static Patient getPatientByPatientName(String patientName) {

        return getPatientList()
                .stream()
                .filter(patient -> patient.getPatientName().equalsIgnoreCase(patientName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Patient found with given name:"+patientName));

    }

    public static Patient getPatientByBookingId(UUID bookingId) {
        return PatientRepo.getBookingIdPatientMap().get(bookingId);
    }

    public static void mapPatientViaBookingId(UUID bookingId, Patient patient) {
        PatientRepo.getBookingIdPatientMap().putIfAbsent(bookingId, patient);
    }
}
