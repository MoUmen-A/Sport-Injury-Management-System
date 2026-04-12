import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainGUI {
    private static final Color APP_BG = new Color(245, 247, 251);
    private static final Color SURFACE = Color.WHITE;
    private static final Color NAV_BG = new Color(30, 41, 59);
    private static final Color SIDEBAR_BG = new Color(51, 65, 85);
    private static final Color ACCENT = new Color(14, 116, 144);
    private static final Color ACCENT_DARK = new Color(15, 23, 42);
    private static final Color MUTED_TEXT = new Color(71, 85, 105);

    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font SECTION_FONT = new Font("Segoe UI", Font.BOLD, 17);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);

    private final JFrame frame;
    private final JPanel rootPanel;
    private final JScrollPane contentScroll;
    private final JLabel navUserLabel;
    private final JLabel footerStatusLabel;
    private final JLabel footerClockLabel;

    private final JButton sidebarDashboardBtn;
    private final JButton sidebarDetailsBtn;
    private final JButton sidebarSportBtn;
    private final JButton sidebarInjuryBtn;
    private final JButton sidebarAppointmentBtn;
    private final JButton sidebarReportBtn;
    private final JButton sidebarLoginBtn;
    private final JButton sidebarSignupBtn;
    private final JButton sidebarLogoutBtn;

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
        frame.setSize(980, 660);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(860, 560));

        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(APP_BG);

        rootPanel.add(createNavbar(), BorderLayout.NORTH);

        JPanel sidebar = createSidebar();
        rootPanel.add(sidebar, BorderLayout.WEST);

        contentScroll = new JScrollPane();
        contentScroll.setBorder(new EmptyBorder(16, 16, 16, 16));
        contentScroll.getVerticalScrollBar().setUnitIncrement(12);
        contentScroll.getViewport().setBackground(APP_BG);
        rootPanel.add(contentScroll, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(SURFACE);
        footer.setBorder(new EmptyBorder(8, 16, 8, 16));
        footerStatusLabel = new JLabel("Ready");
        footerStatusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerStatusLabel.setForeground(MUTED_TEXT);
        footerClockLabel = new JLabel("", SwingConstants.RIGHT);
        footerClockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerClockLabel.setForeground(MUTED_TEXT);
        footer.add(footerStatusLabel, BorderLayout.WEST);
        footer.add(footerClockLabel, BorderLayout.EAST);
        rootPanel.add(footer, BorderLayout.SOUTH);

        frame.add(rootPanel);

        sidebarDashboardBtn = findSidebarButton(sidebar, "Dashboard");
        sidebarDetailsBtn = findSidebarButton(sidebar, "Profile Details");
        sidebarSportBtn = findSidebarButton(sidebar, "Select Sport");
        sidebarInjuryBtn = findSidebarButton(sidebar, "Select Injury");
        sidebarAppointmentBtn = findSidebarButton(sidebar, "Appointments");
        sidebarReportBtn = findSidebarButton(sidebar, "Generate Report");
        sidebarLoginBtn = findSidebarButton(sidebar, "Log In");
        sidebarSignupBtn = findSidebarButton(sidebar, "Sign Up");
        sidebarLogoutBtn = findSidebarButton(sidebar, "Log Out");

        navUserLabel = (JLabel) ((JPanel) ((BorderLayout) ((JPanel) rootPanel.getComponent(0)).getLayout()).getLayoutComponent(BorderLayout.EAST)).getComponent(0);

        bindSidebarActions();
        startClock();
        openLanding();

        frame.setVisible(true);
    }

    private JPanel createNavbar() {
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(NAV_BG);
        navbar.setBorder(new EmptyBorder(12, 16, 12, 16));

        JPanel brand = new JPanel();
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        brand.setOpaque(false);

        JLabel title = new JLabel("Sports Injury Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JLabel subtitle = new JLabel("Athlete-first care planning dashboard");
        subtitle.setForeground(new Color(203, 213, 225));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        brand.add(title);
        brand.add(subtitle);

        JPanel sessionInfo = new JPanel(new BorderLayout());
        sessionInfo.setOpaque(false);
        JLabel user = new JLabel("Guest", SwingConstants.RIGHT);
        user.setForeground(new Color(148, 163, 184));
        user.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sessionInfo.add(user, BorderLayout.CENTER);

        navbar.add(brand, BorderLayout.WEST);
        navbar.add(sessionInfo, BorderLayout.EAST);
        return navbar;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBorder(new EmptyBorder(16, 12, 16, 12));

        JLabel section = new JLabel("Navigation");
        section.setForeground(new Color(226, 232, 240));
        section.setFont(new Font("Segoe UI", Font.BOLD, 12));
        sidebar.add(section);
        sidebar.add(Box.createVerticalStrut(10));

        addSidebarButton(sidebar, "Dashboard");
        addSidebarButton(sidebar, "Profile Details");
        addSidebarButton(sidebar, "Select Sport");
        addSidebarButton(sidebar, "Select Injury");
        addSidebarButton(sidebar, "Appointments");
        addSidebarButton(sidebar, "Generate Report");

        sidebar.add(Box.createVerticalStrut(14));
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(100, 116, 139));
        sidebar.add(separator);
        sidebar.add(Box.createVerticalStrut(14));

        addSidebarButton(sidebar, "Log In");
        addSidebarButton(sidebar, "Sign Up");
        addSidebarButton(sidebar, "Log Out");
        return sidebar;
    }

    private void addSidebarButton(JPanel sidebar, String text) {
        JButton button = new JButton(text);
        button.setName(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(71, 85, 105));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 10, 10, 10));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(8));
    }

    private JButton findSidebarButton(JPanel sidebar, String name) {
        for (Component component : sidebar.getComponents()) {
            if (component instanceof JButton && name.equals(((JButton) component).getName())) {
                return (JButton) component;
            }
        }
        throw new IllegalStateException("Sidebar button not found: " + name);
    }

    private void bindSidebarActions() {
        sidebarDashboardBtn.addActionListener(e -> {
            if (requireLogin()) {
                openUserDashboard();
            }
        });
        sidebarDetailsBtn.addActionListener(e -> {
            if (requireLogin()) {
                openUserDetailsForm();
            }
        });
        sidebarSportBtn.addActionListener(e -> {
            if (requireLogin()) {
                openSportSelectionForm();
            }
        });
        sidebarInjuryBtn.addActionListener(e -> {
            if (requireLogin()) {
                openInjurySelectionForm();
            }
        });
        sidebarAppointmentBtn.addActionListener(e -> {
            if (requireLogin()) {
                openAppointmentForm();
            }
        });
        sidebarReportBtn.addActionListener(e -> {
            if (requireLogin()) {
                generateReport();
            }
        });
        sidebarLoginBtn.addActionListener(e -> openLoginForm());
        sidebarSignupBtn.addActionListener(e -> openSignUpForm());
        sidebarLogoutBtn.addActionListener(e -> logout());
    }

    private void startClock() {
        Timer timer = new Timer(1000, e -> {
            footerClockLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE dd MMM yyyy  hh:mm:ss a")));
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    private void openLanding() {
        JPanel content = createVerticalPanel();
        content.add(createTitleLabel("Athlete Care Console"));
        content.add(Box.createVerticalStrut(10));
        content.add(createHintLabel("Create an account or log in from the sidebar to start your recovery workflow."));
        content.add(Box.createVerticalStrut(18));
        content.add(createInfoCard(
            "What you can do here",
            "1) Complete your profile\n2) Choose your sport and injury\n3) Book an appointment\n4) Generate a treatment-oriented report"
        ));

        JPanel quickActions = createActionsPanel();
        quickActions.add(createPrimaryButton("Sign Up", e -> openSignUpForm()));
        quickActions.add(createSecondaryButton("Log In", e -> openLoginForm()));
        content.add(quickActions);

        swapContent(content, "Guest mode: authentication required for medical actions.");
        refreshShellState();
    }

    private void openSignUpForm() {
        JPanel content = createFormPanel("Create your account");

        JTextField usernameField = createTextField();
        JPasswordField passwordField = createPasswordField();
        content.add(createLabeledField("Username", usernameField));
        content.add(createLabeledField("Password", passwordField));
        content.add(createHintLabel("Username: letters/numbers/_ (3-20 chars). Password: at least 4 chars."));

        JPanel actions = createActionsPanel();
        actions.add(createPrimaryButton("Create Account", e -> {
            String username = usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword()).trim();

            if (!isValidUsername(username)) {
                showError("Invalid username. Use 3-20 chars: letters, numbers, underscore.");
                return;
            }
            if (password.length() < 4) {
                showError("Password must be at least 4 characters.");
                return;
            }
            if (accountManager.isUsernameTaken(username)) {
                showError("Username is already taken. Please choose another one.");
                return;
            }

            Patient user = new Patient(username, password);
            accountManager.saveUser(user);
            accountManager.saveAllUsers();
            JOptionPane.showMessageDialog(frame, "Account created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            openLoginForm();
        }));
        actions.add(createSecondaryButton("Back", e -> openLanding()));
        content.add(actions);

        swapContent(content, "Create your account to unlock all workflow steps.");
    }

    private void openLoginForm() {
        JPanel content = createFormPanel("Log in to your account");

        JTextField usernameField = createTextField();
        JPasswordField passwordField = createPasswordField();
        content.add(createLabeledField("Username", usernameField));
        content.add(createLabeledField("Password", passwordField));

        JPanel actions = createActionsPanel();
        actions.add(createPrimaryButton("Log In", e -> {
            String username = usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                showError("Both username and password are required.");
                return;
            }

            if (!accountManager.validateUser(username, password)) {
                showError("Invalid username or password.");
                return;
            }

            currentUser = accountManager.getUser(username);
            syncSessionFromUser();
            JOptionPane.showMessageDialog(frame, "Welcome back, " + username + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
            openUserDashboard();
        }));
        actions.add(createSecondaryButton("Back", e -> openLanding()));
        content.add(actions);

        swapContent(content, "Secure login active.");
    }

    private void openUserDashboard() {
        JPanel content = createVerticalPanel();

        content.add(createTitleLabel("Dashboard"));
        content.add(Box.createVerticalStrut(8));
        content.add(createHintLabel("Welcome, " + getDisplayName() + ". Follow the guided path to produce a complete medical report."));
        content.add(Box.createVerticalStrut(14));

        int readiness = calculateReadinessScore();
        content.add(createInfoCard("Workflow Readiness", "Current completion score: " + readiness + "%\nNext recommended step: " + getNextStep()));
        content.add(Box.createVerticalStrut(12));
        content.add(createStatusPanel());
        content.add(Box.createVerticalStrut(14));

        JPanel quick = createActionsPanel();
        quick.add(createPrimaryButton("Continue Next Step", e -> navigateToNextStep()));
        quick.add(createSecondaryButton("Generate Report", e -> generateReport()));
        content.add(quick);

        swapContent(content, "Dashboard loaded. Readiness score: " + readiness + "%");
    }

    private JPanel createStatusPanel() {
        JPanel panel = createInfoCard("Current Status", "");
        panel.remove(1);

        JPanel lines = new JPanel();
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));
        lines.setOpaque(false);

        lines.add(createHintLabel("Profile: " + (isProfileComplete() ? "Complete" : "Incomplete")));
        lines.add(createHintLabel("Sport: " + (selectedSport != null ? selectedSport.getName() : "Not selected")));
        lines.add(createHintLabel("Injury: " + (selectedInjury != null ? selectedInjury.getType() : "Not selected")));
        lines.add(createHintLabel("Appointment: " + (appointment != null ? appointment.getWeekday() + " - " + appointment.getTime() : "Not booked")));
        panel.add(lines, BorderLayout.CENTER);
        return panel;
    }

    private void openUserDetailsForm() {
        JPanel content = createFormPanel("Patient Profile");

        JTextField nameField = createTextField();
        JTextField ageField = createTextField(6);
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        JTextField contactField = createTextField(15);
        JTextField addressField = createTextField(30);

        if (currentUser != null && isProfileComplete()) {
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

        JPanel actions = createActionsPanel();
        actions.add(createPrimaryButton("Save Details", e -> {
            if (currentUser == null) {
                showError("Please log in first.");
                return;
            }

            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String contact = contactField.getText().trim();
            String address = addressField.getText().trim();
            boolean gender = "Male".equals(genderComboBox.getSelectedItem());

            if (name.length() < 3) {
                showError("Full name must be at least 3 characters.");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException ex) {
                showError("Age must be a number.");
                return;
            }

            if (age < 8 || age > 95) {
                showError("Age must be between 8 and 95.");
                return;
            }

            if (!contact.matches("\\d{7,15}")) {
                showError("Contact number must contain 7 to 15 digits.");
                return;
            }

            try {
                Patient updatedPatient = currentUser.updateDetails(name, age, gender, contact, address);
                accountManager.updateUser(updatedPatient);
                accountManager.saveAllUsers();
                currentUser = updatedPatient;
                JOptionPane.showMessageDialog(frame, "Profile saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                openUserDashboard();
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        }));
        actions.add(createSecondaryButton("Back", e -> openUserDashboard()));
        content.add(actions);

        swapContent(content, "Keep profile details accurate for better recommendation quality.");
    }

    private void openSportSelectionForm() {
        JPanel content = createFormPanel("Select Sport");
        SportsCollection sportsCollection = new SportsCollection();
        JComboBox<Sport> sportsComboBox = new JComboBox<>(sportsCollection.getSportsList().toArray(new Sport[0]));

        if (selectedSport != null) {
            sportsComboBox.setSelectedItem(selectedSport);
        }

        content.add(createLabeledField("Sport", sportsComboBox));

        JPanel actions = createActionsPanel();
        actions.add(createPrimaryButton("Save Sport", e -> {
            selectedSport = (Sport) sportsComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(frame, "Sport saved: " + (selectedSport != null ? selectedSport.getName() : "N/A"), "Saved", JOptionPane.INFORMATION_MESSAGE);
            openUserDashboard();
        }));
        actions.add(createSecondaryButton("Back", e -> openUserDashboard()));
        content.add(actions);

        swapContent(content, "Sport context helps tailor injury interpretation.");
    }

    private void openInjurySelectionForm() {
        if (selectedSport == null) {
            int option = JOptionPane.showConfirmDialog(frame,
                "Selecting a sport first improves injury context.\nWould you like to select sport now?",
                "Recommended Step",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                openSportSelectionForm();
            }
            return;
        }

        JPanel content = createFormPanel("Select Injury");

        JComboBox<BodyPart> bodyPartComboBox = new JComboBox<>(BodyPart.values());
        JComboBox<Injury> injuriesComboBox = new JComboBox<>();
        JTextArea injuryPreview = new JTextArea(4, 30);
        injuryPreview.setFont(LABEL_FONT);
        injuryPreview.setEditable(false);
        injuryPreview.setLineWrap(true);
        injuryPreview.setWrapStyleWord(true);
        injuryPreview.setBackground(new Color(248, 250, 252));
        injuryPreview.setBorder(new EmptyBorder(8, 8, 8, 8));

        ActionListener reloadInjuries = e -> {
            BodyPart part = (BodyPart) bodyPartComboBox.getSelectedItem();
            List<Injury> injuries = Injury.getInjuriesByBodyPart(part);
            injuriesComboBox.removeAllItems();
            for (Injury injury : injuries) {
                injuriesComboBox.addItem(injury);
            }
            if (injuriesComboBox.getItemCount() > 0) {
                injuriesComboBox.setSelectedIndex(0);
            }
        };

        injuriesComboBox.addActionListener(e -> {
            Injury injury = (Injury) injuriesComboBox.getSelectedItem();
            injuryPreview.setText(injury == null ? "" : injury.getAthleteDescription());
        });

        bodyPartComboBox.addActionListener(reloadInjuries);
        bodyPartComboBox.setSelectedIndex(0);
        reloadInjuries.actionPerformed(null);

        content.add(createLabeledField("Body Part", bodyPartComboBox));
        content.add(createLabeledField("Injury", injuriesComboBox));
        content.add(createLabeledField("Athlete View", new JScrollPane(injuryPreview)));

        JPanel actions = createActionsPanel();
        actions.add(createPrimaryButton("Save Injury", e -> {
            Injury chosen = (Injury) injuriesComboBox.getSelectedItem();
            if (chosen == null) {
                showError("Please choose an injury.");
                return;
            }

            if (alreadyHasInjury(chosen.getType())) {
                int choice = JOptionPane.showConfirmDialog(
                    frame,
                    "This injury already exists in your history. Save it again as a new episode?",
                    "Duplicate Injury",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                if (choice != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            selectedInjury = chosen;
            currentUser.addInjury(chosen);
            accountManager.updateUser(currentUser);
            accountManager.saveAllUsers();

            JOptionPane.showMessageDialog(frame, "Injury saved: " + chosen.getType(), "Saved", JOptionPane.INFORMATION_MESSAGE);
            openUserDashboard();
        }));
        actions.add(createSecondaryButton("Back", e -> openUserDashboard()));
        content.add(actions);

        swapContent(content, "Detailed injury context improves treatment recommendations.");
    }

    private void openAppointmentForm() {
        if (!isProfileComplete()) {
            JOptionPane.showMessageDialog(frame, "Complete your profile before booking an appointment.", "Incomplete Profile", JOptionPane.WARNING_MESSAGE);
            openUserDetailsForm();
            return;
        }

        if (selectedInjury == null) {
            JOptionPane.showMessageDialog(frame, "Select an injury before scheduling an appointment.", "Missing Injury", JOptionPane.WARNING_MESSAGE);
            openInjurySelectionForm();
            return;
        }

        JPanel content = createFormPanel("Schedule Appointment");

        JComboBox<String> doctorComboBox = new JComboBox<>(Appointment.DOCTORS);
        JComboBox<Weekday> dayComboBox = new JComboBox<>(Appointment.DAYS);
        JComboBox<String> timeComboBox = new JComboBox<>();
        JTextArea athleteDescriptionField = new JTextArea(4, 30);
        athleteDescriptionField.setFont(LABEL_FONT);
        athleteDescriptionField.setLineWrap(true);
        athleteDescriptionField.setWrapStyleWord(true);
        athleteDescriptionField.setText(selectedInjury.getAthleteDescription());

        final JButton[] scheduleButtonRef = new JButton[1];
        JButton scheduleButton = createPrimaryButton("Confirm Appointment", e -> {
            String doctor = (String) doctorComboBox.getSelectedItem();
            Weekday day = (Weekday) dayComboBox.getSelectedItem();
            String time = (String) timeComboBox.getSelectedItem();
            String athleteDescription = athleteDescriptionField.getText().trim();

            if (doctor == null || day == null || time == null) {
                showError("Please choose doctor, day, and available time.");
                return;
            }

            if (!Appointment.isSlotFree(doctor, day, time)) {
                showError("This slot just became unavailable. Please choose another time.");
                refreshAvailableTimes(doctorComboBox, dayComboBox, timeComboBox, scheduleButtonRef[0]);
                return;
            }

            appointment = new Appointment(day, time, doctor, currentUser, athleteDescription);
            Appointment.bookSlot(doctor, day, time);
            currentUser.addReservation(appointment);
            accountManager.updateUser(currentUser);
            accountManager.saveAllUsers();

            JOptionPane.showMessageDialog(frame,
                "Appointment confirmed.\nDoctor: " + doctor + "\nDay: " + day + "\nTime: " + time,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            openUserDashboard();
        });
        scheduleButtonRef[0] = scheduleButton;

        ActionListener refreshTimesListener = e -> refreshAvailableTimes(doctorComboBox, dayComboBox, timeComboBox, scheduleButton);
        doctorComboBox.addActionListener(refreshTimesListener);
        dayComboBox.addActionListener(refreshTimesListener);
        refreshAvailableTimes(doctorComboBox, dayComboBox, timeComboBox, scheduleButton);

        content.add(createLabeledField("Doctor", doctorComboBox));
        content.add(createLabeledField("Day", dayComboBox));
        content.add(createLabeledField("Available Time", timeComboBox));
        content.add(createLabeledField("Athlete Notes", new JScrollPane(athleteDescriptionField)));

        JPanel actions = createActionsPanel();
        actions.add(scheduleButton);
        actions.add(createSecondaryButton("Back", e -> openUserDashboard()));
        content.add(actions);

        swapContent(content, "Only free slots are shown to prevent double-booking.");
    }

    private void refreshAvailableTimes(
        JComboBox<String> doctorComboBox,
        JComboBox<Weekday> dayComboBox,
        JComboBox<String> timeComboBox,
        JButton scheduleButton
    ) {
        String doctor = (String) doctorComboBox.getSelectedItem();
        Weekday day = (Weekday) dayComboBox.getSelectedItem();

        timeComboBox.removeAllItems();
        if (doctor == null || day == null) {
            scheduleButton.setEnabled(false);
            return;
        }

        for (String time : Appointment.TIMES) {
            if (Appointment.isSlotFree(doctor, day, time)) {
                timeComboBox.addItem(time);
            }
        }

        boolean hasTimes = timeComboBox.getItemCount() > 0;
        scheduleButton.setEnabled(hasTimes);
        if (!hasTimes) {
            footerStatusLabel.setText("No available slots for selected doctor/day. Choose another option.");
        }
    }

    private void generateReport() {
        if (currentUser == null) {
            showError("Please log in first.");
            return;
        }

        if (!isProfileComplete()) {
            JOptionPane.showMessageDialog(frame, "Please complete your profile before generating report.", "Incomplete Profile", JOptionPane.WARNING_MESSAGE);
            openUserDetailsForm();
            return;
        }

        if (selectedInjury == null) {
            JOptionPane.showMessageDialog(frame, "Select injury first.", "Missing Injury", JOptionPane.WARNING_MESSAGE);
            openInjurySelectionForm();
            return;
        }

        if (appointment == null) {
            JOptionPane.showMessageDialog(frame, "Book appointment first.", "Missing Appointment", JOptionPane.WARNING_MESSAGE);
            openAppointmentForm();
            return;
        }

        JPanel content = createFormPanel("Medical Report");
        treatment = Treatment.getTreatment(selectedInjury.getType());

        String priority = selectedInjury.isMovable() ? "Medium" : "High";
        String treatmentText = treatment != null ? treatment.getTreatmentSuggestion() : "No treatment recommendation available.";

        StringBuilder html = new StringBuilder();
        html.append("<html><body style='font-family: Segoe UI; padding: 10px; line-height: 1.4;'>");
        html.append("<h2 style='color:#0f172a;'>Patient Overview</h2>");
        html.append("<p><b>Name:</b> ").append(escapeHtml(currentUser.getName())).append("<br>");
        html.append("<b>Age:</b> ").append(currentUser.getAge()).append("<br>");
        html.append("<b>Contact:</b> ").append(escapeHtml(currentUser.getContact_no())).append("</p>");

        html.append("<h2 style='color:#0f172a;'>Injury Context</h2>");
        html.append("<p><b>Sport:</b> ").append(escapeHtml(selectedSport != null ? selectedSport.getName() : "Not specified")).append("<br>");
        html.append("<b>Injury:</b> ").append(escapeHtml(selectedInjury.getType())).append("<br>");
        html.append("<b>Body Part:</b> ").append(escapeHtml(String.valueOf(selectedInjury.getBodyPart()))).append("<br>");
        html.append("<b>Severity Priority:</b> ").append(priority).append("<br>");
        html.append("<b>Athlete Description:</b> ").append(escapeHtml(selectedInjury.getAthleteDescription())).append("</p>");

        html.append("<h2 style='color:#0f172a;'>Appointment</h2>");
        html.append("<p><b>Doctor:</b> ").append(escapeHtml(appointment.getDoctorName())).append("<br>");
        html.append("<b>Day:</b> ").append(appointment.getWeekday()).append("<br>");
        html.append("<b>Time:</b> ").append(escapeHtml(appointment.getTime())).append("</p>");

        html.append("<h2 style='color:#0f172a;'>Recommendation</h2>");
        html.append("<p>").append(escapeHtml(treatmentText)).append("</p>");
        html.append("</body></html>");

        JLabel reportLabel = new JLabel(html.toString());
        reportLabel.setVerticalAlignment(SwingConstants.TOP);
        content.add(reportLabel);

        currentUser.addReport("Generated report for " + selectedInjury.getType() + " with " + appointment.getDoctorName());
        accountManager.updateUser(currentUser);
        accountManager.saveAllUsers();

        JPanel actions = createActionsPanel();
        actions.add(createSecondaryButton("Back to Dashboard", e -> openUserDashboard()));
        content.add(actions);

        swapContent(content, "Report generated successfully.");
    }

    private void logout() {
        if (currentUser == null) {
            openLanding();
            return;
        }

        int choice = JOptionPane.showConfirmDialog(frame,
            "Do you want to log out from current session?",
            "Confirm Log Out",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            currentUser = null;
            selectedSport = null;
            selectedInjury = null;
            appointment = null;
            treatment = null;
            openLanding();
        }
    }

    private boolean requireLogin() {
        if (currentUser != null) {
            return true;
        }
        JOptionPane.showMessageDialog(frame, "Please log in first.", "Authentication Required", JOptionPane.WARNING_MESSAGE);
        openLoginForm();
        return false;
    }

    private void syncSessionFromUser() {
        selectedInjury = null;
        appointment = null;

        if (currentUser != null) {
            List<Injury> injuries = currentUser.getInjuries();
            if (!injuries.isEmpty()) {
                selectedInjury = injuries.get(injuries.size() - 1);
            }

            List<Appointment> reservations = currentUser.getReservations();
            if (!reservations.isEmpty()) {
                appointment = reservations.get(reservations.size() - 1);
            }
        }
    }

    private boolean isProfileComplete() {
        return currentUser != null
            && currentUser.getName() != null
            && !currentUser.getName().trim().isEmpty()
            && !"New Patient".equals(currentUser.getName())
            && currentUser.getAge() > 0
            && currentUser.getContact_no() != null
            && !currentUser.getContact_no().trim().isEmpty()
            && currentUser.getAddress() != null
            && !currentUser.getAddress().trim().isEmpty();
    }

    private boolean alreadyHasInjury(String type) {
        if (currentUser == null || type == null) {
            return false;
        }

        for (Injury injury : currentUser.getInjuries()) {
            if (type.equalsIgnoreCase(injury.getType())) {
                return true;
            }
        }
        return false;
    }

    private int calculateReadinessScore() {
        int score = 0;
        if (isProfileComplete()) {
            score += 25;
        }
        if (selectedSport != null) {
            score += 25;
        }
        if (selectedInjury != null) {
            score += 25;
        }
        if (appointment != null) {
            score += 25;
        }
        return score;
    }

    private String getNextStep() {
        if (!isProfileComplete()) {
            return "Complete Profile Details";
        }
        if (selectedSport == null) {
            return "Select Sport";
        }
        if (selectedInjury == null) {
            return "Select Injury";
        }
        if (appointment == null) {
            return "Schedule Appointment";
        }
        return "Generate Final Report";
    }

    private void navigateToNextStep() {
        if (!isProfileComplete()) {
            openUserDetailsForm();
            return;
        }
        if (selectedSport == null) {
            openSportSelectionForm();
            return;
        }
        if (selectedInjury == null) {
            openInjurySelectionForm();
            return;
        }
        if (appointment == null) {
            openAppointmentForm();
            return;
        }
        generateReport();
    }

    private String getDisplayName() {
        if (currentUser == null) {
            return "Guest";
        }
        if (isProfileComplete()) {
            return currentUser.getName();
        }
        return currentUser.getUsername();
    }

    private boolean isValidUsername(String username) {
        return username != null && username.matches("[a-zA-Z0-9_]{3,20}");
    }

    private void refreshShellState() {
        boolean loggedIn = currentUser != null;

        sidebarDashboardBtn.setEnabled(loggedIn);
        sidebarDetailsBtn.setEnabled(loggedIn);
        sidebarSportBtn.setEnabled(loggedIn);
        sidebarInjuryBtn.setEnabled(loggedIn);
        sidebarAppointmentBtn.setEnabled(loggedIn);
        sidebarReportBtn.setEnabled(loggedIn);
        sidebarLogoutBtn.setEnabled(loggedIn);

        sidebarLoginBtn.setEnabled(!loggedIn);
        sidebarSignupBtn.setEnabled(!loggedIn);

        navUserLabel.setText(loggedIn ? "Session: " + getDisplayName() : "Session: Guest");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void swapContent(JPanel content, String footerMessage) {
        content.setBackground(APP_BG);
        content.setBorder(new EmptyBorder(4, 4, 4, 4));
        contentScroll.setViewportView(content);
        footerStatusLabel.setText(footerMessage);
        refreshShellState();
        rootPanel.revalidate();
        rootPanel.repaint();
    }

    private JPanel createVerticalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(APP_BG);
        return panel;
    }

    private JPanel createFormPanel(String title) {
        JPanel panel = createVerticalPanel();
        panel.add(createTitleLabel(title));
        panel.add(Box.createVerticalStrut(12));
        return panel;
    }

    private JPanel createInfoCard(String title, String body) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240)),
            new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(SECTION_FONT);
        titleLabel.setForeground(ACCENT_DARK);

        JTextArea bodyLabel = new JTextArea(body);
        bodyLabel.setEditable(false);
        bodyLabel.setOpaque(false);
        bodyLabel.setLineWrap(true);
        bodyLabel.setWrapStyleWord(true);
        bodyLabel.setFont(LABEL_FONT);
        bodyLabel.setForeground(MUTED_TEXT);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(bodyLabel, BorderLayout.CENTER);
        return card;
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        label.setFont(TITLE_FONT);
        label.setForeground(ACCENT_DARK);
        return label;
    }

    private JLabel createHintLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(MUTED_TEXT);
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

    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(APP_BG);
        container.setBorder(new EmptyBorder(4, 0, 4, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 0;

        gbc.gridx = 0;
        JLabel label = new JLabel(labelText + ":");
        label.setFont(LABEL_FONT);
        label.setForeground(new Color(30, 41, 59));
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
        button.setBackground(ACCENT);
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(8, 14, 8, 14));
        return button;
    }

    private JButton createSecondaryButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(ACCENT_DARK);
        button.setBorder(BorderFactory.createLineBorder(new Color(148, 163, 184)));
        return button;
    }

    private JPanel createActionsPanel() {
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.setBackground(APP_BG);
        actions.setBorder(new EmptyBorder(12, 0, 0, 0));
        return actions;
    }

    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
