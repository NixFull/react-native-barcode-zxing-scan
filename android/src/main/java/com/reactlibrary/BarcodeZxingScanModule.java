// BarcodeZxingScanModule.java

package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BarcodeZxingScanModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private final ReactApplicationContext reactContext;
    private Callback mCallback;

    public BarcodeZxingScanModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "BarcodeZxingScan";
    }

    @ReactMethod
    public void sampleMethod(String stringArgument, Promise promise) {
        // TODO: Implement some actually useful functionality
        try {
            promise.resolve(stringArgument);
        } catch (Exception e) {
            promise.reject(e);
            //TODO: handle exception
        }
    }


    @ReactMethod
    public void showQrReader(Callback callback) {
        mCallback = callback;
        IntentIntegrator integrator = new IntentIntegrator(getCurrentActivity());
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(ContinuousCaptureActivity.class);
        integrator.initiateScan();
        reactContext.addActivityEventListener(this);
    }


    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
         IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        mCallback.invoke(result.getContents(), result.getBarcodeImagePath());
        reactContext.removeActivityEventListener(this);
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

}
