package application.server;


import application.dao.*;
import application.model.equipment.Equipment;
import application.model.reservations.Reservation;
import application.model.users.User;
import application.shared.IServer;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RentalSystemServer extends UnicastRemoteObject implements IServer {
    private final EquipmentDao equipmentDao;
    private final UserDao userDao;
    private final ReservationDao reservationDao;
    private final RemotePropertyChangeSupport<ArrayList> support;

    public RentalSystemServer() throws RemoteException {
        this.equipmentDao = SQLEquipmentDao.getInstance();
        this.userDao = SQLUserDao.getInstance();
        this.reservationDao = SQLReservationDao.getInstance();
        this.support = new RemotePropertyChangeSupport<>(this);
    }

    @Override
    public void addEquipment(String model, String category, boolean available) throws RemoteException {
        try {
            equipmentDao.add(model, category, available);
            ArrayList<Equipment> allEquipment = getAllEquipment();
            ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
            support.firePropertyChange("equipmentManager", null, allEquipment);
            support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Equipment> getAllEquipment() throws RemoteException {
        try {
            return equipmentDao.getAll();
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Equipment> getAllUnreservedEquipment() throws RemoteException {
        try {
            return equipmentDao.getAllUnreserved();
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public void setAvailability(int equipment_id, boolean available) throws RemoteException {
        try {
            equipmentDao.setAvailability(equipment_id, available);
            ArrayList<Equipment> allEquipment = getAllEquipment();
            ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
            support.firePropertyChange("equipmentManager", null, allEquipment);
            support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException {
        try {
            if (isManager) {
                userDao.createManager(firstName, lastName, phoneNumber, email, password);
            } else {
                userDao.createRentee(firstName, lastName, phoneNumber, email, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public User getUser(String email) throws RemoteException {
        try {
            return userDao.get(email);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isValidUser(String email, String password) throws RemoteException {
        try {
            return userDao.isValidUser(email, password);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isUserAManager(String email) throws RemoteException {
        try {
            return userDao.isUserAManager(email);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Reservation> retrieveReservations() throws RemoteException {
        try {
            return reservationDao.retrieveReservations();
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public void approveReservation(int id, String manager_id) throws RemoteException {
        try {
            reservationDao.approveReservation(id, manager_id);
            ArrayList<Reservation> reservations = retrieveReservations();
            support.firePropertyChange("reservations", null, reservations);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public void rejectReservation(int id, String manager_id, String reason) throws RemoteException {
        try {
            reservationDao.rejectReservation(id, manager_id, reason);
            ArrayList<Reservation> reservations = retrieveReservations();
            support.firePropertyChange("reservations", null, reservations);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public void expireReservation(int id) throws RemoteException {
        try {
            reservationDao.expireReservation(id);
            ArrayList<Reservation> reservations = retrieveReservations();
            support.firePropertyChange("reservations", null, reservations);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public void returnReservation(int id) throws RemoteException {
        try {
            reservationDao.returnReservation(id);
            ArrayList<Reservation> reservations = retrieveReservations();
            support.firePropertyChange("reservations", null, reservations);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException {
        try {
            reservationDao.reserveEquipment(equipment_id, rentee_id, rentedFor);
            ArrayList<Reservation> reservations = retrieveReservations();
            ArrayList<Equipment> allEquipment = getAllEquipment();
            ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
            support.firePropertyChange("reservations", null, reservations);
            support.firePropertyChange("equipmentManager", null, allEquipment);
            support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage(), e);
        }
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
