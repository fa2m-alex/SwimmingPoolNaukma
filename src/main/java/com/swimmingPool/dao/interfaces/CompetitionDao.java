package com.swimmingPool.dao.interfaces;

import com.swimmingPool.models.Competition;

import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public interface CompetitionDao {
    public void insert(Competition competition);
    public List<Competition> getAll();
}
