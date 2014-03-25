package com.wsclientimpl.ksoap2;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.serialization.PropertyInfo;


import android.app.Activity;
import android.os.RemoteException;





public class AndroidWSClient extends Activity{
	
	
	 private static final String NAMESPACE = "http://mediaanalyzepackage.com";
	 private static String URL = "http://192.168.56.1:8085/AuriculoidAtToyotaServicesSriLanka/services/GetMediaFile?wsdl"; 
	 private static final String METHOD_NAME = "getMediaFileConfirmation";
	 private static final String SOAP_ACTION =  "http://mediaanalyzepackage/getMediaFileConfirmation";
	 private SoapPrimitive  resultsRequestSOAP;
	 private String TransportationFIle;
	 
	 long strattimetocreatewavfile;
	  long	lattimebyendofResponce;
	 
	 public String wsResponceOnTheScreen () throws RemoteException{
		
		  strattimetocreatewavfile=System.nanoTime();
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 

		  
		  PropertyInfo propInfo=new PropertyInfo();
		  propInfo.name="arg0";
		  propInfo.type=PropertyInfo.OBJECT_CLASS;
		  
		  request.addProperty(propInfo,gettranspotationFile());  

		  SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		 envelope.setOutputSoapObject(request);
		 HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		  try {
		   androidHttpTransport.call(SOAP_ACTION, envelope);
		  
		   resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
		  

		 
		  } 
		  
		  catch (Exception e) {
			  
			  e.printStackTrace();
		  }
		  
	
			
		return resultsRequestSOAP.toString();
		

	

		
	}
	
	
	public void settranspotationFile(String stringconvertedfile) {
		
		TransportationFIle=stringconvertedfile;
		
		//System.out.println("TransportationFIle");
		
	
		
	}
	
	
	public String gettranspotationFile() {
		
		lattimebyendofResponce=System.nanoTime();
		System.out.println("Time to get the Web Service Responce " + (lattimebyendofResponce-strattimetocreatewavfile)+" nanoSeconds");
		return TransportationFIle;
		
	}

}
