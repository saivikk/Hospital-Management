package database;

import models.*;
import java.time.LocalDateTime;
import java.util.*;

public class DatabaseManager {
    private static DatabaseManager instance;
    private List<Patient> patients;
    private List<Doctor> doctors;
    private List<Appointment> appointments;
    private int nextPatientId = 1;
    private int nextDoctorId = 1;
    private int nextAppointmentId = 1;
    
    private DatabaseManager() {
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
        appointments = new ArrayList<>();
        initializeSampleData();
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void initializeSampleData() {
        // Add sample doctors
        addDoctor(new Doctor(nextDoctorId++, "Dr. John Smith", "Cardiology", "123-456-7890", "john.smith@hospital.com", "Mon-Fri 9AM-5PM", 150.0));
        addDoctor(new Doctor(nextDoctorId++, "Dr. Sarah Johnson", "Pediatrics", "123-456-7891", "sarah.johnson@hospital.com", "Mon-Sat 8AM-4PM", 120.0));
        addDoctor(new Doctor(nextDoctorId++, "Dr. Michael Brown", "Orthopedics", "123-456-7892", "michael.brown@hospital.com", "Tue-Sat 10AM-6PM", 180.0));
        
        // Add sample patients
        addPatient(new Patient(nextPatientId++, "Alice Wilson", 35, "Female", "555-0101", "123 Main St", "No known allergies"));
        addPatient(new Patient(nextPatientId++, "Bob Davis", 42, "Male", "555-0102", "456 Oak Ave", "Diabetes Type 2"));
    }
    
    // Patient operations
    public void addPatient(Patient patient) {
        if (patient.getPatientId() == 0) {
            patient.setPatientId(nextPatientId++);
        }
        patients.add(patient);
    }
    
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }
    
    public Patient getPatientById(int id) {
        return patients.stream().filter(p -> p.getPatientId() == id).findFirst().orElse(null);
    }
    
    public void updatePatient(Patient patient) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientId() == patient.getPatientId()) {
                patients.set(i, patient);
                break;
            }
        }
    }
    
    public void deletePatient(int patientId) {
        patients.removeIf(p -> p.getPatientId() == patientId);
    }
    
    // Doctor operations
    public void addDoctor(Doctor doctor) {
        if (doctor.getDoctorId() == 0) {
            doctor.setDoctorId(nextDoctorId++);
        }
        doctors.add(doctor);
    }
    
    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors);
    }
    
    public Doctor getDoctorById(int id) {
        return doctors.stream().filter(d -> d.getDoctorId() == id).findFirst().orElse(null);
    }
    
    public void updateDoctor(Doctor doctor) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getDoctorId() == doctor.getDoctorId()) {
                doctors.set(i, doctor);
                break;
            }
        }
    }
    
    public void deleteDoctor(int doctorId) {
        doctors.removeIf(d -> d.getDoctorId() == doctorId);
    }
    
    // Appointment operations
    public void addAppointment(Appointment appointment) {
        if (appointment.getAppointmentId() == 0) {
            appointment.setAppointmentId(nextAppointmentId++);
        }
        appointments.add(appointment);
    }
    
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }
    
    public Appointment getAppointmentById(int id) {
        return appointments.stream().filter(a -> a.getAppointmentId() == id).findFirst().orElse(null);
    }
    
    public void updateAppointment(Appointment appointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentId() == appointment.getAppointmentId()) {
                appointments.set(i, appointment);
                break;
            }
        }
    }
    
    public void deleteAppointment(int appointmentId) {
        appointments.removeIf(a -> a.getAppointmentId() == appointmentId);
    }
}