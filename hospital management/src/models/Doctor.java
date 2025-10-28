package models;

public class Doctor {
    private int doctorId;
    private String name;
    private String specialization;
    private String phone;
    private String email;
    private String schedule;
    private double consultationFee;

    // Constructor
    public Doctor(int doctorId, String name, String specialization, String phone, String email, String schedule, double consultationFee) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
        this.schedule = schedule;
        this.consultationFee = consultationFee;
    }

    // Default constructor
    public Doctor() {}

    // Getters and Setters
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    @Override
    public String toString() {
        return "Doctor [doctorId=" + doctorId +
               ", name=" + name +
               ", specialization=" + specialization +
               ", phone=" + phone +
               ", email=" + email +
               ", schedule=" + schedule +
               ", consultationFee=" + consultationFee + "]";
    }
}
