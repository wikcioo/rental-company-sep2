package application.client;

import application.model.Equipment;
import application.shared.IServer;
import application.util.NamedPropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RentalSystemClientImplementation extends UnicastRemoteObject implements RentalSystemClient, NamedPropertyChangeSubject {
    private final IServer server;
    private final PropertyChangeSupport support;

    public RentalSystemClientImplementation(String host, int port) throws RemoteException, NotBoundException {
        this.server = (IServer) LocateRegistry.getRegistry(host, port).lookup("Server");
        this.support = new PropertyChangeSupport(this);
    }
    @Override
    public void addEquipment(Equipment equipment) throws RemoteException {
        server.addEquipment(equipment);
    }

    @Override
    public ArrayList<Equipment> getAllEquipment() throws RemoteException {
        return server.getAllEquipment();
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
}
