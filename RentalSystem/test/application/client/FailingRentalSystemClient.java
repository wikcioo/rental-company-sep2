package application.client;

import application.model.equipment.Equipment;
import application.model.equipment.EquipmentList;
import application.model.reservations.*;
import application.model.users.User;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FailingRentalSystemClient implements RentalSystemClient {
    private final EquipmentList equipmentList;
    private final ReservationList reservationList;
    private final ArrayList<User> userList;
    private int equipmentIndex;
    private int reservationIndex;

    public FailingRentalSystemClient() {
        this.equipmentList = new EquipmentList();
        this.userList = new ArrayList<>();
        this.equipmentIndex = 0;
        this.reservationIndex = 0;
        this.reservationList = new ReservationList();
    }

    @Override
    public Equipment addEquipment(String model, String category, boolean available) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public ArrayList<Equipment> getAllEquipment() throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public ArrayList<Equipment> getAllUnreservedEquipment() throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void setAvailability(int equipment_id, boolean available) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public User getUser(String email) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public boolean isValidUser(String email, String password) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public boolean isUserAManager(String email) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public ArrayList<Reservation> retrieveReservations() throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void approveReservation(int id, String manager_id) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void rejectReservation(int id, String manager_id, String reason) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void expireReservation(int id) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void returnReservation(int id) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void pingServer() throws RemoteException {
        throw new RemoteException();
    }
}



