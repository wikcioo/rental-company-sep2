package application.client;

import application.model.equipment.Equipment;
import application.model.reservations.Reservation;
import application.model.users.User;
import application.shared.IServer;
import application.util.NamedPropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RentalSystemClientImplementation extends UnicastRemoteObject implements RentalSystemClient, NamedPropertyChangeSubject {
    private final IServer server;
    private final PropertyChangeSupport support;

    public RentalSystemClientImplementation(String host, int port) throws RemoteException, NotBoundException {
        this.server = (IServer) LocateRegistry.getRegistry(host, port).lookup("Server");
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public Equipment addEquipment(String model, String category, boolean available) throws RemoteException {
        return server.addEquipment(model, category, available);
    }

    @Override
    public ArrayList<Equipment> getAllEquipment() throws RemoteException {
        return server.getAllEquipment();
    }

    @Override
    public ArrayList<Equipment> getAllUnreservedEquipment() throws RemoteException {
        return server.getAllUnreservedEquipment();
    }

    @Override
    public void setAvailability(int equipment_id, boolean available) throws RemoteException {
        server.setAvailability(equipment_id, available);
    }

    @Override
    public void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException {
        server.addUser(firstName, lastName, phoneNumber, email, password, isManager);
    }

    @Override
    public User getUser(String email) throws RemoteException {
        return server.getUser(email);
    }

    @Override
    public boolean isValidUser(String email, String password) throws RemoteException {
        return server.isValidUser(email, password);
    }

    @Override
    public boolean isUserAManager(String email) throws RemoteException {
        return server.isUserAManager(email);
    }

    @Override
    public void addListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removeListener(String propertyName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }

    @Override
    public ArrayList<Reservation> retrieveReservations() throws RemoteException {
        return server.retrieveReservations();
    }

    @Override
    public void approveReservation(int id, String manager_id) throws RemoteException {
        server.approveReservation(id, manager_id);
    }

    @Override
    public void rejectReservation(int id, String manager_id) throws RemoteException {
        server.rejectReservation(id, manager_id);
    }

    @Override
    public void expireReservation(int id) throws RemoteException {
        server.expireReservation(id);
    }

    @Override
    public void returnReservation(int id) throws RemoteException {
        server.returnReservation(id);
    }

    @Override
    public void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException {
        server.reserveEquipment(equipment_id, rentee_id, rentedFor);
    }
}
