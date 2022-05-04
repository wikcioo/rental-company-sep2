package application.dao;

import application.model.Equipment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EquipmentDao {
    void add(Equipment equipment) throws SQLException;
    ArrayList<Equipment> getAll() throws SQLException;
    ArrayList<Equipment> getByModel(String model) throws SQLException;
    ArrayList<Equipment> getByCategory(String category) throws SQLException;
    ArrayList<Equipment> getByPrice(double min, double max) throws SQLException;
    void delete(Equipment equipment) throws SQLException;
}
