import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class creates random patients and books them into free time slots, so the
 * clinic looks busy when the program starts. A random booking is only made when
 * the chosen time slot of the chosen health professional is still free, so the
 * generator never breaks the rule against double booking.
 *
 * @author Junzhou Wang
 * @version 1.0
 */
public class RandomBookingGenerator {

    // the first names that are used to build the names of random patients
    private static final String[] FIRST_NAME_LIST = { "Amelia", "Benjamin", "Chloe", "Daniel", "Ella",
            "Felix", "Grace", "Henry", "Isla", "James" };

    // the last names that are used to build the names of random patients
    private static final String[] LAST_NAME_LIST = { "Adams", "Brooks", "Clark", "Diaz", "Evans",
            "Foster", "Green", "Harris" };

    // the number that the random generator works with when it builds a phone number
    private static final int LARGEST_PHONE_NUMBER_PART = 100000000;

    // how many tries are allowed for every random appointment
    private static final int ATTEMPTS_PER_APPOINTMENT = 20;

    // the random number generator, which uses a fixed seed so the demonstration
    // prints the same result every time it is started
    private Random randomGenerator;

    // the manager that receives the random appointments
    private AppointmentManager appointmentManager;

    // the phone numbers that are already used, so random patients stay different
    private ArrayList<String> usedPhoneNumbers;

    /**
     * Creates the generator for one appointment manager.
     *
     * @param appointmentManagerToUse the manager that receives the random bookings
     */
    public RandomBookingGenerator(AppointmentManager appointmentManagerToUse) {
        this.appointmentManager = appointmentManagerToUse;
        this.randomGenerator = new Random(2026);
        this.usedPhoneNumbers = new ArrayList<String>();
    }

    /**
     * Creates a number of random appointments. Every try picks a random health
     * professional and a random time slot. When the slot is already booked, the
     * try is skipped and another slot is chosen.
     *
     * @param numberOfAppointments how many appointments should be created
     * @return how many random appointments were really created
     */
    public int createRandomAppointments(int numberOfAppointments) {
        int createdCount = 0; // how many random appointments were booked
        int attemptCount = 0; // how many times a free slot was searched for
        int maximumAttemptCount = numberOfAppointments * ATTEMPTS_PER_APPOINTMENT; // the safety limit of the loop
        while (createdCount < numberOfAppointments && attemptCount < maximumAttemptCount) {
            attemptCount++;
            HealthProfessional randomProfessional = pickRandomHealthProfessional(); // the professional of this try
            LocalTime randomTime = pickRandomTimeSlot(); // the time slot of this try
            String identifier = randomProfessional.getHealthProfessionalIdentifier(); // the identifier of that professional
            if (appointmentManager.isHealthProfessionalBookedAtTime(identifier, randomTime) == true) {
                continue;
            }
            Patient randomPatient = createRandomPatient(); // the patient of this try
            String bookingMessage = appointmentManager.addAppointment(randomPatient, identifier, randomTime); // the message of the manager
            System.out.println("  " + bookingMessage);
            createdCount++;
        }
        return createdCount;
    }

    /**
     * Builds one random patient with a full name and a new phone number.
     *
     * @return a new random patient
     */
    private Patient createRandomPatient() {
        int firstNameIndex = randomGenerator.nextInt(FIRST_NAME_LIST.length); // the position of the first name
        int lastNameIndex = randomGenerator.nextInt(LAST_NAME_LIST.length); // the position of the last name
        String firstName = FIRST_NAME_LIST[firstNameIndex]; // the first name of the patient
        String lastName = LAST_NAME_LIST[lastNameIndex]; // the last name of the patient
        String fullName = firstName + " " + lastName; // the full name of the patient
        String phoneNumber = createRandomPhoneNumber(); // the phone number of the patient
        return new Patient(fullName, phoneNumber);
    }

    /**
     * Builds a phone number that was not used before.
     *
     * @return a new mobile phone number with ten digits
     */
    private String createRandomPhoneNumber() {
        String phoneNumber = ""; // the phone number that will be returned
        boolean numberIsNew = false; // true when the number was not used before
        while (numberIsNew == false) {
            int randomPart = randomGenerator.nextInt(LARGEST_PHONE_NUMBER_PART); // the random part of the number
            phoneNumber = String.format("04%08d", randomPart);
            if (usedPhoneNumbers.contains(phoneNumber) == false) {
                usedPhoneNumbers.add(phoneNumber);
                numberIsNew = true;
            }
        }
        return phoneNumber;
    }

    /**
     * Picks one health professional of the clinic at random.
     *
     * @return the chosen health professional
     */
    private HealthProfessional pickRandomHealthProfessional() {
        ArrayList<HealthProfessional> professionalList = appointmentManager.getHealthProfessionalList(); // every professional of the clinic
        int randomIndex = randomGenerator.nextInt(professionalList.size()); // the position of the chosen professional
        return professionalList.get(randomIndex);
    }

    /**
     * Picks one fixed time slot of the clinic day at random.
     *
     * @return the chosen time slot
     */
    private LocalTime pickRandomTimeSlot() {
        ArrayList<LocalTime> timeSlotList = appointmentManager.getDailyTimeSlots(); // every slot of the clinic day
        int randomIndex = randomGenerator.nextInt(timeSlotList.size()); // the position of the chosen slot
        return timeSlotList.get(randomIndex);
    }
}
