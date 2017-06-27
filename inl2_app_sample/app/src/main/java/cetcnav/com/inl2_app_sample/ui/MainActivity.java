package cetcnav.com.inl2_app_sample.ui;

import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cetcnav.com.inl2_app_sample.R;
import cetcnav.com.inl2_app_sample.util.InputCacheUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mMqttAddr,mMqttPort,mMqttPub, mRingMac, mMapUrl;
    private Button mBtnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMqttAddr = (EditText) this.findViewById(R.id.edit_text_mqtt_domain);
        mMqttPort = (EditText) this.findViewById(R.id.edit_text_mqtt_port);
        mMqttPub = (EditText) this.findViewById(R.id.edit_text_mqtt_pub);
        mRingMac = (EditText) this.findViewById(R.id.edit_text_ring_mac);
        mMapUrl = (EditText) this.findViewById(R.id.edit_text_map_url);

        mBtnOK = (Button) this.findViewById(R.id.button_main_ok);
        mBtnOK.setOnClickListener(this);

        mMqttAddr.setText(InputCacheUtil.getMainInfo(this, InputCacheUtil.MAIN_MQTT_BROKER_ADDR));
        mMqttPort.setText(InputCacheUtil.getMainInfo(this, InputCacheUtil.MAIN_MQTT_BROKER_PORT));
        mMqttPub.setText(InputCacheUtil.getMainInfo(this, InputCacheUtil.MAIN_MQTT_PUB_TOPIC));
        mRingMac.setText(InputCacheUtil.getMainInfo(this, InputCacheUtil.MAIN_RING_MAC));
        mMapUrl.setText(InputCacheUtil.getMainInfo(this, InputCacheUtil.MAIN_MAP_URL));
    }

    private boolean checkInput(){
        String mqtt_broker_addr = mMqttAddr.getText().toString();
        String mqtt_broker_port = mMqttPort.getText().toString();
        String mqtt_pub = mMqttPub.getText().toString();
        String ring_mac = mRingMac.getText().toString().toLowerCase();
        String map_url = mMapUrl.getText().toString();

        InputCacheUtil.storeMainInfo(this, InputCacheUtil.MAIN_MQTT_BROKER_ADDR, mqtt_broker_addr);
        InputCacheUtil.storeMainInfo(this, InputCacheUtil.MAIN_MQTT_BROKER_PORT, mqtt_broker_port);
        InputCacheUtil.storeMainInfo(this, InputCacheUtil.MAIN_MQTT_PUB_TOPIC, mqtt_pub);
        InputCacheUtil.storeMainInfo(this, InputCacheUtil.MAIN_RING_MAC, ring_mac);
        InputCacheUtil.storeMainInfo(this, InputCacheUtil.MAIN_MAP_URL, map_url);


        if (mqtt_broker_addr.trim().isEmpty()){
            Toast.makeText(this, getResources().getText(R.string.main_toast_mqtt_domain_should_not_empty),Toast.LENGTH_SHORT).show();
            mMqttAddr.requestFocus();
            return false;
        }

        if (mqtt_broker_port.trim().isEmpty()){
            Toast.makeText(this, getResources().getText(R.string.main_toast_mqtt_port_should_not_empty),Toast.LENGTH_SHORT).show();
            mMqttPort.requestFocus();
            return false;
        }

        if (mqtt_pub.trim().isEmpty()){
            Toast.makeText(this, getResources().getText(R.string.main_toast_mqtt_pub_should_not_empty),Toast.LENGTH_SHORT).show();
            mMqttPub.requestFocus();
            return false;
        }

        if (ring_mac.trim().isEmpty()){
            Toast.makeText(this, getResources().getText(R.string.main_toast_ring_mac_should_not_empty),Toast.LENGTH_SHORT).show();
            mRingMac.requestFocus();
            return false;
        }

        if (map_url.trim().isEmpty()){
            Toast.makeText(this, getResources().getText(R.string.main_toast_map_url_should_not_empty),Toast.LENGTH_SHORT).show();
            mMapUrl.requestFocus();
            return false;
        }

        return  true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_main_ok: {
                if (checkInput()) {
                    Intent i = new Intent();
                    i.setClass(this, MapListActivity.class);
                    startActivity(i);

                }
            }
        }
    }
}
