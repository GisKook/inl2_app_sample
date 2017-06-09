package cetcnav.com.inl2_app_sample.mqtt;

import android.content.Context;
import android.renderscript.ScriptGroup;

import cetcnav.com.inl2_app_sample.util.InputCacheUtil;

/**
 * Created by zhangkai on 2017/6/9.
 */

public class MqttUtil {
    public static String getMqttBrokerAddr(Context cxt){

        String broker_port = InputCacheUtil.getMainInfo(cxt, InputCacheUtil.MAIN_MQTT_BROKER_PORT);
        String broker_addr = InputCacheUtil.getMainInfo(cxt, InputCacheUtil.MAIN_MQTT_BROKER_ADDR);

        return "tcp://"+broker_addr+":"+broker_port;
    }

    public static String getMqttPub(Context cxt){
        return InputCacheUtil.getMainInfo(cxt, InputCacheUtil.MAIN_MQTT_PUB_TOPIC);
    }

    public static String getMqttSub(Context cxt){
        return InputCacheUtil.getMainInfo(cxt, InputCacheUtil.MAIN_RING_MAC);
    }
}
