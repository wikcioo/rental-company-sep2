package application.model;

import application.util.NamedPropertyChangeSubject;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface Model extends NamedPropertyChangeSubject {
    void addEquipment(Equipment equipment) throws RemoteException;

    ArrayList<Equipment> getAllEquipment()
        throws RemoteException;
    void retrieveAllEquipment() throws RemoteException;

    void addReservation(User rentee, Equipment equipment, LocalDateTime reservationEndDate) throws RemoteException;

    boolean logIn(String name, String password) throws RemoteException;

    ArrayList<Reservation> getReservationList() throws RemoteException;

    ArrayList<Reservation> getApprovedReservationList();

    void approveReservation(Reservation reservation) throws RemoteException;

    void toggleAvailability(Equipment equipment) throws RemoteException;

    void editEquipment(Equipment oldEquipment, Equipment newEquipment) throws RemoteException;

    void editEquipment(Equipment equipment, int index) throws RemoteException;
}
