package com.swimmingPool.models;

import java.util.Date;

/**
 * Created by Alex on 11.02.2016.
 */
public class Coach {
    private int id;
    private String name;
    private String surname;
    private Date birthday;

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


    @Override
    public String toString(){
        return name + " " + surname;
    }
}
