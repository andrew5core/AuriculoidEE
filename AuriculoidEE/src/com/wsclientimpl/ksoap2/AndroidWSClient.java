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
	
	
	 private static final String NAMESPACE = "http://methods.enginetuneanalysis.com";
	 private static String URL = "http://192.168.43.200:8085/ToyotaLankaWebServices/services/GetGenuinityPercentage?wsdl"; 
	 private static final String METHOD_NAME = "getSoundSimPercentage";
	 private static final String SOAP_ACTION =  "http://methods.enginetuneanalysis/getSoundSimPercentage";
	 private SoapPrimitive  resultsRequestSOAP;
	 
	 private String TransportationFIle;
	 private String vehicleName;
	 private String vehicleManuYear;
	 private String Method;



	long strattimetocreatewavfile;
	 long	lattimebyendofResponce;
	 
	 public String wsResponceOnTheScreen () throws RemoteException{
		
		  strattimetocreatewavfile=System.nanoTime();
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME); 

		  
		  PropertyInfo propInfo1=new PropertyInfo();
		  propInfo1.name="arg1";
		  propInfo1.type=PropertyInfo.STRING_CLASS;

		  request.addProperty(propInfo1,getVehicleName());
		  
		  PropertyInfo propInfo2=new PropertyInfo();
		  propInfo2.name="arg2";
		  propInfo2.type=PropertyInfo.STRING_CLASS;  
		  
		  request.addProperty(propInfo2,getVehicleManuYear());
		  
		  PropertyInfo propInfo3=new PropertyInfo();
		  propInfo3.name="arg3";
		  propInfo3.type=PropertyInfo.STRING_CLASS; 	  
		  request.addProperty(propInfo3,gettranspotationFile());
		  

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
	
	 public String getVehicleName() {
		return vehicleName;
	}


	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}


	public String getVehicleManuYear() {
		return vehicleManuYear;
	}


	public void setVehicleManuYear(String vehicleManuYear) {
		this.vehicleManuYear = vehicleManuYear;
	}


	 public String getMethod() {
		return Method;
	}


	public void setMethod(String method) {
		Method = method;
	}
	

}
