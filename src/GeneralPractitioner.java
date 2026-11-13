import java.time.DayOfWeek;

/**
 * This class represents a general practitioner, who is the first health
 * professional that most patients see. A general practitioner works in one
 * consultation room and can stop accepting new patients when the room is full.
 *
 * @author Ruizhe Huang
 * @version 1.0
 */
public class GeneralPractitioner extends HealthProfessional {

    private String assignedConsultationRoom; // the consultation room where this general practitioner works

    private boolean acceptsNewPatients; // true when the general practitioner still accepts new patients

    /**
     * Creates a general practitioner with all of the information.
     *
     * @param healthProfessionalIdentifier the identifier, which must store digits only
     * @param healthProfessionalName the full name of the general practitioner
     * @param availableWorkingDays the days of the week on which the professional works
     * @param assignedConsultationRoom the consultation room of this professional
     * @param acceptsNewPatients true when the professional still accepts new patients
     */
    public GeneralPractitioner(String healthProfessionalIdentifier, String healthProfessionalName,
            DayOfWeek[] availableWorkingDays, String assignedConsultationRoom, boolean acceptsNewPatients) {
        super(healthProfessionalIdentifier, healthProfessionalName, availableWorkingDays);
        this.assignedConsultationRoom = checkConsultationRoom(assignedConsultationRoom);
        this.acceptsNewPatients = acceptsNewPatients;
    }

    /**
     * Creates a general practitioner with the default working days of the parent
     * class, a default consultation room and the normal answer for new patients.
     *
     * @param healthProfessionalIdentifier the identifier, which must store digits only
     * @param healthProfessionalName the full name of the general practitioner
     */
    public GeneralPractitioner(String healthProfessionalIdentifier, String healthProfessionalName) {
        super(healthProfessionalIdentifier, healthProfessionalName);
        this.assignedConsultationRoom = "Room 1"; // the room that a new general practitioner receives first
        this.acceptsNewPatients = true; // a new general practitioner normally accepts new patients
    }

    /**
     * Returns the consultation room of this general practitioner.
     *
     * @return the consultation room
     */
    public String getAssignedConsultationRoom() {
        return assignedConsultationRoom;
    }

    /**
     * Tells whether this general practitioner still accepts new patients.
     *
     * @return true when new patients are accepted
     */
    public boolean getAcceptsNewPatients() {
        return acceptsNewPatients;
    }

    /**
     * Returns the name of this specialty.
     *
     * @return the name of the specialty
     */
    @Override
    public String describeProfessionalType() {
        return "General practitioner";
    }

    /**
     * Returns the main duty of a general practitioner.
     *
     * @return a sentence about the main duty
     */
    @Override
    public String performProfessionalDuty() {
        return "Prescribes medication for common illnesses and refers patients to specialists.";
    }

    /**
     * Returns the consultation fee of a general practitioner.
     *
     * @return the consultation fee
     */
    @Override
    public double calculateConsultationFee() {
        return 60.00; // a general practitioner charges sixty dollars
    }

    /**
     * Builds the details of this general practitioner. The shared details come
     * from the parent class, and the room and the patient answer are added here.
     *
     * @return the details of this general practitioner
     */
    @Override
    public String displayDetails() {
        String details = super.displayDetails(); // the shared details from the parent class
        details += "\nAssigned consultation room: " + assignedConsultationRoom;
        details += "\nAccepts new patients: " + acceptsNewPatients;
        return details;
    }

    /**
     * Checks and cleans the name of the consultation room.
     *
     * @param roomToCheck the room name that was given by the caller
     * @return the cleaned room name
     */
    private static String checkConsultationRoom(String roomToCheck) {
        if (roomToCheck == null) {
            throw new IllegalArgumentException("The consultation room cannot be empty.");
        }
        String cleanedRoom = roomToCheck.trim(); // the room name after the spaces are removed
        if (cleanedRoom.length() == 0) {
            throw new IllegalArgumentException("The consultation room cannot be empty.");
        }
        return cleanedRoom;
    }
}
