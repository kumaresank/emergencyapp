package com.example.emergencyapp.app.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LicenseValidateHelper  extends SQLiteOpenHelper {

    public LicenseValidateHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String LOG = LicenseValidateHelper.class.getName();
    private static final String TABLE_TODO = "LicenseValidate";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LicenseValidate";

    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_APPLICATIONNAME = "ApplicationName";
    private static final String KEY_DEVICE_TYPE = "Device_Type";
    private static final String KEY_DEVICE_ID = "Device_Id";
    private static final String KEY_LICENSE_TYPE = "License_Type";
    private static final String KEY_LICENSE_START_DATE = "License_Start_Date";
    private static final String KEY_LICENSE_END_DATE = "License_End_Date";
    private static final String KEY_RESULTCODE = "ResultCode";
    private static final String KEY_RESULTDESCRIPTION = "ResultDescription";

    private static final String CREATE_TABLE_LICENSEVALIDATE = "CREATE TABLE "
            + TABLE_TODO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_APPLICATIONNAME
            + " TEXT," + KEY_DEVICE_TYPE + " TEXT," + KEY_DEVICE_ID
            + " TEXT," + KEY_LICENSE_TYPE + " TEXT," + KEY_LICENSE_START_DATE
            + " DATETIME," + KEY_LICENSE_END_DATE + " DATETIME," + KEY_RESULTCODE
            + " TEXT," + KEY_RESULTDESCRIPTION + " TEXT," + KEY_CREATED_AT
            + " DATETIME" + ")";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LICENSEVALIDATE);

        String selectQuery = "SELECT  * FROM " + TABLE_TODO ;

        int c = db.rawQuery(selectQuery, null).getCount();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_LICENSEVALIDATE);
        // create new tables
        onCreate(db);
    }

    public long CreateValidateLicense(ValidateLicense todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_APPLICATIONNAME, todo.ApplicationName);
        values.put(KEY_DEVICE_TYPE, todo.Device_Type);
        values.put(KEY_DEVICE_ID, todo.Device_Id);
        values.put(KEY_LICENSE_TYPE, todo.License_Type);
        values.put(KEY_LICENSE_START_DATE, todo.License_Start_Date);
        values.put(KEY_LICENSE_END_DATE, todo.License_End_Date);
        values.put(KEY_RESULTCODE, todo.ResultCode);
        values.put(KEY_RESULTDESCRIPTION, todo.ResultDescription);

        long todo_id = db.insert(TABLE_TODO, null, values);
        return todo_id;
    }

    public ValidateLicense getTodo(String ApplicationName, String Device_Type, String License_Type) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TODO + " WHERE "
                + KEY_APPLICATIONNAME + " = '" + ApplicationName
                + "' and " + KEY_DEVICE_TYPE + " = '" + Device_Type + "' and " + KEY_LICENSE_TYPE+ " = '" + License_Type + "'";

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {

            String APPLICATIONNAME = (c.getString(c.getColumnIndex(KEY_APPLICATIONNAME)));
            String DEVICE_TYPE = (c.getString(c.getColumnIndex(KEY_DEVICE_TYPE)));
            String DEVICE_ID = (c.getString(c.getColumnIndex(KEY_DEVICE_ID)));
            String LICENSE_TYPE = (c.getString(c.getColumnIndex(KEY_LICENSE_TYPE)));
            String RESULTCODE = (c.getString(c.getColumnIndex(KEY_RESULTCODE)));
            //long LICENSE_END_DATE = (c.getLong(c.getColumnIndex(KEY_LICENSE_END_DATE)));
            //long LICENSE_START_DATE = (c.getLong(c.getColumnIndex(KEY_LICENSE_START_DATE)));
            String LICENSE_START_DATE = (c.getString(c.getColumnIndex(KEY_LICENSE_START_DATE)));
            String LICENSE_END_DATE = (c.getString(6));
            String RESULTDESCRIPTION = "";//(c.getString(c.getColumnIndex(KEY_RESULTDESCRIPTION)));
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date dt_LICENSE_END_DATE = new Date();
            Date dt_LICENSE_START_DATE = new Date();

            ValidateLicense td = new ValidateLicense(APPLICATIONNAME,
                    DEVICE_TYPE,
                    DEVICE_ID,
                    LICENSE_TYPE,
                    format.format(dt_LICENSE_START_DATE),
                    LICENSE_END_DATE,
                    RESULTCODE,
                    RESULTDESCRIPTION
            );
//            td.setId((c.getLong(c.getColumnIndex(KEY_ID))));

            return td;

        }
        else {
            return null;
        }
    }

    public List<ValidateLicense> getAllToDos() {
        List<ValidateLicense> todos = new ArrayList<ValidateLicense>();
        String selectQuery = "SELECT  * FROM " + TABLE_TODO;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ValidateLicense td = new ValidateLicense((c.getString(c.getColumnIndex(KEY_APPLICATIONNAME))),
                        (c.getString(c.getColumnIndex(KEY_DEVICE_TYPE))),
                        (c.getString(c.getColumnIndex(KEY_DEVICE_ID))),
                        (c.getString(c.getColumnIndex(KEY_LICENSE_TYPE))),
                        (c.getString(c.getColumnIndex(KEY_LICENSE_START_DATE))),
                        (c.getString(c.getColumnIndex(KEY_LICENSE_END_DATE))),
                        (c.getString(c.getColumnIndex(KEY_RESULTCODE))),
                        (c.getString(c.getColumnIndex(KEY_RESULTDESCRIPTION)))
                );

                td.setId((c.getLong(c.getColumnIndex(KEY_RESULTDESCRIPTION))));

                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;



    }

    public int updateToDo(ValidateLicense todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_APPLICATIONNAME, todo.ApplicationName);
        values.put(KEY_DEVICE_TYPE, todo.Device_Type);
        values.put(KEY_DEVICE_ID, todo.Device_Id);
        values.put(KEY_LICENSE_TYPE, todo.License_Type);
        values.put(KEY_LICENSE_START_DATE, todo.License_Start_Date);
        values.put(KEY_LICENSE_END_DATE, todo.License_End_Date);
        values.put(KEY_RESULTCODE, todo.ResultCode);
        values.put(KEY_RESULTDESCRIPTION, todo.ResultDescription);

        // updating row
        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(todo.getId()) });
    }

    public void deleteToDo(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
