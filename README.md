# A1-junzhouwang - Health Service Appointment System

PROG2004 Object Oriented Programming, Assessment 1.

This project is a small appointment system for a health service, written in Java.
Patients can book an appointment with a health professional for the current day,
and the clinic keeps every appointment in one place so it can filter, sort and
cancel them.

The required demonstration is scripted, so it needs no input: the `main` method
creates the objects, exercises every feature, and prints the results to the
console. An optional interactive session is added on top of the demonstration so
a real person can book and cancel appointments as well.

## Project structure

```text
A1-junzhouwang/
|-- .vscode/                     VS Code settings and the run configuration
|-- docs/
|   |-- Design-Decisions.md      Answers to the design considerations of Parts 1-6
|   `-- Video-Script.md          A five minute script for the OOP video
|-- src/
|   |-- AssignmentOne.java       Starts the program and runs the demonstration
|   |-- HealthProfessional.java  Abstract parent of every health professional
|   |-- GeneralPractitioner.java One specialty (Part 2)
|   |-- Surgeon.java             One specialty (Part 2)
|   |-- Dietitian.java           One specialty (Part 2)
|   |-- Nurse.java               One specialty (Part 2)
|   |-- Patient.java             The details of one patient
|   |-- Appointment.java         One appointment between a patient and a professional
|   |-- AppointmentManager.java  The collection of appointments and its rules
|   |-- ClinicSchedule.java      The fixed opening hours and the thirty minute slots
|   |-- RandomBookingGenerator.java  Random patients who fill free time slots
|   `-- HospitalBookingConsole.java  The optional interactive menu
|-- .wakatime-project            The project name that WakaTime records
|-- README.md
`-- A1-junzhouwang.zip           The file that is uploaded to MySCU
```

## How to run the program in VS Code

1. Open the folder `A1-junzhouwang` in VS Code with `File > Open Folder`.
2. Open `src/AssignmentOne.java`.
3. Press `F5`, or click the `Run` button above the `main` method.
4. The demonstration prints to the terminal and then waits for the optional
   interactive session. Type `9` and press `Enter` to leave.

The `.vscode/launch.json` file already points to the `AssignmentOne` class, so
`F5` works without any further setup.

## How to run the program in a terminal

```bat
javac -encoding UTF-8 -d bin src\*.java
java -cp bin AssignmentOne
```

The program needs Java 17 or newer. It was written and tested with Java 26.

## How the classes work together

| Class | Responsibility |
| --- | --- |
| `HealthProfessional` | Abstract class. Stores the identifier, the name and the working days, and asks every subclass for its type name, duty and fee. |
| `GeneralPractitioner`, `Surgeon`, `Dietitian`, `Nurse` | Four specialties. Each one adds its own fields, overrides the abstract methods, and reuses the parent details through `super.displayDetails()`. |
| `Patient` | Stores a checked patient name and a checked mobile phone number. |
| `Appointment` | Links one patient, one health professional and one time slot. Implements `Comparable` so the manager can sort appointments by time. |
| `AppointmentManager` | Holds the appointment list and the daily time slots. Adds, rejects, filters, sorts and cancels appointments, and returns a message for every action. |
| `ClinicSchedule` | Stores the opening time, the closing time and the slot length, and builds the fixed list of time slots. |
| `RandomBookingGenerator` | Creates random patients and books them into free slots, so the clinic looks busy. |
| `HospitalBookingConsole` | The optional interactive menu that reads choices from the keyboard. |
| `AssignmentOne` | Creates the objects, runs the scripted demonstration of Parts 3 to 6, and starts the console. |

## The demonstration

The `main` method prints the following sections:

1. Part 3 - the four health professionals and the second constructor.
2. Part 4 - two appointment objects, the ordering of two appointments, and the
   errors that appear when a detail is missing.
3. Part 5 - adding appointments, rejecting a double booking, rejecting a time
   outside the schedule, filtering by professional and by phone number, sorting
   all and filtered lists, and cancelling by professional and by phone number.
4. Part 6 - six random patients fill free slots, the whole list is printed again,
   and the free slots of one doctor are shown.

Every action prints a message that starts with `Success: ` or `Warning: `, so a
rejected booking is easy to see.

## The optional interactive session

After the demonstration the program starts a small menu. The options are:

1. Show every health professional.
2. Show every appointment of today.
3. Book an appointment (choose the professional, then the time slot, then type
   the patient name and the phone number).
4. Show the appointments of one health professional.
5. Show the appointments of one patient by phone number.
6. Cancel an appointment by health professional and time.
7. Cancel the appointments of one patient by phone number.
8. Show the free time slots of one health professional.
9. Leave the console.

The menu is an extra feature. The assessable demonstration does not need it, and
it finishes correctly even when no input is supplied.

## WakaTime

The WakaTime extension is installed in VS Code and the API key is stored in the
VS Code user settings. This project also has a `.wakatime-project` file, so every
heartbeat is recorded under the project name `A1-junzhouwang`.

Keep the folder open in VS Code while you read, edit and run the code, because
WakaTime only records time while the editor is open on these files. To create the
report that must be submitted, open the WakaTime dashboard, choose the project
`A1-junzhouwang`, set the date range to the assessment period, and export the
report as a PNG or a PDF file named `A1-junzhouwang-WakaTime.png`.

## GitHub

The project is a normal Git repository. The usual workflow is:

```bat
git status
git add .
git commit -m "Add the appointment manager and the cancellation rules"
git push
```

Write a short and clear message for every commit, and commit after each small
step, so the history shows how the project grew. A private repository is fine,
as long as the tutor and the unit assessor are invited as collaborators.

## Submission checklist

- `A1-junzhouwang.zip` with the whole project (the zip already exists in this
  folder, and `bin/` and the compiled class files are left out).
- The GitHub repository link in the submission comments.
- The WakaTime report, `A1-junzhouwang-WakaTime.png` or `.pdf`.
- The shareable OneDrive link to the five minute video.
- The declaration about the use of GenAI.
