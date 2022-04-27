package application.viewmodel;

import application.model.Equipment;
import application.model.Model;
import application.model.ModelManager;
import application.util.NamedPropertyChangeSubject;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;

public class EquipmentViewModel
    implements NamedPropertyChangeSubject, PropertyChangeListener
{
  private final Model model;
  private final PropertyChangeSupport pcs;
  private final ObjectProperty<ObservableList<Equipment>> listObjectProperty;
  public static final String EQUIPMENT_LIST_PROPERTY_NAME = "equipment_list_property_name";

  public EquipmentViewModel(Model model) {
    this.model = model;
    pcs = new PropertyChangeSupport(this);
    listObjectProperty = new SimpleObjectProperty<>();

    model.addListener(ModelManager.EQUIPMENT_LIST_PROPERTY_NAME,this);
  }

  public void bindEquipmentList(ObjectProperty<ObservableList<Equipment>> property) {
    property.bind(listObjectProperty);
  }

  @Override public void addListener(String propertyName, PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(propertyName,listener);
  }


  @Override public void removeListener(String propertyName, PropertyChangeListener listener) {
    pcs.removePropertyChangeListener(propertyName,listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    switch (evt.getPropertyName()) {
      case ModelManager.EQUIPMENT_LIST_PROPERTY_NAME -> {
        listObjectProperty.setValue(FXCollections.observableArrayList((Collection<Equipment>) evt.getNewValue()));
      }
    }
    pcs.firePropertyChange(evt);
  }
}
