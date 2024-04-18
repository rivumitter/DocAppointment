package com.flipkart.dto;

public enum Speciality {
    CARDIOLOGIST("cardiologist"), DERMATOLOGIST("Dermatologist"),
    ORTHOPEDIC("Orthopedic"), GENERAL_PHYSICIAN("General Physician");

    private final String specialityName;

    Speciality(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public static Speciality getSpecialityByName(String specialityName) {
        for(Speciality speciality : Speciality.class.getEnumConstants()) {
            if(speciality.getSpecialityName().equalsIgnoreCase(specialityName))
                return speciality;
        }
        throw new RuntimeException("No specialty found!");
    }
}
