package com.example.labex6_comp3074_wk7.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insert(Student student);

    @Query("SELECT * FROM students")
    List<Student> getAllStudents();
}
