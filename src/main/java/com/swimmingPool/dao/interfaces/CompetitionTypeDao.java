package com.swimmingPool.dao.interfaces;

import com.swimmingPool.models.CompetitionType;

import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public interface CompetitionTypeDao {
    public void insert(CompetitionType competitionType);
    public List<CompetitionType> getAll();
}
