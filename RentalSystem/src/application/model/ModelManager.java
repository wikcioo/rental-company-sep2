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
    public void addEquipment(String model, String category, boolean available) throws RemoteException {
        equipmentList.addEquipment(client.addEquipment(model, category, available));
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipment());
    }


    @Override
    public ArrayList<Equipment> getAllEquipment() throws RemoteException {
        return equipmentList.getAllEquipment();
    }

    @Override
    public ArrayList<Equipment> getAllAvailableEquipment() throws RemoteException {
        return equipmentList.getAllAvailableEquipment();
    }

    //TODO: This only retrieves equipment when the list is empty,
    // but this check can and probably should be removed when rentee and manager views are fully separated
    @Override
    public void retrieveAllEquipment() throws RemoteException {
        if (equipmentList.getAllEquipment().size() == 0) {
            equipmentList.addEquipmentList(client.getAllEquipment());
            support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipment());
        }
    }

    @Override
    public ArrayList<Reservation> getReservationList() throws RemoteException {
        return reservationList;
    }

    @Override
    public ArrayList<Reservation> getApprovedReservationList() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (Reservation r : reservationList) {
            if (r.isApproved()) {
                reservations.add(r);
            }
        }
        return reservations;
    }

    @Override
    public void approveReservation(Reservation reservation) throws RemoteException {
        reservation.approve();
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList);
    }

    @Override
    public void toggleAvailability(Equipment equipment) throws RemoteException {
        equipment.toggleAvailability();
        client.setAvailability(equipment, equipment.isAvailable());
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipment());
    }

    @Override
    public void editEquipment(Equipment oldEquipment, Equipment newEquipment) throws RemoteException {
        int index = equipmentList.getAllEquipment().indexOf(oldEquipment);
        equipmentList.getAllEquipment().set(index, newEquipment);
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipment());
    }

    @Override
    public void addReservation(User user, Equipment equipment, LocalDateTime reservationEndDate) throws RemoteException {
        reservationList.add(new Reservation(user, equipment, reservationEndDate));
        equipment.setAvailable(false);
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipment());
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList);
    }

    @Override
    public void addUser(User user) throws RemoteException {
        client.addUser(user);
    }

    @Override
    public String logIn(String email, String password) throws RemoteException {
        if (client.isValidUser(email, password)) {
            if (client.isUserAManager(email)) return "Manager";
            else return "Rentee";
        }

        return "Invalid";
    }

    @Override
    public void addListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removeListener(String propertyName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }

    public void editEquipment(Equipment equipment, int index) {
        equipmentList.editEquipment(equipment, index);
    }

    @Override
    public void removeReservation(Reservation reservation) throws RemoteException {
        reservation.getEquipment().setAvailable(true);
        reservationList.remove(reservation);
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipment());
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList);
    }
}
