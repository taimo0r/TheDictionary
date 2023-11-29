package com.taimoor.dictionary.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "SearchedWords", indices = {@Index(value = {"word"}, unique = true)})
public class SearchedWords {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "word")
    public String Word;

}
