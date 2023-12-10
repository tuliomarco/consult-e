package classes;

public class Speciality {
    private static int instanceSpecialitiesCount = 0;
    private int code;
    private int doctorsAmount;
    private String name;

    // Construtor
    public Speciality(String name, int doctorsAmount) {
        instanceSpecialitiesCount++;
        this.code = instanceSpecialitiesCount;
        this.doctorsAmount = doctorsAmount;
        this.name = name;
    }

    public Speciality(String name) {
        this(name, 0);
    }

    // Getters
    public int getCode() {
        return code;
    }

    public int getDoctorsAmount() {
        return doctorsAmount;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setCode(int code) {
        this.code = code;
    }

    public void setDoctorsAmount(int doctorsAmount) {
        this.doctorsAmount = doctorsAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Nome: " + name +
        ", Código: " + code +
        ", Quantidade de médicos: " + doctorsAmount;
    }
}
