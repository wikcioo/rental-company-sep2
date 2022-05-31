package application.viewmodel.manager.users;

import application.client.FailingRentalSystemClient;
import application.model.equipment.Equipment;
import application.model.models.ManagerModel;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.model.users.User;
import application.viewmodel.manager.equipment.ManagerEquipmentViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisteredUserViewModelServerFailureTest {
    private RegisteredUserViewModel viewModel;
    private StringProperty error;
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new FailingRentalSystemClient());
        viewModel = new RegisteredUserViewModel((ManagerModel) model);
        error = new SimpleStringProperty();
        viewModel.bindErrorLabel(error);
    }
    @Test
    public void server_failure_while_deleting_user() {
        String email = "j@g.com";
        viewModel.deleteUser(email);
        assertEquals("Failed to delete user with email: " + email, error.get());
    }
}
