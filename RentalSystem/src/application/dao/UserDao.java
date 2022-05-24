package application.dao;

import application.model.users.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDao {
    /**
     * Inserts a new user in the user and manager relation.
     *
     * @param firstName   - first name
     * @param lastName    - last name
     * @param phoneNumber - phone number (max size 16)
     * @param email       - email of the new user. Must be unique
     * @param password    - password
     * @throws SQLException
     */
    void createManager(String firstName, String lastName, String phoneNumber, String email, String password) throws SQLException;

    /**
     * Creates a new user in the user and rentee relation. Currently, sets the rental_id to null
     *
     * @param firstName   - first name
     * @param lastName    - last name
     * @param phoneNumber - phone number (max size 16)
     * @param email       - email of the new user. Must be unique
     * @param password    - password
     * @throws SQLException
     */
    void createRentee(String firstName, String lastName, String phoneNumber, String email, String password) throws SQLException;

    /**
     * Gets the user by the unique email
     *
     * @param email
     * @return
     * @throws SQLException
     */
    User get(String email) throws SQLException;

    /**
     * Gets all the users in the database.
     *
     * @return list of users
     * @throws SQLException
     */
    ArrayList<User> getAllUsers() throws SQLException;

    /**
     * Deletes the user with given email address from the users' table in the database.
     *
     * @param email - the user's email
     * @throws SQLException
     */
    void delete(String email) throws SQLException;

    /**
     * Verifies whether the given credentials and password suit any of the users in the database
     *
     * @param email    - the user's email
     * @param password - the user's password in plain text :)
     * @return true if given credentials exist in the database, otherwise false
     * @throws SQLException
     */
    boolean isValidUser(String email, String password) throws SQLException;

    /**
     * Checks if there is a manager with given email
     *
     * @param email - the user's email
     * @return true if given credentials exist in the database, otherwise false
     * @throws SQLException
     */
    boolean isUserAManager(String email) throws SQLException;
}
