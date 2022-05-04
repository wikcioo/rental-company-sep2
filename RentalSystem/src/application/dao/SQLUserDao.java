package application.dao;

import application.model.User;

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
        return DriverManager.getConnection("jdbc:postgresql://abul.db.elephantsql.com/yzwsewzj", "yzwsewzj", "pb2tFI2SZ3_msyeJyrqmf35pRjUtyotU");
    }

    public UserDao getInstance() {
        return instance;
    }

    @Override
    public User create(String firstName, String lastName, String password) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO User VALUES (?, ?, ?)")
        ) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, password);
            statement.executeUpdate();
            return new User(firstName, lastName, password);
        }
    }

    @Override
    public User get(String firstName, String lastName) throws SQLException {
        try(
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE firstName = ? AND lastName = ?")
        ) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                return new User(firstName, lastName, password);
            } else {
                return null;
            }
        }
    }

    @Override
    public void update(User user) throws SQLException {
        try(
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE User SET password = ? WHERE firstName = ? AND lastName = ?")
        ) {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.executeUpdate();
        }
    }
}
