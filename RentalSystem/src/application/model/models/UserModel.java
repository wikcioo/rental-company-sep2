package application.model.models;

import application.model.users.User;

import java.rmi.RemoteException;

public interface UserModel {
    /**
     * Clears equipmentList and populates it by calling {@link application.client.RentalSystemClient#getAllEquipment() getAllEquipment} method.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     *
     * @throws RemoteException indicates connection issues
     */
    void retrieveAllEquipment() throws RemoteException;

    /**
     * Clears equipmentList and populates it by calling {@link application.client.RentalSystemClient#getAllUnreservedEquipment() getAllUnreservedEquipment} method.
     * Fires property change on EQUIPMENT_LIST_CHANGED event.
     *
     * @throws RemoteException indicates connection issues
     */
    void retrieveAllUnreservedEquipment() throws RemoteException;

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
     * Clears userList and populates it by calling {@link application.client.RentalSystemClient#getAllUsers() getAllUsers} method.
     * Fires property change on USER_LIST_CHANGED event.
     *
     * @throws RemoteException indicates connection issues
     */
    void retrieveAllUsers() throws RemoteException;

    /**
     * Returns a String indicating who the user should be treated as (Manager, Rentee or Invalid)
     * Calls {@link application.client.RentalSystemClient#isValidUser(String, String) isValidUser} method to check if the user is valid.
     * Calls {@link application.client.RentalSystemClient#isUserAManager(String) isUserAManager} method to check if the user is a manager, otherwise, rentee.
     *
     * @param email    user's email address
     * @param password user's password
     * @return String(Manager, Rentee or Invalid) indicating user's role or invalid
     * @throws RemoteException indicates connection issues
     */
    String logIn(String email, String password) throws RemoteException;

    /**
     * Sets a new logged-in user.
     *
     * @param newUser user to be set as logged-in
     */
    void setCurrentlyLoggedInUser(User newUser);

    /**
     * Refreshes reservationList by setting it with a call to
     * {@link application.client.RentalSystemClient#retrieveReservations() retrieveReservations} method.
     * Fires property change on RESERVATION_LIST_CHANGED event.
     */
    void refreshReservations() throws RemoteException;

    /**
     * Returns true if successfully reconnected the client to the server.
     * Otherwise, returns false.
     *
     * @return result of trying to reconnect the client to the server
     */
    boolean tryToReconnectClient();

    /**
     * Checks if there is connectivity with the server. Throws exception if it cannot connect.
     * Calls {@link application.client.RentalSystemClient#pingServer() pingServer} method.
     *
     * @throws RemoteException indicates connection failure
     */
    void pingServer() throws RemoteException;
}
