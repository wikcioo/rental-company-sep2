package application.dao;

import application.model.equipment.Equipment;
import application.model.reservations.Reservation;
import application.model.users.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DAOServerImplementation extends UnicastRemoteObject implements daoRMI {
        private final EquipmentDao equipmentDao;
        private final UserDao userDao;
        private final ReservationDao reservationDao;


        public DAOServerImplementation() throws RemoteException {
            this.equipmentDao = SQLEquipmentDao.getInstance();
            this.userDao = SQLUserDao.getInstance();
            this.reservationDao = SQLReservationDao.getInstance();
        }


        @Override
        public void add(String model, String category, boolean available) throws RemoteException {
            try {
                equipmentDao.add(model, category, available);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public ArrayList<Equipment> getAll() throws RemoteException {
            try {
                return equipmentDao.getAll();
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public ArrayList<Equipment> getAllUnreserved() throws RemoteException {
            try {
                return equipmentDao.getAllUnreserved();
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public void setAvailability(int equipment_id, boolean available) throws RemoteException {
            try {
                equipmentDao.setAvailability(equipment_id, available);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public void createManager(String firstName, String lastName, String phoneNumber, String email, String password) throws RemoteException {
            try {
                userDao.createManager(firstName, lastName, phoneNumber, email, password);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public void createRentee(String firstName, String lastName, String phoneNumber, String email, String password) throws RemoteException {
            try {
                userDao.createRentee(firstName, lastName, phoneNumber, email, password);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public User get(String email) throws RemoteException {
            try {
                return userDao.get(email);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public ArrayList<User> getAllUsers() throws RemoteException {
            try {
                return userDao.getAllUsers();
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public void delete(String email) throws RemoteException {
            try {
                userDao.delete(email);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public boolean isValidUser(String email, String password) throws RemoteException {
            try {
                return userDao.isValidUser(email, password);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public boolean isUserAManager(String email) throws RemoteException {
            try {
                return userDao.isUserAManager(email);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }


        @Override
        public ArrayList<Reservation> retrieveReservations() throws RemoteException {

            try {
                return reservationDao.retrieveReservations();
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public void approveReservation(int id, String manager_id) throws RemoteException {
            try {
                reservationDao.approveReservation(id, manager_id);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public void rejectReservation(int id, String manager_id, String reason) throws RemoteException {
            try {
                reservationDao.rejectReservation(id, manager_id, reason);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public void expireReservation(int id) throws RemoteException {
            try {
                reservationDao.expireReservation(id);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public void returnReservation(int id) throws RemoteException {
            try {
                reservationDao.returnReservation(id);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }

        @Override
        public int reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException {
            try {
                return reservationDao.reserveEquipment(equipment_id, rentee_id, rentedFor);
            } catch (SQLException e) {
                throw new RemoteException();
            }
        }
}
