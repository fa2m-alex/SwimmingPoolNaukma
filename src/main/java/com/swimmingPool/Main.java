package com.swimmingPool;

import com.swimmingPool.dao.impl.SwimmerDaoImpl;
import com.swimmingPool.models.Swimmer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {
        Swimmer s = new Swimmer();
        s.setName("Борис");
        s.setSurname("Гребенщиков");

        String source="12/09/1994";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date d = new java.sql.Date(format.parse(source).getTime());

        s.setBirthday(d);
        s.setGrowth(180);

        SwimmerDaoImpl dao = new SwimmerDaoImpl();
        dao.insert(s);

    }
}
