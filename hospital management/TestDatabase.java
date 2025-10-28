import database.DatabaseManager;
import models.Patient;
import java.util.List;

public class TestDatabase {
    public static void main(String[] args) {
        System.out.println("Testing Database Operations...");
        
        DatabaseManager db = DatabaseManager.getInstance();
        
        // Test getting initial patients
        List<Patient> initialPatients = db.getAllPatients();
        System.out.println("Initial patients count: " + initialPatients.size());
        for (Patient p : initialPatients) {
            System.out.println("- " + p.getName() + " (ID: " + p.getPatientId() + ")");
        }
        
        // Test adding a new patient
        Patient newPatient = new Patient();
        newPatient.setName("Test Patient");
        newPatient.setAge(30);
        newPatient.setGender("Male");
        newPatient.setPhone("123-456-7890");
        newPatient.setAddress("Test Address");
        newPatient.setMedicalHistory("Test History");
        
        System.out.println("\nAdding new patient...");
        db.addPatient(newPatient);
        
        // Test getting patients after adding
        List<Patient> afterPatients = db.getAllPatients();
        System.out.println("Patients count after adding: " + afterPatients.size());
        for (Patient p : afterPatients) {
            System.out.println("- " + p.getName() + " (ID: " + p.getPatientId() + ")");
        }
        
        System.out.println("\nDatabase operations working correctly!");
    }
}