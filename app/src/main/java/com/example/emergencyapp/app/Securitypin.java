package com.example.emergencyapp.app;

/**
 * Created by qencpu01 on 23/8/14.
 */
public class Securitypin {
    public int _id;
    public String _pin;

    public Securitypin() {
    }

    // constructor
    public Securitypin(int id, String _pin) {
        this._id = id;
        this._pin = _pin;
    }

    // constructor
    public Securitypin(String _pin) {
        this._pin = _pin;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting pin
    public String getPin() {
        return this._pin;
    }

    // setting pin
    public void setPin(String pin) {
        this._pin = pin;
    }



}
