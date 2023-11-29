package com.taimoor.dictionary.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface WordsDao {
    @Query(" Select * From SearchedWords")
    List<SearchedWords> getAllWords();

    @Query("Select word From SearchedWords ")
    List<String> getWords();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void SaveWord(SearchedWords words);

    @Delete
    void delete(SearchedWords words);

    @Query(" DELETE FROM SearchedWords")
    void deleteAll();
}
