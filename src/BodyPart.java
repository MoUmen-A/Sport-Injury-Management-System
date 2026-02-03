/**
 * Enumeration representing body parts that can be affected by injuries.
 * Used to categorize injuries and allow filtering by affected area.
 * 
 * Rationale: Using an enum ensures type safety and prevents invalid
 * body part values. It also provides a consistent set of options
 * for the user interface and injury classification.
 */
public enum BodyPart {
    THIGH,
    HAMSTRING,
    CALF,
    ANKLE,
    KNEE,
    FOOT,
    SHIN,
    ARM,
    LEG,
    WRIST,
    SHOULDER,
    ELBOW,
    ACHILLES;

    /**
     * Returns a human-readable string representation of the body part.
     * Converts enum name (e.g., "THIGH") to formatted string (e.g., "Thigh").
     * 
     * @return A formatted string with proper capitalization
     */
    @Override
    public String toString() {
        String lower = name().toLowerCase().replace('_', ' ');
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}