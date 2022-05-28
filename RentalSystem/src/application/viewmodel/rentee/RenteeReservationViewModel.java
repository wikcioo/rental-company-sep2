package application.viewmodel.rentee;

import application.model.models.ModelManager;
import application.model.models.RenteeModel;
import application.model.reservations.Reservation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RenteeReservationViewModel implements PropertyChangeListener {
    private final RenteeModel model;
    private final ObjectProperty<ObservableList<Reservation>> listObjectProperty;
    private final StringProperty errorProperty;

    public RenteeReservationViewModel(RenteeModel model) {
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
                listObjectProperty.get().sort((res1, res2) -> {
                    if (res1.getReservationDate().isBefore(res2.getReservationDate())) {
                        return 1;
                    } else {
                        return -1;
                    }
                });
            }
        }
    }

}
