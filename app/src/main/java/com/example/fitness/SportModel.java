package com.example.fitness;

public class SportModel {
    String sportId;
    String sportName;
    String sportCalori;

    public SportModel() {
    }

    public SportModel(String sportName, String sportCalori,String sportId) {
        this.sportName = sportName;
        this.sportCalori = sportCalori;
        this.sportId=sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportCalori() {
        return sportCalori;
    }

    public void setSportCalori(String sportCalori) {
        this.sportCalori = sportCalori;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }
}
