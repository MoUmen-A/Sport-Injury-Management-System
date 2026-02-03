import java.io.*;
import java.util.ArrayList;

/**
 * Manages patient accounts and authentication in the system.
 * Handles loading/saving accounts from/to file and provides
 * username validation and authentication services.
 * 
 * Rationale: Separating account management into its own class
 * follows the Single Responsibility Principle and makes the code
 * more maintainable and testable.
 */
class AccountManager {
    private static final String FILE_NAME = "accounts.txt";
    private final ArrayList<Patient> users = new ArrayList<>();

    /**
     * Constructs an AccountManager and loads existing accounts from file.
     * Automatically populates the users list on initialization.
     * Constructor is used to load the accounts from the file.
     */
    public AccountManager() {
        loadUsersFromFile();
    }

    /**
     * Loads patient accounts from the accounts.txt file.
     * Each line should contain "username,password" format.
     * Creates Patient objects with minimal information (username/password only).
     */
    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    // Full patient details format: username,password,name,age,gender,contact,address
                    users.add(new Patient(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Boolean.parseBoolean(parts[4]), parts[5], parts[6]));
                } else if (parts.length == 2) {
                    // Old format: username,password only (for backward compatibility)
                    users.add(new Patient(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    /**
     * Adds a new patient to the in-memory list.
     * Note: This does not persist to file - call saveAllUsers() to persist.
     * @param patient The Patient object to add to the account list
     */
    public void saveUser(Patient patient) {
        users.add(patient);
    }
    
    /**
     * Updates an existing patient in the in-memory list.
     * Replaces the existing patient with the same username.
     * Note: This does not persist to file - call saveAllUsers() to persist.
     * @param updatedPatient The Patient object with updated information
     * @return true if the patient was found and updated, false otherwise
     */
    public boolean updateUser(Patient updatedPatient) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedPatient.getUsername())) {
                users.set(i, updatedPatient);
                return true;
            }
        }
        return false;
    }
            
    /**
     * Persists all pat ient accounts to the accounts.txt file.
     * Overwrites the existing file with current account data.
     * Format: Each line contains "username,password".
     * Handles file I/O errors gracefully without crashing the application.
     */
     public void saveAllUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            for (Patient patient : users) {
                writer.write(patient.getUsername() + "," + patient.getPassword() + "," + patient.getName() + "," + patient.getAge() + "," + patient.isGender() + "," + patient.getContact_no() + "," + patient.getAddress() );
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    /**
     * Checks if a username is already taken by another patient.
     * Used during account registration to prevent duplicate usernames.
     */
    public boolean isUsernameTaken(String username) {
        for (Patient patient : users) {
            if (patient.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates user credentials for login.
     * Checks if the username exists and the password matches.
     */
    public boolean validateUser(String username, String password) {
        for (Patient patient : users) {
            if (patient.getUsername().equals(username) && patient.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a Patient object by username.
     * Useful for loading patient data after successful login.
     */
    public Patient getUser(String username) {
        for (Patient patient : users) {
            if (patient.getUsername().equals(username)) {
                return patient;
            }
        }
        return null;
    }
}