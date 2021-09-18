public class DataSystem {
    private final DataStructure db_Order = new DataStructure("ORDER");
    private final DataStructure db_HealthPoint = new DataStructure("HEALTH_POINT");
    private final DataStructure db_PatientID = new DataStructure("PATIENT_ID");
    private int patientCounter;

    public DataSystem(){ patientCounter = 0; }

    /**
     * Complexity O(3*Log(n)) ~ O(Log(n))
     */
    public void Add(int X, int Y) {
        db_Order.insert(new Patient(X, Y, patientCounter));
        db_HealthPoint.insert(new Patient(X, Y, patientCounter));
        db_PatientID.insert(new Patient(X, Y, patientCounter++));
    }

    /**
     * Complexity O(4*Log(n)) ~ O(Log(n))
     */
    public void Update(int patientID, int healthPoint) {
        int IGNORED_VALUE = -1;
        Node target = db_PatientID.search(new Patient(patientID, Integer.MIN_VALUE, IGNORED_VALUE));
        if (target == null) {
            System.out.println(TextColors.RED + "Sorry, Couldn't find patient matching this ID!" + TextColors.RESET);
            return;
        }
        db_Order.search(target.data).data.setHealthPoint(healthPoint); //O(Log(n)) for Search
        db_HealthPoint.delete(target.data); //O(2Log(n)) for Search & delete
        db_HealthPoint.insert(new Patient(target.data.getPatientID(), healthPoint, target.data.getOrder())); //O(Log(n))
        target.data.setHealthPoint(healthPoint);
    }

    /**
     * Complexity O(6*Log(n)) ~ O(Log(n))
     */
    public void ServeFirst() {
        Node node = db_Order.minimumNode(db_Order.root);
        if (node == null){
            System.out.println(TextColors.CYAN + "Oops! seems theres is no patient to serve!" + TextColors.RESET);
            return;
        }
        Patient patient = node.data;
        System.out.println(TextColors.YELLOW + patient.getPatientID() + " " + patient.getHealthPoint() + TextColors.RESET);
        deleteServedFormOrderDataStructure(patient);
        deleteServedFormHPDataStructure(patient);
        deleteServedFormPIDDataStructure(patient);
    }

    /**
     * Complexity O(4*Log(n)) ~ O(Log(n))
     * */
    public void ServeSickest() {
        Node node = db_HealthPoint.minimumNode(db_HealthPoint.root);
        if (node == null){
            System.out.println("Oops! seems theres is no patient to serve!");
            return;
        }
        Patient patient = node.data;
        System.out.println(TextColors.YELLOW + patient.getPatientID() + " " + patient.getHealthPoint() + TextColors.RESET);
        deleteServedFormHPDataStructure(patient);
        deleteServedFormOrderDataStructure(patient);
        deleteServedFormPIDDataStructure(patient);
    }

    /**
     * Complexity O(2Log(n)) ~ O(Log(n))
     * */
    private void deleteServedFormHPDataStructure(Patient patient){
        db_HealthPoint.delete(patient);
    }

    /**
     * Complexity O(2Log(n)) ~ O(Log(n))
     * */
    private void deleteServedFormOrderDataStructure(Patient patient){
        db_Order.delete(patient); // O(log(n))
    }

    /**
     * Complexity O(2Log(n)) ~ O(Log(n))
     * */
    private void deleteServedFormPIDDataStructure(Patient patient){
        db_PatientID.delete(patient); // O(log(n))
    }
}
