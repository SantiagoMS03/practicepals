package com.zybooks.practicepals.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.util.Log;

import com.zybooks.practicepals.database.entities.Piece;
import com.zybooks.practicepals.database.dao.PieceDao;
import com.zybooks.practicepals.database.entities.PracticeLog;
import com.zybooks.practicepals.database.dao.PracticeLogDao;

@Database(
        entities = {Piece.class, PracticeLog.class},
        version = 3,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    // DAOs
    public abstract PieceDao pieceDao();
    public abstract PracticeLogDao practiceLogDao();

    // Singleton instance
    private static volatile AppDatabase INSTANCE;

    // Method to get the database instance
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "practice_pals_database")
                            .fallbackToDestructiveMigration()  // Enable destructive migration
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
