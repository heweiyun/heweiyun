package com.hwy.data.net.client;

import com.hwy.data.net.client.core.CacheHttpClient;
import com.hwy.data.net.client.core.GsonConvertRetrofit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/28 16:53
 * 修改人：heweiyun
 * 修改时间：2016/11/28 16:53
 * 修改备注：
 */

public class ZhiHuRetrofit extends GsonConvertRetrofit{

    private CacheHttpClient mCacheHttpClient;

    @Inject
    public ZhiHuRetrofit(CacheHttpClient cacheHttpClient){
        this.mCacheHttpClient = cacheHttpClient;
    }


    @Override
    public String getBaseUrl() {
        return "http://news-at.zhihu.com";
    }

    @Override
    public OkHttpClient getOkHttpClient() {
        return mCacheHttpClient.get();
    }
}
