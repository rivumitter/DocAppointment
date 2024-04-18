package com.flipkart.dto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Doctor {

    private String doctorName;
    private Speciality speciality;
    List<Slot> slots;
    Queue<WaitList> waitLists;

    public Doctor(String doctorName, Speciality speciality) {
        this.doctorName = doctorName;
        this.speciality = speciality;
        this.slots = new ArrayList<>();
        waitLists = new LinkedList<>();
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public Queue<WaitList> getWaitLists() {
        return waitLists;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
}
