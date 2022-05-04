package application.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModelManager implements Model {
    private final EquipmentList equipmentList;
    private final ArrayList<Reservation> reservationList;
    private final PropertyChangeSupport support;
    public static final String EQUIPMENT_LIST_CHANGED = "equipment_list_changed";
    public static final String RESERVATION_LIST_CHANGED = "reservation_list_changed";

    public ModelManager() {
        equipmentList = new EquipmentList();
        reservationList = new ArrayList<>();
        support = new PropertyChangeSupport(this);
    }

    @Override
    public void addEquipment(Equipment equipment) {
        equipmentList.addEquipment(equipment);
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipments());
    }

    @Override
    public ArrayList<Equipment> getAllEquipment() {
        return equipmentList.getAllEquipment();
    }

    public ArrayList<Reservation> getReservationList() {
        return reservationList;
    }

    @Override
    public void approveReservation(Reservation reservation) {
        reservation.approve();
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList);
    }

    @Override
    public void addReservation(User user, Equipment equipment, LocalDateTime reservationEndDate) {
        reservationList.add(new Reservation(user, equipment, reservationEndDate));
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList);
    }

    @Override
    public void addListener(String propertyName,
                            PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removeListener(String propertyName,
                               PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }

    public boolean logIn(String name, String password) {
        if (name.equals("") && password.equals("")) {
            return true;
        }
        return false;
    }
}
