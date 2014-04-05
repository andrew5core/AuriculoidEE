package com.engineexpert.auriculoidee;


import com.recordfunction.wavfilecreation.AudioWaveRecorder;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.RemoteException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableLayout.LayoutParams;

import com.wsclientimpl.ksoap2.AndroidWSClient;
import com.wsclientimpl.ksoap2.FingerprintConversion;

public class MainActivity extends Activity {

	ImageButton capture, stop, webCheck;
	TextView actionShownText, webServiceCheckText, value;
	String RecordStatus;
	AudioWaveRecorder AWRWAV;
	TableLayout tl;
	CountDownTimer ct;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		createTableTimeElpased(null);
		//
		
		
		
		capture=(ImageButton) findViewById(R.id.recordButton);
		stop=(ImageButton) findViewById(R.id.stopButton);
		//webCheck=(ImageButton) findViewById(R.id.wsCheckbutton);
		//actionShownText = (TextView) findViewById(R.id.actionShownText);
		//webServiceCheckText = (TextView) findViewById(R.id.webServiceCheck);	
		
		
		capture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				
				if(RecordStatus!=null){
					
					AWRWAV.endRecording();
					ct.cancel();
					tl.removeAllViews();
					createTableTimeElpased(null);
	
				}
				
				else{
					
				AWRWAV=new AudioWaveRecorder();	
				AWRWAV.beginRecording();
				RecordStatus="Record Started";
				tl.removeAllViews();
				createTableTimeElpased("Recording");
				

		        	 try {
		        		int mil=1500;
		        		
						Thread.sleep(mil);

					} 
		        	 catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


			 
		        ct =new CountDownTimer(10000, 1000) { // adjust the milli seconds here
		            public void onTick(long millisUntilFinished) {
		            	value.setText("Seconds 0" + millisUntilFinished / 1000);        	
		            
		            }

		            public void onFinish() {
		            	
		            	value.setText("Ready to Analyze");
		            	if(RecordStatus!=null){
		            	AWRWAV.endRecording();
		            	}
						RecordStatus=null;
		            }
		         }.start();

				}
				
				//((ViewManager)entry.getParent()).removeView(entry);
				//createTableTimeElpased("Recording");

			}
			
			
			
		});
		
		
		stop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				//actionShownText.setText("Recording Stoppped by the User");
				  Intent intent = getIntent();
				  overridePendingTransition(0, 0);
				  intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				  finish();

				  overridePendingTransition(0, 0);
				 startActivity(intent);

			}
		});
		
		/*webCheck.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AndroidWSClient AWSC =new AndroidWSClient();
				File userFile = new File(Environment.getExternalStorageDirectory() + "/" + "Genuine_Cature_Car_2nd.wav");
				
				if (userFile.exists()) {
					
	  				byte[] bytes;
	  				
					try {
						bytes = FileUtils.readFileToByteArray(userFile);
						String encoded = Base64.encodeToString(bytes, 0);
						AWSC.settranspotationFile(encoded);
				
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

	  				try {
	  					webServiceCheckText.setText(AWSC.wsResponceOnTheScreen().toString());
	  				} catch (RemoteException e) {
	  					// TODO Auto-generated catch block
	  					e.printStackTrace();

	  		}
						
	}
			}
		});	*/
		
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void createTableSoundAnalysis(){
		
		
		
		TableLayout tl = (TableLayout) findViewById(R.id.tableLayoutforAnalysisResults);
		
		TableRow tr_head = new TableRow(this);
		
		tr_head.setId(10);
		//tr_head.setBackgroundColor(Color.GRAY);
		tr_head.setGravity(Gravity.CENTER_HORIZONTAL);
		tr_head.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		TextView Header = new TextView(this);
		Header.setId(20);
		Header.setTextSize(18);
		Header.setText("Analysed Test Results");
		Header.setTextColor(Color.LTGRAY);
		Header.setTypeface(null, Typeface.BOLD);
		Header.setPadding(5, 5, 5, 5);
		tr_head.addView(Header);
		
        tl.addView(tr_head, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        int i=1;
        
        
        
        while(i<4){
        	
            TableRow sep_tr_row = new TableRow(this);
            TextView sep_rowText = new TextView(this);
            
            //tr_row1.setId(11);
            sep_tr_row.setBackgroundColor(Color.BLACK);
            sep_tr_row.setGravity(Gravity.LEFT);
            sep_tr_row.setLayoutParams(new LayoutParams(
    				LayoutParams.MATCH_PARENT,
    				LayoutParams.WRAP_CONTENT));
    		
    		//TextView rowText = new TextView(this);
    		//row1Text.setId(21);
            sep_rowText.setTextSize(15);
            sep_rowText.setText("Sorted Issue No: "+ i);
            sep_rowText.setTextColor(Color.WHITE);
            sep_rowText.setTypeface(null, Typeface.BOLD);
            sep_rowText.setPadding(5, 5, 5, 5);
            sep_tr_row.addView(sep_rowText);
    		
            tl.addView(sep_tr_row, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));	
        	
        TableRow tr_row = new TableRow(this);
        TextView rowText = new TextView(this);
        
        //tr_row1.setId(11);
        tr_row.setBackgroundColor(Color.GRAY);
        tr_row.setGravity(Gravity.LEFT);
        tr_row.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		//TextView rowText = new TextView(this);
		//row1Text.setId(21);
        rowText.setTextSize(14);
        rowText.setText("Similarity Value");
        rowText.setTextColor(Color.BLACK);
        rowText.setTypeface(null, Typeface.BOLD);
        rowText.setPadding(5, 5, 5, 5);
        tr_row.addView(rowText);
		
        tl.addView(tr_row, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT)); 
        
//
        TableRow de_tr_row = new TableRow(this);
        TextView de_rowText = new TextView(this);
        
        //tr_row1.setId(11);

        de_tr_row.setGravity(Gravity.LEFT);
        de_tr_row.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		//TextView rowText = new TextView(this);
		//row1Text.setId(21);
        de_rowText.setTextSize(12);
        de_rowText.setText("Similarity is 100%");
        de_rowText.setTextColor(Color.BLACK);
        de_rowText.setTypeface(null, Typeface.BOLD_ITALIC);
        de_rowText.setPadding(5, 5, 5, 5);
        de_tr_row.addView(de_rowText);
		
        tl.addView(de_tr_row, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT)); 

//	
       
       	TableRow tr_row1 = new TableRow(this);
       	TextView rowText1 = new TextView(this);
        //tr_row2.setId(12);
       	tr_row1.setBackgroundColor(Color.GRAY);
       	tr_row1.setGravity(Gravity.LEFT);
       	tr_row1.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
        
        //TextView row2Text = new TextView(this);
        //row2Text.setId(22);
       	rowText1.setTextSize(14);
       	rowText1.setText("Possible Issue Analyzed");
       	rowText1.setTextColor(Color.BLACK);
       	rowText1.setTypeface(null, Typeface.BOLD);
       	rowText1.setPadding(5, 5, 5, 5);
        tr_row1.addView(rowText1);
		
        tl.addView(tr_row1, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));  
        
 //	
     
        TableRow de_tr_row1 = new TableRow(this);
        TextView de_rowText1 = new TextView(this);
        
        //tr_row1.setId(11);

        de_tr_row1.setGravity(Gravity.LEFT);
        de_tr_row1.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		//TextView rowText = new TextView(this);
		//row1Text.setId(21);
        de_rowText1.setTextSize(12);
        de_rowText1.setText("No Possible Issue Currently Available");
        de_rowText1.setTextColor(Color.BLACK);
        de_rowText1.setTypeface(null, Typeface.BOLD_ITALIC);
        de_rowText1.setPadding(5, 5, 5, 5);
        de_tr_row1.addView(de_rowText1);
		
        tl.addView(de_tr_row1, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT)); 
//
        
       	TableRow tr_row2 = new TableRow(this);	
       	TextView rowText2 = new TextView(this);
       	
       	tr_row2.setBackgroundColor(Color.GRAY);
       	tr_row2.setGravity(Gravity.LEFT);
       	tr_row2.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
        

        rowText2.setTextSize(14);
        rowText2.setText("Available Diagnosis");
        rowText2.setTextColor(Color.BLACK);
        rowText2.setTypeface(null, Typeface.BOLD);
        rowText2.setPadding(5, 5, 5, 5);
        tr_row2.addView(rowText2);
		
        tl.addView(tr_row2, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));  
        
        //	
        
        TableRow de_tr_row2 = new TableRow(this);
        TextView de_rowText2 = new TextView(this);
        
        //tr_row1.setId(11);

        de_tr_row2.setGravity(Gravity.LEFT);
        de_tr_row2.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		//TextView rowText = new TextView(this);
		//row1Text.setId(21);
        de_rowText2.setTextSize(12);
        de_rowText2.setText("No Possible Issue Currently Available");
        de_rowText2.setTextColor(Color.BLACK);
        de_rowText2.setTypeface(null, Typeface.BOLD_ITALIC);
        de_rowText2.setPadding(5, 5, 5, 5);
        de_tr_row2.addView(de_rowText2);
		
        tl.addView(de_tr_row2, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT)); 
//
        
    	TableRow tr_row3 = new TableRow(this);	
       	TextView rowText3 = new TextView(this);
       	
       	tr_row3.setBackgroundColor(Color.GRAY);
       	tr_row3.setGravity(Gravity.LEFT);
       	tr_row3.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

       	rowText3.setTextSize(14);
       	rowText3.setText("Criticality");
       	rowText3.setTextColor(Color.BLACK);
       	rowText3.setTypeface(null, Typeface.BOLD);
       	rowText3.setPadding(5, 5, 5, 5);
       	tr_row3.addView(rowText3);
		
        tl.addView(tr_row3, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        
        //	
        
        TableRow de_tr_row3 = new TableRow(this);
        TextView de_rowText3 = new TextView(this);
        
        //tr_row1.setId(11);

        de_tr_row3.setGravity(Gravity.LEFT);
        de_tr_row3.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		//TextView rowText = new TextView(this);
		//row1Text.setId(21);
        de_rowText3.setTextSize(12);
        de_rowText3.setText("No Possible Issue Currently Available");
        de_rowText3.setTextColor(Color.BLACK);
        de_rowText3.setTypeface(null, Typeface.BOLD_ITALIC);
        de_rowText3.setPadding(5, 5, 5, 5);
        de_tr_row3.addView(de_rowText3);
		
        tl.addView(de_tr_row3, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT)); 
//
        
        
        i++;
        
         }
           
		ScrollView l1 = (ScrollView) findViewById(R.id.scrolllayoutresults);
		
		l1.setVisibility(View.VISIBLE);
		
		
		
		
	}
	
	public void createTableSoundMatch(){

		TableLayout tl = (TableLayout) findViewById(R.id.genmatchresults);
		
		TableRow tr_head = new TableRow(this);
		
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.RED);
		tr_head.setGravity(Gravity.CENTER_HORIZONTAL);
		tr_head.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		TextView Header = new TextView(this);
		Header.setId(20);
		Header.setTextSize(18);
		Header.setGravity(Gravity.CENTER_HORIZONTAL);
		Header.setText("Sound Genuinity%");
		Header.setTextColor(Color.LTGRAY);
		Header.setTypeface(null, Typeface.BOLD);
		Header.setPadding(5, 5, 5, 5);
		tr_head.addView(Header);
		
        tl.addView(tr_head, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        
		TableRow tr_row = new TableRow(this);
		
		tr_row.setId(11);
		//tr_row.setBackgroundColor(Color.RED);
		tr_row.setGravity(Gravity.CENTER_HORIZONTAL);
		tr_row.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		value = new TextView(this);
		value.setId(21);
		value.setTextSize(40);
		value.setGravity(Gravity.CENTER_HORIZONTAL);
		value.setText("80.03335");
		value.setTextColor(Color.LTGRAY);
		value.setTypeface(null, Typeface.BOLD);
		value.setPadding(5, 5, 5, 5);
		tr_row.addView(value);
		
        tl.addView(tr_row, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
		
		
        TableRow tr_row_button = new TableRow(this);
        
        tr_row_button.setId(11);
		//tr_row.setBackgroundColor(Color.RED);
        tr_row_button.setGravity(Gravity.CENTER_HORIZONTAL);
        tr_row_button.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
        
        Button btn = new Button(this);
        btn.setText("Analyze Possible Issues");
        btn.setId(100);
        
        
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               
            	System.out.println("Button Pressed");
            }
        }
    );
        
        
        
        tr_row_button.addView(btn);
        
        tl.addView(tr_row_button, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

		
        tl.setVisibility(View.VISIBLE);	
		

		
	}
	
	
	public void createTableTimeElpased(String passedvalue){

		tl = (TableLayout) findViewById(R.id.genmatchresults);
		
		TableRow tr_head = new TableRow(this);
		
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.BLACK);
		tr_head.setGravity(Gravity.CENTER_HORIZONTAL);
		tr_head.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		TextView Header = new TextView(this);
		Header.setId(20);
		Header.setTextSize(18);
		Header.setGravity(Gravity.CENTER_HORIZONTAL);
		if(passedvalue!= null){
			
			Header.setText("Recording Started");
		}else{
			Header.setText("Recording Not Initiated");	
		}

		Header.setTextColor(Color.LTGRAY);
		Header.setTypeface(null, Typeface.BOLD);
		Header.setPadding(5, 5, 5, 5);
		tr_head.addView(Header);
		
        tl.addView(tr_head, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        
		TableRow tr_row = new TableRow(this);
		
		tr_row.setId(11);
		//tr_row.setBackgroundColor(Color.RED);
		tr_row.setGravity(Gravity.CENTER_HORIZONTAL);
		tr_row.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		value = new TextView(this);
		value.setId(21);
		value.setTextSize(30);
		value.setGravity(Gravity.CENTER_HORIZONTAL);
		value.setTextColor(Color.LTGRAY);
		value.setTypeface(null, Typeface.BOLD);
		value.setPadding(5, 5, 5, 5);
		
		if(passedvalue!= null){
			
		value.setText("Recording Started");
		tr_row.addView(value);
        tl.addView(tr_row, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
		
		}
		else
		{
			
		value.setText("Press Rec to Start");
		tr_row.addView(value);
        tl.addView(tr_row, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
		}



        TableRow tr_row_button = new TableRow(this);
        
        tr_row_button.setId(12);
		//tr_row.setBackgroundColor(Color.RED);
        tr_row_button.setGravity(Gravity.CENTER_HORIZONTAL);
        tr_row_button.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
        
    	if(passedvalue!= null){
    		
    		
        Button btn = new Button(this);
        btn.setText("Analyze Possible Issues");
        btn.setId(22);
        
        
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               
            	System.out.println("Button Pressed");
            	tl.removeAllViews();
            	createTableSoundMatch();
            	
            	FingerprintConversion FPV=new FingerprintConversion();
            	String fileexsits=FPV.FingerprintConversionOnline();
 
            	
  				try {
  					
  					AndroidWSClient AWSC=new AndroidWSClient();
  					AWSC.setMethod("SoundMatch");
  					AWSC.setVehicleName("Allion 1.6");
  					AWSC.setVehicleManuYear("2010-2012");
  					AWSC.settranspotationFile(fileexsits);
  					
  					String responce=AWSC.wsResponceOnTheScreen();
  					System.out.println("Your Responce " + responce);
  					
  				}
  				 catch (RemoteException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();

  		}
            	
            
            	
            }
        }
    );
        

        tr_row_button.addView(btn);
        tl.addView(tr_row_button, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
    	}
    	
    	else{
    		
    		TextView Instructions1 = new TextView(this);
    		Instructions1.setId(21);
    		Instructions1.setTextSize(12);
    		Instructions1.setGravity(Gravity.LEFT);
    		Instructions1.setTextColor(Color.LTGRAY);
    		Instructions1.setText("1). Pressing Stop will elimenate the Recording. \n2). Refreshing will Roll-Over the Process. \n3). After '05' Seconds you Continue to Analyze");
    		Instructions1.setTypeface(null, Typeface.NORMAL);
    		Instructions1.setPadding(5, 5, 5, 5);
    		tr_row_button.addView(Instructions1);
            tl.addView(tr_row_button, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
    		

    		
    	}

        

       /* TableRow tr_row_progressbar = new TableRow(this);
        
        tr_row_progressbar.setId(13);
		//tr_row.setBackgroundColor(Color.RED);
        tr_row_progressbar.setGravity(Gravity.CENTER_HORIZONTAL);
        tr_row_progressbar.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

        ProgressBar prgbar = new ProgressBar(this);
        prgbar.setId(23);
        prgbar.setIndeterminate(true);
        prgbar.setVisibility(View.VISIBLE);
        
        tr_row_progressbar.addView(prgbar);
        
        tl.addView(tr_row_progressbar, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));*/
		
        tl.setVisibility(View.VISIBLE);	
		


		
	}
		
	
	
}
