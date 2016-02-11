package com.swimmingPool.dao.interfaces;

import com.swimmingPool.models.Injury;

import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public interface InjuryDao {
    public void insert(Injury injury);
    public List<Injury> getAll();
}
