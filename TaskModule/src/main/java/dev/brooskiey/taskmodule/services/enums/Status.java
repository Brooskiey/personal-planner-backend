package dev.brooskiey.taskmodule.services.enums;

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
