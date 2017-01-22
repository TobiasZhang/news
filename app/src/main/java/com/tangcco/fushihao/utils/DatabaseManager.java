package com.tangcco.fushihao.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/9/21.
 */
public class DatabaseManager {
    private AtomicInteger mOpenCounter = new AtomicInteger();

    private static DataBaseHelper instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(Context context, SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DataBaseHelper(context);
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DataBaseHelper getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DataBaseHelper.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if(mOpenCounter.incrementAndGet() == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        if(mOpenCounter.decrementAndGet() == 0) {
            // Closing database
            mDatabase.close();
        }
    }
}
