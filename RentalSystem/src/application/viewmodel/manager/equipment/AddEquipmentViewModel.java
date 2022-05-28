package application.viewmodel.manager.equipment;

import application.model.models.ManagerModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.rmi.RemoteException;

public class AddEquipmentViewModel {
    private final StringProperty equipmentModel;
    private final StringProperty category;
    private final BooleanProperty isAvailable;
    private final ManagerModel model;
    private final StringProperty errorProperty;

    public AddEquipmentViewModel(ManagerModel model) {
        this.model = model;
        category = new SimpleStringProperty("");
        equipmentModel = new SimpleStringProperty("");
        isAvailable = new SimpleBooleanProperty();
        this.errorProperty = new SimpleStringProperty();
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
            errorProperty.set("Server communication error");
        }
    }

    public void bindErrorLabel(StringProperty property) {
        property.bindBidirectional(errorProperty);
    }
}
