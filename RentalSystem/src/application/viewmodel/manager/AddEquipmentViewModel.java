package application.viewmodel.manager;

import application.model.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.rmi.RemoteException;

public class AddEquipmentViewModel {
    private final StringProperty equipmentModel;
    private final StringProperty category;
    private final BooleanProperty isAvailable;
    private final Model model;

    public AddEquipmentViewModel(Model model) {
        this.model = model;
        category = new SimpleStringProperty("");
        equipmentModel = new SimpleStringProperty("");
        isAvailable = new SimpleBooleanProperty();
    }

    public void bindEquipmentModel(StringProperty property) {
        equipmentModel.bind(property);
    }

    public void bindCategory(StringProperty property) {
        category.bind(property);
    }

    public void bindIsAvailable(BooleanProperty property) {
        isAvailable.bind(property);
    }

    public void addEquipment() {
        try {
            model.addEquipment(equipmentModel.get(), category.get(), isAvailable.get());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
