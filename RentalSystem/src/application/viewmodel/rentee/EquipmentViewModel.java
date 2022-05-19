package application.viewmodel.rentee;

import application.model.equipment.Equipment;
import application.model.Model;
import application.model.ModelManager;
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

public class EquipmentViewModel implements PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<ObservableList<Equipment>> listObjectProperty;
    private final ObjectProperty<Equipment> selectedEquipmentProperty;
    private final ObjectProperty<LocalDateTime> reservationEndDate;
    private final StringProperty equipmentErrorProperty;
    private final StringProperty loggedUserProperty;

    public EquipmentViewModel(Model model) {
        this.model = model;
        this.listObjectProperty = new SimpleObjectProperty<>();
        this.selectedEquipmentProperty = new SimpleObjectProperty<>();
        model.addListener(ModelManager.EQUIPMENT_LIST_CHANGED, this);
        this.reservationEndDate = new SimpleObjectProperty<>();
        this.equipmentErrorProperty = new SimpleStringProperty();
        this.loggedUserProperty = new SimpleStringProperty();
    }

    public void bindEquipmentList(ObjectProperty<ObservableList<Equipment>> property) {
        property.bind(listObjectProperty);
    }

    public void bindSelectedEquipment(SimpleObjectProperty<Equipment> property) {
        selectedEquipmentProperty.bind(property);
    }

    public void bindReservationEndDate(SimpleObjectProperty<LocalDateTime> property) {
        reservationEndDate.bind(property);
    }

    public void bindErrorLabel(StringProperty property) {
        equipmentErrorProperty.bindBidirectional(property);
    }

    public void bindLoggedUser(StringProperty property) {
        loggedUserProperty.bindBidirectional(property);
    }

    public void retrieveAllEquipment() {
        try {
            equipmentErrorProperty.set("Successfully retrieved equipment from DB");
            model.retrieveAllEquipment();
        } catch (RemoteException e) {
            e.printStackTrace();
            equipmentErrorProperty.set("Failed to retrieve equipment list");
        }
    }

    public void retrieveAllUnreservedEquipment() {
        try {
            model.retrieveAllUnreservedEquipment();
            equipmentErrorProperty.set("Successfully retrieved equipment from DB");
        } catch (RemoteException e) {
            e.printStackTrace();
            equipmentErrorProperty.set("Failed to retrieve equipment list");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelManager.EQUIPMENT_LIST_CHANGED -> {
                try {
                    listObjectProperty.setValue(FXCollections.observableList(model.getAllAvailableEquipment()));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void reserveEquipment(){
        try {
            model.addReservation(model.getCurrentlyLoggedInUser(),selectedEquipmentProperty.get(),reservationEndDate.get());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void displayLoggedUser() {
        loggedUserProperty.set("Logged as: " + model.getCurrentlyLoggedInUser().getEmail());
    }
}
