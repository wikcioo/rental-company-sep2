package application.viewmodel.manager;

import application.model.models.ManagerModel;
import application.model.models.ModelManager;
import application.model.reservations.Rejected;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RejectedReservationViewModel implements PropertyChangeListener {
    private final ManagerModel model;
    private final ObjectProperty<ObservableList<Rejected>> listObjectProperty;
    private final StringProperty errorProperty;

    public RejectedReservationViewModel(ManagerModel model) {
      this.model = model;
      listObjectProperty = new SimpleObjectProperty<>();
      listObjectProperty.setValue(FXCollections.observableList(model.getRejectedReservations()));
      model.addListener(ModelManager.RESERVATION_LIST_CHANGED, this);
      this.errorProperty = new SimpleStringProperty();
    }

    public void bindReservationList(ObjectProperty<ObservableList<Rejected>> property) {
      property.bind(listObjectProperty);
    }

    public void bindErrorLabel(StringProperty property) {
      property.bindBidirectional(errorProperty);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      switch (evt.getPropertyName()) {
        case ModelManager.RESERVATION_LIST_CHANGED -> {
          listObjectProperty.setValue(FXCollections.observableList(model.getRejectedReservations()));
        }
      }
    }

    }

