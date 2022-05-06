package application.viewmodel;

import application.model.Equipment;
import application.model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.rmi.RemoteException;

public class AddEquipmentViewModel {
    private final StringProperty equipmentModel;
    private final StringProperty category;
    private final Model model;

    public AddEquipmentViewModel(Model model) {
        this.model = model;
        category = new SimpleStringProperty("");
        equipmentModel = new SimpleStringProperty("");
    }

    public void bindEquipmentModel(StringProperty property) {
        equipmentModel.bind(property);
    }

    public void bindCategory(StringProperty property) {
        category.bind(property);
    }

    public void addEquipment() {
        try {
            model.addEquipment(new Equipment(equipmentModel.get(), category.get(), true));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
