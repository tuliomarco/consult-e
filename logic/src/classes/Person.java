package classes;

import java.time.LocalDate;

public class Person {

  private int code;
  private String name;
  private String identifier;
  private LocalDate dateOfBirth;
  private String address;
  private String phoneNumber;
  private String email;
  private String gender;

  public Person(
    int code,
    String name,
    String identifier,
    LocalDate dateOfBirth,
    String address,
    String phoneNumber,
    String email,
    String gender
  ) {
    this.code = code;
    this.name = name;
    this.identifier = identifier;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.gender = gender;
  }

  public Person(
    int code,
    String name,
    String identifier,
    LocalDate dateOfBirth,
    String phoneNumber,
    String email,
    String gender
  ) {
    this(code, name, identifier, dateOfBirth, "", phoneNumber, email, gender);
  }

  public Person(
    int code,
    String name,
    String identifier,
    String phoneNumber,
    String email,
    String gender
  ) {
    this(code, name, identifier, null, "", phoneNumber, email, gender);
  }

  public Person(
    int code,
    String name,
    String identifier,
    String phoneNumber,
    String email
  ) {
    this(code, name, identifier, null, "", phoneNumber, email, "");
  }

  public Person(int code, String name, String identifier) {
    this(code, name, identifier, null, "", "", "", "");
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  @Override
  public String toString() {
    return (
      "Nome: " +
      name +
      ", Código: " +
      code +
      ", Identificador: " +
      identifier +
      ", Data de nascimento: " +
      dateOfBirth +
      ", Endereço: " +
      address +
      ", Número de telefone: " +
      phoneNumber +
      ", Email: " +
      email +
      ", Gênero: " +
      gender
    );
  }
}
