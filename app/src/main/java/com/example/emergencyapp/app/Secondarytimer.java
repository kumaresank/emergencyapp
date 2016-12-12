package com.example.emergencyapp.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by qencpu01 on 30/8/14.
 */

public class Secondarytimer extends Service {
    CountDownTimer Count;
    TimerDatabase td;
    DatabaseHandler dh;
    MessagedbHandler mh;
    LocationListener ll;
    LocationManager manager;
    Location l;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        super.onStartCommand(intent, flags, startId);
        td=new TimerDatabase(this);
        dh=new DatabaseHandler(this);
        mh = new MessagedbHandler(this);
        manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        l = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Count = new CountDownTimer((Integer.parseInt(td.Get_Time(2).getTime())*10), 1000)
       {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                String time = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toDays(millis)),TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                );

                Intent i = new Intent("SECOND_COUNT_UPDATED");
                i.putExtra("secondcount",time);

                sendBroadcast(i);
                ll=new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        l=location;
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                };

                //coundownTimer.setTitle(millisUntilFinished / 1000);

            }
            public void onFinish() {
                String msg = mh.Get_Message(1).getMsg();
                if (manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {

                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);

                    //get last known location, if available
                    l = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if(l != null) {
                        msg += " I'm in the following location. http://maps.google.com/?q=" + l.getLatitude() + "," + l.getLongitude();
                        Toast.makeText(getApplicationContext(), msg,
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "no loc!",
                                Toast.LENGTH_LONG).show();
                    }
                }

                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms=SmsManager.getDefault();
                for(int j=1;j<=dh.Get_Total_Contacts();j++)
                {
                    sms.sendTextMessage(dh.Get_Contact(j).getPhoneNumber(), null,msg, null,null);
                }
                Count.start();
            }
        };

        Count.start();
        return START_STICKY;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Count.cancel();
    }
}
