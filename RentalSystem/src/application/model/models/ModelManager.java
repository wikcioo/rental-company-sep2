package application.model.models;

import application.client.RentalSystemClient;
import application.client.RentalSystemClientImplementation;
import application.model.equipment.Equipment;
import application.model.equipment.EquipmentManager;
import application.model.reservations.*;
import application.model.users.CurrentUserManager;
import application.model.users.User;
import application.model.users.UserManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ModelManager implements Model, UserModel, RenteeModel, ManagerModel, PropertyChangeListener {
    private RentalSystemClient client;
    private final EquipmentManager equipmentManager;
    private final ReservationManager reservationManager;
    private final UserManager userManager;
    private final CurrentUserManager currentUserManager;
    private final PropertyChangeSupport support;
    public static final String EQUIPMENT_LIST_CHANGED = "equipment_list_changed";
    public static final String RESERVATION_LIST_CHANGED = "reservation_list_changed";
    public static final String RESERVATION_ID_RECEIVED = "reservation_id_received";
    public static final String USER_LIST_CHANGED = "user_list_changed";

    public ModelManager(RentalSystemClient client) {
        this.client = client;
        try {
            this.client.addListener(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        this.equipmentManager = new EquipmentManager();
        this.currentUserManager = new CurrentUserManager();
        this.reservationManager = new ReservationManager();
        this.userManager = new UserManager();
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void addEquipment(String model, String category, boolean available) throws RemoteException {
        client.addEquipment(model, category, available);
    }

    @Override
    public ArrayList<Equipment> getAllEquipment() {
        return equipmentManager.getAllEquipment();
    }

    @Override
    public ArrayList<Reservation> getCurrentUserReservations() {
        return currentUserManager.getCurrentUserReservations(reservationManager.getAll());
    }

    @Override
    public int getCurrentUserOverDueEquipmentAmount() {
        return currentUserManager.getCurrentUserOverDueEquipmentAmount(reservationManager.getApprovedReservations());
    }

    @Override
    public ArrayList<Equipment> getAllAvailableEquipment() {
        return equipmentManager.getAllAvailableEquipment();
    }

    @Override
    public void retrieveAllEquipment() throws RemoteException {
        equipmentManager.clear();
        equipmentManager.addEquipmentList(client.getAllEquipment());
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentManager.getAllEquipment());
    }

    @Override
    public void retrieveAllUnreservedEquipment() throws RemoteException {
        equipmentManager.clear();
        equipmentManager.addEquipmentList(client.getAllUnreservedEquipment());
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentManager.getAllEquipment());
    }

    @Override
    public void toggleAvailability(Equipment equipment) throws RemoteException {
        equipment.toggleAvailability();
        client.setAvailability(equipment.getEquipmentId(), equipment.isAvailable());
        support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentManager.getAllEquipment());
    }

    @Override
    public void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException {
        client.addUser(firstName, lastName, phoneNumber, email, password, isManager);
        support.firePropertyChange(USER_LIST_CHANGED, null, userManager.getUsers());
    }

    @Override
    public User getUser(String email) throws RemoteException {
        return client.getUser(email);
    }

    @Override
    public void retrieveAllUsers() throws RemoteException {
        userManager.clear();
        userManager.setUsers(client.getAllUsers());
        support.firePropertyChange(USER_LIST_CHANGED, null, userManager.getUsers());
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return userManager.getUsers();
    }

    @Override
    public void deleteUser(String email) throws RemoteException {
        client.deleteUser(email);
        support.firePropertyChange(USER_LIST_CHANGED, null, userManager.getUsers());
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

    public ArrayList<Unapproved> getUnapprovedReservations() {
        return reservationManager.getUnapprovedReservations();
    }

    public ArrayList<Approved> getApprovedReservations() {
        return reservationManager.getApprovedReservations();
    }

    public ArrayList<Rejected> getRejectedReservations() {
        return reservationManager.getRejectedReservations();
    }

    public ArrayList<Returned> getReturnedReservations() {
        return reservationManager.getReturnedReservations();
    }

    public ArrayList<Expired> getExpiredReservations() {
        return reservationManager.getExpiredReservations();
    }

    public void refreshReservations() throws RemoteException {
        reservationManager.setReservationList(client.retrieveReservations());
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationManager.getAll());
    }

    @Override
    public boolean tryToReconnectClient() {
        try {
            client.removeListener(this);
            client = new RentalSystemClientImplementation("localhost", Registry.REGISTRY_PORT);
            client.addListener(this);
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
        support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationManager);
    }

    @Override
    public void rejectReservation(int id, String manager_id, String reason) throws RemoteException {
        client.rejectReservation(id, manager_id, reason);
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
    public int getExpirationTimeout() throws RemoteException {
        return client.getExpirationTimeout();
    }

    @Override
    public void setExpirationTimeout(int expirationTimeout) throws RemoteException {
        client.setExpirationTimeout(expirationTimeout);
    }

    @Override
    public User getCurrentlyLoggedInUser() {
        return currentUserManager.getCurrentlyLoggedInUser();
    }

    @Override
    public void setCurrentlyLoggedInUser(User newUser) {
        this.currentUserManager.setCurrentlyLoggedInUser(newUser);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "reservations" -> {
                reservationManager.setReservationList((ArrayList<Reservation>) evt.getNewValue());
                support.firePropertyChange(RESERVATION_LIST_CHANGED, null, reservationManager.getAll());
            }
            case "reservation_id" -> {
                support.firePropertyChange(RESERVATION_ID_RECEIVED, null, evt.getNewValue());
            }
            case "users" -> {
                userManager.setUsers((ArrayList<User>) evt.getNewValue());
                support.firePropertyChange(USER_LIST_CHANGED, null, userManager.getUsers());
            }
            case "equipmentManager" -> {
                if (currentUserManager.currentUserIsManger()) {
                    equipmentManager.clear();
                    equipmentManager.addEquipmentList((ArrayList<Equipment>) evt.getNewValue());
                    support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentManager.getAllEquipment());
                }
            }
            case "equipmentRentee" -> {
                if (!currentUserManager.currentUserIsManger()) {
                    equipmentManager.clear();
                    equipmentManager.addEquipmentList((ArrayList<Equipment>) evt.getNewValue());
                    support.firePropertyChange(EQUIPMENT_LIST_CHANGED, null, equipmentManager.getAllAvailableEquipment());
                }
            }
        }
    }
}
