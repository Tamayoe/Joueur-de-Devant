package com.example.joueurdedevant;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Calendar;

@Entity(tableName = "evaluations")
public class Evaluation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String club;

    private Categorie categorie;

    private Date date;

    public Evaluation() {}

    public Evaluation(String cl, Categorie ca) {
        //Generation d'instances
        Calendar currentTime = Calendar.getInstance();

        //Definition des variables
        this.club = cl;
        this.categorie = ca;
        this.date = currentTime.getTime();
    }

    public Evaluation(String cl, Categorie ca, Date d) {
        //Definition des variables
        this.club = cl;
        this.categorie = ca;
        this.date = d;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", club='" + club + '\'' +
                ", categorie=" + categorie +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public enum Categorie {
        NULL    (-1), // Used if input is unexpected (no input / incorrect input)
        NONE    (0), // Doesn't have a use atm
        M14G    (1),
        M15F    (2),
        SENIOR  (3);

        private final int id;

        Categorie(int id) {
            this.id = id;
        }

        public int getId() { return this.id; }
    }
}
