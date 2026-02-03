import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;



public class MainGUI {
    private static final Color PRIMARY_BG = new Color(237, 234, 246);
    private static final Color ACCENT_COLOR = new Color(94, 84, 142);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);

    private final JFrame frame;
    private final JPanel rootPanel;
    private final AccountManager accountManager;

    private Patient currentUser;
    private Sport selectedSport;
    private Injury selectedInjury;
    private Appointment appointment;
    private Treatment treatment;

    public MainGUI() {
        accountManager = new AccountManager();
        frame = new JFrame("Sports Injury Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 560);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(640, 520));

        rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout());
        rootPanel.setBackground(PRIMARY_BG);
        rootPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        initializeGUI();

        frame.add(rootPanel);
        frame.setVisible(true);
    }

    private void initializeGUI() {
        JPanel content = createVerticalPanel();

        JLabel welcomeLabel = createTitleLabel("Welcome to the Sports Injury Management System");
        content.add(welcomeLabel);
        content.add(Box.createVerticalStrut(12));
        content.add(createHintLabel("Please sign up or log in to continue."));

        content.add(Box.createVerticalStrut(24));
        content.add(createPrimaryButton("Sign Up", e -> openSignUpForm()));
        content.add(Box.createVerticalStrut(8));
        content.add(createSecondaryButton("Log In", e -> openLoginForm()));

        swapContent(content);
    }

    private void openSignUpForm() {
        JPanel content = createFormPanel("Create your account");

        JTextField usernameField = createTextField();
        JPasswordField passwordField = createPasswordField();

        content.add(createLabeledField("Username", usernameField));
        content.add(createLabeledField("Password", passwordField));

        JButton submitButton = createPrimaryButton("Sign Up", e -> {
            String username = usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Both username and password are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (accountManager.isUsernameTaken(username)) {
                JOptionPane.showMessageDialog(frame, "Username is already taken. Please try another.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Patient user = new Patient(username, password);
                accountManager.saveUser(user);
                accountManager.saveAllUsers();
                JOptionPane.showMessageDialog(frame, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                initializeGUI();
            }
        });

        JPanel actions = createActionsPanel();
        actions.add(submitButton);
        actions.add(createSecondaryButton("Back", e -> initializeGUI()));
        content.add(actions);

        swapContent(content);
    }

    private void openLoginForm() {
        JPanel content = createFormPanel("Log in to your account");

        JTextField usernameField = createTextField();
        JPasswordField passwordField = createPasswordField();

        content.add(createLabeledField("Username", usernameField));
        content.add(createLabeledField("Password", passwordField));

        JButton submitButton = createPrimaryButton("Log In", e -> {
            String username = usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Both username and password are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (accountManager.validateUser(username, password)) {
                currentUser = accountManager.getUser(username);
                JOptionPane.showMessageDialog(frame, "Login successful! Welcome back, " + username + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                openUserDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel actions = createActionsPanel();
        actions.add(submitButton);
        actions.add(createSecondaryButton("Back", e -> initializeGUI()));
        content.add(actions);

        swapContent(content);
    }

    private void openUserDashboard() {
        JPanel content = createVerticalPanel();

        String welcomeName = (currentUser != null && currentUser.getName() != null && !currentUser.getName().equals("New Patient")) 
            ? currentUser.getName() 
            : (currentUser != null ? currentUser.getUsername() : "User");
        
        JLabel dashboardLabel = createTitleLabel("Welcome, " + welcomeName + "!");
        content.add(dashboardLabel);
        content.add(Box.createVerticalStrut(12));
        content.add(createHintLabel("Manage your medical information and appointments"));
        content.add(Box.createVerticalStrut(20));

        // Status indicators
        JPanel statusPanel = createStatusPanel();
        content.add(statusPanel);
        content.add(Box.createVerticalStrut(16));

        content.add(createPrimaryButton("Enter/Update User Details", e -> openUserDetailsForm()));
        content.add(Box.createVerticalStrut(6));
        content.add(createSecondaryButton("Select Sport", e -> openSportSelectionForm()));
        content.add(Box.createVerticalStrut(6));
        content.add(createSecondaryButton("Select Injury", e -> openInjurySelectionForm()));
        content.add(Box.createVerticalStrut(6));
        content.add(createSecondaryButton("Schedule Appointment", e -> openAppointmentForm()));
        content.add(Box.createVerticalStrut(6));
        content.add(createSecondaryButton("Generate Report", e -> generateReport()));
        content.add(Box.createVerticalStrut(18));
        content.add(createSecondaryButton("Log Out", e -> {
            currentUser = null;
            selectedSport = null;
            selectedInjury = null;
            appointment = null;
            initializeGUI();
        }));

        swapContent(content);
    }
    
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setBackground(PRIMARY_BG);
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        
        if (currentUser != null) {
            String status = "Status: ";
            if (currentUser.getName() != null && !currentUser.getName().equals("New Patient") && currentUser.getAge() > 0) {
                status += "Profile Complete";
            } else {
                status += "Profile Incomplete";
            }
            
            JLabel statusLabel = new JLabel(status);
            statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            statusLabel.setForeground(new Color(80, 80, 80));
            statusPanel.add(statusLabel);
            
            if (selectedSport != null) {
                JLabel sportLabel = new JLabel("Sport: " + selectedSport.getName());
                sportLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                sportLabel.setForeground(new Color(80, 80, 80));
                statusPanel.add(sportLabel);
            }
            
            if (selectedInjury != null) {
                JLabel injuryLabel = new JLabel("Injury: " + currentUser.getInjuries()); // store in array list and reforamte for better visual aids
                injuryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                injuryLabel.setForeground(new Color(80, 80, 80));
                statusPanel.add(injuryLabel);
            }
            
            if (appointment != null) {
                JLabel appointmentLabel = new JLabel("Appointment: " + appointment.getWeekday() + " at " + appointment.getTime());
                appointmentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                appointmentLabel.setForeground(new Color(80, 80, 80));
                statusPanel.add(appointmentLabel);
            }
        }
        
        return statusPanel;
    }

    private void openUserDetailsForm() {
        JPanel content = createFormPanel("Enter your details");

        JTextField nameField = createTextField();
        JTextField ageField = createTextField(5);
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        JTextField contactField = createTextField(15);
        JTextField addressField = createTextField(30);

        // Pre-populate fields if user details already exist
        if (currentUser != null && currentUser.getName() != null && !currentUser.getName().equals("New Patient") && currentUser.getAge() > 0) {
            nameField.setText(currentUser.getName());
            ageField.setText(String.valueOf(currentUser.getAge()));
            genderComboBox.setSelectedItem(currentUser.isGender() ? "Male" : "Female");
            contactField.setText(currentUser.getContact_no());
            addressField.setText(currentUser.getAddress());
        }

        content.add(createLabeledField("Full name", nameField));
        content.add(createLabeledField("Age", ageField));
        content.add(createLabeledField("Gender", genderComboBox));
        content.add(createLabeledField("Contact number", contactField));
        content.add(createLabeledField("Address", addressField));

        JButton saveButton = createPrimaryButton("Save Details", e -> {
            try {
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                boolean gender = "Male".equals(genderComboBox.getSelectedItem());
                String contact = contactField.getText().trim();
                String address = addressField.getText().trim();

                if (currentUser == null) {
                    JOptionPane.showMessageDialog(frame, "Please log in first.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update patient details while preserving reservations, reports, and injuries
                Patient updatedPatient = currentUser.updateDetails(name, age, gender, contact, address);
                accountManager.updateUser(updatedPatient);
                accountManager.saveAllUsers();
                currentUser = updatedPatient;
                
                JOptionPane.showMessageDialog(frame, "Details saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                openUserDashboard();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter numeric values for age.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel actions = createActionsPanel();
        actions.add(saveButton);
        actions.add(createSecondaryButton("Back", e -> openUserDashboard()));
        content.add(actions);

        swapContent(content);
    }

    private void openSportSelectionForm() {
        JPanel content = createFormPanel("Choose a sport");

        SportsCollection sportsCollection = new SportsCollection();
        JComboBox<Sport> sportsComboBox = new JComboBox<>(sportsCollection.getSportsList().toArray(new Sport[0]));

        content.add(createLabeledField("Sport", sportsComboBox));

        JPanel actions = createActionsPanel();
        actions.add(createPrimaryButton("Select Sport", e -> {
            selectedSport = (Sport) sportsComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(frame, "You selected: " + selectedSport.getName(), "Sport Selected", JOptionPane.INFORMATION_MESSAGE);
            openUserDashboard();
        }));
        actions.add(createSecondaryButton("Back", e -> openUserDashboard()));
        content.add(actions);

        swapContent(content);
    }

    private void openInjurySelectionForm() {
        JPanel content = createFormPanel("Choose an injury");

        JComboBox<BodyPart> bodyPartComboBox = new JComboBox<>(BodyPart.values());
        JComboBox<Injury> injuriesComboBox = new JComboBox<>();

        bodyPartComboBox.addActionListener(e -> {
            BodyPart part = (BodyPart) bodyPartComboBox.getSelectedItem();
            List<Injury> injuries = Injury.getInjuriesByBodyPart(part);
            injuriesComboBox.removeAllItems();
            for (Injury injury : injuries) {
                injuriesComboBox.addItem(injury);
            }
        });
        bodyPartComboBox.setSelectedIndex(0);

        List<Injury> initialInjuries = Injury.getInjuriesByBodyPart((BodyPart) bodyPartComboBox.getSelectedItem());
        for (Injury injury : initialInjuries) {
            injuriesComboBox.addItem(injury);
        }
 
        content.add(createLabeledField("Body Part", bodyPartComboBox));
        content.add(createLabeledField("Injury", injuriesComboBox));

        JPanel actions = createActionsPanel();
        actions.add(createPrimaryButton("Select Injury", e -> {
            selectedInjury = (Injury) injuriesComboBox.getSelectedItem();
            if (currentUser != null && selectedInjury != null) {
                currentUser.addInjury(selectedInjury);
            }
            JOptionPane.showMessageDialog(frame, "You selected: " + selectedInjury.getType(), "Injury Selected", JOptionPane.INFORMATION_MESSAGE);
            openUserDashboard();
        }));
        actions.add(createSecondaryButton("Back", e -> openUserDashboard()));
        content.add(actions);

        swapContent(content);
    }

    private void openAppointmentForm() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(frame, "Please log in first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel content = createFormPanel("Schedule an appointment");

        String[] doctors = Appointment.DOCTORS;
        JComboBox<String> doctorComboBox = new JComboBox<>(doctors);
        JComboBox<Weekday> dayComboBox = new JComboBox<>(Weekday.values());
        String[] times = Appointment.TIMES;
        JComboBox<String> timeComboBox = new JComboBox<>(times);
        JTextArea athleteDescriptionField = new JTextArea(3, 30);
        athleteDescriptionField.setFont(LABEL_FONT);
        athleteDescriptionField.setLineWrap(true);
        athleteDescriptionField.setWrapStyleWord(true);
        JScrollPane descriptionScroll = new JScrollPane(athleteDescriptionField);
        descriptionScroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(4, 4, 4, 4)
        ));

        content.add(createLabeledField("Doctor", doctorComboBox));
        content.add(createLabeledField("Day", dayComboBox));
        content.add(createLabeledField("Time", timeComboBox));
        
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBackground(PRIMARY_BG);
        JLabel descLabel = new JLabel("Injury Description (optional):");
        descLabel.setFont(LABEL_FONT);
        descPanel.add(descLabel, BorderLayout.NORTH);
        descPanel.add(Box.createVerticalStrut(4));
        descPanel.add(descriptionScroll, BorderLayout.CENTER);
        content.add(descPanel);

        JPanel actions = createActionsPanel();
        actions.add(createPrimaryButton("Schedule Appointment", e -> {
            String doctor = (String) doctorComboBox.getSelectedItem();
            Weekday day = (Weekday) dayComboBox.getSelectedItem();
            String time = (String) timeComboBox.getSelectedItem();
            String athleteDescription = athleteDescriptionField.getText().trim();

            if (!Appointment.isSlotFree(doctor, day, time)) {
                JOptionPane.showMessageDialog(frame, "Selected slot is already booked for this doctor. Please choose another time.", "Unavailable", JOptionPane.WARNING_MESSAGE);
                return;
            }

            appointment = new Appointment(day, time, doctor, currentUser, athleteDescription);
            Appointment.bookSlot(doctor, day, time);
            currentUser.addReservation(appointment);
            accountManager.updateUser(currentUser);
            accountManager.saveAllUsers();

            JOptionPane.showMessageDialog(frame, "Appointment scheduled successfully!\nDoctor: " + doctor + "\nDay: " + day + "\nTime: " + time, "Appointment Scheduled", JOptionPane.INFORMATION_MESSAGE);
            openUserDashboard();
        }));
        actions.add(createSecondaryButton("Back", e -> openUserDashboard()));
        content.add(actions);

        swapContent(content);
    }

    private void generateReport() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(frame, "Please log in first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (currentUser.getName() == null || currentUser.getName().equals("New Patient") || currentUser.getAge() == 0) {
            JOptionPane.showMessageDialog(frame, "Please complete your user details first.", "Incomplete Information", JOptionPane.WARNING_MESSAGE);
            openUserDetailsForm();
            return;
        }

        JPanel content = createFormPanel("Medical Report");

        StringBuilder reportContent = new StringBuilder();
        reportContent.append("<html><body style='font-family: Segoe UI; padding: 10px;'>");
        
        reportContent.append("<h2 style='color: ").append(String.format("#%02x%02x%02x", 
            ACCENT_COLOR.getRed(), ACCENT_COLOR.getGreen(), ACCENT_COLOR.getBlue())).append(";'>Patient Information</h2>");
        reportContent.append("<p><b>Name:</b> ").append(escapeHtml(currentUser.getName())).append("<br>");
        reportContent.append("<b>Age:</b> ").append(currentUser.getAge()).append("<br>");
        reportContent.append("<b>Gender:</b> ").append(currentUser.isGender() ? "Male" : "Female").append("<br>");
        reportContent.append("<b>Contact:</b> ").append(escapeHtml(currentUser.getContact_no())).append("<br>");
        reportContent.append("<b>Address:</b> ").append(escapeHtml(currentUser.getAddress())).append("</p>");

        if (selectedSport != null) {
            reportContent.append("<h2 style='color: ").append(String.format("#%02x%02x%02x", 
                ACCENT_COLOR.getRed(), ACCENT_COLOR.getGreen(), ACCENT_COLOR.getBlue())).append(";'>Sport Information</h2>");
            reportContent.append("<p><b>Selected Sport:</b> ").append(escapeHtml(selectedSport.getName())).append("</p>");
        }

        if (selectedInjury != null) {
            reportContent.append("<h2 style='color: ").append(String.format("#%02x%02x%02x", 
                ACCENT_COLOR.getRed(), ACCENT_COLOR.getGreen(), ACCENT_COLOR.getBlue())).append(";'>Injury Details</h2>");
            reportContent.append("<p><b>Type:</b> ").append(escapeHtml(selectedInjury.getType())).append("<br>");
            reportContent.append("<b>Movable:</b> ").append(selectedInjury.isMovable() ? "Yes/limited" : "No").append("<br>");
            reportContent.append("<b>Body Part:</b> ").append(escapeHtml(selectedInjury.getBodyPart().toString())).append("<br>");
            reportContent.append("<b>Description:</b> ").append(escapeHtml(selectedInjury.getAthleteDescription())).append("</p>");

            treatment = Treatment.getTreatment(selectedInjury.getType());
            reportContent.append("<h2 style='color: ").append(String.format("#%02x%02x%02x", 
                ACCENT_COLOR.getRed(), ACCENT_COLOR.getGreen(), ACCENT_COLOR.getBlue())).append(";'>Treatment Recommendation</h2>");
            reportContent.append("<p>").append(escapeHtml(treatment.getTreatmentSuggestion())).append("</p>");
            
            currentUser.addReport("Report generated for injury: " + selectedInjury.getType());
            accountManager.updateUser(currentUser);
            accountManager.saveAllUsers();
        }

        if (appointment != null) {
            reportContent.append("<h2 style='color: ").append(String.format("#%02x%02x%02x", 
                ACCENT_COLOR.getRed(), ACCENT_COLOR.getGreen(), ACCENT_COLOR.getBlue())).append(";'>Appointment Details</h2>");
            reportContent.append("<p><b>Doctor:</b> ").append(escapeHtml(appointment.getDoctorName())).append("<br>");
            reportContent.append("<b>Day:</b> ").append(appointment.getWeekday()).append("<br>");
            reportContent.append("<b>Time:</b> ").append(escapeHtml(appointment.getTime())).append("</p>");
            if (appointment.getAthleteDescription() != null && !appointment.getAthleteDescription().isEmpty()) {
                reportContent.append("<p><b>Additional Notes:</b> ").append(escapeHtml(appointment.getAthleteDescription())).append("</p>");
            }
        }

        reportContent.append("</body></html>");

        JLabel reportDetails = new JLabel(reportContent.toString());
        reportDetails.setVerticalAlignment(SwingConstants.TOP);
        reportDetails.setFont(LABEL_FONT);
        content.add(reportDetails);

        JButton backButton = createSecondaryButton("Back to Dashboard", e -> openUserDashboard());
        JPanel actions = createActionsPanel();
        actions.add(backButton);
        content.add(actions);

        swapContent(content);
    }
    
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }

    /**
     * Replace current content inside the root panel with the provided one.
     */
    private void swapContent(JPanel content) {
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

        rootPanel.removeAll();
        rootPanel.add(scrollPane, BorderLayout.CENTER);
        rootPanel.revalidate();
        rootPanel.repaint();
    }

    /**
     * Creates a vertically stacked panel with consistent spacing and background.
     */
    private JPanel createVerticalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PRIMARY_BG);
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));
        return panel;
    }

    /**
     * Creates a form panel with gentle padding and spacing.
     */
    private JPanel createFormPanel(String title) {
        JPanel panel = createVerticalPanel();
        panel.add(createTitleLabel(title));
        panel.add(Box.createVerticalStrut(12));
        return panel;
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        label.setFont(TITLE_FONT);
        label.setForeground(ACCENT_COLOR);
        return label;
    }

    private JLabel createHintLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(80, 80, 80));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(LABEL_FONT);
        return field;
    }

    private JTextField createTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(LABEL_FONT);
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(LABEL_FONT);
        return field;
    }

    /**
     * Wraps a label and its input control in a single row with padding.
     */
    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(PRIMARY_BG);
        container.setBorder(new EmptyBorder(4, 0, 4, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        gbc.gridx = 0;
        JLabel label = new JLabel(labelText + ":");
        label.setFont(LABEL_FONT);
        container.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        container.add(field, gbc);

        return container;
    }

    private JButton createPrimaryButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.WHITE);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        return button;
    }

    private JButton createSecondaryButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(ACCENT_COLOR);
        button.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        return button;
    }

    /**
     * Builds a horizontal container for action buttons with spacing.
     */
    private JPanel createActionsPanel() {
        JPanel actions = new JPanel();
        actions.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.setBackground(PRIMARY_BG);
        actions.setBorder(new EmptyBorder(12, 0, 0, 0));
        return actions;
    }
}
