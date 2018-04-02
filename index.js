import {
  NativeModules,
  NativeEventEmitter,
} from 'react-native';
// export default NativeModules.CNTTS;
const CNTTS = NativeModules.CNTTS;

class Tts extends NativeEventEmitter {
    constructor() {
        super(CNTTS);
    }

    init=CNTTS.init

    speak (content) {
        return CNTTS.speak(content)
    }
    stop () {
        return CNTTS.stop()
    }
    resume () {
        return CNTTS.resume()
    }
    pause () {
        return CNTTS.pause()
    }

    addEventListener(type, handler) {
        this.addListener(type, handler);
    }

    removeEventListener(type, handler) {
        this.removeListener(type, handler);
    }
}

export default new Tts()