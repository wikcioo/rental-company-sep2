package application.shared;

import application.model.Equipment;
import application.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IServer extends Remote {
    Equipment addEquipment(String model, String category, boolean available) throws RemoteException;
    ArrayList<Equipment> getAllEquipment() throws RemoteException;
    ArrayList<Equipment> getAllUnreservedEquipment() throws RemoteException;
    void setAvailability(Equipment equipment, boolean available) throws RemoteException;
    void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException;
    User getUser(String email) throws RemoteException;
    boolean isValidUser(String email, String password) throws RemoteException;
    boolean isUserAManager(String email) throws RemoteException;
}
