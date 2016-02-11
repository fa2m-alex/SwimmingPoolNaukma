package com.swimmingPool.dao.interfaces;

import com.swimmingPool.models.Swimmer;

import java.util.List;

public interface SwimmerDao {
    public void insert(Swimmer swimmer);
    public List<Swimmer> getAll();
    public Swimmer getById(int id);
}
