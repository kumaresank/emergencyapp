package com.example.emergencyapp.app;

/**
 * Created by qencpu01 on 22/8/14.
 */
public class Message {
    // private variables
    public int _id;
    public String _msg;

    public Message() {
    }

    // constructor
    public Message(int id, String msg) {
        this._id = id;
        this._msg = msg;
    }

    // constructor
    public Message(String msg) {
        this._msg = msg;
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
    public String getMsg() {
        return this._msg;
    }

    // setting name
    public void setMsg(String msg) {
        this._msg = msg;
    }
}
