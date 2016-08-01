/**
 * 
 */
package com.dff.cordova.plugin.crashreport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.cordova.CordovaInterface;
import org.json.JSONException;
import org.json.JSONObject;

import com.dff.cordova.plugin.common.AbstractPluginListener;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;
import com.dff.cordova.plugin.crashreport.json.model.JsonDebugMemoryInfo;
import com.dff.cordova.plugin.crashreport.json.model.JsonMemoryInfo;
import com.dff.cordova.plugin.crashreport.json.model.JsonProcessErrorStateInfo;
import com.dff.cordova.plugin.crashreport.json.model.JsonRunningAppProcessInfo;
import com.dff.cordova.plugin.crashreport.json.model.JsonRunningServiceInfo;
import com.dff.cordova.plugin.crashreport.json.model.JsonThread;
import com.dff.cordova.plugin.crashreport.json.model.JsonThrowable;

import android.app.ActivityManager;
import android.content.Context;
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
		String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss.SSS").format(new Date());
		int pid = android.os.Process.myPid();
		
		try {
			try {
				ActivityManager activityManager = (ActivityManager) this.cordova.getActivity().getSystemService(Context.ACTIVITY_SERVICE);
				ActivityManager.RunningAppProcessInfo myMemoryOutState = new ActivityManager.RunningAppProcessInfo();
				ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
				
				ActivityManager.getMyMemoryState(myMemoryOutState);
				activityManager.getMemoryInfo(memoryInfo);
				android.os.Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(new int[] {pid});
				List<ActivityManager.RunningAppProcessInfo> runningAppProcessessInfo = activityManager.getRunningAppProcesses();
				List<ActivityManager.ProcessErrorStateInfo> processErrorStateInfo = activityManager.getProcessesInErrorState();
				List<ActivityManager.RunningServiceInfo> runningServiceInfo = activityManager.getRunningServices(1000);
				
				jsonCrashReport.put("runningAppProcesses", JsonRunningAppProcessInfo.toJson(runningAppProcessessInfo));
				jsonCrashReport.put("processErrorStateInfo", JsonProcessErrorStateInfo.toJson(processErrorStateInfo));
				jsonCrashReport.put("runningServiceInfo", JsonRunningServiceInfo.toJson(runningServiceInfo));				
				jsonCrashReport.put("myMemoryState", JsonRunningAppProcessInfo.toJson(myMemoryOutState));
				jsonCrashReport.put("memoryInfo", JsonMemoryInfo.toJson(memoryInfo));
				jsonCrashReport.put("debugMemoryInfo", JsonDebugMemoryInfo.toJson(memoryInfos));
				jsonCrashReport.put("memoryClass", activityManager.getMemoryClass());
				jsonCrashReport.put("lowRamDevice", activityManager.isLowRamDevice());
				jsonCrashReport.put("isUserAMonkey", ActivityManager.isUserAMonkey());
				jsonCrashReport.put("isRunningInTestHarness", ActivityManager.isRunningInTestHarness());
			}
			catch (Exception e1) {
				CordovaPluginLog.e(LOG_TAG, e.getMessage(), e1);
			}			
					
			jsonCrashReport.put("pid", pid);
			jsonCrashReport.put("date", date);
			jsonCrashReport.put("thread", JsonThread.toJson(t));
			jsonCrashReport.put("throwable", JsonThrowable.toJson(e));
			
			if (isExternalStorageWritable()) {
				File crashReportDir = new File(this.cordova.getActivity().getExternalFilesDir(null), "crashreports");
				
				if (!crashReportDir.mkdirs()) {
					CordovaPluginLog.w(LOG_TAG, crashReportDir.getAbsolutePath() + " not created");
				}
				
				if (crashReportDir.exists() ) {
					String filename = "crashreport_"
							+ date
							+ ".txt";
					File crashReportFile = new File(crashReportDir, filename);
					
					try {
						if (!crashReportFile.exists() && crashReportFile.createNewFile()) {
							CordovaPluginLog.i(LOG_TAG, crashReportFile.getAbsolutePath() + " created");
						}
						
						FileOutputStream outputStream = new FileOutputStream(crashReportFile, true);
						
						outputStream.write(jsonCrashReport.toString().getBytes());
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
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

}
