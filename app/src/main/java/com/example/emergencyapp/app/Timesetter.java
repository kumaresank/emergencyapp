package com.example.emergencyapp.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TimePicker;
import android.widget.Toast;

public class Timesetter extends Activity {
    TimePicker tp1,tp2;
    Button setp,sets;
    TimerDatabase td;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_timesetter);
        TabHost th= (TabHost) findViewById(R.id.tabHost);
        th.setup();
        TabHost.TabSpec th1=th.newTabSpec("firsttimer");
        th1.setContent(R.id.firsttimer);
        th1.setIndicator("First Timer");
        th.addTab(th1);
        TabHost.TabSpec th2=th.newTabSpec("secondtimer");
        th2.setContent(R.id.secondtimer);
        th2.setIndicator("Second Timer");
        th.addTab(th2);
        tp1= (TimePicker) findViewById(R.id.timePicker);
        tp2= (TimePicker) findViewById(R.id.timePicker2);
        tp1.setIs24HourView(true);
        tp2.setIs24HourView(true);
        setp= (Button) findViewById(R.id.setp);
        sets= (Button) findViewById(R.id.sets);
        td = new TimerDatabase(this);
      int cnt= new TimerDatabase(this).count();
            if(cnt == 0)
        {
            td.Add_Time(new Timer("600000"));
            tp1.setCurrentHour(0);
            tp1.setCurrentMinute(10);
            tp2.setCurrentHour(0);
            tp2.setCurrentMinute(10);
        }
        else
        {
            int gt=Integer.parseInt(td.Get_Time(1).getTime());
            int min=gt/(60*100);
            int h=min/60;
            int m=min%60;
            tp1.setCurrentHour(h);
            tp1.setCurrentMinute(m);
            int gt1=Integer.parseInt(td.Get_Time(2).getTime());
            int min1=gt1/(60*100);
            int h1=min1/60;
            int m1=min1%60;
            tp2.setCurrentHour(h1);
            tp2.setCurrentMinute(m1);
        }

        setp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hh= tp1.getCurrentHour();
                int mm= tp1.getCurrentMinute();
                int h =hh*60*60*100;
                int m =mm*60*100;
                String t = String.valueOf(h+m);
                td.Update_Time(new Timer(1, t));
                td.close();
                Toast.makeText(getApplicationContext(), "Scheduled Successfully", Toast.LENGTH_LONG).show();
                stopService(new Intent(getBaseContext(), Maintimer.class));
                startService(new Intent(getBaseContext(), Maintimer.class));
                Intent mainIntent = new Intent(Timesetter.this,Home.class);
                Timesetter.this.startActivity(mainIntent);
                Timesetter.this.finish();
            }
        });
              sets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hh= tp2.getCurrentHour();
                int mm= tp2.getCurrentMinute();
                int h =hh*60*60*100;
                int m =mm*60*100;
                String t = String.valueOf(h+m);
                td.Update_Time(new Timer(2, t));
                td.close();
                Toast.makeText(getApplicationContext(), "Scheduled Successfully", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(Timesetter.this,Home.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Timesetter.this.startActivity(mainIntent);
                Timesetter.this.finish();
            }
        });
    }


}
