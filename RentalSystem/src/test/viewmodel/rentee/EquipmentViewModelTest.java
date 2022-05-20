package test.viewmodel.rentee;

import application.model.Model;
import application.model.ModelManager;
import application.viewmodel.rentee.EquipmentViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.client.FakeRentalSystemClient;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquipmentViewModelTest {
    private EquipmentViewModel viewModel;
    private StringProperty loggedUserProperty;
    private StringProperty error;

    @BeforeEach
    void setUp() throws RemoteException {
        Model model = new ModelManager(new FakeRentalSystemClient());
        viewModel = new EquipmentViewModel(model);
        loggedUserProperty = new SimpleStringProperty();
        error = new SimpleStringProperty();
        viewModel.bindErrorLabel(error);
        viewModel.bindLoggedUser(loggedUserProperty);
        model.setCurrentlyLoggedInUser(model.getUser("john@gmail.com"));

    }

    @Test
    void when_logged_in_displays_user_email(){
        viewModel.displayLoggedUser();
        assertEquals("Logged as: john@gmail.com", loggedUserProperty.get());
    }

    //TODO: the String Property should probably be renamed from error to result
    @Test
    void when_successfully_received_all_equipment_sets_success_label(){
        viewModel.retrieveAllEquipment();
        assertEquals("Successfully retrieved equipment from DB", error.get());
    }

    @Test
    void when_successfully_received_unreserved_equipment_sets_success_label(){
        viewModel.retrieveAllUnreservedEquipment();
        assertEquals("Successfully retrieved equipment from DB", error.get());
    }

}