package application.model.models;

import application.model.equipment.Equipment;
import application.model.equipment.EquipmentManager;
import application.model.reservations.Reservation;
import application.model.users.User;
import application.util.NamedPropertyChangeSubject;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface RenteeModel extends NamedPropertyChangeSubject {
    /**
     * Gets all stored equipment from the equipmentList.
     * Delegates to {@link EquipmentManager#getAllEquipment() getAllEquipment} method.
     *
     * @return {@link ArrayList < Equipment >} of all stored equipment
     */
    ArrayList<Equipment> getAllEquipment();

    /**
     * Gets all stored reservations that were made by the current user
     *
     * @return {@link ArrayList < Reservation >} of all stored reservations
     */
    ArrayList<Reservation> getCurrentUserReservations();

    /**
     * Gets the amount of currently overdue equipment of the current user
     *
     * @return number of equipment overdue
     */
    int getCurrentUserOverDueEquipmentAmount();

    /**
     * Clears equipmentList and populates it by calling {@link application.client.RentalSystemClient#getAllUnreservedEquipment() getAllUnreservedEquipment} method.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     *
     * @throws RemoteException indicates connection issues
     */
    void retrieveAllUnreservedEquipment() throws RemoteException;

    /**
     * Gets all available equipment from the equipmentList.
     * Delegates to {@link EquipmentManager#getAllAvailableEquipment() getAllAvailableEquipment} method.
     *
     * @return {@link ArrayList<Equipment>} of all available equipment
     * @throws RemoteException indicates connection issues
     */
    ArrayList<Equipment> getAllAvailableEquipment() throws RemoteException;

    /**
     * Reserves the equipment with given id, by rentee with given id until certain end date
     * Delegates to {@link application.client.RentalSystemClient#reserveEquipment(int, String, LocalDateTime) reserveEquipment} method.
     *
     * @param equipment_id equipment's id
     * @param rentee_id    rentee's id
     * @param rentedFor    expiration date of the reserved equipment
     * @throws RemoteException indicates connection issues
     */
    void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException;

    /**
     * Returns currently logged-in user.
     *
     * @return user object
     */
    User getCurrentlyLoggedInUser();
}
