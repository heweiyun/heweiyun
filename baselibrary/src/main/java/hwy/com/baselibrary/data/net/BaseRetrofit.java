package hwy.com.baselibrary.data.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/15 18:04
 * 修改人：heweiyun
 * 修改时间：2016/11/15 18:04
 * 修改备注：
 */

public abstract class BaseRetrofit {


    public Retrofit get(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(getBaseUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient());
        builder = customer(builder);
        return builder.build();
    }

    public abstract Retrofit.Builder customer(Retrofit.Builder builder);

    public abstract String getBaseUrl();
    public abstract OkHttpClient getOkHttpClient();

}
