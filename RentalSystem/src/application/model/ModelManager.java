package application.model;

import application.client.RentalSystemClient;
import application.model.equipment.Equipment;
import application.model.equipment.EquipmentList;
import application.model.reservations.*;
import application.model.users.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModelManager implements Model {
    private User currentlyLoggedInUser;
    private final RentalSystemClient client;
    private final EquipmentList equipmentList;
    private final IReservationList reservationList;
    private final PropertyChangeSupport support;
    public static final String EQUIPMENT_LIST_CHANGED = "equipment_list_changed";
    public static final String RESERVATION_LIST_CHANGED = "reservation_list_changed";

    public ModelManager(RentalSystemClient client) {
        this.currentlyLoggedInUser = null;
        this.client = client;
        this.equipmentList = new EquipmentList();
        this.reservationList = new ReservationList();
        this.support = new PropertyChangeSupport(this);
        refreshReservations();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    refreshReservations();
                    System.out.println("Refreshed");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();

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
    public void approveReservation(Reservation reservation) throws RemoteException {
        client.approveReservation(reservation.getId(), "john@gmail.com");
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
        client.reserveEquipment(equipment.getEquipmentId(),user.getEmail(),reservationEndDate);
        equipment.setAvailable(false);
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipment());
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList.getAll());
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


    // TODO: DEPRECATED
    public void editEquipment(Equipment equipment, int index) {
        equipmentList.editEquipment(equipment, index);
    }

    public ArrayList<Reservation> getUnapprovedReservations() {
        return reservationList.getUnapprovedReservations();
    }

    public ArrayList<Approved> getApprovedReservations() {
        System.out.println(reservationList.getApprovedReservations());
        return reservationList.getApprovedReservations();
    }

    public ArrayList<Rejected> getRejectedReservations() {
        return reservationList.getRejectedReservations();
    }

    public ArrayList<Returned> getReturnedReservations() {
        return reservationList.getReturnedReservations();
    }

    public ArrayList<Expired> getExpiredReservations() {
        return reservationList.getExpiredReservations();
    }

    public void refreshReservations() {
        try {
            reservationList.setReservationList(client.retrieveReservations());
            support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList.getAll());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void approveReservation(int id, String manager_id) throws RemoteException {
        client.approveReservation(id, manager_id);
    }

    @Override
    public void rejectReservation(int id, String manager_id) throws RemoteException {
        client.rejectReservation(id, manager_id);

    }

    @Override
    public void expireReservation(int id) throws RemoteException {
        client.expireReservation(id);
    }

    @Override
    public void returnReservation(int id) throws RemoteException {
        client.returnReservation(id);

    }

    @Override
    public void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException {
        client.reserveEquipment(equipment_id, rentee_id, rentedFor);
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
