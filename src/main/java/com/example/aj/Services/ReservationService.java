package com.example.aj.Services;

import com.example.aj.DatabaseManager;
import com.example.aj.Entities.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private Connection cnx;

    public ReservationService() {
        cnx = DatabaseManager.getInstance().getCnx();
    }

    public void addReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO Reservation (Encounter_id, User_id, date) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getEnconter_id());
            pstmt.setInt(2, reservation.getUser_id());
            pstmt.setDate(3, new java.sql.Date(reservation.getDate().getTime()));
            pstmt.executeUpdate();
        }
    }

    public void updateReservation(Reservation reservation) throws SQLException {
        String sql = "UPDATE Reservation SET Encounter_id=?, User_id=?, date=? WHERE id=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getEnconter_id());
            pstmt.setInt(2, reservation.getUser_id());
            pstmt.setDate(3, new java.sql.Date(reservation.getDate().getTime()));
            pstmt.setInt(4, reservation.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteReservation(int reservationId) throws SQLException {
        String sql = "DELETE FROM Reservation WHERE id=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            pstmt.executeUpdate();
        }
    }

    public Reservation getReservationById(int reservationId) throws SQLException {
        String sql = "SELECT * FROM Reservation WHERE id=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, reservationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToReservation(rs);
                }
            }
        }
        return null;
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservation";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }

    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id"));
        reservation.setEnconter_id(rs.getInt("Encounter_id"));
        reservation.setUser_id(rs.getInt("User_id"));
        reservation.setDate(rs.getDate("date"));
        return reservation;
    }
    public List<Reservation> getReservationsByUserId(int userId) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservation WHERE User_id=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapResultSetToReservation(rs));
                }
            }
        }
        return reservations;
    }

}
