package com.swimmingPool.dao.interfaces;

import com.swimmingPool.models.CompetitionTable;

import java.util.List;

/**
 * Created by Alex on 11.02.2016.
 */
public interface CompetitionTableDao {
    public void insert(CompetitionTable competitionTable);
    public List<CompetitionTable> getAll();
}
