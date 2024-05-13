package com.example.aj.Entities;

public class Enconter {
    private int id;
    private String homeTeam;
    private String awayTeam;
    private String stadium;
    private String matchDate;
    private String matchTime;
    private int capacity;
    private int user_id;

    // Default constructor
    public Enconter() {
    }

    // Parameterized constructor
    public Enconter(int id, String homeTeam, String awayTeam, String stadium, String matchDate, String matchTime, int capacity, int adminId) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.stadium = stadium;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.capacity = capacity;
        this.user_id = adminId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Match: " + homeTeam + " vs " + awayTeam + ", Stadium: " + stadium +
                ", Date: " + matchDate + ", Time: " + matchTime + ", Capacity: " + capacity;
    }
}

