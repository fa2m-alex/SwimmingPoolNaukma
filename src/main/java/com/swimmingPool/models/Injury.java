package com.swimmingPool.models;

import java.util.Date;

/**
 * Created by Alex on 11.02.2016.
 */
public class Injury {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSwimmer_id() {
        return swimmer_id;
    }

    public void setSwimmer_id(int swimmer_id) {
        this.swimmer_id = swimmer_id;
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
    private int swimmer_id;
    private String title;
    private Date date;

    @Override
    public String toString(){
        return "id: " + id +
                ", swimmer_id: " + swimmer_id +
                ", title: " + title +
                ", date: " + date;
    }
}
