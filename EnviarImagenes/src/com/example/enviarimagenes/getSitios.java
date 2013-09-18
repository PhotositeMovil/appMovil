package com.example.enviarimagenes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class getSitios extends AsyncTask<String, Float, String> {
	static InputStream is = null;
	 static JSONObject jObj = null;
	 static String json = "", answer="";
	 static String url="http://ec2-54-232-227-187.sa-east-1.compute.amazonaws.com:8080/cpmtooldemo/api/site";
	 
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String contentType = "application/json;charset=UTF-8";
		HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httppost = new HttpGet(url);
		try{
			System.out.println("ALGP");
//			//UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
			
//		    entity.setContentEncoding(HTTP.UTF_8);
//		    entity.setContentType("application/json");
		   

		    httppost.setHeader("Content-Type", contentType);
		    httppost.setHeader("Accept", "application/json");			
			
	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        is = response.getEntity().getContent();           
           
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    	e.printStackTrace();
	    	Log.e("protocol exception", "Error converting result " + e.toString());
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    	e.printStackTrace();
	    	Log.e("IoException", "Error converting result " + e.toString());
	    }
	    try {
          BufferedReader reader = new BufferedReader(new InputStreamReader(
                is));
	     	//StringBuilder sb = new StringBuilder();
          StringBuffer sb= new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }   
	    	System.out.println("SBTRING "+sb.toString());			    	
            is.close();
            json = sb.toString();
        } catch (Exception e) {
        	e.printStackTrace();
            Log.e("Buffer Error", "Error converting result " + e.toString());
            
        }
		
		
		
		
		return null;
	}

	
	
	
	
	
}
