package com.example.aj.Entities;

import java.util.Date;

public class Reservation {
    int id;
    int Enconter_id;
    int User_id;
    Date date;

    public Reservation() {
    }

    public Reservation(int id, int enconter_id, int user_id, Date date) {
        this.id = id;
        Enconter_id = enconter_id;
        User_id = user_id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnconter_id() {
        return Enconter_id;
    }

    public void setEnconter_id(int enconter_id) {
        Enconter_id = enconter_id;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
