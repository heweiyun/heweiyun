package com.hwy.data.net.client;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 19:51
 * 修改人：heweiyun
 * 修改时间：2016/11/30 19:51
 * 修改备注：
 */

public class NetEasyStringRetrofit extends NetEasyGsonRetrofit {

    @Inject
    public NetEasyStringRetrofit(){}

    @Override
    public Retrofit.Builder customer(Retrofit.Builder builder) {
        builder.addConverterFactory(StringConvertterFactory.create());
        return builder;
    }

}
