package application.model;

import application.client.RentalSystemClient;
import application.client.RentalSystemClientImplementation;
import application.model.equipment.Equipment;
import application.model.equipment.EquipmentList;
import application.model.reservations.*;
import application.model.users.User;
import application.model.users.UserList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModelManager implements Model, PropertyChangeListener {
    private User currentlyLoggedInUser;
    private RentalSystemClient client;
    private final EquipmentList equipmentList;
    private final ReservationList reservationList;
    private final UserList userList;
    private final PropertyChangeSupport support;
    public static final String EQUIPMENT_LIST_CHANGED = "equipment_list_changed";
    public static final String RESERVATION_LIST_CHANGED = "reservation_list_changed";
    public static final String USER_LIST_CHANGED = "user_list_changed";

    public ModelManager(RentalSystemClient client) {
        this.currentlyLoggedInUser = null;
        this.client = client;
        try {
            this.client.addListener(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        this.equipmentList = new EquipmentList();
        this.reservationList = new ReservationList();
        this.userList = new UserList();
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void addEquipment(String model, String category, boolean available) throws RemoteException {
        client.addEquipment(model, category, available);
    }

    @Override
    public ArrayList<Equipment> getAllEquipment() {
        return equipmentList.getAllEquipment();
    }

    @Override
    public ArrayList<Equipment> getAllAvailableEquipment() {
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
    public void toggleAvailability(Equipment equipment) throws RemoteException {
        equipment.toggleAvailability();
        client.setAvailability(equipment.getEquipmentId(), equipment.isAvailable());
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipment());
    }

    @Override
    public void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException {
        client.addUser(firstName, lastName, phoneNumber, email, password, isManager);
        support.firePropertyChange(USER_LIST_CHANGED, null, userList.getUsers());
    }

    @Override
    public User getUser(String email) throws RemoteException {
        return client.getUser(email);
    }

    @Override
    public void retrieveAllUsers() throws RemoteException {
        userList.clear();
        userList.setUsers(client.getAllUsers());
        support.firePropertyChange(USER_LIST_CHANGED, null, userList.getUsers());
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return userList.getUsers();
    }

    @Override
    public void deleteUser(String email) throws RemoteException {
        client.deleteUser(email);
        support.firePropertyChange(USER_LIST_CHANGED, null, userList.getUsers());
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

    public ArrayList<Reservation> getUnapprovedReservations() {
        return reservationList.getUnapprovedReservations();
    }

    public ArrayList<Approved> getApprovedReservations() {
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

    public void refreshReservations() throws RemoteException {
        reservationList.setReservationList(client.retrieveReservations());
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList.getAll());
    }

    @Override
    public boolean tryToReconnectClient() {
        try {
            client = new RentalSystemClientImplementation("localhost", Registry.REGISTRY_PORT);
            return true;
        } catch (RemoteException | NotBoundException e) {
            return false;
        }
    }

    @Override
    public void pingServer() throws RemoteException {
        client.pingServer();
    }

    @Override
    public void approveReservation(int id, String manager_id) throws RemoteException {
        client.approveReservation(id, manager_id);
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList);
    }

    @Override
    public void rejectReservation(int id, String manager_id, String reason) throws RemoteException {
        client.rejectReservation(id, manager_id, reason);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "reservations" -> {
                reservationList.setReservationList((ArrayList<Reservation>) evt.getNewValue());
                support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationList.getAll());
            }
            case "users" -> {
                userList.setUsers((ArrayList<User>) evt.getNewValue());
                support.firePropertyChange(USER_LIST_CHANGED, null, userList.getUsers());
            }
            case "equipmentManager" -> {
                if (currentlyLoggedInUser.isManager()) {
                    equipmentList.clear();
                    equipmentList.addEquipmentList((ArrayList<Equipment>) evt.getNewValue());
                    support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllEquipment());
                }
            }
            case "equipmentRentee" -> {
                if (!currentlyLoggedInUser.isManager()) {
                    equipmentList.clear();
                    equipmentList.addEquipmentList((ArrayList<Equipment>) evt.getNewValue());
                    support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentList.getAllAvailableEquipment());
                }
            }
        }
    }
}
