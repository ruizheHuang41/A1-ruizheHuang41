import java.time.DayOfWeek;

/**
 * This class represents a nurse, who takes blood samples and records the vital
 * signs of patients. A nurse works in one ward and works one shift, so both the
 * ward and the shift are stored with the normal details of the nurse.
 *
 * @author Junzhou Wang
 * @version 1.0
 */
public class Nurse extends HealthProfessional {

    // the ward where this nurse works, for example "Ward B"
    private String assignedWard;

    // the name of the shift of this nurse, for example "Morning shift"
    private String shiftName;

    /**
     * Creates a nurse with all of the information.
     *
     * @param healthProfessionalIdentifier the identifier, which must store digits only
     * @param healthProfessionalName the full name of the nurse
     * @param availableWorkingDays the days of the week on which the nurse works
     * @param assignedWard the ward where the nurse works
     * @param shiftName the name of the shift of the nurse
     */
    public Nurse(String healthProfessionalIdentifier, String healthProfessionalName,
            DayOfWeek[] availableWorkingDays, String assignedWard, String shiftName) {
        super(healthProfessionalIdentifier, healthProfessionalName, availableWorkingDays);
        this.assignedWard = checkWard(assignedWard);
        this.shiftName = checkShiftName(shiftName);
    }

    /**
     * Creates a nurse with the default working days of the parent class and two
     * sensible default values for the extra information.
     *
     * @param healthProfessionalIdentifier the identifier, which must store digits only
     * @param healthProfessionalName the full name of the nurse
     */
    public Nurse(String healthProfessionalIdentifier, String healthProfessionalName) {
        super(healthProfessionalIdentifier, healthProfessionalName);
        this.assignedWard = "General ward"; // the ward that a new nurse receives first
        this.shiftName = "Morning shift"; // the shift that a new nurse normally starts with
    }

    /**
     * Returns the ward where this nurse works.
     *
     * @return the name of the ward
     */
    public String getAssignedWard() {
        return assignedWard;
    }

    /**
     * Returns the name of the shift of this nurse.
     *
     * @return the name of the shift
     */
    public String getShiftName() {
        return shiftName;
    }

    /**
     * Returns the name of this specialty.
     *
     * @return the name of the specialty
     */
    @Override
    public String describeProfessionalType() {
        return "Nurse";
    }

    /**
     * Returns the main duty of a nurse.
     *
     * @return a sentence about the main duty
     */
    @Override
    public String performProfessionalDuty() {
        return "Takes blood samples and records the vital signs of patients.";
    }

    /**
     * Returns the consultation fee of a nurse.
     *
     * @return the consultation fee
     */
    @Override
    public double calculateConsultationFee() {
        return 40.00; // a nurse charges forty dollars
    }

    /**
     * Builds the details of this nurse, including the shared details that come
     * from the parent class.
     *
     * @return the details of this nurse
     */
    @Override
    public String displayDetails() {
        String details = super.displayDetails(); // the shared details from the parent class
        details += "\nAssigned ward: " + assignedWard;
        details += "\nShift name: " + shiftName;
        return details;
    }

    /**
     * Checks and cleans the name of the ward.
     *
     * @param wardToCheck the ward name that was given by the caller
     * @return the cleaned ward name
     */
    private static String checkWard(String wardToCheck) {
        if (wardToCheck == null) {
            throw new IllegalArgumentException("The ward cannot be empty.");
        }
        String cleanedWard = wardToCheck.trim(); // the ward name after the spaces are removed
        if (cleanedWard.length() == 0) {
            throw new IllegalArgumentException("The ward cannot be empty.");
        }
        return cleanedWard;
    }

    /**
     * Checks and cleans the name of the shift.
     *
     * @param shiftToCheck the shift name that was given by the caller
     * @return the cleaned shift name
     */
    private static String checkShiftName(String shiftToCheck) {
        if (shiftToCheck == null) {
            throw new IllegalArgumentException("The shift name cannot be empty.");
        }
        String cleanedShift = shiftToCheck.trim(); // the shift name after the spaces are removed
        if (cleanedShift.length() == 0) {
            throw new IllegalArgumentException("The shift name cannot be empty.");
        }
        return cleanedShift;
    }
}
