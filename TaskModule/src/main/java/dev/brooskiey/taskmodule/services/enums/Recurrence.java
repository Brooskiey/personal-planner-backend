package dev.brooskiey.taskmodule.services.enums;

/**
 * Recurrence Enum. These values correspond to the DB values
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
public enum Recurrence {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY,
    FIRST,
    FIFTEENTH,
    LAST,
    ALL;

    /**
     * Check whether the value is contained in the enums.
     * @param value the value to check
     * @return true if it is an enum and false otherwise
     */
    public static boolean contains(String value) {
        for(Recurrence recurrence: values()) {
            if(recurrence.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
