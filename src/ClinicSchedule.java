import java.time.LocalTime;
import java.util.ArrayList;

/**
 * This class describes the fixed daily schedule of the clinic.
 * The clinic works from 08:00 to 16:00, and every appointment slot is thirty
 * minutes long. The opening time, the closing time and the length of a slot are
 * kept in this single class, so the whole clinic can change its schedule by
 * changing these three values only.
 *
 * @author Junzhou Wang
 * @version 1.0
 */
public class ClinicSchedule {

    private static final LocalTime OPENING_TIME = LocalTime.of(8, 0); // the time when the clinic opens

    private static final LocalTime CLOSING_TIME = LocalTime.of(16, 0); // the time when the clinic closes

    private static final int SLOT_LENGTH_IN_MINUTES = 30; // the length of one appointment slot in minutes

    /**
     * This private constructor stops other classes from creating a
     * ClinicSchedule object, because this class only offers static methods.
     */
    private ClinicSchedule() {
    }

    /**
     * Builds the fixed list of time slots for one clinic day.
     * The last slot starts before the closing time, so the last slot of the
     * current schedule is 15:30.
     *
     * @return a new list that holds every time slot of one day in time order
     */
    public static ArrayList<LocalTime> createDailyTimeSlots() {
        ArrayList<LocalTime> timeSlotList = new ArrayList<LocalTime>(); // the list of slots that will be returned
        LocalTime currentTime = OPENING_TIME; // the slot that is being added to the list
        while (currentTime.isBefore(CLOSING_TIME) == true) {
            timeSlotList.add(currentTime);
            currentTime = currentTime.plusMinutes(SLOT_LENGTH_IN_MINUTES);
        }
        return timeSlotList;
    }

    /**
     * Checks whether a given time is one of the fixed clinic time slots.
     *
     * @param timeToCheck the time that should be checked
     * @return true when the time is a valid slot, otherwise false
     */
    public static boolean checkTimeIsOnSchedule(LocalTime timeToCheck) {
        if (timeToCheck == null) {
            return false;
        }
        ArrayList<LocalTime> timeSlotList = createDailyTimeSlots(); // every valid slot of the clinic
        return timeSlotList.contains(timeToCheck);
    }

    /**
     * Returns the opening time of the clinic.
     *
     * @return the time when the clinic opens
     */
    public static LocalTime getOpeningTime() {
        return OPENING_TIME;
    }

    /**
     * Returns the closing time of the clinic.
     *
     * @return the time when the clinic closes
     */
    public static LocalTime getClosingTime() {
        return CLOSING_TIME;
    }
}
