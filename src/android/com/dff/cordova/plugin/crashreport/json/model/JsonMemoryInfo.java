package com.dff.cordova.plugin.crashreport.json.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;

public class JsonMemoryInfo {
	public static JSONObject toJson(ActivityManager.MemoryInfo memoryInfo) throws JSONException {
		JSONObject jsonMemoryInfo = new JSONObject();
		
		jsonMemoryInfo.put("availMem", memoryInfo.availMem);
		jsonMemoryInfo.put("lowMemory", memoryInfo.lowMemory);
		jsonMemoryInfo.put("threshold", memoryInfo.threshold);
		jsonMemoryInfo.put("totalMem", memoryInfo.totalMem);
		
		return jsonMemoryInfo;
	}
	
	public static JSONArray toJson(ActivityManager.MemoryInfo[] memoryInfo) throws JSONException {
		JSONArray jsonMemoryInfo = new JSONArray();
		
		if (memoryInfo != null) {
			for (ActivityManager.MemoryInfo mi : memoryInfo) {
				jsonMemoryInfo.put(JsonMemoryInfo.toJson(mi));
			}
		}
		
		return jsonMemoryInfo;
	}
}
