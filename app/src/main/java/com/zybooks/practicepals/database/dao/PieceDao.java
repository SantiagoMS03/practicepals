package com.zybooks.practicepals.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zybooks.practicepals.database.entities.Piece;

import java.util.List;

@Dao
public interface PieceDao {

    @Insert
    void insert(Piece piece);

    @Update
    void update(Piece piece);

    @Delete
    void delete(Piece piece);

    @Query("SELECT * FROM pieces WHERE piece_id = :pieceId")
    LiveData<Piece> getPieceById(int pieceId);

    @Query("SELECT * FROM pieces ORDER BY date_added DESC")
    LiveData<List<Piece>> getAllPieces();  // Use LiveData instead of List

    @Query("DELETE FROM pieces")
    void delete_all();
}
