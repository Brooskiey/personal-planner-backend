package dev.brooskiey.personalplanner.services.enums;

public enum Type {
    CLEANING,
    APPOINTMENT,
    TRAVEL,
    VISITOR,
    MEDICINE,
    TODO;

    public static boolean contains(String value) {
        for(Type type: values()) {
            if(type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

}
