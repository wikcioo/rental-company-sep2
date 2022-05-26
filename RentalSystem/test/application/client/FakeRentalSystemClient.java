package application.client;

import application.model.equipment.Equipment;
import application.model.equipment.EquipmentList;
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
    private final EquipmentList equipmentList;
    private final ReservationList reservationList;
    private final ArrayList<User> userList;
    private int equipmentIndex;
    private int reservationIndex;

    public FakeRentalSystemClient() {
        this.support = new PropertyChangeSupport(this);
        this.equipmentList = new EquipmentList();
        this.userList = new ArrayList<>();
        userList.add(new Manager("a", "b", "c", "john@gmail.com", "123"));
        userList.add(new Rentee("a", "b", "c", "tomas@gmail.com", "abc"));
        this.equipmentIndex = 0;
        this.reservationIndex = 0;
        this.reservationList = new ReservationList();
    }

    @Override
    public void addEquipment(String model, String category, boolean available) throws RemoteException {
        Equipment equipment = new Equipment(equipmentIndex, model, category, available);
        equipmentIndex++;
        equipmentList.addEquipment(equipment);
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
        return equipmentList.getAllEquipment();
    }

    @Override
    public ArrayList<Equipment> getAllUnreservedEquipment() throws RemoteException {
        return equipmentList.getAllAvailableEquipment();
    }

    @Override
    public void setAvailability(int equipment_id, boolean available) throws RemoteException {
        for (Equipment e : equipmentList.getAllEquipment()) {
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
        return new ArrayList<>(reservationList.getAll());
    }

    @Override
    public void approveReservation(int id, String manager_id) throws RemoteException {
        ArrayList<Reservation> reservations = new ArrayList<>(reservationList.getAll());
        for (Reservation r : reservationList.getUnapprovedReservations()) {
            if (r.getId() == id) {
                reservations.set(reservations.indexOf(r), new Approved(r.getId(), r.getRentee(), r.getEquipment(), r.getRentedFor(), LocalDateTime.now(), manager_id));
                break;
            }
        }
        reservationList.setReservationList(reservations);
        support.firePropertyChange("reservations", null, reservations);
    }

    //TODO: rejectReservation requires a reason but the method to reject one does not have a reason field
    @Override
    public void rejectReservation(int id, String manager_id, String reason) throws RemoteException {
        ArrayList<Reservation> reservations = new ArrayList<>(reservationList.getAll());
        for (Reservation r : reservationList.getUnapprovedReservations()) {
            if (r.getId() == id) {
                reservations.set(reservations.indexOf(r), new Rejected(r.getId(), r.getRentee(), r.getEquipment(), r.getRentedFor(), LocalDateTime.now(), "reason", manager_id));
                break;
            }
        }
        reservationList.setReservationList(reservations);
        support.firePropertyChange("reservations", null, reservations);
    }

    @Override
    public void expireReservation(int id) throws RemoteException {
        ArrayList<Reservation> reservations = new ArrayList<>(reservationList.getAll());
        for (Reservation r : reservationList.getUnapprovedReservations()) {
            if (r.getId() == id) {
                reservations.set(reservations.indexOf(r), new Expired(r.getId(), r.getRentee(), r.getEquipment(), r.getRentedFor(), LocalDateTime.now()));
                break;
            }
        }
        reservationList.setReservationList(reservations);
        support.firePropertyChange("reservations", null, reservations);
    }

    @Override
    public void returnReservation(int id) throws RemoteException {
        ArrayList<Reservation> reservations = new ArrayList<>(reservationList.getAll());
        for (Approved a : reservationList.getApprovedReservations()) {
            if (a.getId() == id) {
                reservations.set(reservations.indexOf(a), new Returned(a.getId(), a.getRentee(), a.getEquipment(), a.getRentedFor(), a.getApprovedDate(), a.getApprovedBy(), LocalDateTime.now()));
                break;
            }
        }
        reservationList.setReservationList(reservations);
        support.firePropertyChange("reservations", null, reservations);
    }

    //Reserving will create new equipment with given id
    @Override
    public void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException {
        ArrayList<Reservation> reservations = new ArrayList<>(reservationList.getAll());
        reservations.add(new Reservation(reservationIndex, getUser(rentee_id), LocalDateTime.now(), rentedFor, new Equipment(equipment_id, "application/model", "category", true)));
        reservationList.setReservationList(reservations);
        reservationIndex++;
        ArrayList<Equipment> allEquipment = getAllEquipment();
        ArrayList<Equipment> unreservedEquipment = getAllUnreservedEquipment();
        support.firePropertyChange("reservations", null, reservations);
        support.firePropertyChange("equipmentManager", null, allEquipment);
        support.firePropertyChange("equipmentRentee", null, unreservedEquipment);
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