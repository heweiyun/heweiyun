package com.hwy.data.net.client;

import com.hwy.data.net.client.core.ApiEndpoint;
import com.hwy.data.net.client.core.BaseRetrofit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/28 16:53
 * 修改人：heweiyun
 * 修改时间：2016/11/28 16:53
 * 修改备注：
 */

public class ZhiHuRetrofit extends BaseRetrofit{

    @Inject
    public ZhiHuRetrofit(){}

    @Override
    public Retrofit.Builder customer(Retrofit.Builder builder) {
        builder.addConverterFactory(GsonConverterFactory.create());
        return builder;
    }

    @Override
    public ApiEndpoint getApiEndpoint() {
        return new ApiEndpoint() {
            @Override
            public String getEndpoint() {
                return "http://news-at.zhihu.com";
            }
        };
    }

    @Override
    public OkHttpClient getOkHttpClient() {
        return new SimpleOkHttpClient().get();
    }
}
