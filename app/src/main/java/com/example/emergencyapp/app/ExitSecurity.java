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
import android.widget.Toast;

public class ExitSecurity extends Activity {
    EditText spin;
    Button ok;
    SecuritydbHandler sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_exit_security);
        spin = (EditText) findViewById(R.id.spin);
        ok = (Button) findViewById(R.id.ok);
        sh = new SecuritydbHandler(this);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = sh.Get_Pin(1).getPin();
                if (s.equals(spin.getText().toString())) {
                    stopService(new Intent(getBaseContext(), Maintimer.class));
                    stopService(new Intent(getBaseContext(), Secondarytimer.class));
                    Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
                    intent.putExtra("enabled", false);
                    sendBroadcast(intent);
                    Home.fa.finish();
                    ExitSecurity.this.finish();
                } else {
                    Intent mainIntent = new Intent(ExitSecurity.this, Home.class);
                    ExitSecurity.this.startActivity(mainIntent);
                    ExitSecurity.this.finish();
                    Toast.makeText(getApplicationContext(), "Wrong Pin", Toast.LENGTH_LONG).show();
                }
            }

        });
    }



}
