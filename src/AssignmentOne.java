import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * This class starts the program and demonstrates the whole appointment system.
 * The main method first runs a scripted demonstration of Part 3, Part 4, Part 5
 * and Part 6, and it then offers an optional interactive booking session. The
 * demonstration prints its own results, so it can be marked without any input.
 *
 * @author Junzhou Wang
 * @version 1.0
 */
public class AssignmentOne {

    // the line that separates the sections of the output
    private static final String SECTION_SEPARATOR = "==================================================================";

    /**
     * Starts the scripted demonstration and then the interactive session.
     *
     * @param commandLineArguments the arguments of the command line, which this program does not use
     */
    public static void main(String[] commandLineArguments) {
        displaySectionHeading("PROG2004 Assessment 1 - Health Service Appointment System");
        System.out.println("This program demonstrates the appointment system of a small health service.");
        System.out.println("Every booking is made for the current day, which is " + LocalDate.now() + ".");
        System.out.println();

        ArrayList<HealthProfessional> healthProfessionalList = createHealthProfessionalList(); // the four professionals of the clinic
        AppointmentManager appointmentManager = new AppointmentManager(healthProfessionalList); // the manager that handles every appointment

        demonstrateHealthProfessionalObjects(healthProfessionalList);
        demonstrateAppointmentObjects(appointmentManager);
        demonstrateAppointmentManagement(appointmentManager);
        demonstrateCompleteAppointmentWorkflow(appointmentManager);
        startInteractiveBooking(appointmentManager);
    }

    /**
     * Creates the four health professionals of the clinic. Each object is a
     * different specialty, but all of them are stored through the parent type.
     *
     * @return the list of health professionals
     */
    private static ArrayList<HealthProfessional> createHealthProfessionalList() {
        ArrayList<HealthProfessional> healthProfessionalList = new ArrayList<HealthProfessional>(); // the list that will be returned

        GeneralPractitioner generalPractitioner = new GeneralPractitioner("101", "Alice Chen",
                new DayOfWeek[] { DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY },
                "Room 3", true); // the general practitioner of the clinic

        Surgeon surgeon = new Surgeon("202", "Ben Patel",
                new DayOfWeek[] { DayOfWeek.TUESDAY, DayOfWeek.THURSDAY },
                "Orthopaedic surgery", 12); // the surgeon of the clinic

        Dietitian dietitian = new Dietitian("303", "Chloe Nguyen",
                new DayOfWeek[] { DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY },
                "Diabetes nutrition", true); // the dietitian of the clinic

        Nurse nurse = new Nurse("404", "David Brooks",
                new DayOfWeek[] { DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY },
                "Ward B", "Morning shift"); // the nurse of the clinic

        healthProfessionalList.add(generalPractitioner);
        healthProfessionalList.add(surgeon);
        healthProfessionalList.add(dietitian);
        healthProfessionalList.add(nurse);
        return healthProfessionalList;
    }

    /**
     * Shows the health professional objects of Part 3 and the second constructor
     * of the parent class.
     *
     * @param healthProfessionalList the professionals of the clinic
     */
    private static void demonstrateHealthProfessionalObjects(
            ArrayList<HealthProfessional> healthProfessionalList) {
        displaySectionHeading("Part 3 - Working with health professional objects");
        System.out.println("The clinic keeps all four specialties in one ArrayList of HealthProfessional.");
        System.out.println("Each object prints its own details, because toString is overridden.");
        System.out.println();
        // index goes through every health professional of the clinic
        for (int index = 0; index < healthProfessionalList.size(); index++) {
            HealthProfessional currentProfessional = healthProfessionalList.get(index); // one professional from the clinic
            System.out.println((index + 1) + ") " + currentProfessional);
            System.out.println();
        }

        displaySubHeading("The second constructor of the parent class");
        HealthProfessional visitingNurse = new Nurse("505", "Fiona White"); // a nurse who only visits the clinic today
        System.out.println(visitingNurse);
        System.out.println();
        System.out.println("This nurse did not receive any working days, so the parent class used Monday to Friday.");
    }

    /**
     * Shows the appointment objects of Part 4, including the ordering of two
     * appointments and the error when a detail is missing.
     *
     * @param appointmentManager the manager that holds the professionals of the clinic
     */
    private static void demonstrateAppointmentObjects(AppointmentManager appointmentManager) {
        displaySectionHeading("Part 4 - Modelling an appointment");
        System.out.println("The fixed time slots of the clinic for today are:");
        appointmentManager.displayTimeSlots();

        Patient firstPatient = new Patient("Jack Taylor", "0466554433"); // the patient of the first example
        Patient secondPatient = new Patient("Grace Lee", "0499887766"); // the patient of the second example
        HealthProfessional generalPractitioner = appointmentManager.findHealthProfessionalByIdentifier("101"); // the general practitioner of the first example
        HealthProfessional surgeon = appointmentManager.findHealthProfessionalByIdentifier("202"); // the surgeon of the second example

        Appointment firstAppointment = new Appointment(firstPatient, generalPractitioner,
                LocalTime.of(10, 0)); // the appointment at ten o'clock
        Appointment secondAppointment = new Appointment(secondPatient, surgeon,
                LocalTime.of(13, 0)); // the appointment at one o'clock

        displaySubHeading("Two appointment objects");
        System.out.println(firstAppointment);
        System.out.println();
        System.out.println(secondAppointment);
        System.out.println();

        int comparisonResult = firstAppointment.compareTo(secondAppointment); // the result of the time comparison
        System.out.println("The first appointment starts before the second appointment: "
                + (comparisonResult < 0) + ".");
        System.out.println("These two objects are examples only, so they are not added to the appointment manager.");

        displaySubHeading("An appointment cannot be created with incomplete details");
        try {
            Appointment brokenAppointment = new Appointment(null, generalPractitioner,
                    LocalTime.of(11, 0)); // this line must fail, because the patient is missing
            System.out.println(brokenAppointment);
        } catch (IllegalArgumentException exception) {
            System.out.println("Warning: " + exception.getMessage());
        }

        try {
            Patient brokenPatient = new Patient("", "0411"); // this line must fail, because the name is empty
            System.out.println(brokenPatient);
        } catch (IllegalArgumentException exception) {
            System.out.println("Warning: " + exception.getMessage());
        }
    }

    /**
     * Shows the appointment management of Part 5: adding, rejecting, filtering,
     * sorting and cancelling appointments.
     *
     * @param appointmentManager the manager that holds every appointment
     */
    private static void demonstrateAppointmentManagement(AppointmentManager appointmentManager) {
        displaySectionHeading("Part 5 - Managing the collection of appointments");

        Patient emmaWilson = new Patient("Emma Wilson", "0411223344"); // the first patient who books
        Patient henryAdams = new Patient("Henry Adams", "0455667788"); // the second patient who books
        Patient isabelMoore = new Patient("Isabel Moore", "0433221100"); // the third patient who books
        Patient jackTaylor = new Patient("Jack Taylor", "0466554433"); // a patient whose booking will be rejected

        displaySubHeading("1. Adding appointments");
        System.out.println(appointmentManager.addAppointment(emmaWilson, "303", LocalTime.of(15, 30)));
        System.out.println(appointmentManager.addAppointment(emmaWilson, "101", LocalTime.of(9, 0)));
        System.out.println(appointmentManager.addAppointment(henryAdams, "202", LocalTime.of(14, 30)));
        System.out.println(appointmentManager.addAppointment(isabelMoore, "404", LocalTime.of(8, 30)));

        displaySubHeading("2. Bookings that must be rejected");
        System.out.println(appointmentManager.addAppointment(jackTaylor, "101", LocalTime.of(9, 0)));
        System.out.println(appointmentManager.addAppointment(jackTaylor, "101", LocalTime.of(7, 15)));
        System.out.println(appointmentManager.addAppointment(jackTaylor, "999", LocalTime.of(10, 0)));

        displaySubHeading("3. Filtering appointments by health professional");
        ArrayList<Appointment> generalPractitionerAppointments = appointmentManager
                .findAppointmentsByHealthProfessionalIdentifier("101"); // the appointments of the general practitioner
        appointmentManager.displayAppointments(generalPractitionerAppointments);

        displaySubHeading("4. Sorting a filtered list by time");
        ArrayList<Appointment> emmaAppointments = appointmentManager
                .findAppointmentsByPatientPhoneNumber("0411223344"); // the two appointments of Emma Wilson
        System.out.println("Appointments of Emma Wilson before sorting:");
        appointmentManager.displayAppointments(emmaAppointments);
        appointmentManager.sortAppointmentsByTime(emmaAppointments);
        System.out.println("Appointments of Emma Wilson after sorting:");
        appointmentManager.displayAppointments(emmaAppointments);

        displaySubHeading("5. Sorting every appointment of the day");
        appointmentManager.sortAppointmentsByTime();
        appointmentManager.displayAllAppointments();

        displaySubHeading("6. Cancelling appointments");
        System.out.println(appointmentManager.cancelAppointment("101", LocalTime.of(9, 0)));
        System.out.println(appointmentManager.cancelAppointment("101", LocalTime.of(9, 0)));
        System.out.println(appointmentManager.cancelAppointmentsByPatientPhoneNumber("0455667788"));
        System.out.println(appointmentManager.cancelAppointmentsByPatientPhoneNumber("0400000000"));
        System.out.println();
        appointmentManager.displayAllAppointments();
    }

    /**
     * Shows the complete workflow of Part 6: random patients fill some free time
     * slots, the whole list is printed again, and the free slots of one doctor
     * are shown.
     *
     * @param appointmentManager the manager that holds every appointment
     */
    private static void demonstrateCompleteAppointmentWorkflow(AppointmentManager appointmentManager) {
        displaySectionHeading("Part 6 - Working with appointments end to end");

        displaySubHeading("Random patients fill some free time slots");
        RandomBookingGenerator randomBookingGenerator = new RandomBookingGenerator(appointmentManager); // the generator of random bookings
        int createdCount = randomBookingGenerator.createRandomAppointments(6); // how many random appointments were created
        System.out.println();
        System.out.println(createdCount + " random appointments were created for today.");

        displaySubHeading("The complete appointment list after the random bookings");
        appointmentManager.displayAllAppointments();

        displaySubHeading("The free time slots of the general practitioner 101");
        ArrayList<LocalTime> freeSlotList = appointmentManager
                .getAvailableTimeSlotsForHealthProfessional("101"); // the slots that are still free for the general practitioner
        String freeSlotText = ""; // the text that lists those free slots
        for (int index = 0; index < freeSlotList.size(); index++) {
            freeSlotText += freeSlotList.get(index) + "   ";
        }
        System.out.println(freeSlotText);

        System.out.println();
        System.out.println("The clinic now has " + appointmentManager.getAppointmentCount()
                + " appointments for today.");
    }

    /**
     * Starts the optional interactive booking session.
     *
     * @param appointmentManager the manager that holds every appointment
     */
    private static void startInteractiveBooking(AppointmentManager appointmentManager) {
        displaySectionHeading("Optional interactive booking session");
        System.out.println("The scripted demonstration is finished.");
        System.out.println("You can now book or cancel appointments yourself, or type 9 to leave.");
        HospitalBookingConsole bookingConsole = new HospitalBookingConsole(appointmentManager); // the console that reads the keyboard
        bookingConsole.startBookingSession();
    }

    /**
     * Prints a section heading between two separator lines.
     *
     * @param headingText the heading of the section
     */
    private static void displaySectionHeading(String headingText) {
        System.out.println();
        System.out.println(SECTION_SEPARATOR);
        System.out.println(headingText);
        System.out.println(SECTION_SEPARATOR);
        System.out.println();
    }

    /**
     * Prints a small heading inside a section.
     *
     * @param subHeadingText the heading of the smaller part
     */
    private static void displaySubHeading(String subHeadingText) {
        System.out.println();
        System.out.println("--- " + subHeadingText + " ---");
    }
}
