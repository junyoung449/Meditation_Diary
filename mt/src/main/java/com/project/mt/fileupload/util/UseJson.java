package com.project.mt.fileupload.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UseJson {

	public JSONObject createRequestBody(String[] imageFileNames){
		JSONObject body = new JSONObject();

		JSONArray images = new JSONArray(); // JSONArray로 변경

		for (int i = 0 ; i < imageFileNames.length ; i++) {
			images.add(imageFileNames[i]);
		}

		body.put("images", images);
		return body;
	}

	public Map<String, Object> callConversionApi(JSONObject body){
		String apiUrl = "http://43.202.44.111:8000/text";

		try {
			HttpURLConnection connection = null;
			URL url = new URL(apiUrl);

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
			connection.setDoOutput(true);

			// DATA
			OutputStreamWriter wr= new OutputStreamWriter(connection.getOutputStream());
			wr.write(body.toJSONString());
			wr.flush();

			// API 호출
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			StringBuffer sb = new StringBuffer();

			while((line = in.readLine()) != null){
				sb.append(line);
			}

			in.close();
			connection.disconnect();

			String text = sb.toString();

			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(text, Map.class);

			return map;
		} catch (ProtocolException e){
			// logger.warn(e.getLocalizedMessage());
		} catch (IOException e){
			// logger.warn(e.getLocalizedMessage());
		}

		return null;
	}

	private JSONObject parseStringToJson(String stringJson) throws ParseException {
		JSONParser parser = new JSONParser();
		Object obj  = parser.parse(stringJson);
		JSONObject json = (JSONObject) obj;
		return json;
	}

	private void readResponseCode(HttpURLConnection connection){

		try{
			int responseCode = connection.getResponseCode();
			if(responseCode == 400){
				System.out.println("400 : command error");
			} else if (responseCode == 500){
				System.out.println("500 : Server error");
			} else {
				System.out.println("response code : " + responseCode);
			}
		} catch (IOException e){
			// logger.warn("IOException : read response code error");
		}
	}
}
