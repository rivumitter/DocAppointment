package com.flipkart.service;

import com.flipkart.dto.Availability;

import java.util.List;

public interface DoctorService {

    String registerDoctor(String doctorName, String speciality);

    String markDocAvail(String doctorName, String startTime, String endTime);

    List<Availability> showAvailBySpeciality(String speciality);
}
