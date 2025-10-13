package com.example.db_demo.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "name_table")
public class Name {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "name_id")
    private long id;

    @NotNull
    @ColumnInfo(name = "name")
    private String name;

    @Ignore
    public Name() {}

    public Name(@NotNull String name){
        this.name = name;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setName(@NotNull String name){
        this.name = name;
    }

    public @NotNull String getName(){
        return name;
    }

    @Override
    public String toString() {
        return "Name{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
