package application.shared;

import application.model.Equipment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IServer extends Remote {
    void addEquipment(Equipment equipment) throws RemoteException;
    ArrayList<Equipment> getAllEquipment() throws RemoteException;
}
