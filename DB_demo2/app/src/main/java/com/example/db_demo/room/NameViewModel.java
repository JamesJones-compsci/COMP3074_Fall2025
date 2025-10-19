package com.example.db_demo.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class NameViewModel extends AndroidViewModel {

    private NameRepo repo;


    public NameViewModel(@NonNull Application application) {
        super(application);
        repo = new NameRepo(application);
    }

    public List<Name> getAllNames() {
       return repo.getAllNames();
    }

    public Name getName(int id){
        return repo.getName(id);
    }

    public boolean insert(Name name){
        return repo.insert(name);
    }

    public boolean update(Name name){
       return repo.update(name);
    }

    public boolean delete(Name name){
        return repo.delete(name);
    }
}
