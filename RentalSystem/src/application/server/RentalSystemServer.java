package application.server;

import application.dao.EquipmentDao;
import application.dao.SQLEquipmentDao;
import application.dao.SQLUserDao;
import application.dao.UserDao;
import application.model.Equipment;
import application.model.User;
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
    public Equipment addEquipment(String model, String category, boolean available) throws RemoteException {
        try {
            return equipmentDao.add(model, category, available);
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
    public void setAvailability(Equipment equipment, boolean available) throws RemoteException {
        try {
            equipmentDao.setAvailability(equipment, available);
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
}
