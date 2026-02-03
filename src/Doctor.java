/**
 * Concrete class representing a Doctor in the Sports Injury Management System.
 * Extends Person to inherit common attributes (name, age, gender, contact, address).
 * 
 * Rationale: Doctor is a concrete implementation of the abstract Person class.
 * Doctors have specialized attributes (specialty, ID) that distinguish them
 * from Patients, demonstrating polymorphism through inheritance.
 */
public class Doctor extends Person {
    private final String specialty;
    private final int id;
    private static int ID_Counter =1 ;

    /**
     * Constructs a Doctor with full personal and professional information.
     * All attributes are validated through the Person superclass constructor.
     */

    //msh mfrod ast8d el abtract method bta3t el person 🚨🚨🚨 
    public Doctor(String name, int age, boolean gender, String contact_no, String address, String specialty) {
        super(name, age, gender, contact_no, address);
        this.id = ID_Counter++;
        this.specialty = specialty;
    }

    /**
     * Retrieves the doctor's medical specialty.
     * @return The doctor's specialty field (e.g., "Sports Medicine")
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Retrieves the doctor's unique professional ID.
     * @return The doctor's ID number
     */
    public int getId() {
        return id;
    }
}