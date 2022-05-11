package application.client;

import application.model.Equipment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RentalSystemClient extends Remote {
    void addEquipment(Equipment equipment) throws RemoteException;
    ArrayList<Equipment> getAllEquipment() throws RemoteException;
    boolean isValidUser(String email, String password) throws RemoteException;
    boolean isUserAManager(String email) throws RemoteException;
}
