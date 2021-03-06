package com.swimmingPool.models;

import java.util.Date;

public class Swimmer {

    private int id;
    private String name;
    private String surname;
    private Date birthday;
    private int growth;
    private int coach_id;
    private int rating_mark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public int getRating_mark() {
        return rating_mark;
    }

    public void setRating_mark(int rating_mark) {
        this.rating_mark = rating_mark;
    }

    public int getCoach_id() {
        return coach_id;
    }

    public void setCoach_id(int coach_id) {
        this.coach_id = coach_id;
    }

    @Override
    public String toString(){
        return name + " " + surname;
    }
}
