/**
 * 
 */
package com.dff.cordova.plugin.crashreport;

import java.lang.Thread.UncaughtExceptionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dff.cordova.plugin.common.AbstractPluginListener;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;

/**
 * @author frank
 *
 */
public class CrashReporter extends AbstractPluginListener implements UncaughtExceptionHandler {
	public static final String LOG_TAG = "com.dff.cordova.plugin.crashreport.CrashReporter";
	private UncaughtExceptionHandler defaultHandler;
	
	public CrashReporter(UncaughtExceptionHandler defaultHandler) {
		this.defaultHandler = defaultHandler;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
	 */
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// first log it
		CordovaPluginLog.e(LOG_TAG, e.getMessage(), e);
		
		JSONObject jsonCrashReport = new JSONObject(); 
		JSONObject jsonThread = new JSONObject();
		JSONObject jsonThrowable = new JSONObject();
		JSONArray jsonStackTrace = new JSONArray();
		StackTraceElement stackTrace[];
		
		try {
			jsonThread.put("isAlive", t.isAlive());
			jsonThread.put("id", t.getId());
			jsonThread.put("priority", t.getPriority());
			jsonThread.put("isDaemon", t.isDaemon());
			jsonThread.put("isInterrupted", t.isInterrupted());
			jsonThread.put("name", t.getName());
			
			jsonThrowable.put("message", e.getMessage());
			
			stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++) {
				jsonStackTrace.put(stackTrace[i].toString());
			}
			
			jsonThrowable.put("stackTrace", jsonStackTrace);
			
			jsonCrashReport.put("thread", jsonThread);
			jsonCrashReport.put("throwable", jsonThrowable);
			
			super.sendPluginResult(jsonCrashReport);
		}
		catch (JSONException e1) {
			CordovaPluginLog.e(LOG_TAG, e1.getMessage(), e1);
		}
		
		// finally call initial exception handler
		defaultHandler.uncaughtException(t, e);
	}
}
