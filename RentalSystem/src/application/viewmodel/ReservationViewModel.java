package application.viewmodel;

import application.model.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ReservationViewModel implements PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<ObservableList<Reservation>> listObjectProperty;


    public ReservationViewModel(Model model) {
        this.model = model;
        listObjectProperty = new SimpleObjectProperty<>();
        model.addListener(ModelManager.RESERVATION_LIST_CHANGED, this);
    }

    public void bindReservationList(ObjectProperty<ObservableList<Reservation>> property) {
        property.bind(listObjectProperty);
    }

    public void approveReservation(Reservation reservation) {
        model.approveReservation(reservation);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ModelManager.RESERVATION_LIST_CHANGED -> {
                listObjectProperty.setValue(FXCollections.observableList((List<Reservation>) evt.getNewValue()));
            }
        }
    }
}
