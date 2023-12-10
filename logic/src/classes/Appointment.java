package classes;

import java.time.LocalDate;


public class Appointment {
    private static int instanceAppointmentCount = 0;
    private int code;
    private LocalDate date;
    private String time;
    private String local;
    private String value;
    private String description;
    private String status;
    private Pacient pacient;
    private Doctor doctor;

    // Construtor com todos os atributos
    public Appointment(LocalDate date, String time, String local, String value, String description, String status, Pacient pacient, Doctor doctor) {
        this.date = date;
        this.time = time;
        this.local = local;
        this.value = value;
        this.description = description;
        this.status = status;
        this.pacient = pacient;
        this.doctor = doctor;
        instanceAppointmentCount++;
        code = instanceAppointmentCount;
    }

    // Construtor sem o valor
    public Appointment(LocalDate date, String local, String time, String description, String status, Doctor doctor, Pacient pacient) {
        this(date, time, local, null, description, status, pacient, doctor);
    }

    // Construtor sem o descricao
    public Appointment(LocalDate date, String value, String local, String time, String status, Pacient pacient, Doctor doctor) {
        this(date, time, local, value, null, status, pacient, doctor);
    }

    // Construtor sem o status
    public Appointment(String time, String local, String value, String description, Pacient pacient, Doctor doctor, LocalDate date) {
        this(date, time, local, value, description, null, pacient, doctor);
    }

    // Construtor sem os 3 atributos citados anteriormente
    public Appointment(LocalDate date, String time, String local, Pacient pacient, Doctor doctor) {
        this(date, time, local, null, null, null, pacient, doctor);
    }

    // Getters e Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return  "Data: " + date +
            ", Descrição: " + description +
            ", Médico: " + doctor +
            ", Local: " + local +
            ", Paciente: " + pacient +
            ", Status: " + status +
            ", Horário: " + time +
            ", Valor: " + value;
    }
}
