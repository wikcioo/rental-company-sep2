package application.server;

import application.model.Equipment;
import application.shared.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RentalSystemServer implements IServer {
    public RentalSystemServer() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void addEquipment(Equipment equipment) throws RemoteException {
        // TODO: Complete the method once we have DAO implemented
    }

    @Override
    public ArrayList<Equipment> getAllEquipment() throws RemoteException {
        // TODO: Complete the method once we have DAO implemented
        return null;
    }
}
