package com.swimmingPool.models;

/**
 * Created by Alex on 11.02.2016.
 */
public class CompetitionType {
    private int id;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "id: " + id +
                ", type: " + type;
    }
}
