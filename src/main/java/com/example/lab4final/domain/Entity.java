package com.example.lab4final.domain;

public class Entity {

    private static final long serialVersionUID = 7331115341259248461L;
    private int id;

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
