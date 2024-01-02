package dev.brooskiey.taskmodule.services.enums;

/**
 * Status Enum. These values correspond to the DB values
 * @author Brooskiey Bullet
 * @version 01.01.2024
 */
public enum Status {
    NOT_STARTED("NOT STARTED"),
    IN_PROGRESS("IN PROGRESS"),
    COMPLETED("COMPLETED"),
    MIGRATED("MIGRATED"),
    CANCELED("CANCELED");

    private final String value;

    Status (String value) {
        this.value = value;
    }

    /**
     * Check whether the value is contained in the enums.
     * @param value the value to check
     * @return true if it is an enum and false otherwise
     */
    public static boolean contains(String value) {
        for(Status status: values()) {
            if(status.getValue().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public String getValue() {
        return value;
    }
}
