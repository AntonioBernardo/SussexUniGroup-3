package uk.ac.sussex.asegr3.tracker.comment.menu;


import uk.ac.sussex.asegr3.tracker.client.ui.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class CommentMenu extends Activity{
	@Override
	
	public void onCreate(Bundle savedInstanceState) {
	
	   super.onCreate(savedInstanceState);
	
	   setContentView(R.layout.main);
	
	}
	
	@Override
	
	public boolean onCreateOptionsMenu(Menu menu) {
	   MenuInflater inflater = getMenuInflater();
	   inflater.inflate(R.menu.test_menu, menu);
	   return true;
	
	}
	
	@Override
	
	public boolean onOptionsItemSelected(MenuItem item) {
	
	   switch (item.getItemId()) {
	
	   case R.id.add:
	
	      Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
	
	      return true;
	
	   case R.id.help:
	
	      Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
	
	      return true;
	
	 
	
	   default:
	
	      return super.onOptionsItemSelected(item);
	
	}
	}


}
