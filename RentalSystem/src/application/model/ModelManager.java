package application.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ModelManager implements Model {
    private final EquipmentList equipmentList;
    private final ArrayList<Reservation> reservations;
    private final PropertyChangeSupport pcs;
    public static final String EQUIPMENT_LIST_PROPERTY_NAME = "equipment_list_property_name";

    public ModelManager() {
        equipmentList = new EquipmentList();
        reservations = new ArrayList<>();
        pcs = new PropertyChangeSupport(this);
    }

    @Override
    public void addEquipment(Equipment equipment) {
        equipmentList.addEquipment(equipment);
        System.out.println(equipmentList.getAllEquipments().toString());
        pcs.firePropertyChange(EQUIPMENT_LIST_PROPERTY_NAME,null,equipmentList.getAllEquipments());
    }

    @Override
    public ArrayList<Equipment> getAllEquipment() {
        return equipmentList.getAllEquipment();
    }

    @Override
    public void addReservation(User user, Equipment equipment) {
        reservations.add(new Reservation(user,equipment));
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

    public boolean logIn(String name, String password){
       if(name.equals("") && password.equals("")){
           return true;
       }
       return false;
    }
}
