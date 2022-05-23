package application.dao;

import application.model.users.User;

import java.sql.*;

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

    // TODO: Make proper update function (design[usage] is wrong)
    // TODO TODO: Remove that TODO above and this function as well. Why is it here if we don't even have the update the user use case?
    @Override
    public void update(User user) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE User SET password = ? WHERE firstName = ? AND lastName = ?")
        ) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
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
