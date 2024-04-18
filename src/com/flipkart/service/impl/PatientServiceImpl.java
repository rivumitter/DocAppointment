package com.flipkart.service.impl;

import com.flipkart.dto.*;
import com.flipkart.repo.PatientRepo;
import com.flipkart.service.PatientService;
import com.flipkart.util.DocAppointmentUtility;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class PatientServiceImpl implements PatientService {

    @Override
    public String registerPatient(String patientName) {

        PatientRepo.getPatientList().add(new Patient(patientName));

        return patientName + "registered successfully";
    }

    @Override
    public String bookAppointment(String patientName, String doctorName, String bookingTime, boolean isWaitList) {

        Patient patient = DocAppointmentUtility.getPatientByPatientName(patientName);

        LocalTime appointmentTime = LocalTime.parse(bookingTime);

        if (patient.getAppointmentMap().get(appointmentTime) != null) {
            return "You already have a booking at " + bookingTime + " with " + patient.getAppointmentMap().get(appointmentTime);
        }

        // get doc and make booking true at that slot if not found throw error

        Optional<Slot> slotDataWrapped = DocAppointmentUtility.getDoctorByDoctorName(doctorName).getSlots().stream()
                .filter(slot -> slot.getStartTime().equals(appointmentTime))
                .findFirst();

        if (slotDataWrapped.isPresent()) {
            var slotData = slotDataWrapped.get();
            var bookingId = UUID.randomUUID();
            if (slotData.isBooked()) {
                if (isWaitList) {
                    Appointment appointment = new Appointment(bookingId, appointmentTime, doctorName);
                    appointment.setWaitList(true);
                    patient.getAppointmentList().add(appointment);
                    DocAppointmentUtility.getDoctorByDoctorName(doctorName).getWaitLists().add(new WaitList(bookingId, patientName));
                    DocAppointmentUtility.mapPatientViaBookingId(bookingId, patient);
                    return "Added to the wait list. Booking id" + bookingId;
                } else
                    return "No slots available for Doc." + doctorName + " at " + bookingTime;
                // don't set appointmentMap as booking is not confirmed yet
            }
            // normal booking so doesn't matter what wait list value is from api prospective
            Appointment appointment = new Appointment(bookingId, appointmentTime, doctorName);
            patient.getAppointmentList().add(appointment);
            patient.getAppointmentMap().put(appointmentTime, doctorName);
            DocAppointmentUtility.mapPatientViaBookingId(bookingId, patient);

            // book doc slots
            slotData.setBooked(true);
            slotData.setBookedBy(patientName);
            return "Booked. booking id: " + appointment.getBookingId();
        } else {
            return "No slots available for Doc." + doctorName + " at " + bookingTime;
        }
    }

    @Override
    public List<Appointment> showAppointmentBooked(String patientName) {

        return DocAppointmentUtility.getPatientByPatientName(patientName).getAppointmentList();
    }

    @Override
    public String cancelBooking(String bookingId) {

        Patient patient = DocAppointmentUtility.getPatientByBookingId(UUID.fromString(bookingId));

        if (Objects.isNull(patient)) {
            return "Invalid booking ID: " + bookingId;
        }

        Appointment appointment = patient.getAppointmentList()
                .stream()
                .filter(app -> bookingId.equalsIgnoreCase(app.getBookingId().toString()))
                .findFirst().orElseThrow(() -> new RuntimeException("No appointment found for patient " + patient.getPatientName()));

        Doctor doctor = DocAppointmentUtility.getDoctorByDoctorName(appointment.getDoctorName());

        if (!doctor.getWaitLists().isEmpty()) {
            WaitList waitList = doctor.getWaitLists().remove();

            // get the slot from doc

            Slot slotData = doctor.getSlots().stream().filter(slot -> patient.getPatientName().equalsIgnoreCase(slot.getBookedBy())
                            && appointment.getAppointmentTime().equals(slot.getStartTime()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("no slot found for the Doc." + doctor.getDoctorName()));
            slotData.setBookedBy(waitList.getPatientName());
            slotData.setBooked(true);
            // get the new patient appointment

            Patient waitingPatient = DocAppointmentUtility.getPatientByBookingId(waitList.getBookingId());

            // do operation of waiting patient

            Appointment waitingPatientAppointment = waitingPatient.getAppointmentList()
                    .stream()
                    .filter(appt -> appt.isWaitList()
                            && appt.getBookingId().equals(waitList.getBookingId()))
                    .findFirst().orElseThrow(() -> new RuntimeException("something wrong with wait list"));

            waitingPatientAppointment.setWaitList(false);

            waitingPatient.getAppointmentMap().put(waitingPatientAppointment.getAppointmentTime(), doctor.getDoctorName());

            System.out.println("Booking confirmed for Booking ID: " + waitingPatientAppointment.getBookingId() + "of patient :"+ waitingPatient.getPatientName());
        }
        else {
            doctor.getSlots().stream().filter(slot -> appointment.getAppointmentTime().equals(slot.getStartTime())).findFirst().orElseThrow().setBooked(false);
        }

        // delete
        patient.getAppointmentMap().remove(appointment.getAppointmentTime());
        patient.getAppointmentList().remove(appointment);
        PatientRepo.getBookingIdPatientMap().remove(UUID.fromString(bookingId));

        return "Booking cancelled of booking ID: " + bookingId + "of patient "+patient.getPatientName();
    }
}
