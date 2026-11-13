import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class offers the optional interactive booking session of the program.
 * The person who runs the program can show the clinic, book a patient with a
 * chosen health professional and a chosen time slot, look for appointments, and
 * cancel appointments. The class only talks to the AppointmentManager, so the
 * business rules stay in one place.
 *
 * @author Junzhou Wang
 * @version 1.0
 */
public class HospitalBookingConsole {

    // how many time slots are printed on one line
    private static final int TIME_SLOTS_PER_OUTPUT_LINE = 4;

    // reads the text that the person types into the console
    private Scanner inputScanner;

    // the appointment manager that stores every booking
    private AppointmentManager appointmentManager;

    /**
     * Creates the console for one appointment manager.
     *
     * @param appointmentManagerToUse the manager that the console works with
     */
    public HospitalBookingConsole(AppointmentManager appointmentManagerToUse) {
        this.appointmentManager = appointmentManagerToUse;
        this.inputScanner = new Scanner(System.in);
    }

    /**
     * Starts the menu and keeps reading choices until the person leaves or the
     * input ends.
     */
    public void startBookingSession() {
        displayWelcomeMessage();
        boolean sessionIsRunning = true; // true while the person wants to stay in the menu
        while (sessionIsRunning == true) {
            displayMainMenu();
            String chosenOption = readText("Type the number of your choice: "); // the menu option that was typed
            if (chosenOption == null) {
                System.out.println("The input has ended, so the booking session stops.");
                sessionIsRunning = false;
            } else if (chosenOption.equals("1")) {
                displayAllHealthProfessionals();
            } else if (chosenOption.equals("2")) {
                displayAllAppointments();
            } else if (chosenOption.equals("3")) {
                bookNewAppointment();
            } else if (chosenOption.equals("4")) {
                displayAppointmentsByHealthProfessional();
            } else if (chosenOption.equals("5")) {
                displayAppointmentsByPhoneNumber();
            } else if (chosenOption.equals("6")) {
                cancelAppointmentByHealthProfessional();
            } else if (chosenOption.equals("7")) {
                cancelAppointmentsByPhoneNumber();
            } else if (chosenOption.equals("8")) {
                displayAvailableTimeSlots();
            } else if (chosenOption.equals("9")) {
                System.out.println("Thank you for using the booking system. Goodbye.");
                sessionIsRunning = false;
            } else {
                System.out.println("Warning: please type a number from 1 to 9.");
            }
            System.out.println();
        }
        inputScanner.close();
    }

    /**
     * Prints a short welcome text for the interactive session.
     */
    private void displayWelcomeMessage() {
        System.out.println();
        System.out.println("Welcome to the interactive booking console.");
        System.out.println("The clinic is open from " + ClinicSchedule.getOpeningTime() + " to "
                + ClinicSchedule.getClosingTime() + " today.");
    }

    /**
     * Prints every option of the main menu.
     */
    private void displayMainMenu() {
        System.out.println("------------------------------------------------------------------");
        System.out.println("1) Show every health professional of the clinic");
        System.out.println("2) Show every appointment of today");
        System.out.println("3) Book an appointment for a patient");
        System.out.println("4) Show the appointments of one health professional");
        System.out.println("5) Show the appointments of one patient");
        System.out.println("6) Cancel an appointment by health professional and time");
        System.out.println("7) Cancel the appointments of one patient by phone number");
        System.out.println("8) Show the free time slots of one health professional");
        System.out.println("9) Leave the booking console");
        System.out.println("------------------------------------------------------------------");
    }

    /**
     * Prints every health professional with a number, so the person can choose
     * one of them by typing that number.
     */
    private void displayAllHealthProfessionals() {
        ArrayList<HealthProfessional> professionalList = appointmentManager.getHealthProfessionalList(); // every professional of the clinic
        System.out.println("Health professionals of the clinic:");
        for (int index = 0; index < professionalList.size(); index++) {
            HealthProfessional currentProfessional = professionalList.get(index); // one professional from the clinic
            System.out.println((index + 1) + ") " + currentProfessional.getHealthProfessionalName()
                    + " - " + currentProfessional.describeProfessionalType()
                    + " - identifier " + currentProfessional.getHealthProfessionalIdentifier());
        }
    }

    /**
     * Prints every appointment of today in time order.
     */
    private void displayAllAppointments() {
        System.out.println();
        appointmentManager.displayAllAppointments();
    }

    /**
     * Books an appointment step by step: the professional, the time slot, the
     * patient name and the patient phone number are asked one after another.
     */
    private void bookNewAppointment() {
        System.out.println();
        System.out.println("Booking a new appointment");
        ArrayList<HealthProfessional> professionalList = appointmentManager.getHealthProfessionalList(); // every professional of the clinic
        displayAllHealthProfessionals();
        int chosenNumber = readNumber("Type the number of the health professional: ", 1, professionalList.size());
        if (chosenNumber == -1) {
            System.out.println("Warning: the booking was stopped because the choice was not valid.");
            return;
        }
        HealthProfessional chosenProfessional = professionalList.get(chosenNumber - 1); // the professional that was chosen
        String identifier = chosenProfessional.getHealthProfessionalIdentifier(); // the identifier of that professional
        ArrayList<LocalTime> freeSlotList = appointmentManager
                .getAvailableTimeSlotsForHealthProfessional(identifier); // the slots that are still free
        if (freeSlotList.size() == 0) {
            System.out.println("Warning: " + chosenProfessional.getHealthProfessionalName()
                    + " has no free time slot today.");
            return;
        }
        printTimeSlots(freeSlotList, "Free time slots of " + chosenProfessional.getHealthProfessionalName());
        int chosenSlotNumber = readNumber("Type the number of the time slot: ", 1, freeSlotList.size());
        if (chosenSlotNumber == -1) {
            System.out.println("Warning: the booking was stopped because the time slot was not valid.");
            return;
        }
        LocalTime chosenTime = freeSlotList.get(chosenSlotNumber - 1); // the time slot that was chosen
        String patientName = readText("Type the full name of the patient: ");
        String phoneNumber = readText("Type the mobile phone number of the patient: ");
        if (patientName == null || phoneNumber == null) {
            System.out.println("Warning: the booking was stopped because the patient details are missing.");
            return;
        }
        Patient newPatient = null; // the patient that will be booked
        try {
            newPatient = new Patient(patientName, phoneNumber);
        } catch (IllegalArgumentException exception) {
            System.out.println("Warning: " + exception.getMessage());
            return;
        }
        String bookingMessage = appointmentManager.addAppointment(newPatient, identifier, chosenTime); // the message of the manager
        System.out.println(bookingMessage);
    }

    /**
     * Shows the appointments of one health professional in time order.
     */
    private void displayAppointmentsByHealthProfessional() {
        System.out.println();
        ArrayList<HealthProfessional> professionalList = appointmentManager.getHealthProfessionalList(); // every professional of the clinic
        displayAllHealthProfessionals();
        int chosenNumber = readNumber("Type the number of the health professional: ", 1, professionalList.size());
        if (chosenNumber == -1) {
            System.out.println("Warning: the choice was not valid.");
            return;
        }
        HealthProfessional chosenProfessional = professionalList.get(chosenNumber - 1); // the professional that was chosen
        ArrayList<Appointment> foundAppointments = appointmentManager
                .findAppointmentsByHealthProfessionalIdentifier(chosenProfessional.getHealthProfessionalIdentifier()); // the appointments of that professional
        if (foundAppointments.size() == 0) {
            System.out.println("There are no appointments for " + chosenProfessional.getHealthProfessionalName() + ".");
            return;
        }
        ArrayList<Appointment> sortedAppointments = appointmentManager.sortAppointmentsByTime(foundAppointments); // the same appointments in time order
        System.out.println("Appointments of " + chosenProfessional.getHealthProfessionalName() + " in time order:");
        appointmentManager.displayAppointments(sortedAppointments);
    }

    /**
     * Shows the appointments of one patient, which are found with the phone
     * number of the patient.
     */
    private void displayAppointmentsByPhoneNumber() {
        System.out.println();
        String phoneNumber = readText("Type the mobile phone number of the patient: ");
        if (phoneNumber == null) {
            System.out.println("Warning: the phone number is missing.");
            return;
        }
        ArrayList<Appointment> foundAppointments = appointmentManager
                .findAppointmentsByPatientPhoneNumber(phoneNumber); // the appointments of that phone number
        if (foundAppointments.size() == 0) {
            System.out.println("There is no appointment for the mobile phone number " + phoneNumber + ".");
            return;
        }
        ArrayList<Appointment> sortedAppointments = appointmentManager.sortAppointmentsByTime(foundAppointments); // the same appointments in time order
        System.out.println("Appointments for the mobile phone number " + phoneNumber + " in time order:");
        appointmentManager.displayAppointments(sortedAppointments);
    }

    /**
     * Cancels one appointment after the professional and the time are chosen.
     */
    private void cancelAppointmentByHealthProfessional() {
        System.out.println();
        ArrayList<HealthProfessional> professionalList = appointmentManager.getHealthProfessionalList(); // every professional of the clinic
        displayAllHealthProfessionals();
        int chosenNumber = readNumber("Type the number of the health professional: ", 1, professionalList.size());
        if (chosenNumber == -1) {
            System.out.println("Warning: the choice was not valid.");
            return;
        }
        HealthProfessional chosenProfessional = professionalList.get(chosenNumber - 1); // the professional that was chosen
        String timeText = readText("Type the appointment time (for example 09:30): ");
        if (timeText == null) {
            System.out.println("Warning: the appointment time is missing.");
            return;
        }
        LocalTime chosenTime = null; // the time after the text is converted
        try {
            chosenTime = LocalTime.parse(timeText);
        } catch (DateTimeParseException exception) {
            System.out.println("Warning: " + timeText + " is not a time written as hour:minute.");
            return;
        }
        String cancelMessage = appointmentManager.cancelAppointment(
                chosenProfessional.getHealthProfessionalIdentifier(), chosenTime); // the message of the manager
        System.out.println(cancelMessage);
    }

    /**
     * Cancels every appointment of one patient after the phone number is typed.
     */
    private void cancelAppointmentsByPhoneNumber() {
        System.out.println();
        String phoneNumber = readText("Type the mobile phone number of the patient: ");
        if (phoneNumber == null) {
            System.out.println("Warning: the phone number is missing.");
            return;
        }
        String cancelMessage = appointmentManager.cancelAppointmentsByPatientPhoneNumber(phoneNumber); // the message of the manager
        System.out.println(cancelMessage);
    }

    /**
     * Shows the time slots that are still free for one health professional.
     */
    private void displayAvailableTimeSlots() {
        System.out.println();
        ArrayList<HealthProfessional> professionalList = appointmentManager.getHealthProfessionalList(); // every professional of the clinic
        displayAllHealthProfessionals();
        int chosenNumber = readNumber("Type the number of the health professional: ", 1, professionalList.size());
        if (chosenNumber == -1) {
            System.out.println("Warning: the choice was not valid.");
            return;
        }
        HealthProfessional chosenProfessional = professionalList.get(chosenNumber - 1); // the professional that was chosen
        ArrayList<LocalTime> freeSlotList = appointmentManager
                .getAvailableTimeSlotsForHealthProfessional(chosenProfessional.getHealthProfessionalIdentifier()); // the slots that are still free
        if (freeSlotList.size() == 0) {
            System.out.println(chosenProfessional.getHealthProfessionalName() + " has no free time slot today.");
            return;
        }
        printTimeSlots(freeSlotList, "Free time slots of " + chosenProfessional.getHealthProfessionalName());
        System.out.println("There are " + freeSlotList.size() + " free time slots.");
    }

    /**
     * Prints a numbered list of time slots.
     *
     * @param timeSlotList the slots that should be printed
     * @param headingText the heading of the list
     */
    private void printTimeSlots(ArrayList<LocalTime> timeSlotList, String headingText) {
        System.out.println(headingText + ":");
        String lineText = ""; // the text of one output line
        for (int index = 0; index < timeSlotList.size(); index++) {
            lineText += "[" + (index + 1) + "] " + timeSlotList.get(index) + "   ";
            if ((index + 1) % TIME_SLOTS_PER_OUTPUT_LINE == 0) {
                System.out.println(lineText);
                lineText = "";
            }
        }
        if (lineText.length() > 0) {
            System.out.println(lineText);
        }
    }

    /**
     * Reads one line of text. The method returns null when the input has ended,
     * so the menu can stop instead of waiting for ever.
     *
     * @param prompt the text that is shown before the input is read
     * @return the text that was typed, or null when the input has ended
     */
    private String readText(String prompt) {
        System.out.print(prompt);
        if (inputScanner.hasNextLine() == false) {
            return null;
        }
        return inputScanner.nextLine().trim();
    }

    /**
     * Reads one whole number inside a range.
     *
     * @param prompt the text that is shown before the input is read
     * @param smallestNumber the smallest number that is accepted
     * @param largestNumber the largest number that is accepted
     * @return the number that was typed, or -1 when the input was not valid
     */
    private int readNumber(String prompt, int smallestNumber, int largestNumber) {
        String typedText = readText(prompt); // the text that the person typed
        if (typedText == null) {
            return -1;
        }
        try {
            int chosenNumber = Integer.parseInt(typedText); // the number after the conversion
            if (chosenNumber < smallestNumber || chosenNumber > largestNumber) {
                return -1;
            }
            return chosenNumber;
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}
