package hwy.com.baselibrary.data.net;

import com.hwy.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/15 17:57
 * 修改人：heweiyun
 * 修改时间：2016/11/15 17:57
 * 修改备注：
 */

public abstract class BaseOkHttpClient {


    private static final long CONNECT_TIMEOUT = 30 * 1000;

    public OkHttpClient get(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));
        builder = customize(builder);
        return builder.build();
    }

    public abstract OkHttpClient.Builder customize(OkHttpClient.Builder builder);

}
