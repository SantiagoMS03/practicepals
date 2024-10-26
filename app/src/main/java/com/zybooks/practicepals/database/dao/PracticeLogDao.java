package com.zybooks.practicepals.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zybooks.practicepals.database.entities.PracticeLog;

import java.util.List;

@Dao
public interface PracticeLogDao {

    @Insert
    void insert(PracticeLog practiceLog);

    @Update
    void update(PracticeLog practiceLog);

    @Query("SELECT * FROM practice_logs WHERE log_id = :logId")
    PracticeLog getLogById(int logId);

    @Query("SELECT * FROM practice_logs WHERE piece_id = :pieceId ORDER BY log_date DESC")
    LiveData<List<PracticeLog>> getLogsForPiece(int pieceId);

    @Delete
    void delete(PracticeLog practiceLog);
}
