package com.zybooks.practicepals.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.zybooks.practicepals.database.AppDatabase;
import com.zybooks.practicepals.database.dao.PieceDao;
import com.zybooks.practicepals.database.entities.Piece;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PieceRepository {
    // Single instance
    private static PieceRepository instance;
    private final PieceDao pieceDao;
    private final ExecutorService executorService;

    // Private constructor to prevent direct instantiation
    private PieceRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        pieceDao = db.pieceDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    // Public method to provide the singleton instance
    public static synchronized PieceRepository getInstance(Application application) {
        if (instance == null) {
            instance = new PieceRepository(application);
        }
        return instance;
    }

    // Insert operation
    public void insert(Piece piece) {
        executorService.execute(() -> pieceDao.insert(piece));
    }

    // Update operation
    public void update(Piece piece) {
        executorService.execute(() -> pieceDao.update(piece));
    }

    // Delete operation
    public void delete(Piece piece) {
        executorService.execute(() -> pieceDao.delete(piece));
    }

    // Retrieve all pieces
    public LiveData<List<Piece>> getAllPieces() {
        return pieceDao.getAllPieces();
    }

    public void delete_all() { executorService.execute(pieceDao::delete_all);}

    // Get a piece by its ID
    public LiveData<Piece> getPieceById(int pieceId) {
        return pieceDao.getPieceById(pieceId);
    }
}
