package com.dff.cordova.plugin.crashreport.json.model;

import org.json.JSONArray;
import org.json.JSONException;

public class JsonStacktrace {
	public static JSONArray toJson(StackTraceElement[] stackTrace) throws JSONException {
		JSONArray jsonStackTrace = new JSONArray();
		
		if (stackTrace != null) {
			for (StackTraceElement ste : stackTrace) {
				jsonStackTrace.put(ste.toString());
			}
		}
		
		return jsonStackTrace;
		
	}
}
