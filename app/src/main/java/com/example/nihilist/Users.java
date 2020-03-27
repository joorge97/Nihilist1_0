package com.example.nihilist;

public class Users {

    private String name;
    private String id_usuairo;

    public Users(String name, String id_usuairo) {
        this.name = name;
        this.id_usuairo = id_usuairo;
    }

    public Users() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_usuairo() {
        return id_usuairo;
    }

    public void setId_usuairo(String id_usuairo) {
        this.id_usuairo = id_usuairo;
    }

    @Override
    public String toString() {
        return name +" " + id_usuairo;
    }
}
