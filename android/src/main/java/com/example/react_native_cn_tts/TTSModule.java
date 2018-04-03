package com.example.react_native_cn_tts;

import android.content.Context;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.*;

import java.io.IOException;

/**
 * Created by Symous on 2017-07-25.
 */

public class TTSModule extends ReactContextBaseJavaModule implements SpeechSynthesizerListener {

    Context context;
    BaiduTTS baiduTTS;

    public TTSModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext;
        try {
            baiduTTS = new BaiduTTS(context, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "CNTTS";
    }


    @ReactMethod
    public void init(String appID, String apiKey, String secKey, Promise promise){
        boolean result = baiduTTS.initTts(appID,apiKey,secKey);
        if(result) promise.resolve(null);
        else promise.reject("-1","init error");
    }
    @ReactMethod
    public void speak(String content){
        baiduTTS.speak(content);
    }

    @ReactMethod
    public void stop(){
        baiduTTS.stop();
    }

    @ReactMethod
    public void resume(){
        baiduTTS.resume();
    }

    @ReactMethod
    public void pause(){
        baiduTTS.pause();
    }

    @Override
    public void onSynthesizeStart(String s) {

    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

    }

    @Override
    public void onSynthesizeFinish(String s) {

    }

    @Override
    public void onSpeechStart(String s) {
        sendEvent("tts-start", s);
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {
        sendEvent("tts-progress", s);
    }

    @Override
    public void onSpeechFinish(String s) {
        sendEvent("tts-finish", s);
    }

    @Override
    public void onError(String s, SpeechError speechError) {
        sendEvent("tts-error", s);
    }

    private void sendEvent(String eventName, String utteranceId) {
        WritableMap params = Arguments.createMap();
        params.putString("utteranceId", utteranceId);
        getReactApplicationContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
