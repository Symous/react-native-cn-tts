# react-native-cn-tts
React Native TTS for Chinese


#### 描述
目前在TTS方面，iOS内置了对中文语言对支持，但是Android中并没有，该组件对用途便是为React Native Android提供中文TTS的能力，语音合成SDK来自百度语音。
如果你希望你的App在多平台下支持多种语言的TTS，可搭配使用该组件：> https://github.com/ak1394/react-native-tts。

#### 安装
```
npm install react-native-cn-tts
react-native link react-native-cn-tts
```

#### 配置
要正常使用该组件，需要先注册百度语音，并创建应用以获得授权
1. 账号注册： https://passport.baidu.com/v2/?reg&tpl=open_audio&u=http://yuyin.baidu.com/
2. 创建应用： 应用管理内创建应用 - 输入名称 - 启动语音合成服务 - 选择Android平台 - 输入最终想要集成到的App包名，如com.symous.cntts
3. 获取授权信息： 在现有应用列表内，找到创建的应用，可查看到App ID／Api Key／Secret Key

#### 开始使用
```js
//引入module
import cntts from 'react-native-cn-tts'
//注册服务
cntts.init(appID,apiKey,secretKey).then(()=>{
  //注册成功
  tts.speak('成功了，老铁！');
}).catch((e)=>{
  //注册失败
})

```


