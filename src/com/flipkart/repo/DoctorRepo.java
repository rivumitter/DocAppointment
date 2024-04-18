package com.flipkart.repo;

import com.flipkart.dto.Doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorRepo {

    private final static List<Doctor> DOCTOR_LIST = new ArrayList<>();
    private static final Map<String, List<Doctor>> doctorSpecialtyMap = new HashMap<>();

    public static List<Doctor> getDoctorRepo() {
        return DOCTOR_LIST;
    }

    public static Map<String, List<Doctor>> doctorSpecialtyMap() {
        return doctorSpecialtyMap;
    }

}
