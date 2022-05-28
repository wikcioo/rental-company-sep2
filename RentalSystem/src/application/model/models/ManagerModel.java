package application.model.models;

import application.model.equipment.Equipment;
import application.model.reservations.*;
import application.model.users.User;
import application.util.NamedPropertyChangeSubject;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ManagerModel extends NamedPropertyChangeSubject {
    /**
     * Adds new equipment with given parameters to the database and to the equipmentList field.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     * Calls {@link application.client.RentalSystemClient#addEquipment(String, String, boolean) addEquipment} method.
     *
     * @param model     equipment's model
     * @param category  equipment's category
     * @param available equipment's availability
     * @throws RemoteException indicates connection issues
     */
    void addEquipment(String model, String category, boolean available) throws RemoteException;

    /**
     * Gets all stored equipment from the equipmentList.
     * Delegates to {@link application.model.equipment.EquipmentList#getAllEquipment() getAllEquipment} method.
     *
     * @return {@link ArrayList < Equipment >} of all stored equipment
     */
    ArrayList<Equipment> getAllEquipment();

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
     *
     * @throws RemoteException indicates connection issues
     */
    void retrieveAllEquipment() throws RemoteException;

    /**
     * Adds new user to the database with given parameters.
     * Delegates to {@link application.client.RentalSystemClient#addUser(String, String, String, String, String, boolean) addUser} method.
     *
     * @param firstName   user's first name
     * @param lastName    user's last name
     * @param phoneNumber user's phone number
     * @param email       user's email address
     * @param password    user's password
     * @param isManager   user's role (true - manager, false - rentee)
     * @throws RemoteException indicates connection issues
     */
    void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException;

    /**
     * Returns a list of all registered users.
     *
     * @return list of all users
     */
    ArrayList<User> getAllUsers();

    /**
     * Deletes the user with given email address from the list of registered users.
     * Delegates to {@link application.client.RentalSystemClient#deleteUser(String) deleteUser} method.
     *
     * @param email user's email
     * @throws RemoteException indicates connection issues
     */
    void deleteUser(String email) throws RemoteException;

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
     * Delegates to {@link application.model.reservations.ReservationList#getUnapprovedReservations() getUnapprovedReservations} method.
     *
     * @return {@link ArrayList< Reservation >} of unapproved reservations
     */
    ArrayList<Unapproved> getUnapprovedReservations();

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
     * @param id         reservation's id
     * @param manager_id manager's id
     * @throws RemoteException indicates connection issues
     */
    void approveReservation(int id, String manager_id) throws RemoteException;

    /**
     * Rejects the reservation with given id by the manger with given manager_id.
     * Delegates to {@link application.client.RentalSystemClient#rejectReservation(int, String, String) rejectReservation} method.
     *
     * @param id         reservation's id
     * @param manager_id manager's id
     * @param reason     reason given by the manager for rejection
     * @throws RemoteException indicates connection issues
     */
    void rejectReservation(int id, String manager_id, String reason) throws RemoteException;

    /**
     * Makes the reservation with given id returned.
     * Delegates to {@link application.client.RentalSystemClient#returnReservation(int) returnReservation} method.
     *
     * @param id reservation's id
     * @throws RemoteException indicates connection issues
     */
    void returnReservation(int id) throws RemoteException;

    /**
     * Returns current expiration timeout of reservations.
     * Delegates to {@link application.client.RentalSystemClient#getExpirationTimeout() getExpirationTimeout} method.
     *
     * @return expiration timeout of reservations
     * @throws RemoteException indicates connection issues
     */
    int getExpirationTimeout() throws RemoteException;

    /**
     * Sets expiration timeout for reservations.
     * Delegates to {@link application.client.RentalSystemClient#setExpirationTimeout(int) setExpirationTimeout} method.
     *
     * @param expirationTimeout new expiration timeout in seconds
     */
    void setExpirationTimeout(int expirationTimeout) throws RemoteException;

    /**
     * Returns currently logged-in user.
     *
     * @return user object
     */
    User getCurrentlyLoggedInUser();
}
