package application.dao;

import application.model.users.User;

import java.sql.*;
import java.util.ArrayList;

public class SQLUserDao implements UserDao {
    private static final UserDao instance = new SQLUserDao();

    private SQLUserDao() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://abul.db.elephantsql.com/yzwsewzj?currentSchema=rentalsystemdbs", "yzwsewzj", "pb2tFI2SZ3_msyeJyrqmf35pRjUtyotU");
    }

    public static UserDao getInstance() {
        return instance;
    }

    private void createUser(String firstName, String lastName, String phoneNumber, String email, String password) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO rentalsystemdbs.users VALUES (?, ?, ?, ?, ?)")
        ) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, phoneNumber);
            statement.setString(4, firstName);
            statement.setString(5, lastName);
            statement.executeUpdate();
        }
    }

    @Override
    public void createManager(String firstName, String lastName, String phoneNumber, String email, String password) throws SQLException {
        createUser(firstName, lastName, phoneNumber, email, password);

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO rentalsystemdbs.manager VALUES (?)");
        ) {
            statement.setString(1, email);
            statement.executeUpdate();
        }
    }

    @Override
    public void createRentee(String firstName, String lastName, String phoneNumber, String email, String password) throws SQLException {
        createUser(firstName, lastName, phoneNumber, email, password);

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO rentalsystemdbs.rentee VALUES (?)")
        ) {
            statement.setString(1, email);
            statement.executeUpdate();
        }
    }

    @Override
    public User get(String email) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM rentalsystemdbs.users WHERE email = ?")
        ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phoneNumber = resultSet.getString("phone_number");
                String password = resultSet.getString("password");
                return new User(firstName, lastName, phoneNumber, email, password);
            } else {
                return null;
            }
        }
    }

    @Override
    public ArrayList<User> getAllUsers() throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM rentalsystemdbs.users")
        ) {
            ArrayList<User> users = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phoneNumber = resultSet.getString("phone_number");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                users.add(new User(firstName, lastName, phoneNumber, email, password));
            }

            return users;
        }
    }

    @Override
    public void delete(String email) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM rentalsystemdbs.users WHERE email = ?")
        ) {
            statement.setString(1, email);
            statement.executeUpdate();
        }
    }

    @Override
    public boolean isValidUser(String email, String password) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT password FROM rentalsystemdbs.users WHERE email = ?")
        ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String pass = resultSet.getString("password");
                if (pass.equals(password)) return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean isUserAManager(String email) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM rentalsystemdbs.manager WHERE email = ?")
        ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
}
