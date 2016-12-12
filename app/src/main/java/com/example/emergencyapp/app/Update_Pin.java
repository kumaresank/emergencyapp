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

public class Update_Pin extends Activity {
    Button done,cancel;
    EditText newpin,confirm,current;
    SecuritydbHandler sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__pin);
        sh=new SecuritydbHandler(this);
        done= (Button) findViewById(R.id.done);
        cancel= (Button) findViewById(R.id.cancel);
        newpin = (EditText) findViewById(R.id.newpin);
        confirm = (EditText) findViewById(R.id.confirm);
        current = (EditText) findViewById(R.id.oldpin);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin = newpin.getText().toString();
                String confirmpin = confirm.getText().toString();
                String currentpin = current.getText().toString();
                if(pin==null || pin.equals("") || confirmpin ==null || confirmpin.equals("") || currentpin==null || currentpin.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Pin", Toast.LENGTH_LONG).show();
                }
                else if(pin.equals(confirmpin))
                {
                    if(currentpin.equals(sh.Get_Pin(1).getPin()))
                    {
                        sh.Update_Pin(new Securitypin(1,pin));
                        sh.close();

                        Intent mainIntent = new Intent(Update_Pin.this,Home.class);
                        Update_Pin.this.startActivity(mainIntent);
                        Update_Pin.this.finish();
                        Toast.makeText(getApplicationContext(),"New Pin Changed",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Current Pin Is Wrong",Toast.LENGTH_LONG).show();
                        current.setText("");
                        newpin.setText("");
                        confirm.setText("");
                    }
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
                Intent mainIntent = new Intent(Update_Pin.this,Home.class);
                Update_Pin.this.startActivity(mainIntent);
                Update_Pin.this.finish();
            }
        });

    }
    }


