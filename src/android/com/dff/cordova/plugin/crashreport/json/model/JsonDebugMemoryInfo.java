package com.dff.cordova.plugin.crashreport.json.model;

import android.os.Debug;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonDebugMemoryInfo {
	public static JSONObject toJson(Debug.MemoryInfo memoryInfo) throws JSONException {
		JSONObject jsonMemoryInfo = new JSONObject();
		
		if (memoryInfo != null) {
			jsonMemoryInfo.put("dalvikPrivateDirty", memoryInfo.dalvikPrivateDirty);
			jsonMemoryInfo.put("dalvikPss", memoryInfo.dalvikPss);
			jsonMemoryInfo.put("dalvikSharedDirty", memoryInfo.dalvikSharedDirty);
			jsonMemoryInfo.put("nativePrivateDirty", memoryInfo.nativePrivateDirty);
			jsonMemoryInfo.put("nativePss", memoryInfo.nativePss);
			jsonMemoryInfo.put("nativeSharedDirty", memoryInfo.nativeSharedDirty);
			jsonMemoryInfo.put("otherPrivateDirty", memoryInfo.otherPrivateDirty);
			jsonMemoryInfo.put("otherPss", memoryInfo.otherPss);
			jsonMemoryInfo.put("otherSharedDirty", memoryInfo.otherSharedDirty);
			jsonMemoryInfo.put("totalPrivateClean", memoryInfo.getTotalPrivateClean());
			jsonMemoryInfo.put("totalPrivateDirty", memoryInfo.getTotalPrivateDirty());
			jsonMemoryInfo.put("totalPss", memoryInfo.getTotalPss());
			jsonMemoryInfo.put("totalSharedClean", memoryInfo.getTotalSharedClean());
			jsonMemoryInfo.put("totalSharedDirty", memoryInfo.getTotalSharedDirty());
 	 		jsonMemoryInfo.put("totalSwappablePss", memoryInfo.getTotalSwappablePss());
		}	
		
		return jsonMemoryInfo;
		
	}
	
	public static JSONArray toJson(Debug.MemoryInfo[] memoryInfo) throws JSONException {
		JSONArray jsonMemoryInfo = new JSONArray();
		
		if (memoryInfo != null) {
			for (Debug.MemoryInfo mi : memoryInfo) {
				jsonMemoryInfo.put(JsonDebugMemoryInfo.toJson(mi));
			}
		}
		
		return jsonMemoryInfo;
	}
}
