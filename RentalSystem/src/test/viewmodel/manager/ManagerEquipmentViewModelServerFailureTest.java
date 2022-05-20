package test.viewmodel.manager;

import application.model.Model;
import application.model.ModelManager;
import application.viewmodel.manager.ManagerEquipmentViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.client.FailingRentalSystemClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ManagerEquipmentViewModelServerFailureTest {
    private ManagerEquipmentViewModel viewModel;
    private StringProperty error;

    @BeforeEach
    public void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        viewModel = new ManagerEquipmentViewModel(model);
        error = new SimpleStringProperty();
        viewModel.bindErrorLabel(error);
    }

    @Test
    public void server_failure_during_toggling_availability_throws_RuntimeException() {
        assertThrows(RuntimeException.class, () -> viewModel.toggleAvailability());
    }

    @Test
    public void server_failure_during_retrieving_all_equipment_sets_error() {
        viewModel.retrieveAllEquipment();
        assertEquals("Failed to retrieve equipment list", error.get());
    }
}
