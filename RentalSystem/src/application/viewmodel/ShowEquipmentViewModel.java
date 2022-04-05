package application.viewmodel;

import application.model.Model;
import application.model.ModelManager;
import application.util.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ShowEquipmentViewModel implements NamedPropertyChangeSubject, PropertyChangeListener
{
  private final Model model;
  private final PropertyChangeSupport pcs;
  public static final String EQUIPMENT_LIST_PROPERTY_NAME = "equipment_list_property_name";

  public ShowEquipmentViewModel(Model model) {
    this.model = model;
    pcs = new PropertyChangeSupport(this);

    model.addListener(ModelManager.EQUIPMENT_LIST_PROPERTY_NAME,this);
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
      case ModelManager.EQUIPMENT_LIST_PROPERTY_NAME -> pcs.firePropertyChange(ShowEquipmentViewModel.EQUIPMENT_LIST_PROPERTY_NAME,null,evt.getNewValue());
    }
    pcs.firePropertyChange(evt);
  }
}
