package application.server;


import application.client.RentalSystemClient;
import application.dao.*;
import application.model.equipment.Equipment;
import application.model.reservations.Reservation;
import application.model.users.User;
import application.server.timer.ExpiringReservationTimer;
import application.server.timer.SelectiveReservationTimer;
import application.server.timer.SelectiveReservationTimerImplementation;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RentalSystemServerImplementation extends UnicastRemoteObject implements RentalSystemServer, PropertyChangeListener {

    private final daoRMI dao;
    private final SelectiveReservationTimer expirationTimer;
    private final RemotePropertyChangeSupport<ArrayList> support;

    public RentalSystemServerImplementation(String host, int port) throws RemoteException, NotBoundException {

        this.dao = (daoRMI) LocateRegistry.getRegistry(host, port).lookup("DAO");
        this.expirationTimer = new SelectiveReservationTimerImplementation(1800);
        this.expirationTimer.addListener(ExpiringReservationTimer.RESERVATION_EXPIRED,this);
        this.support = new RemotePropertyChangeSupport<>(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ExpiringReservationTimer.RESERVATION_EXPIRED -> {
                try {
                    expireReservation((int) evt.getNewValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addEquipment(String model, String category, boolean available) throws RemoteException {
        dao.add(model, category, available);
        ArrayList<Equipment> allEquipment = getAllEquipment();
        ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
        support.firePropertyChange("equipmentManager", null, allEquipment);
        support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
    }

    @Override
    public ArrayList<Equipment> getAllEquipment() throws RemoteException {
        return dao.getAll();
    }

    @Override
    public ArrayList<Equipment> getAllUnreservedEquipment() throws RemoteException {
        return dao.getAllUnreserved();
    }

    @Override
    public void setAvailability(int equipment_id, boolean available) throws RemoteException {
        dao.setAvailability(equipment_id, available);
        ArrayList<Equipment> allEquipment = getAllEquipment();
        ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
        support.firePropertyChange("equipmentManager", null, allEquipment);
        support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
    }

    @Override
    public void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException {
        if (isManager) {
            dao.createManager(firstName, lastName, phoneNumber, email, password);
        } else {
            dao.createRentee(firstName, lastName, phoneNumber, email, password);
        }

        ArrayList<User> users = getAllUsers();
        support.firePropertyChange("users", null, users);
    }

    @Override
    public User getUser(String email) throws RemoteException {
        return dao.get(email);
    }

    @Override
    public ArrayList<User> getAllUsers() throws RemoteException {
        return dao.getAllUsers();
    }

    @Override
    public void deleteUser(String email) throws RemoteException {
        dao.delete(email);
        ArrayList<User> users = getAllUsers();
        support.firePropertyChange("users", null, users);
    }

    @Override
    public boolean isValidUser(String email, String password) throws RemoteException {
        return dao.isValidUser(email, password);
    }

    @Override
    public boolean isUserAManager(String email) throws RemoteException {
        return dao.isUserAManager(email);
    }

    @Override
    public ArrayList<Reservation> retrieveReservations() throws RemoteException {
        ArrayList<Reservation> reservations = dao.retrieveReservations();
        expirationTimer.setAllUnapprovedReservations(reservations);
        return reservations;
    }

    @Override
    public void approveReservation(int id, String manager_id) throws RemoteException {
        dao.approveReservation(id, manager_id);
        ArrayList<Reservation> reservations = retrieveReservations();
        support.firePropertyChange("reservations", null, reservations);
    }

    @Override
    public void rejectReservation(int id, String manager_id, String reason) throws RemoteException {
        dao.rejectReservation(id, manager_id, reason);
        ArrayList<Reservation> reservations = retrieveReservations();
        support.firePropertyChange("reservations", null, reservations);
        ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
        support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
    }

    @Override
    public void expireReservation(int id) throws RemoteException {
        dao.expireReservation(id);
        ArrayList<Reservation> reservations = retrieveReservations();
        support.firePropertyChange("reservations", null, reservations);
        ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
        support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
    }

    @Override
    public void returnReservation(int id) throws RemoteException {
        dao.returnReservation(id);
        ArrayList<Reservation> reservations = retrieveReservations();
        support.firePropertyChange("reservations", null, reservations);
        ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
        support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
    }

    @Override
    public void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor, RentalSystemClient sender) throws RemoteException {
        sender.replyReservationId(dao.reserveEquipment(equipment_id, rentee_id, rentedFor));
        ArrayList<Reservation> reservations = retrieveReservations();
        ArrayList<Equipment> allEquipment = getAllEquipment();
        ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
        support.firePropertyChange("reservations", null, reservations);
        support.firePropertyChange("equipmentManager", null, allEquipment);
        support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
    }

    @Override
    public int getExpirationTimeout() throws RemoteException {
        return expirationTimer.getExpirationTimeout();
    }

    @Override
    public void setExpirationTimeout(int expirationTimeout) throws RemoteException {
        expirationTimer.setExpirationTimeout(expirationTimeout);
    }

    @Override
    public void pingServer() throws RemoteException {
    }

    @Override
    public void addPropertyChangeListener(RemotePropertyChangeListener<ArrayList> listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(RemotePropertyChangeListener<ArrayList> listener) {
        support.removePropertyChangeListener(listener);
    }
}
