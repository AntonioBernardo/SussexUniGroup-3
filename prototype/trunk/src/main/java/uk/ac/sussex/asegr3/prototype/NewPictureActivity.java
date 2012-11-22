package uk.ac.sussex.asegr3.prototype;





	import java.io.File;
	import java.text.SimpleDateFormat;
	import java.util.Date;

	import android.app.Activity;
	import android.content.ContentValues;
	import android.content.Intent;
	import android.database.Cursor;
	import android.net.Uri;
	import android.os.Bundle;
	import android.os.Environment;
	import android.provider.MediaStore;
	import android.util.Log;
	import android.view.View;

		
	public class NewPictureActivity extends Activity{
	
		private static final String TAG = NewPictureActivity.class.getSimpleName(); 
		
		private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
		private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
		public static final int MEDIA_TYPE_IMAGE = 1;
	    public static final int MEDIA_TYPE_VIDEO = 2;

	    private Uri fileUri;
	    
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_new_picture);
	    }

	    /** 
	     * https://developer.android.com/guide/topics/media/camera.html 
	     * **/
	    public void onCaptureImage(View v) 
	    {
	        // give the image a name so we can store it in the phone's default location
	    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    	
	        ContentValues values = new ContentValues();
			values.put(MediaStore.Images.Media.TITLE, "IMG_" + timeStamp + ".jpg");

	        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        
	        //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image (this doesn't work at all for images)
	        fileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); // store content values
			intent.putExtra( MediaStore.EXTRA_OUTPUT,  fileUri);
	       
	        // start the image capture Intent
	        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	    }
	    
	    /** 
	     * https://developer.android.com/guide/topics/media/camera.html 
	     * **/
	    public void onCaptureVideo(View v) 
	    {
	    	 //create new Intent
	        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

	        //fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video in specific folder (this works for video only)
	        //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name
	      
	        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high

	        // start the Video Capture Intent
	        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
	    }
	    
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	    {
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
					}
	                
	            } else if (resultCode == RESULT_CANCELED) {
	                finish();
	            } else {
	                finish();
	            }
	        }

	        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
	            if (resultCode == RESULT_OK) {
	            	
	                // Video captured and saved to fileUri specified in the Intent
	            	fileUri = (Uri) data.getData();
					
					if(fileUri != null) {
						Log.d(TAG, "Video saved to:\n" + fileUri);
						Log.d(TAG, "Video path:\n" + fileUri.getPath());
						Log.d(TAG, "Video name:\n" + getName(fileUri)); // use uri.getLastPathSegment() if store in folder
					}
					
	            } else if (resultCode == RESULT_CANCELED) {
	                // User cancelled the video capture
	            } else {
	                // Video capture failed, advise user
	            }
	        }
	    }
	    
	    /** Create a file Uri for saving an image or video to specific folder
	     * https://developer.android.com/guide/topics/media/camera.html#saving-media
	     * */
	    private static Uri getOutputMediaFileUri(int type)
	    {
	          return Uri.fromFile(getOutputMediaFile(type));
	    }

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
	            } else if(type == MEDIA_TYPE_VIDEO) {
	                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	                "VID_"+ timeStamp + ".mp4");
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
	


