# Video Script - The Four Pillars of OOP

The video must be at most five minutes. This script keeps the four parts short
and points at the exact file and method to show on screen. Read it once, then
talk in your own words; do not read the script word for word.

## Before recording

- Open `src/AssignmentOne.java` in VS Code and run the program once, so the
  console output is already in the terminal.
- Open the four files that are named below in separate tabs.
- Check that the microphone works and that the screen shows the code clearly.

## 0:00 - 0:30 Introduction

Say what the program does: it is a small appointment system for a health service.
A patient books an appointment with a health professional for the current day, and
the clinic can filter, sort and cancel appointments.

Show `AssignmentOne.main` and mention that the demonstration is scripted.

## 0:30 - 1:45 Pillar 1: Encapsulation

Open `HealthProfessional.java`.

- "Every field is private. Outside code can only read the values through getters."
- Show `getAvailableWorkingDays`, which returns a copy of the array, so the caller
  cannot change the real array.
- Show the constructor and the method `checkIdentifier`, which rejects anything
  that is not digits only. "An object is checked when it is created, so it can
  never be in an invalid state."
- Show `Patient.checkMobilePhoneNumber` as a second example.

## 1:45 - 2:45 Pillar 2: Inheritance

Open `HealthProfessional.java` and then `Surgeon.java`.

- "`Surgeon` extends `HealthProfessional`, so it inherits the identifier, the
  name, the working days and the methods."
- Show the `super(...)` call in the constructor.
- Show `displayDetails()` in `Surgeon`: it calls `super.displayDetails()` first and
  then adds its own two lines.
- Point out the four subclasses: `GeneralPractitioner`, `Surgeon`, `Dietitian`
  and `Nurse`.

## 2:45 - 3:45 Pillar 3: Polymorphism

Open `AssignmentOne.java` and show `createHealthProfessionalList` and
`demonstrateHealthProfessionalObjects`.

- "The four objects are stored in one `ArrayList<HealthProfessional>`."
- "The loop calls `toString()` but the object decides which version runs."
- Run the program and show that the same loop prints four different blocks.
- Show `describeProfessionalType()` in two subclasses to prove that the same call
  gives a different answer.

## 3:45 - 4:40 Pillar 4: Abstraction

Open `HealthProfessional.java`.

- "The class is abstract, because the clinic never has a health professional
  without a specialty."
- Show the three abstract methods: `describeProfessionalType`,
  `performProfessionalDuty` and `calculateConsultationFee`.
- "Every subclass has to provide them, so the parent class can describe a rule
  without knowing the details."
- Optionally show `ClinicSchedule`: it hides the opening time and the slot length
  behind `checkTimeIsOnSchedule`, so the rest of the program does not care how the
  schedule is built.

## 4:40 - 5:00 Closing

- "The system keeps the appointments in `AppointmentManager`, rejects a double
  booking, and returns a warning message when an action cannot be done."
- Mention that the interactive menu was added on top of the required
  demonstration.
- Thank the viewer and stop the recording.

## Reminders

- One minute per pillar is enough for the high distinction level, so do not spend
  too long on any single point.
- Say the name of each pillar clearly, then explain it in your own words, then
  point at the code, then say why you chose that design.
- Do not read the code line by line; explain the idea and show the part that
  proves it.
