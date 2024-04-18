package com.flipkart.service.impl;

import com.flipkart.dto.Availability;
import com.flipkart.dto.Doctor;
import com.flipkart.dto.Slot;
import com.flipkart.dto.Speciality;
import com.flipkart.repo.DoctorRepo;
import com.flipkart.service.DoctorService;
import com.flipkart.util.DocAppointmentUtility;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DoctorServiceImpl implements DoctorService {

    @Override
    public String registerDoctor(String doctorName, String speciality) {
        Doctor doctor = new Doctor(doctorName, Speciality.getSpecialityByName(speciality));
        DoctorRepo.getDoctorRepo().add(doctor);

        List<Doctor> doctors = DoctorRepo.doctorSpecialtyMap().get(speciality);

        if (Objects.isNull(doctors)) {
            doctors = new ArrayList<>();
        }
        doctors.add(doctor);
        DoctorRepo.doctorSpecialtyMap().put(speciality, doctors);

        return "Welcome Dr." + doctorName + "!!";
    }

    @Override
    public String markDocAvail(String doctorName, String startTime, String endTime) {

        String reply = "Done Doc!";

        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);

        var duration = Duration.between(start, end).toMinutes();

        if (duration != 30) {
            return "Sorry Dr." + doctorName + " slots are 30 mins only";
        }

        Doctor doctor = DocAppointmentUtility.getDoctorByDoctorName(doctorName);
        doctor.getSlots().add(new Slot(start, end));

        return reply;
    }

    @Override
    public List<Availability> showAvailBySpeciality(String speciality) {

        List<Doctor> doctors = DoctorRepo.doctorSpecialtyMap().get(speciality);

        if (Objects.isNull(doctors) || doctors.isEmpty()) {
            return Collections.emptyList();
        }
        return doctors.stream().map(doctor -> {
            String availTime = "NA";

            List<String> slotTimeList = doctor.getSlots().stream()
                    .filter(slot -> !slot.isBooked())
                    .map(slot -> slot.getStartTime().toString() + "-" + slot.getEndTime().toString()).toList();


            return new Availability(doctor.getDoctorName(), slotTimeList.isEmpty() ? availTime : String.join(",", slotTimeList));
        }).collect(Collectors.toList());
    }
}
