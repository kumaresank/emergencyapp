package com.example.emergencyapp.app;

/**
 * Created by qencpu01 on 19/8/14.
 */
public class Timer {
    public int _id;
    public String _time;

    public Timer() {
    }

    // constructor
    public Timer(int id, String time) {
        this._id = id;
        this._time = time;
    }

    // constructor
    public Timer(String time) {
        this._time = time;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting name
    public String getTime() {
        return this._time;
    }

    // setting name
    public void setTime(String time) {
        this._time = time;
    }
}
