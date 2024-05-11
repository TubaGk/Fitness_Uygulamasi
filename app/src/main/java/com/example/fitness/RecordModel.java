package com.example.fitness;

public class RecordModel {
    String date;

    String takeCalori;
    String dropCalori;
    String changedCalori;

    public RecordModel() {
    }

    public RecordModel(String date, String takeCalori, String dropCalori, String changedCalori) {
        this.date = date;
        this.takeCalori = takeCalori;
        this.dropCalori = dropCalori;
        this.changedCalori = changedCalori;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTakeCalori() {
        return takeCalori;
    }

    public void setTakeCalori(String takeCalori) {
        this.takeCalori = takeCalori;
    }

    public String getDropCalori() {
        return dropCalori;
    }

    public void setDropCalori(String dropCalori) {
        this.dropCalori = dropCalori;
    }

    public String getChangedCalori() {
        return changedCalori;
    }

    public void setChangedCalori(String changedCalori) {
        this.changedCalori = changedCalori;
    }
}
