package cetcnav.com.inl2_app_sample.ui;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import cetcnav.com.inl2_app_sample.R;
import cetcnav.com.inl2_app_sample.mqtt.MqttClient;
import cetcnav.com.inl2_app_sample.mqtt.MqttUtil;
import cetcnav.com.inl2_app_sample.util.InputCacheUtil;

public class MapActivity extends AppCompatActivity implements MqttCallback{

    public static final String MAP_ACTIVITY_MAP_URL = "map.activity.map.url";
    public static final String MAP_ACTIVITY_MAP_URI = "map.activity.map.uri";
    WebView mWebViewMap;
    private String mX, mY, mOrientation, mMapUri,mMapUrl, mSampleMapUrl;
    private boolean bSampling;
    private int mSampleCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mWebViewMap = (WebView) findViewById(R.id.web_view_map);
        mMapUri = getIntent().getStringExtra(MAP_ACTIVITY_MAP_URI);
        mMapUrl = getIntent().getStringExtra(MAP_ACTIVITY_MAP_URL);

        MqttClient.getInstance(getApplicationContext()).setCallback(this);
        MqttClient.getInstance(getApplicationContext()).connect();
        showMap();
    }

    @JavascriptInterface
    public void receiveString(String value) {
        String[] params = value.split(";");
        mX = params[0];
        mY = params[1];
        mOrientation = params[2];
        bSampling = true;
        mSampleCount = 0;
    }

    private void showMap(){

            WebSettings webSettings = mWebViewMap.getSettings();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            mWebViewMap.addJavascriptInterface(this, "android");
            mWebViewMap.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url){
                    mWebViewMap.loadUrl("javascript:loadMap(\""+ mMapUrl+"\")");
                }
            });

            String mapUrl = InputCacheUtil.getMainInfo(this, InputCacheUtil.MAIN_MAP_URL);
            mWebViewMap.loadUrl(mapUrl);
    }

    @Override
    public void connectionLost(Throwable cause) {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.map_mqtt_toast_connect_lost),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if(bSampling){
            mSampleCount ++;
            JSONObject jsonObject = new JSONObject(new String(message.getPayload()));
            jsonObject.put("x", mX);
            jsonObject.put("y", mY);
            jsonObject.put("orientation",mOrientation);
            jsonObject.put("map_uri", mMapUri);
            MqttClient.getInstance(this.getApplicationContext()).publish(MqttUtil.getMqttPub(this.getApplicationContext()),jsonObject.toString());
            if (mSampleCount % 20 == 0) {
                Toast.makeText(this.getApplicationContext(),getResources().getString(R.string.map_mqtt_toast_report_process) + String.valueOf(mSampleCount)+"/100", Toast.LENGTH_SHORT).show();
            }
            if(mSampleCount > 105){
                bSampling = false;
                Toast.makeText(this.getApplicationContext(),
                        getResources().getString(R.string.map_mqtt_toast_reported_first) + String.valueOf(mSampleCount) +
                                getResources().getString(R.string.map_mqtt_toast_reported_second), Toast.LENGTH_SHORT).show();
                mSampleCount =  0;
                mWebViewMap.loadUrl("javascript:endCollecting()");
            }
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
