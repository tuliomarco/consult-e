package classes;

import java.time.LocalDate;

public class Doctor extends Person {

  private static int instanceDoctorCount = 0;
  private Speciality speciality;
  private String attendenceRoom;
  private String status;

  public Doctor(
    String name,
    String identifier,
    String phoneNumber,
    String email,
    String gender,
    Speciality speciality,
    String attendenceRoom,
    LocalDate dateOfBirth,
    String status
  ) {
    super(
      ++instanceDoctorCount,
      name,
      identifier,
      dateOfBirth,
      phoneNumber,
      email,
      gender
    );
    this.speciality = speciality;
    this.status = status;
    this.attendenceRoom = attendenceRoom;
  }

  public Doctor(
    String name,
    String identifier,
    String phoneNumber,
    String email,
    Speciality speciality,
    String attendenceRoom,
    LocalDate dateOfBirth,
    String status
  ) {
    super(
      ++instanceDoctorCount,
      name,
      identifier,
      dateOfBirth,
      phoneNumber,
      email,
      "Não identificado"
    );
    this.speciality = speciality;
    this.status = status;
    this.attendenceRoom = attendenceRoom;
  }

  public Doctor(
    String name,
    String identifier,
    String phoneNumber,
    String email,
    String gender,
    Speciality speciality,
    String attendenceRoom,
    String status
  ) {
    super(++instanceDoctorCount, name, identifier, phoneNumber, email, gender);
    this.speciality = speciality;
    this.status = status;
    this.attendenceRoom = attendenceRoom;
  }

  public Doctor(
    String name,
    String identifier,
    String phoneNumber,
    String email,
    Speciality speciality,
    String attendenceRoom,
    String status
  ) {
    super(++instanceDoctorCount, name, identifier, phoneNumber, email);
    this.speciality = speciality;
    this.status = status;
    this.attendenceRoom = attendenceRoom;
  }

  public Speciality getSpeciality() {
    return speciality;
  }

  public void setSpeciality(Speciality speciality) {
    this.speciality = speciality;
  }

  public String getAttendenceRoom() {
    return attendenceRoom;
  }

  public void setAttendenceRoom(String attendenceRoom) {
    this.attendenceRoom = attendenceRoom;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return (
      super.toString() +
      ", Especialidade: " +
      speciality +
      ", Status: " +
      status
    );
  }
}
