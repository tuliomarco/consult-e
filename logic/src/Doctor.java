import java.util.Date;

public class Doctor extends Person {
    private String crm; 
    private String specialty;
    // private List<Appointment> appointments;

    public Doctor(
            String firstName,
            String lastName,
            String socialSecurityNumber,
            Date dateOfBirth,
            String crm,
            String specialty
    ) {
        super(firstName, lastName, socialSecurityNumber, dateOfBirth);
        this.crm = crm;
        this.specialty = specialty;
        // this.appointments = new ArrayList<>();
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    // public List<Appointment> getAppointments() {
    //     return appointments;
    // }

    // public void addAppointment(Appointment appointment) {
    //     appointments.add(appointment);
    // }

    @Override
    public String toString() {
        return super.toString() +
                ", CRM: " + crm +
                ", Specialty: " + specialty;
    }
}
