package application.viewmodel.manager;

import application.client.FailingRentalSystemClient;
import application.model.Model;
import application.model.ModelManager;
import application.model.equipment.Equipment;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerEquipmentViewModelServerFailureTest {
    private ManagerEquipmentViewModel viewModel;
    private StringProperty error;
    private ObjectProperty<Equipment> selectedEquipmentProperty;

    @BeforeEach
    public void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());

        viewModel = new ManagerEquipmentViewModel(model);
        error = new SimpleStringProperty();
        viewModel.bindErrorLabel(error);
        selectedEquipmentProperty = new SimpleObjectProperty<>();
    }

    @Test
    public void server_failure_during_toggling_availability_throws_RuntimeException() {
        viewModel.bindSelectedEquipment(new SimpleObjectProperty<>(new Equipment(0, null, null, true)));
        viewModel.toggleAvailability();
        assertEquals("Server communication error", error.get());
    }

    @Test
    public void server_failure_during_retrieving_all_equipment_sets_error() {
        viewModel.retrieveAllEquipment();
        assertEquals("Server communication error", error.get());
    }
}
