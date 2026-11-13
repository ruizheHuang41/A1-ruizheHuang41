import java.time.DayOfWeek;

/**
 * This class represents a surgeon, who performs operations in the operating
 * theatre. A surgeon records the surgical specialty and the number of years of
 * experience, because a patient may want to know how experienced the surgeon is.
 *
 * @author Junzhou Wang
 * @version 1.0
 */
public class Surgeon extends HealthProfessional {

    // the area of surgery that this surgeon specialises in
    private String surgicalSpecialty;

    // the number of full years that this surgeon has worked in a hospital
    private int yearsOfExperience;

    /**
     * Creates a surgeon with all of the information.
     *
     * @param healthProfessionalIdentifier the identifier, which must store digits only
     * @param healthProfessionalName the full name of the surgeon
     * @param availableWorkingDays the days of the week on which the surgeon works
     * @param surgicalSpecialty the area of surgery of this surgeon
     * @param yearsOfExperience the number of full years of experience
     */
    public Surgeon(String healthProfessionalIdentifier, String healthProfessionalName,
            DayOfWeek[] availableWorkingDays, String surgicalSpecialty, int yearsOfExperience) {
        super(healthProfessionalIdentifier, healthProfessionalName, availableWorkingDays);
        this.surgicalSpecialty = checkSurgicalSpecialty(surgicalSpecialty);
        this.yearsOfExperience = checkYearsOfExperience(yearsOfExperience);
    }

    /**
     * Creates a surgeon with the default working days of the parent class and
     * two sensible default values for the extra information.
     *
     * @param healthProfessionalIdentifier the identifier, which must store digits only
     * @param healthProfessionalName the full name of the surgeon
     */
    public Surgeon(String healthProfessionalIdentifier, String healthProfessionalName) {
        super(healthProfessionalIdentifier, healthProfessionalName);
        this.surgicalSpecialty = "General surgery"; // the specialty that a new surgeon starts with
        this.yearsOfExperience = 0; // a new surgeon has no full year of experience yet
    }

    /**
     * Returns the surgical specialty of this surgeon.
     *
     * @return the surgical specialty
     */
    public String getSurgicalSpecialty() {
        return surgicalSpecialty;
    }

    /**
     * Returns the number of full years of experience.
     *
     * @return the number of years of experience
     */
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    /**
     * Returns the name of this specialty.
     *
     * @return the name of the specialty
     */
    @Override
    public String describeProfessionalType() {
        return "Surgeon";
    }

    /**
     * Returns the main duty of a surgeon.
     *
     * @return a sentence about the main duty
     */
    @Override
    public String performProfessionalDuty() {
        return "Performs operations in the operating theatre and reviews patients after surgery.";
    }

    /**
     * Returns the consultation fee of a surgeon.
     *
     * @return the consultation fee
     */
    @Override
    public double calculateConsultationFee() {
        return 250.00; // a surgeon charges two hundred and fifty dollars
    }

    /**
     * Builds the details of this surgeon, including the shared details that come
     * from the parent class.
     *
     * @return the details of this surgeon
     */
    @Override
    public String displayDetails() {
        String details = super.displayDetails(); // the shared details from the parent class
        details += "\nSurgical specialty: " + surgicalSpecialty;
        details += "\nYears of experience: " + yearsOfExperience;
        return details;
    }

    /**
     * Checks and cleans the name of the surgical specialty.
     *
     * @param specialtyToCheck the specialty that was given by the caller
     * @return the cleaned specialty
     */
    private static String checkSurgicalSpecialty(String specialtyToCheck) {
        if (specialtyToCheck == null) {
            throw new IllegalArgumentException("The surgical specialty cannot be empty.");
        }
        String cleanedSpecialty = specialtyToCheck.trim(); // the specialty after the spaces are removed
        if (cleanedSpecialty.length() == 0) {
            throw new IllegalArgumentException("The surgical specialty cannot be empty.");
        }
        return cleanedSpecialty;
    }

    /**
     * Checks the number of years of experience.
     *
     * @param yearsToCheck the number of years that was given by the caller
     * @return the checked number of years
     */
    private static int checkYearsOfExperience(int yearsToCheck) {
        if (yearsToCheck < 0) {
            throw new IllegalArgumentException("The years of experience cannot be negative.");
        }
        return yearsToCheck;
    }
}
