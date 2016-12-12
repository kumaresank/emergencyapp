package com.example.emergencyapp.app.model;

/**
 * Created by A on 9/21/2014.
 */
public class ValidateLicense {

    public long Id;
    public String ApplicationName;
    public String Device_Type;
    public String Device_Id;
    public String License_Type;
    public String License_Start_Date;
    public String License_End_Date;
    public String ResultCode;
    public String ResultDescription;

    public ValidateLicense(String ApplicationName, String Device_Type, String Device_Id, String License_Type, String License_Start_Date, String License_End_Date, String ResultCode, String ResultDescription) {
        this.ApplicationName = ApplicationName;
        this.Device_Type = Device_Type;
        this.Device_Id = Device_Id;
        this.License_Type = License_Type;
        this.License_Start_Date = License_Start_Date;
        this.License_End_Date = License_End_Date;
        this.ResultCode = ResultCode;
        this.ResultDescription = ResultDescription;
    }

    public void setId(long Id)
    {
        this.Id = Id;
    }

    public long getId()
    {
        return this.Id;
    }
}
