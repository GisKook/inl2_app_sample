package cetcnav.com.inl2_app_sample.http.retrofit.response;

import java.util.List;

/**
 * Created by zhangkai on 2017/6/8.
 */

public class MapList {
    /**
     * MapUri : 1
     * MapUrl : http://222.222.218.50:8886/zhanting/map.html
     * MapName : 地图1
     */

    private List<MapListBean> MapList;

    public List<MapListBean> getMapList() {
        return MapList;
    }

    public void setMapList(List<MapListBean> MapList) {
        this.MapList = MapList;
    }

    public static class MapListBean {
        private int MapUri;
        private String MapUrl;
        private String MapName;

        public int getMapUri() {
            return MapUri;
        }

        public void setMapUri(int MapUri) {
            this.MapUri = MapUri;
        }

        public String getMapUrl() {
            return MapUrl;
        }

        public void setMapUrl(String MapUrl) {
            this.MapUrl = MapUrl;
        }

        public String getMapName() {
            return MapName;
        }

        public void setMapName(String MapName) {
            this.MapName = MapName;
        }
    }
}
