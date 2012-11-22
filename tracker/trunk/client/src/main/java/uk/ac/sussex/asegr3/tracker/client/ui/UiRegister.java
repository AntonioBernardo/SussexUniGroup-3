package uk.ac.sussex.asegr3.tracker.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class UiRegister extends Activity {

	// Initializing variables
	EditText inputName;
	EditText inputPass;
	EditText inputPass2;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        
        //getting the connection of the objects
        inputName = (EditText) findViewById(R.id.editText1);
		inputPass = (EditText) findViewById(R.id.editText2);
		inputPass2 = (EditText) findViewById(R.id.EditText01);
		
        Button next = (Button) findViewById(R.id.button1);
        
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                
            	Intent myIntent = new Intent(view.getContext(), TrackingActivity.class);
                
              //Sending data to another Activity
                myIntent.putExtra("name", inputName.getText().toString());
                myIntent.putExtra("password", inputPass.getText().toString());
                
                //Validating password
                if(inputName.getText().toString().isEmpty()){
                	
                	Toast.makeText(getApplicationContext(), 
                            "Please make sure the name is not empty", Toast.LENGTH_LONG).show();

                }else if (inputPass.getText().toString().isEmpty()){
                	
                	Toast.makeText(getApplicationContext(), 
                            "Please make sure the password is not empty", Toast.LENGTH_LONG).show();
                	
                }else if (!inputPass.getText().toString().equals(inputPass2.getText().toString())){
                
                	Toast.makeText(getApplicationContext(), 
                            "Please make sure the password is the same", Toast.LENGTH_LONG).show();
                	
                }else{
                	startActivity(myIntent);
                }
                
                
            }

        });

        Button exit = (Button) findViewById(R.id.button2);
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), UiLogin.class);
                startActivity(myIntent);
                finish();
            }

        });
        
	}
	
}
