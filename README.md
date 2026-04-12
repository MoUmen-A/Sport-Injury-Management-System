# Sports Injury Management System (Injury Assist)

A Java desktop application that helps athletes and patients manage injury information, book doctor appointments, and generate treatment-oriented medical reports.

## Overview
Injury Assist is built to improve early injury handling and follow-up care. The system guides the user through a clear workflow:
1. Authentication
2. Profile completion
3. Sport selection
4. Injury selection
5. Appointment booking
6. Report generation

This sequence reduces missing data and improves the quality of recommendations.

## Highlights
- Modern Swing application shell with:
	- Navbar
	- Sidebar navigation
	- Footer status bar with live clock
- Guided workflow with readiness score and next-step recommendation
- Business rule validation for:
	- Username and password quality
	- Profile fields (age/contact rules)
	- Booking prerequisites
- Appointment scheduling with slot-availability checks
- Auto-generated medical report combining patient, injury, appointment, and treatment data

## Core Features
- Account sign up and login
- Patient profile management
- Sport and injury classification flow
- Doctor appointment booking
- Treatment matching by injury type
- Medical report generation and persistence

## Tech Stack
- Language: Java
- UI: Java Swing
- Data Storage: Text file persistence (accounts.txt)
- Programming Model: Object-Oriented Design

## Project Structure
```text
FinalOOPproject/
|-- README.md
|-- accounts.txt
|-- bin/                        # compiled classes
|-- docs/
|   |-- Class diagramUML.png
|   |-- Introduction to the injury assist system.docx
|   `-- PROJECT_STRUCTURE.md
`-- src/
		|-- AccountManager.java
		|-- Main.java               # CLI entry point
		|-- MainGUI.java            # Swing GUI entry point
		|-- Appointment.java
		|-- BodyPart.java
		|-- Doctor.java
		|-- Injury.java
		|-- Patient.java
		|-- Person.java
		|-- Report.java
		|-- Sport.java
		|-- Treatment.java
		|-- Weekday.java
		`-- accounts.txt
```

## UML Diagram
Use the local diagram in the repository:

![UML Diagram](docs/Class%20diagramUML.png)

## Run the Project

### Prerequisites
- JDK 11 or newer (JDK 17 recommended)
- java and javac available in PATH

### Recommended (from project root)
```powershell
cd "d:\Gam3a\programming\Java\Java OOP\FinalOOPproject"
javac -d bin src\*.java
java -cp bin MainGUI
```

### Run CLI version
```powershell
cd "d:\Gam3a\programming\Java\Java OOP\FinalOOPproject"
javac -d bin src\*.java
java -cp bin Main
```

### Quick legacy run from src
```powershell
cd "d:\Gam3a\programming\Java\Java OOP\FinalOOPproject\src"
javac *.java
java MainGUI
```

## Build Notes
- Keep generated .class files in bin, not in src.
- The repository includes .gitignore rules to keep build artifacts out of version control.
- For consistent data behavior, run from project root when possible.

## Data Notes
- User records are stored in accounts.txt as CSV-like rows.
- Depending on the working directory, the app may use either:
	- accounts.txt (root)
	- src/accounts.txt (when running from src)

## Design and OOP Principles
- Encapsulation of domain state in classes such as Patient, Injury, and Appointment
- Separation of concerns between UI workflow and data model classes
- Static slot registry in appointment logic to prevent double booking
- Reusable validation logic in UI flow for safer operations

## Motivation
- For Doctors: Faster triage context and better-prepared visits
- For Patients/Athletes: Structured history, clearer recommendations, and better follow-up documentation

## Future Improvements
- Export report to PDF
- Persistent appointment database instead of in-memory slot map
- Package refactor into layered namespaces (ui, model, service, persistence)
- Unit tests for validation and scheduling rules
