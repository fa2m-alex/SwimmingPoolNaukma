package com.swimmingPool.models;

import java.util.Date;

/**
 * Created by Alex on 11.02.2016.
 */
public class Competition {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getDiscipline_id() {
        return discipline_id;
    }

    public void setDiscipline_id(int discipline_id) {
        this.discipline_id = discipline_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private int id;
    private int type_id;
    private int discipline_id;
    private String title;
    private Date date;

    @Override
    public String toString(){
        return "id: " + id +
                ", type_id: " + type_id +
                ", discipline_id: " + discipline_id +
                ", title: " + title +
                ", date: " + date;
    }

}
