package cetcnav.com.inl2_app_sample.http.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangkai on 2017/6/8.
 */
public class RetrofitClient {
    private static Retrofit ourInstance = null;

    public static synchronized Retrofit getInstance(String baseUrl) {
        if (ourInstance == null){
            ourInstance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return ourInstance;
    }
}
