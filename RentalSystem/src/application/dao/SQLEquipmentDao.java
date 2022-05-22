package application.dao;

import application.model.equipment.Equipment;

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
    public Equipment add(String model, String category, boolean available) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO rentalsystemdbs.equipment VALUES (DEFAULT, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, model);
            statement.setString(2, category);
            statement.setBoolean(3, available);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            long pk = 0;
            if (rs.next()) {
                pk = rs.getLong(1);
            }

            return new Equipment((int)pk, model, category, available);
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
                equipmentList.add(new Equipment(id, model, category, available));
            }
        }

        return equipmentList;
    }


    //TODO Two queries which are not a transaction. Possibility of creating inconsistent state of the database
    @Override
    public ArrayList<Equipment> getAllUnreserved() throws SQLException {
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet rs = statement.executeQuery("SELECT * FROM rentalsystemdbs.reservation");
            ArrayList<Integer> reservedEquipmentIds = new ArrayList<>();
            while (rs.next()) {
                reservedEquipmentIds.add(rs.getInt("equipment_id"));
            }

            rs = statement.executeQuery("SELECT * FROM rentalsystemdbs.equipment");
            while (rs.next()) {
                int id = rs.getInt("equipment_id");
                if (reservedEquipmentIds.contains(id)) {
                    continue;
                }
                String model = rs.getString("model");
                String category = rs.getString("category");
                boolean available = rs.getBoolean("availability");
                equipmentList.add(new Equipment(id, model, category, available));
            }
        }

        return equipmentList;
    }

    @Override
    public ArrayList<Equipment> getByCategory(String category) throws SQLException {
        // TODO
        return null;
    }

    @Override
    public void setAvailability(int equipment_id, boolean available) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE rentalsystemdbs.equipment SET availability = ? WHERE equipment_id = ?")
        ) {
            statement.setBoolean(1, available);
            statement.setInt(2, equipment_id);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int equipment_id) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM rentalsystemdbs.equipment WHERE equipment_id = ?")
        ) {
            statement.setInt(1, equipment_id);
            statement.executeUpdate();
        }
    }
}
