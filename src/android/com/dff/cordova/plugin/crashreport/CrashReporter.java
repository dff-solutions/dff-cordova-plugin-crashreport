/**
 * 
 */
package com.dff.cordova.plugin.crashreport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dff.cordova.plugin.common.AbstractPluginListener;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;

import android.os.Environment;

/**
 * @author frank
 *
 */
public class CrashReporter extends AbstractPluginListener implements UncaughtExceptionHandler {
	public static final String LOG_TAG = "com.dff.cordova.plugin.crashreport.CrashReporter";
	private UncaughtExceptionHandler defaultHandler;
	private CordovaInterface cordova;
	
	public CrashReporter(UncaughtExceptionHandler defaultHandler, CordovaInterface cordova) {
		this.defaultHandler = defaultHandler;
		this.cordova = cordova;
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
			
			if (isExternalStorageWritable()) {
				File crashReportDir = new File(this.cordova.getActivity().getExternalFilesDir(null), "crashreports");
				
				if (!crashReportDir.mkdirs()) {
					CordovaPluginLog.w(LOG_TAG, crashReportDir.getAbsolutePath() + " not created");
				}
				
				if (crashReportDir.exists() ) {
					String filename = "crashreport_" + System.currentTimeMillis() + ".txt";
					File crashReportFile = new File(crashReportDir, filename);
					
					try {
						if (!crashReportFile.exists() && crashReportFile.createNewFile()) {
							CordovaPluginLog.i(LOG_TAG, "created new file: " + crashReportFile.getAbsolutePath());
						}
						
						FileOutputStream outputStream = new FileOutputStream(crashReportFile, true);
						
						outputStream.write(jsonCrashReport.toString(4).getBytes());
						outputStream.flush();
						outputStream.close();						
					}
					catch (FileNotFoundException e1) {
						CordovaPluginLog.e(LOG_TAG, e1.getMessage(), e);
					}
					catch (IOException e1) {
						CordovaPluginLog.e(LOG_TAG, e1.getMessage(), e);
					}
				}
				else {
					CordovaPluginLog.w(LOG_TAG, crashReportDir.getAbsolutePath() + " does not exist");
				}
			}
			
			super.sendPluginResult(jsonCrashReport);
		}
		catch (JSONException e1) {
			CordovaPluginLog.e(LOG_TAG, e1.getMessage(), e1);
		}
		
		// finally call initial exception handler
		defaultHandler.uncaughtException(t, e);
	}
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

}
