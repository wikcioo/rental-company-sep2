package application.dao;

import application.model.equipment.Equipment;
import application.model.reservations.*;
import application.model.users.User;
import application.util.ConsoleLogger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SQLReservationDao implements ReservationDao {
    private static final ReservationDao instance = new SQLReservationDao();

    private SQLReservationDao() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://abul.db.elephantsql.com/yzwsewzj?currentSchema=rentalsystemdbs", "yzwsewzj", "pb2tFI2SZ3_msyeJyrqmf35pRjUtyotU");
    }

    public static ReservationDao getInstance() {
        return instance;
    }

    @Override
    public ArrayList<Reservation> retrieveReservations() throws SQLException {
        ArrayList<Reservation> reservationList = new ArrayList<>();
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet rs = statement.executeQuery(
                    "SELECT *," +
                            " approved.reservation_id as approved," +
                            " expired.reservation_id as expired," +
                            " rejected.reservation_id as rejected," +
                            " returned.approved_id as returned," +
                            " approved.date as approved_date," +
                            " expired.date as expired_date," +
                            " rejected.date as rejected_date," +
                            " returned.date as returned_date  " +
                            " FROM rentalsystemdbs.reservation" +
                            "    INNER JOIN rentalsystemdbs.equipment on equipment.equipment_id = reservation.equipment_id" +
                            "    LEFT JOIN rentalsystemdbs.users u on reservation.rentee = u.email" +
                            "    LEFT JOIN rentalsystemdbs.approved on reservation.reservation_id = approved.reservation_id" +
                            "    LEFT JOIN rentalsystemdbs.expired on reservation.reservation_id = expired.reservation_id" +
                            "    LEFT JOIN rentalsystemdbs.rejected on reservation.reservation_id = rejected.reservation_id" +
                            "    LEFT JOIN rentalsystemdbs.returned on approved.reservation_id = returned.approved_id;");

            while (rs.next()) {
                Reservation reservation = new Unapproved(
                        rs.getInt("reservation_id"),
                        new User(
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("phone_number"),
                                rs.getString("rentee"),
                                rs.getString("password")
                        ),
                        (rs.getTimestamp("reservation_date")) != null ? rs.getTimestamp("reservation_date").toLocalDateTime() : null,
                        (rs.getTimestamp("rented_for")) != null ? rs.getTimestamp("rented_for").toLocalDateTime() : null,
                        new Equipment(
                                rs.getInt("equipment_id"),
                                rs.getString("model"),
                                rs.getString("category"),
                                rs.getBoolean("availability")
                        )
                );

                if (rs.getInt("expired") != 0) {
                    reservationList.add(new Expired(reservation, rs.getTimestamp("expired_date").toLocalDateTime()));
                    continue;
                }
                if (rs.getInt("rejected") != 0) {
                    reservationList.add(new Rejected(reservation, rs.getTimestamp("rejected_date").toLocalDateTime(), rs.getString("reason"), rs.getString("rejected_by")));
                    continue;
                }
                if (rs.getInt("approved") != 0) {
                    Approved approved = new Approved(reservation, rs.getTimestamp("approved_date").toLocalDateTime(), rs.getString("approved_by"));
                    if (rs.getInt("returned") != 0) {
                        reservationList.add(new Returned(approved, rs.getTimestamp("returned_date").toLocalDateTime()));
                        continue;
                    }
                    reservationList.add(approved);
                    continue;
                }
                reservationList.add(reservation);
            }
        }

        return reservationList;
    }

    @Override
    public void approveReservation(int id, String manager_id) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO approved(reservation_id, date, approved_by)" +
                        " SELECT ?,now(), ?" +
                        " WHERE NOT EXISTS (SELECT reservation_id FROM rejected WHERE reservation_id = ?)" +
                        " AND NOT EXISTS (SELECT reservation_id FROM expired WHERE reservation_id = ?);")
        ) {
            statement.setInt(1, id);
            statement.setInt(3, id);
            statement.setInt(4, id);
            statement.setString(2, manager_id);
            statement.executeUpdate();
        }
    }

    @Override
    public void rejectReservation(int id, String manager_id, String reason) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO rejected(reservation_id, date, rejected_by, reason)" +
                        " SELECT ?,now(), ?,?" +
                        " WHERE NOT EXISTS (SELECT reservation_id FROM approved WHERE reservation_id = ?)" +
                        " AND NOT EXISTS (SELECT reservation_id FROM expired WHERE reservation_id = ?);")
        ) {
            statement.setInt(1,id);
            statement.setInt(4,id);
            statement.setInt(5,id);
            statement.setString(2,manager_id);
            if(reason != null && reason.equals("")) {
                statement.setNull(3, Types.VARCHAR);
            }
            else statement.setString(3, reason);
            statement.executeUpdate();
        }
    }

    @Override
    public void expireReservation(int id) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO expired(reservation_id, date)" +
                        " SELECT ?,now()" +
                        " WHERE NOT EXISTS (SELECT reservation_id FROM rejected WHERE reservation_id = ?)" +
                        " AND NOT EXISTS (SELECT reservation_id FROM approved WHERE reservation_id = ?);")
        ) {
            statement.setInt(1, id);
            statement.setInt(2, id);
            statement.setInt(3, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void returnReservation(int id) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO rentalsystemdbs.returned VALUES (?, DEFAULT)")
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public int reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO rentalsystemdbs.reservation VALUES (DEFAULT, DEFAULT, ?, ?,?)",Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, rentee_id);
            statement.setInt(2, equipment_id);
            statement.setTimestamp(3, Timestamp.valueOf(rentedFor));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            int pk = 0;
            if (rs.next()) {
                pk = rs.getInt(1);
            }
            return pk;
        }
    }
}
