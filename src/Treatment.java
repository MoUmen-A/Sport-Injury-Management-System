public class Treatment {
    private String injuryType;
    private String treatmentSuggestion;

    public Treatment(String injuryType, String treatmentSuggestion) {
        this.injuryType = injuryType;
        this.treatmentSuggestion = treatmentSuggestion;
    }



    public String getTreatmentSuggestion() {
        return treatmentSuggestion;
    }

    public void displayTreatment() {
        System.out.println("Treatment for " + injuryType + ": " + treatmentSuggestion);
    }

    // Modify getTreatment to return a Treatment object, not just a string
    public static Treatment getTreatment(String injuryName) {
        String treatmentSuggestion = "";

        switch (injuryName) {
            // Treatments aligned with the common injuries defined in Injury.COMMON_INJURIES
            case "Quadriceps Contusion":
                treatmentSuggestion = "Rest from impact activities, apply ice for 15–20 minutes every 2–3 hours, gently stretch as tolerated, and avoid massaging deep bruises early on.";
                break;
            case "Hamstring Strain Grade 2":
                treatmentSuggestion = "Stop activity immediately, use RICE (Rest, Ice, Compression, Elevation), avoid sprinting and aggressive stretching, and begin guided physiotherapy once pain decreases.";
                break;

            case "Achilles Tendinitis":
                treatmentSuggestion = "Reduce running/jumping, apply ice after activity, use heel lifts or supportive shoes, perform eccentric calf strengthening, and see a sports doctor if pain persists.";
                break;
            case "Calf Muscle Pull":
                treatmentSuggestion = "Rest from running, apply ice during the first 48 hours, use compression bandage, elevate the leg, and gradually return with gentle stretching and strengthening.";
                break;

            case "High Ankle Sprain":
                treatmentSuggestion = "Avoid weight-bearing, use crutches if needed, apply ice and compression, keep the ankle elevated, and seek medical evaluation due to longer recovery risk.";
                break;
            case "Ankle Fracture":
                treatmentSuggestion = "Do not walk on the ankle, immobilize it, avoid trying to straighten it, and go to the emergency department or orthopedic specialist immediately.";
                break;

            case "ACL Tear":
                treatmentSuggestion = "Stop playing immediately, apply ice and compression to reduce swelling, keep the leg elevated, use crutches if needed, and consult an orthopedic surgeon promptly.";
                break;
            case "Meniscus Tear":
                treatmentSuggestion = "Avoid twisting or deep squats, apply ice for pain and swelling, use a knee brace if advised, and see a specialist to decide between rehab and possible surgery.";
                break;

            case "Plantar Fasciitis":
                treatmentSuggestion = "Reduce standing/running time, stretch the calf and plantar fascia regularly, use supportive shoes or orthotics, ice the heel after activity, and consider physiotherapy.";
                break;
            case "Metatarsal Stress Fracture":
                treatmentSuggestion = "Stop impact sports, use stiff-soled shoes or a boot as recommended, avoid running/jumping, and consult a doctor for imaging and load-management plan.";
                break;

            case "Compartment Syndrome":
                treatmentSuggestion = "This can be an emergency—stop activity immediately, keep the leg at heart level (not elevated), and seek urgent medical care, especially if pain is severe with numbness.";
                break;
            case "Tibial Stress Reaction":
                treatmentSuggestion = "Cut back running volume, avoid hard surfaces, use cross-training with low impact (bike/swim), and gradually reload the shin under medical or physio supervision.";
                break;

            case "Tennis Elbow":
                treatmentSuggestion = "Rest from gripping/lifting heavy objects, apply ice to the outside of the elbow, use a counterforce strap if advised, and follow eccentric forearm strengthening.";
                break;
            case "Golfer's Elbow":
                treatmentSuggestion = "Reduce activities that stress the inside of the elbow, apply ice, gently stretch the wrist flexors, and start a strengthening program guided by a therapist.";
                break;

            case "Rotator Cuff Tear":
                treatmentSuggestion = "Avoid overhead lifting and throwing, apply ice for pain, use a sling only short-term if needed, and see an orthopedic/shoulder specialist for imaging and rehab or surgery plan.";
                break;
            case "Frozen Shoulder":
                treatmentSuggestion = "Keep the shoulder gently moving within pain limits, use heat before stretching and ice after, and follow a long-term physiotherapy program; consult a doctor for pain control.";
                break;

            case "Wrist Sprain":
                treatmentSuggestion = "Rest from weight-bearing on the wrist, apply ice 10–15 minutes several times per day, use a wrist brace for support, and avoid heavy lifting until pain and strength improve.";
                break;
            case "Carpal Tunnel Syndrome":
                treatmentSuggestion = "Use a night splint to keep the wrist neutral, avoid prolonged wrist flexion, take breaks from repetitive hand tasks, and see a doctor if numbness or weakness continues.";
                break;

            case "Femur Fracture":
                treatmentSuggestion = "This is a medical emergency—do not move the leg unnecessarily, keep the person still, support the leg, and call emergency services immediately.";
                break;
            case "IT Band Syndrome":
                treatmentSuggestion = "Reduce running, especially downhill, use ice on the outside of the knee/hip after activity, foam-roll the IT band and surrounding muscles, and strengthen hip abductors.";
                break;

            case "Biceps Tendinitis":
                treatmentSuggestion = "Avoid overhead or heavy lifting, apply ice to the front of the shoulder, correct lifting/throwing technique, and follow a shoulder and scapular strengthening program.";
                break;
            case "Triceps Strain":
                treatmentSuggestion = "Rest from pushing/pressing movements, use ice in the first 48 hours, apply light compression, and gradually add stretching and strengthening once pain decreases.";
                break;

            case "AC Joint Separation":
                treatmentSuggestion = "Use a sling for comfort, apply ice on top of the shoulder, avoid overhead or cross-body movements early on, and see a doctor to grade the injury and guide return-to-sport.";
                break;
            case "Patellar Tendinitis":
                treatmentSuggestion = "Reduce jumping and running, apply ice after training, use a patellar strap if recommended, and start eccentric quadriceps exercises and hip strengthening.";
                break;

            case "Achilles Rupture":
                treatmentSuggestion = "You may feel a sudden pop—do not walk on the leg, keep the ankle supported, and go to emergency care or a specialist immediately for surgical/non-surgical management.";
                break;
            case "Anterior Ankle Impingement":
                treatmentSuggestion = "Avoid deep squats and repeated dorsiflexion, apply ice after activity, work on ankle mobility and calf flexibility, and consult a sports clinician if pain persists.";
                break;
            default:
                treatmentSuggestion = "No specific treatment found. Consult a healthcare provider for proper care.";
                break;
        }

        return new Treatment(injuryName, treatmentSuggestion); // Return the Treatment object
    }
}