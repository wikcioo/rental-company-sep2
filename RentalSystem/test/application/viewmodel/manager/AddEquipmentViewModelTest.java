package application.viewmodel.manager;

import application.model.Model;
import application.model.ModelManager;
import application.model.equipment.Equipment;
import application.model.users.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.client.FakeRentalSystemClient;

import java.rmi.RemoteException;

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
        viewModel = new AddEquipmentViewModel(model);
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
