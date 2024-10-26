package com.zybooks.practicepals.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zybooks.practicepals.database.entities.Piece;
import com.zybooks.practicepals.database.entities.PracticeLog;
import com.zybooks.practicepals.database.dao.PracticeLogDao;
import com.zybooks.practicepals.database.AppDatabase;

public class PracticeLogRepository {
    private static PracticeLogRepository instance;
    private final PracticeLogDao practiceLogDao;
    private final ExecutorService executorService;

    private PracticeLogRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        practiceLogDao = db.practiceLogDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    public static synchronized PracticeLogRepository getInstance(Application application) {
        if (instance == null) {
            instance = new PracticeLogRepository(application);
        }
        return instance;
    }


    public void insert(PracticeLog practiceLog) {
        executorService.execute(() -> practiceLogDao.insert(practiceLog));
    }

    public void update(PracticeLog practiceLog) {
        executorService.execute(() -> practiceLogDao.update(practiceLog));
    }

    public LiveData<List<PracticeLog>> getPracticeLogsForPiece(int pieceId) {
        return practiceLogDao.getLogsForPiece(pieceId);
    }

    public PracticeLog getLogById(int logId) {
        return practiceLogDao.getLogById(logId);
    }

    public void delete(PracticeLog practiceLog) {
        executorService.execute(() -> practiceLogDao.delete(practiceLog));
    }
}
