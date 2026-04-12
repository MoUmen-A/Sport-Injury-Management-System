# Sports Injury Management System

A Java OOP project for managing sports injuries, patient profiles, appointments, and treatment recommendations.

This project provides two interfaces:
- CLI workflow via `Main`
- Swing GUI workflow via `MainGUI`

## Table of Contents
- [Overview](#overview)
- [Core Features](#core-features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [How to Run](#how-to-run)
- [Build and Clean](#build-and-clean)
- [Data Storage](#data-storage)
- [Documentation](#documentation)
- [Known Notes](#known-notes)
- [Contributing](#contributing)

## Overview
The system helps an athlete/patient:
- Create an account or log in
- Complete personal profile details
- Select sport and injury details
- Schedule doctor appointments
- Generate injury/treatment reports

The codebase follows object-oriented design with separate classes for domain entities and workflow control.

## Core Features
- Authentication and account persistence
- Patient profile management
- Sport and injury selection flows
- Doctor appointment scheduling with slot checks
- Treatment recommendation lookup
- Medical report generation
- GUI and CLI support in the same project

## Tech Stack
- Language: Java
- UI: Java Swing (for desktop GUI)
- Persistence: Plain text file (`accounts.txt`)

## Project Structure
```text
FinalOOPproject/
|-- README.md
|-- accounts.txt                  # Root-level account data (used when running from project root)
|-- bin/                          # Compiled class output (recommended)
|-- docs/
|   |-- Class diagramUML.png
|   |-- Introduction to the injury assist system.docx
|   `-- PROJECT_STRUCTURE.md
`-- src/
    |-- AccountManager.java
    |-- Main.java                 # CLI entry point
    |-- MainGUI.java              # GUI entry point
    |-- Appointment.java
    |-- Patient.java
    |-- Person.java
    |-- Injury.java
    |-- Treatment.java
    |-- Report.java
    |-- Doctor.java
    |-- Sport.java
    |-- BodyPart.java
    |-- Weekday.java
    |-- accounts.txt              # Legacy data file if running with cwd=src
    `-- com/
```

## How to Run

### Prerequisites
- JDK 17+ installed (JDK 11+ usually works for this project)
- `java` and `javac` available in your `PATH`

### Option 1: Run GUI (recommended)
From project root:

```powershell
cd "d:\Gam3a\programming\Java\Java OOP\FinalOOPproject"
javac -d bin src\*.java
java -cp bin MainGUI
```

### Option 2: Run CLI
From project root:

```powershell
cd "d:\Gam3a\programming\Java\Java OOP\FinalOOPproject"
javac -d bin src\*.java
java -cp bin Main
```

### Quick run from `src` (legacy style)
```powershell
cd "d:\Gam3a\programming\Java\Java OOP\FinalOOPproject\src"
javac *.java
java MainGUI
```

## Build and Clean

### Rebuild to `bin`
```powershell
cd "d:\Gam3a\programming\Java\Java OOP\FinalOOPproject"
Remove-Item -Recurse -Force bin\* -ErrorAction SilentlyContinue
javac -d bin src\*.java
```

### Keep source folder clean
Do not commit `.class` files inside `src`. The repository includes `.gitignore` rules to prevent this.

## Data Storage
- Accounts are stored in `accounts.txt` as comma-separated values.
- Depending on working directory, app may read/write:
  - `accounts.txt` (project root), or
  - `src/accounts.txt` (if you run from `src`)

For consistency, prefer running from project root using the `bin` output workflow.

## Documentation
Additional project documentation is available in:
- `docs/Class diagramUML.png`
- `docs/Introduction to the injury assist system.docx`
- `docs/PROJECT_STRUCTURE.md`

## Known Notes
- GUI depends on desktop environment support (Swing).
- Appointment slots are managed in memory during runtime.
- Data format in `accounts.txt` supports both old (username/password) and extended user profile format.

## Contributing
1. Create a feature branch.
2. Keep source code in `src` and build output in `bin`.
3. Run both CLI and GUI paths before submitting.
4. Update documentation when changing behavior or structure.
