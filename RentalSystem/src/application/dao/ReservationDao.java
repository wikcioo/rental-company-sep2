package application.dao;

import application.model.reservations.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ReservationDao {
    /**
     * <ul>
     * Retrieves all the reservation from the database from returned, approved, rejected, expired and reservation relation.
     * Each reservation has a class that correspond to their current state.
     *
     * <li>If no action on the reservation was taken, the reservation will be of the {@link Reservation}</li>
     * <li>If the reservation has expired, it will be the {@link Expired}</li>
     * <li>If the reservation has been rejected, it will be the {@link Rejected}</li>
     * <li>If the reservation has been approved, it will be the {@link Approved}</li>
     * <li>If the reservation had been approved and the equipment have been returned , it will be the {@link Returned}</li>
     * </ul>
     *
     * @return The {@link ArrayList<Reservation>} of the {@link Reservation} extending the class of corresponding state
     * @throws SQLException
     */
    ArrayList<Reservation> retrieveReservations() throws SQLException;

    /**
     * Approves the reservation of the given id. Manager id identifies which manager approved the reservation
     *
     * @param id         the reservation id. The reservation must exist in the database, otherwise SQLException is thrown
     * @param manager_id the id of the manager (email). The email must belong to the manager, otherwise SQLException is thrown
     * @throws SQLException
     */
    void approveReservation(int id, String manager_id) throws SQLException;

    /**
     * Rejects the reservation of the given id. Manager id identifies which manager rejected the reservation
     * TODO add the reason for rejection
     *
     * @param id         the reservation id. The reservation must exist in the database, otherwise SQLException is thrown
     * @param manager_id the id of the manager (email). The email must belong to the manager, otherwise SQLException is thrown
     * @throws SQLException
     */
    void rejectReservation(int id, String manager_id) throws SQLException;

    /**
     * Causes the reservation of the given id to expire.
     *
     * @param id the reservation id. The reservation must exist in the database, otherwise SQLException is thrown
     * @throws SQLException
     */
    void expireReservation(int id) throws SQLException;


    /**
     * Confirms that equipment of the approved reservation has been returned.
     *
     * @param id the reservation id. The reservation must exist in the database and must have been previously approved, otherwise SQLException is thrown
     * @throws SQLException
     */
    void returnReservation(int id) throws SQLException;

    /**
     * Inserts a new row  in the reservation relation. Inserts the default value in a reservation_date.
     *
     * @param equipment_id the equipment id. The equipment id must exist in the database, otherwise SQLException is thrown
     * @param rentee_id    the user id. The user must exist in the database, otherwise SQLException is thrown
     * @param rentedFor    the date until which rentee wishes to have time to return the equipment
     * @throws SQLException
     */
    void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws SQLException;
}
