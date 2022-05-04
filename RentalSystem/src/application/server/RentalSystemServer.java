package application.server;

import application.dao.EquipmentDao;
import application.dao.SQLEquipmentDao;
import application.dao.SQLUserDao;
import application.dao.UserDao;
import application.model.Equipment;
import application.shared.IServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class RentalSystemServer implements IServer {
    private final EquipmentDao equipmentDao;
    private final UserDao userDao;
    public RentalSystemServer() throws RemoteException {
        this.equipmentDao = SQLEquipmentDao.getInstance();
        this.userDao = SQLUserDao.getInstance();
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void addEquipment(Equipment equipment) throws RemoteException {
        try {
            equipmentDao.add(equipment);
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
}
