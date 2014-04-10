package com.recordfunction.wavfilecreation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;



public class AudioWaveRecorder extends Activity{
	
	  private static final int WAV_HEADER_LENGTH = 44;
	  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	  private static final int NOTICE_RECORD = 0;
	  

	  private Button actionButton;
	  private ImageButton newTimestamp;
	  private EditText editText;
	  private String filename;
	  String filenamefingerprint;
	  private ProgressBar saving;
	  private Spinner spinner;
	  private View startedRecording;
	  private TextView startedRecordingTime;
	  private AudioRecord recordInstance;
	  
	  private AlertDialog dialog;

	  private File outFile;

	  private boolean isListening;
	
	  public int sampleRate = 44100;
	

	
	  
	  public void endRecording() {
		  
		    isListening = false;
		    Thread thread = new Thread() {
		      @Override
		      public void run() {

		        if (outFile != null) {
		          appendHeader(outFile);
		  
		          outFile = null;
		          
		        }
		      }
		    };
		    thread.start();
		    
		   
	}
		      
	  

	  
	  private class SpaceCheck implements Runnable {
		    @Override
		    public void run() {
		      String sdDirectory = Environment.getExternalStorageDirectory().toString();
		      StatFs stats = new StatFs(sdDirectory);
		      while (isListening) {
		        stats.restat(sdDirectory);
		        final long freeBytes = (long) stats.getAvailableBlocks() * (long) stats.getBlockSize();
		        if (freeBytes < 5242880) { // less than 5MB remaining
		          runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		              showDialog("Low on disk space", "There isn't enough space " + "left on your SD card (" + freeBytes
		                 + "b) , but what you've " + "recorded up to now has been saved.");
		              actionButton.performClick();
		            	
		            	System.out.println("No enough memo in ur card");
		            }
		          });
		          return;
		        }

		        try {
		          Thread.sleep(3000);
		          
		        } catch (InterruptedException e) {
		        }
		      }
		    }
		  }
	
	  public void beginRecording() {
		  


		    // check that there's somewhere to record to
		    String state = Environment.getExternalStorageState();	
		    System.out.println(Environment.getDataDirectory());
		    
		    Log.d("FS State", state);
		    if (state.equals(Environment.MEDIA_SHARED)) {
		      showDialog("Unmount USB storage", "Please unmount USB storage before starting to record.");
		    System.out.println("USB is connected plaese remove");
            	System.out.println("Please unmount USB storage before starting to record.");
		      return;
		    } else if (state.equals(Environment.MEDIA_REMOVED)) {
		      showDialog("Insert SD Card", "Please insert an SD card. You need something to record onto.");
		  	System.out.println("Please insert an SD card. You need something to record onto.");
		      return;
		    }

		    // check that the user's supplied a file name
		    filename= "record_temp.wav";
		    filenamefingerprint = "record_temp.fingerprint";


		  
		    	startRecording();	

		    
		  }
	  
	  
	  @Override
	  public void onDestroy() {
	    super.onDestroy();
	    isListening = false;
	   
	   
	  }

	  public void startRecording() {
		  

		    isListening = true;
		    Thread s = new Thread(new SpaceCheck());
		    s.start();
		    Thread t = new Thread(new Capture());
		    t.start();
	    
  
	  }

	   
	  private class Capture implements Runnable {

		    private final int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
		    private final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

		    @Override
		    public void run() {
		      // We're important...
		     android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

		      // Allocate Recorder and Start Recording...
		      int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioEncoding);
		      if (AudioRecord.ERROR_BAD_VALUE == minBufferSize || AudioRecord.ERROR == minBufferSize){
		        runOnUiThread(new Runnable() {
		          @Override
		          public void run() {
		            showDialog("Error recording audio", "Your audio hardware doesn't support the sampling rate you have specified." +
		              "Try a lower sampling rate, if that doesn't work your audio hardware might be broken.");
		            
		            System.out.println("Error recording audio Your audio hardware doesn't support the sampling rate you have specified.");
		            actionButton.performClick();
		          }
		        });
		        return;
		      }
		      int bufferSize = 2 * minBufferSize;
		      

		      
		       recordInstance =
		          new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioEncoding,
		              bufferSize);
		      
		      if (recordInstance.getState() != AudioRecord.STATE_INITIALIZED) {
		        runOnUiThread(new Runnable() {
		          @Override
		          public void run() {
		            showDialog("Error recording audio", "Unable to access the audio recording hardware - is your mic working?");
		            actionButton.performClick();
		            
		            System.out.println("Unable to access the audio recording hardware - is your mic working?");
					
		          }
		        });
		        

		        return;
		      }

		      byte[] tempBuffer = new byte[bufferSize];

		      String sdDirectory = Environment.getExternalStorageDirectory().toString();
		      outFile = new File(sdDirectory + "/" + filename);
		      if (outFile.exists())
		        outFile.delete();
		  
		      

		      FileOutputStream outStream = null;
		      try {
		        outFile.createNewFile();
		        outStream = new FileOutputStream(outFile);
		        outStream.write(createHeader(0));// Write a dummy header for a file of length 0 to get updated later
		      } catch (Exception e) {
		        runOnUiThread(new Runnable() {
		          @Override
		          public void run() {
		            showDialog("Error creating file", "The WAV file you specified "
		                + "couldn't be created. Try again with a " + "different filename.");
		            outFile = null;
		            System.out.println("Error Creating the wave file you specified");
		            //actionButton.performClick();
		          }
		        });
		        return;
		      }

		      recordInstance.startRecording();

		      try {
		        while (isListening) {
		          recordInstance.read(tempBuffer, 0, bufferSize);
		          outStream.write(tempBuffer);
		          System.out.println("Recording success 1");
		        }
		      } catch (final IOException e) {
		        runOnUiThread(new Runnable() {

		          @Override
		          public void run() {
		            showDialog("IO Exception", "An exception occured when writing to disk or reading from the microphone\n"
		                    + e.getLocalizedMessage()
		                    + "\nWhat you have recorded so far should be saved to disk.");
		            //actionButton.performClick();
		            System.out.println("An exception occured when writing to disk or reading from the microphone\n");
		            
		          }

		        });
		      } catch (OutOfMemoryError om) {
		        runOnUiThread(new Runnable() {
		          @Override
		          public void run() {
		            showDialog("Out of memory", "The system has been " + "too strong for too long - but what you "
		                + "recorded up to now has been saved.");
		            System.gc();
		            //actionButton.performClick();
		            
		            System.out.println("The system has been " + "too strong for too long - but what you ");
		          }
		        });
		      }

		      // we're done recording
		      Log.d("Capture", "Stopping recording");
		      recordInstance.stop();
		      
		      try {
		        outStream.close();
		        
		      } catch (Exception e) {
		        e.printStackTrace();
		       System.out.println("Exception from the OutputstreaClose"); 
		        
		      }
		    }
		  }

		  private void showDialog(String title, String message){
		    dialog.setTitle(title);
		    dialog.setMessage(message);
		    dialog.show();
		  }	
	
	public void appendHeader(File file) {

			    int bytesLength = (int) file.length();
			    byte[] header = createHeader(bytesLength - WAV_HEADER_LENGTH);

			    try {
			      RandomAccessFile ramFile = new RandomAccessFile(file, "rw");
			      ramFile.seek(0);
			      ramFile.write(header);
			      ramFile.close();
			    } catch (FileNotFoundException e) {
			      Log.e("Auriculoid", "Tried to append header to invalid file: " + e.getLocalizedMessage());
			      return;
			    } catch (IOException e) {
			      Log.e("Auriculoid", "IO Error during header append: " + e.getLocalizedMessage());
			      return;
			    }

			  }
	
	
	
	public byte[] createHeader(int bytesLength) {

	    int totalLength = bytesLength + 4 + 24 + 8;
	    byte[] lengthData = intToBytes(totalLength);
	    byte[] samplesLength = intToBytes(bytesLength);
	    byte[] sampleRateBytes = intToBytes(this.sampleRate);
	    byte[] bytesPerSecond = intToBytes(this.sampleRate * 2);

	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {
	      out.write(new byte[] {'R', 'I', 'F', 'F'});
	      out.write(lengthData);
	      out.write(new byte[] {'W', 'A', 'V', 'E'});

	      out.write(new byte[] {'f', 'm', 't', ' '});
	      out.write(new byte[] {0x10, 0x00, 0x00, 0x00}); // 16 bit chunks
	      out.write(new byte[] {0x01, 0x00, 0x01, 0x00}); // mono
	      out.write(sampleRateBytes); // sampling rate
	      out.write(bytesPerSecond); // bytes per second
	      out.write(new byte[] {0x02, 0x00, 0x10, 0x00}); // 2 bytes per sample

	      out.write(new byte[] {'d', 'a', 't', 'a'});
	      out.write(samplesLength);
	    } catch (IOException e) {
	      Log.e("Create WAV", e.getMessage());
	    }

	    return out.toByteArray();
	  }	
	
	
	  public static byte[] intToBytes(int in) {
		    byte[] bytes = new byte[4];
		    for (int i = 0; i < 4; i++) {
		      bytes[i] = (byte) ((in >>> i * 8) & 0xFF);
		    }
		    return bytes;
		  }
			
	


}
