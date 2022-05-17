package application.model;

import application.client.RentalSystemClient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModelManager implements Model {
    private User currentlyLoggedInUser;
    private final RentalSystemClient client;
    private final EquipmentList equipmentList;
    private final ArrayList<Reservation> reservationList;
    private final PropertyChangeSupport support;
    public static final String EQUIPMENT_LIST_CHANGED = "equipment_list_changed";
    public static final String RESERVATION_LIST_CHANGED = "reservation_list_changed";

    public ModelManager(RentalSystemClient client) {
        this.currentlyLoggedInUser = null;
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

    @Override
    public void retrieveAllEquipment() throws RemoteException {
        equipmentList.clear();
        equipmentList.addEquipmentList(client.getAllEquipment());
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipment());
    }

    @Override
    public void retrieveAllUnreservedEquipment() throws RemoteException {
        equipmentList.clear();
        equipmentList.addEquipmentList(client.getAllUnreservedEquipment());
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipment());
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
    public void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException {
        client.addUser(firstName, lastName, phoneNumber, email, password, isManager);
    }

    @Override
    public User getUser(String email) throws RemoteException {
        return client.getUser(email);
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

    @Override
    public User getCurrentlyLoggedInUser() {
        return currentlyLoggedInUser;
    }

    @Override
    public void setCurrentlyLoggedInUser(User newUser) {
        this.currentlyLoggedInUser = newUser;
    }
}
