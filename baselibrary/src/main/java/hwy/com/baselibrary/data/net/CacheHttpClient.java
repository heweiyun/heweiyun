package hwy.com.baselibrary.data.net;

import android.app.Application;

import com.hwy.common.util.NetworkUtil;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/12/6 10:35
 * 修改人：heweiyun
 * 修改时间：2016/12/6 10:35
 * 修改备注：
 */

public class CacheHttpClient extends BaseOkHttpClient {
    private static final long CACHE_SIZE = 1024 * 1024 * 50;

    @Inject
    Application mContext;

    @Inject
    public CacheHttpClient(){}

    @Override
    public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
        File cacheFile = new File(mContext.getCacheDir(),"hey_cache");
        builder.cache(new Cache(cacheFile,CACHE_SIZE));
        builder.addNetworkInterceptor(mCacheInterCept);
        return builder;
    }

    private Interceptor mCacheInterCept = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtil.isNetworkAvailable(mContext)){
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            Response response = chain.proceed(request);
            if (NetworkUtil.isNetworkAvailable(mContext)){
                String cacheControl = request.cacheControl().toString();
                return response.newBuilder().addHeader("Cache-Control",cacheControl).build();
            }else {
                return response.newBuilder().addHeader("Cache-Control",CacheControl.FORCE_CACHE.toString()).build();
            }
        }
    };

}
