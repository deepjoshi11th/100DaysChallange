package rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RestMan {
	public String shortURl(String longURL) {
		try {
			URL url = new URL("https://firebasedynamiclinks.googleapis.com/v1/shortLinks?key=AIzaSyByMiAQFjJJR9lRDyGCMImTGYkjNTc4z2Y");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String input = "{\"longDynamicLink\":\""+longURL+"\"}";
			conn.getOutputStream().write(input.getBytes());
			conn.getOutputStream().flush();
			if(conn.getResponseCode()!=200) {
				throw (new Exception());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String str = "Not Working";
			while((str=br.readLine())!=null) {
				if(str.contains("shortLink")) {
					return str.substring(str.indexOf("shortLink")+13, str.length()-1);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	@SuppressWarnings("deprecation")
	public void tweet(String message) {
		try {
			URL url = new URL("https://api.twitter.com/1.1/statuses/update.json?status="+URLEncoder.encode(message).replace("+","%20"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("authorization", "Consumer_key");
			conn.setRequestProperty("Content-Type", "application/json");
			//Ooath 1.0 needs to be replaced here

			if(conn.getResponseCode()!=200) {
				throw (new Exception());
			} else {
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}