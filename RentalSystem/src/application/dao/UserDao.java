package application.dao;

import application.model.users.User;

import java.sql.SQLException;

public interface UserDao {
    void createManager(String firstName, String lastName, String phoneNumber, String email, String password) throws SQLException;
    void createRentee(String firstName, String lastName, String phoneNumber, String email, String password) throws SQLException;
    User get(String email) throws SQLException;
    void update(User user) throws SQLException;
    boolean isValidUser(String email, String password) throws SQLException;
    boolean isUserAManager(String email) throws SQLException;
}
