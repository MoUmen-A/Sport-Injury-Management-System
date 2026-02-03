/**
 * Abstract base class representing a Person in the system.
 * This class cannot be instantiated directly - it serves as a foundation
 * for concrete implementations like Patient and Doctor.
 * 
 * Rationale: Making Person abstract enforces proper OOP design by ensuring
 * that only specific types of persons (Patient, Doctor) can be created,
 * preventing the creation of generic "Person" objects that lack specific context.
 */
public abstract class Person {
    private String name;
    private int age;
    private boolean gender;// camal case , 
    private String contact_no; // 11-digit string quanitive , qualili
    private String address;

    /**
     * Constructs a Person with validated attributes.
    */
   
    public Person(String name, int age, boolean gender, String contact_no, String address) {
        setName(name);
        setAge(age);
        setGender(gender);
        setContact_no(contact_no);
        setAddress(address);
    }

    /**
     * Default constructor for Person.
     * Allows subclasses to initialize with default values before setting attributes.
     */
    public Person() {
    }

    /**
     * Retrieves the person's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the person's age.
     * @return The person's age (guaranteed to be non-negative)
     */
    public int getAge() {
        return age;
    }

    /**
     * Retrieves the person's gender.
     * @return true if Male, false if Female
     */
    public boolean isGender() {
        return gender;
    }

    /**
     * Retrieves the person's contact number.
     * @return The 11-digit contact number as a string
     */
    public String getContact_no() {
        return contact_no;
    }

    public String getAddress() {
        return address;
    }

    /**
     * Sets the person's name with null safety and trimming.
     */
    protected void setName(String name) {
        this.name = name == null ? "" : name.trim();
    }

    /**
     * Sets the person's age with validation.
     * Enforces  age cannot be negative.
     * @throws IllegalArgumentException if age is negative
     */
    protected void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }

    protected void setGender(boolean gender) {
        this.gender = gender;
    }

    protected void setContact_no(String contact_no) {
        if (contact_no == null ) {
            throw new IllegalArgumentException("Contact number must be exactly 11 digits");
        }
        this.contact_no = contact_no;
    }

    protected void setAddress(String address) {
        this.address = (address == null) ? "" : address.trim();
    }
}