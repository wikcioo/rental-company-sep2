package application.dao;

import application.model.reservations.IReservation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationDao {
    ArrayList<IReservation> retrieveReservations() throws SQLException;

    void approveReservation(int id, String manager_id) throws SQLException;
    void rejectReservation(int id, String manager_id) throws SQLException;
    void expireReservation(int id) throws SQLException;
    void returnReservation(int id) throws SQLException;
    void reserveEquipment(int equipment_id, String rentee_id) throws SQLException;

}
