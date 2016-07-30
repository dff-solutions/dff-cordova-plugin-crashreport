package com.dff.cordova.plugin.crashreport.json.model;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonThread {
	public static JSONObject toJson(Thread t)  throws JSONException {
		JSONObject jsonThread = new JSONObject();
		jsonThread.put("isAlive", t.isAlive());
		jsonThread.put("id", t.getId());
		jsonThread.put("priority", t.getPriority());
		jsonThread.put("isDaemon", t.isDaemon());
		jsonThread.put("isInterrupted", t.isInterrupted());
		jsonThread.put("name", t.getName());
		
		return jsonThread;
	}
}
