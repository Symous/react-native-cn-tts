package com.example.react_native_cn_tts;

import android.content.Context;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.IOException;

/**
 * Created by Symous on 2017-07-25.
 */

public class TTSModule extends ReactContextBaseJavaModule {

    Context context;
    BaiduTTS baiduTTS;

    public TTSModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext;
        try {
            baiduTTS = new BaiduTTS(context);
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

}
