package application.viewmodel.manager;

import application.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.rmi.RemoteException;

public class AddUserViewModel {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty phoneNumber;
    private final StringProperty email;
    private final StringProperty password;
    private final StringProperty error;
    private final Model model;

    public AddUserViewModel(Model model) {
        this.model = model;
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.phoneNumber = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.error = new SimpleStringProperty("");
    }

    public void bindFirstName(StringProperty property) {
        firstName.bindBidirectional(property);
    }

    public void bindLastName(StringProperty property) {
        lastName.bindBidirectional(property);
    }

    public void bindPhoneNumber(StringProperty property) {
        phoneNumber.bindBidirectional(property);
    }

    public void bindEmail(StringProperty property) {
        email.bindBidirectional(property);
    }

    public void bindPassword(StringProperty property) {
        password.bindBidirectional(property);
    }

    public void bindError(StringProperty property) {
        error.bindBidirectional(property);
    }

    public boolean addUser(String role) {
        try {
            if (role.equals("Manager")) {
                model.addUser(firstName.getValue(), lastName.getValue(), phoneNumber.getValue(), email.getValue(), password.getValue(), true);
            } else if (role.equals("Rentee")) {
                model.addUser(firstName.getValue(), lastName.getValue(), phoneNumber.getValue(), email.getValue(), password.getValue(), false);
            }
            return true;
        } catch (RemoteException e) {
            error.setValue("Failed to add user to the database");
            return false;
        }
    }
}
