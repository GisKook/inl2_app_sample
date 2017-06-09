package cetcnav.com.inl2_app_sample.http.retrofit.service;

import java.util.List;

import cetcnav.com.inl2_app_sample.http.retrofit.response.MapList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by zhangkai on 2017/6/8.
 */

public interface GetMapListService {
    @GET("/inl2/get_map_list")
    Call<MapList> getMapList();
}
