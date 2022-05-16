package application.viewmodel;

import application.model.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public class ManagerEquipmentViewModel implements PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<ObservableList<Equipment>> listObjectProperty;
    private final ObjectProperty<Equipment> selectedEquipmentProperty;
    private final ObjectProperty<LocalDateTime> reservationEndDate;
    private final StringProperty errorProperty;

    public ManagerEquipmentViewModel(Model model) {
        this.model = model;
        listObjectProperty = new SimpleObjectProperty<>();
        selectedEquipmentProperty = new SimpleObjectProperty<>();
        model.addListener(ModelManager.EQUIPMENT_LIST_CHANGED, this);
        reservationEndDate = new SimpleObjectProperty<>();
        errorProperty = new SimpleStringProperty();
    }

    public void bindEquipmentList(ObjectProperty<ObservableList<Equipment>> property) {
        property.bind(listObjectProperty);
    }

    public void bindSelectedEquipment(SimpleObjectProperty<Equipment> property) {
        selectedEquipmentProperty.bind(property);
    }

    public void bindErrorLabel(StringProperty property) {
        property.bind(errorProperty);
    }

    public void toggleAvailability() {
        try {
            model.toggleAvailability(selectedEquipmentProperty.get());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void retrieveAllEquipment() {
        try {
            errorProperty.set("Successfully retrieved equipment from DB");
            model.retrieveAllEquipment();
        } catch (RemoteException e) {
            e.printStackTrace();
            errorProperty.set("Failed to retrieve equipment list");
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
