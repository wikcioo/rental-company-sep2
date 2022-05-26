package application.viewmodel.manager;

import application.model.Model;
import application.model.ModelManager;
import application.model.equipment.Equipment;
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

public class ManagerMainMenuViewModel implements PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<ObservableList<Equipment>> listObjectProperty;
    private final ObjectProperty<Equipment> selectedEquipmentProperty;
    private final ObjectProperty<LocalDateTime> reservationEndDate;
    private final StringProperty errorProperty;
    private final StringProperty loggedUserProperty;

    public ManagerMainMenuViewModel(Model model) {
        this.model = model;
        this.listObjectProperty = new SimpleObjectProperty<>();
        this.selectedEquipmentProperty = new SimpleObjectProperty<>();
        model.addListener(ModelManager.EQUIPMENT_LIST_CHANGED, this);
        this.reservationEndDate = new SimpleObjectProperty<>();
        this.errorProperty = new SimpleStringProperty();
        this.loggedUserProperty = new SimpleStringProperty();
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

    public void bindLoggedUser(StringProperty property) {
        loggedUserProperty.bindBidirectional(property);
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

    public void displayLoggedUser() {
        loggedUserProperty.set("Logged as: " + model.getCurrentlyLoggedInUser().getEmail());
    }
}
