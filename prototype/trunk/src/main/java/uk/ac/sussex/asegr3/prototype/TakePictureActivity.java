package uk.ac.sussex.asegr3.prototype;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;



public class TakePictureActivity extends Activity {
	
	private static final int PIC_CROP=103;
	private static final String TAG = NewPictureActivity.class.getSimpleName(); 
	
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private Uri fileUri;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_picture);
		
		// create Intent to take a picture and return control to the calling application
//	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
//	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

	    // start the image capture Intent
//	    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		
		
		 // give the image a name so we can store it in the phone's default location
    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    	
        ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, "IMG_" + timeStamp + ".jpg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        
        //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image (this doesn't work at all for images)
        fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); // store content values
		intent.putExtra(MediaStore.EXTRA_OUTPUT,  fileUri);
       
        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//	        if (resultCode == RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
//	            Toast.makeText(this, "Image saved to:\n" +
//	                     data.getData(), Toast.LENGTH_LONG).show();
//	            System.out.println("Before cropping data");
//	            		performCrop(data.getData());
//
//	            System.out.println("After cropping data");
//	            
//	            setResult(Activity.RESULT_OK, data);
//	        } else if (resultCode == RESULT_CANCELED) {
//	            finish();
//	        } else {
//	            //FAILED
//	        	
//	        	finish();
//	        }
//	    }
//	    else if(resultCode == PIC_CROP){
	    	//get the returned data
//	    	Bundle extras = data.getExtras();
	    	//get the cropped bitmap
//	    	Bitmap thePic = extras.getParcelable("data");
	    	//retrieve a reference to the ImageView
//	    	ImageView picView = (ImageView)findViewById(R.id.commentImageView1);
	    	//display the returned cropped image
//	    	picView.setImageBitmap(thePic);
	    	
//	    }
		
		super.onActivityResult(requestCode, resultCode, data);
    	
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	
            	// Originally I was going to iterate through the list of images and grab last added to the MediaStore.
            	// But this is not necessary if we store the Uri in the image
            	/*
            	String[] projection = {MediaStore.Images.ImageColumns._ID};
            	String sort = MediaStore.Images.ImageColumns._ID + " DESC";

            	Cursor cursor = this.managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, sort);

            	try{
            		cursor.moveToFirst();
            		Long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID));
            		fileUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(id));
            	} finally{
            		cursor.close();
            	}
            	*/
                
				if(fileUri != null) {
					Log.d(TAG, "Image saved to:\n" + fileUri);
					Log.d(TAG, "Image path:\n" + fileUri.getPath());
					Log.d(TAG, "Image name:\n" + getName(fileUri)); // use uri.getLastPathSegment() if store in folder
					
					ImageView picView = (ImageView)findViewById(R.id.commentImageView1);
					picView.setImageURI(fileUri);
				}
                
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            	finish();
            } else {
                // Image capture failed, advise user
            	finish();
            }
        }
	    
	}
	
	private void performCrop(Uri uri){
		
		System.out.println("First step of cropping image");
		
		try {
		    //call the standard crop action intent (the user device may not support it)
			
			System.out.println("Before creating crop intent");
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			    //indicate image type and Uri
			cropIntent.setDataAndType(uri, "image/*");
			    //set crop properties
			cropIntent.putExtra("crop", "true");
			    //indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			    //indicate output X and Y
			cropIntent.putExtra("outputX", 256);
			cropIntent.putExtra("outputY", 256);
			    //retrieve data on return
			cropIntent.putExtra("return-data", true);
			    //start the activity - we handle returning in onActivityResult
			
			System.out.println("Before call of crop Image");
			startActivityForResult(cropIntent, PIC_CROP);

			System.out.println("After call of cropp image");
			
		}
		catch(ActivityNotFoundException anfe){
			System.out.println("Crop action not supported");
		    //display an error message
		    String errorMessage = "Whoops - your device doesn't support the crop action!";
		    Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
		    toast.show();
		}
	}

	
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
//	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

//	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
//	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
//	    if (! mediaStorageDir.exists()){
//	        if (! mediaStorageDir.mkdirs()){
//	            Log.d("MyCameraApp", "failed to create directory");
//	            return null;
//	        }
//	    }

	    // Create a media file name
//	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//	    File mediaFile;
//	    if (type == MEDIA_TYPE_IMAGE){
//	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//	        "IMG_"+ timeStamp + ".jpg");
//	    } else {
//	        return null;
//	    }
//
//	    return mediaFile;
//	}
	
	
	  /** Create a file Uri for saving an image or video to specific folder
     * https://developer.android.com/guide/topics/media/camera.html#saving-media
     * */
//    private static Uri getOutputMediaFileUri(int type)
//    {
//          return Uri.fromFile(getOutputMediaFile(type));
//    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type)
    {
        // To be safe, you should check that the SDCard is mounted
        
    	if(Environment.getExternalStorageState() != null) {
    		// this works for Android 2.2 and above
    		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "AndroidCameraTestsFolder");
            
            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.

            // Create the storage directory if it does not exist
            if (! mediaStorageDir.exists()) {
                if (! mediaStorageDir.mkdirs()) {
                    Log.d(TAG, "failed to create directory");
                    return null;
                }
            }

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
            if (type == MEDIA_TYPE_IMAGE){
                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
//            } else if(type == MEDIA_TYPE_VIDEO) {
//                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//                "VID_"+ timeStamp + ".mp4");
            } else {
                return null;
            }

            return mediaFile;
    	}
        
    	return null;
    }

    // grab the name of the media from the Uri
    protected String getName(Uri uri) 
	{
		String filename = null;

		try {
			String[] projection = { MediaStore.Images.Media.DISPLAY_NAME };
			Cursor cursor = managedQuery(uri, projection, null, null, null);

			if(cursor != null && cursor.moveToFirst()){
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
				filename = cursor.getString(column_index);
			} else {
				filename = null;
			}
		} catch (Exception e) {
			Log.e(TAG, "Error getting file name: " + e.getMessage());
		}

		return filename;
	}
}
