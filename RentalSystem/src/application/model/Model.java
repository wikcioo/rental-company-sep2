package application.model;

import application.util.NamedPropertyChangeSubject;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface Model extends NamedPropertyChangeSubject {
    void addEquipment(Equipment equipment) throws RemoteException;

    void retrieveAllEquipment() throws RemoteException;

    void addReservation(User rentee, Equipment equipment, LocalDateTime reservationEndDate) throws RemoteException;

    boolean logIn(String name, String password) throws RemoteException;

    ArrayList<Reservation> getReservationList() throws RemoteException;

    void approveReservation(Reservation reservation) throws RemoteException;
}
