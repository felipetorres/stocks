package com.example.bolsadevalores.json;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class JSONCurrencyDeserializer implements JsonDeserializer<Map<String, Double>>{

	@Override
	public Map<String, Double> deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {

		Map<String, Double> result = new HashMap<String, Double>();
		
		JsonArray array = json.getAsJsonArray();
		
		for (JsonElement element : array) {
			JsonObject object = element.getAsJsonObject();
			
			for (Entry<String, JsonElement> entry : object.entrySet()) {
				String key = entry.getKey();
				double value = entry.getValue().getAsDouble();
				
				result.put(key, value);
			}
		}
		
		return result;
	}

}
