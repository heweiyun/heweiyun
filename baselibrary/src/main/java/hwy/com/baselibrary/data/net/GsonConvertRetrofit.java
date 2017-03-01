package hwy.com.baselibrary.data.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/12/6 16:34
 * 修改人：heweiyun
 * 修改时间：2016/12/6 16:34
 * 修改备注：
 */

public abstract class GsonConvertRetrofit extends BaseRetrofit {

    @Override
    public Retrofit.Builder customer(Retrofit.Builder builder) {
        return builder.addConverterFactory(GsonConverterFactory.create());
    }
}
