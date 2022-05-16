package application.shared;

import application.model.Equipment;
import application.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IServer extends Remote {
    Equipment addEquipment(String model, String category, boolean available) throws RemoteException;
    ArrayList<Equipment> getAllEquipment() throws RemoteException;
    void addUser(User user) throws RemoteException;
    boolean isValidUser(String email, String password) throws RemoteException;
    boolean isUserAManager(String email) throws RemoteException;
}
