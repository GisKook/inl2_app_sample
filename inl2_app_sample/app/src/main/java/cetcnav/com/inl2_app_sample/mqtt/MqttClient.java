package cetcnav.com.inl2_app_sample.mqtt;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import cetcnav.com.inl2_app_sample.R;

/**
 * Created by zhangkai on 2017/6/9.
 */

public class MqttClient {
    private static Context mContext;
    private static MqttClient instance;
    private MqttAndroidClient mqttAndroidClient;
    private MqttClient(Context cxt) {
        mqttAndroidClient = new MqttAndroidClient(cxt, MqttUtil.getMqttBrokerAddr(cxt),MqttUtil.getMqttSub(mContext));
    }

    public static synchronized MqttClient getInstance(Context cxt){
        mContext = cxt;
        if(instance == null){
            instance = new MqttClient(cxt);
        }
        return instance;
    }

    public void setCallback(MqttCallback callback){
        mqttAndroidClient.setCallback(callback);
    }

    public void connect(){
        try {
            mqttAndroidClient.connect(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    try {
                        Toast.makeText(mContext, mContext.getResources().getText(R.string.map_list_toast_mqtt_connect_success),Toast.LENGTH_SHORT).show();
                        mqttAndroidClient.subscribe(MqttUtil.getMqttSub(mContext), 0);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(mContext, mContext.getResources().getText(R.string.map_list_toast_mqtt_connect_fail),Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String value){
        try {
            mqttAndroidClient.publish(topic, new MqttMessage(value.getBytes()));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
