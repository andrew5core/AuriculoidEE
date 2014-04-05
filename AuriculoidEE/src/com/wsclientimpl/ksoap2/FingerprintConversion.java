package com.wsclientimpl.ksoap2;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import android.os.Environment;
import android.util.Base64;

import com.musicg.fingerprint.FingerprintManager;
import com.musicg.wave.Wave;


public class FingerprintConversion {
	
	
String encoded;	
byte[] bytes;
	
	public String FingerprintConversionOnline(){
	
    File userFile = new File(Environment.getExternalStorageDirectory() + "/" + "record_temp.wav");
    
    if (userFile.exists()) {
    		


		// create a wave object
		Wave wave = new Wave(Environment.getExternalStorageDirectory() + "/" + "record_temp.wav");

		// get the fingerprint
		byte[] fingerprint=wave.getFingerprint();

		// dump the fingerprint
		FingerprintManager fingerprintManager=new FingerprintManager();
		fingerprintManager.saveFingerprintAsFile(fingerprint, Environment.getExternalStorageDirectory() + "/" +"record_temp"+".fingerprint");
		
		// load fingerprint from file
		bytes=fingerprintManager.getFingerprintFromFile(Environment.getExternalStorageDirectory() + "/" +"record_temp"+".fingerprint");
		File fileToBeString = new File(Environment.getExternalStorageDirectory() + "/" +"record_temp"+".fingerprint");
		
		if(bytes==null){
			
			System.out.println("file is null Error");
		}
		
		// fingerprint bytes checking
		for (int i=0; i<fingerprint.length; i++){
			System.out.println(fingerprint[i]+" vs "+bytes[i]);
		}
		
		try {
			
			bytes = FileUtils.readFileToByteArray(fileToBeString);
			encoded = Base64.encodeToString(bytes, 0);

	
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    }	

    
	return encoded;
	}
}
