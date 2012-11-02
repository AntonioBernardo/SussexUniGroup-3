//package uk.ac.sussex.asegr3.tracker.client.ui;
//
//import android.R.menu;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//public class UiLogin extends Activity {
//
//	
//	    private EditText username;
//	    private EditText password;
//
//	    public String user_name;
//	    public String pass_word;
//
//
//	    /** Called when the activity is first created. */
//	    @Override
//	    public void onCreate(Bundle savedInstanceState) {
//	        super.onCreate(savedInstanceState);
//	        setContentView(R.layout.UiLogin);
//
//	        username = (EditText) findViewById(R.id.ev_unameLogin);
//	        password = (EditText) findViewById(R.id.ev_passwordLogin);
//
//	        final Button button = (Button) findViewById(R.id.btn_login);
//	        button.setOnClickListener(new View.OnClickListener() {           
//	            public void onClick(View v) {              
//
//	                user_name = username.getText().toString();
//	                pass_word = password.getText().toString();
//
//	                Intent goToNextActivity = new Intent(getApplicationContext(), menu.class);
//	                startActivity(goToNextActivity);  //TrackingActivity
//
//
//	            }
//	        });                 
//
//
//	    }
//	}
//
