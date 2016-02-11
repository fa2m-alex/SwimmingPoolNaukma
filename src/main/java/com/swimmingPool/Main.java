package com.swimmingPool;

import com.swimmingPool.dao.impl.CoachDaoImpl;
import com.swimmingPool.dao.impl.InjuryDaoImpl;
import com.swimmingPool.dao.impl.SwimmerDaoImpl;
import com.swimmingPool.dao.interfaces.CoachDao;
import com.swimmingPool.dao.interfaces.InjuryDao;
import com.swimmingPool.dao.interfaces.SwimmerDao;
import com.swimmingPool.models.Coach;
import com.swimmingPool.models.Injury;
import com.swimmingPool.models.Swimmer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {
        /*Swimmer s = new Swimmer();
        s.setName("David");
        s.setSurname("Beckham");

        String source="12/09/1976";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date d = new java.sql.Date(format.parse(source).getTime());

        s.setBirthday(d);
        s.setGrowth(188);
        s.setCoach_id(1);

        SwimmerDao dao = new SwimmerDaoImpl();
        dao.insert(s);
*/
        /*Coach coach = new Coach();
        coach.setName("Michael");
        coach.setSurname("Jackson");

        String source="12/09/1945";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date d = new java.sql.Date(format.parse(source).getTime());
        coach.setBirthday(d);

        CoachDao coachDao = new CoachDaoImpl();
        coachDao.insert(coach);*/

        Injury injury = new Injury();
        injury.setSwimmer_id(101);
        injury.setTitle("Oww");

        String source="12/09/2015";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date d = new java.sql.Date(format.parse(source).getTime());
        injury.setDate(d);

        InjuryDao injuryDao = new InjuryDaoImpl();
        injuryDao.insert(injury);
    }
}
