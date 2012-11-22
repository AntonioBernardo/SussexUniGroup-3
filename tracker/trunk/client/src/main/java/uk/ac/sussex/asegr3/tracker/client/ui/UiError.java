package uk.ac.sussex.asegr3.tracker.client.ui;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UiError extends Activity {

	
    @Override
    public void onCreate(Bundle icicle) {
        
    	
       
    	
    	super.onCreate(icicle);
   
    	setContentView(R.layout.loadingerror);
        
            //register button activity
            Button register = (Button) findViewById(R.id.button1);
            register.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), UiRegister.class);
                    startActivity(myIntent);
                    
                }

            });
            
           
            //back button activity
            Button back = (Button) findViewById(R.id.button2);
            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), UiLogin.class);
                    startActivity(myIntent);
                }

            });

    }

	

}
