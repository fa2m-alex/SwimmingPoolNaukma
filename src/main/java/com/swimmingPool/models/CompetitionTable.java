package com.swimmingPool.models;

/**
 * Created by Alex on 11.02.2016.
 */
public class CompetitionTable {

    private int id;
    private int competition_id;
    private int swimmer_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(int competition_id) {
        this.competition_id = competition_id;
    }

    public int getSwimmer_id() {
        return swimmer_id;
    }

    public void setSwimmer_id(int swimmer_id) {
        this.swimmer_id = swimmer_id;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", competition_id: " + competition_id +
                ", swimmer_id: " + swimmer_id;
    }
}
