package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpUtil {
public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			HttpURLConnection conn=null;
			try {
				URL url=new URL(address);
				conn=(HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(8000);
				conn.setReadTimeout(8000);
				InputStream input=conn.getInputStream();
				BufferedReader read=new BufferedReader(new InputStreamReader(input));
				StringBuilder response=new StringBuilder();
				String line;
				while ((line=read.readLine())!=null) {
					Log.e("FirstActivity", line);
					response.append(line);
					
				}
				if (listener!=null) {
					listener.onFinish(response.toString());
				}
			} catch (Exception e) {
				// TODO: handle exception
				if (listener!=null) {
					listener.onError(e);
				}
			}finally{
				if (conn!=null) {
					conn.disconnect();
				}
			}
		}
	}).start();
	
}
public interface HttpCallbackListener{
	void onFinish(String response);
	void onError(Exception e);
}
}
