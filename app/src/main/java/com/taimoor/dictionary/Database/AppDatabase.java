package com.taimoor.dictionary.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {SearchedWords.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    public abstract WordsDao wordsDao();

    private static AppDatabase Instance;

    public static AppDatabase getInstance(Context context){
        if (Instance == null){
            Instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"SearchedWords")
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }
}
