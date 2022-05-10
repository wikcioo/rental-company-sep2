package application.dao;

import application.model.Equipment;

import java.sql.*;
import java.util.ArrayList;

public class SQLEquipmentDao implements EquipmentDao {
    private static final EquipmentDao instance = new SQLEquipmentDao();

    private SQLEquipmentDao() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://abul.db.elephantsql.com/yzwsewzj", "yzwsewzj", "pb2tFI2SZ3_msyeJyrqmf35pRjUtyotU");
    }

    public static EquipmentDao getInstance() {
        return instance;
    }

    @Override
    public void add(Equipment equipment) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO rentalsystemdbs.equipment VALUES (DEFAULT, ?, ?, ?)")
        ) {
            statement.setString(1, equipment.getModel());
            statement.setString(2, equipment.getCategory());
            statement.setBoolean(3, true);
            statement.executeUpdate();
        }
    }

    @Override
    public ArrayList<Equipment> getAll() throws SQLException {
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet rs = statement.executeQuery("SELECT * FROM rentalsystemdbs.equipment");
            while (rs.next()) {
                int id = rs.getInt("equipment_id");
                String model = rs.getString("model");
                String category = rs.getString("category");
                boolean available = rs.getBoolean("availability");
                equipmentList.add(new Equipment(model, category, available));
            }
        }

        return equipmentList;
    }

    @Override
    public ArrayList<Equipment> getByModel(String model) throws SQLException {
        // TODO
        return null;
    }

    @Override
    public ArrayList<Equipment> getByCategory(String category) throws SQLException {
        // TODO
        return null;
    }

    @Override
    public ArrayList<Equipment> getByPrice(double min, double max) throws SQLException {
        // TODO
        return null;
    }

    @Override
    public void delete(Equipment equipment) throws SQLException {
        // TODO
    }
}
