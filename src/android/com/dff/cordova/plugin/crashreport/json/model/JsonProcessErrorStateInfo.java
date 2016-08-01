package com.dff.cordova.plugin.crashreport.json.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.ProcessErrorStateInfo;

public class JsonProcessErrorStateInfo {
	public static JSONObject toJson(ActivityManager.ProcessErrorStateInfo processErrorStateInfo) throws JSONException {
		JSONObject jsonProcessErrorStateInfo = new JSONObject();
		
		if (processErrorStateInfo != null) {
			int condition = processErrorStateInfo.condition;
			String conditionName = "";
			
			switch (condition) {
			case ProcessErrorStateInfo.CRASHED:
				conditionName = "CRASHED";
				break;
			case ProcessErrorStateInfo.NO_ERROR:
				conditionName = "NO_ERROR";
				break;
			case ProcessErrorStateInfo.NOT_RESPONDING:
				conditionName = "NOT_RESPONDING";
				break;

			default:
				break;
			}
			
			jsonProcessErrorStateInfo.put("condition", condition);
			jsonProcessErrorStateInfo.put("conditionName", conditionName);
			jsonProcessErrorStateInfo.put("pid", processErrorStateInfo.pid);
			jsonProcessErrorStateInfo.put("uid", processErrorStateInfo.uid);
			jsonProcessErrorStateInfo.put("longMsg", processErrorStateInfo.longMsg);
			jsonProcessErrorStateInfo.put("processName", processErrorStateInfo.processName);
			jsonProcessErrorStateInfo.put("shortMsg", processErrorStateInfo.shortMsg);
			jsonProcessErrorStateInfo.put("stackTrace", processErrorStateInfo.stackTrace);
			jsonProcessErrorStateInfo.put("tag", processErrorStateInfo.tag);
		}
		
		return jsonProcessErrorStateInfo;
	}
	
	public static JSONArray toJson(List<ActivityManager.ProcessErrorStateInfo> processErrorStateInfo) throws JSONException {
		JSONArray jsonProcessErrorStateInfo = new JSONArray();
		
		if (processErrorStateInfo != null) {
			for (ActivityManager.ProcessErrorStateInfo pesi : processErrorStateInfo) {
				jsonProcessErrorStateInfo.put(toJson(pesi));
			}
		}
		
		return jsonProcessErrorStateInfo;
	}
}
