package dev.brooskiey.personalplanner.services.enums;

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

    public static boolean contains(String value) {
        for(Recurrence recurrence: values()) {
            if(recurrence.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
