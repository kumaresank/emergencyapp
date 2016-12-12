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

public class Enterpin extends Activity {
EditText spin;
    Button ok;
    SecuritydbHandler sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enterpin);
        spin= (EditText) findViewById(R.id.spin);
        ok = (Button) findViewById(R.id.ok);
        sh=new SecuritydbHandler(this);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = sh.Get_Pin(1).getPin();
                if(s.equals(spin.getText().toString()))
                {
                    Intent mainIntent = new Intent(Enterpin.this,Home.class);
                    Enterpin.this.startActivity(mainIntent);
                    Enterpin.this.finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Wrong Pin", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
