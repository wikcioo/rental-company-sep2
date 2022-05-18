package application.dao;

import application.model.*;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ReservationDao {
    ArrayList<IReservation> retrieveReservations() throws SQLException;

    void approveReservation(int id, String manager_id) throws SQLException;
    void rejectReservation(int id, String manager_id) throws SQLException;
    void expireReservation(int id) throws SQLException;
    void returnReservation(int id) throws SQLException;

    void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws SQLException;
}
