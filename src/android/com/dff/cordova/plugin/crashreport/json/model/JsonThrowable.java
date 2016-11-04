package com.dff.cordova.plugin.crashreport.json.model;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonThrowable {
	public static JSONObject toJson(Throwable e) throws JSONException {
		JSONObject jsonThrowable = new JSONObject();
		jsonThrowable.put("className", e.getClass().getName());
		jsonThrowable.put("message", e.getMessage());		
		jsonThrowable.put("stackTrace", JsonStacktrace.toJson(e.getStackTrace()));
		
		Throwable cause = e.getCause();
		
		if (cause != null) {
			jsonThrowable.put("cause", JsonThrowable.toJson(cause));
		}
		
		return jsonThrowable;
	}
}
