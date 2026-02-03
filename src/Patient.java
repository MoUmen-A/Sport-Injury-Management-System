/**
 * Concrete class representing a Patient in the Sports Injury Management System.
 * Extends Person to inherit common attributes (name, age, gender, contact, address).
 * 
 * This design ensures Person cannot be instantiated directly, while Patient
 * provides a complete implementation with authentication and medical data management.
 * 
 * Composition relationships:
 * - Has multiple Appointments (reservations) - tracks scheduled appointments
 * - Has multiple Reports (reports) - stores generated medical reports
 * - Has multiple Injuries (injuries) - maintains injury history
 */
import java.util.ArrayList;
import java.util.List;

class Patient extends Person {
    private final String username;
    private final String password;
    private final List<Appointment> reservations = new ArrayList<>();
    private final List<String> reports = new ArrayList<>();
    private final List<Injury> injuries = new ArrayList<>();

    /**
     * Constructs a Patient with full personal and account information.
     * Initializes all composition lists (reservations, reports, injuries) as empty.
     * Constructor is used to create a new patient with all the information.
     */
    public Patient(String username, String password, String name, int age, boolean gender, String contact_no, String address) {
        super(name, age, gender, contact_no, address);
        this.username = username;
        this.password = password;
    }

    /**
     * Constructs a Patient with minimal account information only.
     * Uses default values for personal information, which can be updated later.
     * Useful when creating an account before collecting full patient details.
  
     */
     public Patient(String username, String password) {
        this(username, password, "New Patient", 0, true, "00000000000", "");
     }

    /**
     * Retrieves the patient's username.
     * @return The unique username used for login
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the patient's password.
     * Note: In production, passwords should be hashed, not stored in plain text.
     * @return The password used for authentication
     */
    public String getPassword() {
        return password;
    }


    /**
     * Retrieves the list of appointments (reservations) for this patient.
     * Returns a reference to the internal list - modifications affect the patient's data.
     * 
     * @return A list of Appointment objects representing scheduled appointments
     */
    public List<Appointment> getReservations() {
        return reservations;
    }

    /**
     * Retrieves the list of reports generated for this patient.
     * Returns a reference to the internal list - modifications affect the patient's data.
     * 
     * @return A list of String objects representing medical reports
     */
    public List<String> getReports() {
        return reports;
    }

    /**
     * Retrieves the list of injuries recorded for this patient.
     * Returns a reference to the internal list - modifications affect the patient's data.
     * This represents the composition relationship: Patient HAS-A list of Injuries.
     * 
     * @return A list of Injury objects representing the patient's injury history
     */
    public List<Injury> getInjuries() {
        return injuries;
    }

    /**
     * Adds a new appointment/reservation to the patient's record.
     * Performs null-check to prevent adding invalid appointments.
     * 
     * @param appointment The Appointment object to add (must not be null)
     */
    public void addReservation(Appointment appointment) {
        if (appointment != null) {
            reservations.add(appointment);
        }
    }

    /**
     * Adds a new report string to the patient's record.
     * Performs validation to ensure the report is not null or empty.
     * 
     * @param report The report string to add (must not be null or empty after trimming)
     */
    public void addReport(String report) {
        if (report != null && !report.trim().isEmpty()) {
            reports.add(report);
        }
    }

    /**
     * Adds a new injury to the patient's injury history.
     * Performs null-check to prevent adding invalid injuries.
     * This maintains the composition relationship: Patient HAS-A Injury.
     * 
     */
    public void addInjury(Injury injury) {
        if (injury != null) {
            injuries.add(injury);
        }
    }
    
    /**
     * Creates a new Patient object with updated personal details while preserving
     * all existing reservations, reports, and injuries. This is useful when updating
     * patient information without losing their medical history.
     */
    public Patient updateDetails(String name, int age, boolean gender, String contact_no, String address) {
        Patient updated = new Patient(this.username, this.password, name, age, gender, contact_no, address);
        // Copy all reservations
        updated.reservations.addAll(this.reservations);
        // Copy all reports
        updated.reports.addAll(this.reports);
        // Copy all injuries
        updated.injuries.addAll(this.injuries);
        return updated;
    }
}