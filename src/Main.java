import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AccountManager accountManager = new AccountManager();
    public static void main(String[] args) {
        System.out.println("=== Welcome to Sports Injury Management System ===\n");

        while (true) {
            Patient patient = handleAuthentication();
            if (patient == null) {
                continue;
            }

            patient = collectPatientInformation(patient);
            Sport selectedSport = processMedicalData(patient);
            generateAndSaveReport(patient, selectedSport);

            if (!askToContinue()) {
                break;
            }

            accountManager.saveAllUsers();
        }

        accountManager.saveAllUsers();
        scanner.close();
        System.out.println("\nThank you for using Sports Injury Management System!");
    }

    private static Patient handleAuthentication() {
        System.out.println("=== Authentication ===");
        System.out.println("1. Sign Up (Create New Account)");
        System.out.println("2. Log In (Existing Account)");
        System.out.print("Enter your choice (1 or 2): ");

        int choice = getValidInteger(1, 2);
        if (choice == -1) {
            System.out.println("Invalid choice. Please try again.\n");
            return null;
        }

        if (choice == 1) {
            return handleSignUp();
        } else {
            return handleLogin();
        }
    }

    private static Patient handleSignUp() {
        System.out.println("\n=== Sign Up ===");

        String username = getValidUsername();
        if (username == null) {
            return null;
        }

        String password = getValidPassword();
        if (password == null) {
            return null;
        }

        Patient patient = new Patient(username, password);
        accountManager.saveUser(patient);
        accountManager.saveAllUsers();

        System.out.println("\nAccount created successfully!");
        System.out.println("Welcome, " + username + "!\n");
        return patient;
    }

    private static Patient handleLogin() {
        System.out.println("\n=== Log In ===");

        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        if (accountManager.validateUser(username, password)) {
            Patient patient = accountManager.getUser(username);
            System.out.println("\nLogin successful! Welcome back, " + username + "!\n");
            return patient;
        } else {
            System.out.println("\nInvalid username or password. Please try again.\n");
            return null;
        }
    }

    private static String getValidUsername() {
        while (true) {
            System.out.print("Enter a username: ");
            String username = scanner.nextLine().trim();

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty. Please try again.");
                continue;
            }

            if (accountManager.isUsernameTaken(username)) {
                System.out.println("Username '" + username + "' is already taken. Please choose another.");
                System.out.print("Try again? (yes/no): ");
                String response = scanner.nextLine().trim();
                if (!response.equalsIgnoreCase("yes")) {
                    return null;
                }
                continue;
            }

            return username;
        }
    }

    private static String getValidPassword() {
        while (true) {
            System.out.print("Enter a password: ");
            String password = scanner.nextLine().trim();

            if (password.trim().isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
                continue;
            }

            return password;
        }
    }

    private static Patient collectPatientInformation(Patient patient) {
        System.out.println("=== Patient Information ===");

        while (true) {
            try {
                System.out.print("Enter your full name: ");
                String name = scanner.nextLine().trim();

                System.out.print("Enter your age: ");
                int age = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter your gender (1 for Male, 0 for Female): ");
                int genderInput = scanner.nextInt();
                scanner.nextLine();
                boolean gender = (genderInput == 1);

                System.out.print("Enter your contact number (11 digits): ");
                String contactNo = scanner.nextLine().trim();

                System.out.print("Enter your address: ");
                String address = scanner.nextLine().trim();

                // Update patient details while preserving reservations, reports, and injuries
                patient = patient.updateDetails(name, age, gender, contactNo, address);
                accountManager.updateUser(patient);
                accountManager.saveAllUsers();
                
                System.out.println("\nPatient information saved successfully!\n");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.println("Please try again.\n");
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("\nInvalid input. Please try again.\n");
            }
        }

        return patient;
    }

    
    private static void generateAndSaveReport(Patient patient, Sport selectedSport) {
        if (patient.getInjuries().isEmpty() || patient.getReservations().isEmpty()) {
            System.out.println("Cannot generate report: Missing injury or appointment data.");
            return;
        }
        
        Injury injury = patient.getInjuries().get(patient.getInjuries().size() - 1);
        Appointment appointment = patient.getReservations().get(patient.getReservations().size() - 1);
        Treatment treatment = Treatment.getTreatment(injury.getType());
        
        Report report = new Report(patient, injury, treatment, appointment, selectedSport);
        report.generateReport();
        
        patient.addReport("Generated report for " + injury.getType() + " on " + 
        appointment.getWeekday() + " at " + appointment.getTime());
        System.out.println("\nReport generated and saved successfully!\n");
    }
    
    private static Sport processMedicalData(Patient patient) {
        Sport selectedSport = chooseSport();
        System.out.println("Selected sport: " + selectedSport.getName() + "\n");

        BodyPart chosenPart = chooseBodyPart();
        Injury injury = chooseInjury(chosenPart);
        patient.addInjury(injury);
        System.out.println("Selected injury: " + injury.getType() + "\n");

        Appointment appointment = Appointment.createAppointment(patient);
        patient.addReservation(appointment);
        System.out.println("Appointment scheduled successfully!\n");

        return selectedSport;
    }
    private static Sport chooseSport() {
        SportsCollection sportsCollection = new SportsCollection();
        ArrayList<Sport> sportsList = sportsCollection.getSportsList();

        System.out.println("=== Choose a Sport ===");
        for (int i = 0; i < sportsList.size(); i++) {
            System.out.println((i + 1) + ". " + sportsList.get(i).getName());
        }

        int sportChoice;
        do {
            System.out.print("Enter the number of the sport (1-" + sportsList.size() + "): ");
            sportChoice = getValidInteger(1, sportsList.size());
        } while (sportChoice < 1 || sportChoice > sportsList.size());

        return sportsList.get(sportChoice - 1);
    }

    private static BodyPart chooseBodyPart() {
        BodyPart[] parts = BodyPart.values();
        System.out.println("=== Choose Body Part ===");
        for (int i = 0; i < parts.length; i++) {
            System.out.println((i + 1) + ". " + parts[i]);
        }

        int choice;
        do {
            System.out.print("Enter the number of the body part (1-" + parts.length + "): ");
            choice = getValidInteger(1, parts.length);
        } while (choice < 1 || choice > parts.length);

        return parts[choice - 1];
    }

    private static Injury chooseInjury(BodyPart part) {
        List<Injury> filtered = Injury.getInjuriesByBodyPart(part);
        System.out.println("\n=== Choose Injury ===");
        for (int i = 0; i < filtered.size(); i++) {
            Injury injury = filtered.get(i);
            System.out.println((i + 1) + ". " + injury.getType());
            System.out.println("   Description: " + injury.getAthleteDescription());
            System.out.println("   Movable: " + (injury.isMovable() ? "Yes" : "No") + "\n");
        }

        int choice;
        do {
            System.out.print("Enter the number of the injury (1-" + filtered.size() + "): ");
            choice = getValidInteger(1, filtered.size());
        } while (choice < 1 || choice > filtered.size());

        return filtered.get(choice - 1);
    }

    // Check input form the user for each input
    private static int getValidInteger(int min, int max) {
        try {
            int value = scanner.nextInt();
            scanner.nextLine();

            if (value >= min && value <= max) {
                return value;
            } else {
                System.out.println("Please enter a number between " + min + " and " + max + ".");
                return -1;
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    private static boolean askToContinue() {
        System.out.print("Do you want to add another patient? (yes/no): ");
        String response = scanner.nextLine().trim();
        return response.equalsIgnoreCase("yes");
    }
}
