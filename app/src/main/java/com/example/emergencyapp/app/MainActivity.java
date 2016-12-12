package com.example.emergencyapp.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.emergencyapp.app.model.LicenseValidateHelper;
import com.example.emergencyapp.app.model.ValidateLicense;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.telephony.TelephonyManager;
public class MainActivity extends Activity {
   private final int SPLASH_DISPLAY_LENGTH = 2000;
    SecuritydbHandler sh;
    TimerDatabase td;
    MessagedbHandler mh;
    DatabaseHandler dh;
    String Notify = "";
    ProgressBar pg;
    LicenseValidateHelper db;

    private static final String LOG = WebService.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        db = new LicenseValidateHelper(getApplicationContext());
        pg = (ProgressBar) findViewById(R.id.progressBar);
        sh=new SecuritydbHandler(this);
        td=new TimerDatabase(this);
        mh=new MessagedbHandler(this);
        dh=new DatabaseHandler(this);
        check_internet();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                {
                    licence_check();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public void check_internet() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getActiveNetworkInfo() != null && connec.getActiveNetworkInfo().isAvailable()

                && connec.getActiveNetworkInfo().isConnected()) {



        } else  {

            internet_enable();

        }

    }
public void licence_check()
{
    try {
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String device =  tm.getDeviceId();
        ValidateLicense lic =  db.getTodo("SMART_ALERT","MOBILE", "TRIAL");
     //   tv.setText("Your Application A.1");
        if(lic == null)
        {
       //     tv.setText("Your Application A.");

            AsyncCallWS task = new AsyncCallWS();
            task.execute();
            db.close();
        }
        else
        {
      //      tv.setText("Your Application B.");
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
      //      tv.setText("Your Application B." + lic.License_End_Date);
            Date date1 = new Date(), date2;
            try {
                date2 = format.parse(lic.License_End_Date);
                if(date2.getTime() < date1.getTime()) {
          //          tv.setText("Your Application Expired.");
                    Intent mainIntent = new Intent(MainActivity.this,Licence_Expired.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();

                }
                else {
                    startService(new Intent(getBaseContext(), Maintimer.class));
                    check();
                }
                Notify = Notify +  "\n Current Time : " + date1.getTime();
                Notify = Notify +  "\n Current Time : " + date2.getTime();
            }
            catch (ParseException ex) {

            //    tv.setText(ex.toString());
            }

        }
    } catch (Exception ex) {
    //    Notify +=  "\n Exception On Click : " + ex.toString();
    //    tv.setText(Notify);
    }
    finally {
        db.close();
    }
   }
public void check()
{
    int cnt= new TimerDatabase(this).count();
    if(cnt == 0)
    {
        td.Add_Time(new Timer("360000"));
        td.Add_Time(new Timer("60000"));
    }
    int con=new MessagedbHandler(this).count();
    if(con == 0)
    {
        mh.Add_Message(new Message("I am in emergency"));
        mh.Add_Message(new Message("Now I am in the Following "));
    }
  int c = sh.count();
    if(c == 0)
    {
        Intent mainIntent = new Intent(MainActivity.this,Setuppin.class);
        MainActivity.this.startActivity(mainIntent);
        MainActivity.this.finish();
    }
else
    {
        Intent mainIntent = new Intent(MainActivity.this,Enterpin.class);
        MainActivity.this.startActivity(mainIntent);
        MainActivity.this.finish();
    }
}
    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        String PersonalNotify= "";
        @Override
        protected Void doInBackground(String... params) {
            TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String device =  tm.getDeviceId();
            //tv.setText("Calling Web Service..");
            String xml = WebService.invokeHelloWorldWS(device);
            Document doc = WebService.getDomElement(xml);
            NodeList nl = doc.getElementsByTagName("LicenseVerify");
            Element e = (Element) nl.item(0);
            String ApplicationName = WebService.getValue(e, "ApplicationName");
            String Device_Type = WebService.getValue(e, "Device_Type");
            String Device_Id = WebService.getValue(e, "Device_Id");
            String License_Type = WebService.getValue(e, "License_Type");
            String License_Start_Date = WebService.getValue(e, "License_Start_Date");
            String License_End_Date = WebService.getValue(e, "License_End_Date");
            String ResultCode = WebService.getValue(e, "ResultCode");
            String ResultDescription = WebService.getValue(e, "ResultDescription");
            ValidateLicense lic = new ValidateLicense(ApplicationName, Device_Type, Device_Id, License_Type, License_Start_Date, License_End_Date, ResultCode, ResultDescription);
            long id = db.CreateValidateLicense(lic);
            lic.setId(id);
            db.close();
            PersonalNotify += "\n Web Service xml : " + xml;

            if(ResultCode.toUpperCase().contains("RECORD")) {
                String dtEnd = WebService.getValue(e, "License_End_Date");
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date1 = new Date(), date2;
                try {
                    date2 = format.parse(dtEnd);
                    PersonalNotify +=  "\n End Time : " + date2.toString();
                    PersonalNotify +=  "\n Current Time : " + date1.getTime();
                    if(date2.getTime() < date1.getTime()) {
                        PersonalNotify +=  "\n Expired ";
                    }
                    else {
                        PersonalNotify +=  "\n Active ";
                    }
                }
                catch (ParseException ex) {
                    PersonalNotify +=  "\n Expire : " + ex.toString();
                }
                finally {
                    db.close();
                }
            }
            else {
                PersonalNotify +=  "\n Error Occured in Web Service : ResultCode " + ResultCode;
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            pg.setVisibility(View.INVISIBLE);
       //     tv.setText(PersonalNotify + "\n Processing Completed");
            licence_check();
            db.close();
        }

        @Override
        protected void onPreExecute() {
            pg.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    public void internet_enable()
    {
        ConnectivityManager dataManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        Method dataMtd = null;
        try {
            dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        dataMtd.setAccessible(true);
         try {
            dataMtd.invoke(dataManager, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }




    }
}
