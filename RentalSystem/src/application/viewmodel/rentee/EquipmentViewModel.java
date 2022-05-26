package application.viewmodel.rentee;

import application.model.equipment.Equipment;
import application.model.Model;
import application.model.ModelManager;
import javafx.application.Platform;
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
    private final StringProperty reservationErrorProperty;
    private final StringProperty loggedUserProperty;

    public EquipmentViewModel(Model model) {
        this.model = model;
        this.model.addListener(ModelManager.EQUIPMENT_LIST_CHANGED, this);
        this.model.addListener(ModelManager.RESERVATION_ID_RECEIVED, this);
        this.listObjectProperty = new SimpleObjectProperty<>();
        this.selectedEquipmentProperty = new SimpleObjectProperty<>();
        this.reservationEndDate = new SimpleObjectProperty<>();
        this.equipmentErrorProperty = new SimpleStringProperty();
        this.reservationErrorProperty = new SimpleStringProperty();
        this.loggedUserProperty = new SimpleStringProperty();
    }

    public void bindEquipmentList(ObjectProperty<ObservableList<Equipment>> property) {
        property.bind(listObjectProperty);
    }

    public void bindSelectedEquipment(SimpleObjectProperty<Equipment> property) {
        selectedEquipmentProperty.bind(property);
    }

    public void bindReservationEndDate(SimpleObjectProperty<LocalDateTime> property) {
        reservationEndDate.bindBidirectional(property);
    }

    public void bindEquipmentErrorLabel(StringProperty property) {
        property.bindBidirectional(equipmentErrorProperty);
    }
    public void bindReservationErrorLabel(StringProperty property) {
        property.bindBidirectional(reservationErrorProperty);
    }

    public void bindLoggedUser(StringProperty property) {
        loggedUserProperty.bindBidirectional(property);
    }

    public void retrieveAllUnreservedEquipment() {
        try {
            model.retrieveAllUnreservedEquipment();
        } catch (RemoteException e) {
            equipmentErrorProperty.set("Server communication error");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelManager.RESERVATION_ID_RECEIVED ->  {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        reservationErrorProperty.set("Success! reservation id: " + evt.getNewValue());
                    }
                });
            }
            case ModelManager.EQUIPMENT_LIST_CHANGED -> {
                try {
                    listObjectProperty.setValue(FXCollections.observableList(model.getAllAvailableEquipment()));
                } catch (RemoteException e) {
                    equipmentErrorProperty.set("Server communication error");
                }
            }
        }
    }

    public void reserveEquipment() {
        try {
            model.reserveEquipment(selectedEquipmentProperty.get().getEquipmentId(), model.getCurrentlyLoggedInUser().getEmail(), reservationEndDate.get());
        } catch (RemoteException e) {
            reservationErrorProperty.set("Failed to reserve equipment");
        }
    }

    public void displayLoggedUser() {
        loggedUserProperty.set("Logged as: " + model.getCurrentlyLoggedInUser().getEmail());
    }
}
