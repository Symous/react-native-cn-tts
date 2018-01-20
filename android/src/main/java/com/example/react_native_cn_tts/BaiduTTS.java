package com.example.react_native_cn_tts;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Symous on 2017-07-25.
 */

public class BaiduTTS implements SpeechSynthesizerListener{

    String textFile = "bd_etts_ch_text.dat";
    String speechFile = "bd_etts_ch_speech_female.dat";
    //String appId = "10716869",apiKey="5xuBepa3nHKnNhygRi0HfHlp",secKey="e0ee91d8555e2b15a64110d8c4440d74";
    // 语音合成客户端
    private SpeechSynthesizer mSpeechSynthesizer;
    String path = Environment.getExternalStorageDirectory().toString()+"/CNTTS/语音文件/";
    public BaiduTTS(Context context) throws IOException {

        if(!new File(path+textFile).exists() || !new File(path+speechFile).exists()){
            copySpeechFileFromAssert(context,"data",path);
        }

        // 获取语音合成对象实例
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        // 设置context
        mSpeechSynthesizer.setContext(context);
        // 设置语音合成状态监听器
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 设置语音合成文本模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, path+textFile);
        // 设置语音合成声音模型文件
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, path+speechFile);
        // 设置语音合成声音授权文件
        //mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, "your_licence_path");
    }

    public void speak(String content){
        mSpeechSynthesizer.speak(content);
    }
    public void pause(){mSpeechSynthesizer.pause();}
    public void resume(){mSpeechSynthesizer.resume();}
    public void stop(){ mSpeechSynthesizer.stop();}


    public boolean initTts(String appID,String apiKey,String secKey){
        // 设置离线语音合成授权，需要填入从百度语音官网申请的app_id
        mSpeechSynthesizer.setAppId(appID);
        // 设置在线语音合成授权，需要填入从百度语音官网申请的api_key和secret_key
        mSpeechSynthesizer.setApiKey(apiKey,secKey);
        // 获取语音合成授权信息
        AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);
        // 判断授权信息是否正确，如果正确则初始化语音合成器并开始语音合成，如果失败则做错误处理
        if (authInfo.isSuccess()) {
            int result = mSpeechSynthesizer.initTts(TtsMode.MIX);
            Log.d("cntts","init successfully :"+result);
            return true;
        } else {
            String errMsg = authInfo.getTtsError().getDetailMessage();
            Log.d("cntts",errMsg);
            return false;
        }
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

    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {

    }

    @Override
    public void onError(String s, SpeechError speechError) {

    }

    public void copySpeechFileFromAssert(Context context,String oldPath,String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);//获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {//如果是目录
                File file = new File(newPath);
                file.mkdirs();//如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copySpeechFileFromAssert(context,oldPath + "/" + fileName,newPath+"/"+fileName);
                }
            } else {//如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount=0;
                while((byteCount=is.read(buffer))!=-1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
