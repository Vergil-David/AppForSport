package com.example.befit.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.befit.model.SportActivity;
import com.example.befit.model.SportType;

import java.util.List;

@Dao
public interface SportDao {
    @Insert
    void insertSportType(SportType sportType);

    @Query("SELECT * FROM sport_type")
    List<SportType> getAllSportTypes();

    @Insert
    void insertSportActivities(SportActivity sportTypeActivity);

    @Query("SELECT * FROM sport_activity WHERE sportTypeId = :sportTypeId")
    List<SportActivity> getAllSportActivities(int sportTypeId);
}
