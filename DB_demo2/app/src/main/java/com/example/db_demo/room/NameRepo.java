package com.example.db_demo.room;

import android.app.Application;

import java.util.List;

public class NameRepo {

    // This is a single source of truth for us for data
    // fetch from db, network (API), etc.

    private NameDao dao;

    public NameRepo(Application application){
        NameRoomDb db = NameRoomDb.getINSTANCE(application);
        dao = db.dao();
    }

    public List<Name> getAllNames() {
        return dao.getAllNames();
    }

    public Name getName(int id){
        return dao.getName(id);
    }

    public boolean insert(Name name){
        NameRoomDb.databaseExecutor.execute(() -> dao.insert(name));
        return name.getId() != -1L;
    }

    public boolean update(Name name){
        try{
            NameRoomDb.databaseExecutor.execute(() -> dao.update(name));
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean delete(Name name){
        try{
            NameRoomDb.databaseExecutor.execute(() -> dao.delete(name));
            return true;
        } catch (Throwable th) {
            return false;
        }
    }



}
