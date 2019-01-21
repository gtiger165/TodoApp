package com.hirarki.todoapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by hp on 09/12/2018.
 */

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task WHERE kategori = :kategoriPilihan")
    List<Task> getAll(String kategoriPilihan);

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
