package application.model;

import application.client.RentalSystemClient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModelManager implements Model {
    private final RentalSystemClient client;
    private final EquipmentList equipmentList;
    private final ArrayList<Reservation> reservationList;
    private final PropertyChangeSupport support;
    public static final String EQUIPMENT_LIST_CHANGED = "equipment_list_changed";
    public static final String RESERVATION_LIST_CHANGED = "reservation_list_changed";

    public ModelManager(RentalSystemClient client) {
        this.client = client;
        this.equipmentList = new EquipmentList();
        this.reservationList = new ArrayList<>();
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void addEquipment(Equipment equipment) throws RemoteException {
        client.addEquipment(equipment);
        equipmentList.addEquipment(equipment);
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipments());
    }

    @Override
    public void retrieveAllEquipment() throws RemoteException {
        equipmentList.addEquipmentList(client.getAllEquipment());
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipments());
    }

    @Override
    public ArrayList<Reservation> getReservationList() throws RemoteException  {
        return reservationList;
    }

    @Override
    public void approveReservation(Reservation reservation) throws RemoteException  {
        reservation.approve();
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList);
    }

    @Override
    public void addReservation(User user, Equipment equipment, LocalDateTime reservationEndDate) throws RemoteException  {
        reservationList.add(new Reservation(user, equipment, reservationEndDate));
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList);
    }

    public boolean logIn(String name, String password) throws RemoteException  {
        if (name.equals("") && password.equals("")) {
            return true;
        }
        return false;
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
}
