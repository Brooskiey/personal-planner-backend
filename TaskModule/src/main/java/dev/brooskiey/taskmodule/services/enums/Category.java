package dev.brooskiey.taskmodule.services.enums;

public enum Category {
    WEEKLY,
    MONTHLY,
    BIWEEKLY,
    EVERYDAY;

    public static boolean contains(String value) {
        for(Category category: values()) {
            if(category.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
