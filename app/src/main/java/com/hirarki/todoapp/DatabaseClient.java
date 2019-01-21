package com.hirarki.todoapp;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by hp on 09/12/2018.
 */

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    //Objek database pada aplikasi
    private AppDatabase appDatabase;

    private DatabaseClient(Context mCtx){
        this.mCtx = mCtx;

        //Membuat database pada aplikasi dengan Room database builder
        //TodoApps adalah name database nya
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "TodoApps").build();
    }

    public static synchronized  DatabaseClient getmInstance(Context mCtx){
        if (mInstance == null){
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
