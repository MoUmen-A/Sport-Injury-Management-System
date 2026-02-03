import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Represents a sports injury with athlete-friendly descriptions.
 * Contains the top 10 most common athletic injuries with descriptions
 * written from the athlete's perspective (not medical terminology).
 * 
 * Rationale: Athletes may not know medical diagnoses, so injuries are
 * described in terms they understand (e.g., "It feels tight when I run").
 * The movable flag indicates whether the affected area can be moved,
 * which is crucial for injury assessment.
 */
public class Injury {
    private final String type;
    private final BodyPart bodyPart;
    private final boolean movable;
    private final String athleteDescription;

    /**
     * Static list of the top 10 most common athletic injuries.
     * These injuries are predefined to ensure consistency and provide
     * athlete-friendly descriptions that don't require medical knowledge.
     */
    private static final List<Injury> COMMON_INJURIES = Arrays.asList(
        new Injury("Quadriceps Contusion", BodyPart.THIGH, true, "Deep bruise from direct impact. Can walk but with pain."),
    new Injury("Hamstring Strain Grade 2", BodyPart.HAMSTRING, true, "Partial muscle tear. Pain when bending knee or stretching."),

    new Injury("Achilles Tendinitis", BodyPart.ACHILLES, true, "Morning stiffness and pain along the back of heel."),
    new Injury("Calf Muscle Pull", BodyPart.CALF, true, "Sudden sharp pain during push-off. Can't run properly."),

    new Injury("High Ankle Sprain", BodyPart.ANKLE, false, "Pain above ankle, between tibia and fibula. Very unstable."),
    new Injury("Ankle Fracture", BodyPart.ANKLE, false, "Broken bone in ankle. Can't bear any weight at all."),

    new Injury("ACL Tear", BodyPart.KNEE, false, "Knee gave out with popping sound. Immediate swelling."),
    new Injury("Meniscus Tear", BodyPart.KNEE, true, "Locking/catching sensation. Pain when twisting knee."),

    new Injury("Plantar Fasciitis", BodyPart.FOOT, true, "Heel pain especially first steps in morning."),
    new Injury("Metatarsal Stress Fracture", BodyPart.FOOT, false, "Pain in middle of foot. Worse with activity."),

    new Injury("Compartment Syndrome", BodyPart.SHIN, false, "Intense pressure and pain. Numbness in foot."),
    new Injury("Tibial Stress Reaction", BodyPart.SHIN, true, "Pain along shin bone that worsens with exercise."),

    new Injury("Tennis Elbow", BodyPart.ELBOW, true, "Pain on outside of elbow when gripping or lifting."),
    new Injury("Golfer's Elbow", BodyPart.ELBOW, true, "Pain on inside of elbow, worse with wrist flexion."),

    new Injury("Rotator Cuff Tear", BodyPart.SHOULDER, true, "Pain when lifting arm overhead. Weakness."),
    new Injury("Frozen Shoulder", BodyPart.SHOULDER, false, "Stiffness and pain. Gradually losing range of motion."),

    new Injury("Wrist Sprain", BodyPart.WRIST, true, "Pain with movement, especially bending backward."),
    new Injury("Carpal Tunnel Syndrome", BodyPart.WRIST, true, "Numbness/tingling in fingers, especially at night."),

    new Injury("Femur Fracture", BodyPart.LEG, false, "Severe thigh pain. Leg appears deformed."),
    new Injury("IT Band Syndrome", BodyPart.LEG, true, "Pain on outside of knee/hip. Worse with running."),

    new Injury("Biceps Tendinitis", BodyPart.ARM, true, "Pain in front of shoulder when lifting."),
    new Injury("Triceps Strain", BodyPart.ARM, true, "Pain in back of upper arm when extending elbow."),

    new Injury("AC Joint Separation", BodyPart.SHOULDER, false, "Bump on top of shoulder. Pain with arm movement."),
    new Injury("Patellar Tendinitis", BodyPart.KNEE, true, "Pain below kneecap, especially when jumping."),

    new Injury("Achilles Rupture", BodyPart.ACHILLES, false, "Sudden pop in calf. Can't push off foot."),
    new Injury("Anterior Ankle Impingement", BodyPart.ANKLE, true, "Pain in front of ankle when pointing toes up.")
        );

    /**
     * Constructs an Injury with all required attributes.
     **/

    // mfrod eni a2od bs el type w body part w 3la assha ha8d el moveable w decitption msh el user eli deh8lha 🚨
    public Injury(String type, BodyPart bodyPart, boolean movable, String athleteDescription) {
        this.type = type;
        this.bodyPart = bodyPart;
        this.movable = movable;
        this.athleteDescription = athleteDescription;
    }

    /**
     * Retrieves all common injuries as an array.
     * Useful for populating dropdown lists or selection menus in the GUI.
     * 
     * @return An array containing all 10 predefined common injuries
     */
    public static Injury[] getAllInjuries() {
        return COMMON_INJURIES.toArray(new Injury[0]);
    }

    /**
     * Filters injuries by the specified body part.
     * If null is passed, returns all injuries (useful for "All" selection).
     * Uses Java Streams for efficient filtering.
     * 
     * @param part The BodyPart enum to filter by (null returns all injuries)
     * @return A filtered list of injuries matching the specified body part
     */
    public static List<Injury> getInjuriesByBodyPart(BodyPart part) {
        if (part == null) {
            return COMMON_INJURIES;
        }
        
        ArrayList<Injury> result = new ArrayList<Injury>();
        for (Injury injury : COMMON_INJURIES) {
            if (injury.bodyPart == part) {
                result.add(injury);
            }
        }
        return result;
         
    }

    /**
     * Retrieves the body part affected by this injury.
     * @return The BodyPart enum representing the affected area
     */
    public BodyPart getBodyPart() {
        return bodyPart;
    }

    /**
     * Retrieves the type/name of this injury.
     * @return The injury type (e.g., "Ankle Sprain", "Muscle Tear")
     */
    public String getType() {
        return type;
    }

    /**
     * Checks if the affected area is movable.
     * @return true if the area can be moved (even if painful), false if immobile
     */
    public boolean isMovable() {
        return movable;
    }

    /**
     * Retrieves the athlete-friendly description of this injury.
     * Written from the athlete's perspective, not using medical terminology.
     * 
     * @return A description string (e.g., "It feels tight when I run. I can move but not fully.")
     */
    public String getAthleteDescription() {
        return athleteDescription;
    }

    /**
     * Displays detailed information about this injury to the console.
     * Shows type, body part, mobility status, and athlete description.
     * Useful for console-based interfaces and debugging.
     */
    public void displayInjuryDetails() {
        System.out.println("Injury Type: " + type);
        System.out.println("Body Part: " + bodyPart);
        System.out.println("Movable: " + (movable ? "Yes/limited" : "No"));
        System.out.println("Athlete description: " + athleteDescription);
    }

    /**
     * Returns a string representation of this injury.
     * Format: "InjuryType (BodyPart)" (e.g., "Ankle Sprain (Ankle)")
     * 
     * @return A formatted string representation
     */
    @Override
    public String toString() {
        return type + " : "+ athleteDescription;
    }
}
