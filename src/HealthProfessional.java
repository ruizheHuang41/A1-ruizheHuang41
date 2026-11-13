import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * This class stores the information that every health professional shares.
 * A health professional always has an identifier that stores digits only, a
 * full name, and a fixed set of working days. The class is abstract because the
 * clinic never employs a health professional who has no specialty, and every
 * specialty has to provide its own description, duty and consultation fee.
 *
 * @author Junzhou Wang
 * @version 1.0
 */
public abstract class HealthProfessional {

    // the identifier of the health professional, which stores digits only
    private String healthProfessionalIdentifier;

    // the full name of the health professional
    private String healthProfessionalName;

    // the days of the week on which the health professional works
    private DayOfWeek[] availableWorkingDays;

    /**
     * Creates a health professional with all of the shared information.
     *
     * @param healthProfessionalIdentifier the identifier, which must store digits only
     * @param healthProfessionalName the full name of the health professional
     * @param availableWorkingDays the days of the week on which the professional works
     */
    public HealthProfessional(String healthProfessionalIdentifier, String healthProfessionalName,
            DayOfWeek[] availableWorkingDays) {
        this.healthProfessionalIdentifier = checkIdentifier(healthProfessionalIdentifier);
        this.healthProfessionalName = checkName(healthProfessionalName);
        this.availableWorkingDays = checkWorkingDays(availableWorkingDays);
    }

    /**
     * Creates a health professional who works from Monday to Friday.
     * This second constructor is useful when the caller does not need to choose
     * the working days, because the parent class supplies a sensible default.
     *
     * @param healthProfessionalIdentifier the identifier, which must store digits only
     * @param healthProfessionalName the full name of the health professional
     */
    public HealthProfessional(String healthProfessionalIdentifier, String healthProfessionalName) {
        // the next line uses the main constructor and passes the default working days
        this(healthProfessionalIdentifier, healthProfessionalName, new DayOfWeek[] {
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY });
    }

    /**
     * Returns the identifier of the health professional.
     *
     * @return the identifier of the health professional
     */
    public String getHealthProfessionalIdentifier() {
        return healthProfessionalIdentifier;
    }

    /**
     * Returns the full name of the health professional.
     *
     * @return the full name of the health professional
     */
    public String getHealthProfessionalName() {
        return healthProfessionalName;
    }

    /**
     * Returns a copy of the working days, so outside code can read the days but
     * cannot change the array that this object stores.
     *
     * @return a copy of the working days of the health professional
     */
    public DayOfWeek[] getAvailableWorkingDays() {
        DayOfWeek[] copiedWorkingDays = new DayOfWeek[availableWorkingDays.length]; // a copy that the caller can keep
        for (int index = 0; index < availableWorkingDays.length; index++) {
            copiedWorkingDays[index] = availableWorkingDays[index];
        }
        return copiedWorkingDays;
    }

    /**
     * Checks whether the health professional works on a given day.
     *
     * @param dayToCheck the day of the week that should be checked
     * @return true when the professional works on that day, otherwise false
     */
    public boolean isAvailableOnDay(DayOfWeek dayToCheck) {
        boolean dayWasFound = false; // becomes true when the requested day is found
        for (int index = 0; index < availableWorkingDays.length; index++) {
            if (availableWorkingDays[index] == dayToCheck) {
                dayWasFound = true;
            }
        }
        return dayWasFound;
    }

    /**
     * Builds a readable list of the working days, for example
     * "Monday, Wednesday, Friday".
     *
     * @return the working days written as normal English words
     */
    public String formatWorkingDays() {
        String workingDayText = ""; // the readable text of all working days
        for (int index = 0; index < availableWorkingDays.length; index++) {
            workingDayText += availableWorkingDays[index].getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            if (index < availableWorkingDays.length - 1) {
                workingDayText += ", ";
            }
        }
        return workingDayText;
    }

    /**
     * Returns the name of the specialty of this health professional.
     * Every subclass must provide its own answer.
     *
     * @return the name of the specialty
     */
    public abstract String describeProfessionalType();

    /**
     * Returns a short description of the main duty of this health professional.
     * Every subclass must provide its own answer, which is the polymorphism
     * that the clinic uses later.
     *
     * @return a sentence that describes the main duty
     */
    public abstract String performProfessionalDuty();

    /**
     * Returns the consultation fee of this health professional in dollars.
     * Every subclass decides its own fee.
     *
     * @return the consultation fee
     */
    public abstract double calculateConsultationFee();

    /**
     * Builds the full details of the health professional, including the
     * information that is shared by every professional.
     *
     * @return the details of this health professional
     */
    public String displayDetails() {
        String details = ""; // the text that describes this health professional
        details += "Health professional identifier: " + healthProfessionalIdentifier + "\n";
        details += "Health professional name: " + healthProfessionalName + "\n";
        details += "Professional type: " + describeProfessionalType() + "\n";
        details += "Working days: " + formatWorkingDays() + "\n";
        details += "Consultation fee: " + String.format("%.2f", calculateConsultationFee()) + " dollars\n";
        details += "Main duty: " + performProfessionalDuty();
        return details;
    }

    /**
     * Returns the same text that displayDetails returns, so printing an object
     * with System.out.println shows every detail in a readable form.
     *
     * @return the details of this health professional
     */
    @Override
    public String toString() {
        return displayDetails();
    }

    /**
     * Two health professionals are equal when they share the same identifier,
     * because the identifier is the value that makes a professional unique.
     *
     * @param otherObject the object that this professional is compared with
     * @return true when both objects describe the same health professional
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if ((otherObject instanceof HealthProfessional) == false) {
            return false;
        }
        HealthProfessional otherHealthProfessional = (HealthProfessional) otherObject; // the object that is compared
        return healthProfessionalIdentifier.equals(otherHealthProfessional.getHealthProfessionalIdentifier());
    }

    /**
     * Returns a hash value that matches the equals method.
     *
     * @return the hash value of the identifier
     */
    @Override
    public int hashCode() {
        return healthProfessionalIdentifier.hashCode();
    }

    /**
     * Checks and cleans the identifier. The identifier must contain digits only,
     * so no letter or other symbol can ever reach the object.
     *
     * @param identifierToCheck the identifier that was given by the caller
     * @return the cleaned identifier
     */
    private static String checkIdentifier(String identifierToCheck) {
        if (identifierToCheck == null) {
            throw new IllegalArgumentException("The health professional identifier cannot be empty.");
        }
        String cleanedIdentifier = identifierToCheck.trim(); // the identifier after the spaces are removed
        if (cleanedIdentifier.length() == 0) {
            throw new IllegalArgumentException("The health professional identifier cannot be empty.");
        }
        for (int index = 0; index < cleanedIdentifier.length(); index++) {
            char currentCharacter = cleanedIdentifier.charAt(index); // one character of the identifier
            if (Character.isDigit(currentCharacter) == false) {
                throw new IllegalArgumentException("The health professional identifier must contain digits only.");
            }
        }
        return cleanedIdentifier;
    }

    /**
     * Checks and cleans the name of the health professional.
     *
     * @param nameToCheck the name that was given by the caller
     * @return the cleaned name
     */
    private static String checkName(String nameToCheck) {
        if (nameToCheck == null) {
            throw new IllegalArgumentException("The health professional name cannot be empty.");
        }
        String cleanedName = nameToCheck.trim(); // the name after the spaces are removed
        if (cleanedName.length() == 0) {
            throw new IllegalArgumentException("The health professional name cannot be empty.");
        }
        return cleanedName;
    }

    /**
     * Checks the working days and returns a safe copy of the array.
     * A health professional must work on at least one day, and the same day
     * cannot appear twice in the list.
     *
     * @param workingDaysToCheck the working days that were given by the caller
     * @return a copy of the working days that this object can own
     */
    private static DayOfWeek[] checkWorkingDays(DayOfWeek[] workingDaysToCheck) {
        if (workingDaysToCheck == null || workingDaysToCheck.length == 0) {
            throw new IllegalArgumentException("A health professional must work on at least one day.");
        }
        DayOfWeek[] copiedWorkingDays = new DayOfWeek[workingDaysToCheck.length]; // the copy that this object will store
        for (int index = 0; index < workingDaysToCheck.length; index++) {
            if (workingDaysToCheck[index] == null) {
                throw new IllegalArgumentException("A working day cannot be empty.");
            }
            copiedWorkingDays[index] = workingDaysToCheck[index];
            for (int earlierIndex = 0; earlierIndex < index; earlierIndex++) {
                if (copiedWorkingDays[earlierIndex] == workingDaysToCheck[index]) {
                    throw new IllegalArgumentException("A working day cannot appear twice.");
                }
            }
        }
        return copiedWorkingDays;
    }
}
