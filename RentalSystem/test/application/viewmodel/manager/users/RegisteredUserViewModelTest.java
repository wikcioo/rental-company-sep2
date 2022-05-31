package application.viewmodel.manager.users;

import application.client.FakeRentalSystemClient;
import application.model.equipment.Equipment;
import application.model.models.ManagerModel;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.viewmodel.manager.equipment.ManagerEquipmentViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisteredUserViewModelTest {
    private RegisteredUserViewModel viewModel;
    private final String email = "j@g.com";
    private Model model;

    @BeforeEach
    public void setUp() throws RemoteException {
        model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new RegisteredUserViewModel((ManagerModel) model);
        model.setCurrentlyLoggedInUser(model.getUser("john@gmail.com"));
        model.addUser("a", "b", "c", email, "abc", false);
    }

    @Test
    void choosing_to_delete_user_removes_user_from_list() throws RemoteException {
        viewModel.deleteUser(email);
        model.retrieveAllUsers();
        assertEquals(2, model.getAllUsers().size());
    }
}