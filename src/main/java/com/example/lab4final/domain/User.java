package com.example.lab4final.domain;

public class User extends Entity{
    String prenume;
    String nume;
    String email;
    String parola;

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public User(Integer id, String prenume, String nume, String email, String parola) {
        super(id);
        this.prenume = prenume;
        this.nume = nume;
        this.email = email;
        this.parola = parola;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
