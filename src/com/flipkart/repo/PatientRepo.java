package com.flipkart.repo;

import com.flipkart.dto.Patient;

import java.util.*;

public class PatientRepo {

    private static final List<Patient> PATIENT_LIST = new ArrayList<>();
    private static final Map<UUID, Patient> BOOKING_ID_PATIENT_MAP = new HashMap<>();

    public static List<Patient> getPatientList() {
        return PATIENT_LIST;
    }

    public static Map<UUID, Patient> getBookingIdPatientMap() {
        return BOOKING_ID_PATIENT_MAP;
    }
}
