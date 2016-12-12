package com.example.emergencyapp.app;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Add_Update_User extends Activity {
    EditText add_name, add_mobile;
    Button add_save_btn, add_view_all, update_btn;
    LinearLayout add_view, update_view;
    String name=null,number=null,Toast_msg = null;
    int USER_ID;
    DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.add_update_screen);
		// set screen
	Set_Add_Update_Screen();

	// set visibility of view as per calling activity
	String called_from = getIntent().getStringExtra("called");

	if (called_from.equalsIgnoreCase("add")) {
	    add_view.setVisibility(View.VISIBLE);
	    update_view.setVisibility(View.GONE);
	} else {

	    update_view.setVisibility(View.VISIBLE);
	    add_view.setVisibility(View.GONE);
	    USER_ID = Integer.parseInt(getIntent().getStringExtra("USER_ID"));

	    Contact c = dbHandler.Get_Contact(USER_ID);

	    add_name.setText(c.getName());
	    add_mobile.setText(c.getPhoneNumber());
	    
	    // dbHandler.close();
	}
	
	
	add_save_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		
            name=add_name.getText().toString();
            number=add_mobile.getText().toString();
            if (name.equals("") && number.equals(""))
            {
                Toast_msg = "Please Enter Valid Details";
                Show_Toast(Toast_msg);
            }
            else
            {
		    dbHandler.Add_Contact(new Contact(name,
			    number));
		    Toast_msg = "Data inserted successfully";
		    Show_Toast(Toast_msg);
		    Reset_Text();
            }
	    }
	});

	update_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub

		name = add_name.getText().toString();
		number = add_mobile.getText().toString();
		
		    dbHandler.Update_Contact(new Contact(USER_ID,name,
			    number));
		    dbHandler.close();
		    Toast_msg = "Data Update successfully";
		    Show_Toast(Toast_msg);
		    Reset_Text();
	

	    }
	});
	
	add_view_all.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent view_user = new Intent(Add_Update_User.this,
			Main_Screen.class);
		view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(view_user);
		finish();
	    }
	});


	add_name.setOnClickListener(new View.OnClickListener() {
		@Override
	    public void onClick(View arg0) {
		Intent intent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
		intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
		startActivityForResult(intent, 1);
		}
			});
	
    
    }
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (resultCode == RESULT_OK) {
	Uri ur=data.getData();
	Cursor cc=managedQuery(ur, null, null, null, null);
	if(cc.moveToFirst())
	{
	String name=cc.getString(cc.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	String number=cc.getString(cc.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        String number1=number.substring(number.length()-10);
	add_name.setText(name);
	add_mobile.setText(number1);
	}
	}
	}
    
    public void Set_Add_Update_Screen() {

	add_name = (EditText) findViewById(R.id.add_name);
	add_mobile = (EditText) findViewById(R.id.add_mobile);
	add_save_btn = (Button) findViewById(R.id.add_save_btn);
	update_btn = (Button) findViewById(R.id.update_btn);
	add_view_all = (Button) findViewById(R.id.add_view_all);
	add_view = (LinearLayout) findViewById(R.id.add_view);
	update_view = (LinearLayout) findViewById(R.id.update_view);
	add_view.setVisibility(View.GONE);
	update_view.setVisibility(View.GONE);

    }

    public void Show_Toast(String msg) {
	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void Reset_Text() {

	add_name.getText().clear();
	add_mobile.getText().clear();
	    }
    }
