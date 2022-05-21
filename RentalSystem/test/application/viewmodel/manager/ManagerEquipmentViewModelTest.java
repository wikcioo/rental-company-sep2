package application.viewmodel.manager;

import application.client.FakeRentalSystemClient;
import application.model.Model;
import application.model.ModelManager;
import application.model.equipment.Equipment;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerEquipmentViewModelTest {
    private ManagerEquipmentViewModel viewModel;
    private SimpleObjectProperty<Equipment> selectedEquipment;
    private StringProperty loggedUser;
    private StringProperty error;

    @BeforeEach
    public void setUp() throws RemoteException {
        Model model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new ManagerEquipmentViewModel(model);
        selectedEquipment = new SimpleObjectProperty<>(new Equipment(0, "", "", true));
        loggedUser = new SimpleStringProperty();
        error = new SimpleStringProperty();
        viewModel.bindSelectedEquipment(selectedEquipment);
        viewModel.bindLoggedUser(loggedUser);
        viewModel.bindErrorLabel(error);
        model.setCurrentlyLoggedInUser(model.getUser("john@gmail.com"));
    }

    @Test
    public void when_logged_in_display_user_email() {
        viewModel.displayLoggedUser();
        assertEquals("Logged as: john@gmail.com", loggedUser.get());
    }

    @Test
    public void when_successfully_received_all_equipment_sets_success_label(){
        viewModel.retrieveAllEquipment();
        assertEquals("Successfully retrieved equipment from DB", error.get());
    }

    @Test
    public void when_successfully_toggled_availability_method_returns_exception_free() {
        assertDoesNotThrow(() -> viewModel.toggleAvailability());
    }
}
