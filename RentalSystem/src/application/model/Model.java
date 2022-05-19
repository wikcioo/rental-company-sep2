package application.model;

import application.model.equipment.Equipment;
import application.model.reservations.*;
import application.model.users.User;
import application.util.NamedPropertyChangeSubject;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface Model extends NamedPropertyChangeSubject {
    void addEquipment(String model, String category, boolean available) throws RemoteException;

    ArrayList<Equipment> getAllEquipment() throws RemoteException;

    ArrayList<Equipment> getAllAvailableEquipment() throws RemoteException;

    void retrieveAllEquipment() throws RemoteException;
    void retrieveAllUnreservedEquipment() throws RemoteException;

    void addReservation(User rentee, Equipment equipment, LocalDateTime reservationEndDate) throws RemoteException;
    void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException;
    User getUser(String email) throws RemoteException;

    String logIn(String email, String password) throws RemoteException;

    ArrayList<Reservation> getApprovedReservationList() throws RemoteException;

    void approveReservation(Reservation reservation) throws RemoteException;

    void toggleAvailability(Equipment equipment) throws RemoteException;

    void editEquipment(Equipment oldEquipment, Equipment newEquipment) throws RemoteException;

    void editEquipment(Equipment equipment, int index) throws RemoteException;

    ArrayList<Reservation> getUnapprovedReservations();
    ArrayList<Approved> getApprovedReservations();
    ArrayList<Rejected> getRejectedReservations();
    ArrayList<Returned> getReturnedReservations();
    ArrayList<Expired> getExpiredReservations();
    void approveReservation(int id, String manager_id) throws RemoteException;
    void rejectReservation(int id, String manager_id) throws RemoteException;
    void expireReservation(int id) throws RemoteException;
    void returnReservation(int id) throws RemoteException;

    void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException;

    User getCurrentlyLoggedInUser();
    void setCurrentlyLoggedInUser(User newUser);
}
