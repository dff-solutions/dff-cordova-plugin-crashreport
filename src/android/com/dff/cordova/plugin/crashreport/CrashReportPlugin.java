/**
 * 
 */
package com.dff.cordova.plugin.crashreport;

import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import com.dff.cordova.plugin.common.CommonPlugin;
import com.dff.cordova.plugin.common.log.CordovaPluginLog;

import android.os.DeadObjectException;

/**
 * @author frank
 *
 */
public class CrashReportPlugin extends CommonPlugin {
	public static final String LOG_TAG = "com.dff.cordova.plugin.crashreport.CrashReportPlugin";
	
	private CrashReporter crashReporter;
	
	public CrashReportPlugin() {
		super(LOG_TAG);
	}
	
	
    /**
	 * Called after plugin construction and fields have been initialized.
	 */
	@Override
	public void pluginInitialize() {
		super.pluginInitialize();
		
		UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
		this.crashReporter = new CrashReporter(handler);
		Thread.setDefaultUncaughtExceptionHandler(this.crashReporter);
	}
	
    /**
     * The final call you receive before your activity is destroyed.
     */
	@Override
	public void onDestroy() {		
		super.onDestroy();
	}
	
    /**
     * Executes the request.
     *
     * This method is called from the WebView thread.
     * To do a non-trivial amount of work, use:
     * cordova.getThreadPool().execute(runnable);
     *
     * To run on the UI thread, use:
     * cordova.getActivity().runOnUiThread(runnable);
     *
     * @param action The action to execute.
     * @param args The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return Whether the action was valid.
     */
 	@Override
     public boolean execute(String action
     		, final JSONArray args
     		, final CallbackContext callbackContext)
         throws JSONException {
 		
     	CordovaPluginLog.i(LOG_TAG, "call for action: " + action + "; args: " + args);
     	
     	if (action.equals("onCrash")) {
     		this.crashReporter.setCallBack(callbackContext);
     		
     		return true;
     	}
     	else if (action.equals("throwDeadObjectException")) {     		
     		throw new Error(new DeadObjectException());
     	}
     	
     	return super.execute(action, args, callbackContext);
     }
}
