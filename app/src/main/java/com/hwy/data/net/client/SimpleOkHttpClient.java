package com.hwy.data.net.client;

import com.hwy.data.net.client.core.BaseOkHttpClient;

import okhttp3.OkHttpClient;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/28 17:46
 * 修改人：heweiyun
 * 修改时间：2016/11/28 17:46
 * 修改备注：
 */

public class SimpleOkHttpClient extends BaseOkHttpClient {
    @Override
    public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
        return builder;
    }
}
