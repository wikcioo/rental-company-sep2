package application.dao;

import application.model.equipment.Equipment;

import java.sql.SQLException;
import java.util.ArrayList;


public interface EquipmentDao {
    /**
     * Adds a new row into an Equipment relation in the database and returns an equipment object that's been stored.
     * @param model the equipment's model
     * @param category the equipment's category
     * @param available the equipment's availability
     * @return the {@link Equipment} with an id that points to the row in a relation.
     * @throws SQLException
     */
    Equipment add(String model, String category, boolean available) throws SQLException;

    /**
     *Returns all the equipment from Equipment relation.
     *
     * @return The {@link ArrayList<Equipment>} containing the {@link Equipment} from the database
     * @throws SQLException
     */
    ArrayList<Equipment> getAll() throws SQLException;

    /**
     *Returns all the equipment from the equipment relation that is not currently in the reservation relation.
     *
     * @return the {@link ArrayList} of the {@link Equipment} containing all the equipment that has never been reserved before
     * @throws SQLException
     */
    ArrayList<Equipment> getAllUnreserved() throws SQLException;

    /**
     * Returns all equipment that matches a given category. Will return only the equipment that matches exactly the given category
     * @param category The equipment's desired category
     * @return {@link ArrayList} of the {@link Equipment} that has a matching category
     * @throws SQLException
     */
    ArrayList<Equipment> getByCategory(String category) throws SQLException;

    //TODO why pass the entire object Equipment if only the id is needed? Also would be nice if the method would return a boolean whether it succeeded
    /**
     * Changes the availability of given equipment in the database.
     * The id of the equipment is used to change the availability
     *
     * @param equipment The equipment which the availability will be changed
     * @param available The desired availability
     * @throws SQLException
     */
    void setAvailability(Equipment equipment, boolean available) throws SQLException;

    /**
     * Deletes the given equipment from the equipment relation
     *
     * TODO (TO PO CHUJ TO TU STOI JAK CI SIE NIE CHCE TEGO IMPLEMENTOWAC)
     *
     * @param equipment The soon to be removed equipment
     * @throws SQLException
     */
    void delete(Equipment equipment) throws SQLException;
}
