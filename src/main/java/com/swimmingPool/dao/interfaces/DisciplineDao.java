package com.swimmingPool.dao.interfaces;

import com.swimmingPool.models.Discipline;

import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public interface DisciplineDao {
    public void insert(Discipline discipline);
    public List<Discipline> getAll();
}
