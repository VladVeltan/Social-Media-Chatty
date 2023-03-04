package com.example.lab4final.domain;

import java.sql.Timestamp;

public class Friendship extends Entity{
    int idUser1;
    int idUser2;

    Timestamp dateTime;

    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Friendship(Integer id, int idUser1, int idUser2, Timestamp dateTime, String status) {
        super(id);
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.dateTime = dateTime;
        this.status = status;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public int getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(int idUser1) {
        this.idUser1 = idUser1;
    }

    public int getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(int idUser2) {
        this.idUser2 = idUser2;
    }


}
