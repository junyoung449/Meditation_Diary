package com.project.mt.fileupload.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UseJson {

	public JSONObject createRequestBody(Long memberIdx, String modelId, String[] imageUrl){
		JSONObject body = new JSONObject();

		JSONArray images = new JSONArray(); // JSONArray로 변경

		for (int i = 0 ; i < imageUrl.length ; i++) {
			images.add(imageUrl[i]);
		}

		body.put("voice_id", modelId);
		body.put("images", images);

		return body;
	}

	public Map<String, List<String>> callConversionApi(JSONObject body){

		try {
			HttpURLConnection connection = null;
			URL url = new URL("https://j9b205.p.ssafy.io/ai/text");

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
			Map<String, List<String>> map = mapper.readValue(text, Map.class);

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
