package application.viewmodel.manager.equipment;

import application.model.equipment.Equipment;
import application.model.models.ManagerModel;
import application.model.models.ModelManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.List;

public class ManagerEquipmentViewModel implements PropertyChangeListener {
    private final ManagerModel model;
    private final ObjectProperty<ObservableList<Equipment>> listObjectProperty;
    private final ObjectProperty<Equipment> selectedEquipmentProperty;
    private final StringProperty errorProperty;

    public ManagerEquipmentViewModel(ManagerModel model) {
        this.model = model;
        this.listObjectProperty = new SimpleObjectProperty<>();
        this.selectedEquipmentProperty = new SimpleObjectProperty<>();
        model.addListener(ModelManager.EQUIPMENT_LIST_CHANGED, this);
        this.errorProperty = new SimpleStringProperty();
    }

    public void bindEquipmentList(ObjectProperty<ObservableList<Equipment>> property) {
        property.bind(listObjectProperty);
    }

    public void bindSelectedEquipment(SimpleObjectProperty<Equipment> property) {
        selectedEquipmentProperty.bind(property);
    }

    public void bindErrorLabel(StringProperty property) {
        property.bindBidirectional(errorProperty);
    }

    public void toggleAvailability() {
        try {
            model.toggleAvailability(selectedEquipmentProperty.get());
        } catch (RemoteException e) {
            errorProperty.set("Server communication error");
        }
    }

    public void retrieveAllEquipment() {
        try {
            model.retrieveAllEquipment();
        } catch (RemoteException e) {
            e.printStackTrace();
            errorProperty.set("Server communication error");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelManager.EQUIPMENT_LIST_CHANGED -> {
                listObjectProperty.setValue(
                        FXCollections.observableList((List<Equipment>) evt.getNewValue()));
            }
        }
    }

}
