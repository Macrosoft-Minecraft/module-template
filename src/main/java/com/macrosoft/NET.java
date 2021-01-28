package com.macrosoft;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author Federal
 * This class makes POST and GET requests : )
 *
 */
public class NET {

	//It uses Application/x-www-form-urlencoded format on post data
		public static Map<String, String> request(String method, String targetURL, String data, String headers, int timeout) {
			HttpURLConnection connection = null;
			String headersList[] = headers.split(",");
			Map<String, String> responseTuple = new HashMap<String, String>();
			try {
				//Create connection
				URL url = new URL(targetURL);
				connection = (HttpURLConnection) url.openConnection();
				connection.setUseCaches(false);
				connection.setDoOutput(true);
				connection.setRequestMethod(method);
				connection.setConnectTimeout(timeout);
				for (String header: headersList) {
					String headerSnippets[] = header.split(":");
					try {
						connection.setRequestProperty(headerSnippets[0].trim(), headerSnippets[1].trim().replace('+',';'));
						System.out.println("Header "+headerSnippets[0].trim()+" : "+headerSnippets[1].trim().replace('+',';')+" added");
					} catch (Exception e) {
						System.out.println("Header "+header+" ignored");
					}
				}
				//connection.setRequestProperty("Content-Language", "en-US");
				//connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				if (method.toUpperCase().equals("POST".toUpperCase())) {
					connection.setRequestProperty("Content-Length",
						Integer.toString(data.getBytes().length));
					//Append data
					connection.getOutputStream().write(data.getBytes("UTF-8"));
					/*try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
						wr.writeBytes(data);
						wr.flush();
						wr.close();
					}*/
				}

				//Get Response  
				InputStream is = connection.getInputStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is));
				StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
				String line;
				while ((line = rd.readLine()) != null) {
					response.append(line);
					response.append('\r');
				}
				rd.close();
				responseTuple.put("data",response.toString());
				responseTuple.put("status_code",String.valueOf(connection.getResponseCode()));
				return responseTuple;
			} catch (Exception e) {
				e.printStackTrace();
				responseTuple.put("data","");
				responseTuple.put("status_code","-1");
				return responseTuple;
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
		}
	
}
