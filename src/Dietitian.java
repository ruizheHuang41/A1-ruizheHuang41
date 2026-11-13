import java.time.DayOfWeek;

/**
 * This class represents a dietitian, who helps patients with the food they eat.
 * A dietitian records the main focus of the diet plan and whether online
 * consultations are offered, because many patients prefer to stay at home.
 *
 * @author Junzhou Wang
 * @version 1.0
 */
public class Dietitian extends HealthProfessional {

    // the health problem that the diet plans of this dietitian focus on
    private String dietPlanFocus;

    // true when this dietitian offers online consultations as well
    private boolean offersOnlineConsultation;

    /**
     * Creates a dietitian with all of the information.
     *
     * @param healthProfessionalIdentifier the identifier, which must store digits only
     * @param healthProfessionalName the full name of the dietitian
     * @param availableWorkingDays the days of the week on which the dietitian works
     * @param dietPlanFocus the main focus of the diet plans of this dietitian
     * @param offersOnlineConsultation true when online consultations are offered
     */
    public Dietitian(String healthProfessionalIdentifier, String healthProfessionalName,
            DayOfWeek[] availableWorkingDays, String dietPlanFocus, boolean offersOnlineConsultation) {
        super(healthProfessionalIdentifier, healthProfessionalName, availableWorkingDays);
        this.dietPlanFocus = checkDietPlanFocus(dietPlanFocus);
        this.offersOnlineConsultation = offersOnlineConsultation;
    }

    /**
     * Creates a dietitian with the default working days of the parent class and
     * two sensible default values for the extra information.
     *
     * @param healthProfessionalIdentifier the identifier, which must store digits only
     * @param healthProfessionalName the full name of the dietitian
     */
    public Dietitian(String healthProfessionalIdentifier, String healthProfessionalName) {
        super(healthProfessionalIdentifier, healthProfessionalName);
        this.dietPlanFocus = "Healthy eating"; // the focus that a new dietitian starts with
        this.offersOnlineConsultation = false; // online consultations must be switched on first
    }

    /**
     * Returns the main focus of the diet plans.
     *
     * @return the main focus of the diet plans
     */
    public String getDietPlanFocus() {
        return dietPlanFocus;
    }

    /**
     * Tells whether this dietitian offers online consultations.
     *
     * @return true when online consultations are offered
     */
    public boolean getOffersOnlineConsultation() {
        return offersOnlineConsultation;
    }

    /**
     * Returns the name of this specialty.
     *
     * @return the name of the specialty
     */
    @Override
    public String describeProfessionalType() {
        return "Dietitian";
    }

    /**
     * Returns the main duty of a dietitian.
     *
     * @return a sentence about the main duty
     */
    @Override
    public String performProfessionalDuty() {
        return "Creates nutrition plans and reviews the weight of patients.";
    }

    /**
     * Returns the consultation fee of a dietitian.
     *
     * @return the consultation fee
     */
    @Override
    public double calculateConsultationFee() {
        return 80.00; // a dietitian charges eighty dollars
    }

    /**
     * Builds the details of this dietitian, including the shared details that
     * come from the parent class.
     *
     * @return the details of this dietitian
     */
    @Override
    public String displayDetails() {
        String details = super.displayDetails(); // the shared details from the parent class
        details += "\nDiet plan focus: " + dietPlanFocus;
        details += "\nOffers online consultation: " + offersOnlineConsultation;
        return details;
    }

    /**
     * Checks and cleans the main focus of the diet plans.
     *
     * @param focusToCheck the focus that was given by the caller
     * @return the cleaned focus
     */
    private static String checkDietPlanFocus(String focusToCheck) {
        if (focusToCheck == null) {
            throw new IllegalArgumentException("The diet plan focus cannot be empty.");
        }
        String cleanedFocus = focusToCheck.trim(); // the focus after the spaces are removed
        if (cleanedFocus.length() == 0) {
            throw new IllegalArgumentException("The diet plan focus cannot be empty.");
        }
        return cleanedFocus;
    }
}
