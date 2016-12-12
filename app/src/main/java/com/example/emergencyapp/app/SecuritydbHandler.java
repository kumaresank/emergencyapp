package com.example.emergencyapp.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qencpu01 on 23/8/14.
 */
public class SecuritydbHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SecurityManager";

    // Contacts table name
    private static final String TABLE_SECURITY = "Pins";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PIN_NO = "pin";

    public SecuritydbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PINS_TABLE = "CREATE TABLE " + TABLE_SECURITY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PIN_NO + " TEXT" + ")";
        db.execSQL(CREATE_PINS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SECURITY);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void Add_Pin(Securitypin securitypin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PIN_NO, securitypin.getPin()); // Contact Name

        // Inserting Row
        db.insert(TABLE_SECURITY, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Securitypin Get_Pin(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SECURITY, new String[] { KEY_ID,
                KEY_PIN_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Securitypin securitypin = new Securitypin(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return contact
        cursor.close();
        db.close();

        return securitypin;
    }
    // Updating single contact
    public int Update_Pin(Securitypin securitypin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PIN_NO, securitypin.getPin());
        // updating row
        return db.update(TABLE_SECURITY, values, KEY_ID + " = ?",
                new String[] { String.valueOf(securitypin.getID()) });
    }

    // Deleting single contact
    public void Delete_Contact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SECURITY, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public int count() {
        String countQuery = "SELECT  * FROM " + TABLE_SECURITY;
        SQLiteDatabase db = this.getReadableDatabase();
        int c = db.rawQuery(countQuery, null).getCount();
        db.close();

        // return count
        return c;
    }

}
