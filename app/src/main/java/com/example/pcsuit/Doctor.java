package com.example.pcsuit;

public class Doctor {

        private String name;
        private int age;
        private String gender;
        private String experience;
        private String speciality;
        private String languages;
        private String hospital;
        private String hospitalLocation;
        private String description;
        private String email;
        private String password;

        // Empty constructor required for Firebase
        public Doctor(String s, String cardiologist, String mbararaRegionalReferralHospital) {
        }

        // Constructor
        public Doctor(String name, int age, String gender, String experience, String speciality, String languages, String hospital, String hospitalLocation, String description, String email, String password) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.experience = experience;
            this.speciality = speciality;
            this.languages = languages;
            this.hospital = hospital;
            this.hospitalLocation = hospitalLocation;
            this.description = description;
            this.email = email;
            this.password = password;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHospitalLocation() {
        return hospitalLocation;
    }

    public void setHospitalLocation(String hospitalLocation) {
        this.hospitalLocation = hospitalLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
// Getters and setters (optional, depending on your requirements)
        // Add getters and setters for all fields


}
