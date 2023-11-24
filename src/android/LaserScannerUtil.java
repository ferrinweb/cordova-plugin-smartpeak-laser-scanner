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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


/**
 * Created by ferrinweb on 2018/9/22.
 * A Cordova Plugin: Get data from seuic PDA laser Scanner.
 */

public class LaserScannerUtil extends CordovaPlugin {

    private PluginResult pluginResult;
    private CallbackContext cc;

    private JSONObject result = new JSONObject();

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        cc = callbackContext;
        if (action != null) {
            if ("init".equals(action)) {
                try {
                    setListener();
                    result.put("message", "scanner is opened and start listening for scan result.");
                    cc.sendPluginResult(prepareResult(result, PluginResult.Status.OK));
                    return true;
                } catch (Exception e) {
                    result.put("error", e.getMessage());
                    cc.error(result.toString());
                }
            }
        } else {
            result.put("error", "Use int method to use the plugin, please check your input");
            cc.error(result.toString());
        }

        return super.execute(action, args, cc);
    }

    private PluginResult prepareResult (JSONObject map, PluginResult.Status type) {
        pluginResult = new PluginResult(type, map.toString());
        pluginResult.setKeepCallback(true);
        return pluginResult;
    }

    /**
     * set mScanDataReceiver
     */
    private void setListener() {
        // register a receiver
        IntentFilter scanDataIntentFilter = new IntentFilter();
        scanDataIntentFilter.addAction("com.android.server.scannerservice.broadcast");
        cordova.getActivity().registerReceiver(mScanDataReceiver, scanDataIntentFilter);
    }

    /**
     * scan result receiver
     */
    private BroadcastReceiver mScanDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.android.server.scannerservice.broadcast")) {
                String str = intent.getStringExtra("scannerdata");

                result.remove("message");
                result.remove("error");
                if (str != "" && str != null) {
                    result.put("text", str);
                    pluginResult = prepareResult(result, PluginResult.Status.OK);
                } else {
                    result.put("error", "scan result is empty.");
                    pluginResult = prepareResult(result, PluginResult.Status.ERROR);
                }
                cc.sendPluginResult(pluginResult);
            }
        }
    };
}
