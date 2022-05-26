package application.viewmodel.rentee;

import application.model.Model;
import application.model.ModelManager;
import application.model.reservations.Approved;
import application.model.reservations.Reservation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class RenteeReservationViewModel implements PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<ObservableList<Reservation>> listObjectProperty;
    private final StringProperty errorProperty;

    public RenteeReservationViewModel(Model model) {
        this.model = model;
        listObjectProperty = new SimpleObjectProperty<>();
        listObjectProperty.setValue(FXCollections.observableList(model.getCurrentUserReservations()));
        model.addListener(ModelManager.RESERVATION_LIST_CHANGED, this);
        this.errorProperty = new SimpleStringProperty();
    }

    public void bindReservationList(ObjectProperty<ObservableList<Reservation>> property) {
        property.bind(listObjectProperty);
    }

    public void bindErrorLabel(StringProperty property) {
        property.bindBidirectional(errorProperty);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelManager.RESERVATION_LIST_CHANGED -> {
                listObjectProperty.setValue(FXCollections.observableList(model.getCurrentUserReservations()));
            }
        }
    }

}
