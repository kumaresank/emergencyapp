package com.example.emergencyapp.app;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by qencpu01 on 19/8/14.
 */
public class TimerDatabase extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TimeManager";

    // Contacts table name
    private static final String TABLE_TIMERS = "timers";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "time";


    public TimerDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TIMERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMERS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void Add_Time(Timer time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, time.getTime()); // Contact Name
        // Inserting Row
        db.insert(TABLE_TIMERS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Timer Get_Time(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TIMERS, new String[] { KEY_ID,
                KEY_NAME}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Timer time = new Timer(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        cursor.close();
        db.close();

        return time;
    }

    // Updating single contact
    public int Update_Time(Timer time) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, time.getTime());
        return db.update(TABLE_TIMERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(time.getID()) });

    }

    // Deleting single contact
    public void Delete_Time(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TIMERS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    // Getting contacts Count
    public int count() {
        String countQuery = "SELECT  * FROM " + TABLE_TIMERS;
        SQLiteDatabase db = this.getReadableDatabase();
        int c = db.rawQuery(countQuery, null).getCount();
        db.close();

        // return count
        return c;
    }
}
