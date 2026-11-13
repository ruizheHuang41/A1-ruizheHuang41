/**
 * This class stores the details of one patient who books an appointment.
 * The name and the mobile phone number are checked inside the constructor, so a
 * Patient object can never hold empty or strange looking information.
 *
 * @author Junzhou Wang
 * @version 1.0
 */
public class Patient {

    private static final int MINIMUM_NAME_LENGTH = 2; // the smallest number of characters of a patient name

    private static final int PHONE_NUMBER_LENGTH = 10; // the exact number of digits of a phone number

    private String patientName; // the full name of the patient

    private String mobilePhoneNumber; // the mobile phone number of the patient, which stores ten digits

    /**
     * Creates a patient after the name and the phone number are checked.
     *
     * @param patientName the full name of the patient
     * @param mobilePhoneNumber the mobile phone number of the patient
     */
    public Patient(String patientName, String mobilePhoneNumber) {
        this.patientName = checkPatientName(patientName);
        this.mobilePhoneNumber = checkMobilePhoneNumber(mobilePhoneNumber);
    }

    /**
     * Returns the full name of the patient.
     *
     * @return the full name of the patient
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Returns the mobile phone number of the patient.
     *
     * @return the mobile phone number of the patient
     */
    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    /**
     * Builds the details of the patient in a readable form.
     *
     * @return the details of this patient
     */
    public String displayDetails() {
        String details = ""; // the text that describes this patient
        details += "Patient name: " + patientName + "\n";
        details += "Mobile phone number: " + mobilePhoneNumber;
        return details;
    }

    /**
     * Returns the same text that displayDetails returns.
     *
     * @return the details of this patient
     */
    @Override
    public String toString() {
        return displayDetails();
    }

    /**
     * Checks and cleans the patient name. The name must contain letters,
     * spaces, hyphens or apostrophes only, so a name such as "Emma Wilson"
     * or "Anne-Marie O'Neil" is accepted.
     *
     * @param nameToCheck the name that was given by the caller
     * @return the cleaned name
     */
    private static String checkPatientName(String nameToCheck) {
        if (nameToCheck == null) {
            throw new IllegalArgumentException("The patient name cannot be empty.");
        }
        String cleanedName = nameToCheck.trim(); // the name after the spaces at both ends are removed
        if (cleanedName.length() < MINIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException("The patient name needs at least "
                    + MINIMUM_NAME_LENGTH + " characters.");
        }
        // index goes through every character of the name
        for (int index = 0; index < cleanedName.length(); index++) {
            char currentCharacter = cleanedName.charAt(index); // one character of the patient name
            boolean characterIsLetterOrSpace = Character.isLetter(currentCharacter) || currentCharacter == ' '; // true for a letter or a space
            boolean characterIsNamePunctuation = currentCharacter == '-' || currentCharacter == '\''; // true for a hyphen or an apostrophe
            if (characterIsLetterOrSpace == false && characterIsNamePunctuation == false) {
                throw new IllegalArgumentException("The patient name can only contain letters, spaces, '-' and '''.");
            }
        }
        return cleanedName;
    }

    /**
     * Checks and cleans the mobile phone number. The number must store exactly
     * ten digits and must start with zero.
     *
     * @param phoneNumberToCheck the phone number that was given by the caller
     * @return the cleaned phone number
     */
    private static String checkMobilePhoneNumber(String phoneNumberToCheck) {
        if (phoneNumberToCheck == null) {
            throw new IllegalArgumentException("The mobile phone number cannot be empty.");
        }
        String cleanedNumber = phoneNumberToCheck.trim().replace(" ", ""); // the number without any space
        if (cleanedNumber.length() != PHONE_NUMBER_LENGTH) {
            throw new IllegalArgumentException("The mobile phone number must contain exactly "
                    + PHONE_NUMBER_LENGTH + " digits.");
        }
        // index goes through every digit of the phone number
        for (int index = 0; index < cleanedNumber.length(); index++) {
            char currentCharacter = cleanedNumber.charAt(index); // one digit of the phone number
            if (Character.isDigit(currentCharacter) == false) {
                throw new IllegalArgumentException("The mobile phone number must contain digits only.");
            }
        }
        if (cleanedNumber.startsWith("0") == false) {
            throw new IllegalArgumentException("The mobile phone number must start with 0.");
        }
        return cleanedNumber;
    }
}
