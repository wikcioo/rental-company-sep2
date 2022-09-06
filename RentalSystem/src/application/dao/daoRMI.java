package application.dao;

import application.model.equipment.Equipment;
import application.model.reservations.Reservation;
import application.model.users.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface daoRMI  extends Remote {
    
    void add(String model, String category, boolean available) throws RemoteException;

    
    ArrayList<Equipment> getAll() throws RemoteException;

    
    ArrayList<Equipment> getAllUnreserved() throws RemoteException;

    
    void setAvailability(int equipment_id, boolean available) throws RemoteException;

    
    ArrayList<Reservation> retrieveReservations() throws RemoteException;

    
    void approveReservation(int id, String manager_id) throws RemoteException;

    
    void rejectReservation(int id, String manager_id, String reason) throws RemoteException;

    
    void expireReservation(int id) throws RemoteException;

    
    void returnReservation(int id) throws RemoteException;

    
    int reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException;

    
    void createManager(String firstName, String lastName, String phoneNumber, String email, String password) throws RemoteException;

    
    void createRentee(String firstName, String lastName, String phoneNumber, String email, String password) throws RemoteException;

    
    User get(String email) throws RemoteException;

    
    ArrayList<User> getAllUsers() throws RemoteException;

    
    void delete(String email) throws RemoteException;

    
    boolean isValidUser(String email, String password) throws RemoteException;

    
    boolean isUserAManager(String email) throws RemoteException;
}
