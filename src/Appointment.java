import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class represents one appointment in the clinic. An appointment links one
 * patient to one health professional at one time slot. The health professional
 * is stored as the general type, so an appointment can hold any specialty that
 * extends HealthProfessional. The appointment date is always the current day,
 * because the clinic only accepts bookings for today.
 *
 * @author Junzhou Wang
 * @version 1.0
 */
public class Appointment implements Comparable<Appointment> {

    private Patient appointmentPatient; // the patient who booked this appointment

    private HealthProfessional appointmentHealthProfessional; // the health professional who will see the patient

    private LocalTime appointmentTime; // the time slot of this appointment, for example 09:30

    private LocalDate appointmentDate; // the day of this appointment, which is always the current day

    /**
     * Creates an appointment after every detail is checked. The date is filled in
     * with the current day, so an appointment can never be booked for any other day.
     *
     * @param appointmentPatient the patient who books the appointment
     * @param appointmentHealthProfessional the health professional who will be seen
     * @param appointmentTime the time slot of the appointment
     */
    public Appointment(Patient appointmentPatient, HealthProfessional appointmentHealthProfessional,
            LocalTime appointmentTime) {
        if (appointmentPatient == null) {
            throw new IllegalArgumentException("An appointment needs a patient.");
        }
        if (appointmentHealthProfessional == null) {
            throw new IllegalArgumentException("An appointment needs a health professional.");
        }
        if (appointmentTime == null) {
            throw new IllegalArgumentException("An appointment needs a time slot.");
        }
        if (ClinicSchedule.checkTimeIsOnSchedule(appointmentTime) == false) {
            throw new IllegalArgumentException("The time " + appointmentTime
                    + " is not one of the clinic time slots.");
        }
        this.appointmentPatient = appointmentPatient;
        this.appointmentHealthProfessional = appointmentHealthProfessional;
        this.appointmentTime = appointmentTime;
        this.appointmentDate = LocalDate.now(); // every booking is made for the current day only
    }

    /**
     * Returns the patient of this appointment.
     *
     * @return the patient of this appointment
     */
    public Patient getAppointmentPatient() {
        return appointmentPatient;
    }

    /**
     * Returns the health professional of this appointment.
     *
     * @return the health professional of this appointment
     */
    public HealthProfessional getAppointmentHealthProfessional() {
        return appointmentHealthProfessional;
    }

    /**
     * Returns the time slot of this appointment.
     *
     * @return the time slot of this appointment
     */
    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * Returns the date of this appointment.
     *
     * @return the date of this appointment
     */
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Compares this appointment with another appointment, so a whole list of
     * appointments can be sorted from the earliest time to the latest time.
     * When two appointments start at the same time, the identifiers of the
     * health professionals decide the order, so the result is always the same.
     *
     * @param otherAppointment the appointment that this appointment is compared with
     * @return a negative number, zero, or a positive number
     */
    @Override
    public int compareTo(Appointment otherAppointment) {
        int timeComparison = appointmentTime.compareTo(otherAppointment.getAppointmentTime()); // the result of the time comparison
        if (timeComparison != 0) {
            return timeComparison;
        }
        String thisIdentifier = appointmentHealthProfessional.getHealthProfessionalIdentifier(); // the identifier of this professional
        String otherIdentifier = otherAppointment.getAppointmentHealthProfessional()
                .getHealthProfessionalIdentifier(); // the identifier of the other professional
        return thisIdentifier.compareTo(otherIdentifier);
    }

    /**
     * Builds the details of this appointment in a readable form. The details of
     * the chosen health professional are included, so the person who reads the
     * appointment knows who the patient will see.
     *
     * @return the details of this appointment
     */
    @Override
    public String toString() {
        String details = ""; // the text that describes this appointment
        details += "Appointment date: " + appointmentDate + "\n";
        details += "Appointment time: " + appointmentTime + "\n";
        details += "Patient: " + appointmentPatient.getPatientName()
                + " (mobile phone number " + appointmentPatient.getMobilePhoneNumber() + ")\n";
        details += "Health professional: " + appointmentHealthProfessional.getHealthProfessionalName()
                + " (" + appointmentHealthProfessional.describeProfessionalType()
                + ", identifier " + appointmentHealthProfessional.getHealthProfessionalIdentifier() + ")\n";
        details += "Consultation fee: "
                + String.format("%.2f", appointmentHealthProfessional.calculateConsultationFee()) + " dollars";
        return details;
    }
}
