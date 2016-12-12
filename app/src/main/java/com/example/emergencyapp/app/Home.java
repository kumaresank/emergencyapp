package com.example.emergencyapp.app;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class Home extends ActionBarActivity {
    Button exit,reset;
    TextView time,timer2;
    TimerDatabase td;
    MessagedbHandler mh;
    DatabaseHandler dh;
    LocationManager manager;
    public static Activity fa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        exit= (Button) findViewById(R.id.exit);
        reset= (Button) findViewById(R.id.reset);
        time= (TextView) findViewById(R.id.showtimer);
        timer2= (TextView) findViewById(R.id.timer2);
         dh=new DatabaseHandler(this);
         td=new TimerDatabase(this);
         mh=new MessagedbHandler(this);
        check();
        manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            //buildAlertMessageNoGps();
            Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            intent.putExtra("enabled", true);
            sendBroadcast(intent);
        }

         registerReceiver(uiUpdated1, new IntentFilter("FIRST_COUNT_UPDATED"));
         registerReceiver(uiUpdated2, new IntentFilter("SECOND_COUNT_UPDATED"));

        reset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                stopService(new Intent(getBaseContext(), Maintimer.class));
                startService(new Intent(getBaseContext(), Maintimer.class));

                           }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Home.this,ExitSecurity.class);
                Home.this.startActivity(mainIntent);
                  }
        });
        fa = this;
    }
    private BroadcastReceiver uiUpdated1 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            time.setText(intent.getExtras().getString("firstcount"));
               }
    };
    private BroadcastReceiver uiUpdated2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            timer2.setText(intent.getExtras().getString("secondcount"));
        }
    };

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopService(new Intent(this, Maintimer.class));
        stopService(new Intent(this, Secondarytimer.class));

    }
    @Override
    public void onResume()
    {
        super.onResume();
        registerReceiver(uiUpdated1, new IntentFilter("FIRST_COUNT_UPDATED"));
        registerReceiver(uiUpdated2, new IntentFilter("SECOND_COUNT_UPDATED"));
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(uiUpdated1);
        unregisterReceiver(uiUpdated2);
     }
    @Override
    protected void onRestart()
    {
        super.onRestart();
        registerReceiver(uiUpdated1, new IntentFilter("FIRST_COUNT_UPDATED"));
        registerReceiver(uiUpdated2, new IntentFilter("SECOND_COUNT_UPDATED"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.contact) {
            Intent mainIntent = new Intent(Home.this,Main_Screen.class);
            Home.this.startActivity(mainIntent);
             }
        if (id == R.id.message) {
            Intent mainIntent = new Intent(Home.this,MessageEdit.class);
            Home.this.startActivity(mainIntent);
                  }
        if (id == R.id.settimer) {
            Intent mainIntent = new Intent(Home.this,Timesetter.class);
            Home.this.startActivity(mainIntent);
                        }
        if (id == R.id.changepin) {
            Intent mainIntent = new Intent(Home.this,Update_Pin.class);
            Home.this.startActivity(mainIntent);
               }
        if (id == R.id.about) {
            Intent mainIntent = new Intent(Home.this,About.class);
            Home.this.startActivity(mainIntent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void check()
    {

        int c = dh.Get_Total_Contacts();
        if(c==0)
        {
            Intent mainIntent = new Intent(Home.this,Main_Screen.class);
            Home.this.startActivity(mainIntent);
        }
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onBackPressed() {

            }
}

