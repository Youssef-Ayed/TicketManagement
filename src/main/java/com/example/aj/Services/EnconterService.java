package com.example.aj.Services;

import com.example.aj.DatabaseManager;
import com.example.aj.Entities.Enconter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnconterService {
    private Connection cnx;

    public EnconterService() {
        cnx = DatabaseManager.getInstance().getCnx();
    }

    public void addEncounter(Enconter encounter) throws SQLException {
        String sql = "INSERT INTO Encounter (homeTeam, awayTeam, stadium, matchDate, matchTime, capacity) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, encounter.getHomeTeam());
            pstmt.setString(2, encounter.getAwayTeam());
            pstmt.setString(3, encounter.getStadium());
            pstmt.setString(4, encounter.getMatchDate());
            pstmt.setString(5, encounter.getMatchTime());
            pstmt.setInt(6, encounter.getCapacity());
            pstmt.executeUpdate();
        }
    }

    public void updateEncounter(Enconter encounter) throws SQLException {
        String sql = "UPDATE Encounter SET homeTeam=?, awayTeam=?, stadium=?, matchDate=?, matchTime=?, capacity=? WHERE id=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, encounter.getHomeTeam());
            pstmt.setString(2, encounter.getAwayTeam());
            pstmt.setString(3, encounter.getStadium());
            pstmt.setString(4, encounter.getMatchDate());
            pstmt.setString(5, encounter.getMatchTime());
            pstmt.setInt(6, encounter.getCapacity());
            pstmt.setInt(7, encounter.getId());
            pstmt.executeUpdate();
        }
    }

    public void deleteEncounter(int encounterId) throws SQLException {
        String sql = "DELETE FROM Encounter WHERE id=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, encounterId);
            pstmt.executeUpdate();
        }
    }

    public Enconter getEncounterById(int encounterId) throws SQLException {
        String sql = "SELECT * FROM Encounter WHERE id=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setInt(1, encounterId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEncounter(rs);
                }
            }
        }
        return null;
    }

    public List<Enconter> getAllEncounters() throws SQLException {
        List<Enconter> encounters = new ArrayList<>();
        String sql = "SELECT * FROM Encounter";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                encounters.add(mapResultSetToEncounter(rs));
            }
        }
        return encounters;
    }

    private Enconter mapResultSetToEncounter(ResultSet rs) throws SQLException {
        Enconter encounter = new Enconter();
        encounter.setId(rs.getInt("id"));
        encounter.setHomeTeam(rs.getString("homeTeam"));
        encounter.setAwayTeam(rs.getString("awayTeam"));
        encounter.setStadium(rs.getString("stadium"));
        encounter.setMatchDate(rs.getString("matchDate"));
        encounter.setMatchTime(rs.getString("matchTime"));
        encounter.setCapacity(rs.getInt("capacity"));
        encounter.setUser_id(rs.getInt("user_id"));
        return encounter;
    }
}
