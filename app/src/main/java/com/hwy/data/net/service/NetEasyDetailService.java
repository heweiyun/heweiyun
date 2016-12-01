package com.hwy.data.net.service;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 19:53
 * 修改人：heweiyun
 * 修改时间：2016/11/30 19:53
 * 修改备注：
 */

public interface NetEasyDetailService {

    @GET("/nc/article/{id}/full.html")
    Observable<String> getNewsDetail(@Path("id") String id);
}
