package com.swimmingPool.dao.interfaces;

import com.swimmingPool.models.Coach;
import com.swimmingPool.models.Swimmer;

import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public interface CoachDao {
    public void insert(Coach coach);
    public List<Coach> getAll();
    public Coach getById(int id);
    public void update(Coach coach);
    public void delete(Coach coach);
}
