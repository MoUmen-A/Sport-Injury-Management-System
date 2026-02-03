/**
 * Enumeration representing available weekdays for appointments.
 * Limits appointment scheduling to specific days (Sunday, Tuesday, Thursday).
 * 
 * Rationale: Using an enum ensures only valid weekdays can be selected,
 * preventing scheduling errors and maintaining consistency across the system.
 */
public enum Weekday {
    SUNDAY,
    TUESDAY,
    THURSDAY;

    /**
     * Returns a human-readable string representation of the weekday.
     * Converts enum name (e.g., "SUNDAY") to formatted string (e.g., "Sunday").
     * 
     * @return A formatted string with proper capitalization
     */
    @Override
    public String toString() {
        String lower = name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}

