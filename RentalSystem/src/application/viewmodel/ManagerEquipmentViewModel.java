package application.viewmodel;

import application.model.Equipment;
import application.model.Model;
import application.model.ModelManager;
import application.model.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public class ManagerEquipmentViewModel
        implements  PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<ObservableList<Equipment>> listObjectProperty;
    private final ObjectProperty<Equipment> selectedEquipmentProperty;
    private final ObjectProperty<LocalDateTime> reservationEndDate;

    public ManagerEquipmentViewModel(Model model) {
        this.model = model;
        listObjectProperty = new SimpleObjectProperty<>();
        selectedEquipmentProperty = new SimpleObjectProperty<>();
        model.addListener(ModelManager.EQUIPMENT_LIST_CHANGED, this);
        reservationEndDate = new SimpleObjectProperty<>();
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

    public void toggleAvailability(Equipment equipment) {
        try {
            model.toggleAvailability(equipment);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelManager.EQUIPMENT_LIST_CHANGED -> {
                listObjectProperty.setValue(FXCollections.observableList((List<Equipment>) evt.getNewValue()));
            }
        }
    }

    public void reserveEquipment(){
        try {
            model.addReservation(new User("a","b"),selectedEquipmentProperty.get(),reservationEndDate.get());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
