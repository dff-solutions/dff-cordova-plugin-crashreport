package com.dff.cordova.plugin.crashreport.json.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;

public class JsonRunningAppProcessInfo {

	public static JSONObject toJson(ActivityManager.RunningAppProcessInfo outState) throws JSONException {
		JSONObject jsonOutState = new JSONObject();
		
		int importance = outState.importance;
		int importanceReasonCode = outState.importanceReasonCode;
		String importanceName = null;
		String importanceReasonCodeName = null;
		
		switch (importance) {
		case ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND:
			importanceName = "IMPORTANCE_BACKGROUND";
			break;
		case ActivityManager.RunningAppProcessInfo.IMPORTANCE_EMPTY:
			importanceName = "IMPORTANCE_EMPTY";
			break;
		case ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND:
			importanceName = "IMPORTANCE_FOREGROUND";
			break;
		case ActivityManager.RunningAppProcessInfo.IMPORTANCE_PERCEPTIBLE:
			importanceName = "IMPORTANCE_PERCEPTIBLE";
			break;
		case ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE:
			importanceName = "IMPORTANCE_SERVICE";
			break;
		case ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE:
			importanceName = "IMPORTANCE_VISIBLE";
			break;

		default:
			break;
		}
		
		switch (importanceReasonCode) {
		case ActivityManager.RunningAppProcessInfo.REASON_PROVIDER_IN_USE:
			importanceReasonCodeName = "REASON_PROVIDER_IN_USE";
			break;
		case ActivityManager.RunningAppProcessInfo.REASON_SERVICE_IN_USE:
			importanceReasonCodeName = "REASON_SERVICE_IN_USE";
			break;
		case ActivityManager.RunningAppProcessInfo.REASON_UNKNOWN:
			importanceReasonCodeName = "REASON_UNKNOWN";
			break;

		default:
			break;
		}
		
		jsonOutState.put("importance", importance);
		jsonOutState.put("importanceName", importanceName);
		jsonOutState.put("importanceReasonCode", importanceReasonCode);
		jsonOutState.put("importanceReasonCodeName", importanceReasonCodeName);
		jsonOutState.put("importanceReasonPid", outState.importanceReasonPid);
		jsonOutState.put("lastTrimLevel", outState.lastTrimLevel);
		jsonOutState.put("lru", outState.lru);
		jsonOutState.put("pid", outState.pid);
		jsonOutState.put("uid", outState.uid);
		jsonOutState.put("processName", outState.processName);
		
		String[] pkgList = outState.pkgList;
		JSONArray jsonPkgList = new JSONArray();
		
		if (pkgList != null) {
			for (String pkg : pkgList) {
				jsonPkgList.put(pkg);
			}
		}
		
		jsonOutState.put("pkgList", jsonPkgList);
		
		return jsonOutState;
		
	}
	
	public static JSONArray toJson(ActivityManager.RunningAppProcessInfo[] processInfo) throws JSONException {
		JSONArray jsonProcessInfo = new JSONArray();
		
		if (processInfo != null) {
			for (ActivityManager.RunningAppProcessInfo pi : processInfo) {
				jsonProcessInfo.put(JsonRunningAppProcessInfo.toJson(pi));
			}
		}
		
		return jsonProcessInfo;
	}
	
	public static JSONArray toJson(List<ActivityManager.RunningAppProcessInfo> processInfo) throws JSONException {
		JSONArray jsonProcessInfo = new JSONArray();
		
		if (processInfo != null) {
			for (ActivityManager.RunningAppProcessInfo pi : processInfo) {
				jsonProcessInfo.put(JsonRunningAppProcessInfo.toJson(pi));
			}
		}
		
		return jsonProcessInfo;
	}
}
