package application.model;

import application.model.equipment.Equipment;
import application.model.reservations.*;
import application.model.users.User;
import application.util.NamedPropertyChangeSubject;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface Model extends NamedPropertyChangeSubject {
    /**
     * Adds new equipment with given parameters to the database and to the equipmentList field.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     * Calls {@link application.client.RentalSystemClient#addEquipment(String, String, boolean) addEquipment} method.
     *
     * @param model equipment's model
     * @param category equipment's category
     * @param available equipment's availability
     * @throws RemoteException indicates connection issues
     */
    void addEquipment(String model, String category, boolean available) throws RemoteException;

    /**
     * Gets all stored equipment from the equipmentList.
     * Delegates to {@link application.model.equipment.EquipmentList#getAllEquipment() getAllEquipment} method.
     *
     * @return {@link ArrayList<Equipment>} of all stored equipment
     * @throws RemoteException indicates connection issues
     */
    ArrayList<Equipment> getAllEquipment() throws RemoteException;

    /**
     * Gets all available equipment from the equipmentList.
     * Delegates to {@link application.model.equipment.EquipmentList#getAllAvailableEquipment() getAllAvailableEquipment} method.
     *
     * @return {@link ArrayList<Equipment>} of all available equipment
     * @throws RemoteException indicates connection issues
     */
    ArrayList<Equipment> getAllAvailableEquipment() throws RemoteException;

    /**
     * Clears equipmentList and populates it by calling {@link application.client.RentalSystemClient#getAllEquipment() getAllEquipment} method.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     * @throws RemoteException indicates connection issues
     */
    void retrieveAllEquipment() throws RemoteException;

    /**
     * Clears equipmentList and populates it by calling {@link application.client.RentalSystemClient#getAllUnreservedEquipment() getAllUnreservedEquipment} method.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     * @throws RemoteException indicates connection issues
     */
    void retrieveAllUnreservedEquipment() throws RemoteException;

    /**
     * Adds reservation to the database by calling {@link application.client.RentalSystemClient#reserveEquipment(int, String, LocalDateTime) reserveEquipment} method.
     * Fires property change on EQUIPMENT_LIST_CHANGED and RESERVATION_LIST_CHANGED events.
     *
     * @param rentee rentee user object
     * @param equipment equipment object
     * @param reservationEndDate reservation's end date
     * @throws RemoteException indicates connection issues
     */
    void addReservation(User rentee, Equipment equipment, LocalDateTime reservationEndDate) throws RemoteException;

    /**
     * Adds new user to the database with given parameters.
     * Delegates to {@link application.client.RentalSystemClient#addUser(String, String, String, String, String, boolean) addUser} method.
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @param phoneNumber user's phone number
     * @param email user's email address
     * @param password user's password
     * @param isManager user's role (true - manager, false - rentee)
     * @throws RemoteException indicates connection issues
     */
    void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException;

    /**
     * Gets the user from the database by given email address.
     * Delegates to {@link application.client.RentalSystemClient#getUser(String) getUser} method.
     *
     * @param email user's email
     * @return {@link User} that has been added
     * @throws RemoteException indicates connection issues
     */
    User getUser(String email) throws RemoteException;

    /**
     * Returns a String indicating who the user should be treated as (Manager, Rentee or Invalid)
     * Calls {@link application.client.RentalSystemClient#isValidUser(String, String) isValidUser} method to check if the user is valid.
     * Calls {@link application.client.RentalSystemClient#isUserAManager(String) isUserAManager} method to check if the user is a manager, otherwise, rentee.
     *
     * @param email user's email address
     * @param password user's password
     * @return String(Manager, Rentee or Invalid) indicating user's role or invalid
     * @throws RemoteException indicates connection issues
     */
    String logIn(String email, String password) throws RemoteException;

    // TODO[viktor]: Right now the manager_id is hardcoded in the implementation to "john@gmail.com"
    /**
     * Approves the reservation in the database.
     * Calls {@link application.client.RentalSystemClient#approveReservation(int, String) approveReservation} method.
     * Fires property change on RESERVATION_LIST_CHANGED event.
     *
     * @param reservation reservation object
     * @throws RemoteException indicates connection issues
     */
    void approveReservation(Reservation reservation) throws RemoteException;

    /**
     * Toggles the availability of the given equipment.
     * Calls {@link application.client.RentalSystemClient#setAvailability(int, boolean) setAvailability} method with an appropriate boolean value.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     *
     * @param equipment equipment object
     * @throws RemoteException indicates connection issues
     */
    void toggleAvailability(Equipment equipment) throws RemoteException;

    /**
     * Edits and old equipment with a new one
     * Delegates to {@link application.model.equipment.EquipmentList#editEquipment(Equipment, int) editEquipment} method.
     *
     * @param oldEquipment old equipment object
     * @param newEquipment new equipment object
     * @throws RemoteException indicates connection issues
     */
    void editEquipment(Equipment oldEquipment, Equipment newEquipment) throws RemoteException;

    /**
     * Edits and old equipment at given index
     * Delegates to {@link application.model.equipment.EquipmentList#editEquipment(Equipment, int) editEquipment} method.
     *
     * @param equipment new equipment object
     * @param index position of equipment to be replaced with the new one
     * @throws RemoteException indicates connection issues
     */
    void editEquipment(Equipment equipment, int index) throws RemoteException;

    /**
     * Delegates to {@link application.model.reservations.ReservationList#getUnapprovedReservations() getUnapprovedReservations} method.
     *
     * @return {@link ArrayList<Reservation>} of unapproved reservations
     */
    ArrayList<Reservation> getUnapprovedReservations();

    /**
     * Delegates to {@link application.model.reservations.ReservationList#getApprovedReservations() getApprovedReservations} method.
     *
     * @return {@link ArrayList<Reservation>} of approved reservations
     */
    ArrayList<Approved> getApprovedReservations();

    /**
     * Delegates to {@link application.model.reservations.ReservationList#getRejectedReservations() getRejectedReservations} method.
     *
     * @return {@link ArrayList<Reservation>} of rejected reservations
     */
    ArrayList<Rejected> getRejectedReservations();

    /**
     * Delegates to {@link application.model.reservations.ReservationList#getReturnedReservations() getReturnedReservations} method.
     *
     * @return {@link ArrayList<Reservation>} of returned reservations
     */
    ArrayList<Returned> getReturnedReservations();

    /**
     * Delegates to {@link application.model.reservations.ReservationList#getExpiredReservations() getExpiredReservations} method.
     *
     * @return {@link ArrayList<Reservation>} of expired reservations
     */
    ArrayList<Expired> getExpiredReservations();

    /**
     * Approved the reservation with given id by the manager with given manager_id.
     * Delegates to {@link application.client.RentalSystemClient#approveReservation(int, String) approveReservation} method.
     *
     * @param id reservation's id
     * @param manager_id manager's id
     * @throws RemoteException indicates connection issues
     */
    void approveReservation(int id, String manager_id) throws RemoteException;

    /**
     * Rejects the reservation with given id by the manger with given manager_id.
     * Delegates to {@link application.client.RentalSystemClient#rejectReservation(int, String) rejectReservation} method.
     *
     * @param id reservation's id
     * @param manager_id manager's id
     * @throws RemoteException indicates connection issues
     */
    void rejectReservation(int id, String manager_id) throws RemoteException;

    /**
     * Expires the reservation with given id.
     * Delegates to {@link application.client.RentalSystemClient#expireReservation(int) expireReservation} method.
     *
     * @param id reservation's id
     * @throws RemoteException indicates connection issues
     */
    void expireReservation(int id) throws RemoteException;

    /**
     * Makes the reservation with given id returned.
     * Delegates to {@link application.client.RentalSystemClient#returnReservation(int) returnReservation} method.
     *
     * @param id reservation's id
     * @throws RemoteException indicates connection issues
     */
    void returnReservation(int id) throws RemoteException;

    /**
     * Reserves the equipment with given id.
     * Delegates to {@link application.client.RentalSystemClient#reserveEquipment(int, String, LocalDateTime) reserveEquipment} method.
     *
     * @param equipment_id equipment's id
     * @param rentee_id rentee's id
     * @param rentedFor expiration date of the reserved equipment
     * @throws RemoteException indicates connection issues
     */
    void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException;

    /**
     * Returns currently logged-in user
     *
     * @return user object
     */
    User getCurrentlyLoggedInUser();

    /**
     * Sets a new logged-in user
     *
     * @param newUser user to be set as logged-in
     */
    void setCurrentlyLoggedInUser(User newUser);

    /**
     * Refreshes reservationList by setting it with a call to
     * {@link application.client.RentalSystemClient#retrieveReservations() retrieveReservations} method.
     * Fires property change on RESERVATION_LIST_CHANGED event.
     */
    void refreshReservations();
}
