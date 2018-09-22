package com.ferrinweb;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONException;

import java.util.Date;
import java.util.List;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


/**
 * Created by ferrinweb on 2018/9/22.
 * A Cordova Plugin: Get data from smartpeak PDA laser Scanner.
 */

public class LaserScannerUtil extends CordovaPlugin {

    private CallbackContext callbackContext;
    private PluginResult pluginResult;
    private List<Object> codeCache = new ArrayList<Object>();
    private String scanning = "laser scanner scanning...";
    private String closed = "laser scanner closed.";

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        if (action != null) {
            if ("history".equals(action)) {
                // get scan result
                callbackContext.success(codeCache.toString());
                return true;
            } else if ("scan".equals(action)) {
                // exec scan
                callbackContext.success(scanning);
                execScan(callbackContext);
                return true;
            } else if ("listen".equals(action)) {
                this.callbackContext = callbackContext;
                // open service
                setListener(callbackContext);
                return true;
            } else if ("close".equals(action)) {
                // close service
                removeListener(callbackContext);
                return true;
            }
        } else {
            callbackContext.error("laser scanner error: you must present an action param to use the plugin.");
        }

        return super.execute(action, args, callbackContext);
    }

    /**
     * set mScanDataReceiver
     */
    private void setListener(CallbackContext callbackContext) {
        Intent scanIntent = new Intent("com.android.scanservice.scan.on");
        // register a receiver
        IntentFilter scanDataIntentFilter = new IntentFilter();
        scanDataIntentFilter.addAction("com.android.scancontext");
        cordova.getActivity().registerReceiver(mScanDataReceiver, scanDataIntentFilter);

        cordova.getActivity().sendBroadcast(scanIntent);

        pluginResult = new PluginResult(PluginResult.Status.OK, "scanner is opened and start listening for scan result.");
        pluginResult.setKeepCallback(true);
        callbackContext.sendPluginResult(pluginResult);
    }

    /**
     * remove mScanDataReceiver
     */
    private void removeListener(CallbackContext callbackContext) {
        cordova.getActivity().unregisterReceiver(mScanDataReceiver);

        Intent scanIntent = new Intent("com.android.scanservice.scan.off");
        cordova.getActivity().sendBroadcast(scanIntent);

        codeCache.clear();

        callbackContext.success(closed);
    }

    /**
     * exec scan action
     */
    private void execScan(CallbackContext callbackContext) {
        Intent intent = new Intent("android.intent.action.FUNCTION_BUTTON_DOWN", null);
        cordova.getActivity().sendBroadcast(intent);
    }

    /**
     * scan result receiver
     */
    private BroadcastReceiver mScanDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.android.scancontext")) {
                String str = intent.getStringExtra("Scan_context");

                if (str != "" && str != null) {
                    // cache scan result
                    Map codeData = new HashMap();
                    codeData.put("timestamp", new Date().getTime());
                    codeData.put("code", str);
                    codeCache.add(codeData);

                    pluginResult = new PluginResult(PluginResult.Status.OK, str);
                } else {
                    pluginResult = new PluginResult(PluginResult.Status.ERROR, "scan result is empty.");
                }

                pluginResult.setKeepCallback(true);
                callbackContext.sendPluginResult(pluginResult);
            }
        }
    };
}
