package com.example.emergencyapp.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;
public class MessageEdit extends Activity {
    MessagedbHandler dh;
    EditText primarymsg,secondarymsg;
    Button primarysave,primarycancel,secondarysave,secondarycancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_edit);
        TabHost th= (TabHost) findViewById(R.id.tabHost);
        th.setup();
        TabHost.TabSpec th1=th.newTabSpec("primarymsg");
        th1.setContent(R.id.primarymsg);
        th1.setIndicator("Main Message");
        th.addTab(th1);
        TabHost.TabSpec th2=th.newTabSpec("secondarymsg");
        th2.setContent(R.id.secondarymsg);
        th2.setIndicator("Secondary Message");
        th.addTab(th2);
        dh = new MessagedbHandler(this);
        primarymsg= (EditText) findViewById(R.id.primarymessage);
        secondarymsg= (EditText) findViewById(R.id.secondarymessage);
        primarysave= (Button) findViewById(R.id.primarysave);
        primarycancel= (Button) findViewById(R.id.primarycancel);
        secondarysave= (Button) findViewById(R.id.secondarysave);
        secondarycancel= (Button) findViewById(R.id.secondarycancel);
            primarymsg.setText(dh.Get_Message(1).getMsg());
            secondarymsg.setText(dh.Get_Message(2).getMsg());
        primarysave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dh.Update_Message(new Message(1,primarymsg.getText().toString()));
                dh.close();
                Toast.makeText(getApplicationContext(),"Primary Message Updated Successfully",Toast.LENGTH_LONG).show();
            }
        });
        secondarysave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dh.Update_Message(new Message(2,secondarymsg.getText().toString()));
                dh.close();
                Toast.makeText(getApplicationContext(),"Secondary Message Updated Successfully",Toast.LENGTH_LONG).show();
            }
        });
        primarycancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(MessageEdit.this,Home.class);
                MessageEdit.this.startActivity(mainIntent);
                MessageEdit.this.finish();
            }
        });
        secondarycancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(MessageEdit.this,Home.class);
                MessageEdit.this.startActivity(mainIntent);
                MessageEdit.this.finish();
            }
        });

    }

}
