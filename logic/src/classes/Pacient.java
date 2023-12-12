package classes;

import java.time.LocalDate;

public class Pacient extends Person {

  private static int instancePacientCount = 0;
  private String plan;
  private String status;

  public Pacient(
    String name,
    String identifier,
    LocalDate dateOfBirth,
    String address,
    String phoneNumber,
    String email,
    String gender,
    String plan,
    String status
  ) {
    super(
      ++instancePacientCount,
      name,
      identifier,
      dateOfBirth,
      address,
      phoneNumber,
      email,
      gender
    );
    this.plan = plan;
    this.status = status;
  }

  public Pacient(
    String name,
    String identifier,
    LocalDate dateOfBirth,
    String address,
    String phoneNumber,
    String email,
    String gender,
    String status
  ) {
    super(
      ++instancePacientCount,
      name,
      identifier,
      dateOfBirth,
      address,
      phoneNumber,
      email,
      gender
    );
    this.status = status;
  }

  public String getPlan() {
    return plan;
  }

  public void setPlan(String plano) {
    this.plan = plano;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return super.toString() + ", Plano: " + plan + ", Status: " + status;
  }
}
