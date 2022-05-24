package application.client;

import application.model.equipment.Equipment;
import application.model.reservations.Reservation;
import application.model.users.User;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface RentalSystemClient extends Remote {
    /**
     * Adds new equipment with given parameters to the database.
     * Calls {@link application.shared.IServer#addEquipment(String, String, boolean) addEquipment} method.
     *
     * @param model     equipment's model
     * @param category  equipment's category
     * @param available equipment's availability
     * @throws RemoteException indicates connection failure
     */
    void addEquipment(String model, String category, boolean available) throws RemoteException;

    /**
     * Gets all stored equipment from the database.
     * Calls {@link application.shared.IServer#getAllEquipment() getAllEquipment} method.
     *
     * @return {@link ArrayList<Equipment>} of all stored equipment
     * @throws RemoteException indicates connection failure
     */
    ArrayList<Equipment> getAllEquipment() throws RemoteException;

    /**
     * Gets all stored equipment from the database that has not been reserved.
     * Calls {@link application.shared.IServer#getAllUnreservedEquipment() getAllUnreservedEquipment} method.
     *
     * @return {@link ArrayList<Equipment>} of all unreserved equipment
     * @throws RemoteException indicates connection failure
     */
    ArrayList<Equipment> getAllUnreservedEquipment() throws RemoteException;

    /**
     * Sets the availability of given equipment object in the database.
     * Calls {@link application.shared.IServer#setAvailability(int, boolean) setAvailability} method.
     *
     * @param equipment_id equipment's id
     * @param available    equipment's availability
     * @throws RemoteException indicates connection failure
     */
    void setAvailability(int equipment_id, boolean available) throws RemoteException;

    /**
     * Adds new user to the database with given parameters.
     * Calls {@link application.shared.IServer#addUser(String, String, String, String, String, boolean) addUser} method.
     *
     * @param firstName   user's first name
     * @param lastName    user's last name
     * @param phoneNumber user's phone number
     * @param email       user's email address
     * @param password    user's password
     * @param isManager   user's role (true - manager, false - rentee)
     * @throws RemoteException indicates connection failure
     */
    void addUser(String firstName, String lastName, String phoneNumber, String email, String password, boolean isManager) throws RemoteException;

    /**
     * Gets the user from the database by given email address.
     * Calls {@link application.shared.IServer#getUser(String) getUser} method.
     *
     * @param email user's email
     * @return {@link User} that has been added
     * @throws RemoteException indicates connection failure
     */
    User getUser(String email) throws RemoteException;

    /**
     * Returns a list of all users in the database.
     * Calls {@link application.shared.IServer#getAllUsers() getAllUsers} method.
     *
     * @return list of all users
     * @throws RemoteException indicates connection failure
     */
    ArrayList<User> getAllUsers() throws RemoteException;

    /**
     * Deletes user with given email address from the database.
     * Calls {@link application.shared.IServer#deleteUser(String) deleteUser} method.
     *
     * @param email - user's email
     * @throws RemoteException indicates connection failure
     */
    void deleteUser(String email) throws RemoteException;

    /**
     * Returns true if the user with given email address and password is a valid user in the database.
     * Otherwise, returns false.
     * Calls {@link application.shared.IServer#isValidUser(String, String) isValidUser} method.
     *
     * @param email    user's email address
     * @param password user's password
     * @return boolean indicating if the user is a valid one
     * @throws RemoteException indicates connection failure
     */
    boolean isValidUser(String email, String password) throws RemoteException;

    /**
     * Returns true if the user with given email address is a manager.
     * Otherwise, returns false indicating the user is a rentee.
     * Calls {@link application.shared.IServer#isUserAManager(String) isUserAManager} method.
     *
     * @param email user's email
     * @return boolean indicating if the user is a manager
     * @throws RemoteException indicates connection failure
     */
    boolean isUserAManager(String email) throws RemoteException;

    /**
     * Retrieves all reservations from the database.
     * Calls {@link application.shared.IServer#retrieveReservations() retrieveReservations} method.
     *
     * @return {@link ArrayList<Reservation>} of all retrieved reservations
     * @throws RemoteException indicates connection failure
     */
    ArrayList<Reservation> retrieveReservations() throws RemoteException;

    /**
     * Approves the reservation with given id identified by the manager with given manager_id in the database.
     * Calls {@link application.shared.IServer#approveReservation(int, String) approveReservation} method.
     *
     * @param id         reservation's id
     * @param manager_id manager's id
     * @throws RemoteException indicates connection failure
     */
    void approveReservation(int id, String manager_id) throws RemoteException;

    /**
     * Rejects the reservation with given id identified by the manager with given manager_id in the database
     * Calls {@link application.shared.IServer#rejectReservation(int, String, String) rejectReservation} method.
     *
     * @param id         reservation's id
     * @param manager_id manager's id
     * @param reason reason given by the manager for rejection
     * @throws RemoteException indicates connection failure
     */
    void rejectReservation(int id, String manager_id, String reason) throws RemoteException;

    /**
     * Expires the reservation with given id in the database.
     * Calls {@link application.shared.IServer#expireReservation(int) expireReservation} method.
     *
     * @param id reservation's id
     * @throws RemoteException indicates connection failure
     */
    void expireReservation(int id) throws RemoteException;

    /**
     * Makes the reservation with given id being returned, in the database.
     * Calls {@link application.shared.IServer#returnReservation(int) returnReservation} method.
     *
     * @param id reservation's id
     * @throws RemoteException indicates connection failure
     */
    void returnReservation(int id) throws RemoteException;

    /**
     * Reserves the equipment with given id in the database.
     * Calls {@link application.shared.IServer#reserveEquipment(int, String, LocalDateTime) reserveEquipment} method.
     *
     * @param equipment_id equipment's id
     * @param rentee_id    rentee's id
     * @param rentedFor    expiration date of the reserved equipment
     * @throws RemoteException indicates connection failure
     */
    void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException;

    /**
     * Checks if there is connectivity with the server. Throws exception if it cannot connect.
     * Calls {@link application.shared.IServer#pingServer() pingServer} method.
     *
     * @throws RemoteException indicates connection failure
     */
    void pingServer() throws RemoteException;


    /**
     * Adds a listener to start listening to events in the client
     *
     * @param listener the listener which will catch events
     * @throws RemoteException indicates connection failure
     */
    void addListener(PropertyChangeListener listener) throws RemoteException;

    /**
     * Removes a listener to stop listening to events in the client
     *
     * @param listener the listener which will catch events
     * @throws RemoteException indicates connection failure
     */
    void removeListener(PropertyChangeListener listener) throws RemoteException;
}
