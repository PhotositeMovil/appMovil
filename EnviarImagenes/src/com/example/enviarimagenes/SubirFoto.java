package com.example.enviarimagenes;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Environment;

public class SubirFoto extends AsyncTask<String, Float, String>{
	URL url;
	HttpURLConnection httpUrlConnection = null;
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		UploadImages();
		return null;
	}

	
	public  void  UploadImages() {
		
		try {
			//URL en la AWS: Ojo con los parametros, en especial el site.id que es el que asigna la imagen al proyecto
			url = new URL("http://ec2-54-232-227-187.sa-east-1.compute.amazonaws.com:8080/cpmtooldemo/imageResource/create?path=1&url=2&reference=1");
			//url = new URL("http://localhost:8080/cpmtooldemo/imageResource/create?path=1&url=2&id=3&reference=1&site.id=1");
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			String attachmentName = "image";
			String attachmentFileName = "image.jpg";
			String crlf = "\r\n";
			String twoHyphens = "--";
			String boundary = "*****";

			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setDoOutput(true);

			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
			httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
			httpUrlConnection.setRequestProperty("max_allowed_packet","4918708");
			httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			

			DataOutputStream request = new DataOutputStream(
					httpUrlConnection.getOutputStream());

			request.writeBytes(twoHyphens + boundary + crlf);
			request.writeBytes("Content-Disposition: form-data; name=\""+ attachmentName + "\";filename=\"" + attachmentFileName
					+ "\"" + crlf);
			request.writeBytes(crlf);
			File filedir=new File(Environment.getExternalStorageDirectory()+"/PHOTOSITE/prueba/OTRAS");
			File[] list=filedir.listFiles();
			File file=list[0];
			//Bloque que carga la imagen, cambia la ruta para que pruebes. Debe cambiarse este bloque con la imagen obtenida por el telefono
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int ch;
			while((ch = fis.read()) != -1){
				baos.write(ch);
			}
			request.write(baos.toByteArray());
			//------------------------------
			
			request.writeBytes(crlf);
			request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);

			request.flush();
			request.close();

			InputStream responseStream = new BufferedInputStream(
					httpUrlConnection.getInputStream());

			BufferedReader responseStreamReader = new BufferedReader(
					new InputStreamReader(responseStream));
			String line = "";
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = responseStreamReader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			responseStreamReader.close();

			String response = stringBuilder.toString();
			System.out.println("response: " + response);
			responseStream.close();
			httpUrlConnection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpUrlConnection != null) {
				httpUrlConnection.disconnect();
			}
		}
	}
	
}
