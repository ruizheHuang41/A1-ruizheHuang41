import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is the central place where every appointment of the clinic is kept.
 * The class also keeps the health professionals of the clinic and the fixed time
 * slots of the current day, so every booking, filtering, sorting and cancelling
 * action can be checked before the appointment list is changed. Every action
 * returns a message, and the message starts with "Success: " or "Warning: ".
 *
 * @author Junzhou Wang
 * @version 1.0
 */
public class AppointmentManager {

    // the text that every successful action message starts with
    private static final String SUCCESS_PREFIX = "Success: ";

    // the text that every rejected action message starts with
    private static final String WARNING_PREFIX = "Warning: ";

    // how many time slots are printed on one line
    private static final int SLOTS_PER_OUTPUT_LINE = 6;

    // all appointments that are booked for the current day
    private ArrayList<Appointment> appointmentList;

    // all health professionals who work in the clinic
    private ArrayList<HealthProfessional> healthProfessionalList;

    // the fixed time slots of one clinic day, for example 08:00, 08:30, 09:00
    private ArrayList<LocalTime> dailyTimeSlots;

    /**
     * Creates the manager and copies the health professionals into its own list.
     *
     * @param healthProfessionalListToStore the health professionals who work in the clinic
     */
    public AppointmentManager(ArrayList<HealthProfessional> healthProfessionalListToStore) {
        this.healthProfessionalList = new ArrayList<HealthProfessional>(); // the clinic owns this copy of the list
        for (int index = 0; index < healthProfessionalListToStore.size(); index++) {
            this.healthProfessionalList.add(healthProfessionalListToStore.get(index));
        }
        this.appointmentList = new ArrayList<Appointment>(); // the clinic starts with no appointments
        this.dailyTimeSlots = ClinicSchedule.createDailyTimeSlots(); // the fixed slots of one clinic day
    }

    /**
     * Returns the number of appointments that are currently booked.
     *
     * @return the number of appointments
     */
    public int getAppointmentCount() {
        return appointmentList.size();
    }

    /**
     * Returns a copy of the appointment list, so outside code cannot change the
     * list that the manager owns.
     *
     * @return a copy of the appointment list
     */
    public ArrayList<Appointment> getAppointmentList() {
        ArrayList<Appointment> copiedList = new ArrayList<Appointment>(); // the copy that the caller receives
        for (int index = 0; index < appointmentList.size(); index++) {
            copiedList.add(appointmentList.get(index));
        }
        return copiedList;
    }

    /**
     * Returns a copy of the health professional list.
     *
     * @return a copy of the health professional list
     */
    public ArrayList<HealthProfessional> getHealthProfessionalList() {
        ArrayList<HealthProfessional> copiedList = new ArrayList<HealthProfessional>(); // the copy that the caller receives
        for (int index = 0; index < healthProfessionalList.size(); index++) {
            copiedList.add(healthProfessionalList.get(index));
        }
        return copiedList;
    }

    /**
     * Returns a copy of the fixed time slots of one clinic day.
     *
     * @return a copy of the daily time slots
     */
    public ArrayList<LocalTime> getDailyTimeSlots() {
        ArrayList<LocalTime> copiedList = new ArrayList<LocalTime>(); // the copy that the caller receives
        for (int index = 0; index < dailyTimeSlots.size(); index++) {
            copiedList.add(dailyTimeSlots.get(index));
        }
        return copiedList;
    }

    /**
     * Finds a health professional by the identifier.
     *
     * @param healthProfessionalIdentifier the identifier that is searched for
     * @return the health professional with that identifier, or null when nobody matches
     */
    public HealthProfessional findHealthProfessionalByIdentifier(String healthProfessionalIdentifier) {
        for (int index = 0; index < healthProfessionalList.size(); index++) {
            HealthProfessional currentProfessional = healthProfessionalList.get(index); // one professional from the clinic
            if (currentProfessional.getHealthProfessionalIdentifier().equals(healthProfessionalIdentifier)) {
                return currentProfessional;
            }
        }
        return null;
    }

    /**
     * Books an appointment after every rule of the clinic is checked. The method
     * rejects a booking when information is missing, when the health professional
     * does not work in the clinic, when the time is not a clinic slot, or when the
     * health professional is already booked at that time.
     *
     * @param patient the patient who books the appointment
     * @param healthProfessionalIdentifier the identifier of the chosen professional
     * @param appointmentTime the time slot of the appointment
     * @return a message that starts with "Success: " or "Warning: "
     */
    public String addAppointment(Patient patient, String healthProfessionalIdentifier, LocalTime appointmentTime) {
        if (patient == null) {
            return WARNING_PREFIX + "the patient information is missing.";
        }
        if (appointmentTime == null) {
            return WARNING_PREFIX + "the appointment time is missing.";
        }
        HealthProfessional chosenProfessional = findHealthProfessionalByIdentifier(healthProfessionalIdentifier); // the professional that the patient wants to see
        if (chosenProfessional == null) {
            return WARNING_PREFIX + "there is no health professional with the identifier "
                    + healthProfessionalIdentifier + ".";
        }
        if (ClinicSchedule.checkTimeIsOnSchedule(appointmentTime) == false) {
            return WARNING_PREFIX + appointmentTime + " is not a clinic time slot. The clinic runs from "
                    + ClinicSchedule.getOpeningTime() + " to " + ClinicSchedule.getClosingTime()
                    + " in thirty minute intervals.";
        }
        if (isHealthProfessionalBookedAtTime(healthProfessionalIdentifier, appointmentTime) == true) {
            return WARNING_PREFIX + chosenProfessional.getHealthProfessionalName()
                    + " is already booked at " + appointmentTime + ".";
        }
        Appointment newAppointment = null; // the appointment that will be added to the list
        try {
            newAppointment = new Appointment(patient, chosenProfessional, appointmentTime);
        } catch (IllegalArgumentException exception) {
            return WARNING_PREFIX + exception.getMessage();
        }
        appointmentList.add(newAppointment);
        return SUCCESS_PREFIX + "the appointment is booked for " + patient.getPatientName() + " with "
                + chosenProfessional.getHealthProfessionalName() + " at " + appointmentTime + ".";
    }

    /**
     * Checks whether a health professional already has an appointment at a time.
     *
     * @param healthProfessionalIdentifier the identifier of the professional
     * @param appointmentTime the time that is checked
     * @return true when the professional is already booked at that time
     */
    public boolean isHealthProfessionalBookedAtTime(String healthProfessionalIdentifier,
            LocalTime appointmentTime) {
        HealthProfessional chosenProfessional = findHealthProfessionalByIdentifier(healthProfessionalIdentifier); // the professional that is checked
        if (chosenProfessional == null || appointmentTime == null) {
            return false;
        }
        for (int index = 0; index < appointmentList.size(); index++) {
            Appointment currentAppointment = appointmentList.get(index); // one appointment from the list
            boolean sameProfessional = currentAppointment.getAppointmentHealthProfessional().equals(chosenProfessional);
            boolean sameTime = currentAppointment.getAppointmentTime().equals(appointmentTime);
            if (sameProfessional == true && sameTime == true) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds every appointment of one health professional.
     *
     * @param healthProfessionalIdentifier the identifier that is searched for
     * @return a list of appointments, which is empty when nothing matches
     */
    public ArrayList<Appointment> findAppointmentsByHealthProfessionalIdentifier(
            String healthProfessionalIdentifier) {
        ArrayList<Appointment> foundAppointments = new ArrayList<Appointment>(); // the appointments that match
        for (int index = 0; index < appointmentList.size(); index++) {
            Appointment currentAppointment = appointmentList.get(index); // one appointment from the list
            String currentIdentifier = currentAppointment.getAppointmentHealthProfessional()
                    .getHealthProfessionalIdentifier(); // the identifier of the professional of this appointment
            if (currentIdentifier.equals(healthProfessionalIdentifier)) {
                foundAppointments.add(currentAppointment);
            }
        }
        return foundAppointments;
    }

    /**
     * Finds every appointment of one patient, which is done with the mobile phone
     * number because two patients may have the same name.
     *
     * @param mobilePhoneNumber the phone number that is searched for
     * @return a list of appointments, which is empty when nothing matches
     */
    public ArrayList<Appointment> findAppointmentsByPatientPhoneNumber(String mobilePhoneNumber) {
        ArrayList<Appointment> foundAppointments = new ArrayList<Appointment>(); // the appointments that match
        if (mobilePhoneNumber == null) {
            return foundAppointments;
        }
        for (int index = 0; index < appointmentList.size(); index++) {
            Appointment currentAppointment = appointmentList.get(index); // one appointment from the list
            String currentPhoneNumber = currentAppointment.getAppointmentPatient().getMobilePhoneNumber(); // the phone number of this appointment
            if (currentPhoneNumber.equals(mobilePhoneNumber.trim())) {
                foundAppointments.add(currentAppointment);
            }
        }
        return foundAppointments;
    }

    /**
     * Sorts the appointment list of the manager from the earliest time to the
     * latest time. The sort uses the compareTo method of the Appointment class.
     */
    public void sortAppointmentsByTime() {
        Collections.sort(appointmentList);
    }

    /**
     * Sorts a filtered list of appointments, for example the appointments of one
     * health professional, from the earliest time to the latest time.
     *
     * @param appointmentListToSort the list that should be sorted
     * @return the same list after it was sorted
     */
    public ArrayList<Appointment> sortAppointmentsByTime(ArrayList<Appointment> appointmentListToSort) {
        Collections.sort(appointmentListToSort);
        return appointmentListToSort;
    }

    /**
     * Builds a sorted copy of all appointments, so the manager can show them in
     * time order without changing the order of the real list.
     *
     * @return a sorted copy of every appointment
     */
    public ArrayList<Appointment> getAppointmentsSortedByTime() {
        ArrayList<Appointment> sortedAppointments = new ArrayList<Appointment>(); // the copy that will be sorted
        for (int index = 0; index < appointmentList.size(); index++) {
            sortedAppointments.add(appointmentList.get(index));
        }
        Collections.sort(sortedAppointments);
        return sortedAppointments;
    }

    /**
     * Prints every appointment of the day in time order.
     */
    public void displayAllAppointments() {
        System.out.println("All appointments for today (" + appointmentList.size() + " in total), in time order:");
        displayAppointments(getAppointmentsSortedByTime());
    }

    /**
     * Prints a given list of appointments. The list is printed in the order that
     * the caller supplied, so the caller can decide whether to sort it first.
     *
     * @param appointmentListToDisplay the appointments that should be printed
     */
    public void displayAppointments(ArrayList<Appointment> appointmentListToDisplay) {
        if (appointmentListToDisplay.size() == 0) {
            System.out.println("There are no appointments to display.");
            return;
        }
        for (int index = 0; index < appointmentListToDisplay.size(); index++) {
            System.out.println((index + 1) + ". " + appointmentListToDisplay.get(index));
            System.out.println();
        }
    }

    /**
     * Prints every fixed time slot of the clinic day in a compact form.
     */
    public void displayTimeSlots() {
        String slotText = ""; // the text that lists every time slot
        for (int index = 0; index < dailyTimeSlots.size(); index++) {
            slotText += dailyTimeSlots.get(index);
            if (index < dailyTimeSlots.size() - 1) {
                slotText += "   ";
            }
            if ((index + 1) % SLOTS_PER_OUTPUT_LINE == 0) {
                slotText += "\n";
            }
        }
        System.out.println(slotText);
    }

    /**
     * Cancels the appointment of one health professional at one time.
     *
     * @param healthProfessionalIdentifier the identifier of the professional
     * @param appointmentTime the time of the appointment that should be cancelled
     * @return a message that starts with "Success: " or "Warning: "
     */
    public String cancelAppointment(String healthProfessionalIdentifier, LocalTime appointmentTime) {
        HealthProfessional chosenProfessional = findHealthProfessionalByIdentifier(healthProfessionalIdentifier); // the professional from the request
        if (chosenProfessional == null) {
            return WARNING_PREFIX + "there is no health professional with the identifier "
                    + healthProfessionalIdentifier + ".";
        }
        if (appointmentTime == null) {
            return WARNING_PREFIX + "the appointment time is missing.";
        }
        for (int index = 0; index < appointmentList.size(); index++) {
            Appointment currentAppointment = appointmentList.get(index); // one appointment from the list
            boolean sameProfessional = currentAppointment.getAppointmentHealthProfessional().equals(chosenProfessional);
            boolean sameTime = currentAppointment.getAppointmentTime().equals(appointmentTime);
            if (sameProfessional == true && sameTime == true) {
                appointmentList.remove(index);
                return SUCCESS_PREFIX + "the appointment at " + appointmentTime + " with "
                        + chosenProfessional.getHealthProfessionalName() + " is cancelled.";
            }
        }
        return WARNING_PREFIX + "there is no appointment with " + chosenProfessional.getHealthProfessionalName()
                + " at " + appointmentTime + ".";
    }

    /**
     * Cancels every appointment that belongs to one mobile phone number.
     *
     * @param mobilePhoneNumber the phone number of the patient
     * @return a message that starts with "Success: " or "Warning: "
     */
    public String cancelAppointmentsByPatientPhoneNumber(String mobilePhoneNumber) {
        if (mobilePhoneNumber == null || mobilePhoneNumber.trim().length() == 0) {
            return WARNING_PREFIX + "the mobile phone number is missing.";
        }
        String cleanedPhoneNumber = mobilePhoneNumber.trim(); // the phone number without spaces
        int cancelledCount = 0; // how many appointments were removed
        for (int index = appointmentList.size() - 1; index >= 0; index--) {
            Appointment currentAppointment = appointmentList.get(index); // one appointment from the list
            String currentPhoneNumber = currentAppointment.getAppointmentPatient().getMobilePhoneNumber(); // the phone number of this appointment
            if (currentPhoneNumber.equals(cleanedPhoneNumber)) {
                appointmentList.remove(index);
                cancelledCount++;
            }
        }
        if (cancelledCount == 0) {
            return WARNING_PREFIX + "there is no appointment for the mobile phone number "
                    + cleanedPhoneNumber + ".";
        }
        return SUCCESS_PREFIX + cancelledCount + " appointment(s) for the mobile phone number "
                + cleanedPhoneNumber + " are cancelled.";
    }

    /**
     * Builds the list of time slots that are still free for one professional.
     *
     * @param healthProfessionalIdentifier the identifier of the professional
     * @return the free time slots, which is an empty list when the day is full
     */
    public ArrayList<LocalTime> getAvailableTimeSlotsForHealthProfessional(
            String healthProfessionalIdentifier) {
        ArrayList<LocalTime> availableTimeSlots = new ArrayList<LocalTime>(); // the slots that are still free
        for (int index = 0; index < dailyTimeSlots.size(); index++) {
            LocalTime currentTime = dailyTimeSlots.get(index); // one slot of the clinic day
            if (isHealthProfessionalBookedAtTime(healthProfessionalIdentifier, currentTime) == false) {
                availableTimeSlots.add(currentTime);
            }
        }
        return availableTimeSlots;
    }
}
