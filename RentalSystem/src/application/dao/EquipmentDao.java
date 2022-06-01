package application.dao;

import application.model.equipment.Equipment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EquipmentDao {
    /**
     * Adds a new row into an Equipment relation in the database and returns an equipment object that's been stored.
     *
     * @param model     the equipment's model
     * @param category  the equipment's category
     * @param available the equipment's availability
     * @throws SQLException
     */
    void add(String model, String category, boolean available) throws SQLException;

    /**
     * Returns all the equipment from Equipment relation.
     *
     * @return The {@link ArrayList<Equipment>} containing the {@link Equipment} from the database
     * @throws SQLException
     */
    ArrayList<Equipment> getAll() throws SQLException;

    /**
     * Returns all the equipment from the equipment relation that is not currently in the reservation relation.
     *
     * @return the {@link ArrayList} of the {@link Equipment} containing all the equipment that has never been reserved before
     * @throws SQLException
     */
    ArrayList<Equipment> getAllUnreserved() throws SQLException;

    /**
     * Changes the availability of given equipment id in the database.
     *
     * @param equipment_id id of the equipment that will have its availability set
     * @param available    The desired availability
     * @throws SQLException
     */
    void setAvailability(int equipment_id, boolean available) throws SQLException;

}
