package application.viewmodel.manager;

import application.client.FailingRentalSystemClient;
import application.model.Model;
import application.model.ModelManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddEquipmentViewModelServerFailureTest {
    private AddEquipmentViewModel viewModel;
    private StringProperty equipmentModel;
    private StringProperty category;

    @BeforeEach
    void setUp() {
        Model model = new ModelManager(new FailingRentalSystemClient());
        viewModel = new AddEquipmentViewModel(model);
        equipmentModel = new SimpleStringProperty();
        category = new SimpleStringProperty();
        viewModel.bindEquipmentModel(equipmentModel);
        viewModel.bindCategory(category);
    }

    @Test
    public void server_failure_during_adding_equipment() {
        assertThrows(RuntimeException.class, () -> viewModel.addEquipment());
    }
}
