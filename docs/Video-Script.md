# Video Script - Four Pillars of OOP

A five minute video for PROG2004 Assessment 1.
Every section tells you which file to open, which lines to show, and the exact
words to say. The whole speech is about 600 words, which is about four and a
half minutes at a normal speaking speed.

## Before you record

1. Open these files as tabs, in this order:
   `AssignmentOne.java`, `HealthProfessional.java`, `Surgeon.java`,
   `ClinicSchedule.java`, `AppointmentManager.java`.
2. Run the program once and leave the output in the terminal.
3. In the terminal, scroll to the top of the output, so the "Part 3" heading is
   visible when you need it.
4. Zoom the editor with `Ctrl` and `+` two times, so the code is readable in the
   recording.
5. Jump to a line with `Ctrl + G` and type the line number.
6. Do one practice run without recording. Then record in one take if you can.

## Timing table

| Time | Pillar | File | Lines to show |
| --- | --- | --- | --- |
| 0:00 - 0:30 | Introduction | AssignmentOne.java | 25 - 30 |
| 0:30 - 1:25 | Encapsulation | HealthProfessional.java | 17 - 21, then 76 - 82, then 206 - 222 |
| 1:25 - 2:15 | Inheritance | Surgeon.java | 11, then 26 - 38, then 101 - 106 |
| 2:15 - 3:10 | Polymorphism | AssignmentOne.java | 47 - 78, then 87 - 91, then terminal |
| 3:10 - 4:00 | Abstraction | HealthProfessional.java | 15, then 123, 132, 140, then ClinicSchedule.java 16 - 20 |
| 4:00 - 4:35 | The booking rule | AppointmentManager.java | 120 - 140, then terminal |
| 4:35 - 5:00 | Closing | terminal | the final list |

---

## 0:00 - 0:30 Introduction

SHOW ON SCREEN: `AssignmentOne.java`, lines 25 to 30, the main method.

SAY:

> Hello, my name is Ruizhe Huang, and this is my assessment one for PROG2004.
> The program is a health service appointment system written in Java. A patient
> books an appointment with a health professional for today, and the clinic can
> filter, sort and cancel appointments. In this video I will explain the four
> pillars of object-oriented programming and show where each one is used in my
> code.

---

## 0:30 - 1:25 Encapsulation

SHOW ON SCREEN: `HealthProfessional.java`, lines 17 to 21, the three private
fields. Then jump to line 76, the getter that returns a copy. Then jump to line
206, the identifier check.

SAY:

> The first pillar is encapsulation. Encapsulation means the data inside an
> object is hidden, and outside code can only reach it through methods that keep
> it valid. Here, all three fields are private, so no other class can change them
> directly. The getter for the working days returns a copy of the array, not the
> array itself, so the caller cannot change the real data behind the object's
> back. And the constructor calls checkIdentifier, which rejects anything that is
> not digits only. I used encapsulation because an appointment system must never
> hold a broken identifier, and this design makes that impossible.

---

## 1:25 - 2:15 Inheritance

SHOW ON SCREEN: `Surgeon.java`, line 11, the class declaration. Then lines 26 to
38, the constructor with the super call. Then lines 101 to 106, the display
method that calls super.

SAY:

> The second pillar is inheritance. Inheritance lets a class reuse the code of a
> parent class. Surgeon extends HealthProfessional, so it inherits the identifier,
> the name, the working days, the getters and the display logic, without copying
> any of them. The constructor calls super first, so the parent class checks the
> shared values before the surgeon adds its own. And displayDetails starts with
> super.displayDetails, then adds the surgical specialty and the years of
> experience. I used inheritance because all four specialties share the same core
> data, and repeating it in every class would be hard to maintain.
> GeneralPractitioner, Dietitian and Nurse use the same pattern.

---

## 2:15 - 3:10 Polymorphism

SHOW ON SCREEN: `AssignmentOne.java`, lines 47 to 78, the method that creates the
four objects. Then lines 87 to 91, the loop. Then switch to the terminal and
scroll to the heading "Part 3 - Working with health professional objects".

SAY:

> The third pillar is polymorphism. Polymorphism means one reference type can
> hold objects of different subclasses, and each object answers in its own way.
> In createHealthProfessionalList I put a general practitioner, a surgeon, a
> dietitian and a nurse into one ArrayList of HealthProfessional. The loop only
> knows the parent type, but it calls toString, and Java runs the version of the
> real object. That is why the same loop prints four different blocks of details.
> I used polymorphism because the clinic can add a new specialty later, and this
> loop will not need to change.

---

## 3:10 - 4:00 Abstraction

SHOW ON SCREEN: `HealthProfessional.java`, line 15, the abstract class. Then
lines 123, 132 and 140, the three abstract methods. Then `ClinicSchedule.java`,
lines 16 to 20, the schedule constants.

SAY:

> The fourth pillar is abstraction. Abstraction means I describe what something
> must do, without saying how it does it. HealthProfessional is abstract, because
> the clinic never has a health professional with no specialty. It declares three
> abstract methods: the type name, the main duty and the consultation fee. Every
> subclass has to implement them, so the parent can rely on those behaviours
> without knowing the details. I also used abstraction in ClinicSchedule, which
> hides the opening time, the closing time and the slot length behind one check,
> so no other class hard codes a time.

---

## 4:00 - 4:35 The booking rule

SHOW ON SCREEN: `AppointmentManager.java`, lines 120 to 140, especially the
check at line 137. Then switch to the terminal and scroll to "Part 5 - Managing
the collection of appointments", the part called "2. Bookings that must be
rejected".

SAY:

> To show that the design works, here is the booking rule. addAppointment checks
> the professional, the time slot, and then asks whether that professional is
> already booked. If they are, it returns a warning and the list does not change.
> Here is the same code running: a second booking at nine o'clock is rejected
> with a warning. Cancelling works the same way, and it also returns a warning
> when no appointment matches.

---

## 4:35 - 5:00 Closing

SHOW ON SCREEN: the terminal, the end of the appointment list from Part 6.

SAY:

> So the four pillars work together: encapsulation protects the data,
> inheritance removes duplication, polymorphism lets one loop handle four
> specialties, and abstraction defines the contract. Thank you for watching.

---

## If the video is too long

Cut in this order, until you are under five minutes:

1. The last two sentences of the inheritance section.
2. The ClinicSchedule part of the abstraction section.
3. The cancelling sentence of the booking rule section.

## What the marker is listening for

- The pillar is named clearly.
- The concept is explained in your own words, not read from the code.
- You point at the exact place in the code.
- You say why you chose that design.

## Common mistakes to avoid

- Do not read the code line by line. Point at it and explain the idea.
- Do not say "this is encapsulation" and then only show a private field. Say what
  problem it solves.
- Do not record with the terminal covering the code. Keep the code visible.
- Do not go over five minutes. The limit is strict.
