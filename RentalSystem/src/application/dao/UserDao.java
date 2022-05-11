package application.dao;

import application.model.User;

import java.sql.SQLException;

public interface UserDao {
    User create(String firstName, String lastName, String password) throws SQLException;
    User get(String firstName, String lastName) throws SQLException;
    void update(User user) throws SQLException;
    boolean isValidUser(String email, String password) throws SQLException;
    boolean isUserAManager(String email) throws SQLException;
}
