package com.hwy.data.net.client.core;

import retrofit2.Retrofit;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/12/6 16:35
 * 修改人：heweiyun
 * 修改时间：2016/12/6 16:35
 * 修改备注：
 */

public abstract class StringConvertRetrofit extends BaseRetrofit {

    @Override
    public Retrofit.Builder customer(Retrofit.Builder builder) {
        return builder.addConverterFactory(StringConvertterFactory.create());
    }
}
