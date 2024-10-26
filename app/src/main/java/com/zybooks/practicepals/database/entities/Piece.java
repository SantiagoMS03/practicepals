package com.zybooks.practicepals.database.entities;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pieces")
public class Piece {

    @PrimaryKey(autoGenerate = true)
    public int piece_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "composer")
    public String composer;

    @ColumnInfo(name = "date_added")
    public long dateAdded;  // Unix timestamp for easier handling of dates

    @ColumnInfo(name = "total_time_practiced")
    public long totalTimePracticed = 0;  // Initialize to 0


}
