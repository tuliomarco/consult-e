import java.util.Date;

public class Person {
    private String firstName;
    private String lastName;
    private String socialSecurityNumber;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;

    public Person(
            String firstName,
            String lastName,
            String socialSecurityNumber,
            Date dateOfBirth,
            String address,
            String phoneNumber,
            String email
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Person(
        String firstName,
        String lastName,
        String socialSecurityNumber,
        Date dateOfBirth
) {
    this(firstName, lastName, socialSecurityNumber, dateOfBirth, "", "", "");
}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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

    @Override
    public String toString() {
        return "Person: " +
                "First Name: " + firstName +
                ", Last Name: " + lastName +
                ", SSN: " + socialSecurityNumber +
                ", Date of Birth: " + dateOfBirth +
                ", Address: " + address +
                ", Phone Number: " + phoneNumber +
                ", Email: " + email;
    }
}
