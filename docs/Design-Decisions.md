# Design Decisions

This document answers the design considerations of the brief, part by part, and
points to the class or the method that implements each decision.

## Part 1 - Modelling a health professional

**What information is common to every health professional?**

The identifier (digits only), the full name, and the fixed set of working days.
These three values are stored in `HealthProfessional`, because they are true for
a general practitioner, a surgeon, a dietitian and a nurse alike.

**How is the fixed collection of working days stored?**

The working days are stored in a `DayOfWeek[]` array. An array fits a fixed set
of values of the same type, and `DayOfWeek` already knows the seven day names.
The array is copied inside the constructor (`checkWorkingDays`) and copied again
in `getAvailableWorkingDays`, so outside code can read the days but cannot change
the array that the object owns.

**How is controlled access provided?**

All fields are `private`, and the class only offers getters. Every value is
checked in the constructor: the identifier must contain digits only, the name
cannot be empty, and the working days must contain at least one day without
duplicates. A bad value throws an `IllegalArgumentException` immediately, so an
object can never exist in an invalid state.

**How does printing an object show every detail?**

`displayDetails()` builds one readable block of text, and `toString()` returns
exactly the same text. Java chooses the most specific `toString()` at run time,
so `System.out.println(doctorA)` prints the details of the real subtype.

**Is a second constructor worthwhile?**

Yes. `HealthProfessional(String, String)` supplies the working days Monday to
Friday. Most professionals work those days, so the caller does not have to write
the array again, and every subclass can call it with `super(identifier, name)`.

## Part 2 - Specialising health professionals

**What relationship lets the common code be reused?**

Inheritance. `GeneralPractitioner`, `Surgeon`, `Dietitian` and `Nurse` all extend
`HealthProfessional`, so they inherit the fields and the shared methods, and they
only add what is unique to them: a room and a patient answer for the general
practitioner, a specialty and years of experience for the surgeon, a diet focus
for the dietitian, and a ward and a shift for the nurse.

**What is unique to each type?**

The extra data (see above), the type name from `describeProfessionalType()`, the
duty from `performProfessionalDuty()`, and the fee from
`calculateConsultationFee()`.

**Is a second constructor worthwhile here?**

Yes, in the same way as in Part 1. The two argument constructor of every subclass
calls `super(identifier, name)` for the default working days and fills the extra
fields with sensible default values.

**How is the shared and the specific information set up correctly?**

The subclass constructor calls `super(...)` first, which runs the checks of the
parent class. Only then does the subclass check its own extra values. The
`displayDetails()` method of every subclass starts with `super.displayDetails()`
and appends its own lines, so no detail is repeated and no detail is lost.

**How do the types behave when they are treated through the same general type?**

`AssignmentOne.createHealthProfessionalList()` stores the four objects in one
`ArrayList<HealthProfessional>`. The loop only knows the parent type, but each
object answers with its own type, duty and fee. That is polymorphism, and it means
one loop can handle every specialty.

**Should a plain health professional exist?**

No. The class is `abstract`, so it cannot be instantiated, and every subclass must
implement the three abstract methods. This stops a professional without a
specialty from being created and forces every new specialty to provide its own
behaviour.

## Part 3 - Working with health professional objects

The four objects are created in `createHealthProfessionalList()` and printed in
one loop in `demonstrateHealthProfessionalObjects()`. Adding a fifth specialty
would only mean writing one more class and adding one more object to the list;
the loop would not change at all.

## Part 4 - Modelling an appointment

**Should patient be its own class?**

Yes. A patient has its own data and its own validation rules, and one patient can
hold more than one appointment, so a `Patient` object is separate from an
`Appointment` object.

**What does an appointment hold?**

The patient, the health professional, the time slot, and the date. The date is
filled in with `LocalDate.now()` inside the constructor, because bookings are only
made for the current day.

**How does one appointment work with any type of professional?**

The field is declared as `HealthProfessional`, so the constructor accepts any
subtype. The appointment does not need to know which specialty it holds.

**How is an invalid appointment prevented?**

The constructor checks that the patient, the professional and the time are not
null, and that the time is one of the fixed clinic slots. Any problem throws an
`IllegalArgumentException`, which the manager catches and turns into a warning
message.

**How can appointments be ordered?**

`Appointment` implements `Comparable<Appointment>`. `compareTo` compares the
`LocalTime` values first, and uses the identifiers of the professionals to break a
tie, so `Collections.sort` can order a whole list from the earliest time to the
latest time. `LocalTime` is used instead of a plain text field, because it
compares correctly and prints as `09:30`.

## Part 5 - Managing the collection of appointments

**Which data structure, and why?**

An `ArrayList<Appointment>`. The number of appointments changes during the day,
the list must keep the order in which bookings arrive, and the price of searching
a list of a few dozen appointments is not a problem. An array would need a fixed
size, and a `HashMap` would lose the natural order of the bookings.

**How does adding an appointment handle any type of professional?**

`addAppointment` takes the identifier of the professional, finds the object in the
clinic list, and passes it to the `Appointment` constructor. The parameter type is
the general `HealthProfessional`, so any specialty works.

**What happens when a cancellation does not match an appointment?**

The method returns a warning message and changes nothing. Nothing is thrown at
the user, because a missing appointment is a normal situation.

**What feedback does every operation give?**

Every method returns a `String` that starts with `Success: ` or `Warning: `, and
the demonstration prints that text. The constant prefixes `SUCCESS_PREFIX` and
`WARNING_PREFIX` keep the wording the same everywhere.

**How is the fixed daily schedule represented?**

`ClinicSchedule` keeps the opening time, the closing time and the slot length in
one class, and `createDailyTimeSlots()` builds the list from them. If the clinic
changes its hours, only these three constants change, and every other class keeps
working, because the manager asks `ClinicSchedule.checkTimeIsOnSchedule` instead
of hard coding times.

**When are two health professionals the same?**

When their identifiers are equal. `HealthProfessional.equals` compares the
identifiers, and `hashCode` is based on the same value, so two objects that
describe the same professional are equal. The manager uses this `equals` inside
`isHealthProfessionalBookedAtTime` and `cancelAppointment`.

**How are appointments shown in time order?**

`getAppointmentsSortedByTime()` copies the list and sorts the copy, and
`sortAppointmentsByTime()` sorts the real list. A filtered list can be sorted with
the second overload of `sortAppointmentsByTime`. Two appointments at the same
time are ordered by the identifiers of their professionals, so the output is the
same on every run.

## Part 6 - Working with appointments

`AssignmentOne` runs the whole workflow in four methods:

- `demonstrateHealthProfessionalObjects` for Part 3.
- `demonstrateAppointmentObjects` for Part 4, including the invalid appointment
  and the invalid patient.
- `demonstrateAppointmentManagement` for Part 5, with successful bookings,
  rejected bookings, filtering, sorting and both kinds of cancellation.
- `demonstrateCompleteAppointmentWorkflow` for Part 6, where random patients fill
  free slots and the final list is printed again.

Both the normal cases and the error cases are shown, and filtering and cancelling
are demonstrated with both kinds of input: the identifier of a professional and
the phone number of a patient.
