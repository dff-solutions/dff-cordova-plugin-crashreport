package com.dff.cordova.plugin.crashreport.json.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;

public class JsonRunningServiceInfo {
	public static JSONObject toJson(ActivityManager.RunningServiceInfo runningServiceInfo) throws JSONException {
		JSONObject jsonRunningServiceInfo = new JSONObject();
		
		if (runningServiceInfo != null) {
			jsonRunningServiceInfo.put("activeSince", runningServiceInfo.activeSince);
			jsonRunningServiceInfo.put("clientCount", runningServiceInfo.clientCount);
			jsonRunningServiceInfo.put("clientLabel", runningServiceInfo.clientLabel);
			jsonRunningServiceInfo.put("clientPackage", runningServiceInfo.clientPackage);
			jsonRunningServiceInfo.put("crashCount", runningServiceInfo.crashCount);
			
			int flags = runningServiceInfo.flags;
			JSONArray jsonFlagNames = new JSONArray();
			
			if ((flags & RunningServiceInfo.FLAG_FOREGROUND) == RunningServiceInfo.FLAG_FOREGROUND) {
				jsonFlagNames.put("FOREGROUND");
			}
			
			if ((flags & RunningServiceInfo.FLAG_PERSISTENT_PROCESS) == RunningServiceInfo.FLAG_PERSISTENT_PROCESS) {
				jsonFlagNames.put("PERSISTENT_PROCESS");
			}
			
			if ((flags & RunningServiceInfo.FLAG_STARTED) == RunningServiceInfo.FLAG_STARTED) {
				jsonFlagNames.put("STARTED");
			}
			
			if ((flags & RunningServiceInfo.FLAG_SYSTEM_PROCESS) == RunningServiceInfo.FLAG_SYSTEM_PROCESS) {
				jsonFlagNames.put("SYSTEM_PROCESS");
			}
			
			jsonRunningServiceInfo.put("flags", flags);
			jsonRunningServiceInfo.put("flagNames", jsonFlagNames);
			jsonRunningServiceInfo.put("foreground", runningServiceInfo.foreground);
			jsonRunningServiceInfo.put("lastActivityTime", runningServiceInfo.lastActivityTime);
			jsonRunningServiceInfo.put("pid", runningServiceInfo.pid);
			jsonRunningServiceInfo.put("restarting", runningServiceInfo.restarting);
			jsonRunningServiceInfo.put("started", runningServiceInfo.started);
			jsonRunningServiceInfo.put("uid", runningServiceInfo.uid);
			
			ComponentName componentName = runningServiceInfo.service;
			
			if (componentName != null) {
				jsonRunningServiceInfo.put("service", componentName.flattenToString());
			}			
			
		}
		
		return jsonRunningServiceInfo;
	}
	
	public static JSONArray toJson(List<ActivityManager.RunningServiceInfo> runningServiceInfo) throws JSONException {
		JSONArray jsonRunningServiceInfo = new JSONArray();
		
		if (runningServiceInfo != null) {
			for (ActivityManager.RunningServiceInfo rsi : runningServiceInfo) {
				jsonRunningServiceInfo.put(toJson(rsi));
			}
		}
		
		return jsonRunningServiceInfo;
	}
}
