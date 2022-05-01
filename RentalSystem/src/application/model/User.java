package application.model;

public class User {
    private String firstName;
    private String lastName;
    private String password;

    public User(String firstName, String lastName) {
        this(firstName, lastName, null);
    }

    public User(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
