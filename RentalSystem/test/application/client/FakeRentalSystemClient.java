package application.client;

import application.model.equipment.Equipment;
import application.model.equipment.EquipmentManager;
import application.model.reservations.*;
import application.model.users.Manager;
import application.model.users.Rentee;
import application.model.users.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FakeRentalSystemClient implements RentalSystemClient {
    private final PropertyChangeSupport support;
    private final EquipmentManager equipmentManager;
    private final ReservationManager reservationManager;
    private final ArrayList<User> userList;
    private int equipmentIndex;
    private int reservationIndex;
    private int expirationTimeout;

    public FakeRentalSystemClient() {
        this.support = new PropertyChangeSupport(this);
        this.equipmentManager = new EquipmentManager();
        this.userList = new ArrayList<>();
        userList.add(new Manager("a", "b", "c", "john@gmail.com", "123"));
        userList.add(new Rentee("a", "b", "c", "tomas@gmail.com", "abc"));
        this.equipmentIndex = 0;
        this.reservationIndex = 0;
        this.reservationManager = new ReservationManager();
    }

    @Override
    public void addEquipment(String model, String category, boolean available) throws RemoteException {
        Equipment equipment = new Equipment(equipmentIndex, model, category, available);
        equipmentIndex++;
        equipmentManager.addEquipment(equipment);
        ArrayList<Equipment> allEquipment = getAllEquipment();
        ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
        support.firePropertyChange("equipmentManager", null, allEquipment);
        support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
    }

    @Override
    public void replyReservationId(int id) throws RemoteException {
    }

    @Override
    public ArrayList<Equipment> getAllEquipment() throws RemoteException {
        return equipmentManager.getAllEquipment();
    }

    @Override
    public ArrayList<Equipment> getAllUnreservedEquipment() throws RemoteException {
        return equipmentManager.getAllAvailableEquipment();
    }

    @Override
    public void setAvailability(int equipment_id, boolean available) throws RemoteException {
        for (Equipment e : equipmentManager.getAllEquipment()) {
            if (e.getEquipmentId() == equipment_id) {
                e.setAvailable(available);
            }
        }
        ArrayList<Equipment> allEquipment = getAllEquipment();
        ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
        support.firePropertyChange("equipmentManager", null, allEquipment);
        support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
    }

    @Override
    public void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException {
        if (isManager) {
            userList.add(new Manager(firstName, lastName, phoneNumber, email, password));
        } else {
            userList.add(new Rentee(firstName, lastName, phoneNumber, email, password));
        }
    }

    @Override
    public User getUser(String email) throws RemoteException {
        for (User u : userList) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers() throws RemoteException {
        return userList;
    }

    @Override
    public void deleteUser(String email) throws RemoteException {
        userList.removeIf(u -> u.getEmail().equals(email));
    }

    @Override
    public boolean isValidUser(String email, String password) throws RemoteException {
        for (User u : userList) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isUserAManager(String email) throws RemoteException {
        return getUser(email) instanceof Manager;
    }

    @Override
    public ArrayList<Reservation> retrieveReservations() throws RemoteException {
        return new ArrayList<>(reservationManager.getAll());
    }

    @Override
    public void approveReservation(int id, String manager_id) throws RemoteException {
        ArrayList<Reservation> reservations = new ArrayList<>(reservationManager.getAll());
        for (Reservation r : reservationManager.getUnapprovedReservations()) {
            if (r.getId() == id) {
                reservations.set(reservations.indexOf(r), new Approved(r.getId(), r.getRentee(), r.getEquipment(), r.getRentedFor(), LocalDateTime.now(), manager_id));
                break;
            }
        }
        reservationManager.setReservationList(reservations);
        support.firePropertyChange("reservations", null, reservations);
    }

    @Override
    public void rejectReservation(int id, String manager_id, String reason) throws RemoteException {
        ArrayList<Reservation> reservations = new ArrayList<>(reservationManager.getAll());
        for (Reservation r : reservationManager.getUnapprovedReservations()) {
            if (r.getId() == id) {
                reservations.set(reservations.indexOf(r), new Rejected(r.getId(), r.getRentee(), r.getEquipment(), r.getRentedFor(), LocalDateTime.now(), "reason", manager_id));
                break;
            }
        }
        reservationManager.setReservationList(reservations);
        support.firePropertyChange("reservations", null, reservations);
    }

    @Override
    public void returnReservation(int id) throws RemoteException {
        ArrayList<Reservation> reservations = new ArrayList<>(reservationManager.getAll());
        for (Approved a : reservationManager.getApprovedReservations()) {
            if (a.getId() == id) {
                reservations.set(reservations.indexOf(a), new Returned(a.getId(), a.getRentee(), a.getEquipment(), a.getRentedFor(), a.getApprovedDate(), a.getApprovedBy(), LocalDateTime.now()));
                break;
            }
        }
        reservationManager.setReservationList(reservations);
        support.firePropertyChange("reservations", null, reservations);
    }

    //Reserving will create new equipment with given id
    @Override
    public void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException {
        ArrayList<Reservation> reservations = new ArrayList<>(reservationManager.getAll());
        reservations.add(new Unapproved(reservationIndex, getUser(rentee_id), LocalDateTime.now(), rentedFor, new Equipment(equipment_id, "application/model", "category", true)));
        reservationManager.setReservationList(reservations);
        reservationIndex++;
        ArrayList<Equipment> allEquipment = getAllEquipment();
        ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
        support.firePropertyChange("reservations", null, reservations);
        support.firePropertyChange("equipmentManager", null, allEquipment);
        support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
    }

    @Override
    public int getExpirationTimeout() throws RemoteException {
        return expirationTimeout;
    }

    @Override
    public void setExpirationTimeout(int expirationTimeout) throws RemoteException {
        this.expirationTimeout = expirationTimeout;
    }

    @Override
    public void pingServer() throws RemoteException {
        // do nothing
    }

    @Override
    public void addListener(PropertyChangeListener listener) throws RemoteException {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removeListener(PropertyChangeListener listener) throws RemoteException {
        support.removePropertyChangeListener(listener);
    }

}