package com.borzei.laba2.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.borzei.laba2.data.pojo.DetailModel;

@Database(entities = {DetailModel.class}, version = 1, exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private static RoomDatabase INSTANCE;

    public static RoomDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), RoomDatabase.class, "MovieDB")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public abstract RoomDao moviesDAO();

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

