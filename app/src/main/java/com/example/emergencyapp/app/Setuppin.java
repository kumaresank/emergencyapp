package com.example.emergencyapp.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Setuppin extends Activity {
Button done,cancel;
    EditText newpin,confirm;
    SecuritydbHandler sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setuppin);
        sh=new SecuritydbHandler(this);
        done= (Button) findViewById(R.id.done);
        cancel= (Button) findViewById(R.id.cancel);
        newpin = (EditText) findViewById(R.id.newpin);
        confirm = (EditText) findViewById(R.id.confirm);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin = newpin.getText().toString();
                String confirmpin = confirm.getText().toString();
                if(pin==null || pin.equals("") || confirmpin ==null || confirmpin.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Pin",Toast.LENGTH_LONG).show();
                }
                else if(pin.equals(confirmpin))
                {
                    sh.Add_Pin(new Securitypin(pin));
                    Intent mainIntent = new Intent(Setuppin.this,Home.class);
                    Setuppin.this.startActivity(mainIntent);
                    Setuppin.this.finish();
                    Toast.makeText(getApplicationContext(),"Security Pin Created",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Pin does not match",Toast.LENGTH_LONG).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setuppin.this.finish();
            }
        });

    }

}
