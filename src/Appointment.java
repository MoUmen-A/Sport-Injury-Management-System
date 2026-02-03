import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents a medical appointment/reservation in the system.
 * Tracks the relationship between a Patient, Doctor, weekday, and time slot.
 * 
 * Rationale: Appointment prevents double-booking by maintaining a static
 * booking registry that tracks which time slots are taken for each doctor
 * on each weekday. This ensures data integrity and prevents scheduling conflicts.
 */
class Appointment {
    private final Weekday weekday;
    private final String time;
    private final String doctorName;
    private final Patient patient;
    private String athleteDescription;

    /** Available doctors in the system */
    public static final String[] DOCTORS = {"Dr. Maiada", "Dr. Ahmed Mo'men", "Dr. Shehab Wael", "Dr. Omar Tamer"};
    /** Available time slots for appointments */
    public static final String[] TIMES = {"4:30 PM", "6:30 PM", "8:30 PM", "10:00 PM"};
    /** Available weekdays for appointments */
    public static final Weekday[] DAYS = {Weekday.SUNDAY, Weekday.TUESDAY, Weekday.THURSDAY};

    // used in GUI for display
    public String[] getListOfDoctors(){
        return DOCTORS ;
    }

    public String[] getListOfTimes(){
        return TIMES ;
    }

    public Weekday[] getListOfDay(){
        return DAYS ;
    }

    /**
     * Static booking registry: doctor -> weekday -> set of booked times.
     * This data structure prevents double-booking by tracking which time slots
     * are already reserved for each doctor on each weekday.
     * 
     * Structure: Map<DoctorName, Map<Weekday, Set<Time>>>
     */
    private static final Map<String, Map<Weekday, Set<String>>> BOOKINGS = new HashMap<>();

    /**
     * Constructs an Appointment with all required information.

     */
    public Appointment(Weekday weekday, String time, String doctorName, Patient patient) {
        this.weekday = weekday;
        this.time = time;
        this.doctorName = doctorName;
        this.patient = patient;
        this.athleteDescription = "";
    }
    
    /**
     * Constructs an Appointment with all required information including athlete description.

     */
    public Appointment(Weekday weekday, String time, String doctorName, Patient patient, String athleteDescription) {
        this.weekday = weekday;
        this.time = time;
        this.doctorName = doctorName;
        this.patient = patient;
        this.athleteDescription = athleteDescription != null ? athleteDescription : "";
    }
    
    /**
     * Sets the athlete description for this appointment.
     * @param athleteDescription The description of the injury from the athlete's perspective
     */
    public void setAthleteDescription(String athleteDescription) {
        this.athleteDescription = athleteDescription != null ? athleteDescription : "";
    }
    
    /**
     * Retrieves the athlete description for this appointment.
     * @return The description of the injury from the athlete's perspective
     */
    public String getAthleteDescription() {
        return athleteDescription;
    }

    /**
     * Interactive method to create an appointment via console input.
     * Guides the user through selecting doctor, weekday, and available time slot.
     * Automatically checks availability and prevents double-booking.
     * 
     * Note: This method uses Scanner which should be closed by the caller
     * in production code. Suppressed warning for resource leak.
     * 
     * @param patient The Patient object making the appointment
     * @return A new Appointment object with the selected details
     */
    @SuppressWarnings("resource")
    public static Appointment createAppointment(Patient patient) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose a doctor:");
        for (int i = 0; i < DOCTORS.length; i++) {
            System.out.println((i + 1) + ". " + DOCTORS[i]);
        }
        int doctorChoice;
        do {
            System.out.print("Enter the number of the doctor (1-" + DOCTORS.length + "): ");
            doctorChoice = scanner.nextInt();
        } while (doctorChoice < 1 || doctorChoice > DOCTORS.length);

        System.out.println("\nChoose a weekday:");
        for (int i = 0; i < DAYS.length; i++) {
            System.out.println((i + 1) + ". " + DAYS[i]);
        }
        int dayChoice;
        do {
            System.out.print("Enter the number of the day (1-" + DAYS.length + "): ");
            dayChoice = scanner.nextInt();
        } while (dayChoice < 1 || dayChoice > DAYS.length);

        int timeChoice = -1;
        Weekday selectedDay = DAYS[dayChoice - 1];
        String selectedDoctor = DOCTORS[doctorChoice - 1];
        while (true) {
            System.out.println("\nChoose a time:");
            for (int i = 0; i < TIMES.length; i++) {
                if (isSlotFree(selectedDoctor, selectedDay, TIMES[i])) {
                    System.out.println((i + 1) + ". " + TIMES[i]);
                }
            }
            System.out.print("Enter the number of the time (1-" + TIMES.length + "): ");
            timeChoice = scanner.nextInt() - 1;

            if (timeChoice >= 0 && timeChoice < TIMES.length && isSlotFree(selectedDoctor, selectedDay, TIMES[timeChoice])) {
                bookSlot(selectedDoctor, selectedDay, TIMES[timeChoice]);
                break;
            } else {
                System.out.println("The selected time is already booked for this doctor. Please choose another time.");
            }
        }

        return new Appointment(selectedDay, TIMES[timeChoice], selectedDoctor, patient);
    }

    /**
     * Checks if a specific time slot is available for a doctor on a given weekday.
     * Uses the static BOOKINGS registry to determine availability.
     * 
     * @param doctor The doctor's name
     * @param day The weekday enum to check
     * @param time The time slot string to check
     * @return true if the slot is free, false if already booked
     */
    public static boolean isSlotFree(String doctor, Weekday day, String time) {
        return BOOKINGS.getOrDefault(doctor, new HashMap<>())
                .getOrDefault(day, new HashSet<>())
                .stream()
                .noneMatch(t -> t.equals(time));
    }

    /**
     * Books a time slot for a doctor on a specific weekday.
     * Adds the time to the BOOKINGS registry to prevent future double-booking.
     * Uses computeIfAbsent to automatically create nested maps/sets if needed.
     * 
     * @param doctor The doctor's name
     * @param day The weekday enum to book
     * @param time The time slot string to book
     */
    public static void bookSlot(String doctor, Weekday day, String time) {
        BOOKINGS.computeIfAbsent(doctor, d -> new HashMap<>())
                .computeIfAbsent(day, w -> new HashSet<>())
                .add(time);
    }

    /**
     * Retrieves the weekday of this appointment.
     * @return The Weekday enum (SUNDAY, TUESDAY, or THURSDAY)
     */
    public Weekday getWeekday() {
        return weekday;
    }

    /**
     * Retrieves the doctor's name for this appointment.
     * @return The doctor's name string
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * Retrieves the time slot for this appointment.
     * @return The time string (e.g., "4:30 PM")
     */
    public String getTime() {
        return time;
    }

    /**
     * Retrieves the patient who made this appointment.
     * @return The Patient object associated with this appointment
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Displays appointment details to the console.
     * Shows doctor name, weekday, time, and patient name.
     * Useful for console-based interfaces and debugging.
     */
    public void displayAppointment() {
        System.out.println("\nAppointment Details:");
        System.out.println("Doctor: " + doctorName);
        System.out.println("Day: " + weekday);
        System.out.println("Time: " + time);
        System.out.println("Patient: " + (patient != null ? patient.getName() : "N/A"));
    }
}