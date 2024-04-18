package com.flipkart;

import com.flipkart.repo.DoctorRepo;
import com.flipkart.repo.PatientRepo;
import com.flipkart.service.DoctorService;
import com.flipkart.service.PatientService;
import com.flipkart.service.impl.DoctorServiceImpl;
import com.flipkart.service.impl.PatientServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DoctorService doctorService = new DoctorServiceImpl();
        PatientService patientService = new PatientServiceImpl();

        while (true) {
            String inp = scanner.nextLine();
            inp = inp.trim();
            String[] inpArr = inp.split(" ");
            try {
                switch (inpArr[0]) {
                    case "registerDoc" -> System.out.println(doctorService.registerDoctor(inpArr[1], inpArr[2]));
                    case "markDocAvail" -> {
                        var timeArr = inpArr[2].split("-");
                        System.out.println(doctorService.markDocAvail(inpArr[1], timeArr[0], timeArr[1]));
                    }
                    case "showAvailBySpeciality" -> {
                        doctorService.showAvailBySpeciality(inpArr[1]).forEach(System.out::println);
                    }
                    case "registerPatient" -> {
                        System.out.println(patientService.registerPatient(inpArr[1]));
                    }
                    case "bookAppointment" -> {
                        boolean isWaitList = false;
                        if (inpArr.length == 5) {
                            isWaitList = Boolean.parseBoolean(inpArr[4]);
                        }
                        System.out.println(patientService.bookAppointment(inpArr[1], inpArr[2], inpArr[3], isWaitList));
                    }
                    case "showAppointmentBooked" -> System.out.println(patientService.showAppointmentBooked(inpArr[1]));
                    case "cancelBooking" -> {
                        System.out.println(patientService.cancelBooking(inpArr[1]));
                    }
                    case "showDoc" -> {

                        DoctorRepo.getDoctorRepo().forEach(doctor -> {
                            System.out.println("name:" + doctor.getDoctorName() + " speciality: " + doctor.getSpeciality().getSpecialityName());
                            if(!doctor.getWaitLists().isEmpty()) {
                                System.out.println("waitList" + doctor.getWaitLists().peek());
                            }
                            doctor.getSlots().forEach(slot -> {
                                System.out.println("slot: " + slot.getBookedBy() + " " + slot.getStartTime().toString() + "is booked ? " + slot.isBooked());
                            });
                        });

                        System.out.println("DocMap..");

                        DoctorRepo.doctorSpecialtyMap().forEach((s, doctors) -> {
                            System.out.println("speciality: " + s);
                            doctors.forEach(doctor -> System.out.println("docName: " + doctor.getDoctorName()));
                        });

                    }
                    case "showPatient" -> {
                        PatientRepo.getPatientList().forEach(patient -> {
                            System.out.println("name:" + patient.getPatientName());
                            patient.getAppointmentList().forEach(appointment -> System.out.println("appointment: " + appointment));
                            System.out.println("patientMap:" + patient.getAppointmentMap());
                        });

                        System.out.println("booking map:"+ PatientRepo.getBookingIdPatientMap());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}