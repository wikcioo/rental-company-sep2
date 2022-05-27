package application.shared;

import application.client.RentalSystemClient;
import application.model.equipment.Equipment;
import application.model.reservations.Reservation;
import application.model.users.User;
import dk.via.remote.observer.RemotePropertyChangeListener;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IServer extends Remote {
    /**
     * Adds new equipment with given parameters to the database.
     * Calls {@link application.dao.EquipmentDao#add(String, String, boolean) add} method.
     *
     * @param model     equipment's model
     * @param category  equipment's category
     * @param available equipment's availability
     * @throws RemoteException indicates connection failure
     */
    void addEquipment(String model, String category, boolean available) throws RemoteException;

    /**
     * Gets all stored equipment from the database.
     * Calls {@link application.dao.EquipmentDao#getAll() getAll} method.
     *
     * @return {@link ArrayList<Equipment>} of all stored equipment
     * @throws RemoteException indicates connection failure
     */
    ArrayList<Equipment> getAllEquipment() throws RemoteException;

    /**
     * Gets all stored equipment from the database that has not been reserved.
     * Calls {@link application.dao.EquipmentDao#getAllUnreserved() getAllUnreserved} method.
     *
     * @return {@link ArrayList<Equipment>} of all unreserved equipment
     * @throws RemoteException indicates connection failure
     */
    ArrayList<Equipment> getAllUnreservedEquipment() throws RemoteException;

    /**
     * Sets the availability of given equipment object in the database.
     * Calls {@link application.dao.EquipmentDao#setAvailability(int, boolean) setAvailability} method.
     *
     * @param equipment_id equipment's id
     * @param available    equipment's availability
     * @throws RemoteException indicates connection failure
     */
    void setAvailability(int equipment_id, boolean available) throws RemoteException;

    /**
     * Adds new user to the database with given parameters.
     * If isManager is true, calls {@link application.dao.UserDao#createManager(String, String, String, String, String) createManager} method.
     * Otherwise, calls {@link application.dao.UserDao#createRentee(String, String, String, String, String) createRentee} method.
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
     * Calls {@link application.dao.UserDao#get(String) get} method.
     *
     * @param email user's email
     * @return {@link User} that has been added
     * @throws RemoteException indicates connection failure
     */
    User getUser(String email) throws RemoteException;

    /**
     * Returns a list of all users in the database.
     * Calls {@link application.dao.UserDao#getAllUsers() getAllUsers} method.
     *
     * @return list of all users
     * @throws RemoteException indicates connection failure
     */
    ArrayList<User> getAllUsers() throws RemoteException;

    /**
     * Deletes user with given email address from the database.
     * Calls {@link application.dao.UserDao#delete(String) delete} method.
     *
     * @param email - user's email
     * @throws RemoteException indicates connection failure
     */
    void deleteUser(String email) throws RemoteException;

    /**
     * Returns true if the user with given email address and password is a valid user in the database.
     * Otherwise, returns false.
     * Calls {@link application.dao.UserDao#isValidUser(String, String) isValidUser} method.
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
     * Calls {@link application.dao.UserDao#isUserAManager(String) isUserAManager} method.
     *
     * @param email user's email
     * @return boolean indicating if the user is a manager
     * @throws RemoteException indicates connection failure
     */
    boolean isUserAManager(String email) throws RemoteException;

    /**
     * Retrieves all reservations from the database.
     * Calls {@link application.dao.ReservationDao#reserveEquipment(int, String, LocalDateTime) reserveEquipment} method.
     *
     * @return {@link ArrayList<Reservation>} of all retrieved reservations
     * @throws RemoteException indicates connection failure
     */
    ArrayList<Reservation> retrieveReservations() throws RemoteException;

    /**
     * Approves the reservation with given id identified by the manager with given manager_id in the database.
     * Calls {@link application.dao.ReservationDao#approveReservation(int, String) approveReservation} method.
     *
     * @param id         reservation's id
     * @param manager_id manager's id
     * @throws RemoteException indicates connection failure
     */
    void approveReservation(int id, String manager_id) throws RemoteException;

    /**
     * Rejects the reservation with given id identified by the manager with given manager_id in the database
     * Calls {@link application.dao.ReservationDao#rejectReservation(int, String, String) rejectReservation} method.
     *
     * @param id         reservation's id
     * @param manager_id manager's id
     * @param reason reason given by the manager for rejection
     * @throws RemoteException indicates connection failure
     */
    void rejectReservation(int id, String manager_id, String reason) throws RemoteException;
    /**
     * Expires the reservation with given id in the database.
     * Calls {@link application.dao.ReservationDao#expireReservation(int) expireReservation} method.
     *
     * @param id reservation's id
     * @throws RemoteException indicates connection failure
     */
    void expireReservation(int id) throws RemoteException;

    /**
     * Makes the reservation with given id being returned, in the database.
     * Calls {@link application.dao.ReservationDao#returnReservation(int) returnReservation} method.
     *
     * @param id reservation's id
     * @throws RemoteException indicates connection failure
     */
    void returnReservation(int id) throws RemoteException;

    /**
     * Reserves the equipment with given id in the database and notifies a sender about new reservation's id.
     * Calls {@link application.dao.ReservationDao#reserveEquipment(int, String, LocalDateTime) reserveEquipment} method.
     *
     * @param equipment_id equipment's id
     * @param rentee_id    rentee's id
     * @param rentedFor    expiration date of the reserved equipment
     * @param sender       sender, to which id of a new reservation will be replied to
     * @throws RemoteException indicates connection failure
     */
    void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor, RentalSystemClient sender) throws RemoteException;

    /**
     * Returns current expiration timeout of reservations.
     * Calls {@link application.server.timer.SelectiveReservationTimer#getExpirationTimeout() getExpirationTimeout} method.
     *
     * @return expiration timeout of reservations
     * @throws RemoteException indicates connection failure
     */
    int getExpirationTimeout() throws RemoteException;

    /**
     * Sets new expiration timeout for reservations.
     * Calls {@link application.server.timer.SelectiveReservationTimer#setExpirationTimeout(int) setExpirationTimeout} method.
     *
     * @param expirationTimeout new reservation expiration timeout in seconds
     * @throws RemoteException indicates connection failure
     */
    void setExpirationTimeout(int expirationTimeout) throws RemoteException;

    /**
     * Throws Remote Exception when RMI client cannot connect to it.
     * No need for implementation. Method body can be empty.
     *
     * @throws RemoteException indicates connection failure
     */
    void pingServer() throws RemoteException;


    /**
     * Adds a listener to start listening to events in the server
     *
     * @param listener the listener which will catch events
     * @throws RemoteException indicates connection failure
     */
    void addPropertyChangeListener(RemotePropertyChangeListener<ArrayList> listener) throws RemoteException;

    /**
     * Removes a listener to stop listening to events in the server
     *
     * @param listener the listener which will catch events
     * @throws RemoteException indicates connection failure
     */
    void removePropertyChangeListener(RemotePropertyChangeListener<ArrayList> listener) throws RemoteException;
}
