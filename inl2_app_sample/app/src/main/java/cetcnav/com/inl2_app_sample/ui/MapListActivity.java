package cetcnav.com.inl2_app_sample.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cetcnav.com.inl2_app_sample.R;
import cetcnav.com.inl2_app_sample.http.retrofit.RetrofitClient;
import cetcnav.com.inl2_app_sample.http.retrofit.response.MapList;
import cetcnav.com.inl2_app_sample.http.retrofit.service.GetMapListService;
import cetcnav.com.inl2_app_sample.mqtt.MqttClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapListActivity extends AppCompatActivity {

    ListView mListViewMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);
        mListViewMap = (ListView) findViewById(R.id.list_view_map_list);
        new GetMapListAsynTask().execute(Constants.HTTP_DOMAIN);
    }

    private class GetMapListAsynTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = RetrofitClient.getInstance(strings[0]);
            GetMapListService service = retrofit.create(GetMapListService.class);
            Call<MapList> call = service.getMapList();
            try {
                Response<MapList> mapList = call.execute();
                return new Gson().toJson(mapList.body());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return  "";
        }

        @Override
        protected void onPostExecute(String result){
            MapList map_list = new Gson().fromJson(result, MapList.class);

            ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i< map_list.getMapList().size(); i++){
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("MapName", map_list.getMapList().get(i).getMapName());
                map.put("MapUri", map_list.getMapList().get(i).getMapUri());
                map.put("MapUrl", map_list.getMapList().get(i).getMapUrl());
                listItem.add(map);
            }

            SimpleAdapter listItemAdapter = new SimpleAdapter(
                    MapListActivity.this,
                    listItem,
                    R.layout.list_view_item_map_list,
                    new String[] { "MapName", "MapUri", "MapUrl"},
                    new int[] { R.id.text_view_map_name, R.id.text_view_map_uri, R.id.text_view_map_url});

            mListViewMap.setAdapter(listItemAdapter);
            mListViewMap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent();
                    intent.setClass(MapListActivity.this, MapActivity.class);
                    intent.putExtra(MapActivity.MAP_ACTIVITY_MAP_URL, ((TextView)view.findViewById(R.id.text_view_map_url)).getText().toString());
                    intent.putExtra(MapActivity.MAP_ACTIVITY_MAP_URI, ((TextView)view.findViewById(R.id.text_view_map_uri)).getText().toString());
                    MapListActivity.this.startActivity(intent);
                }
            });
        }
    }

}
