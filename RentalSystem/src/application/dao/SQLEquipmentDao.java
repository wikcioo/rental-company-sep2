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
        return DriverManager.getConnection("jdbc:postgresql://abul.db.elephantsql.com/yzwsewzj?currentSchema=rentalsystemdbs", "yzwsewzj", "pb2tFI2SZ3_msyeJyrqmf35pRjUtyotU");
    }

    public static EquipmentDao getInstance() {
        return instance;
    }

    @Override
    public void add(String model, String category, boolean available) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO category VALUES (?) ON CONFLICT DO NOTHING;" +
                        "INSERT INTO rentalsystemdbs.equipment VALUES (DEFAULT, ?, ?, ?)")
        ) {
            statement.setString(1, category);
            statement.setString(2, model);
            statement.setString(3, category);
            statement.setBoolean(4, available);
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
                equipmentList.add(new Equipment(id, model, category, available));
            }
        }

        return equipmentList;
    }

    @Override
    public ArrayList<Equipment> getAllUnreserved() throws SQLException {
        ArrayList<Equipment> equipmentList = new ArrayList<>();
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet rs = statement.executeQuery(
                    "SELECT id,model,category,availability FROM unreserved " +
                    "UNION SELECT id,model,category,availability FROM unavailable;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String model = rs.getString("model");
                String category = rs.getString("category");
                boolean available = rs.getBoolean("availability");
                equipmentList.add(new Equipment(id, model, category, available));
            }
        }

        return equipmentList;
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

}
