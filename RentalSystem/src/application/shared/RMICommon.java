package application.shared;

import application.model.equipment.Equipment;
import application.model.reservations.Reservation;
import application.model.users.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMICommon extends Remote {
    /**
     * Adds new equipment with given parameters to the database.
     *
     * @param model     equipment's model
     * @param category  equipment's category
     * @param available equipment's availability
     * @throws RemoteException indicates connection failure
     */
    void addEquipment(String model, String category, boolean available) throws RemoteException;

    /**
     * Gets all stored equipment from the database.
     *
     * @return {@link ArrayList<Equipment>} of all stored equipment
     * @throws RemoteException indicates connection failure
     */
    ArrayList<Equipment> getAllEquipment() throws RemoteException;

    /**
     * Gets all stored equipment from the database that has not been reserved.
     *
     * @return {@link ArrayList<Equipment>} of all unreserved equipment
     * @throws RemoteException indicates connection failure
     */
    ArrayList<Equipment> getAllUnreservedEquipment() throws RemoteException;

    /**
     * Sets the availability of given equipment object in the database.
     *
     * @param equipment_id equipment's id
     * @param available    equipment's availability
     * @throws RemoteException indicates connection failure
     */
    void setAvailability(int equipment_id, boolean available) throws RemoteException;

    /**
     * Adds new user to the database with given parameters.
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
     *
     * @param email user's email
     * @return {@link User} that has been added
     * @throws RemoteException indicates connection failure
     */
    User getUser(String email) throws RemoteException;

    /**
     * Returns a list of all users in the database.
     *
     * @return list of all users
     * @throws RemoteException indicates connection failure
     */
    ArrayList<User> getAllUsers() throws RemoteException;

    /**
     * Deletes user with given email address from the database.
     *
     * @param email - user's email
     * @throws RemoteException indicates connection failure
     */
    void deleteUser(String email) throws RemoteException;

    /**
     * Returns true if the user with given email address and password is a valid user in the database.
     * Otherwise, returns false.
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
     *
     * @param email user's email
     * @return boolean indicating if the user is a manager
     * @throws RemoteException indicates connection failure
     */
    boolean isUserAManager(String email) throws RemoteException;

    /**
     * Retrieves all reservations from the database.
     *
     * @return {@link ArrayList<Reservation>} of all retrieved reservations
     * @throws RemoteException indicates connection failure
     */
    ArrayList<Reservation> retrieveReservations() throws RemoteException;

    /**
     * Approves the reservation with given id identified by the manager with given manager_id in the database.
     *
     * @param id         reservation's id
     * @param manager_id manager's id
     * @throws RemoteException indicates connection failure
     */
    void approveReservation(int id, String manager_id) throws RemoteException;

    /**
     * Rejects the reservation with given id identified by the manager with given manager_id in the database
     *
     * @param id         reservation's id
     * @param manager_id manager's id
     * @param reason reason given by the manager for rejection
     * @throws RemoteException indicates connection failure
     */
    void rejectReservation(int id, String manager_id, String reason) throws RemoteException;

    /**
     * Makes the reservation with given id being returned, in the database.
     *
     * @param id reservation's id
     * @throws RemoteException indicates connection failure
     */
    void returnReservation(int id) throws RemoteException;

    /**
     * Returns current expiration timeout of reservations.
     *
     * @return expiration timeout of reservations
     * @throws RemoteException indicates connection failure
     */
    int getExpirationTimeout() throws RemoteException;

    /**
     * Sets new expiration timeout for reservations.
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
}
