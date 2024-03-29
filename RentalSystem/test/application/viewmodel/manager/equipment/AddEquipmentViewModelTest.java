package application.viewmodel.manager.equipment;

import application.model.models.ManagerModel;
import application.model.models.Model;
import application.model.models.ModelManager;
import application.model.equipment.Equipment;
import application.model.users.User;
import application.viewmodel.manager.equipment.AddEquipmentViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.client.FakeRentalSystemClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddEquipmentViewModelTest {
    private AddEquipmentViewModel viewModel;
    private StringProperty equipmentModel;
    private StringProperty category;
    private Model model;

    @BeforeEach
    void setUp() {
        model = new ModelManager(new FakeRentalSystemClient());
        User user = new User("d", "e", "f", "abc@gmail.com", "def");
        user.setManager(true);
        model.setCurrentlyLoggedInUser(user);
        viewModel = new AddEquipmentViewModel((ManagerModel) model);
        equipmentModel = new SimpleStringProperty();
        category = new SimpleStringProperty();
        viewModel.bindEquipmentModel(equipmentModel);
        viewModel.bindCategory(category);
    }

    @Test
    void equipment_in_equipment_list_when_added() {
        String _equipmentModel = "Raspberry Pi";
        String _category = "Electronics";
        equipmentModel.set(_equipmentModel);
        category.set(_category);
        viewModel.addEquipment();
        Equipment equipment = model.getAllEquipment().get(0);
        assertEquals(_equipmentModel, equipment.getModel());
        assertEquals(_category, equipment.getCategory());
    }
}
