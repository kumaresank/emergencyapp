package com.example.emergencyapp.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by qencpu01 on 30/8/14.
 */
public class Maintimer extends Service {
    CountDownTimer Count;
    TimerDatabase td;
    DatabaseHandler dh;
    MessagedbHandler mh;
    GPSTracker gps;
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
        mh=new MessagedbHandler(this);
        Count = new CountDownTimer((Integer.parseInt(td.Get_Time(1).getTime())*10), 1000) {

            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                String time = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toDays(millis)),TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                );

                Intent i = new Intent("FIRST_COUNT_UPDATED");
                i.putExtra("firstcount",time);

                sendBroadcast(i);

                //coundownTimer.setTitle(millisUntilFinished / 1000);

            }
            public void onFinish() {
                //coundownTimer.setTitle("Sedned!");
                gps = new GPSTracker(Maintimer.this);

                Intent i = new Intent("FIRST_COUNT_UPDATED");
                i.putExtra("firstcount","Alert!");
                String msg = mh.Get_Message(1).getMsg();

                        msg += " I'm in the following location. http://maps.google.com/?q=" + gps.getLatitude() + "," + gps.getLongitude();
                        Toast.makeText(getApplicationContext(), msg,
                                Toast.LENGTH_LONG).show();



                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms=SmsManager.getDefault();
                for(int j=1;j<=dh.Get_Total_Contacts();j++)
                {
                    sms.sendTextMessage(dh.Get_Contact(j).getPhoneNumber(), null,msg, null,null);
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + dh.Get_Contact(1).getPhoneNumber()));
                startActivity(callIntent);
                sendBroadcast(i);
                startService(new Intent(getBaseContext(), Secondarytimer.class));
                //Log.d("COUNTDOWN", "FINISH!");
                stopSelf();

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
