package cetcnav.com.inl2_app_sample.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhangkai on 2017/6/7.
 */

public class InputCacheUtil{
    public static final  String MAIN_MQTT_BROKER_ADDR = "main_mqtt_broker_addr";
    public static final  String MAIN_MQTT_BROKER_PORT = "main_mqtt_broker_port";
    public static final  String MAIN_MQTT_PUB_TOPIC  = "main_mqtt_pub_topic";
    public static final  String MAIN_RING_MAC  = "main_ring_mac";
    public static final  String MAIN_MAP_URL  = "main_map_url";

    private static final  String MAIN_KEY = "main_key";

    public static void storeMainInfo(Context cxt, String key, String value) {
        SharedPreferences.Editor editor = cxt.getSharedPreferences(MAIN_KEY, cxt.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getMainInfo(Context cxt, String key) {
        SharedPreferences sp = cxt.getSharedPreferences(MAIN_KEY, cxt.MODE_PRIVATE);
        return sp.getString(key,"");
    }
}
