package com.hwy.data.net.client;

import com.hwy.data.net.client.core.StringConvertRetrofit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 19:51
 * 修改人：heweiyun
 * 修改时间：2016/11/30 19:51
 * 修改备注：
 */

public class NetEasyStringRetrofit extends StringConvertRetrofit {

    @Inject
    public NetEasyStringRetrofit(){}


    @Override
    public String getBaseUrl() {
        return "http://c.m.163.com";
    }


    @Override
    public OkHttpClient getOkHttpClient() {
        return new SimpleOkHttpClient().get();
    }

}
