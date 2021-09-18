public class Patient {
    private int patientID;
    private int healthPoint;
    private int order;

    public Patient(int X, int Y, int order){
        setPatientID(X);
        setHealthPoint(Y);
        setOrder(order);
    }

    private void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    private void setOrder(int order) {
        this.order = order;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getOrder() {
        return order;
    }

    public int getPatientID() {
        return patientID;
    }

    public int getHealthPoint() {
        return healthPoint;
    }
}
