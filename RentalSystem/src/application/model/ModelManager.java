package application.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelManager implements Model {
    private final EquipmentList equipmentList;
    private final PropertyChangeSupport pcs;
    public static final String EQUIPMENT_LIST_PROPERTY_NAME = "equipment_list_property_name";

    public ModelManager() {
        equipmentList = new EquipmentList();
        pcs = new PropertyChangeSupport(this);
    }

    @Override
    public void addEquipment(Equipment equipment) {
        equipmentList.addEquipment(equipment);
        System.out.println(equipmentList.getAllEquipments().toString());
        pcs.firePropertyChange(EQUIPMENT_LIST_PROPERTY_NAME,null,equipmentList.getAllEquipments());
    }

    @Override public void addListener(String propertyName,
        PropertyChangeListener listener)
    {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    @Override public void removeListener(String propertyName,
        PropertyChangeListener listener)
    {
        pcs.removePropertyChangeListener(propertyName, listener);
    }
}
