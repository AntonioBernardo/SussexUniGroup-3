package uk.ac.sussex.asegr3.tracker.client.ui;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.concurrent.Executor;

import uk.ac.sussex.asegr3.tracker.client.service.NewUserCallback;
import uk.ac.sussex.asegr3.tracker.client.service.NewUserService;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApiFactory;
import uk.ac.sussex.asegr3.transport.beans.Base64Encoder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;



public class UiRegister extends Activity implements NewUserCallback{

	// Initializing variables
	EditText inputName;
	EditText inputPass;
	EditText inputPass2;
	
	private static final String DEFAULT_HOSTNAME = "176.34.83.168:5050";
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final ProgressBar progress = (ProgressBar)findViewById(R.id.progressBar1);
        
        //getting the connection of the objects
        inputName = (EditText) findViewById(R.id.editText1);
		inputPass = (EditText) findViewById(R.id.editText2);
		inputPass2 = (EditText) findViewById(R.id.EditText01);
		
		
		try {
			final NewUserService userService=new NewUserService(HttpTransportClientApiFactory.create(DEFAULT_HOSTNAME, AndroidLogger.
			INSTANCE, createNetworkInfoProvider(UiRegister.this), new AndroidBase64Encoder()), AsyncTask.SERIAL_EXECUTOR, (NewUserCallback)UiRegister.this);
		
			Button next = (Button) findViewById(R.id.button1);
        
			next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                
            	
            	
				userService.signUp(inputName.getText().toString(), inputPass.getText().toString(), inputPass2.getText().toString());
				
			//  progress.setVisibility(ProgressBar.VISIBLE);
                Toast.makeText(getBaseContext(),
						"Loading.....",
						Toast.LENGTH_SHORT).show();
                
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
        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}


	@Override
	public void processNewUser(HttpTransportClientApi api) {
		Intent myIntent = new Intent(this.getBaseContext(), TrackingActivity.class);
		//Sending data to another Activity
        myIntent.putExtra("name", inputName.getText().toString());
        myIntent.putExtra("password", inputPass.getText().toString());
		startActivity(myIntent);
	}


	@Override
	public void processFailedSignup(Exception e) {
		// TODO Auto-generated method stub
		e.printStackTrace();
		System.out.println(e.getMessage());
		Intent myIntent = new Intent(this.getBaseContext(), UiError.class);
   	 	startActivity(myIntent);
		
	}
	
	private static class AndroidBase64Encoder implements Base64Encoder {

		@Override
		public String encode(byte[] bytes) {
			return Base64.encodeToString(bytes, Base64.DEFAULT);
		}

		@Override
		public byte[] decode(String code) {
			return Base64.decode(code, Base64.DEFAULT);
		}
		
	}
	
	private NetworkInfoProvider createNetworkInfoProvider(final Activity activity){
		return new NetworkInfoProvider(){

			@Override
			public NetworkInfo getNetworkInfo() {
				return UiRegister.this.getNetworkInfo(activity);
			}
			
		};
	}
	
	private NetworkInfo getNetworkInfo(Activity activity) {
		 ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		 return connectivityManager.getActiveNetworkInfo();
	}
	
}
