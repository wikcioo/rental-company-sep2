package application.model.models;

import application.model.equipment.Equipment;
import application.model.reservations.Reservation;
import application.model.users.User;
import application.util.NamedPropertyChangeSubject;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface RenteeModel extends NamedPropertyChangeSubject {
    /**
     * Gets all stored equipment from the equipmentList.
     * Delegates to {@link application.model.equipment.EquipmentList#getAllEquipment() getAllEquipment} method.
     *
     * @return {@link ArrayList < Equipment >} of all stored equipment
     */
    ArrayList<Equipment> getAllEquipment();

    //TODO: [Aivaras] JavaDoc for method
    ArrayList<Reservation> getCurrentUserReservations();
    //TODO: [Aivaras] JavaDoc for method
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
     * Delegates to {@link application.model.equipment.EquipmentList#getAllAvailableEquipment() getAllAvailableEquipment} method.
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
