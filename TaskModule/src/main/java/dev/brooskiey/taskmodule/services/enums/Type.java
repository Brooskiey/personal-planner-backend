package dev.brooskiey.taskmodule.services.enums;

/**
 * Type Enum. These values correspond to the DB values
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
public enum Type {
    CLEANING,
    APPOINTMENT,
    TRAVEL,
    VISITOR,
    MEDICINE,
    TODO;

    /**
     * Check whether the value is contained in the enums.
     * @param value the value to check
     * @return true if it is an enum and false otherwise
     */
    public static boolean contains(String value) {
        for(Type type: values()) {
            if(type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

}
