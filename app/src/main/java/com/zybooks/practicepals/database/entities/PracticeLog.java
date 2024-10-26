package com.zybooks.practicepals.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static com.zybooks.practicepals.utilities.Stopwatch.formatHoursMinutesSeconds;

@Entity(tableName = "practice_logs",
        foreignKeys = @ForeignKey(
                entity = Piece.class,
                parentColumns = "piece_id",
                childColumns = "piece_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("piece_id")}
)

public class PracticeLog {

    @PrimaryKey(autoGenerate = true)
    public int log_id;

    @ColumnInfo(name = "piece_id")
    public int pieceId;  // Foreign key linking to Piece

    @ColumnInfo(name = "log_text")
    public String logText;

    @ColumnInfo(name = "log_date")
    public long logDate;  // Timestamp for the practice session

    @ColumnInfo(name = "log_practice_time")
    public long logPracticeTime;

    public String getDate() {
        return String.valueOf(this.logDate);
    }

    public String getTimePracticed() {
        return formatHoursMinutesSeconds(this.logPracticeTime);
    }

    public String getLogText() {
        return this.logText;
    }
}
