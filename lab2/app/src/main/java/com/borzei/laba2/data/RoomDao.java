package com.borzei.laba2.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.borzei.laba2.data.pojo.DetailModel;

import java.util.List;

@Dao
public interface RoomDao {

    @Query("SELECT * FROM DetailModel")
    List<DetailModel> getAll();

    @Query("SELECT * FROM DetailModel WHERE id = :id")
    DetailModel getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DetailModel item);

}